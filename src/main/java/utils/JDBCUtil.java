package utils;

import java.sql.*;

public class JDBCUtil {
    private static final String url = "jdbc:mysql://localhost:3306/lrui1?serverTimezone=Asia/Chongqing";
    private static final String username = "root";
    private static final String password = "jmu_zuicaide";

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(url, username, password);
        return con;
    }


    public static void closeConnection(Connection con) {
        if(con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void realeaseAll(ResultSet rs, Statement st, Connection con) {
        if(rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null) {
            try {
                st.close();
                st = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(con);
    }

}
