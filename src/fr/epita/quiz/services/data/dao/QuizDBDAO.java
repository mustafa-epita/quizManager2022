package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.services.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Mustafa Faris
 *
 */
public class QuizDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS QUIZZES( id SERIAL PRIMARY KEY, title varchar(255))";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new quiz in DB.
     * @param quiz details
     * @param topics details
     * @param difficulty details
     * @throws SQLException details
     */

    public void create(Quiz quiz, String[] topics, Integer difficulty) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO QUIZZES(title) values (?) RETURNING id";

        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setString(1, quiz.getTitle());
        ResultSet resultSet = ps.executeQuery();
        int quizID = 0;
        while (resultSet.next()){
            quizID = resultSet.getInt("id");
            quiz.setId(quizID);
        }

        List<Question> filteredQuestions = new ArrayList<>();
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        List<Question> questions = questionDBDAO.readAll();
        for (Question question: questions) {

            for (String queryTopic: topics) {
                Boolean hasDifficulty = question.getDifficulty() == difficulty;
                Boolean hasTopic = Arrays.stream(question.getTopics()).anyMatch(topic -> topic.trim().equalsIgnoreCase(queryTopic.trim()));

                if (hasDifficulty && hasTopic) {
                    filteredQuestions.add(question);
                    break;
                }
            }
        }

        String createQuizzesQuestionsTableQuery = "CREATE TABLE IF NOT EXISTS QUIZZES_QUESTIONS( id SERIAL PRIMARY KEY, quiz_id INT, question_id INT)";
        connection.prepareStatement(createQuizzesQuestionsTableQuery).execute();

        for (Question question: filteredQuestions) {
            String insertIntoQuizzesQuestionsQuery = "INSERT INTO QUIZZES_QUESTIONS (quiz_id, question_id) VALUES (?, ?)";
            ps = connection.prepareStatement(insertIntoQuizzesQuestionsQuery);
            ps.setInt(1, quizID);
            ps.setInt(2, question.getId());
            ps.execute();
        }

        connection.close();
    }

    /**
     * Returns a list of all quizzes in DB.
     * @return Quizzes details
     * @throws SQLException details
     */

    public List<Quiz> readAll() throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT id, title from QUIZZES";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Quiz> quizzes = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            quizzes.add(new Quiz(id, title));
        }
        connection.close();

        return quizzes;
    }

    public List<Quiz> readUniqueQuizzes(Student student) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT id, title from QUIZZES WHERE ";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Quiz> quizzes = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            quizzes.add(new Quiz(id, title));
        }
        connection.close();

        return quizzes;
    }

    /**
     * Deletes a quiz from DB.
     * @param quiz details
     * @throws SQLException details
     */

    public void delete(Quiz quiz) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM QUIZZES WHERE QUIZZES.id = ?");
        preparedStatement.setInt(1, quiz.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("DELETE FROM QUIZZES_QUESTIONS WHERE QUIZZES_QUESTIONS.quiz_id = ?");
        preparedStatement.setInt(1, quiz.getId());
        preparedStatement.execute();
        connection.close();
    }


    /**
     * Retrieves a quiz and its choices from DB.
     * @param quiz details
     * @return MCQQuestions details
     * @throws SQLException details
     */
    public List<MCQQuestion> readQuiz(Quiz quiz) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();

        String sqlQuery = "SELECT question_id from QUIZZES_QUESTIONS where quiz_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, quiz.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        List<MCQQuestion> mcqQuestions = new ArrayList<>();
        MCQChoicesDBDAO mcqChoicesDBDAO = new MCQChoicesDBDAO();
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        while (resultSet.next()){
            int questionID = resultSet.getInt("question_id");
            Question question = questionDBDAO.readQuestionByID(questionID);
            List<MCQChoice> choices = mcqChoicesDBDAO.readByQuestionID(questionID);
            mcqQuestions.add(new MCQQuestion(question.getId(), question.getQuestion(),question.getTopics(), question.getDifficulty(), choices));
        }

        connection.close();
        return mcqQuestions;
    }

    private Connection getConnection() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.GetDBConnection();
    }
}
