package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.AppointmentDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete appointment command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeleteAppointmentCommand extends Command {

    private static final long serialVersionUID = 6465729437830674429L;

    private static final Logger log = Logger.getLogger(DeleteAppointmentCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete Appointment Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String userIdString = request.getParameter("userId");
        log.trace("Request parameter: userId --> " + userIdString);

        int userId;

        try {
            userId = Integer.parseInt(userIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong user identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        String patientIdString = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientIdString);

        int patientId;

        try {
            patientId = Integer.parseInt(patientIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        try {
            new AppointmentDao().deleteAppointment(userId, patientId);
        }
        catch (Exception e) {
            errorMessage = "Failed to delete appointment";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        redirect = Path.COMMAND__LIST_APPOINTMENTS + patientId;

        log.debug("Commands finished");
        return redirect;
    }

}
