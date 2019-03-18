package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.bean.LocalizedHealthCardRecordBean;
import com.epam.burmensky.hospital.model.entity.HealthCardRecordDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.RecordType;
import com.epam.burmensky.hospital.model.enumeration.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * Show add/edit health card record page command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditCardRecordGetCommand extends Command {

    private static final long serialVersionUID = 741036643678740602L;

    private static final Logger log = Logger.getLogger(AddEditCardRecordGetCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        String recordId = request.getParameter("id");
        if (recordId == null || recordId.isEmpty()) {
            log.trace("Add Card Record Command starts");

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

            LocalizedHealthCardRecordBean recordBean = new LocalizedHealthCardRecordBean();
            List<HealthCardRecordDetails> recordDetailsList = new ArrayList<>();
            for (Language lang : Language.values()) {
                HealthCardRecordDetails recordDetails = new HealthCardRecordDetails();
                recordDetails.setLangId(lang.getLangId());
                recordDetailsList.add(recordDetails);
            }
            recordBean.setHealthCardRecordDetails(recordDetailsList);

            DetailedUserBean userBean = (DetailedUserBean)session.getAttribute("user");
            recordBean.setUserId(userBean.getId());
            recordBean.setPatientId(patientId);
            recordBean.setDate(new Date());

            // put user bean to request
            request.setAttribute("recordBean", recordBean);
            log.trace("Set the request attribute: recordBean --> " + recordBean);
        }
        else {
            log.trace("Edit Card Record Command starts");

            Integer id = Integer.parseInt(recordId);
            LocalizedHealthCardRecordBean recordBean = new HealthCardRecordDao().findRecordById(id);

            // put user bean to request
            request.setAttribute("recordBean", recordBean);
            log.trace("Set the request attribute: recordBean --> " + recordBean);
        }

        EnumSet<RecordType> recordTypes = EnumSet.allOf(RecordType.class);

        Role role = (Role)session.getAttribute("userRole");
        if (role == Role.NURSE) {
            recordTypes.remove(RecordType.DIAGNOSIS);
            recordTypes.remove(RecordType.SURGERY);
        }

        request.setAttribute("recordTypes", recordTypes);
        log.trace("Set the request attribute: recordTypes --> " + recordTypes);

        log.debug("Commands finished");
        return Path.PAGE__ADD_EDIT_CARD_RECORD;
    }
}
