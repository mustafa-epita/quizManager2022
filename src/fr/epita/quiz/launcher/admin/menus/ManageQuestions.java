package fr.epita.quiz.launcher.admin.menus;

import fr.epita.quiz.services.QuestionService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class ManageQuestions {
    public void Menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuestionService questionService = new QuestionService();

        String userResponse = "";
        while (!"0".equals(userResponse)) {
            System.out.println("What operation would you like to do?");
            System.out.println("1. Show all questions");
            System.out.println("2. Create question");
            System.out.println("3. Delete question");
            System.out.println("4. Load questions from CSV");
            System.out.println("5. Search questions");
            System.out.println("0. Back");
            System.out.println("type your choice (1|2|3|4|5|0)");

            userResponse = scanner.nextLine();
            switch (userResponse) {
                case "1":
                    questionService.ShowAllQuestions();
                    break;
                case "2":
                    questionService.CreateQuestion();
                    break;
                case "3":
                    questionService.DeleteQuestion();
                    break;
                case "4":
                    questionService.LoadQuestionsFromCSV();
                    break;
                case "5":
                    questionService.SearchQuestions();
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
