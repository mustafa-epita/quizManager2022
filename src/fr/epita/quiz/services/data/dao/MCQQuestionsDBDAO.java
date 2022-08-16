package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class MCQQuestionsDBDAO {

    /**
     * Creates new MCQQuestion in DB.
     * @param mcqQuestion details
     * @throws SQLException details
     */
    public void create(MCQQuestion mcqQuestion) throws SQLException {
        Question question = new Question(mcqQuestion.getId(),mcqQuestion.getQuestion(),mcqQuestion.getTopics(),mcqQuestion.getDifficulty());
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        questionDBDAO.create(question);

        // Adding the MCQChoices for the question to the CHOICES table in the database.
        MCQChoicesDBDAO mcqChoicesDBDAO = new MCQChoicesDBDAO();
        List<MCQChoice> choices = mcqQuestion.getChoiceList();
        for (MCQChoice choice: choices) {
            mcqChoicesDBDAO.create(choice, question);
        }
    }

    /**
     * Deletes an MCQQuestion from DB.
     * @param question details
     * @throws SQLException details
     */

    public void delete(Question question) throws SQLException {
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        questionDBDAO.delete(question);

        MCQChoicesDBDAO mcqChoicesDBDAO = new MCQChoicesDBDAO();
        mcqChoicesDBDAO.deleteByQuestionID(question);
    }
}
