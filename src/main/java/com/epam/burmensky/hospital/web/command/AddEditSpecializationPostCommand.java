package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.SpecializationDao;
import com.epam.burmensky.hospital.model.bean.LocalizedSpecializationBean;
import com.epam.burmensky.hospital.model.entity.SpecializationDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Add/edit specialization command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditSpecializationPostCommand extends Command {

    private static final long serialVersionUID = -7816631158928905321L;

    private static final Logger log = Logger.getLogger(AddEditSpecializationPostCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add/Edit Specialization Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String specializationId = request.getParameter("id");

        int id = 0;

        if (specializationId != null && !specializationId.isEmpty()) {
            log.trace("Request parameter: id --> " + specializationId);
            try {
                id = Integer.parseInt(specializationId);
            }
            catch (Exception ex) {
                errorMessage = "Wrong specialization identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return redirect;
            }
            log.trace("Patient identifier found. Edit Specialization Command starts");
        }
        else {
            log.trace("Patient identifier is not found. Add Specialization Command starts");
        }

        Map<Language, String> localizedName = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedName.put(lang, request.getParameter("name_" + lang.getLangName()));
            log.trace("Request parameter: name_" + lang.getLangName() + " --> " +
                    localizedName.get(lang));
        }

        LocalizedSpecializationBean bean = new LocalizedSpecializationBean();
        bean.setId(id);

        List<SpecializationDetails> specializationDetailsList = new ArrayList<>();
        for (Language lang : Language.values()) {
            SpecializationDetails specializationDetails = new SpecializationDetails();
            specializationDetails.setSpecializationId(bean.getId());
            specializationDetails.setLangId(lang.getLangId());
            specializationDetails.setName(localizedName.get(lang));
            specializationDetailsList.add(specializationDetails);
        }

        bean.setSpecializationDetails(specializationDetailsList);

        if (id == 0) {
            new SpecializationDao().insertSpecialization(bean);
        }
        else {
            new SpecializationDao().updateSpecialization(bean);
        }
        redirect = Path.COMMAND__LIST_SPECIALIZATIONS;

        log.debug("Commands finished");
        return redirect;
    }
}
