package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.LocalizedUserBean;
import com.epam.burmensky.hospital.model.entity.UserDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Add/edit user command.
 *
 * @author Rustam Burmensky
 *
 */
@SuppressWarnings("Duplicates")
public class AddEditUserPostCommand extends Command {

    private static final long serialVersionUID = 2674971843085162813L;

    private static final Logger log = Logger.getLogger(AddEditUserPostCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Add/Edit User Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String userId = request.getParameter("id");

        int id = 0;

        if (userId != null && !userId.isEmpty()) {
            log.trace("Request parameter: id --> " + userId);
            try {
                id = Integer.parseInt(userId);
            }
            catch (Exception ex) {
                errorMessage = "Wrong user identifier";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return redirect;
            }
            log.trace("User identifier found. Edit User Command starts");
        }
        else {
            log.trace("User identifier is not found. Add User Command starts");
        }

        String roleIdString = request.getParameter("roleId");
        log.trace("Request parameter: roleId --> " + roleIdString);

        byte roleId;

        try {
            roleId = Byte.parseByte(roleIdString);
            Role userRole = Role.getRole(roleId);
        }
        catch (Exception ex) {
            errorMessage = "Wrong role identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        String specializationIdString = request.getParameter("specializationId");
        log.trace("Request parameter: specializationId --> " + specializationIdString);

        int specializationId;

        try {
            specializationId = Integer.parseInt(specializationIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong specialization identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        String langIdString = request.getParameter("langId");
        log.trace("Request parameter: langId --> " + langIdString);

        Language language;

        try {
            language = Language.getLanguage(Byte.parseByte(langIdString));
        }
        catch (Exception ex) {
            errorMessage = "Wrong language identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
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

        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
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

        LocalizedUserBean bean = new LocalizedUserBean();
        bean.setId(id);
        bean.setRoleId(roleId);
        bean.setSpecializationId(specializationId);
        bean.setLangId(language.getLangId());
        bean.setBirthday(birthday);
        bean.setLogin(login);
        bean.setPassword(password);

        List<UserDetails> userDetailsList = new ArrayList<>();
        for (Language lang : Language.values()) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(bean.getId());
            userDetails.setLangId(lang.getLangId());
            userDetails.setFirstName(localizedFirstName.get(lang));
            userDetails.setSecondName(localizedSecondName.get(lang));
            userDetails.setPatronymic(localizedPatronymic.get(lang));
            userDetailsList.add(userDetails);
        }

        bean.setUserDetails(userDetailsList);

        if (id == 0) {
            new UserDao().insertUser(bean);
        }
        else {
            new UserDao().updateUser(bean);
        }
        redirect = Path.COMMAND__LIST_USERS;

        log.debug("Commands finished");
        return redirect;
    }
}
