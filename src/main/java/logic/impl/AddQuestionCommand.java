package logic.impl;

import controller.JspPageName;
import dao.QuestionDAO;
import dao.impl.JDBCQuestionDAO;
import entities.Message;
import entities.Question;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class AddQuestionCommand implements ICommand {
    private static final Logger log = Logger.getLogger(AddQuestionCommand.class);
    private final QuestionDAO questionDAO = new JDBCQuestionDAO();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String text = request.getParameter("text");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        Question question = new Question(text, user.getId());
        try {
            questionDAO.addQuestion(question);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return new GetQuestionsCommand().execute(request);
    }
}
