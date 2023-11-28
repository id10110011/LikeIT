package logic.impl;

import controller.JspPageName;
import dao.QuestionDAO;
import dao.impl.JDBCQuestionDAO;
import entities.Question;
import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

import java.util.List;

public class GetQuestionsCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetQuestionsCommand.class);
    private final QuestionDAO questionDAO = new JDBCQuestionDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        List<Question> questions;
        try {
            questions = questionDAO.getQuestions();
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        request.setAttribute("questions", questions);
        return JspPageName.QUESTIONS_LIST_PAGE;
    }
}
