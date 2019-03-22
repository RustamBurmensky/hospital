package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.PatientDao;
import com.epam.burmensky.hospital.db.SpecializationDao;
import com.epam.burmensky.hospital.model.bean.LocalizedPatientBean;
import com.epam.burmensky.hospital.model.bean.LocalizedSpecializationBean;
import com.epam.burmensky.hospital.model.entity.PatientDetails;
import com.epam.burmensky.hospital.model.entity.SpecializationDetails;
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
 * Show add/edit specialization page command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditSpecializationGetCommand extends Command {

    private static final long serialVersionUID = 5267896724861649049L;

    private static final Logger log = Logger.getLogger(AddEditSpecializationGetCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String specializationId = request.getParameter("id");
        if (specializationId == null || specializationId.isEmpty()) {
            log.trace("Add Specialization Command starts");

            LocalizedSpecializationBean specializationBean = new LocalizedSpecializationBean();
            List<SpecializationDetails> specializationDetailsList = new ArrayList<>();
            for (Language lang : Language.values()) {
                SpecializationDetails specializationDetails = new SpecializationDetails();
                specializationDetails.setLangId(lang.getLangId());
                specializationDetailsList.add(specializationDetails);
            }
            specializationBean.setSpecializationDetails(specializationDetailsList);

            // put user bean to request
            request.setAttribute("specializationBean", specializationBean);
            log.trace("Set the request attribute: specializationBean --> " + specializationBean);
        }
        else {
            log.trace("Edit Specialiazation Command starts");

            Integer id = Integer.parseInt(specializationId);
            LocalizedSpecializationBean specializationBean = new SpecializationDao().findSpecializationById(id);

            // put user bean to request
            request.setAttribute("specializationBean", specializationBean);
            log.trace("Set the request attribute: specializationBean --> " + specializationBean);
        }

        log.debug("Commands finished");
        return new ForwardCommandResult(Path.PAGE__ADD_EDIT_SPECIALIZATION, request, response);
    }
}
