package im.expensive.utils.shader.exception;

public class UndefinedShader extends Throwable {

    private final String shader;

    @Override
    public String getMessage() {
        return shader;
    }

    public UndefinedShader(String shader) {
        this.shader =  shader;
    }

}
