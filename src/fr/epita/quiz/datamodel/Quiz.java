package fr.epita.quiz.datamodel;

import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class Quiz {
    private Integer id;
    private String title;
    private List<Question> questions;

    public Quiz(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return title;
    }
}
