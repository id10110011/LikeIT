package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.CommandHelper;
import logic.ICommand;
import logic.impl.ChangeLanguageCommand;

import java.io.IOException;

public class Controller extends HttpServlet {

    public Controller() { super(); }

    private static final String JSP_PATH = "/WEB-INF/jsp/%s";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        if (page != null) {
            request.getRequestDispatcher(String.format(JSP_PATH, page)).forward(request, response);
        } else {
            processCommands(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommands(request, response);
    }

    private void processCommands(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(RequestParameterName.COMMAND_NAME);
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        String page;
        try {
            page = command.execute(req);
        } catch (Exception e) {
            page = JspPageName.ERROR_PAGE;
        }
        if (command instanceof ChangeLanguageCommand) {
            resp.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            if (dispatcher != null) {
                dispatcher.forward(req, resp);
            } else {
                errorMessageDirectlyFromResponse(resp);
            }
        }
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}
