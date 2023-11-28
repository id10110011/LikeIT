package dao;

import entities.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> getMessagesByUserId(int userId);
    List<Message> getMessagesByQuestionId(int questionId);
    void rateMessage(int m_id, int m_rating);
    void addMessage(Message message);
}
