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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Add/edit patient command.
 *
 * @author Rustam Burmensky
 *
 */
@SuppressWarnings("Duplicates")
public class AddEditPatientPostCommand extends Command {

    private static final long serialVersionUID = -1179008915944044635L;

    private static final Logger log = Logger.getLogger(AddEditPatientPostCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add/Edit User Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String patientId = request.getParameter("id");

        int id = 0;

        if (patientId != null && !patientId.isEmpty()) {
            log.trace("Request parameter: id --> " + patientId);
            try {
                id = Integer.parseInt(patientId);
            }
            catch (Exception ex) {
                errorMessage = "Wrong patient identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return redirect;
            }
            log.trace("Patient identifier found. Edit Patient Command starts");
        }
        else {
            log.trace("Patient identifier is not found. Add Patient Command starts");
        }

        String birthdayString = request.getParameter("birthday");
        log.trace("Request parameter: birthday --> " + birthdayString);

        Date birthday;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            birthday = simpleDateFormat.parse(birthdayString);
        }
        catch (ParseException pe) {
            errorMessage = "Wrong date format (birthday)";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        short weight = 0;
        String weightString = request.getParameter("weight");
        log.trace("Request parameter: weight --> " + weightString);

        try {
            weight = Short.parseShort(weightString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong weight value";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        short height = 0;
        String heightString = request.getParameter("height");
        log.trace("Request parameter: height --> " + heightString);

        try {
            height = Short.parseShort(heightString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong height value";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        Map<Language, String> localizedFirstName = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedFirstName.put(lang, request.getParameter("firstName_" + lang.getLangName()));
            log.trace("Request parameter: firstName_" + lang.getLangName() + " --> " +
                    localizedFirstName.get(lang));
        }

        Map<Language, String> localizedSecondName = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedSecondName.put(lang, request.getParameter("secondName_" + lang.getLangName()));
            log.trace("Request parameter: secondName_" + lang.getLangName() + " --> " +
                    localizedSecondName.get(lang));
        }

        Map<Language, String> localizedPatronymic = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedPatronymic.put(lang, request.getParameter("patronymic_" + lang.getLangName()));
            log.trace("Request parameter: patronymic_" + lang.getLangName() + " --> "
                    + localizedPatronymic.get(lang));
        }

        LocalizedPatientBean bean = new LocalizedPatientBean();
        bean.setId(id);
        bean.setBirthday(birthday);
        bean.setWeight(weight);
        bean.setHeight(height);

        List<PatientDetails> patientDetailsList = new ArrayList<>();
        for (Language lang : Language.values()) {
            PatientDetails userDetails = new PatientDetails();
            userDetails.setPatientId(bean.getId());
            userDetails.setLangId(lang.getLangId());
            userDetails.setFirstName(localizedFirstName.get(lang));
            userDetails.setSecondName(localizedSecondName.get(lang));
            userDetails.setPatronymic(localizedPatronymic.get(lang));
            patientDetailsList.add(userDetails);
        }

        bean.setPatientDetails(patientDetailsList);

        if (id == 0) {
            new PatientDao().insertPatient(bean);
        }
        else {
            new PatientDao().updatePatient(bean);
        }
        redirect = Path.COMMAND__LIST_PATIENTS;

        log.debug("Commands finished");
        return redirect;
    }
}
