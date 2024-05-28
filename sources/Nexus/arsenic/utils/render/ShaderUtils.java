package arsenic.utils.render;

import arsenic.utils.java.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BiConsumer;

import static org.lwjgl.opengl.Display.update;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils extends UtilityClass {

    public static final Program mainMenuProgram = new Program("circle",
            (program, params) -> {
                GL20.glUniform2f(glGetUniformLocation(program, "resolution"), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                GL20.glUniform1f(glGetUniformLocation(program, "time"), (float) params[0]);
                GL20.glUniform4f(glGetUniformLocation(program, "tint"), ((float) params[1])/16f, ((float) params[2])/16f, ((float) params[3])/16f, 1.0f);
            });

    public static final Program cursorProgram = new Program("cursortrail",
            (program, params) -> {
                float scaleFactor = (float) params[3];
                GL20.glUniform2f(glGetUniformLocation(program, "resolution"), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                GL20.glUniform1f(glGetUniformLocation(program, "time"), (float) params[0]);
                GL20.glUniform2f(glGetUniformLocation(program, "mouse"), scaleFactor * (float) params[1], mc.displayHeight - ((float) params[2]) * scaleFactor);
            });

    private static Framebuffer blurredBuffer;

    private static void start() {
        update();
        if(blurredBuffer != null)
            blurredBuffer.deleteFramebuffer();
        blurredBuffer = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, false);
    }

    private static void end(Program program) {
        float height = mc.currentScreen.height;
        float width = mc.currentScreen.width;
        blurredBuffer.bindFramebuffer(false);
        glBindTexture(GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);

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
        blurredBuffer.unbindFramebuffer();

        glUseProgram(program.getProgram());

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        glBindTexture(GL_TEXTURE_2D, blurredBuffer.framebufferTexture);

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

        glUseProgram(0);
    }


    public static void drawShader(Program program, Object... o) {
        start();
        glUseProgram(program.getProgram());
        program.runnable.accept(program.getProgram(), o);
        end(program);
    }




    private static int createProgram(final String fragmentShaderPath, final String vertexShaderPath) {
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
    private static int createShader(final InputStream input, final int type) throws IOException {
        int shader = glCreateShader(type);

        glShaderSource(shader, readShader(input));
        glCompileShader(shader);

        return shader;
    }


    public static class Program {

        public int getProgram() {
            return program;
        }

        public BiConsumer<Integer, Object[]> getRunnable() {
            return runnable;
        }

        private final int program;
        private final BiConsumer<Integer, Object[]> runnable;
        public Program(String name, BiConsumer<Integer, Object[]> runnable) {
            this.program = createProgram("/assets/arsenic/shaders/" + name + ".frag", "/assets/arsenic/shaders/vertex.vsh");;
            this.runnable = runnable;
        }
    }


}
