package logic.impl;

import controller.JspPageName;
import dao.UserDAO;
import dao.impl.JDBCUserDAO;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class DeleteUserCommand implements ICommand {
    private static final Logger log = Logger.getLogger(DeleteUserCommand.class);
    private final UserDAO userDAO = new JDBCUserDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        try {
            userDAO.removeUser(userId);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return new GetUsersCommand().execute(request);
    }
}
