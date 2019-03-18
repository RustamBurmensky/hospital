package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.SpecializationDao;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedSpecializationBean;
import com.epam.burmensky.hospital.model.bean.LocalizedUserBean;
import com.epam.burmensky.hospital.model.entity.UserDetails;
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
 * Show add/edit user page command.
 *
 * @author Rustam Burmensky
 *
 */
@SuppressWarnings("Duplicates")
public class AddEditUserGetCommand extends Command {

    private static final long serialVersionUID = 9159614147985890120L;

    private static final Logger log = Logger.getLogger(AddEditUserGetCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String userId = request.getParameter("id");
        if (userId == null || userId.isEmpty()) {
            log.trace("Add User Command starts");

            LocalizedUserBean userBean = new LocalizedUserBean();
            List<UserDetails> userDetailsList = new ArrayList<>();
            for (Language lang : Language.values()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setLangId(lang.getLangId());
                userDetailsList.add(userDetails);
            }
            userBean.setUserDetails(userDetailsList);

            // put user bean to request
            request.setAttribute("userBean", userBean);
            log.trace("Set the request attribute: userBean --> " + userBean);
        }
        else {
            log.trace("Edit User Command starts");

            Integer id = Integer.parseInt(userId);
            LocalizedUserBean userBean = new UserDao().findUserById(id);

            // put user bean to request
            request.setAttribute("userBean", userBean);
            log.trace("Set the request attribute: userBean --> " + userBean);
        }

        Language language = (Language)session.getAttribute("userLang");
        if (language == null) {
            language = Language.languageFromLocale(request.getLocale());
        }

        List<DetailedSpecializationBean> specializationBeans =
                new SpecializationDao().findAllSpecializations(language);

        request.setAttribute("specializationBeans", specializationBeans);

        log.debug("Commands finished");
        return Path.PAGE__ADD_EDIT_USER;
    }
}
