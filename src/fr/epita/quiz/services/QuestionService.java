package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.data.dao.MCQQuestionCSVDAO;
import fr.epita.quiz.services.data.dao.MCQQuestionsDBDAO;
import fr.epita.quiz.services.data.dao.QuestionDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mustafa Faris
 *
 */
public class QuestionService {
    public void ShowAllQuestions() throws SQLException {
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        List<Question> questions = questionDBDAO.readAll();

        if (questions.size() < 1) {
            System.out.println("There aren't any questions!");
            return;
        }

        System.out.println("Questions List");
        for (Question question : questions) {
            System.out.println(question);
        }
    }
    public void CreateQuestion() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        MCQQuestionsDBDAO mcqQuestionsDBDAO = new MCQQuestionsDBDAO();

        System.out.println("Enter Question: ");
        String questionText = scanner.nextLine();
        System.out.println("What topics does it cover? (e.g. history, math, sport) ");
        String[] topics = scanner.nextLine().split(",");
        System.out.println("What's the difficulty? type your choice (1|2|3)");
        Integer difficulty = Integer.parseInt(scanner.nextLine());

        System.out.println("How Many Choices? ");
        int choicesCount = Integer.parseInt(scanner.nextLine());
        List<MCQChoice> choicesList = new ArrayList<>();
        for (int i = 1; i <= choicesCount; i++) {
            System.out.println("Enter Choice " + i + ": ");
            String choiceText = scanner.nextLine();
            System.out.println("Is the choice the valid answer? y/n");
            Boolean isValid = scanner.nextLine() == "y" ? true : false;
            MCQChoice choice = new MCQChoice(0, choiceText, isValid);
            choicesList.add(choice);
        }
        try {
            MCQQuestion newMCQQuestion = new MCQQuestion(0, questionText, topics, difficulty, choicesList);
            mcqQuestionsDBDAO.create(newMCQQuestion);

            System.out.println("MCQ question '" + questionText +"' was created Successfully!");
        } catch (Exception exception) {
            System.out.println("Error: There was problem creating the MCQ question!");
            System.out.println(exception);
        }
    }
    public void DeleteQuestion() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        List<Question> questions = questionDBDAO.readAll();

        if (questions.size() < 1) {
            System.out.println("There aren't any questions to delete!");
            return;
        }

        int count = 1;
        for (Question question : questions) {
            System.out.println(count + ". " + question);
            count++;
        }
        System.out.println("Which question do you want to delete? Enter 0 to cancel");
        try {
            Integer targetQuestion = Integer.parseInt(scanner.nextLine());
            if (targetQuestion >= 1) {
                MCQQuestionsDBDAO mcqQuestionsDBDAO = new MCQQuestionsDBDAO();
                mcqQuestionsDBDAO.delete(questions.get(targetQuestion - 1));
                System.out.println("Question deleted successfully!");

            }
        } catch (Exception exception) {
            System.out.println("Error: There was problem deleting question!");
            System.out.println(exception);
        }
    }
    public void LoadQuestionsFromCSV() {
        MCQQuestionsDBDAO mcqQuestionsDBDAO = new MCQQuestionsDBDAO();

        System.out.println("Loading Questions from resources/questions.csv");
        try {
            MCQQuestionCSVDAO mcqQuestionCSVDAO = new MCQQuestionCSVDAO();
            List<MCQQuestion> mcqQuestions = mcqQuestionCSVDAO.readAll();

            for (MCQQuestion mcqQuestion: mcqQuestions) {
                mcqQuestionsDBDAO.create(mcqQuestion);
            }
            System.out.println("Questions loaded successfully from CSV file.");
        } catch (Exception exception) {
            System.out.println("Error: There was problem loading MCQ Questions from CSV file!");
            System.out.println(exception);
        }
    }
    public void SearchQuestions() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        QuestionDBDAO questionDBDAO = new QuestionDBDAO();
        List<Question> questions = questionDBDAO.readAll();

        if (questions.size() < 1) {
            System.out.println("There aren't any questions to search!");
            return;
        }

        String userResponse = "";
        System.out.println("How do you want to search question: ");
        System.out.println("1. By topic");
        System.out.println("2. By difficulty");
        System.out.println("0. Back");
        System.out.println("type your choice (1|2|0)");
        userResponse = scanner.nextLine();

        switch (userResponse) {
            case "1":
                System.out.println("Type the topic you want to searching for");
                String queryTopic = scanner.nextLine();
                for (Question question: questions) {
                    Boolean hasTopic = Arrays.stream(question.getTopics()).anyMatch(topic -> topic.trim().equalsIgnoreCase(queryTopic.trim()));
                    if (hasTopic) {
                        System.out.println(question);
                    }
                }
                break;
            case "2":
                System.out.println("Type the difficulty level you want to searching for: (1|2|3)");
                int difficulty = Integer.parseInt(scanner.nextLine());
                for (Question question: questions) {
                    Boolean sameDiff = question.getDifficulty() == difficulty;
                    if (sameDiff) {
                        System.out.println(question);
                    }
                }
                break;
            case "0":
                //back
                break;
            default:
                System.out.println("invalid option, retry");
                break;
        }
    }
}
