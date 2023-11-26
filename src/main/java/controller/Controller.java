package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Controller() { super(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommands(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommands(req, resp);
    }

    private void processCommands(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getRequestURI().substring(1);
        if (commandName.contains("/")) {
            commandName = parseComplexCommand(commandName);
        }
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        CommandAnswer answer;
        try{
            answer = command.execute(req);
        } catch (Exception e) {
            answer = new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
        if (!answer.isRedirect()) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(answer.getForwardPage());
            if (requestDispatcher != null) {
                requestDispatcher.forward(req, resp);
            } else {
                errorMessageDirectlyFromResponse(resp);
            }
        } else {
            resp.sendRedirect(answer.getRedirectUrl());
        }
    }

    private String parseComplexCommand(String command) {
        if (command.endsWith("/review")) {
            return "review";
        } else if (command.endsWith("/delete") && command.contains("review")) {
            return "delete-review";
        } else if (command.endsWith("/delete") && command.contains("film")) {
            return "delete-film";
        } else {
            return command.substring(0, command.indexOf("/"));
        }
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}
