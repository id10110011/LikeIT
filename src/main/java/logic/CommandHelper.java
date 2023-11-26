package logic;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private final Map<CommandName, ICommand> commands = new HashMap<>();
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
