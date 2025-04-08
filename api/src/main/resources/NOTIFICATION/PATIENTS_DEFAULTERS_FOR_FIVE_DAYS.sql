
SELECT next_appointment.patient_id, identifier.identifier, start_art.art_start_date, contact.value, MAX(next_appointment_date) next_appointment_date FROM (
	
	SELECT p.patient_id, MAX(o.value_datetime) next_appointment_date FROM patient p
		INNER JOIN encounter e ON e.patient_id = p.patient_id
		INNER JOIN obs o ON o.encounter_id = e.encounter_id
			WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0
			AND o.concept_id = 1410 AND e.encounter_type IN (6,9)
			AND e.location_id = :location
				GROUP BY p.patient_id
	UNION
	
	SELECT p.patient_id, MAX(o.value_datetime) next_appointment_date FROM patient p
		INNER JOIN encounter e ON e.patient_id = p.patient_id
		INNER JOIN obs o ON o.encounter_id = e.encounter_id
			WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0
			AND o.concept_id = 5096 AND e.encounter_type = 18
			AND e.location_id = :location
				GROUP BY p.patient_id
			
	UNION
	
	SELECT p.patient_id, DATE_ADD(MAX(o.value_datetime), INTERVAL 30 DAY) next_appointment_date FROM patient p
		INNER JOIN encounter e ON e.patient_id = p.patient_id
		INNER JOIN obs o ON o.encounter_id = e.encounter_id
			WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0
			AND o.concept_id = 23866 AND e.encounter_type = 52
			AND e.location_id = :location
				GROUP BY p.patient_id
			
	)next_appointment 
	INNER JOIN 
	(
	   	SELECT p.patient_id, MAX(e.encounter_datetime) consent_date FROM patient p
			INNER JOIN encounter e ON e.patient_id = p.patient_id
			INNER JOIN obs o ON o.encounter_id = e.encounter_id
				WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0
				AND e.encounter_type = 35 AND o.concept_id = 6306 AND o.value_coded = 1065
				AND e.encounter_datetime <= :endDate AND e.location_id = :location
					GROUP BY p.patient_id
	 )consent ON consent.patient_id = next_appointment.patient_id
	 INNER JOIN
	 (
	   	SELECT * FROM patient_identifier pi
	   		WHERE pi.preferred = 1
	   		GROUP BY pi.patient_id
      ) identifier ON identifier.patient_id = next_appointment.patient_id
	INNER JOIN
	(
		SELECT * FROM person_attribute pa
			WHERE pa.voided = 0 AND pa.person_attribute_type_id = 9
				GROUP BY pa.person_id
	)contact ON contact.person_id = next_appointment.patient_id
	INNER JOIN 
	(
		SELECT patient_id, MIN(art_start_date) art_start_date FROM
        (	
            SELECT p.patient_id,MIN(e.encounter_datetime) art_start_date FROM patient p 
                INNER JOIN encounter e ON p.patient_id=e.patient_id	
                INNER JOIN obs o ON o.encounter_id=e.encounter_id 
                    WHERE e.voided=0 AND o.voided=0 AND p.voided=0 
                    AND e.encounter_type IN (18,6,9) AND o.concept_id=1255 AND o.value_coded=1256 
                    AND e.encounter_datetime <= :endDate AND e.location_id = :location
                        GROUP BY p.patient_id
            UNION

            SELECT p.patient_id,MIN(value_datetime) art_start_date FROM patient p
                INNER JOIN encounter e ON p.patient_id=e.patient_id
                INNER JOIN obs o ON e.encounter_id=o.encounter_id
                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type in (18,6,9,53) 
                    AND o.concept_id=1190 AND o.value_datetime is NOT NULL 
                    AND o.value_datetime <= :endDate AND e.location_id = :location
                        GROUP BY p.patient_id
            
            UNION

            SELECT pg.patient_id,MIN(date_enrolled) art_start_date FROM patient p 
                INNER JOIN patient_program pg ON p.patient_id=pg.patient_id
                    WHERE pg.voided=0 AND p.voided=0 AND program_id=2 AND date_enrolled <= :endDate AND pg.location_id = :location
                        GROUP BY pg.patient_id
            
            UNION
            
            SELECT e.patient_id, MIN(e.encounter_datetime) AS art_start_date FROM patient p
                INNER JOIN encounter e ON p.patient_id=e.patient_id
                    WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 AND e.encounter_datetime <= :endDate AND e.location_id = :location
                        GROUP BY p.patient_id
            
            UNION
            
            SELECT p.patient_id,MIN(value_datetime) art_start_date FROM patient p
                INNER JOIN encounter e ON p.patient_id=e.patient_id
                INNER JOIN obs o ON e.encounter_id=o.encounter_id
                    WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=52 
                    AND o.concept_id=23866 and o.value_datetime is NOT NULL 
                    AND o.value_datetime <= :endDate AND e.location_id = :location
                        GROUP BY p.patient_id	

        )min_art_start_date GROUP BY min_art_start_date.patient_id
     )start_art ON start_art.patient_id = next_appointment.patient_id
	WHERE DATEDIFF( :endDate, next_appointment_date) = 5 
		GROUP BY next_appointment.patient_id