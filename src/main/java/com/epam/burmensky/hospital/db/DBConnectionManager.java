package com.epam.burmensky.hospital.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB connection manager. Works with MySQL DB.
 *
 * @author Rustam Burmensky
 *
 */
public class DBConnectionManager {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/hospital"+
            "?verifyServerCertificate=false" +
            "&useUnicode=true" +
            "&useSSL=false" +
            "&requireSSL=false" +
            "&useGmtMillisForDatetimes=true" +
            "&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false" +
            "&useTimezone=true" +
            "&serverTimezone=UTC";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final Logger logger = Logger.getLogger(DBConnectionManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBConnectionManager instance;

    private DBConnectionManager(){}

    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
        }
        catch (Exception ex) {
            logger.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and closes the given connection.
     *
     * @param con
     *            Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and closes the given connection.
     *
     * @param con
     *            Connection to be rolled back and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
