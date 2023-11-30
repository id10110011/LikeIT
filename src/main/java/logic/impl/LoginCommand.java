package logic.impl;

import controller.JspPageName;
import dao.UserDAO;
import dao.impl.JDBCUserDAO;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class LoginCommand implements ICommand {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private final UserDAO userDao = new JDBCUserDAO();
    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (password.length() < 6) {
            request.setAttribute("error", "Password should be at least 6 characters");
            return JspPageName.LOGIN_PAGE;
        }
        User user;
        try {
            user = userDao.authorization(email, password);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(email + " got error " + e.getMessage().toLowerCase());
            return JspPageName.LOGIN_PAGE;
        }
        request.getSession().setAttribute("user", user);
        request.setAttribute("login", true);
        return JspPageName.LOGIN_PAGE;
    }
}
