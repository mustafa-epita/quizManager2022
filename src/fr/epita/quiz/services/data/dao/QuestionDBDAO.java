package fr.epita.quiz.services.data.dao;

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
public class QuestionDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS QUESTIONS( id SERIAL PRIMARY KEY, question varchar(255), topics varchar(255), difficulty INT)";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new question in DB.
     * @param question details
     * @throws SQLException details
     */
    public void create(Question question) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO QUESTIONS(question, topics, difficulty) values (?, ?, ?) RETURNING id";

        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setString(1, question.getQuestion());
        ps.setString(2, String.join(",",question.getTopics()));
        ps.setInt(3, question.getDifficulty());
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            question.setId(id);
        }
        connection.close();
    }

    /**
     * Returns a list of all questions in DB.
     * @return Questions details
     * @throws SQLException details
     */

    public List<Question> readAll() throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT id, question, topics, difficulty from QUESTIONS";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String question = resultSet.getString("question");
            String[] topics = resultSet.getString("topics").split(",");
            int difficulty = resultSet.getInt("difficulty");
            questions.add(new Question(id, question, topics, difficulty));
        }
        connection.close();

        return questions;
    }

    /**
     * Returns a single question by its from DB.
     * @param questionID details
     * @return question details
     * @throws SQLException details
     */
    public Question readQuestionByID(int questionID) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT id, question, topics, difficulty from QUESTIONS WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, questionID);
        ResultSet resultSet = preparedStatement.executeQuery();

        Question question = null;
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String questionText = resultSet.getString("question");
            String[] topics = resultSet.getString("topics").split(",");
            int difficulty = resultSet.getInt("difficulty");
            question = new Question(id, questionText, topics, difficulty);
        }
        connection.close();

        return question;
    }

    /**
     * Deletes a question from DB.
     * @param question details
     * @throws SQLException details
     */

    public void delete(Question question) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM QUESTIONS WHERE QUESTIONS.id = ?");
        preparedStatement.setInt(1, question.getId());
        preparedStatement.execute();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.GetDBConnection();
    }
}
