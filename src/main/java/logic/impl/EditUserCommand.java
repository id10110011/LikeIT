package logic.impl;

import controller.JspPageName;
import dao.UserDAO;
import dao.impl.JDBCUserDAO;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class EditUserCommand implements ICommand {
    private static final Logger log = Logger.getLogger(EditUserCommand.class);
    private final UserDAO userDAO = new JDBCUserDAO();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int age;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            age = Integer.parseInt(request.getParameter("age"));
            userDAO.updateUser(user.getId(), name, surname, age);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Age should be a positive number");
            log.error(e.getMessage());
            return JspPageName.EDIT_USER_PAGE;
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.EDIT_USER_PAGE;
        }
        user.setAge(age);
        user.setName(name);
        user.setSurname(surname);
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("user", user);
        return JspPageName.EDIT_USER_PAGE;
    }
}
