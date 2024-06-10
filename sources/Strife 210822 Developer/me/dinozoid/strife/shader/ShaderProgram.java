package me.dinozoid.strife.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ShaderProgram {

    private final int programID;
    private final String vertexName, fragmentName;

    private final int vertexShaderID, fragmentShaderID;

    public ShaderProgram(String vertexName, String fragmentName) {
        this.vertexName = vertexName;
        this.fragmentName = fragmentName;

        final String vertexSource = readShader(vertexName);
        vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderID, vertexSource);
        GL20.glCompileShader(vertexShaderID);

        final String fragmentSource = readShader(fragmentName);
        fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderID, fragmentSource);
        GL20.glCompileShader(fragmentShaderID);

        final boolean compiled = GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_TRUE;
        this.programID = compiled ? GL20.glCreateProgram() : 0;

        if (!compiled) {
            final String shaderLog = GL20.glGetShaderInfoLog(fragmentShaderID, 2048);
            System.err.printf("%s: Failed to compile shader source. Message\n%s%n", this, shaderLog);
            return;
        }

        GL20.glAttachShader(this.programID, vertexShaderID);
        GL20.glAttachShader(this.programID, fragmentShaderID);
        GL20.glLinkProgram(this.programID);
    }

    public ShaderProgram(String fragmentName) {
        this("vertex/vertex.vert", fragmentName);
    }

    public void renderCanvas(float width, float height) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2d(0, 0);

        glTexCoord2f(0, 0);
        glVertex2d(0, height);

        glVertex2d(width, height);

        glTexCoord2f(1, 1);
        glVertex2d(width, 0);
        glEnd();
    }

    private static String readShader(String fileName) {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(String.format("assets/minecraft/strife/shaders/%s", fileName)));
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public void deleteShaderProgram() {
        GL20.glDeleteShader(this.vertexShaderID);
        GL20.glDeleteShader(this.fragmentShaderID);
        GL20.glDeleteProgram(this.programID);
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void uninit() {
        GL20.glUseProgram(0);
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public float getCurrentUniformValue(String name, FloatBuffer buffer) {
        GL20.glGetUniform(this.programID, this.getUniform(name), buffer);
        return buffer.get(0);
    }

    public int getCurrentUniformValue(String name, IntBuffer buffer) {
        this.init();
        GL20.glGetUniform(this.programID, this.getUniform(name), buffer);
        this.uninit();
        return buffer.get(0);
    }


    public int getProgramID() {
        return programID;
    }

    @Override
    public String toString() {
        return "ShaderProgram{" +
                "programID=" + programID +
                ", vertexName='" + vertexName + '\'' +
                ", fragmentName='" + fragmentName + '\'' +
                '}';
    }
}