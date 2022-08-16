package fr.epita.quiz.datamodel;

/**
 *
 * @author Mustafa Faris
 *
 */

public class MCQAnswer {
    private MCQChoice choice;
    private Quiz quiz;
    private Student student;

    public MCQAnswer(MCQChoice choice, Quiz quiz, Student student) {
        this.choice = choice;
        this.quiz = quiz;
        this.student = student;
    }

    public MCQChoice getChoice() {
        return choice;
    }

    public void setChoice(MCQChoice choice) {
        this.choice = choice;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
