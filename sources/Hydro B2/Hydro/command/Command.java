package Hydro.command;

public class Command {

    private final String name;
    private final String desc;

    private CommandExecutor executor;

    public Command(String name, String desc, CommandExecutor executor){
        this.name = name;
        this.desc = desc;

        this.executor = executor;
    }

    public Command(String name, String desc){
        this(name, desc, null);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }
}
