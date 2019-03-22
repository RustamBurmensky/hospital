package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.SpecializationDao;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedSpecializationBean;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.bean.LocalizedUserBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Show user settings.
 *
 * @author Rustam Burmensky
 *
 */
public class ShowSettingsCommand extends Command {

    private static final long serialVersionUID = 4628901242363284930L;

    private static final Logger log = Logger.getLogger(ShowSettingsCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        log.trace("Show Settings Command starts");

        DetailedUserBean userBean = (DetailedUserBean)session.getAttribute("user");
        LocalizedUserBean localizedUserBean = new UserDao().findUserById(userBean.getId());
        request.setAttribute("userBean", localizedUserBean);

        Language language = (Language)session.getAttribute("userLang");

        List<DetailedSpecializationBean> specializationBeans =
                new SpecializationDao().findAllSpecializations(language);

        request.setAttribute("specializationBeans", specializationBeans);

        log.debug("Commands finished");
        return new ForwardCommandResult(Path.PAGE__SETTINGS, request, response);
    }

}
