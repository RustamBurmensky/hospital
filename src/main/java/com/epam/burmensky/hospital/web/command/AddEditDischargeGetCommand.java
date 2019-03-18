package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.DischargeDao;
import com.epam.burmensky.hospital.model.bean.LocalizedDischargeBean;
import com.epam.burmensky.hospital.model.entity.DischargeDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Show add/edit discharge command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditDischargeGetCommand extends Command {

    private static final long serialVersionUID = 5505286330342463881L;

    private static final Logger log = Logger.getLogger(AddEditDischargeGetCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        String dischargeIdString = request.getParameter("id");
        if (dischargeIdString == null || dischargeIdString.isEmpty()) {
            log.trace("Add Discharge Command starts");

            String patientIdString = request.getParameter("patientId");
            int patientId;
            try {
                patientId = Integer.parseInt(patientIdString);
                log.trace("Request parameter: patientId --> " + patientId);
            }
            catch (Exception e) {
                errorMessage = "Wrong patient identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return forward;
            }

            LocalizedDischargeBean dischargeBean = new LocalizedDischargeBean();
            List<DischargeDetails> dischargeDetailsList = new ArrayList<>();
            for (Language lang : Language.values()) {
                DischargeDetails dischargeDetails = new DischargeDetails();
                dischargeDetails.setLangId(lang.getLangId());
                dischargeDetailsList.add(dischargeDetails);
            }
            dischargeBean.setDischargeDetails(dischargeDetailsList);

            dischargeBean.setPatientId(patientId);
            dischargeBean.setDate(new Date());

            // put user bean to request
            request.setAttribute("dischargeBean", dischargeBean);
            log.trace("Set the request attribute: dischargeBean --> " + dischargeBean);
        }
        else {
            log.trace("Edit Discharge Command starts");

            Integer id = Integer.parseInt(dischargeIdString);
            LocalizedDischargeBean dischargeBean = new DischargeDao().findPatientDischarge(id);

            // put user bean to request
            request.setAttribute("dischargeBean", dischargeBean);
            log.trace("Set the request attribute: dischargeBean --> " + dischargeBean);
        }

        log.debug("Commands finished");
        return Path.PAGE__ADD_EDIT_DISCHARGE;
    }
}
