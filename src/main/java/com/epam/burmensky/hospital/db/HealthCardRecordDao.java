package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.DetailedHealthCardRecordBean;
import com.epam.burmensky.hospital.model.bean.LocalizedHealthCardRecordBean;
import com.epam.burmensky.hospital.model.bean.LocalizedPatientBean;
import com.epam.burmensky.hospital.model.entity.HealthCardRecord;
import com.epam.burmensky.hospital.model.entity.HealthCardRecordDetails;
import com.epam.burmensky.hospital.model.entity.Patient;
import com.epam.burmensky.hospital.model.entity.PatientDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class HealthCardRecordDao {

    private static final int ELEMENTS_ON_PAGE = 20;

    private static final String SQL__FIND_PATIENT_RECORD_BEANS =
            "SELECT health_card_records.id, health_card_records.user_id, users_description.first_name," +
                    " users_description.second_name, users_description.patronymic, specializations_description.name," +
                    " health_card_records.patient_id, health_card_records.date," +
                    " health_card_records.record_type_id, health_card_records_description.text" +
                    " FROM ((((health_card_records INNER JOIN health_card_records_description ON" +
                    " health_card_records_description.record_id = health_card_records.id)" +
                    " INNER JOIN users ON health_card_records.user_id = users.id)" +
                    " INNER JOIN users_description ON users_description.user_id = users.id)" +
                    " INNER JOIN specializations ON users.specialization_id = specializations.id)" +
                    " INNER JOIN specializations_description ON" +
                    " specializations.id = specializations_description.specialization_id" +
                    " WHERE health_card_records.patient_id=? AND health_card_records_description.lang_id=?" +
                    " AND specializations_description.lang_id = health_card_records_description.lang_id" +
                    " AND users_description.lang_id = health_card_records_description.lang_id" +
                    " ORDER BY health_card_records.date DESC LIMIT ? OFFSET ?";

    private static final String SQL__FIND_PATIENT_RECORD_BEANS_BY_TYPE =
            "SELECT health_card_records.id, health_card_records.user_id, users_description.first_name," +
                    " users_description.second_name, users_description.patronymic, specializations_description.name," +
                    " health_card_records.patient_id, health_card_records.date," +
                    " health_card_records.record_type_id, health_card_records_description.text" +
                    " FROM ((((health_card_records INNER JOIN health_card_records_description ON" +
                    " health_card_records_description.record_id = health_card_records.id)" +
                    " INNER JOIN users ON health_card_records.user_id = users.id)" +
                    " INNER JOIN users_description ON users_description.user_id = users.id)" +
                    " INNER JOIN specializations ON users.specialization_id = specializations.id)" +
                    " INNER JOIN specializations_description ON" +
                    " specializations.id = specializations_description.specialization_id" +
                    " WHERE health_card_records.patient_id=? AND health_card_records.record_type_id=?" +
                    " AND health_card_records_description.lang_id=?" +
                    " AND specializations_description.lang_id = health_card_records_description.lang_id" +
                    " AND users_description.lang_id = health_card_records_description.lang_id" +
                    " ORDER BY health_card_records.date DESC LIMIT ? OFFSET ?";

    private static final String SQL__FIND_RECORD_BEAN_BY_ID =
            "SELECT health_card_records.id, health_card_records.user_id, users_description.first_name," +
                    " users_description.second_name, users_description.patronymic, specializations_description.name," +
                    " health_card_records.patient_id, health_card_records.date," +
                    " health_card_records.record_type_id, health_card_records_description.text" +
                    " FROM ((((health_card_records INNER JOIN health_card_records_description ON" +
                    " health_card_records_description.record_id = health_card_records.id)" +
                    " INNER JOIN users ON health_card_records.user_id = users.id)" +
                    " INNER JOIN users_description ON users_description.user_id = users.id)" +
                    " INNER JOIN specializations ON users.specialization_id = specializations.id)" +
                    " INNER JOIN specializations_description ON" +
                    " specializations.id = specializations_description.specialization_id" +
                    " WHERE health_card_records.id=? AND health_card_records_description.lang_id=?" +
                    " AND specializations_description.lang_id = health_card_records_description.lang_id" +
                    " AND users_description.lang_id = health_card_records_description.lang_id";

    private static final String SQL__FIND_RECORD_BY_ID =
            "SELECT health_card_records.id, health_card_records.user_id," +
                    " health_card_records.patient_id, health_card_records.date," +
                    " health_card_records.record_type_id" +
                    " FROM health_card_records " +
                    " WHERE health_card_records.id=?";

    private static final String SQL__FIND_RECORD_DETAILS_BY_RECORD_ID =
            "SELECT health_card_records_description.record_id, health_card_records_description.lang_id," +
                    " health_card_records_description.text" +
                    " FROM health_card_records_description" +
                    " WHERE health_card_records_description.record_id=?";

    private static final String SQL__PATIENT_HEALTH_CARD_RECORD_COUNT =
            "SELECT COUNT(id) AS records_count FROM health_card_records" +
                    " WHERE health_card_records.patient_id=?";

    private static final String SQL_INSERT_RECORD =
            "INSERT INTO health_card_records (user_id, patient_id, date, record_type_id)" +
                    " VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE_RECORD =
            "UPDATE health_card_records SET date=?, record_type_id=? WHERE id=?";

    private static final String SQL_DELETE_RECORD =
            "DELETE FROM health_card_records WHERE id=?";

    private static final String SQL_INSERT_RECORD_DETAILS =
            "INSERT INTO health_card_records_description (record_id, lang_id, text) VALUES (?,?,?)";

    private static final String SQL_UPDATE_RECORD_DETAILS =
            "UPDATE health_card_records_description SET text=? WHERE record_id=? AND lang_id=?";

    private static final String SQL_DELETE_RECORD_DETAILS =
            "DELETE FROM health_card_records_description WHERE record_id=?";

    /**
     * Returns a list of patient's health card records on the given page.
     *
     * @param page
     *            page number.
     * @param patientId
     *            patient identifier.
     * @param lang
     *            user language.
     * @return list of HealthCardRecord entities.
     */
    public List<DetailedHealthCardRecordBean> findPatientRecords(Integer page, Integer patientId, Language lang) {
        List<DetailedHealthCardRecordBean> records = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedHealthCardRecordMapper mapper = new DetailedHealthCardRecordMapper();
            pstmt = con.prepareStatement(SQL__FIND_PATIENT_RECORD_BEANS);
            pstmt.setInt(1, patientId);
            pstmt.setByte(2, lang.getLangId());
            pstmt.setInt(3, ELEMENTS_ON_PAGE);
            pstmt.setInt(4, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next())
                records.add(mapper.mapRow(rs));
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
        return records;
    }

    /**
     * Returns a list of patient's health card records with the given type on the given page.
     * @param page
     *            page number.
     * @param patientId
     *            patient identifier.
     * @param recordTypeId
     *            record type identifier.
     * @param lang
     *            user language.
     * @return User entity.
     */
    public List<DetailedHealthCardRecordBean> findPatientRecordsByType(Integer page, Integer patientId,
                                                                       Byte recordTypeId, Language lang) {
        List<DetailedHealthCardRecordBean> records = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedHealthCardRecordMapper mapper = new DetailedHealthCardRecordMapper();
            pstmt = con.prepareStatement(SQL__FIND_PATIENT_RECORD_BEANS_BY_TYPE);
            pstmt.setInt(1, patientId);
            pstmt.setByte(2, recordTypeId);
            pstmt.setByte(3, lang.getLangId());
            pstmt.setInt(4, ELEMENTS_ON_PAGE);
            pstmt.setInt(5, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next())
                records.add(mapper.mapRow(rs));
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return records;
    }

    /**
     * Returns the number of pages.
     *
     * @param patientId
     *            patient identifier.
     * @return number of pages.
     */
    public long pageCount(Integer patientId) {
        Long usersCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__PATIENT_HEALTH_CARD_RECORD_COUNT);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                usersCount = rs.getLong("records_count");
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
     * Returns a health card record with the given identifier.
     *
     * @param id
     *            health card record identifier.
     * @param lang
     *            user language.
     * @return HealthCardRecord entity.
     */
    public DetailedHealthCardRecordBean findRecordById(Integer id, Language lang) {
        DetailedHealthCardRecordBean record = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedHealthCardRecordMapper mapper = new DetailedHealthCardRecordMapper();
            pstmt = con.prepareStatement(SQL__FIND_RECORD_BEAN_BY_ID);
            pstmt.setLong(1, id);
            pstmt.setByte(2, lang.getLangId());
            rs = pstmt.executeQuery();
            if (rs.next())
                record = mapper.mapRow(rs);
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
        return record;
    }

    /**
     * Returns a localized health card record bean with the given identifier.
     *
     * @param id
     *            Patient identifier.
     * @return LocalizedPatientBean object.
     */
    public LocalizedHealthCardRecordBean findRecordById(Integer id) {
        LocalizedHealthCardRecordBean localizedRecordBean = null;
        HealthCardRecord record = null;
        List<HealthCardRecordDetails> recordDetails = new ArrayList<>();
        PreparedStatement pstmtRecord = null;
        PreparedStatement pstmtRecordDetails = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            HealthCardRecordMapper mapper = new HealthCardRecordMapper();
            pstmtRecord = con.prepareStatement(SQL__FIND_RECORD_BY_ID);
            pstmtRecord.setInt(1, id);
            rs = pstmtRecord.executeQuery();
            if (rs.next())
                record = mapper.mapRow(rs);
            rs.close();
            pstmtRecord.close();

            HealthCardRecordDetailsMapper detailsMapper = new HealthCardRecordDetailsMapper();
            pstmtRecordDetails = con.prepareStatement(SQL__FIND_RECORD_DETAILS_BY_RECORD_ID);
            pstmtRecordDetails.setInt(1, id);
            rs = pstmtRecordDetails.executeQuery();
            while (rs.next())
                recordDetails.add(detailsMapper.mapRow(rs));
            rs.close();
            pstmtRecordDetails.close();

            localizedRecordBean = new LocalizedHealthCardRecordBean(record, recordDetails);
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return localizedRecordBean;
    }

    /**
     * Insert health card record.
     *
     * @param record
     *            record to insert.
     */
    public void insertRecord(LocalizedHealthCardRecordBean record) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertRecord(con, record);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Update health card record.
     *
     * @param record
     *            record to update.
     */
    public void updateRecord(LocalizedHealthCardRecordBean record) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            updateRecord(con, record);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete health card record.
     *
     * @param recordId
     *            identifier of record to delete.
     */
    public void deleteRecord(Integer recordId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deleteRecord(con, recordId);
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
     * Insert health card record.
     *
     * @param record
     *            record to insert.
     * @throws SQLException
     */
    private void insertRecord(Connection con, LocalizedHealthCardRecordBean record) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_RECORD, Statement.RETURN_GENERATED_KEYS);
        int k = 1;
        pstmt.setInt(k++, record.getUserId());
        pstmt.setInt(k++, record.getPatientId());
        pstmt.setDate(k++, new java.sql.Date(record.getDate().getTime()));
        pstmt.setByte(k, record.getRecordTypeId());
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                record.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating health card record failed, no ID obtained.");
            }
        }

        pstmt.close();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_RECORD_DETAILS);
        for (HealthCardRecordDetails details : record.getHealthCardRecordDetails()) {
            preparedStatement.setInt(1, record.getId());
            preparedStatement.setByte(2, details.getLangId());
            preparedStatement.setString(3, details.getText());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    /**
     * Update health card record.
     *
     * @param record
     *            record to update.
     * @throws SQLException
     */
    private void updateRecord(Connection con, LocalizedHealthCardRecordBean record) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_RECORD);
        int k = 1;
        pstmt.setDate(k++, new java.sql.Date(record.getDate().getTime()));
        pstmt.setByte(k++, record.getRecordTypeId());
        pstmt.setInt(k, record.getId());
        pstmt.executeUpdate();
        pstmt.close();
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_UPDATE_RECORD_DETAILS);
        for (HealthCardRecordDetails details : record.getHealthCardRecordDetails()) {
            pstmtDetails.setString(1, details.getText());
            pstmtDetails.setInt(2, details.getRecordId());
            pstmtDetails.setByte(3, details.getLangId());
            pstmtDetails.addBatch();
        }
        pstmtDetails.executeBatch();
        pstmtDetails.close();
    }

    /**
     * Delete health card record.
     *
     * @param recordId
     *            identifier of record to delete.
     * @throws SQLException
     */
    private void deleteRecord(Connection con, Integer recordId) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_DELETE_RECORD_DETAILS);
        pstmtDetails.setInt(1, recordId);
        pstmtDetails.executeUpdate();
        pstmtDetails.close();

        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_RECORD);
        pstmt.setInt(1, recordId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a detailed health card record bean from the result set row.
     */
    private static class DetailedHealthCardRecordMapper implements EntityMapper<DetailedHealthCardRecordBean> {

        public DetailedHealthCardRecordBean mapRow(ResultSet resultSet) {
            try {
                DetailedHealthCardRecordBean bean = new DetailedHealthCardRecordBean();
                bean.setId(resultSet.getInt(DBFields.ENTITY__ID));
                bean.setUserId(resultSet.getInt(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__USER_ID));
                bean.setPhysicianFirstName(resultSet.getString(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_FIRST_NAME));
                bean.setPhysicianSecondName(resultSet.getString(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_SECOND_NAME));
                bean.setPhysicianPatronymic(resultSet.getString(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__PHYSICIAN_PATRONYMIC));
                bean.setSpecializationName(resultSet.getString(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__SPECIALIZATION_NAME));
                bean.setPatientId(resultSet.getInt(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__PATIENT_ID));
                bean.setDate(resultSet.getDate(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__DATE));
                bean.setRecordTypeId(resultSet.getByte(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__RECORD_TYPE_ID));
                bean.setText(resultSet.getString(DBFields.DETAILED_HEALTH_CARD_RECORD_BEAN__TEXT));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a health card record entity from the result set row.
     */
    private static class HealthCardRecordMapper implements EntityMapper<HealthCardRecord> {

        public HealthCardRecord mapRow(ResultSet resultSet) {
            try {
                HealthCardRecord record = new HealthCardRecord();
                record.setId(resultSet.getInt(DBFields.ENTITY__ID));
                record.setUserId(resultSet.getInt(DBFields.HEALTH_CARD_RECORD__USER_ID));
                record.setPatientId(resultSet.getInt(DBFields.HEALTH_CARD_RECORD__PATIENT_ID));
                record.setDate(resultSet.getDate(DBFields.HEALTH_CARD_RECORD__DATE));
                record.setRecordTypeId(resultSet.getByte(DBFields.HEALTH_CARD_RECORD__RECORD_TYPE_ID));
                return record;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a health card record details entity from the result set row.
     */
    private static class HealthCardRecordDetailsMapper implements EntityMapper<HealthCardRecordDetails> {

        public HealthCardRecordDetails mapRow(ResultSet resultSet) {
            try {
                HealthCardRecordDetails details = new HealthCardRecordDetails();
                details.setRecordId(resultSet.getInt(DBFields.HEALTH_CARD_RECORD_DTLS__RECORD_ID));
                details.setLangId(resultSet.getByte(DBFields.HEALTH_CARD_RECORD_DTLS__LANG_ID));
                details.setText(resultSet.getString(DBFields.HEALTH_CARD_RECORD_DTLS__TEXT));
                return details;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
