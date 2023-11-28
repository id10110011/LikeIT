package logic.impl;

import controller.JspPageName;
import dao.QuestionDAO;
import dao.impl.JDBCQuestionDAO;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

public class DeleteQuestionCommand implements ICommand {
    private static final Logger log = Logger.getLogger(DeleteQuestionCommand.class);
    private final QuestionDAO questionDAO = new JDBCQuestionDAO();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int questionId = Integer.parseInt(request.getParameter("question_id"));
        try {
            questionDAO.removeQuestion(questionId);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return new GetQuestionsCommand().execute(request);
    }
}
