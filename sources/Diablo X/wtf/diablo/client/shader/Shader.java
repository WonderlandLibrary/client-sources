package wtf.diablo.client.shader;

public interface Shader {

    /**
     * Loads the shader.
     */
    void useProgram();

    /**
     * Unloads the shader.
     */
    void unUseProgram();

}
