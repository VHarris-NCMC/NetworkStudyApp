package net.shadyoakdesign.netplus;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
    private static final String DB_URL = "jdbc:mysql://your-database-url";
    private static final String DB_USERNAME = "your-username";
    private static final String DB_PASSWORD = "your-password";

    private Connection connection;

    public DBHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public ResultSet executeQuery(String query, Object... params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setQueryParameters(statement, params);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return resultSet;
    }

    private void setQueryParameters(PreparedStatement statement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
        }
    }

    public void addQuizRecord(Quiz quiz)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef.setValue("Hello, World!");
    }
}
