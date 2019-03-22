package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.AppointmentDao;
import com.epam.burmensky.hospital.model.entity.Appointment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add patient-to-user appointment command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddAppointmentPostCommand extends Command {

    private static final long serialVersionUID = -4549120390228546271L;

    private static final Logger log = Logger.getLogger(AddAppointmentPostCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add Appointment Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String userIdString = request.getParameter("userId");
        log.trace("Request parameter: userId --> " + userIdString);

        int userId = 0;

        try {
            userId = Integer.parseInt(userIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong user identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        String patientIdString = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientIdString);

        int patientId;

        try {
            patientId = Byte.parseByte(patientIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setPatientId(patientId);

        new AppointmentDao().insertAppointment(appointment);

        redirect = Path.COMMAND__LIST_APPOINTMENTS + patientId;

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
