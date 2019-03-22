package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.DischargeDao;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.model.bean.LocalizedDischargeBean;
import com.epam.burmensky.hospital.model.bean.LocalizedHealthCardRecordBean;
import com.epam.burmensky.hospital.model.entity.DischargeDetails;
import com.epam.burmensky.hospital.model.entity.HealthCardRecordDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.RecordType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * Add/edit discharge command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditDischargePostCommand extends Command {

    private static final long serialVersionUID = 4561241575839212449L;

    private static final Logger log = Logger.getLogger(AddEditDischargePostCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add/Edit Discharge Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String dischargeId = request.getParameter("id");

        int id = 0;

        if (dischargeId != null && !dischargeId.isEmpty()) {
            log.trace("Request parameter: id --> " + dischargeId);
            try {
                id = Integer.parseInt(dischargeId);
                log.trace("Discharge identifier found. Edit Discharge Command starts");
            }
            catch (Exception ex) {
                errorMessage = "Wrong discharge identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return new ForwardCommandResult(redirect, request, response);
            }
        }
        else {
            log.trace("Discharge identifier is not found. Add Discharge Command starts");
        }

        String patientIdString = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientIdString);

        int patientId;

        try {
            patientId = Integer.parseInt(patientIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        String dateString = request.getParameter("date");
        log.trace("Request parameter: date --> " + dateString);

        Date date;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = simpleDateFormat.parse(dateString);
        }
        catch (ParseException pe) {
            errorMessage = "Wrong date format (date)";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        Map<Language, String> localizedDiagnosis = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedDiagnosis.put(lang, request.getParameter("diagnosis_" + lang.getLangName()));
            log.trace("Request parameter: diagnosis_" + lang.getLangName() + " --> " +
                    localizedDiagnosis.get(lang));
        }

        LocalizedDischargeBean bean = new LocalizedDischargeBean();
        bean.setId(id);
        bean.setPatientId(patientId);
        bean.setDate(date);

        List<DischargeDetails> dischargeDetailsList = new ArrayList<>();
        for (Language lang : Language.values()) {
            DischargeDetails dischargeDetails = new DischargeDetails();
            dischargeDetails.setDischargeId(bean.getId());
            dischargeDetails.setLangId(lang.getLangId());
            dischargeDetails.setDiagnosis(localizedDiagnosis.get(lang));
            dischargeDetailsList.add(dischargeDetails);
        }

        bean.setDischargeDetails(dischargeDetailsList);

        if (id == 0) {
            new DischargeDao().insertDischarge(bean);
        }
        else {
            new DischargeDao().updateDischarge(bean);
        }
        redirect = Path.COMMAND__SHOW_DISCHARGE + bean.getPatientId();

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
