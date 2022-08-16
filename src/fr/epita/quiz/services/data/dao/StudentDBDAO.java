package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */

public class StudentDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS STUDENTS( id varchar(30), studentName varchar(255))";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new student in DB.
     * @param student details
     * @throws SQLException details
     */

    public void create(Student student) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO STUDENTS(id, studentName) values (?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setString(1, student.getId());
        ps.setString(2, student.getName());
        ps.execute();
        connection.close();
    }

    /**
     * Returns a list of all students in DB.
     * @return Students details
     * @throws SQLException details
     */

    public List<Student> readAll() throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "select id, studentName from STUDENTS";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Student> students = new ArrayList<>();
        while (resultSet.next()){
            String name = resultSet.getString("studentName");
            String id = resultSet.getString("id");
            students.add(new Student(id, name));
        }
        connection.close();

        return students;
    }

    /**
     * Deletes a student from DB.
     * @param student details
     * @throws SQLException details
     */

    public void delete(Student student) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM STUDENTS WHERE STUDENTS.id = ?");
        preparedStatement.setString(1, student.getId());
        preparedStatement.execute();
        connection.close();
    }

    /**
     * Retrieves a student from DB to auth login.
     * @param email details
     * @param name details
     * @return Student details
     * @throws SQLException details
     */
    public Student login(String email, String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, studentname FROM STUDENTS WHERE UPPER(STUDENTS.id) = UPPER(?) AND UPPER(STUDENTS.studentname) = UPPER(?)");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Student resultStudent = null;
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String resName = resultSet.getString("studentname");
            resultStudent = new Student(id, resName);
        }
        connection.close();

        return resultStudent;
    }

    private Connection getConnection() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.GetDBConnection();
    }
}
