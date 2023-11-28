package logic.impl;

import controller.JspPageName;
import dao.QuestionDAO;
import dao.impl.JDBCQuestionDAO;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;


public class EditQuestionCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(EditQuestionCommand.class);
    private final QuestionDAO questionDAO = new JDBCQuestionDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int questionId = Integer.parseInt(request.getParameter("q_id"));
        String text = request.getParameter("q_text");
        try {
            questionDAO.updateQuestion(questionId, text);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            logger.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.QUESTION_PAGE;
    }
}
