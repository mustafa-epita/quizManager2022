package fr.epita.quiz.datamodel;

import java.util.List;

/**
 *
 * @author Mustafa Faris
 *
 */
public class MCQQuestion extends Question {

    private List<MCQChoice> choiceList;

    public MCQQuestion(Integer id, String question, String[] topics, Integer difficulty, List<MCQChoice> choiceList) {
        super(id, question, topics, difficulty);
        this.choiceList = choiceList;
    }

    public List<MCQChoice> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<MCQChoice> choiceList) {
        this.choiceList = choiceList;
    }


    private String renderChoices(List<MCQChoice> choiceList) {
        String choiceText = "";
        int count = 1;
        for (MCQChoice choice: choiceList) {
            choiceText = choiceText + count +  ". " + choice.getChoice() + "\n";
            count++;
        }
        return choiceText;
    }

    @Override
    public String toString() {
        return  this.getQuestion() + "\n" +
                renderChoices(choiceList);
    }
}
