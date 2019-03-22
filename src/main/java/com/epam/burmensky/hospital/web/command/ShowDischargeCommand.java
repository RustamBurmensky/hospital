package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.DischargeDao;
import com.epam.burmensky.hospital.db.PatientDao;
import com.epam.burmensky.hospital.model.bean.DetailedDischargeBean;
import com.epam.burmensky.hospital.model.bean.DetailedPatientBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Show patient's discharge.
 *
 * @author Rustam Burmensky
 *
 */
public class ShowDischargeCommand extends Command {

    private static final long serialVersionUID = 1155022322230246606L;

    private static final Logger log = Logger.getLogger(ShowDischargeCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Show Discharge Command starts");

        HttpSession session = request.getSession();

        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        int id;
        String patientId = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientId);

        try {
            id = Integer.parseInt(patientId);
        }
        catch (Exception e) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(forward, request, response);
        }

        Language language = (Language)session.getAttribute("userLang");

        DetailedDischargeBean dischargeBean = new DischargeDao().findPatientDischarge(id, language);
        log.trace("Found in DB: dischargeBean --> " + dischargeBean);

        DetailedPatientBean patient = new PatientDao().findPatientById(id, language);
        log.trace("Found in DB: patient --> " + patient);

        // put user order beans list to request
        request.setAttribute("dischargeBean", dischargeBean);
        log.trace("Set the request attribute: dischargeBean --> " + dischargeBean);
        request.setAttribute("patient", patient);
        log.trace("Set the request attribute: patient --> " + patient);

        log.debug("Commands finished");
        return new ForwardCommandResult(Path.PAGE__SHOW_DISCHARGE, request, response);
    }

}
