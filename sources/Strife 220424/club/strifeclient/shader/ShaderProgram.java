package club.strifeclient.shader;

import club.strifeclient.util.misc.MinecraftUtil;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram extends MinecraftUtil {

    @Getter
    private final int program;
    private final String vertexName, fragmentName;

    public ShaderProgram(String fragmentName) {
        this("vertex/vertex.vert", fragmentName);
    }

    public ShaderProgram(String vertexName, String fragmentName) {
        this.vertexName = vertexName;
        this.fragmentName = fragmentName;

        int program = glCreateProgram();

        final String vertexSource = readShader(vertexName);
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexSource);
        glCompileShader(vertexShader);

        final String fragmentSource = readShader(fragmentName);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentSource);
        glCompileShader(fragmentShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(vertexShader, 4096));
            throw new IllegalStateException(String.format("Vertex Shader (%s) failed to compile!", GL_VERTEX_SHADER));
        }

        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(fragmentShader, 4096));
            throw new IllegalStateException(String.format("Fragment Shader (%s) failed to compile!", GL_FRAGMENT_SHADER));
        }

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        this.program = program;
    }

    private static String readShader(String fileName) {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(String.format("assets/minecraft/strife/shaders/%s", fileName)));
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void renderCanvas() {
        if (mc.gameSettings.ofFastRender) return;
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_BLEND);
        glBegin(GL_QUADS);
        renderTex();
        glEnd();
        glDisable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
    }

    public void renderTex() {
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, 1);
        glTexCoord2f(1, 0);
        glVertex2f(1, 1);
        glTexCoord2f(1, 1);
        glVertex2f(1, 0);
    }

    public void init() {
        glUseProgram(this.program);
    }

    public void uninit() {
        glUseProgram(0);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(this.program, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = glGetUniformLocation(program, name);
        if (args.length > 1) {
            if (args.length > 2) {
                if (args.length > 3) glUniform4f(loc, args[0], args[1], args[2], args[3]);
                else glUniform3f(loc, args[0], args[1], args[2]);
            } else glUniform2f(loc, args[0], args[1]);
        } else glUniform1f(loc, args[0]);
    }

    public void setUniformi(String name, int... args) {
        int loc = glGetUniformLocation(program, name);
        if (args.length > 1) glUniform2i(loc, args[0], args[1]);
        else glUniform1i(loc, args[0]);
    }

    @Override
    public String toString() {
        return "Shader{" +
                "program=" + program +
                ", vertex='" + vertexName + '\'' +
                ", fragment='" + fragmentName + '\'' +
                '}';
    }
}