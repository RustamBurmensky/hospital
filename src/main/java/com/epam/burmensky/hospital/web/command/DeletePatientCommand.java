package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.PatientDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete patient command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeletePatientCommand extends Command {

    private static final long serialVersionUID = -8064203137967052710L;

    private static final Logger log = Logger.getLogger(DeletePatientCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete Patient Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String patientIdString = request.getParameter("id");
        log.trace("Request parameter: id --> " + patientIdString);

        int id;

        try {
            id = Integer.parseInt(patientIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        try {
            new PatientDao().deletePatient(id);
        }
        catch (Exception e) {
            errorMessage = "Failed to delete patient";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        redirect = Path.COMMAND__LIST_PATIENTS;

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
