package wtf.resolute.command;

public interface ParametersFactory {
    Parameters createParameters(String message, String delimiter);
}
