package entities;

import java.util.List;

public class Question {
    private int id;
    private String text;
    private final int userId;
    private List<Message> answers;

    public Question(int id, String text, int userId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public Question(String text, int userId) {
        this.text = text;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }
}
