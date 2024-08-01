package wtf.diablo.client.util.render.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import wtf.diablo.client.util.render.shader.exceptions.ShaderException;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public final class Shader {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private final String shaderIdentifier;
    private final int programID;
    private final int vertexID;
    private final int fragmentID;

    private final long initTime = System.currentTimeMillis();

    private final boolean linkedSuccessfully;

    private final Map<String, Integer> uniformLocationMap = new HashMap<>();

    Shader(String identifier, int programID, int vertexID, int fragmentID) {
        this.shaderIdentifier = identifier;
        this.programID = programID;
        this.vertexID = vertexID;
        this.fragmentID = fragmentID;

        glLinkProgram(programID);
        int linkStatus = glGetProgrami(programID, GL_LINK_STATUS);
        if (linkStatus == 0) {
            System.err.println(glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH)));
            throw new ShaderException("Shader linking failed");
        }
        linkedSuccessfully = true;
    }


    protected void setUniform(final String uniform, final int... data) {
        if(!uniformLocationMap.containsKey(uniform)) {
            System.err.println("Attempted to set invalid uniform "+uniform+" in shader "+shaderIdentifier);
            return;
        }
        int size = data.length;
        if (size == 0) {
            System.err.println("Attempted to set uniform "+uniform+" to invalid value in shader "+shaderIdentifier);
        } else if (size == 1) {
            glUniform1i(uniformLocationMap.get(uniform), data[0]);
        } else if (size == 2) {
            glUniform2i(uniformLocationMap.get(uniform), data[0], data[1]);
        } else if (size == 3) {
            glUniform3i(uniformLocationMap.get(uniform), data[0], data[1], data[2]);
        } else if (size == 4) {
            glUniform4i(uniformLocationMap.get(uniform), data[0], data[1], data[2], data[3]);
        } else {
            System.err.println("Attempted to set uniform "+uniform+" to too many values in shader "+shaderIdentifier);
        }
    }

    protected static void drawQuads(float x, float y, float width, float height) {
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

    protected static void drawQuads() {
        if (mc.gameSettings.ofFastRender) return;
        float width = mc.displayWidth;
        float height = mc.displayHeight;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);
        GL11.glEnd();
    }

    protected static void drawQuadsScaled() {
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

    protected static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        }
        return framebuffer;
    }

    public void startShader() throws ShaderException {
        if (!linkedSuccessfully)
            throw new ShaderException("Attempted to use shader " + shaderIdentifier + " however it did not link successfully");
        GL20.glUseProgram(programID);
    }

    public void endShader() {
        GL20.glUseProgram(0);
    }

    public int getTimeElapsed() {
        return (int) (System.currentTimeMillis() - initTime);
    }

    public void getUniforms(final String... uniformNames) {
        for (String uniform : uniformNames) {
            int uniformLocation = glGetUniformLocation(programID, uniform);

            if (uniformLocation == -1) {
                System.err.println("Invalid uniform " + uniform + " in shader " + shaderIdentifier);
                continue;
            }

            uniformLocationMap.put(uniform, uniformLocation);
        }
    }

    public void setUniform(String uniform, float... data) {
        if (!uniformLocationMap.containsKey(uniform)) {
            System.err.println("Attempted to set invalid uniform " + uniform + " in shader " + shaderIdentifier);
            return;
        }
        int size = data.length;
        if (size == 0) {
            System.err.println("Attempted to set uniform " + uniform + " to invalid value in shader " + shaderIdentifier);
        } else if (size == 1) {
            glUniform1f(uniformLocationMap.get(uniform), data[0]);
        } else if (size == 2) {
            glUniform2f(uniformLocationMap.get(uniform), data[0], data[1]);
        } else if (size == 3) {
            glUniform3f(uniformLocationMap.get(uniform), data[0], data[1], data[2]);
        } else if (size == 4) {
            glUniform4f(uniformLocationMap.get(uniform), data[0], data[1], data[2], data[3]);
        } else {
            System.err.println("Attempted to set uniform " + uniform + " to too many values in shader " + shaderIdentifier);
        }
    }

    public boolean isLinkedSuccessfully() {
        return linkedSuccessfully;
    }

    public int getFragmentID() {
        return fragmentID;
    }

    public int getProgramID() {
        return programID;
    }

    public int getVertexID() {
        return vertexID;
    }

    public String getShaderIdentifier() {
        return shaderIdentifier;
    }

    public int getUniform(String uniform) {
        return this.uniformLocationMap.get(uniform);
    }
}