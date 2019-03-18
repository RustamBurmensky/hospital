package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.SpecializationDao;
import com.epam.burmensky.hospital.model.bean.DetailedSpecializationBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists specializations.
 *
 * @author Rustam Burmensky
 *
 */
public class ListSpecializationsCommand extends Command {

    private static final long serialVersionUID = 7783537376447956745L;

    private static final Logger log = Logger.getLogger(ListSpecializationsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("List Specializations Command starts");

        HttpSession session = request.getSession();

        String pageString = request.getParameter("page");

        Integer page = (pageString != null && !pageString.isEmpty()) ? Integer.parseInt(pageString) : 0;
        Language language = (Language)session.getAttribute("userLang");

        List<DetailedSpecializationBean> specializationBeanList =
                new SpecializationDao().findAllSpecializations(page, language);
        Long pageNumber = new SpecializationDao().pageCount();
        log.trace("Found in DB: specializationBeanList --> " + specializationBeanList);

        // put user order beans list to request
        request.setAttribute("specializationBeanList", specializationBeanList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: specializationBeanList --> " + specializationBeanList);

        log.debug("Commands finished");
        return Path.PAGE__LIST_SPECIALIZATIONS;
    }
}
