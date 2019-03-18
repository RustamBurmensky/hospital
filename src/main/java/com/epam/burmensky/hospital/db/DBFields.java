package com.epam.burmensky.hospital.db;

/**
 * Holder for fields' names of DB tables and beans.
 *
 * @author Rustam Burmensky
 *
 */
public final class DBFields {

    //Entity
    public static final String ENTITY__ID = "id";

    //User
    public static final String USER__ROLE_ID = "role_id";
    public static final String USER__SPECIALIZATION_ID = "specialization_id";
    public static final String USER__LANG_ID = "lang_id";
    public static final String USER__BIRTHDAY = "birthday";
    public static final String USER__LOGIN = "login";
    public static final String USER__PASSWORD = "password";
    //UserDetails
    public static final String USER_DTLS__USER_ID = "user_id";
    public static final String USER_DTLS__LANG_ID = "lang_id";
    public static final String USER_DTLS__FIRST_NAME = "first_name";
    public static final String USER_DTLS__SECOND_NAME = "second_name";
    public static final String USER_DTLS__PATRONYMIC = "patronymic";

    //Patient
    public static final String PATIENT__BIRTHDAY = "birthday";
    public static final String PATIENT__WEIGHT = "weight";
    public static final String PATIENT__HEIGHT = "height";
    //PatientDetails
    public static final String PATIENT_DETAILS__PATIENT_ID = "patient_id";
    public static final String PATIENT_DETAILS__LANG_ID = "lang_id";
    public static final String PATIENT_DETAILS__FIRST_NAME = "first_name";
    public static final String PATIENT_DETAILS__SECOND_NAME = "second_name";
    public static final String PATIENT_DETAILS__PATRONYMIC = "patronymic";

    //Appointment
    public static final String APPOINTMENT__USER_ID = "user_id";
    public static final String APPOINTMENT__PATIENT_ID = "patient_id";

    //HealthCardRecord
    public static final String HEALTH_CARD_RECORD__USER_ID = "user_id";
    public static final String HEALTH_CARD_RECORD__PATIENT_ID = "patient_id";
    public static final String HEALTH_CARD_RECORD__DATE = "date";
    public static final String HEALTH_CARD_RECORD__RECORD_TYPE_ID = "record_type_id";
    //HealthCardRecordDetails
    public static final String HEALTH_CARD_RECORD_DTLS__RECORD_ID = "record_id";
    public static final String HEALTH_CARD_RECORD_DTLS__LANG_ID = "lang_id";
    public static final String HEALTH_CARD_RECORD_DTLS__TEXT = "text";

    //Discharge
    public static final String DISCHARGE__PATIENT_ID = "patient_id";
    public static final String DISCHARGE__DATE = "date";
    //DischargeDetails
    public static final String DISCHARGE_DTLS__DISCHARGE_ID = "discharge_id";
    public static final String DISCHARGE_DTLS__LANG_ID = "lang_id";
    public static final String DISCHARGE_DTLS__DIAGNOSIS = "diagnosis";

    //SpecializationDetails
    public static final String SPECIALIZATION_DTLS__SPECIALIZATION_ID = "specialization_id";
    public static final String SPECIALIZATION_DTLS__LANG_ID = "lang_id";
    public static final String SPECIALIZATION_DTLS__NAME = "name";

    // ///////////////////////////////
    // Beans
    // ///////////////////////////////

    //DetailedUserBean
    public static final String DETAILED_USER_BEAN__FIRST_NAME = "first_name";
    public static final String DETAILED_USER_BEAN__SECOND_NAME = "second_name";
    public static final String DETAILED_USER_BEAN__PATRONYMIC = "patronymic";
    public static final String DETAILED_USER_BEAN__ROLE_ID = "role_id";
    public static final String DETAILED_USER_BEAN__SPECIALIZATION_NAME = "name";
    public static final String DETAILED_USER_BEAN__BIRTHDAY = "birthday";
    public static final String DETAILED_USER_BEAN__LOGIN = "login";
    public static final String DETAILED_USER_BEAN__PASSWORD = "password";
    public static final String DETAILED_USER_BEAN__PATIENTS_NUMBER = "patients_number";

    //DetailedPatientBean
    public static final String DETAILED_PATIENT_BEAN__FIRST_NAME = "first_name";
    public static final String DETAILED_PATIENT_BEAN__SECOND_NAME = "second_name";
    public static final String DETAILED_PATIENT_BEAN__PATRONYMIC = "patronymic";
    public static final String DETAILED_PATIENT_BEAN__BIRTHDAY = "birthday";
    public static final String DETAILED_PATIENT_BEAN__WEIGHT = "weight";
    public static final String DETAILED_PATIENT_BEAN__HEIGHT = "height";

    //DetailedSpecializationBean
    public static final String DETAILED_SPECIALIZATION_BEAN__NAME = "name";

    //DetailedHealthCardRecordBean
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__USER_ID = "user_id";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_FIRST_NAME = "first_name";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_SECOND_NAME = "second_name";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_PATRONYMIC = "patronymic";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__SPECIALIZATION_NAME = "name";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__PATIENT_ID = "patient_id";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__DATE = "date";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__RECORD_TYPE_ID = "record_type_id";
    public static final String DETAILED_HEALTH_CARD_RECORD_BEAN__TEXT = "text";

    //DetailedDischargeBean
    public static final String DETAILED_DISCHARGE__PATIENT_ID = "patient_id";
    public static final String DETAILED_DISCHARGE__PATIENT_FIRST_NAME = "first_name";
    public static final String DETAILED_DISCHARGE__PATIENT_SECOND_NAME = "second_name";
    public static final String DETAILED_DISCHARGE__PATIENT_PATRONYMIC = "patronymic";
    public static final String DETAILED_DISCHARGE__DATE = "date";
    public static final String DETAILED_DISCHARGE__DIAGNOSIS = "diagnosis";

    //UserAppointmentBean
    public static final String USER_APPOINTMENT__USER_ID = "user_id";
    public static final String USER_APPOINTMENT__ROLE_ID = "role_id";
    public static final String USER_APPOINTMENT__SPECIALIZATION_NAME = "name";
    public static final String USER_APPOINTMENT__BIRTHDAY = "birthday";
    public static final String USER_APPOINTMENT__FIRST_NAME = "first_name";
    public static final String USER_APPOINTMENT__SECOND_NAME = "second_name";
    public static final String USER_APPOINTMENT__PATRONYMIC = "patronymic";

    //PatientAppointmentBean
    public static final String PATIENT_APPOINTMENT__PATIENT_ID = "patient_id";
    public static final String PATIENT_APPOINTMENT__FIRST_NAME = "first_name";
    public static final String PATIENT_APPOINTMENT__SECOND_NAME = "second_name";
    public static final String PATIENT_APPOINTMENT__PATRONYMIC = "patronymic";
    public static final String PATIENT_APPOINTMENT__BIRTHDAY = "birthday";

}
