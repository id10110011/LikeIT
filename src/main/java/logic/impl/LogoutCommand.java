package logic.impl;

import controller.JspPageName;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;

public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return JspPageName.LOGIN_PAGE;
    }
}
