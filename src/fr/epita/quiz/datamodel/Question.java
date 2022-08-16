package fr.epita.quiz.datamodel;

import java.util.Arrays;

/**
 *
 * @author Mustafa Faris
 *
 */

public class Question {
    private Integer id;
    private String question;
    private String[] topics;
    private Integer difficulty;

    public Question(Integer id, String question, String[] topics, Integer difficulty) {
        this.id = id;
        this.question = question;
        this.topics = topics;
        this.difficulty = difficulty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getTopics() {
        return topics;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return  "id:" + id +
                ", question:'" + question + '\'' +
                ", topics:" + Arrays.toString(topics) +
                ", difficulty:" + difficulty;
    }
}
