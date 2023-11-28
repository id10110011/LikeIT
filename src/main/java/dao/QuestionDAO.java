package dao;

import entities.Message;
import entities.Question;

import java.util.List;

public interface QuestionDAO {
    Question getQuestion(int questionId);
    List<Question> getQuestions();
    List<Question> getQuestionsByUserId(int userId);
    void updateQuestion(int q_id, String text);
    void addQuestion(Question question);
    void removeQuestion(int q_id);
}
