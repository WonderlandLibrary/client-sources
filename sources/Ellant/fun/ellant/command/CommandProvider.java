package fun.ellant.command;

public interface CommandProvider {
    Command command(String alias);
}
