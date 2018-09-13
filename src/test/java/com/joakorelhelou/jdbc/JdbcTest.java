package com.joakorelhelou.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.out;
import static org.junit.Assert.fail;

public class JdbcTest {

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private final String user = "hbstudent";
    private final String pass = "hbstudent";

    @Test
    public void jdbcConnection() {
        try {
            out.println("Connecting to database");
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
            myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error connecting with the database");
        }
    }

    @Test(expected = SQLException.class)
    public void jbdcConnectionInvalidPass() throws SQLException {
        out.println("Connecting to database with invalid password");
        DriverManager.getConnection(jdbcUrl, user, "Invalid Pass");
        fail("Connected to the database with invalid password");
    }
}
