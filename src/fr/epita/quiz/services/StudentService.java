package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.data.dao.StudentDBDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */

public class StudentService {
    public void ShowAllStudents() throws SQLException {
        StudentDBDAO studentDBDAO = new StudentDBDAO();
        List<Student> students = studentDBDAO.readAll();

        if (students.size() < 1) {
            System.out.println("There aren't any students!");
            return;
        }

        System.out.println("Students List");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void CreateStudent() {
        StudentDBDAO studentDBDAO = new StudentDBDAO();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Student Name: ");
        String studentName = scanner.nextLine();
        System.out.println("Enter Student Email: ");
        String studentEmail = scanner.nextLine();
        Student newStudent = new Student(studentEmail, studentName);
        try {
            studentDBDAO.create(newStudent);
            System.out.println("Student " + studentName +" was created Successfully!");
        } catch (Exception exception) {
            System.out.println("Error: There was problem creating student!");
            System.out.println(exception);
            scanner.close();
        }
    }

    public void DeleteStudent() throws SQLException {
        StudentDBDAO studentDBDAO = new StudentDBDAO();
        List<Student> students = studentDBDAO.readAll();
        Scanner scanner = new Scanner(System.in);

        if (students.size() < 1) {
            System.out.println("There aren't any students to delete!");
            return;
        }

        int count = 1;
        for (Student student : students) {
            System.out.println(count + ". " + student);
            count++;
        }
        System.out.println("Which student do you want to delete? Enter 0 to cancel");
        try {
            Integer targetStudent = Integer.parseInt(scanner.nextLine());
            if (targetStudent >= 1) {
                studentDBDAO.delete(students.get(targetStudent - 1));
            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem deleting student!");
            System.out.println(exception);
        }
    }
}
