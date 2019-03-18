package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.model.bean.LocalizedHealthCardRecordBean;
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
import java.util.*;

/**
 * Add/edit health card record command.
 *
 * @author Rustam Burmensky
 *
 */
public class AddEditCardRecordPostCommand extends Command {

    private static final long serialVersionUID = 8925231157767988247L;

    private static final Logger log = Logger.getLogger(AddEditCardRecordPostCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add/Edit Card Record Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String recordId = request.getParameter("id");

        int id = 0;

        if (recordId != null && !recordId.isEmpty()) {
            log.trace("Request parameter: id --> " + recordId);
            try {
                id = Integer.parseInt(recordId);
            }
            catch (Exception ex) {
                errorMessage = "Wrong card record identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return redirect;
            }
            log.trace("Card Record identifier found. Edit Card Record Command starts");
        }
        else {
            log.trace("Card Record identifier is not found. Add Card Record Command starts");
        }

        String userIdString = request.getParameter("userId");
        log.trace("Request parameter: userId --> " + userIdString);

        int userId;

        try {
            userId = Integer.parseInt(userIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong user identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
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
            return redirect;
        }

        String recordTypeIdString = request.getParameter("recordTypeId");
        log.trace("Request parameter: recordTypeId --> " + recordTypeIdString);

        byte recordTypeId;

        try {
            recordTypeId = Byte.parseByte(recordTypeIdString);
            RecordType recordType = RecordType.getRecordType(recordTypeId);
        }
        catch (Exception ex) {
            errorMessage = "Wrong record type identifier";
            request.setAttribute("errorMessage", errorMessage + ex);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
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
            return redirect;
        }

        Map<Language, String> localizedText = new HashMap<>();
        for (Language lang : Language.values()) {
            localizedText.put(lang, request.getParameter("text_" + lang.getLangName()));
            log.trace("Request parameter: text_" + lang.getLangName() + " --> " +
                    localizedText.get(lang));
        }

        LocalizedHealthCardRecordBean bean = new LocalizedHealthCardRecordBean();
        bean.setId(id);
        bean.setUserId(userId);
        bean.setPatientId(patientId);
        bean.setDate(date);
        bean.setRecordTypeId(recordTypeId);

        List<HealthCardRecordDetails> recordDetailsList = new ArrayList<>();
        for (Language lang : Language.values()) {
            HealthCardRecordDetails recordDetails = new HealthCardRecordDetails();
            recordDetails.setRecordId(bean.getId());
            recordDetails.setLangId(lang.getLangId());
            recordDetails.setText(localizedText.get(lang));
            recordDetailsList.add(recordDetails);
        }

        bean.setHealthCardRecordDetails(recordDetailsList);

        if (id == 0) {
            new HealthCardRecordDao().insertRecord(bean);
        }
        else {
            new HealthCardRecordDao().updateRecord(bean);
        }
        redirect = Path.COMMAND__LIST_CARD_RECORDS + bean.getPatientId();

        log.debug("Commands finished");
        return redirect;
    }
}
