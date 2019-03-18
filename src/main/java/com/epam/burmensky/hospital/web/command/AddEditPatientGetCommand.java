package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.PatientDao;
import com.epam.burmensky.hospital.model.bean.LocalizedPatientBean;
import com.epam.burmensky.hospital.model.entity.PatientDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Show add/edit patient page command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditPatientGetCommand extends Command {

    private static final long serialVersionUID = 4563534709006002676L;

    private static final Logger log = Logger.getLogger(AddEditPatientGetCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String patientId = request.getParameter("id");
        if (patientId == null || patientId.isEmpty()) {
            log.trace("Add Patient Command starts");

            LocalizedPatientBean patientBean = new LocalizedPatientBean();
            List<PatientDetails> patientDetailsList = new ArrayList<>();
            for (Language lang : Language.values()) {
                PatientDetails patientDetails = new PatientDetails();
                patientDetails.setLangId(lang.getLangId());
                patientDetailsList.add(patientDetails);
            }
            patientBean.setPatientDetails(patientDetailsList);

            // put user bean to request
            request.setAttribute("patientBean", patientBean);
            log.trace("Set the request attribute: patientBean --> " + patientBean);
        }
        else {
            log.trace("Edit Patient Command starts");

            Integer id = Integer.parseInt(patientId);
            LocalizedPatientBean patientBean = new PatientDao().findPatientById(id);

            // put user bean to request
            request.setAttribute("patientBean", patientBean);
            log.trace("Set the request attribute: userBean --> " + patientBean);
        }

        log.debug("Commands finished");
        return Path.PAGE__ADD_EDIT_PATIENT;
    }
}
