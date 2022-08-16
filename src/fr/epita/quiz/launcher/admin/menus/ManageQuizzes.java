package fr.epita.quiz.launcher.admin.menus;

import fr.epita.quiz.services.QuizzesService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class ManageQuizzes {
    public void Menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuizzesService quizzesService = new QuizzesService();

        String userResponse = "";
        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Show all quizzes");
            System.out.println("2. Create quiz");
            System.out.println("3. View quiz");
            System.out.println("4. Delete quiz");
            System.out.println("0. Back");
            System.out.println("type your choice (1|2|3|4|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    quizzesService.ShowAllQuizzes();
                    break;
                case "2":
                    quizzesService.CreateQuiz();
                    break;
                case "3":
                    quizzesService.ViewQuiz();
                    break;
                case "4":
                    quizzesService.DeleteQuiz();
                    break;
                case "0":
                    //back
                    break;
                default:
                    System.out.println("invalid option, retry");
                    break;
            }
        }
        scanner.close();
    }
}
