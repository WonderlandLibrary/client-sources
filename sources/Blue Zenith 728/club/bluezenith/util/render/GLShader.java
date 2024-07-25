package club.bluezenith.util.render;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * skidded from ketamine
 */
public class GLShader {
    private int program;

    private final Map<String, Integer> uniformLocationMap = new HashMap<>();

    public GLShader(final String vertexSource, final String fragSource) {
        this.program = glCreateProgram();

        // Attach vertex & fragment shader to the program
        glAttachShader(this.program, createShader(vertexSource, GL_VERTEX_SHADER));
        glAttachShader(this.program, createShader(fragSource, GL_FRAGMENT_SHADER));
        // Link the program
        glLinkProgram(this.program);
        // Check if linkage was a success
        final int status = glGetProgrami(this.program, GL_LINK_STATUS);
        // Check is status is a null ptr
        if (status == 0) {
            // Invalidate if error
            this.program = -1;
            throw new Error("Failed to setup a shader. If this has happened before/on startup, " +
                    "consider disabling shader usage in .minecraft/BlueZenith/preferences.json by setting the right values to false.");
        }

        this.setupUniforms();
    }

    private static int createShader(final String source, final int type) {
        final int shader = glCreateShader(type); // Create new shader of passed type (vertex or fragment)
        glShaderSource(shader, source); // Specify the source (the code of the shader)
        glCompileShader(shader);               // Compile the code

        final int status = glGetShaderi(shader, GL_COMPILE_STATUS); // Check if the compilation succeeded

        if (status == 0) { // Equivalent to checking invalid ptr
            return -1;
        }

        return shader;
    }

    public void use() {
        // Use shader program
        glUseProgram(this.program);
        this.updateUniforms();
    }

    public void useAuto() { //Use shader program and apply required transformations to entire screen
        glDisable(GL_CULL_FACE);
        use();
        glBegin(GL_QUADS);
        glVertex2i(-1, -1);
        glVertex2i(-1, 1);
        glVertex2i(1, 1);
        glVertex2i(1, -1);
        glEnd();

        glUseProgram(0);
    }

    public int getProgram() {
        return program;
    }

    public void setupUniforms() {}

    public void updateUniforms() {}

    public void setupUniform(final String uniform) {
        this.uniformLocationMap.put(uniform, glGetUniformLocation(this.program, uniform));
    }

    public int getUniformLocation(final String uniform) {
        Integer a = this.uniformLocationMap.get(uniform);
        if(a == null) {
            System.err.println("null: " + uniform);
        }
        return this.uniformLocationMap.get(uniform);
    }
}
