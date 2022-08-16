package fr.epita.quiz.datamodel;

import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */

public class Answer {
    private Integer id;
    private Quiz quiz;
    private Student student;
    private List<MCQAnswer> answerList;
    private String grade;

    public Answer(Integer id, Quiz quiz, Student student, List<MCQAnswer> answerList) {
        this.id = id;
        this.quiz = quiz;
        this.student = student;
        this.answerList = answerList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<MCQAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<MCQAnswer> answerList) {
        this.answerList = answerList;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "quiz: " + quiz.getTitle() + ", student: " + student.getName() + ", grade: " + grade;
    }
}
