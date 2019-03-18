package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.Role;
import com.epam.burmensky.hospital.model.enumeration.UserSortingMode;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists users.
 *
 * @author Rustam Burmensky
 *
 */
public class ListUsersCommand extends Command {

    private static final long serialVersionUID = -4182648818368891707L;

    private static final Logger log = Logger.getLogger(ListUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("List Users Command starts");

        HttpSession session = request.getSession();

        String pageString = request.getParameter("page");
        String sortingModeString = request.getParameter("sortingMode");

        Integer page = (pageString != null && !pageString.isEmpty()) ? Integer.parseInt(pageString) : 0;
        Language language = (Language)session.getAttribute("userLang");

        UserSortingMode sortingMode = (sortingModeString != null && !sortingModeString.isEmpty()) ?
                UserSortingMode.getSortingMode(Byte.parseByte(sortingModeString)) : UserSortingMode.ALPHABETICAL;

        List<DetailedUserBean> userBeanList = new UserDao().findAllUsers(page, language, sortingMode);
        Long pageNumber = new UserDao().pageCount();
        log.trace("Found in DB: userBeanList --> " + userBeanList);

        // put user order beans list to request
        request.setAttribute("userBeanList", userBeanList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", page);
        request.setAttribute("sortingMode", sortingMode);
        log.trace("Set the request attribute: userBeanList --> " + userBeanList);

        log.debug("Commands finished");
        return Path.PAGE__LIST_USERS;
    }
}
