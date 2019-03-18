package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.*;
import com.epam.burmensky.hospital.model.entity.Appointment;
import com.epam.burmensky.hospital.model.enumeration.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class AppointmentDao {

    private static final int ELEMENTS_ON_PAGE = 20;

    private static final String SQL__FIND_PATIENT_APPOINTMENTS =
            "SELECT appointments.user_id, users.role_id, users_description.first_name, users_description.second_name," +
                    " users_description.patronymic, users.birthday, specializations_description.name" +
                    " FROM (((appointments INNER JOIN patients ON appointments.patient_id = patients.id)" +
                    " INNER JOIN users ON appointments.user_id = users.id)" +
                    " INNER JOIN users_description ON appointments.user_id = users_description.user_id)" +
                    " INNER JOIN specializations_description ON users.specialization_id = specializations_description.specialization_id" +
                    " WHERE appointments.patient_id=? AND users_description.lang_id=?" +
                    " AND users_description.lang_id = specializations_description.lang_id" +
                    " ORDER BY users_description.second_name, users_description.first_name, users_description.patronymic" +
                    " LIMIT ? OFFSET ?";

    private static final String SQL__FIND_NOT_APPOINTED_PHYSICIANS =
            "SELECT users.id as user_id, users.role_id, users_description.first_name, users_description.second_name," +
                    " users_description.patronymic, users.birthday, specializations_description.name" +
                    " FROM ((users INNER JOIN users_description ON users.id = users_description.user_id)" +
                    " INNER JOIN specializations ON specializations.id = users.specialization_id)" +
                    " INNER JOIN specializations_description ON specializations.id = specializations_description.specialization_id" +
                    " WHERE users.id NOT IN" +
                    " (SELECT appointments.user_id FROM appointments WHERE patient_id=?)" +
                    " AND users_description.lang_id=?" +
                    " AND users_description.lang_id = specializations_description.lang_id" +
                    " ORDER BY users_description.second_name, users_description.first_name, users_description.patronymic";

    private static final String SQL__FIND_PHYSICIAN_APPOINTMENTS =
            "SELECT appointments.patient_id, patients_description.first_name, patients_description.second_name," +
                    " patients_description.patronymic, patients.birthday" +
                    " FROM (appointments INNER JOIN patients ON" +
                    " appointments.patient_id = patients.id)" +
                    " INNER JOIN patients_description ON patients_description.patient_id = patients.id" +
                    " WHERE appointments.user_id=? AND patients_description.lang_id=?" +
                    " ORDER BY patients_description.second_name, patients_description.first_name," +
                    " patients_description.patronymic LIMIT ? OFFSET ?";

    private static final String SQL__FIND_APPOINTMENT =
            "SELECT appointments.user_id, appointments.patient_id FROM appointments" +
                    " WHERE user_id=? AND patient_id=?";

    private static final String SQL__APPOINTED_USERS_COUNT =
            "SELECT COUNT(user_id) AS user_number FROM appointments WHERE patient_id=?";

    private static final String SQL__APPOINTED_PATIENTS_COUNT =
            "SELECT COUNT(patient_id) AS patient_number FROM appointments WHERE user_id=?";

    private static final String SQL_INSERT_APPOINTMENT =
            "INSERT INTO appointments (user_id, patient_id) VALUES (?,?)";

    private static final String SQL_DELETE_APPOINTMENT =
            "DELETE FROM appointments WHERE user_id=? AND patient_id=?";

    /**
     * Checks if patient-to-user appointment exists.
     *
     * @param userId
     *            user identifier.
     * @param patientId
     *            patient identifier.
     * @return boolean value.
     */
    public boolean appointmentExists(Integer userId, Integer patientId) {
        boolean result = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            UserAppointmentMapper mapper = new UserAppointmentMapper();
            pstmt = con.prepareStatement(SQL__FIND_APPOINTMENT);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, patientId);
            rs = pstmt.executeQuery();
            result = rs.next();
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return result;
    }


    /**
     * Returns a list of physicians appointed to the patient on the given page.
     *
     * @param page
     *            page number.
     * @param patientId
     *            patient identifier.
     * @param lang
     *            user language.
     * @return list of UserAppointments entities.
     */
    public List<UserAppointmentBean> findAppointedUsers(Integer page, Integer patientId, Language lang) {
        List<UserAppointmentBean> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            UserAppointmentMapper mapper = new UserAppointmentMapper();
            pstmt = con.prepareStatement(SQL__FIND_PATIENT_APPOINTMENTS);
            pstmt.setInt(1, patientId);
            pstmt.setByte(2, lang.getLangId());
            pstmt.setInt(3, ELEMENTS_ON_PAGE);
            pstmt.setInt(4, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(mapper.mapRow(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return users;
    }

    /**
     * Returns a list of physicians who are not appointed to the patient.
     *
     * @param patientId
     *            patient identifier.
     * @param lang
     *            user language.
     * @return list of UserAppointments entities.
     */
    public List<UserAppointmentBean> findNotAppointedUsers(Integer patientId, Language lang) {
        List<UserAppointmentBean> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            UserAppointmentMapper mapper = new UserAppointmentMapper();
            pstmt = con.prepareStatement(SQL__FIND_NOT_APPOINTED_PHYSICIANS);
            pstmt.setInt(1, patientId);
            pstmt.setByte(2, lang.getLangId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(mapper.mapRow(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return users;
    }

    /**
     * Returns a list of patients who are appointed to the user on the given page.
     *
     * @param page
     *            page number.
     * @param userId
     *            user identifier.
     * @param lang
     *            user language.
     * @return list of Discharge entities.
     */
    public List<PatientAppointmentBean> findAppointedPatients(Integer page, Integer userId, Language lang) {
        List<PatientAppointmentBean> discharges = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            PatientAppointmentMapper mapper = new PatientAppointmentMapper();
            pstmt = con.prepareStatement(SQL__FIND_PHYSICIAN_APPOINTMENTS);
            pstmt.setInt(1, userId);
            pstmt.setByte(2, lang.getLangId());
            pstmt.setInt(3, ELEMENTS_ON_PAGE);
            pstmt.setInt(4, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                discharges.add(mapper.mapRow(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return discharges;
    }

    /**
     * Returns the number of pages of appointed users.
     *
     * @param patientId
     *            patient identifier.
     * @return number of pages.
     */
    public long appointedUsersPageCount(Integer patientId) {
        Long usersCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__APPOINTED_USERS_COUNT);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                usersCount = rs.getLong("user_number");
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return (long) Math.ceil(usersCount.doubleValue() / ELEMENTS_ON_PAGE);
    }

    /**
     * Returns the number of pages of appointed patients.
     *
     * @param userId
     *            user identifier.
     * @return number of pages.
     */
    public long appointedPatientsPageCount(Integer userId) {
        Long patientsCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__APPOINTED_PATIENTS_COUNT);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patientsCount = rs.getLong("patients_number");
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return (long) Math.ceil(patientsCount.doubleValue() / ELEMENTS_ON_PAGE);
    }

    /**
     * Insert appointment.
     *
     * @param appointment
     *            appointment to insert.
     */
    public void insertAppointment(Appointment appointment) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertAppointment(con, appointment);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete appointment.
     *
     * @param userId
     *            user identifier.
     * @param patientId
     *            patient identifier.
     */
    public void deleteAppointment(Integer userId, Integer patientId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deleteAppointment(con, userId, patientId);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Insert discharge.
     *
     * @param appointment
     *            appointment to insert.
     * @throws SQLException
     */
    private void insertAppointment(Connection con, Appointment appointment) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_APPOINTMENT);
        pstmt.setInt(1, appointment.getUserId());
        pstmt.setInt(2, appointment.getPatientId());
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Delete appointment.
     *
     * @param userId
     *            user identifier.
     * @param patientId
     *            patient identifier.
     * @throws SQLException
     */
    private void deleteAppointment(Connection con, Integer userId, Integer patientId) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_APPOINTMENT);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, patientId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts an user appointment bean from the result set row.
     */
    private static class UserAppointmentMapper implements EntityMapper<UserAppointmentBean> {

        public UserAppointmentBean mapRow(ResultSet resultSet) {
            try {
                UserAppointmentBean bean = new UserAppointmentBean();
                bean.setId(resultSet.getInt(DBFields.USER_APPOINTMENT__USER_ID));
                bean.setRoleId(resultSet.getByte(DBFields.USER_APPOINTMENT__ROLE_ID));
                bean.setSpecializationName(resultSet.getString(DBFields.USER_APPOINTMENT__SPECIALIZATION_NAME));
                bean.setBirthday(resultSet.getDate(DBFields.USER_APPOINTMENT__BIRTHDAY));
                bean.setFirstName(resultSet.getString(DBFields.USER_APPOINTMENT__FIRST_NAME));
                bean.setSecondName(resultSet.getString(DBFields.USER_APPOINTMENT__SECOND_NAME));
                bean.setPatronymic(resultSet.getString(DBFields.USER_APPOINTMENT__PATRONYMIC));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts an patient appointment bean from the result set row.
     */
    private static class PatientAppointmentMapper implements EntityMapper<PatientAppointmentBean> {

        public PatientAppointmentBean mapRow(ResultSet resultSet) {
            try {
                PatientAppointmentBean bean = new PatientAppointmentBean();
                bean.setId(resultSet.getInt(DBFields.PATIENT_APPOINTMENT__PATIENT_ID));
                bean.setFirstName(resultSet.getString(DBFields.PATIENT_APPOINTMENT__FIRST_NAME));
                bean.setSecondName(resultSet.getString(DBFields.PATIENT_APPOINTMENT__SECOND_NAME));
                bean.setPatronymic(resultSet.getString(DBFields.PATIENT_APPOINTMENT__PATRONYMIC));
                bean.setBirthday(resultSet.getDate(DBFields.PATIENT_APPOINTMENT__BIRTHDAY));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
