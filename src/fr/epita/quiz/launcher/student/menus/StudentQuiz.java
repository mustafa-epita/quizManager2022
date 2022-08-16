package fr.epita.quiz.launcher.student.menus;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.services.AnswersService;
import fr.epita.quiz.services.data.dao.AnswerDBDAO;
import fr.epita.quiz.services.data.dao.QuizDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentQuiz {
    public void Menu(Student student) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuizDBDAO quizDBDAO = new QuizDBDAO();
        List<Quiz> quizzes = quizDBDAO.readAll();

        int countView = 1;
        for (Quiz quiz: quizzes) {
            System.out.println(countView + ". " + quiz);
            countView++;
        }
        if (quizzes.size() < 1) {
            System.out.println("There aren't any quizzes to take!");
            return;
        }
        System.out.println("Which quiz do you want to solve? Enter 0 to cancel");
        Integer selectedQuiz = Integer.parseInt(scanner.nextLine());
        if (selectedQuiz >= 1) {
            List<MCQQuestion> quizQuestions = quizDBDAO.readQuiz(quizzes.get(selectedQuiz - 1));
            QuizView(quizQuestions, quizzes.get(selectedQuiz - 1), student);
        }
    }

    public void QuizView(List<MCQQuestion> quizQuestions, Quiz quiz, Student student) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<MCQAnswer> answerList = new ArrayList<>();
        for (MCQQuestion quizQuestion: quizQuestions) {
            Integer selectedAnswer = 0;
            while (selectedAnswer <= 0 || selectedAnswer > 4) {
                System.out.println(quizQuestion);
                System.out.println("Enter your answer: (1|2|3|4)");
                selectedAnswer = Integer.parseInt(scanner.nextLine());
                if (selectedAnswer <= 0 || selectedAnswer > 4) {
                    System.out.println("Error: Invalid Choice!\n");
                } else {
                    MCQAnswer mcqAnswer = new MCQAnswer(quizQuestion.getChoiceList().get(selectedAnswer -1), quiz, student);
                    answerList.add(mcqAnswer);
                }
            }
        }
        Answer answer = new Answer(0, quiz, student, answerList);
        AnswersService answersService = new AnswersService();
        String grade = answersService.gradeAnswers(answer);
        answer.setGrade(grade);
        AnswerDBDAO answerDBDAO = new AnswerDBDAO();
        answerDBDAO.create(answer);
        System.out.println("Congratulations, you have finished the quiz! Your score is:  " + grade + "\n");
    }
}
