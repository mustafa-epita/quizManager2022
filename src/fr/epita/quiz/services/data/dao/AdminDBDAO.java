package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class AdminDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS ADMINS( id SERIAL PRIMARY KEY, name varchar(255), password varchar(255))";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new admin in DB.
     * @param admin details
     * @throws SQLException details
     */

    public void create(Admin admin) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO ADMINS(name, password) values (?, ?) RETURNING id";
        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setString(1, admin.getName());
        ps.setString(2, admin.getPassword());
        ResultSet resultSet = ps.executeQuery();
        int adminID = 0;
        while (resultSet.next()){
            adminID = resultSet.getInt("id");
        }
        admin.setId(adminID);

        connection.close();
    }

    /**
     * Returns a list of all admin in DB.
     * @return admins details
     * @throws SQLException details
     */

    public List<Admin> readAll() throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT id, name, password from ADMINS";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Admin> admins = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            admins.add(new Admin(id, name, password));
        }
        connection.close();

        return admins;
    }

    /**
     * Deletes an admin from DB.
     * @param admin details
     * @throws SQLException details
     */

    public void delete(Admin admin) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ADMINS WHERE ADMINS.id = ?");
        preparedStatement.setInt(1, admin.getId());
        preparedStatement.execute();
        connection.close();
    }

    /**
     * Retrieves an admin from DB to auth login.
     * @param username details
     * @param password details
     * @return Admin details
     * @throws SQLException details
     */
    public Admin login(String username, String password) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, password FROM ADMINS WHERE ADMINS.name = ? AND ADMINS.password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        Admin resultAdmin = null;
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String resName = resultSet.getString("name");
            String resPassword = resultSet.getString("password");
            resultAdmin = new Admin(id, resName, resPassword);
        }
        connection.close();

        return resultAdmin;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/quiz-manager", "faris", "");
    }
}
