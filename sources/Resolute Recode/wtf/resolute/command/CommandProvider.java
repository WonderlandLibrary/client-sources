package wtf.resolute.command;

public interface CommandProvider {
    Command command(String alias);
}
