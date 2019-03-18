package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.DetailedPatientBean;
import com.epam.burmensky.hospital.model.bean.LocalizedPatientBean;
import com.epam.burmensky.hospital.model.entity.Patient;
import com.epam.burmensky.hospital.model.entity.PatientDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.PatientSortingMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PatientDao {

    private static final int ELEMENTS_ON_PAGE = 20;

    private static final String SQL__FIND_ALL =
            "SELECT patients.id, patients_description.first_name, patients_description.second_name," +
                    " patients_description.patronymic, patients.birthday, patients.weight, patients.height" +
                    " FROM patients INNER JOIN patients_description ON patients.id = patients_description.patient_id" +
                    " WHERE patients_description.lang_id=?";

    private static final String SQL__PAGINATION = " LIMIT ? OFFSET ?";

    private static final String SQL__FIND_PATIENT_BEAN_BY_ID =
            "SELECT patients.id, patients_description.first_name, patients_description.second_name," +
                    " patients_description.patronymic, patients.birthday, patients.weight, patients.height" +
                    " FROM patients INNER JOIN patients_description ON patients.id = patients_description.patient_id" +
                    " WHERE patients_description.lang_id=? AND patients.id=?";

    private static final String SQL__FIND_PATIENT_BEAN_BY_USER_ID =
            "SELECT patients.id, patients_description.first_name, patients_description.second_name," +
                    " patients_description.patronymic, patients.birthday, patients.weight, patients.height" +
                    " FROM patients INNER JOIN patients_description ON patients.id = patients_description.patient_id" +
                    " WHERE patient_id IN (SELECT patient_id FROM appointments WHERE user_id=?)" +
                    " AND patients_description.lang_id=?";

    private static final String SQL__FIND_PATIENT_BY_ID =
            "SELECT patients.id, patients.birthday, patients.height, patients.weight" +
                    " FROM patients" +
                    " WHERE patients.id=?";

    private static final String SQL__FIND_PATIENT_DETAILS_BY_PATIENT_ID =
            "SELECT patient_id, lang_id, first_name, second_name, patronymic" +
                    " FROM patients_description" +
                    " WHERE patient_id=?";

    private static final String SQL__PATIENT_COUNT =
            "SELECT COUNT(id) AS patients_count FROM patients";

    private static final String SQL__USER_PATIENT_COUNT =
            "SELECT COUNT(id) AS patients_count FROM patients" +
                    " WHERE id IN (SELECT patient_id FROM appointments WHERE user_id=?)";

    private static final String SQL_INSERT_PATIENT =
            "INSERT INTO patients (birthday, weight, height) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE_PATIENT =
            "UPDATE patients SET birthday=?, weight=?, height=? WHERE id=?";

    private static final String SQL_DELETE_PATIENT =
            "DELETE FROM patients WHERE id=?";

    private static final String SQL_INSERT_PATIENT_DETAILS =
            "INSERT INTO patients_description (patient_id, lang_id, first_name, second_name, patronymic) VALUES (?,?,?,?,?)";

    private static final String SQL_UPDATE_PATIENT_DETAILS =
            "UPDATE patients_description SET first_name=?, second_name=?, patronymic=? WHERE patient_id=? AND lang_id=?";

    private static final String SQL_DELETE_PATIENT_DETAILS =
            "DELETE FROM patients_description WHERE patient_id=?";

    /**
     * Returns a list of patients on the given page.
     *
     * @param page
     *            page number.
     * @param lang
     *            user language.
     * @param sortingMode
     *            patient sorting mode.
     * @return list of Patient entities.
     */
    public List<DetailedPatientBean> findAllPatients(Integer page, Language lang, PatientSortingMode sortingMode) {
        List<DetailedPatientBean> patients = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedPatientMapper mapper = new DetailedPatientMapper();
            pstmt = con.prepareStatement(SQL__FIND_ALL + sortingMode.getSQLOrderBy() + SQL__PAGINATION);
            pstmt.setByte(1, lang.getLangId());
            pstmt.setInt(2, ELEMENTS_ON_PAGE);
            pstmt.setInt(3, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                patients.add(mapper.mapRow(rs));
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
        return patients;
    }

    /**
     * Returns a list of patients who are appointed to specified user on the given page.
     *
     * @param userId
     *            user identifier.
     * @param page
     *            page number.
     * @param lang
     *            user language.
     * @param sortingMode
     *            patient sorting mode.
     * @return list of Patient entities.
     */
    public List<DetailedPatientBean> findPatientsByUserId(Integer userId,
                                                           Integer page, Language lang, PatientSortingMode sortingMode) {
        List<DetailedPatientBean> patients = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedPatientMapper mapper = new DetailedPatientMapper();
            pstmt = con.prepareStatement(SQL__FIND_PATIENT_BEAN_BY_USER_ID + sortingMode.getSQLOrderBy() + SQL__PAGINATION);
            pstmt.setInt(1, userId);
            pstmt.setByte(2, lang.getLangId());
            pstmt.setInt(3, ELEMENTS_ON_PAGE);
            pstmt.setInt(4, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                patients.add(mapper.mapRow(rs));
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
        return patients;
    }

    /**
     * Returns the number of pages.
     *
     * @return number of pages.
     */
    public long pageCount() {
        Long patientsCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__PATIENT_COUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patientsCount = rs.getLong("patients_count");
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
     * Returns the number of pages of patients who are appointed to specified user.
     *
     * @param userId
     *            user identifier.
     * @return number of pages.
     */
    public long pageCount(Integer userId) {
        Long patientsCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__USER_PATIENT_COUNT);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patientsCount = rs.getLong("patients_count");
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
     * Returns a patient with the given identifier.
     *
     * @param id
     *            Patient identifier.
     * @param lang
     *            user language.
     * @return Patient entity.
     */
    public DetailedPatientBean findPatientById(Integer id, Language lang) {
        DetailedPatientBean patient = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedPatientMapper mapper = new DetailedPatientMapper();
            pstmt = con.prepareStatement(SQL__FIND_PATIENT_BEAN_BY_ID);
            pstmt.setByte(1, lang.getLangId());
            pstmt.setLong(2, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                patient = mapper.mapRow(rs);
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
        return patient;
    }

    /**
     * Returns a localized patient bean with the given identifier.
     *
     * @param id
     *            Patient identifier.
     * @return LocalizedPatientBean object.
     */
    public LocalizedPatientBean findPatientById(Integer id) {
        LocalizedPatientBean localizedPatientBean = null;
        Patient patient = null;
        List<PatientDetails> patientDetails = new ArrayList<>();
        PreparedStatement pstmtPatient = null;
        PreparedStatement pstmtPatientDetails = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            PatientMapper mapper = new PatientMapper();
            pstmtPatient = con.prepareStatement(SQL__FIND_PATIENT_BY_ID);
            pstmtPatient.setInt(1, id);
            rs = pstmtPatient.executeQuery();
            if (rs.next())
                patient = mapper.mapRow(rs);
            rs.close();
            pstmtPatient.close();

            if (patient != null) {
                PatientDetailsMapper detailsMapper = new PatientDetailsMapper();
                pstmtPatientDetails = con.prepareStatement(SQL__FIND_PATIENT_DETAILS_BY_PATIENT_ID);
                pstmtPatientDetails.setInt(1, id);
                rs = pstmtPatientDetails.executeQuery();
                while (rs.next())
                    patientDetails.add(detailsMapper.mapRow(rs));
                rs.close();
                pstmtPatientDetails.close();
                localizedPatientBean = new LocalizedPatientBean(patient, patientDetails);
            }
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return localizedPatientBean;
    }

    /**
     * Insert patient.
     *
     * @param patient
     *            patient to insert.
     */
    public void insertPatient(LocalizedPatientBean patient) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertPatient(con, patient);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Update patient.
     *
     * @param patient
     *            patient to update.
     */
    public void updatePatient(LocalizedPatientBean patient) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            updatePatient(con, patient);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete patient.
     *
     * @param patientId
     *            identifier of patient to delete.
     */
    public void deletePatient(Integer patientId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deletePatient(con, patientId);
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
     * Insert patient.
     *
     * @param patient
     *            patient to insert.
     * @throws SQLException
     */
    private void insertPatient(Connection con, LocalizedPatientBean patient) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);
        int k = 1;
        pstmt.setDate(k++, new java.sql.Date(patient.getBirthday().getTime()));
        pstmt.setShort(k++, patient.getWeight());
        pstmt.setShort(k, patient.getHeight());
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                patient.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating patient failed, no ID obtained.");
            }
        }

        pstmt.close();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_PATIENT_DETAILS);
        for (PatientDetails details : patient.getPatientDetails()) {
            preparedStatement.setInt(1, patient.getId());
            preparedStatement.setByte(2, details.getLangId());
            preparedStatement.setString(3, details.getFirstName());
            preparedStatement.setString(4, details.getSecondName());
            preparedStatement.setString(5, details.getPatronymic());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    /**
     * Update patient.
     *
     * @param patient
     *            patient to update.
     * @throws SQLException
     */
    private void updatePatient(Connection con, LocalizedPatientBean patient) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_PATIENT);
        int k = 1;
        pstmt.setDate(k++, new java.sql.Date(patient.getBirthday().getTime()));
        pstmt.setShort(k++, patient.getWeight());
        pstmt.setShort(k++, patient.getHeight());
        pstmt.setInt(k, patient.getId());
        pstmt.executeUpdate();
        pstmt.close();
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_UPDATE_PATIENT_DETAILS);
        for (PatientDetails details : patient.getPatientDetails()) {
            pstmtDetails.setString(1, details.getFirstName());
            pstmtDetails.setString(2, details.getSecondName());
            pstmtDetails.setString(3, details.getPatronymic());
            pstmtDetails.setInt(4, details.getPatientId());
            pstmtDetails.setByte(5, details.getLangId());
            pstmtDetails.addBatch();
        }
        pstmtDetails.executeBatch();
        pstmtDetails.close();
    }

    /**
     * Delete user.
     *
     * @param patientId
     *            identifier of patient to delete.
     * @throws SQLException
     */
    private void deletePatient(Connection con, Integer patientId) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_DELETE_PATIENT_DETAILS);
        pstmtDetails.setInt(1, patientId);
        pstmtDetails.executeUpdate();
        pstmtDetails.close();

        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_PATIENT);
        pstmt.setInt(1, patientId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a detailed patient bean from the result set row.
     */
    private static class DetailedPatientMapper implements EntityMapper<DetailedPatientBean> {

        public DetailedPatientBean mapRow(ResultSet resultSet) {
            try {
                DetailedPatientBean bean = new DetailedPatientBean();
                bean.setId(resultSet.getInt(DBFields.ENTITY__ID));
                bean.setFirstName(resultSet.getString(DBFields.DETAILED_PATIENT_BEAN__FIRST_NAME));
                bean.setSecondName(resultSet.getString(DBFields.DETAILED_PATIENT_BEAN__SECOND_NAME));
                bean.setPatronymic(resultSet.getString(DBFields.DETAILED_PATIENT_BEAN__PATRONYMIC));
                bean.setBirthday(resultSet.getDate(DBFields.DETAILED_PATIENT_BEAN__BIRTHDAY));
                bean.setWeight(resultSet.getShort(DBFields.DETAILED_PATIENT_BEAN__WEIGHT));
                bean.setHeight(resultSet.getShort(DBFields.DETAILED_PATIENT_BEAN__HEIGHT));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a patient entity from the result set row.
     */
    private static class PatientMapper implements EntityMapper<Patient> {

        public Patient mapRow(ResultSet resultSet) {
            try {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt(DBFields.ENTITY__ID));
                patient.setBirthday(resultSet.getDate(DBFields.PATIENT__BIRTHDAY));
                patient.setWeight(resultSet.getShort(DBFields.PATIENT__WEIGHT));
                patient.setHeight(resultSet.getShort(DBFields.PATIENT__HEIGHT));
                return patient;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a patient details entity from the result set row.
     */
    private static class PatientDetailsMapper implements EntityMapper<PatientDetails> {

        public PatientDetails mapRow(ResultSet resultSet) {
            try {
                PatientDetails patientDetails = new PatientDetails();
                patientDetails.setPatientId(resultSet.getInt(DBFields.PATIENT_DETAILS__PATIENT_ID));
                patientDetails.setLangId(resultSet.getByte(DBFields.PATIENT_DETAILS__LANG_ID));
                patientDetails.setFirstName(resultSet.getString(DBFields.PATIENT_DETAILS__FIRST_NAME));
                patientDetails.setSecondName(resultSet.getString(DBFields.PATIENT_DETAILS__SECOND_NAME));
                patientDetails.setPatronymic(resultSet.getString(DBFields.PATIENT_DETAILS__PATRONYMIC));
                return patientDetails;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
