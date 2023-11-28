package dao.impl;

import dao.QuestionDAO;
import entities.Message;
import entities.Question;
import pool.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCQuestionDAO implements QuestionDAO {
    private final BasicConnectionPool pool = BasicConnectionPool.getInstance();

    @Override
    public Question getQuestion(int questionId) {
        Connection connection = pool.getConnection();
        Question question = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM question WHERE q_id = ?"
            );
            preparedStatement.setInt(1, questionId);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("q_id");
                final String text = result.getString("q_text");
                final int userId = result.getInt("q_userId");
                question = new Question(id, text, userId);
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return question;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM question"
            );
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("q_id");
                final String text = result.getString("q_text");
                final int userId = result.getInt("q_userId");
                questions.add(new Question(id, text, userId));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return questions;
    }

    @Override
    public List<Question> getQuestionsByUserId(int userId) {
        List<Question> questions = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM question WHERE q_userId = ?"
            );
            preparedStatement.setInt(1, userId);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("q_id");
                final String text = result.getString("q_text");
                questions.add(new Question(id, text, userId));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return questions;
    }

    @Override
    public void updateQuestion(int q_id, String text) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE question SET q_text = ? WHERE q_id = ?"
            );
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, q_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect question id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addQuestion(Question question) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO question (q_text, q_userId) VALUES(?, ?)"
            );
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getUserId());

            preparedStatement.executeUpdate();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            pool.releaseConnection(connection);
            throw new RuntimeException("Error insert into question");
        }
    }

    @Override
    public void removeQuestion(int m_id) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM question WHERE m_id = ?"
            );
            preparedStatement.setInt(1, m_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect question id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }
}
