package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.PatientDao;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedPatientBean;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.PatientSortingMode;
import com.epam.burmensky.hospital.model.enumeration.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists patients.
 *
 * @author Rustam Burmensky
 *
 */
public class ListPatientsCommand extends Command {

    private static final long serialVersionUID = 1784210072157329035L;

    private static final Logger log = Logger.getLogger(ListPatientsCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("List Patients Command starts");

        HttpSession session = request.getSession();

        String pageString = request.getParameter("page");
        String sortingModeString = request.getParameter("sortingMode");

        Integer page = (pageString != null && !pageString.isEmpty()) ? Integer.parseInt(pageString) : 0;
        Language language = (Language)session.getAttribute("userLang");

        PatientSortingMode sortingMode = (sortingModeString != null && !sortingModeString.isEmpty()) ?
                PatientSortingMode.getSortingMode(Byte.parseByte(sortingModeString)) : PatientSortingMode.ALPHABETICAL;

        DetailedUserBean userBean = (DetailedUserBean)session.getAttribute("user");
        List<DetailedPatientBean> patientBeanList;
        long pageNumber;

        Role userRole = (Role)session.getAttribute("userRole");
        if (userRole == Role.ADMIN) {
            patientBeanList = new PatientDao().findAllPatients(page, language, sortingMode);
            pageNumber = new PatientDao().pageCount();
        }
        else {
            patientBeanList = new PatientDao().findPatientsByUserId(userBean.getId(), page, language, sortingMode);
            pageNumber = new PatientDao().pageCount(userBean.getId());
        }

        log.trace("Found in DB: patientBeanList --> " + patientBeanList);

        // put user order beans list to request
        request.setAttribute("patientBeanList", patientBeanList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", page);
        request.setAttribute("sortingMode", sortingMode);
        log.trace("Set the request attribute: patientBeanList --> " + patientBeanList);

        log.debug("Commands finished");
        return new ForwardCommandResult(Path.PAGE__LIST_PATIENTS, request, response);
    }
}
