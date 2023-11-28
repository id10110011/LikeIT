package logic.impl;

import controller.JspPageName;
import jakarta.servlet.http.HttpServletRequest;
import logic.ICommand;

public class NoSuchCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPageName.ERROR_PAGE;
    }
}
