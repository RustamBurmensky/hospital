package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.bean.LocalizedUserBean;
import com.epam.burmensky.hospital.model.entity.UserDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Update user settings command.
 *
 * @author Rustam Burmensky
 *
 */
public class UpdateSettingsCommand extends Command {

    private static final long serialVersionUID = 8791671394300314518L;

    private static final Logger log = Logger.getLogger(UpdateSettingsCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Update Settings Command starts");

        HttpSession session = request.getSession();

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String roleIdString = request.getParameter("roleId");
        log.trace("Request parameter: roleId --> " + roleIdString);

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
            return new ForwardCommandResult(redirect, request, response);
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
            return new ForwardCommandResult(redirect, request, response);
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
            return new ForwardCommandResult(redirect, request, response);
        }

        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
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

        DetailedUserBean userBean = (DetailedUserBean)session.getAttribute("user");

        LocalizedUserBean bean = new LocalizedUserBean();
        bean.setId(userBean.getId());
        bean.setRoleId(userBean.getRoleId());
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

        new UserDao().updateUser(bean);

        DetailedUserBean updatedUser = new UserDao().findUserById(userBean.getId(), language);
        session.setAttribute("user", updatedUser);
        log.trace("Set the session attribute: user --> " + updatedUser);

        session.setAttribute("userLang", language);
        log.trace("Set the session attribute: userLang --> " + language);

        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", language.getLocale().getLanguage());
        log.info("Locale for user: defaultLocale --> " + language.getLocale());

        redirect = Path.COMMAND__SHOW_SETTINGS;

        log.debug("Commands finished");
        return new RedirectCommandResult(redirect, request, response);
    }

}
