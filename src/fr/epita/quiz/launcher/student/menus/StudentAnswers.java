package fr.epita.quiz.launcher.student.menus;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.data.dao.AnswerDBDAO;

import java.sql.SQLException;
import java.util.List;

public class StudentAnswers {
    public void Menu(Student student) throws SQLException {
        AnswerDBDAO answerDBDAO = new AnswerDBDAO();
        List<Answer> answers = answerDBDAO.readByStudentID(student.getId());

        int countView = 1;
        System.out.println("Answers Lists");
        for (Answer answer: answers) {
            System.out.println(countView + ". " + answer);
            countView++;
        }
        System.out.println("\n");
    }
}
