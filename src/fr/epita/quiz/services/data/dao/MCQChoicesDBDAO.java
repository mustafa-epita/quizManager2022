package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class MCQChoicesDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS CHOICES( id SERIAL PRIMARY KEY, choice varchar(255), valid BOOLEAN, question_id INT)";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new MCQChoice.
     * @param choice details
     * @param question details
     * @throws SQLException details
     */
    public void create(MCQChoice choice, Question question) throws SQLException {
        Connection connection = getConnection();

        createTableIfNotExists();
        String insertQuery = "INSERT INTO CHOICES(choice, valid, question_id) values (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setString(1, choice.getChoice());
        ps.setBoolean(2, choice.getValid());
        ps.setInt(3, question.getId());
        ps.execute();
        connection.close();
    }

    /**
     * Returns a list of all MCQchoices by question id.
     * @param questionID details
     * @return MCQChoices details
     * @throws SQLException details
     */
    public List<MCQChoice> readByQuestionID(int questionID) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "select id, choice, valid from CHOICES where CHOICES.question_id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setInt(1, questionID);
        ResultSet resultSet = ps.executeQuery();

        List<MCQChoice> choices = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String choice = resultSet.getString("choice");
            Boolean valid = resultSet.getBoolean("valid");
            choices.add(new MCQChoice(id, choice, valid));
        }
        connection.close();

        return choices;
    }

    /**
     * Deletes MCQChoices by question id.
     * @param question details
     * @throws SQLException details
     */
    public void deleteByQuestionID(Question question) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CHOICES WHERE CHOICES.question_id = ?");
        System.out.println(question.getId());
        preparedStatement.setInt(1, question.getId());
        preparedStatement.execute();
        connection.close();
    }

    /**
     * Deletes an MCQChoice.
     * @param choice details
     * @throws SQLException details
     */

    public void delete(MCQChoice choice) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CHOICES WHERE CHOICES.id = ?");
        preparedStatement.setInt(1, choice.getId());
        preparedStatement.execute();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.GetDBConnection();
    }
}
