package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.DischargeDao;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.model.bean.LocalizedDischargeBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete discharge command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeleteDischargeCommand extends Command {

    private static final long serialVersionUID = -6493679251860260234L;

    private static final Logger log = Logger.getLogger(DeleteDischargeCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete Discharge Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String patientIdString = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientIdString);

        LocalizedDischargeBean dischargeBean = null;
        int patientId;

        try {
            patientId = Integer.parseInt(patientIdString);
            dischargeBean = new DischargeDao().findPatientDischarge(patientId);
        }
        catch (Exception ex) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        try {
            new DischargeDao().deleteDischarge(dischargeBean.getId());
        }
        catch (Exception e) {
            errorMessage = "Failed to delete discharge";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        redirect = Path.COMMAND__SHOW_DISCHARGE + patientId;

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
