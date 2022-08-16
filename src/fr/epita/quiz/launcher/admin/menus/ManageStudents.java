package fr.epita.quiz.launcher.admin.menus;

import fr.epita.quiz.services.StudentService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class ManageStudents {
    public void Menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();

        String userResponse = "";
        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Show all students");
            System.out.println("2. Create student");
            System.out.println("3. Delete student");
            System.out.println("0. Back");
            System.out.println("type your choice (1|2|3|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    studentService.ShowAllStudents();
                    break;
                case "2":
                    studentService.CreateStudent();
                    break;
                case "3":
                    studentService.DeleteStudent();
                    break;
                case "0":
                    //back
                    break;
                default:
                    System.out.println("invalid option, retry");
                    break;
            }
        }
//        scanner.close();
    }
}
