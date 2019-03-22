package com.epam.burmensky.hospital;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author Rustam Burmensky
 *
 */
public class Path {

    // pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE__LIST_USERS = "/WEB-INF/jsp/admin/list_users.jsp";
    public static final String PAGE__LIST_PATIENTS = "/WEB-INF/jsp/admin/list_patients.jsp";
    public static final String PAGE__LIST_SPECIALIZATIONS = "/WEB-INF/jsp/admin/list_specializations.jsp";
    public static final String PAGE__LIST_CARD_RECORDS = "/WEB-INF/jsp/physician/list_card_records.jsp";
    public static final String PAGE__LIST_APPOINTMENTS = "/WEB-INF/jsp/admin/list_appointments.jsp";
    public static final String PAGE__SHOW_DISCHARGE = "/WEB-INF/jsp/admin/show_discharge.jsp";

    public static final String PAGE__ADD_EDIT_USER = "/WEB-INF/jsp/admin/add_edit_user.jsp";
    public static final String PAGE__ADD_EDIT_PATIENT = "/WEB-INF/jsp/admin/add_edit_patient.jsp";
    public static final String PAGE__ADD_EDIT_SPECIALIZATION = "/WEB-INF/jsp/admin/add_edit_specialization.jsp";
    public static final String PAGE__ADD_EDIT_CARD_RECORD = "/WEB-INF/jsp/physician/add_edit_card_record.jsp";
    public static final String PAGE__ADD_EDIT_DISCHARGE = "/WEB-INF/jsp/admin/add_edit_discharge.jsp";
    public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";

    // commands
    public static final String COMMAND__LIST_USERS = "/controller?command=listUsers";
    public static final String COMMAND__LIST_PATIENTS = "/controller?command=listPatients";
    public static final String COMMAND__LIST_SPECIALIZATIONS = "/controller?command=listSpecializations";
    public static final String COMMAND__LIST_CARD_RECORDS = "/controller?command=listCardRecords&patientId=";
    public static final String COMMAND__LIST_APPOINTMENTS = "/controller?command=listAppointments&patientId=";
    public static final String COMMAND__SHOW_DISCHARGE = "/controller?command=showDischarge&patientId=";
    public static final String COMMAND__SHOW_SETTINGS = "/controller?command=showSettings";

    public static final String RESOURCE_BUNDLE = "resources";

}
