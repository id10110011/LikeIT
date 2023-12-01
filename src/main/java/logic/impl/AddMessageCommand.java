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

public class AddMessageCommand implements ICommand {
    private static final Logger log = Logger.getLogger(AddMessageCommand.class);
    private final MessageDAO messageDAO = new JDBCMessageDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String text = request.getParameter("text");
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        Message message = new Message(text, 0, user.getId(), questionId);
        try {
            messageDAO.addMessage(message);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return new GetQuestionsCommand().execute(request);
    }
}
