package logic.impl;

import controller.JspPageName;
import dao.MessageDAO;
import dao.QuestionDAO;
import dao.impl.JDBCMessageDAO;
import dao.impl.JDBCQuestionDAO;
import entities.Message;
import entities.Question;
import jakarta.servlet.http.HttpServletRequest;
import logic.CommandException;
import logic.ICommand;
import org.apache.log4j.Logger;

import java.util.List;

public class GetQuestionCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetUsersCommand.class);
    private final QuestionDAO questionDAO = new JDBCQuestionDAO();
    private final MessageDAO messageDAO = new JDBCMessageDAO();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        List<Message> messages;
        Question question;
        try {
            question = questionDAO.getQuestion(questionId);
            if (question == null) {
                throw new RuntimeException("Question is not found");
            }
            messages = messageDAO.getMessagesByQuestionId(questionId);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        question.setAnswers(messages);
        request.setAttribute("question", question);
        return JspPageName.QUESTION_PAGE;
    }
}
