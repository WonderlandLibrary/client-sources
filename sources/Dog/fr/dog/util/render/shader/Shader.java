package fr.dog.util.render.shader;


import fr.dog.util.InstanceAccess;
import fr.dog.util.system.FileUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL20;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements InstanceAccess {
    private final int id;
    public final long startTime;

    public Shader(String fragmentShader) {
        int program = glCreateProgram();

        try {
            final int fragmentShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation("dogclient/shaders/" + fragmentShader)).getInputStream(), GL_FRAGMENT_SHADER);
            final int vertexShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation("dogclient/shaders/vertex.vert")).getInputStream(), GL_VERTEX_SHADER);

            glAttachShader(program, fragmentShaderID);
            glAttachShader(program, vertexShaderID);

        } catch (IOException e) {
            e.printStackTrace();
        }

        glLinkProgram(program);

        int status = glGetProgrami(program, GL_LINK_STATUS);

        if (status == 0)
            throw new IllegalStateException("Shader failed to link!");

        this.id = program;
        this.startTime = System.currentTimeMillis();
    }

    public void start() {
        glUseProgram(id);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void setUniform(String name, float... args) {
        int location = glGetUniformLocation(id, name);
        switch (args.length) {
            case 1 -> glUniform1f(location, args[0]);
            case 2 -> glUniform2f(location, args[0], args[1]);
            case 3 -> glUniform3f(location, args[0], args[1], args[2]);
            case 4 -> glUniform4f(location, args[0], args[1], args[2], args[3]);
        }
    }

    public void drawQuads(float x, float y, float width, float height) {
        if (mc.gameSettings.ofFastRender) return;
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    //Whole screen
    public void drawQuads() {
        if (mc.gameSettings.ofFastRender) return;
        ScaledResolution sr = new ScaledResolution(mc);
        float width = (float) sr.getScaledWidth_double();
        float height = (float) sr.getScaledHeight_double();

        glBegin(GL_QUADS);

        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, 0);

        glEnd();
    }

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);

        glShaderSource(shader, FileUtil.readInputStream(inputStream));
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
            throw new IllegalStateException(String.format("Could not compile shader %s!", shaderType));

        return shader;
    }

    public final void setUniformFloat(String name, float... args) {
        int location = glGetUniformLocation(id, name);
        switch (args.length) {
            case 1 -> glUniform1f(location, args[0]);
            case 2 -> glUniform2f(location, args[0], args[1]);
            case 3 -> glUniform3f(location, args[0], args[1], args[2]);
            case 4 -> glUniform4f(location, args[0], args[1], args[2], args[3]);
        }
    }

    public final void setUniformInteger(String name, int... args) {
        int location = glGetUniformLocation(id, name);
        switch (args.length) {
            case 1 -> glUniform1i(location, args[0]);
            case 2 -> glUniform2i(location, args[0], args[1]);
            case 3 -> glUniform3i(location, args[0], args[1], args[2]);
            case 4 -> glUniform4i(location, args[0], args[1], args[2], args[3]);
        }
    }

    public final void setUniformFv(String name, FloatBuffer fb) {
        GL20.glUniform1fv(glGetUniformLocation(id, name), fb);
    }

    public int getUniformf(final String name) {
        return glGetUniformLocation(id, name);
    }

}