package logic.impl;

import controller.JspPageName;
import dao.UserDAO;
import dao.impl.JDBCUserDAO;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

import java.util.List;

public class GetUsersCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetUsersCommand.class);
    private final UserDAO userDAO = new JDBCUserDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<User> users;
        try {
            users = userDAO.getUsers();
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        request.setAttribute("users", users);
        return JspPageName.ADMIN_PAGE;
    }
}
