package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.DetailedSpecializationBean;
import com.epam.burmensky.hospital.model.bean.LocalizedSpecializationBean;
import com.epam.burmensky.hospital.model.entity.Specialization;
import com.epam.burmensky.hospital.model.entity.SpecializationDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SpecializationDao {

    private static final int ELEMENTS_ON_PAGE = 20;

    private static final String SQL__FIND_ALL =
            "SELECT specializations.id, specializations_description.name" +
                    " FROM specializations INNER JOIN specializations_description ON specializations.id = specializations_description.specialization_id" +
                    " WHERE specializations_description.lang_id=? ORDER BY specializations_description.name";

    private static final String SQL__FIND_ALL_WITH_PAGINATION =
            "SELECT specializations.id, specializations_description.name" +
                    " FROM specializations INNER JOIN specializations_description ON specializations.id = specializations_description.specialization_id" +
                    " WHERE specializations_description.lang_id=? ORDER BY specializations_description.name LIMIT ? OFFSET ?";

    private static final String SQL__FIND_SPECIALIZATION_BEAN_BY_ID =
            "SELECT specializations.id, specializations_description.name" +
                    " FROM specializations INNER JOIN specializations_description ON specializations.id = specializations_description.specialization_id" +
                    " WHERE specializations_description.lang_id=? AND specialization.id=? ORDER BY specializations_description.name" +
                    " LIMIT ? OFFSET ?";

    private static final String SQL__FIND_SPECIALIZATION_BY_ID =
            "SELECT specializations.id FROM specializations WHERE specializations.id=?";

    private static final String SQL__FIND_SPECIALIZATION_DETAILS_BY_SPECIALIZATION_ID =
            "SELECT specialization_id, lang_id, name FROM specializations_description WHERE specialization_id=?";

    private static final String SQL__SPECIALIZATION_COUNT =
            "SELECT COUNT(id) AS specialization_count FROM specializations";

    private static final String SQL_INSERT_SPECIALIZATION =
            "INSERT INTO specializations () VALUES ()";

    private static final String SQL_DELETE_SPECIALIZATION =
            "DELETE FROM specializations WHERE id=?";

    private static final String SQL_INSERT_SPECIALIZATION_DETAILS =
            "INSERT INTO specializations_description (specialization_id, lang_id, name) VALUES (?,?,?)";

    private static final String SQL_UPDATE_SPECIALIZATION_DETAILS =
            "UPDATE specializations_description SET name=? WHERE specialization_id=? AND lang_id=?";

    private static final String SQL_DELETE_SPECIALIZATION_DETAILS =
            "DELETE FROM specializations_description WHERE specialization_id=?";

    /**
     * Returns a list of specializations on the given page.
     *
     * @param lang
     *            user language.
     * @return list of Patient entities.
     */
    public List<DetailedSpecializationBean> findAllSpecializations(Language lang) {
        List<DetailedSpecializationBean> specializations = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedSpecializationMapper mapper = new DetailedSpecializationMapper();
            pstmt = con.prepareStatement(SQL__FIND_ALL);
            pstmt.setByte(1, lang.getLangId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                specializations.add(mapper.mapRow(rs));
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
        return specializations;
    }

    /**
     * Returns a list of specializations on the given page.
     *
     * @param page
     *            page number.
     * @param lang
     *            user language.
     * @return list of Patient entities.
     */
    public List<DetailedSpecializationBean> findAllSpecializations(Integer page, Language lang) {
        List<DetailedSpecializationBean> specializations = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedSpecializationMapper mapper = new DetailedSpecializationMapper();
            pstmt = con.prepareStatement(SQL__FIND_ALL_WITH_PAGINATION);
            pstmt.setByte(1, lang.getLangId());
            pstmt.setInt(2, ELEMENTS_ON_PAGE);
            pstmt.setInt(3, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                specializations.add(mapper.mapRow(rs));
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
        return specializations;
    }

    /**
     * Returns the number of pages.
     *
     * @return number of pages.
     */
    public long pageCount() {
        Long specializationCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__SPECIALIZATION_COUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                specializationCount = rs.getLong("specialization_count");
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
        return (long) Math.ceil(specializationCount.doubleValue() / ELEMENTS_ON_PAGE);
    }

    /**
     * Returns a specialization with the given identifier.
     *
     * @param id
     *            Specialization identifier.
     * @param lang
     *            user language.
     * @return Specialization entity.
     */
    public DetailedSpecializationBean findSpecializationById(Integer id, Language lang) {
        DetailedSpecializationBean specialization = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedSpecializationMapper mapper = new DetailedSpecializationMapper();
            pstmt = con.prepareStatement(SQL__FIND_SPECIALIZATION_BEAN_BY_ID);
            pstmt.setByte(1, lang.getLangId());
            pstmt.setLong(2, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                specialization = mapper.mapRow(rs);
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
        return specialization;
    }

    /**
     * Returns a localized specialization bean with the given identifier.
     *
     * @param id
     *            Specialization identifier.
     * @return LocalizedSpecializationBean object.
     */
    public LocalizedSpecializationBean findSpecializationById(Integer id) {
        LocalizedSpecializationBean localizedSpecializationBean = null;
        Specialization specialization = null;
        List<SpecializationDetails> specializationDetails = new ArrayList<>();
        PreparedStatement pstmtSpecialization = null;
        PreparedStatement pstmtSpecializationDetails = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            SpecializationMapper mapper = new SpecializationMapper();
            pstmtSpecialization = con.prepareStatement(SQL__FIND_SPECIALIZATION_BY_ID);
            pstmtSpecialization.setInt(1, id);
            rs = pstmtSpecialization.executeQuery();
            if (rs.next())
                specialization = mapper.mapRow(rs);
            rs.close();
            pstmtSpecialization.close();

            SpecializationDetailsMapper detailsMapper = new SpecializationDetailsMapper();
            pstmtSpecializationDetails = con.prepareStatement(SQL__FIND_SPECIALIZATION_DETAILS_BY_SPECIALIZATION_ID);
            pstmtSpecializationDetails.setInt(1, id);
            rs = pstmtSpecializationDetails.executeQuery();
            while (rs.next())
                specializationDetails.add(detailsMapper.mapRow(rs));
            rs.close();
            pstmtSpecializationDetails.close();

            localizedSpecializationBean = new LocalizedSpecializationBean(specialization, specializationDetails);
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
        return localizedSpecializationBean;
    }

    /**
     * Insert specialization.
     *
     * @param specialization
     *            specialization to insert.
     */
    public void insertSpecialization(LocalizedSpecializationBean specialization) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertSpecialization(con, specialization);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Update specialization.
     *
     * @param specialization
     *            specialization to update.
     */
    public void updateSpecialization(LocalizedSpecializationBean specialization) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            updateSpecialization(con, specialization);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBConnectionManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete specialization.
     *
     * @param specializationId
     *            identifier of specialization to delete.
     */
    public void deleteSpecialization(Integer specializationId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deleteSpecialization(con, specializationId);
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
     * Insert specialization.
     *
     * @param specialization
     *            specialization to insert.
     * @throws SQLException
     */
    private void insertSpecialization(Connection con, LocalizedSpecializationBean specialization) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_SPECIALIZATION, Statement.RETURN_GENERATED_KEYS);
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                specialization.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating specialization failed, no ID obtained.");
            }
        }

        pstmt.close();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_SPECIALIZATION_DETAILS);
        for (SpecializationDetails details : specialization.getSpecializationDetails()) {
            preparedStatement.setInt(1, specialization.getId());
            preparedStatement.setByte(2, details.getLangId());
            preparedStatement.setString(3, details.getName());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    /**
     * Update specialization.
     *
     * @param specialization
     *            specialization to update.
     * @throws SQLException
     */
    private void updateSpecialization(Connection con, LocalizedSpecializationBean specialization) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_UPDATE_SPECIALIZATION_DETAILS);
        for (SpecializationDetails details : specialization.getSpecializationDetails()) {
            pstmtDetails.setString(1, details.getName());
            pstmtDetails.setInt(2, details.getSpecializationId());
            pstmtDetails.setByte(3, details.getLangId());
            pstmtDetails.addBatch();
        }
        pstmtDetails.executeBatch();
        pstmtDetails.close();
    }

    /**
     * Delete user.
     *
     * @param specializationId
     *            identifier of specialization to delete.
     * @throws SQLException
     */
    private void deleteSpecialization(Connection con, Integer specializationId) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_DELETE_SPECIALIZATION_DETAILS);
        pstmtDetails.setInt(1, specializationId);
        pstmtDetails.executeUpdate();
        pstmtDetails.close();

        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_SPECIALIZATION);
        pstmt.setInt(1, specializationId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a detailed specialization bean from the result set row.
     */
    private static class DetailedSpecializationMapper implements EntityMapper<DetailedSpecializationBean> {

        public DetailedSpecializationBean mapRow(ResultSet resultSet) {
            try {
                DetailedSpecializationBean bean = new DetailedSpecializationBean();
                bean.setId(resultSet.getInt(DBFields.ENTITY__ID));
                bean.setName(resultSet.getString(DBFields.DETAILED_SPECIALIZATION_BEAN__NAME));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a specialization entity from the result set row.
     */
    private static class SpecializationMapper implements EntityMapper<Specialization> {

        public Specialization mapRow(ResultSet resultSet) {
            try {
                Specialization specialization = new Specialization();
                specialization.setId(resultSet.getInt(DBFields.ENTITY__ID));
                return specialization;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a specialization details entity from the result set row.
     */
    private static class SpecializationDetailsMapper implements EntityMapper<SpecializationDetails> {

        public SpecializationDetails mapRow(ResultSet resultSet) {
            try {
                SpecializationDetails specializationDetails = new SpecializationDetails();
                specializationDetails.setSpecializationId(resultSet.getInt(DBFields.SPECIALIZATION_DTLS__SPECIALIZATION_ID));
                specializationDetails.setLangId(resultSet.getByte(DBFields.SPECIALIZATION_DTLS__LANG_ID));
                specializationDetails.setName(resultSet.getString(DBFields.SPECIALIZATION_DTLS__NAME));
                return specializationDetails;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }



}
