package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.entity.User;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Login command.
 *
 * @author Rustam Burmensky
 *
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -4611551468906747007L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Login Command starts");

        HttpSession session = request.getSession();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(Path.RESOURCE_BUNDLE,
                request.getLocale());

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String redirect = Path.PAGE__LOGIN;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = resourceBundle.getString("login_jsp.error.empty_login_or_password");
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        }

        User user = new UserDao().findUserByLogin(login);
        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = resourceBundle.getString("login_jsp.error.wrong_login_or_password");
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardCommandResult(redirect, request, response);
        } else {
            Role userRole = Role.getRole(user.getRoleId());
            log.trace("userRole --> " + userRole);

            if (userRole == Role.ADMIN)
                redirect = Path.COMMAND__LIST_USERS;

            if (userRole == Role.PHYSICIAN || userRole == Role.NURSE)
                redirect = Path.COMMAND__LIST_PATIENTS;

            Language userLang = Language.getLanguage(user.getLangId());
            DetailedUserBean userBean = new UserDao().findUserById(user.getId(), userLang);

            session.setAttribute("user", userBean);
            log.trace("Set the session attribute: user --> " + userBean);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);

            session.setAttribute("userLang", userLang);
            log.trace("Set the session attribute: userLang --> " + userLang);

            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLang.getLocale().getLanguage());
            log.info("Locale for user: defaultLocale --> " + userLang.getLocale());

            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        }

        log.debug("Command finished");
        return new RedirectCommandResult(redirect, request, response);
    }
}
