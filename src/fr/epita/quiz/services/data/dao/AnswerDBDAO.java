package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.MCQAnswer;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.datamodel.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class AnswerDBDAO {

    /**
     * Creates table if it does not exist.
     * @throws SQLException details
     */
    public void createTableIfNotExists() throws SQLException {
        Connection connection = getConnection();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS ANSWERS( id SERIAL PRIMARY KEY, quiz_id INT, student_id varchar(255), grade varchar(255))";
        connection.prepareStatement(createTableQuery).execute();
        connection.close();
    }

    /**
     * Creates new answer.
     * @param answer details
     * @throws SQLException details
     */

    public void create(Answer answer) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();

        String insertQuery = "INSERT INTO ANSWERS(quiz_id, student_id, grade) values (?, ?, ?) RETURNING id";

        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setInt(1, answer.getQuiz().getId());
        ps.setString(2, answer.getStudent().getId());
        ps.setString(3, answer.getGrade());
        ResultSet resultSet = ps.executeQuery();
        int answerID = 0;
        while (resultSet.next()){
            answerID = resultSet.getInt("id");
            answer.setId(answerID);
        }

        connection.close();
    }

    /**
     * Returns a list of all answers.
     * @return answers details
     * @throws SQLException details
     */

    public List<Answer> readAll() throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT ANSWERS.id, ANSWERS.student_id, STUDENTS.studentname AS student_name, ANSWERS.grade, ANSWERS.quiz_id, QUIZZES.title AS quiz_title from ANSWERS INNER JOIN QUIZZES ON ANSWERS.quiz_id=QUIZZES.id INNER JOIN STUDENTS ON ANSWERS.student_id=STUDENTS.id";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Answer> answers = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String studentID = resultSet.getString("student_id");
            String studentName = resultSet.getString("student_name");
            int quizID = resultSet.getInt("quiz_id");
            String quizTitle = resultSet.getString("quiz_title");
            String grade = resultSet.getString("grade");

            Quiz quiz = new Quiz(quizID, quizTitle);
            Student student = new Student(studentID, studentName);
            List<MCQAnswer> answerList = new ArrayList<>();
            Answer answer = new Answer(id, quiz, student, answerList);
            answer.setGrade(grade);
            answers.add(answer);
        }
        connection.close();

        return answers;
    }

    /**
     * Returns a list of all answers by a single student.
     * @param targetStudentID details
     * @return answers details
     * @throws SQLException details
     */
    public List<Answer> readByStudentID(String targetStudentID) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();
        String sqlQuery = "SELECT " +
                "ANSWERS.id, ANSWERS.student_id, STUDENTS.studentname AS student_name, ANSWERS.grade, ANSWERS.quiz_id, QUIZZES.title AS quiz_title from ANSWERS " +
                "INNER JOIN QUIZZES ON ANSWERS.quiz_id=QUIZZES.id INNER JOIN STUDENTS ON ANSWERS.student_id=STUDENTS.id WHERE ANSWERS.student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, targetStudentID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Answer> answers = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String studentID = resultSet.getString("student_id");
            String studentName = resultSet.getString("student_name");
            int quizID = resultSet.getInt("quiz_id");
            String quizTitle = resultSet.getString("quiz_title");
            String grade = resultSet.getString("grade");

            Quiz quiz = new Quiz(quizID, quizTitle);
            Student student = new Student(studentID, studentName);
            List<MCQAnswer> answerList = new ArrayList<>();
            Answer answer = new Answer(id, quiz, student, answerList);
            answer.setGrade(grade);
            answers.add(answer);
        }
        connection.close();

        return answers;
    }

    /**
     * Deletes an answer.
     * @param answer details
     * @throws SQLException details
     */
    public void delete(Answer answer) throws SQLException {
        createTableIfNotExists();
        Connection connection = getConnection();

        String insertQuery = "DELETE FROM ANSWERS WHERE ANSWERS.id = ?";
        PreparedStatement ps = connection.prepareStatement(insertQuery);
        ps.setInt(1, answer.getId());
        ps.execute();

        connection.close();
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/quiz-manager", "faris", "");
    }
}
