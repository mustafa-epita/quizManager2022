package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.MCQAnswer;
import fr.epita.quiz.services.data.dao.AnswerDBDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */

public class AnswersService {
    public void ShowAllAnswers() throws SQLException {
        AnswerDBDAO answerDBDAO = new AnswerDBDAO();
        List<Answer> answers = answerDBDAO.readAll();

        if (answers.size() < 1) {
            System.out.println("There aren't any answers!");
            return;
        }

        System.out.println("Quiz Answers List");
        for (Answer answer : answers) {
            System.out.println(answer);
        }
    }
    public void DeleteAnswers() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AnswerDBDAO answerDBDAO = new AnswerDBDAO();
        List<Answer> answers = answerDBDAO.readAll();

        if (answers.size() < 1) {
            System.out.println("There aren't any answers to delete!");
            return;
        }

        int count = 1;
        for (Answer answer : answers) {
            System.out.println(count + ". " + answer);
            count++;
        }
        System.out.println("Which answer do you want to delete? Enter 0 to cancel");
        try {
            Integer targetAnswer = Integer.parseInt(scanner.nextLine());
            if (targetAnswer >= 1) {
                answerDBDAO.delete(answers.get(targetAnswer - 1));
            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem deleting answers!");
            System.out.println(exception);
        }
    }

    public String gradeAnswers(Answer answer) {
        List<MCQAnswer> answerList = answer.getAnswerList();
        int totalGrade = answerList.size();
        int grade = 0;
        for (MCQAnswer mcqAnswer: answerList) {
            if(mcqAnswer.getChoice().getValid()) {
                grade++;
            }
        }
        return totalGrade + "/" + grade;
    }
}
