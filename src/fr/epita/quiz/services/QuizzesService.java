package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.services.data.dao.QuizDBDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class QuizzesService {
    public void ShowAllQuizzes() throws SQLException {
        QuizDBDAO quizDBDAO = new QuizDBDAO();
        List<Quiz> quizzes = quizDBDAO.readAll();

        if (quizzes.size() < 1) {
            System.out.println("There aren't any quizzes!");
            return;
        }

        System.out.println("Quizzes List");
        for (Quiz quiz: quizzes) {
            System.out.println(quiz);
        }
    }

    public void CreateQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the quiz title?");
        String quizTitle = scanner.nextLine();
        System.out.println("What topic to cover?");
        String[] quizTopics = scanner.nextLine().split(",");
        System.out.println("What difficulty? ");
        Integer quizDifficulty = Integer.parseInt(scanner.nextLine());
        Quiz newQuiz = new Quiz(0, quizTitle);

        try {
            QuizDBDAO quizDBDAO = new QuizDBDAO();
            quizDBDAO.create(newQuiz, quizTopics, quizDifficulty);
            System.out.println("Quiz " + quizTitle +" was created Successfully!");
        } catch (Exception exception) {
            System.out.println("Error: There was problem creating quiz!");
            System.out.println(exception);
        }
    }

    public void ViewQuiz() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuizDBDAO quizDBDAO = new QuizDBDAO();
        List<Quiz> quizzes = quizDBDAO.readAll();

        if (quizzes.size() < 1) {
            System.out.println("There aren't any quizzes to view!");
            return;
        }

        int countView = 1;
        for (Quiz quiz: quizzes) {
            System.out.println(countView + ". " + quiz);
            countView++;
        }
        System.out.println("Which quiz do you want to view? Enter 0 to cancel");
        try {
            Integer selectedQuiz = Integer.parseInt(scanner.nextLine());
            if (selectedQuiz >= 1) {
                List<MCQQuestion> quizQuestions = quizDBDAO.readQuiz(quizzes.get(selectedQuiz - 1));
                for (MCQQuestion quizQuestion: quizQuestions) {
                    System.out.println(quizQuestion);
                }
            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem viewing quiz!");
            System.out.println(exception);
        }

    }

    public void DeleteQuiz() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuizDBDAO quizDBDAO = new QuizDBDAO();
        List<Quiz> quizzes = quizDBDAO.readAll();

        if (quizzes.size() < 1) {
            System.out.println("There aren't any quizzes to delete!");
            return;
        }

        int count = 1;
        for (Quiz quiz: quizzes) {
            System.out.println(count + ". " + quiz);
            count++;
        }
        System.out.println("Which quiz do you want to delete? Enter 0 to cancel");
        try {
            Integer targetQuiz = Integer.parseInt(scanner.nextLine());
            if (targetQuiz >= 1 && targetQuiz <= count) {
                quizDBDAO.delete(quizzes.get(targetQuiz - 1));
            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem deleting quiz!");
            System.out.println(exception);
        }
    }
}
