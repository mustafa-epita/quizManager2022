package fr.epita.quiz.launcher.admin.menus;

import fr.epita.quiz.services.AdminService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class ManageAdmins {
    public void Menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AdminService adminService = new AdminService();
        String userResponse = "";
        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Show all admins");
            System.out.println("2. Create admin");
            System.out.println("3. Delete admin");
            System.out.println("0. Back");
            System.out.println("type your choice (1|2|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    adminService.ShowAllAdmins();
                    break;
                case "2":
                    adminService.CreateAdmin();
                    break;
                case "3":
                    adminService.DeleteAdmin();
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
