package fr.epita.quiz.launcher.admin.menus;

import fr.epita.quiz.services.AnswersService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class ManageAnswers {
    public void Menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AnswersService answersService = new AnswersService();

        String userResponse = "";
        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Show all answers");
            System.out.println("2. Delete answers");
            System.out.println("0. Back");
            System.out.println("type your choice (1|2|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    answersService.ShowAllAnswers();
                    break;
                case "2":
                    answersService.DeleteAnswers();
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
