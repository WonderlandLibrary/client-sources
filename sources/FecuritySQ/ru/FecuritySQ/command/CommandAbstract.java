package ru.FecuritySQ.command;


import ru.FecuritySQ.utils.RenderUtil;

public abstract class CommandAbstract  {


    public String name;
    public String description;

    public CommandAbstract() {
        name = getClass().getAnnotation(Command.class).name();
        description = getClass().getAnnotation(Command.class).description();
    }

    public abstract void error();

    public abstract void execute(String[] args) throws Exception;

    public void sendMessage(String message) {
        RenderUtil.addChatMessage(message);
    }



}
