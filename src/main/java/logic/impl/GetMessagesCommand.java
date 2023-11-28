package logic.impl;

import controller.JspPageName;
import dao.MessageDAO;
import dao.impl.JDBCMessageDAO;
import entities.Message;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

import java.util.List;

public class GetMessagesCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetMessagesCommand.class);
    private final MessageDAO messageDAO = new JDBCMessageDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        int userId = user.getId();
        List<Message> messages;
        try {
            messages = messageDAO.getMessagesByUserId(userId);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        request.setAttribute("messages", messages);
        return JspPageName.MESSAGES_LIST_PAGE;
    }
}
