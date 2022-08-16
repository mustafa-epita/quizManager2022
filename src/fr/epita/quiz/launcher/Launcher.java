package fr.epita.quiz.launcher;

import fr.epita.quiz.datamodel.Admin;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.launcher.admin.AdminMenu;
import fr.epita.quiz.launcher.student.StudentMenu;
import fr.epita.quiz.services.data.dao.AdminDBDAO;
import fr.epita.quiz.services.data.dao.StudentDBDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        String userResponse = "";

        // Checking if there are no admins in DB, and creating a default admin if true
        AdminDBDAO adminDBDAO = new AdminDBDAO();
        if (adminDBDAO.readAll().size() < 1) {
            Admin admin = new Admin(1,"admin", "admin");
            adminDBDAO.create(admin);
        }

        while (!"0".equals(userResponse)) {
            System.out.println("Welcome, How do you want to login?");
            System.out.println("1. Admin Login");
            System.out.println("2. Students Login");
            System.out.println("0. Quit");
            System.out.println("type your choice (1|2|3|4|5|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    AdminLogin();
                    break;
                case "2":
                    StudentLogin();
                    break;
                case "0":
                    //quit
                    scanner.close();
                    break;
                default:
                    System.out.println("invalid option, retry");
                    break;
            }
        }
    }

    public static void AdminLogin() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        AdminDBDAO adminDBDAO = new AdminDBDAO();
        Admin admin = adminDBDAO.login(username, password);

        if (admin == null) {
            System.out.println("\nError: Could not authenticate!\n");
            return;
        }

        AdminMenu adminMenu = new AdminMenu();
        adminMenu.MainMenu(admin);
    }

    public static void StudentLogin() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();

        StudentDBDAO studentDBDAO = new StudentDBDAO();
        Student student = studentDBDAO.login(email, name);

        if (student == null) {
            System.out.println("\nError: Could not authenticate!\n");
            return;
        }

        StudentMenu studentMenu = new StudentMenu();
        studentMenu.MainMenu(student);
    }
}
