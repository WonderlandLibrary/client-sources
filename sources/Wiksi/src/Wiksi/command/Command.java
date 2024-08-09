package src.Wiksi.command;

public interface Command {
    void execute(Parameters parameters);

    String name();

    String description();
}
