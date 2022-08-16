package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Admin;
import fr.epita.quiz.services.data.dao.AdminDBDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */

public class AdminService {
    public void ShowAllAdmins() throws SQLException {
        AdminDBDAO adminDBDAO = new AdminDBDAO();
        List<Admin> admins = adminDBDAO.readAll();

        if (admins.size() < 1) {
            System.out.println("There aren't any admins!");
            return;
        }

        System.out.println("Quiz Answers List");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    public void CreateAdmin() {
        Scanner scanner = new Scanner(System.in);
        AdminDBDAO adminDBDAO = new AdminDBDAO();

        System.out.println("Enter Admin Name: ");
        String adminName = scanner.nextLine();
        System.out.println("Enter Admin Password: ");
        String adminPassword = scanner.nextLine();
        Admin newAdmin = new Admin(0, adminName, adminPassword);
        try {
            adminDBDAO.create(newAdmin);
            System.out.println("Admin " + adminName +" was created Successfully!");
        } catch (Exception exception) {
            System.out.println("Error: There was problem creating admin!");
            System.out.println(exception);
        }

    }

    public void DeleteAdmin() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AdminDBDAO adminDBDAO = new AdminDBDAO();
        List<Admin> admins = adminDBDAO.readAll();

        if (admins.size() < 1) {
            System.out.println("There aren't any admins to delete!");
            return;
        }

        int count = 1;
        for (Admin admin : admins) {
            System.out.println(count + ". " + admin);
            count++;
        }
        System.out.println("Which admin do you want to delete? Enter 0 to cancel");
        try {
            Integer targetAdmin = Integer.parseInt(scanner.nextLine());
            if (targetAdmin >= 1) {
                adminDBDAO.delete(admins.get(targetAdmin - 1));
            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem deleting admin!");
            System.out.println(exception);
        }
    }
}
