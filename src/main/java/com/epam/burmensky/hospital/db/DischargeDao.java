package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.DetailedDischargeBean;
import com.epam.burmensky.hospital.model.bean.LocalizedDischargeBean;
import com.epam.burmensky.hospital.model.entity.Discharge;
import com.epam.burmensky.hospital.model.entity.DischargeDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DischargeDao {

    private static final String SQL__FIND_DISCHARGE_BEAN_BY_PATIENT =
            "SELECT discharges.id, discharges.patient_id, patients_description.first_name, patients_description.second_name," +
                    " patients_description.patronymic, discharges.date, discharges_description.diagnosis" +
                    " FROM ((discharges INNER JOIN discharges_description ON discharges_description.discharge_id = discharges.id)" +
                    " INNER JOIN patients ON discharges.patient_id = patients.id)" +
                    " INNER JOIN patients_description ON patients_description.patient_id = patients.id" +
                    " WHERE discharges.patient_id=? AND discharges_description.lang_id=?";

    private static final String SQL__FIND_DISCHARGE_BY_PATIENT =
            "SELECT id, patient_id, date" +
                    " FROM discharges" +
                    " WHERE patient_id=?";

    private static final String SQL__FIND_DISCHARGE_DETAILS_BY_DISCHARGE_ID =
            "SELECT discharge_id, lang_id, diagnosis" +
                    " FROM discharges_description" +
                    " WHERE discharge_id=?";

    private static final String SQL_INSERT_DISCHARGE =
            "INSERT INTO discharges (patient_id, date) VALUES (?,?)";

    private static final String SQL_UPDATE_DISCHARGE =
            "UPDATE discharges SET date=? WHERE id=?";

    private static final String SQL_DELETE_DISCHARGE =
            "DELETE FROM discharges WHERE id=?";

    private static final String SQL_INSERT_DISCHARGE_DETAILS =
            "INSERT INTO discharges_description (discharge_id, lang_id, diagnosis) VALUES (?,?,?)";

    private static final String SQL_UPDATE_DISCHARGE_DETAILS =
            "UPDATE discharges_description SET diagnosis=? WHERE discharge_id=? AND lang_id=?";

    private static final String SQL_DELETE_DISCHARGE_DETAILS =
            "DELETE FROM discharges_description WHERE discharge_id=?";

    /**
     * Returns a discharge of specified patient.
     *
     * @param patientId
     *            patient identifier.
     * @param lang
     *            user language.
     * @return Discharge entity.
     */
    public DetailedDischargeBean findPatientDischarge(Integer patientId, Language lang) {
        DetailedDischargeBean discharge = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedDischargeMapper mapper = new DetailedDischargeMapper();
            pstmt = con.prepareStatement(SQL__FIND_DISCHARGE_BEAN_BY_PATIENT);
            pstmt.setInt(1, patientId);
            pstmt.setByte(2, lang.getLangId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                discharge = mapper.mapRow(rs);
            }
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return discharge;
    }

    /**
     * Returns a discharge of specified patient.
     *
     * @param patientId
     *            patient identifier.
     * @return Discharge entity.
     */
    public LocalizedDischargeBean findPatientDischarge(Integer patientId) {
        LocalizedDischargeBean localizedDischargeBean = null;
        Discharge discharge = null;
        List<DischargeDetails> dischargeDetails = new ArrayList<>();
        PreparedStatement pstmt = null;
        PreparedStatement pstmtDetails = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DischargeMapper mapper = new DischargeMapper();
            pstmt = con.prepareStatement(SQL__FIND_DISCHARGE_BY_PATIENT);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            if (rs.next())
                discharge = mapper.mapRow(rs);
            rs.close();
            pstmt.close();

            if (discharge != null) {
                pstmtDetails = con.prepareStatement(SQL__FIND_DISCHARGE_DETAILS_BY_DISCHARGE_ID);
                DischargeDetailsMapper detailsMapper = new DischargeDetailsMapper();
                pstmtDetails.setInt(1, discharge.getId());
                rs = pstmtDetails.executeQuery();
                while (rs.next())
                    dischargeDetails.add(detailsMapper.mapRow(rs));
                rs.close();
                pstmtDetails.close();
                localizedDischargeBean = new LocalizedDischargeBean(discharge, dischargeDetails);

                DBConnectionManager.getInstance().commitAndClose(con);
            }
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return localizedDischargeBean;
    }

    /**
     * Insert discharge.
     *
     * @param discharge
     *            discharge to insert.
     */
    public void insertDischarge(LocalizedDischargeBean discharge) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertDischarge(con, discharge);
            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
    }

    /**
     * Update discharge.
     *
     * @param discharge
     *            discharge to update.
     */
    public void updateDischarge(LocalizedDischargeBean discharge) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            updateDischarge(con, discharge);
            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
    }

    /**
     * Delete discharge.
     *
     * @param dischargeId
     *            identifier of discharge to delete.
     */
    public void deleteDischarge(Integer dischargeId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deleteDischarge(con, dischargeId);
            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Insert discharge.
     *
     * @param discharge
     *            specialization to insert.
     * @throws SQLException
     */
    private void insertDischarge(Connection con, LocalizedDischargeBean discharge) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_DISCHARGE, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, discharge.getPatientId());
        pstmt.setDate(2, new java.sql.Date(discharge.getDate().getTime()));
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                discharge.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating patient failed, no ID obtained.");
            }
        }

        pstmt.close();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_DISCHARGE_DETAILS);
        for (DischargeDetails details : discharge.getDischargeDetails()) {
            preparedStatement.setInt(1, discharge.getId());
            preparedStatement.setByte(2, details.getLangId());
            preparedStatement.setString(3, details.getDiagnosis());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    /**
     * Update discharge.
     *
     * @param discharge
     *            discharge to update.
     * @throws SQLException
     */
    private void updateDischarge(Connection con, LocalizedDischargeBean discharge) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_DISCHARGE);
        pstmt.setDate(1, new java.sql.Date(discharge.getDate().getTime()));
        pstmt.setInt(2, discharge.getId());
        pstmt.executeUpdate();
        pstmt.close();
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_UPDATE_DISCHARGE_DETAILS);
        for (DischargeDetails details : discharge.getDischargeDetails()) {
            pstmtDetails.setString(1, details.getDiagnosis());
            pstmtDetails.setInt(2, details.getDischargeId());
            pstmtDetails.setByte(3, details.getLangId());
            pstmtDetails.addBatch();
        }
        pstmtDetails.executeBatch();
        pstmtDetails.close();
    }

    /**
     * Delete discharge.
     *
     * @param dischargeId
     *            identifier of discharge to to delete.
     * @throws SQLException
     */
    private void deleteDischarge(Connection con, Integer dischargeId) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_DELETE_DISCHARGE_DETAILS);
        pstmtDetails.setInt(1, dischargeId);
        pstmtDetails.executeUpdate();
        pstmtDetails.close();

        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_DISCHARGE);
        pstmt.setInt(1, dischargeId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a detailed discharge bean from the result set row.
     */
    private static class DetailedDischargeMapper implements EntityMapper<DetailedDischargeBean> {

        public DetailedDischargeBean mapRow(ResultSet resultSet) {
            try {
                DetailedDischargeBean bean = new DetailedDischargeBean();
                bean.setId(resultSet.getInt(DBFields.ENTITY__ID));
                bean.setPatientId(resultSet.getInt(DBFields.DETAILED_DISCHARGE__PATIENT_ID));
                bean.setPatientFirstName(resultSet.getString(DBFields.DETAILED_DISCHARGE__PATIENT_FIRST_NAME));
                bean.setPatientSecondName(resultSet.getString(DBFields.DETAILED_DISCHARGE__PATIENT_SECOND_NAME));
                bean.setPatientPatronymic(resultSet.getString(DBFields.DETAILED_DISCHARGE__PATIENT_PATRONYMIC));
                bean.setDate(resultSet.getDate(DBFields.DETAILED_DISCHARGE__DATE));
                bean.setDiagnosis(resultSet.getString(DBFields.DETAILED_DISCHARGE__DIAGNOSIS));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a discharge entity from the result set row.
     */
    private static class DischargeMapper implements EntityMapper<Discharge> {

        public Discharge mapRow(ResultSet resultSet) {
            try {
                Discharge discharge = new Discharge();
                discharge.setId(resultSet.getInt(DBFields.ENTITY__ID));
                discharge.setPatientId(resultSet.getInt(DBFields.DISCHARGE__PATIENT_ID));
                discharge.setDate(resultSet.getDate(DBFields.DISCHARGE__DATE));
                return discharge;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a discharge details entity from the result set row.
     */
    private static class DischargeDetailsMapper implements EntityMapper<DischargeDetails> {

        public DischargeDetails mapRow(ResultSet resultSet) {
            try {
                DischargeDetails dischargeDetails = new DischargeDetails();
                dischargeDetails.setDischargeId(resultSet.getInt(DBFields.DISCHARGE_DTLS__DISCHARGE_ID));
                dischargeDetails.setLangId(resultSet.getByte(DBFields.DISCHARGE_DTLS__LANG_ID));
                dischargeDetails.setDiagnosis(resultSet.getString(DBFields.DISCHARGE_DTLS__DIAGNOSIS));
                return dischargeDetails;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
