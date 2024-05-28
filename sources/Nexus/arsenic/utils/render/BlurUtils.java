package arsenic.utils.render;

import arsenic.utils.java.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Math.abs;
import static org.lwjgl.opengl.Display.update;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class BlurUtils extends UtilityClass {

    private static final int gaussianProgram = createProgram("/assets/arsenic/shaders/circle.frag", "/assets/arsenic/shaders/vertex.vsh");
    private static Framebuffer blurredBuffer;

    /**
     * Render the blur effect.
     * @param _radius the radius of the blur.
     * @param _compression the compression of the blur.
     */

    public static void blur(float time, double height, double width) {
        update();
        if(blurredBuffer != null)
            blurredBuffer.deleteFramebuffer();
        blurredBuffer = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, false);

        glUseProgram(gaussianProgram);

        GL20.glUniform2f(glGetUniformLocation(gaussianProgram, "resolution"), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GL20.glUniform1f(glGetUniformLocation(gaussianProgram, "time"), time);
        blurredBuffer.bindFramebuffer(false);
        glBindTexture(GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, (float) height);
        glTexCoord2f(1, 0);
        glVertex2f((float) width, (float) height);
        glTexCoord2f(1, 1);
        glVertex2f((float) width, 0);
        glEnd();
        blurredBuffer.unbindFramebuffer();

        glUseProgram(gaussianProgram);

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        glBindTexture(GL_TEXTURE_2D, blurredBuffer.framebufferTexture);

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, (float) height);
        glTexCoord2f(1, 0);
        glVertex2f((float) width, (float) height);
        glTexCoord2f(1, 1);
        glVertex2f((float) width, 0);
        glEnd();

        glUseProgram(0);
    }

    public static int createProgram(final String fragmentShaderPath, final String vertexShaderPath) {
        int program = glCreateProgram();

        try {
            int fragShader = createShader(BlurUtils.class.getResourceAsStream(fragmentShaderPath), GL_FRAGMENT_SHADER);
            glAttachShader(program, fragShader);

            int vertexShader = createShader(BlurUtils.class.getResourceAsStream(vertexShaderPath), GL_VERTEX_SHADER);
            glAttachShader(program, vertexShader);
        } catch (IOException ignored) {
            return 0;
        }

        glLinkProgram(program);

        return program;
    }

    /**
     * Reads a shader from a file stream.
     * @param input the input file.
     * @return the file as a string.
     */
    private static String readShader(final InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(isr);

        String l;
        while ((l = br.readLine()) != null) {
            sb.append(l).append("\n");
        }

        return sb.toString();
    }

    /**
     * Creates the GL shader.
     * @param input the input stream.
     * @param type the type of shader.
     * @return shader program.
     */
    public static int createShader(final InputStream input, final int type) throws IOException {
        int shader = glCreateShader(type);

        glShaderSource(shader, readShader(input));
        glCompileShader(shader);

        return shader;
    }


}
