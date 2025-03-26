/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.smartnotifier.api.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.smartnotifier.api.model.Item;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("smartnotifier.PatientNotificationDAO")
public class PatientNotificationDAO {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(final String uuid) {
		return (Item) this.getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public PatientNotification savePatientNotification(final PatientNotification patientNotification) {
		this.getSession().saveOrUpdate(patientNotification);
		return patientNotification;
	}
	
	public List<PatientNotification> getPatientsToNotify(final String query, final Map<String, Object> params) {
		
		final SQLQuery sqlQuery = this.getSession().createSQLQuery(query);
		
		for (final Entry<String, Object> param : params.entrySet()) {
			
			sqlQuery.setParameter(param.getKey(), param.getValue());
			
			if (param.getValue() instanceof LocalDate) {
				sqlQuery.setParameter(param.getKey(), DateUtil.toTimestamp((LocalDate) param.getValue()));
			}
		}
		
		@SuppressWarnings("unchecked")
		final List<Object[]> result = sqlQuery.list();
		
		final List<PatientNotification> patientNotifications = new ArrayList<PatientNotification>();
		
		for (final Object[] row : result) {
			final PatientNotification notification = new PatientNotification();
			final Patient patient = new Patient();
			
			patient.setId((Integer) row[0]);
			
			notification.setPatient(patient);
			notification.setIdentifier((String) row[1]);
			notification.setArtStartDate((Timestamp) row[2]);
			notification.setPhoneNumebr((String) row[3]);
			notification.setAppointmentDate((Timestamp) row[4]);
			
			patientNotifications.add(notification);
			
		}
		
		return patientNotifications;
	}
	
	@SuppressWarnings("unchecked")
	public List<PatientNotification> getPendingPatientNotifications() {
		
		final Query query = this
		        .getSession()
		        .createQuery(
		            "SELECT pn FROM smartnotifier.api.model.PatientNotification pn WHERE pn.notificationStatus = :notificationStatus");
		query.setParameter("notificationStatus", NotificationStatus.PENDING);
		
		return query.list();
	}
}
