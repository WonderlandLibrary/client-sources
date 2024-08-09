package fun.ellant.command;

public interface ParametersFactory {
    Parameters createParameters(String message, String delimiter);
}
