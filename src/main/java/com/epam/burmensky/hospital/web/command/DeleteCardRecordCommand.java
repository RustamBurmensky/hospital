package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete health card record command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeleteCardRecordCommand extends Command {

    private static final long serialVersionUID = -2135400917453964121L;

    private static final Logger log = Logger.getLogger(DeleteCardRecordCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete Card Record Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String recordIdString = request.getParameter("id");
        log.trace("Request parameter: id --> " + recordIdString);

        int id;

        try {
            id = Integer.parseInt(recordIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong card record identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        int patientId;

        try {
            patientId = new HealthCardRecordDao().findRecordById(id).getPatientId();
            new HealthCardRecordDao().deleteRecord(id);
        }
        catch (Exception e) {
            errorMessage = "Failed to delete card record";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        redirect = Path.COMMAND__LIST_CARD_RECORDS + patientId;

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
