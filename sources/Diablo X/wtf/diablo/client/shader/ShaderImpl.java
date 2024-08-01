package wtf.diablo.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShaderImpl implements Shader {

    private int programID;
    private final Map<String, Integer> uniformMap;
    private final String vertexShader;

    public ShaderImpl(final String fragmentShader) {
        this(fragmentShader, new HashMap<>());
    }

    public ShaderImpl(final String fragmentShader, final HashMap<String, Integer> uniformMap) {
        this.uniformMap = uniformMap;
        this.vertexShader = "#version 120\n" +
                "\n" +
                "void main(void) {\n" +
                "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "\n" +
                "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                "}";
        this.createShader(this.vertexShader, fragmentShader);
    }

    @Override
    public void useProgram()
    {
        GL20.glUseProgram(this.programID);
    }

    @Override
    public void unUseProgram()
    {
        GL20.glUseProgram(GL11.GL_ZERO);
    }

    protected void setUniform(final String name) {
        this.uniformMap.put(name, GL20.glGetUniformLocation(programID, name));
    }

    public void setUniformFloat(final String name, float... floats) {
        this.setUniform(name);

        switch (floats.length)
        {
            case 1:
                GL20.glUniform1f(this.uniformMap.get(name), floats[0]);
                break;
            case 2:
                GL20.glUniform2f(this.uniformMap.get(name), floats[0], floats[1]);
                break;
            case 3:
                GL20.glUniform3f(this.uniformMap.get(name), floats[0], floats[1], floats[2]);
                break;
            case 4:
                GL20.glUniform4f(this.uniformMap.get(name), floats[0], floats[1], floats[2], floats[3]);
                break;
            default: throw new IllegalArgumentException("Invalid float array length: " + floats.length);
        }
    }

    public void setUniformInt(final String name, int... ints)
    {
        this.setUniform(name);

        switch (ints.length)
        {
            case 1:
                GL20.glUniform1i(this.uniformMap.get(name), ints[0]);
                break;
            case 2:
                GL20.glUniform2i(this.uniformMap.get(name), ints[0], ints[1]);
                break;
            case 3:
                GL20.glUniform3i(this.uniformMap.get(name), ints[0], ints[1], ints[2]);
                break;
            case 4:
                GL20.glUniform4i(this.uniformMap.get(name), ints[0], ints[1], ints[2], ints[3]);
                break;
            default: throw new IllegalArgumentException("Invalid int array length: " + ints.length);
        }
    }

    protected void drawCanvas(final double x, final double y, final double width, final double height) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ZERO);
        GL11.glVertex2d(x, y);

        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ONE);
        GL11.glVertex2d(x, y + height);

        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glVertex2d(x + width, y + height);

        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glVertex2d(x + width, y);

        GL11.glEnd();
    }

    private void createShader(final String vertexShaderCode, final String fragmentShaderCode) {
        final int programId = GL20.glCreateProgram();

        final int vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        final int fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        // Compile vertex shader
        GL20.glShaderSource(vertexShaderId, vertexShaderCode);
        GL20.glCompileShader(vertexShaderId);

        // Compile fragment shader
        GL20.glShaderSource(fragmentShaderId, fragmentShaderCode);
        GL20.glCompileShader(fragmentShaderId);

        // Create shader program
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        GL20.glLinkProgram(programId);

        this.programID = programId;
    }

    /**
     * Credit artemis.
     */
    public static void drawQuads()
    {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final float width = (float) sr.getScaledWidth_double();
        final float height = (float) sr.getScaledHeight_double();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(width, 0);
        GL11.glEnd();
    }

    public void setUniform1(final String uniformName, final FloatBuffer value)
    {
        this.setUniform(uniformName);

        GL20.glUniform1(this.uniformMap.get(uniformName), value);
    }

}
