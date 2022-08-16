package fr.epita.quiz.launcher.admin;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.launcher.admin.menus.*;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class AdminMenu {

    public void MainMenu(Admin admin) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String userResponse = "";

        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Manage students");
            System.out.println("2. Manage quizzes");
            System.out.println("3. Manage questions");
            System.out.println("4. Manage answers");
            System.out.println("5. Manage admins");
            System.out.println("0. Quit");
            System.out.println("type your choice (1|2|3|4|5|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    ManageStudents manageStudents = new ManageStudents();
                    manageStudents.Menu();
                    break;
                case "2":
                    ManageQuizzes manageQuizzes = new ManageQuizzes();
                    manageQuizzes.Menu();
                    break;
                case "3":
                    ManageQuestions manageQuestions = new ManageQuestions();
                    manageQuestions.Menu();
                    break;
                case "4":
                    ManageAnswers manageAnswers = new ManageAnswers();
                    manageAnswers.Menu();
                    break;
                case "5":
                    ManageAdmins manageAdmins = new ManageAdmins();
                    manageAdmins.Menu();
                    break;
                case "0":
                    //quit
                    break;
                default:
                    System.out.println("invalid option, retry");
                    break;
            }
        }
        scanner.close();
    }
}
