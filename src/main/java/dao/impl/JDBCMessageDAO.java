package dao.impl;

import dao.MessageDAO;
import entities.Message;
import pool.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCMessageDAO implements MessageDAO {
    private final BasicConnectionPool pool = BasicConnectionPool.getInstance();
    @Override
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM message WHERE m_userId = ?"
            );
            preparedStatement.setInt(1, userId);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("m_id");
                final String text = result.getString("m_text");
                final int rating = result.getInt("m_rating");
                final int questionId = result.getInt("m_questionId");
                messages.add(new Message(id, text, rating, userId, questionId));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return messages;
    }

    @Override
    public List<Message> getMessagesByQuestionId(int questionId) {
        List<Message> messages = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM message WHERE m_questionId = ?"
            );
            preparedStatement.setInt(1, questionId);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("m_id");
                final String text = result.getString("m_text");
                final int rating = result.getInt("m_rating");
                final int userId = result.getInt("m_userId");
                messages.add(new Message(id, text, rating, userId, questionId));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return messages;
    }

    @Override
    public void rateMessage(int m_id, int m_rating) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE message SET m_rating = ? WHERE m_id = ?"
            );
            preparedStatement.setInt(1, m_rating);
            preparedStatement.setInt(2, m_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect message id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addMessage(Message message) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO message (m_text, m_rating, m_questionId, m_authorId) VALUES(?, ? ,?, ?)"
            );
            preparedStatement.setString(1, message.getText());
            preparedStatement.setInt(2, message.getRating());
            preparedStatement.setInt(3, message.getQuestionId());
            preparedStatement.setInt(4, message.getAuthorId());

            preparedStatement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            pool.releaseConnection(connection);
            throw new RuntimeException("Error insert into message");
        }
    }
}
