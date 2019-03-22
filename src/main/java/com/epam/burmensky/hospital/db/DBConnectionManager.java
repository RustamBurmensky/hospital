package com.epam.burmensky.hospital.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DB connection manager. Works with MySQL DB.
 *
 * @author Rustam Burmensky
 *
 */
public class DBConnectionManager {

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
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");

            // db - the name of data source
            DataSource ds = (DataSource)envContext.lookup("jdbc/db");
            con = ds.getConnection();
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
