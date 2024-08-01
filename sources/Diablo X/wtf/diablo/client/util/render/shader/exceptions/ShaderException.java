package wtf.diablo.client.util.render.shader.exceptions;

public class ShaderException extends RuntimeException {
    public ShaderException(String error, Throwable throwable) {
        super(error, throwable);
    }

    public ShaderException(String error) {
        super(error);
    }
}