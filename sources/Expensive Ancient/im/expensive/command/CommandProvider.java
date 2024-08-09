package im.expensive.command;

public interface CommandProvider {
    Command command(String alias);
}
