package wtf.diablo.client.shader.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import wtf.diablo.client.shader.ShaderImpl;
import wtf.diablo.client.shader.actual.ShaderConstants;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.gl.FramebufferUtils;
import wtf.diablo.client.util.render.gl.GLUtils;

import java.util.ArrayList;
import java.util.List;

public final class KawaseBlur {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final ShaderImpl kawaseDown = new ShaderImpl(ShaderConstants.KAWASE_DOWN_BLUR);
    private static final ShaderImpl kawaseUp = new ShaderImpl(ShaderConstants.KAWASE_UP_BLUR);

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    private static int currentIterations;

    private static final List<Framebuffer> framebufferList = new ArrayList<>();

    private static void initFramebuffers(final float iterations)
    {
        for (final Framebuffer framebuffer : framebufferList)
        {
            framebuffer.deleteFramebuffer();
        }

        framebufferList.clear();

        framebufferList.add(framebuffer = FramebufferUtils.createFrameBuffer(null));


        for (int i = 1; i <= iterations; i++)
        {
            final int value = (int) (mc.displayWidth / Math.pow(2, i));
            final int value2 = (int) (mc.displayHeight / Math.pow(2, i));
            final Framebuffer currentBuffer = new Framebuffer(value, value2, false);
            currentBuffer.setFramebufferFilter(GL11.GL_LINEAR);
            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
            GlStateManager.bindTexture(0);

            framebufferList.add(currentBuffer);
        }
    }


    public static void renderBlur(final int stencilFrameBufferTexture, final int iterations, final int offset)
    {
        if (currentIterations != iterations || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight)
        {
            initFramebuffers(iterations);
            currentIterations = iterations;
        }

        renderFBO(framebufferList.get(1), mc.getFramebuffer().framebufferTexture, kawaseDown, offset);

        //Downsample
        for (int i = 1; i < iterations; i++)
        {
            renderFBO(framebufferList.get(i + 1), framebufferList.get(i).framebufferTexture, kawaseDown, offset);
        }

        //Upsample
        for (int i = iterations; i > 1; i--)
        {
            renderFBO(framebufferList.get(i - 1), framebufferList.get(i).framebufferTexture, kawaseUp, offset);
        }


        Framebuffer lastBuffer = framebufferList.get(0);
        lastBuffer.framebufferClear();
        lastBuffer.bindFramebuffer(false);
        kawaseUp.useProgram();
        kawaseUp.setUniformFloat("offset", offset, offset);
        kawaseUp.setUniformInt("texture", 0);
        kawaseUp.setUniformInt("temp", 1);
        kawaseUp.setUniformInt("texture2", 16);
        kawaseUp.setUniformFloat("eachPixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
        kawaseUp.setUniformFloat("resolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);
        GL13.glActiveTexture(GL13.GL_TEXTURE16);
        GLUtils.bindTexture(stencilFrameBufferTexture);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GLUtils.bindTexture(framebufferList.get(1).framebufferTexture);
        ColorUtil.resetColor();
        ShaderImpl.drawQuads();
        kawaseUp.unUseProgram();


        mc.getFramebuffer().bindFramebuffer(true);
        GLUtils.bindTexture(framebufferList.get(0).framebufferTexture);
        ColorUtil.setAlphaLimit(0);
        GLUtils.enableBlend();
        ColorUtil.resetColor();
        ShaderImpl.drawQuads();
        GlStateManager.bindTexture(0);
    }

    private static void renderFBO(final Framebuffer framebuffer, final int framebufferTexture, final ShaderImpl shader, final float offset)
    {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        shader.useProgram();
        GLUtils.bindTexture(framebufferTexture);
        shader.setUniformFloat("offset", offset, offset);
        shader.setUniformInt("texture", 0);
        shader.setUniformInt("temp", 0);
        shader.setUniformFloat("eachPixel", 1.0f / framebuffer.framebufferWidth, 1.0f / framebuffer.framebufferHeight);
        shader.setUniformFloat("resolution", framebuffer.framebufferWidth, framebuffer.framebufferHeight);
        ColorUtil.resetColor();
        ShaderImpl.drawQuads();
        shader.unUseProgram();
    }

}
