package entities;

public class Message {
    private int id;
    private String text;
    private int rating;
    private final int authorId;
    private final int questionId;

    public Message(int id, String text, int rating, int authorId, int questionId) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.authorId = authorId;
        this.questionId = questionId;
    }

    public Message(String text, int rating, int authorId, int questionId) {
        this.text = text;
        this.rating = rating;
        this.authorId = authorId;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getQuestionId() {
        return questionId;
    }
}
