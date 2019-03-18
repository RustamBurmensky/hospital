package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.AppointmentDao;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.db.PatientDao;
import com.epam.burmensky.hospital.db.UserDao;
import com.epam.burmensky.hospital.model.bean.DetailedHealthCardRecordBean;
import com.epam.burmensky.hospital.model.bean.DetailedPatientBean;
import com.epam.burmensky.hospital.model.bean.UserAppointmentBean;
import com.epam.burmensky.hospital.model.enumeration.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists patient-to-user appointments.
 *
 * @author Rustam Burmensky
 *
 */
public class ListAppointmentsCommand extends Command {

    private static final long serialVersionUID = 6646756736350791144L;

    private static final Logger log = Logger.getLogger(ListAppointmentsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("List Appointments Command starts");

        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        HttpSession session = request.getSession();

        String pageString = request.getParameter("page");

        Integer page = (pageString != null && !pageString.isEmpty()) ? Integer.parseInt(pageString) : 0;
        Language language = (Language)session.getAttribute("userLang");

        int id;
        String patientId = request.getParameter("patientId");
        log.trace("Request parameter: patientId --> " + patientId);

        try {
            id = Integer.parseInt(patientId);
        }
        catch (Exception e) {
            errorMessage = "Wrong patient identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        List<UserAppointmentBean> appointmentBeanList =
                new AppointmentDao().findAppointedUsers(page, id, language);
        log.trace("Found in DB: appointmentBeanList --> " + appointmentBeanList);

        List<UserAppointmentBean> usersToAppoint =
                new AppointmentDao().findNotAppointedUsers(id, language);
        log.trace("Found in DB: usersToAppoint --> " + usersToAppoint);

        Long pageNumber = new AppointmentDao().appointedUsersPageCount(id);
        log.trace("Found in DB: appointmentBeanList --> " + appointmentBeanList);

        DetailedPatientBean patient = new PatientDao().findPatientById(id, language);
        log.trace("Found in DB: patient --> " + patient);

        // put user order beans list to request
        request.setAttribute("appointmentBeanList", appointmentBeanList);
        request.setAttribute("usersToAppoint", usersToAppoint);
        request.setAttribute("patient", patient);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: appointmentBeanList --> " + appointmentBeanList);

        log.debug("Commands finished");
        return Path.PAGE__LIST_APPOINTMENTS;
    }
}
