package fr.epita.quiz.services.data.dao;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.MCQQuestion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class MCQQuestionCSVDAO {
    private File file =  new File("./resources/questions.csv");

    /**
     * Returns a list of all MCQQuestions from a CSV file.
     * @return MCQQuestions details
     * @throws IOException details
     */

    public List<MCQQuestion> readAll() throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        lines.remove(0);
        List<MCQQuestion> mcqQuestions = new ArrayList<>();

        int id = 1;
        for (String line : lines) {
            String[] lineParts = line.split(";");
            String questionText = lineParts[0];
            int answer = Integer.parseInt(lineParts[5]);
            List<MCQChoice> choices = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                MCQChoice choice = new MCQChoice(0, lineParts[i], answer == i);
                choices.add(choice);
            }
            String[] topics = lineParts[6].split(",");
            Integer difficulty = Integer.parseInt(lineParts[7]);
            MCQQuestion mcqQuestion = new MCQQuestion(id, questionText, topics, difficulty, choices);
            mcqQuestions.add(mcqQuestion);
            id++;
        }

        return mcqQuestions;
    }
}

