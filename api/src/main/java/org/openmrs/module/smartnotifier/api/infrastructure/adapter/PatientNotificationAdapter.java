/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.smartnotifier.api.infrastructure.adapter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.openmrs.Patient;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.infrastructure.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("org.openmrs.module.smartnotifier.api.infrastructure.adapter.PatientNotificationAdapter")
public class PatientNotificationAdapter implements PatientNotificationPort {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public PatientNotification savePatientNotification(final PatientNotification patientNotification) {
		this.getSession().saveOrUpdate(patientNotification);
		return patientNotification;
	}
	
	@Override
	public List<PatientNotification> getPatientsToNotify(final String query, final Map<String, Object> params)
	        throws BusinessException {
		
		final SQLQuery sqlQuery = this.getSession().createSQLQuery(QueryUtil.loadQuery(query));
		
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
			notification.setPhoneNumber((String) row[3]);
			notification.setAppointmentDate((Timestamp) row[4]);
			
			patientNotifications.add(notification);
			
		}
		
		return patientNotifications;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PatientNotification> getPendingPatientNotifications() {
		
		final Query query = this
		        .getSession()
		        .createQuery(
		            "SELECT pn FROM smartnotifier.api.infrastructure.entity.PatientNotification pn WHERE pn.notificationStatus = :notificationStatus");
		query.setParameter("notificationStatus", NotificationStatus.PENDING);
		
		return query.list();
	}
}
