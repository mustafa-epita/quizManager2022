package fr.epita.quiz.launcher.student;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.launcher.student.menus.StudentAnswers;
import fr.epita.quiz.launcher.student.menus.StudentQuiz;
import fr.epita.quiz.services.AnswersService;
import fr.epita.quiz.services.data.dao.AnswerDBDAO;
import fr.epita.quiz.services.data.dao.QuizDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class StudentMenu {
    public void MainMenu(Student student) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String userResponse = "";

        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Solve a quiz");
            System.out.println("2. Check Grades");
            System.out.println("0. Quit");
            System.out.println("type your choice (1|2|3|4|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    StudentQuiz studentQuiz = new StudentQuiz();
                    studentQuiz.Menu(student);
                    break;
                case "2":
                    StudentAnswers studentAnswers = new StudentAnswers();
                    studentAnswers.Menu(student);
                    break;
                case "0":
                    //quit
                    break;
                default:
                    System.out.println("invalid option, retry");
                    break;
            }
        }
    }
}
