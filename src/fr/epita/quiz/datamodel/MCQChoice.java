package fr.epita.quiz.datamodel;

/**
 *
 * @author Mustafa Faris
 *
 */

public class MCQChoice {
    private Integer id;
    private String choice;
    private Boolean valid;

    public MCQChoice(Integer id, String choice, Boolean valid) {
        this.id = id;
        this.choice = choice;
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public String getChoice() {
        return choice;
    }

    public Boolean getValid() {
        return valid;
    }

    @Override
    public String toString() {
        return choice;
    }
}
