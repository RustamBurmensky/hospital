package com.epam.burmensky.hospital.db;

import com.epam.burmensky.hospital.model.bean.DetailedUserBean;
import com.epam.burmensky.hospital.model.bean.LocalizedUserBean;
import com.epam.burmensky.hospital.model.entity.User;
import com.epam.burmensky.hospital.model.entity.UserDetails;
import com.epam.burmensky.hospital.model.enumeration.Language;
import com.epam.burmensky.hospital.model.enumeration.UserSortingMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class UserDao {

    private static final int ELEMENTS_ON_PAGE = 20;

    private static final String SQL__FIND_ALL =
        "SELECT users.id, users_description.first_name, users_description.second_name, users_description.patronymic," +
            " users.birthday, users.role_id, specializations_description.name," +
            " users.login, users.password, COUNT(appointments.patient_id) AS patients_number" +
            " FROM ((users INNER JOIN users_description ON users.id = users_description.user_id)" +
            " INNER JOIN specializations_description ON users.specialization_id = specializations_description.specialization_id)" +
            " LEFT JOIN appointments ON appointments.user_id = users.id" +
            " WHERE users_description.lang_id=? AND specializations_description.lang_id=users_description.lang_id" +
            " GROUP BY users.id";

    private static final String SQL__FIND_USER_BEAN_BY_ID =
        "SELECT users.id, users_description.first_name, users_description.second_name, users_description.patronymic," +
            " users.birthday, users.role_id, specializations_description.name," +
            " users.login, users.password, COUNT(appointments.patient_id) AS patients_number" +
            " FROM ((users INNER JOIN users_description ON users.id = users_description.user_id)" +
            " INNER JOIN specializations_description ON users.specialization_id = specializations_description.specialization_id)" +
            " LEFT JOIN appointments ON appointments.user_id = users.id" +
            " WHERE users_description.lang_id=? AND specializations_description.lang_id=users_description.lang_id" +
            " AND users.id=? GROUP BY users.id";

    private static final String SQL__FIND_USER_BY_LOGIN =
        "SELECT id, birthday, role_id, specialization_id, lang_id, login, password" +
            " FROM users" +
            " WHERE login=?";

    private static final String SQL__FIND_USER_BEANS_BY_SPECIALIZATION =
        "SELECT users.id, users_description.first_name, users_description.second_name, users_description.patronymic," +
            " users.birthday, users.role_id, specializations_description.name," +
            " users.login, users.password, COUNT(appointments.patient_id) AS patients_number" +
            " FROM ((users INNER JOIN users_description ON users.id = users_description.user_id)" +
            " INNER JOIN specializations_description ON users.specialization_id = specializations_description.specialization_id)" +
            " LEFT JOIN appointments ON appointments.user_id = users.id" +
            " WHERE users_description.lang_id=? AND specializations_description.lang_id=users_description.lang_id" +
            " AND users.specialization_id=? GROUP BY users.id";

    private static final String SQL__FIND_USER_BY_ID =
        "SELECT id, birthday, role_id, specialization_id, lang_id, login, password" +
                " FROM users" +
                " WHERE id=?";

    private static final String SQL__FIND_USER_DETAILS_BY_USER_ID =
        "SELECT user_id, lang_id, first_name, second_name, patronymic" +
                " FROM users_description" +
                " WHERE user_id=?";

    private static final String SQL__PAGINATION = " LIMIT ? OFFSET ?";

    private static final String SQL__USER_COUNT =
            "SELECT COUNT(id) AS users_count FROM users";

    private static final String SQL_INSERT_USER =
        "INSERT INTO users (birthday, role_id, specialization_id, lang_id, login, password)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER =
        "UPDATE users SET birthday=?, role_id=?, specialization_id=?, lang_id=?, login=?, password=?"+
            " WHERE id=?";

    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE id=?";

    private static final String SQL_INSERT_USER_DETAILS =
            "INSERT INTO users_description (user_id, lang_id, first_name, second_name, patronymic) VALUES (?,?,?,?,?)";

    private static final String SQL_UPDATE_USER_DETAILS =
            "UPDATE users_description SET first_name=?, second_name=?, patronymic=? WHERE user_id=? AND lang_id=?";

    private static final String SQL_DELETE_USER_DETAILS =
            "DELETE FROM users_description WHERE user_id=?";

    /**
     * Returns a list of users on the given page.
     *
     * @param page
     *            page number.
     * @param lang
     *            user language.
     * @param sortingMode
     *            user sorting mode.
     * @return list of User entities.
     */
    public List<DetailedUserBean> findAllUsers(Integer page, Language lang, UserSortingMode sortingMode) {
        List<DetailedUserBean> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedUserMapper mapper = new DetailedUserMapper();
            pstmt = con.prepareStatement(SQL__FIND_ALL + sortingMode.getSQLOrderBy() + SQL__PAGINATION);
            pstmt.setByte(1, (byte)lang.getLangId());
            pstmt.setInt(2, ELEMENTS_ON_PAGE);
            pstmt.setInt(3, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next())
                users.add(mapper.mapRow(rs));
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return users;
    }

    /**
     * Returns a list of users with the given specialization.
     *
     * @param specializationId
     *            user specialization identifier.
     * @param lang
     *            user language.
     * @return User entity.
     */
    public List<DetailedUserBean> findUsersBySpecialization(Integer page, Integer specializationId,
                                                            Language lang, UserSortingMode sortingMode) {
        List<DetailedUserBean> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedUserMapper mapper = new DetailedUserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BEANS_BY_SPECIALIZATION + sortingMode.getSQLOrderBy() + SQL__PAGINATION);
            pstmt.setByte(1, (byte)lang.getLangId());
            pstmt.setInt(2, specializationId);
            pstmt.setInt(3, ELEMENTS_ON_PAGE);
            pstmt.setInt(4, page * ELEMENTS_ON_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next())
                users.add(mapper.mapRow(rs));
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return users;
    }

    /**
     * Returns the number of pages.
     *
     * @return number of pages.
     */
    public long pageCount() {
        Long usersCount = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__USER_COUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                usersCount = rs.getLong("users_count");
            }
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return (long) Math.ceil(usersCount.doubleValue() / ELEMENTS_ON_PAGE);
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param id
     *            User identifier.
     * @param lang
     *            user language.
     * @return User entity.
     */
    public DetailedUserBean findUserById(Integer id, Language lang) {
        DetailedUserBean user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            DetailedUserMapper mapper = new DetailedUserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BEAN_BY_ID);
            pstmt.setByte(1, lang.getLangId());
            pstmt.setInt(2, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * Returns a localized user bean with the given identifier.
     *
     * @param id
     *            User identifier.
     * @return LocalizedUserBean object.
     */
    public LocalizedUserBean findUserById(Integer id) {
        LocalizedUserBean localizedUserBean = null;
        User user = null;
        List<UserDetails> userDetails = new ArrayList<>();
        PreparedStatement pstmtUser = null;
        PreparedStatement pstmtUserDetails = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmtUser = con.prepareStatement(SQL__FIND_USER_BY_ID);
            pstmtUser.setInt(1, id);
            rs = pstmtUser.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmtUser.close();

            if (user != null) {
                UserDetailsMapper userDetailsMapper = new UserDetailsMapper();
                pstmtUserDetails = con.prepareStatement(SQL__FIND_USER_DETAILS_BY_USER_ID);
                pstmtUserDetails.setInt(1, id);
                rs = pstmtUserDetails.executeQuery();
                while (rs.next())
                    userDetails.add(userDetailsMapper.mapRow(rs));
                rs.close();
                pstmtUserDetails.close();

                localizedUserBean = new LocalizedUserBean(user, userDetails);
            }

            DBConnectionManager.getInstance().commitAndClose(con);
        }
        catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return localizedUserBean;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login
     *            User login.
     * @return User entity.
     */
    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();

            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * Insert user.
     *
     * @param user
     *            user to insert.
     */
    public void insertUser(LocalizedUserBean user) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            insertUser(con, user);
            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
    }

    /**
     * Update user.
     *
     * @param user
     *            user to update.
     */
    public void updateUser(LocalizedUserBean user) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            updateUser(con, user);
            DBConnectionManager.getInstance().commitAndClose(con);
        } catch (SQLException ex) {
            DBConnectionManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        }
    }

    /**
     * Delete user.
     *
     * @param userId
     *            identifier of user to delete.
     */
    public void deleteUser(Integer userId) {
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection();
            deleteUser(con, userId);
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
     * Insert user.
     *
     * @param user
     *            user to insert.
     * @throws SQLException
     */
    private void insertUser(Connection con, LocalizedUserBean user) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        int k = 1;
        pstmt.setDate(k++, new java.sql.Date(user.getBirthday().getTime()));
        pstmt.setByte(k++, user.getRoleId());
        pstmt.setInt(k++, user.getSpecializationId());
        pstmt.setByte(k++, user.getLangId());
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k, user.getPassword());
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }

        pstmt.close();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_USER_DETAILS);
        for (UserDetails details : user.getUserDetails()) {
            preparedStatement.setInt(1, user.getId());
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
     * Update user.
     *
     * @param user
     *            user to update.
     * @throws SQLException
     */
    private void updateUser(Connection con, LocalizedUserBean user) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
        int k = 1;
        pstmt.setDate(k++, new java.sql.Date(user.getBirthday().getTime()));
        pstmt.setByte(k++, user.getRoleId());
        pstmt.setInt(k++, user.getSpecializationId());
        pstmt.setByte(k++, user.getLangId());
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k++, user.getPassword());
        pstmt.setInt(k, user.getId());
        pstmt.executeUpdate();
        pstmt.close();
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_UPDATE_USER_DETAILS);
        for (UserDetails details : user.getUserDetails()) {
            pstmtDetails.setString(1, details.getFirstName());
            pstmtDetails.setString(2, details.getSecondName());
            pstmtDetails.setString(3, details.getPatronymic());
            pstmtDetails.setInt(4, details.getUserId());
            pstmtDetails.setByte(5, details.getLangId());
            pstmtDetails.addBatch();
        }
        pstmtDetails.executeBatch();
        pstmtDetails.close();
    }

    /**
     * Delete user.
     *
     * @param userId
     *            identifier of user to delete.
     * @throws SQLException
     */
    private void deleteUser(Connection con, Integer userId) throws SQLException {
        PreparedStatement pstmtDetails = con.prepareStatement(SQL_DELETE_USER_DETAILS);
        pstmtDetails.setInt(1,userId);
        pstmtDetails.executeUpdate();
        pstmtDetails.close();

        PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USER);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a detailed user bean from the result set row.
     */
    private static class DetailedUserMapper implements EntityMapper<DetailedUserBean> {

        public DetailedUserBean mapRow(ResultSet resultSet) {
            try {
                DetailedUserBean bean = new DetailedUserBean();
                bean.setId(resultSet.getInt(DBFields.ENTITY__ID));
                bean.setFirstName(resultSet.getString(DBFields.DETAILED_USER_BEAN__FIRST_NAME));
                bean.setSecondName(resultSet.getString(DBFields.DETAILED_USER_BEAN__SECOND_NAME));
                bean.setPatronymic(resultSet.getString(DBFields.DETAILED_USER_BEAN__PATRONYMIC));
                bean.setRoleId(resultSet.getByte(DBFields.DETAILED_USER_BEAN__ROLE_ID));
                bean.setSpecializationName(resultSet.getString(DBFields.DETAILED_USER_BEAN__SPECIALIZATION_NAME));
                bean.setBirthday(resultSet.getDate(DBFields.DETAILED_USER_BEAN__BIRTHDAY));
                bean.setLogin(resultSet.getString(DBFields.DETAILED_USER_BEAN__LOGIN));
                bean.setPassword(resultSet.getString(DBFields.DETAILED_USER_BEAN__PASSWORD));
                bean.setPatientsNumber(resultSet.getInt(DBFields.DETAILED_USER_BEAN__PATIENTS_NUMBER));
                return bean;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a user entity from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        public User mapRow(ResultSet resultSet) {
            try {
                User user = new User();
                user.setId(resultSet.getInt(DBFields.ENTITY__ID));
                user.setRoleId(resultSet.getByte(DBFields.USER__ROLE_ID));
                user.setSpecializationId(resultSet.getInt(DBFields.USER__SPECIALIZATION_ID));
                user.setLangId(resultSet.getByte(DBFields.USER__LANG_ID));
                user.setBirthday(resultSet.getDate(DBFields.USER__BIRTHDAY));
                user.setLogin(resultSet.getString(DBFields.USER__LOGIN));
                user.setPassword(resultSet.getString(DBFields.USER__PASSWORD));
                return user;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    /**
     * Extracts a user details entity from the result set row.
     */
    private static class UserDetailsMapper implements EntityMapper<UserDetails> {

        public UserDetails mapRow(ResultSet resultSet) {
            try {
                UserDetails userDetails = new UserDetails();
                userDetails.setUserId(resultSet.getInt(DBFields.USER_DTLS__USER_ID));
                userDetails.setLangId(resultSet.getByte(DBFields.USER_DTLS__LANG_ID));
                userDetails.setFirstName(resultSet.getString(DBFields.USER_DTLS__FIRST_NAME));
                userDetails.setSecondName(resultSet.getString(DBFields.USER_DTLS__SECOND_NAME));
                userDetails.setPatronymic(resultSet.getString(DBFields.USER_DTLS__PATRONYMIC));
                return userDetails;
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
