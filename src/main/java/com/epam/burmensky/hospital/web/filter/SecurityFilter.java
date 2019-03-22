package com.epam.burmensky.hospital.web.filter;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.AppointmentDao;
import com.epam.burmensky.hospital.db.HealthCardRecordDao;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.enumeration.RecordType;
import com.epam.burmensky.hospital.model.enumeration.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter
 *
 * @author Rustam Burmensky
 *
 */
public class SecurityFilter implements Filter {

    private static final Logger log = Logger.getLogger(SecurityFilter.class);

    // commands access
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("Filter starts");

        if (accessAllowed(request)) {
            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            ResourceBundle resourceBundle = ResourceBundle.getBundle(Path.RESOURCE_BUNDLE);
            String errorMessage = resourceBundle.getString("error_page_jsp.http_403_forbidden");
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, errorMessage);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = (Role)session.getAttribute("userRole");
        if (userRole == null)
            return false;

        if (commandName.equals("addEditCardRecordPost") && userRole == Role.NURSE) {
            String recordTypeString = request.getParameter("recordTypeId");
            RecordType recordType = RecordType.getRecordType(Byte.parseByte(recordTypeString));
            if (recordType == RecordType.DIAGNOSIS || recordType == RecordType.SURGERY)
                return false;
        }

        if (userRole != Role.ADMIN) {
            if (commandName.equals("listCardRecords")) {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                int userId = ((DetailedUserBean) session.getAttribute("user")).getId();
                if (!new AppointmentDao().appointmentExists(userId, patientId))
                    return false;
            }

            if (commandName.equals("addEditCardRecordGet") || commandName.equals("addEditCardRecordPost") ||
                commandName.equals("deleteCardRecord")) {
                String recordIdString = request.getParameter("id");
                if (recordIdString != null && !recordIdString.isEmpty()) {
                    int recordId = Integer.parseInt(recordIdString);
                    int userId = ((DetailedUserBean) session.getAttribute("user")).getId();
                    if (new HealthCardRecordDao().findRecordById(recordId).getUserId() != userId) {
                        return false;
                    }
                }
            }
        }

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.PHYSICIAN, asList(fConfig.getInitParameter("physician")));
        accessMap.put(Role.NURSE, asList(fConfig.getInitParameter("nurse")));
        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        log.trace("Out of control commands --> " + outOfControl);

        log.debug("Filter initialization finished");
    }

    /**
     * Extracts parameter values from string.
     *
     * @param str
     *            parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

}
