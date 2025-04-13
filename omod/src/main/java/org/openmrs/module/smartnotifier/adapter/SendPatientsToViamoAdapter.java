/**
 *
 */
package org.openmrs.module.smartnotifier.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openmrs.module.smartnotifier.api.application.out.Message;
import org.openmrs.module.smartnotifier.api.application.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.dto.PatientNotificationDTO;

/**
 * @author Stélio Moiane
 */
public class SendPatientsToViamoAdapter implements SendPatientPort {
	
	@Override
	public Message send(final List<PatientNotification> patientNotifications) throws BusinessException {

		final Client client = ClientBuilder
				.newClient();

		final List<PatientNotificationDTO> patientNotificationDTOs = patientNotifications.stream()
				.map(notification -> new PatientNotificationDTO(notification.getPhoneNumber(), notification.getNotificationType().getValue(),
						notification.getSuggestedAppointmentDate().toLocalDateTime().toLocalDate().toString()))
				.collect(Collectors.toList());

		try {
			final Response response = client.target(VIAMO.URL).request(MediaType.APPLICATION_JSON)
					.header("api-key", VIAMO.API_KEY)
					.post(Entity.entity(patientNotificationDTOs, MediaType.APPLICATION_JSON));

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				return new Message(MessageStatus.OK, "Sucess...");
			}

		} catch (final ProcessingException e) {
			return new Message(MessageStatus.FAILED, "Failed...");
		} finally {
			client.close();
		}

		return new Message(MessageStatus.FAILED, "Failed...");
	}
}
