package im.expensive.command;

public interface Command {
    void execute(Parameters parameters);

    String name();

    String description();
}
