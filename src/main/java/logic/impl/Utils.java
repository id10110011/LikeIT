package logic.impl;

import dao.MessageDAO;
import dao.UserDAO;
import dao.impl.JDBCMessageDAO;
import dao.impl.JDBCUserDAO;
import entities.Message;

import java.util.List;

public class Utils {
    static MessageDAO messageDAO = new JDBCMessageDAO();
    static UserDAO userDAO = new JDBCUserDAO();
    public static void UpdateUserRating(int userId) throws RuntimeException {
        List<Message> messages = messageDAO.getMessagesByUserId(userId);
        double sumRating = messages.stream().mapToInt(Message::getRating).sum();
        double rating = (double) Math.round(sumRating / messages.size() * 10) / 10;
        userDAO.updateUser(userId, rating);
    }
}
