package im.expensive.command;

public interface ParametersFactory {
    Parameters createParameters(String message, String delimiter);
}
