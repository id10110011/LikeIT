package logic.impl;

import controller.JspPageName;
import dao.UserDAO;
import dao.impl.JDBCUserDAO;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class RegisterCommand implements ICommand {
    private static final Logger log = Logger.getLogger(RegisterCommand.class);
    private final UserDAO userDao = new JDBCUserDAO();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeat-password");
        if (!Objects.equals(password, repeatPassword)) {
            request.setAttribute("error", "Passwords should match");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (password.length() < 6) {
            request.setAttribute("error", "Password should be at least 6 characters");
            return JspPageName.REGISTRATION_PAGE;
        }
        try {
            int age = Integer.parseInt(request.getParameter("age"));
            int userId = userDao.registerUser(name, surname, email, age, hashPassword(password));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Age should be a positive number");
            return JspPageName.REGISTRATION_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(email + " got error " + e.getMessage().toLowerCase());
            return JspPageName.REGISTRATION_PAGE;
        }
        request.setAttribute("message", "Successfully registered, you can login now");
        return JspPageName.REGISTRATION_PAGE;
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }
}
