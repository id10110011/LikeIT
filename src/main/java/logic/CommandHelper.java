package logic;

import logic.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private final Map<CommandName, ICommand> commands = new HashMap<>();

    public CommandHelper() {
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTER, new RegisterCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandName.GET_QUESTIONS, new GetQuestionsCommand());
        commands.put(CommandName.GET_QUESTION, new GetQuestionCommand());
        commands.put(CommandName.ADD_QUESTION, new AddQuestionCommand());
        commands.put(CommandName.ADD_MESSAGE, new AddMessageCommand());
        commands.put(CommandName.RATE_MESSAGE, new RateMessageCommand());
        commands.put(CommandName.EDIT_QUESTION, new EditQuestionCommand());
        commands.put(CommandName.GET_MESSAGES, new GetMessagesCommand());
        commands.put(CommandName.DELETE_QUESTION, new DeleteQuestionCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.GET_USERS, new GetUsersCommand());
        commands.put(CommandName.EDIT_USER, new EditUserCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
    }
    public static CommandHelper getInstance() {
        return instance;
    }

    public ICommand getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(CommandName.NO_SUCH_COMMAND);
        }
        CommandName name;
        try {
            name = CommandName.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return commands.get(name);
    }
}