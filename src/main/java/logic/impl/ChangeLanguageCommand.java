package logic.impl;

import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;

public class ChangeLanguageCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String newLang = request.getParameter("language");
        request.getSession().setAttribute("lang", newLang);
        return request.getHeader("Referer");
    }
}
