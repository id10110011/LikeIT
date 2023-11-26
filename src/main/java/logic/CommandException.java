package logic;

import exception.ProjectException;

public class CommandException extends ProjectException {
    public CommandException(String msg){
        super(msg);
    }
    public CommandException(String msg, Exception e){
        super(msg, e);
    }
}
