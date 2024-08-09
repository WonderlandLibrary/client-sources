package fun.ellant.command;

public interface Command {
    void execute(Parameters parameters);

    String name();

    String description();
}
