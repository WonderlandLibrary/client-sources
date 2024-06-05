package xyz.vergoclient.util.Gl;

// innominate da world
// (for dummies, this is skidded from ketamine.
// Saved me a whole lotta headaches.)
// ml innom.

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import xyz.vergoclient.util.main.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class BloomUtil {


    private static final String OUTLINE_SHADER =
            "#version 120\n" +
                    "\n" +
                    "uniform sampler2D texture;\n" +
                    "uniform vec2 texelSize;\n" +
                    "\n" +
                    "uniform vec4 colour;\n" +
                    "uniform float radius;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    float a = 0.0;\n" +
                    "    vec3 rgb = colour.rgb;\n" +
                    "    float closest = 1.0;\n" +
                    "    for (float x = -radius; x <= radius; x++) {\n" +
                    "        for (float y = -radius; y <= radius; y++) {\n" +
                    "            vec2 st = gl_TexCoord[0].st + vec2(x, y) * texelSize;\n" +
                    "            vec4 smpl = texture2D(texture, st);\n" +
                    "            float dist = distance(st, gl_TexCoord[0].st);\n" +
                    "            if (smpl.a > 0.0 && dist < closest) {" +
                    "               rgb = smpl.rgb;\n" +
                    "               closest = dist;\n" +
                    "            }\n" +
                    "            a += smpl.a*smpl.a;\n" +
                    "        }\n" +
                    "    }\n" +
                    "    vec4 smpl = texture2D(texture, gl_TexCoord[0].st);\n" +
                    "    gl_FragColor = vec4(rgb, a * colour.a / (4.0 * radius * radius)) * (smpl.a > 0.0 ? 0.0 : 1.0);\n" +
                    "}\n";

    private static final String VERTEX_SHADER =
            "#version 120 \n" +
                    "\n" +
                    "void main() {\n" +
                    "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                    "    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n" +
                    "}";


    private static final GLShader shader = new GLShader(VERTEX_SHADER, OUTLINE_SHADER) {
        @Override
        public void setupUniforms() {
            this.setupUniform("texture");
            this.setupUniform("texelSize");
            this.setupUniform("colour");
            this.setupUniform("radius");
            glUniform1i(this.getUniformLocation("texture"), 0);
        }

        @Override
        public void updateUniforms() {

            glUniform4f(this.getUniformLocation("colour"),
                    1.0f,
                    1.0f,
                    1.0f,
                    1.0f);
            glUniform2f(this.getUniformLocation("texelSize"),
                    1f / Minecraft.getMinecraft().displayWidth,
                    1f / Minecraft.getMinecraft().displayHeight);
            glUniform1f(this.getUniformLocation("radius"), 5f);
        }
    };

    private static Framebuffer framebuffer;
    public static boolean disableBloom;
    private static List<RenderCallback> renders = new ArrayList<>();

    private BloomUtil() {
    }

    public static void bloom(RenderCallback render) {
        if (disableBloom) return;
        renders.add(render);
    }

    public static void drawAndBloom(RenderCallback render) {
        render.render();
        if (disableBloom) return;
        renders.add(render);
    }

    public static void onRenderGameOverlay(ScaledResolution scaledResolution, Framebuffer mcFramebuffer) {
        if (framebuffer == null) return;

        framebuffer.bindFramebuffer(false);

        for (RenderCallback callback : renders) {
            callback.render();
        }

        renders.clear();

        mcFramebuffer.bindFramebuffer(false);

        // Use the outline shader
        shader.use();
        // Draw our framebuffer
        ColorUtils.glDrawFramebuffer(framebuffer.framebufferTexture, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        // Disable shader
        glUseProgram(0);
        // Clear our framebuffer
        framebuffer.framebufferClear();
        // Need to rebind minecraft framebuffer after clearing ours
        mcFramebuffer.bindFramebuffer(false);
    }

    public static void onFrameBufferResize(final int width, final int height) {
        if (framebuffer != null) {
            // Delete old buffers as to not cause a memory leak
            framebuffer.deleteFramebuffer();
        }
        // Create new framebuffer
        // False means it doesn't allocate a depth buffer which we don't need
        framebuffer = new Framebuffer(width, height, false);
    }
}