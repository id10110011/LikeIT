package logic.impl;

import controller.JspPageName;
import dao.MessageDAO;
import dao.impl.JDBCMessageDAO;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class RateMessageCommand implements ICommand {
    private static final Logger log = Logger.getLogger(RateMessageCommand.class);
    private final MessageDAO messageDAO = new JDBCMessageDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int rating = Integer.parseInt(request.getParameter("m_rating"));
        int messageId = Integer.parseInt(request.getParameter("m_id"));
        User user = (User) request.getSession().getAttribute("user");
        try {
            messageDAO.rateMessage(messageId, rating);
            Utils.UpdateUserRating(user.getId());
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.QUESTION_PAGE;
    }
}
