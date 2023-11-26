package entities;

import java.util.List;

public class Question {
    private final int id;
    private String text;
    private List<Message> answers;

    public Question(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Message> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Message> answers) {
        this.answers = answers;
    }
}
