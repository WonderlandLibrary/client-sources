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

public final class KawaseBloom {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static ShaderImpl kawaseDown = new ShaderImpl(ShaderConstants.KAWASE_DOWN_GLOW);
    public static ShaderImpl kawaseUp = new ShaderImpl(ShaderConstants.KAWASE_UP_GLOW);

    public static Framebuffer framebuffer = new Framebuffer(1, 1, true);

    private static int currentIterations;

    private static final List<Framebuffer> framebufferList = new ArrayList<>();

    private static void initFramebuffers(final float iterations)
    {
        for (final Framebuffer framebuffer : framebufferList)
        {
            framebuffer.deleteFramebuffer();
        }
        framebufferList.clear();

        //Have to make the framebuffer null so that it does not try to delete a framebuffer that has already been deleted
        framebufferList.add(framebuffer = FramebufferUtils.createFrameBuffer(null, true));


        for (int i = 1; i <= iterations; i++)
        {
            Framebuffer currentBuffer = new Framebuffer((int) (mc.displayWidth / Math.pow(2, i)), (int) (mc.displayHeight / Math.pow(2, i)), true);
            currentBuffer.setFramebufferFilter(GL11.GL_LINEAR);

            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
            GlStateManager.bindTexture(0);

            framebufferList.add(currentBuffer);
        }
    }


    public static void renderBlur(final int framebufferTexture, final int iterations, final int offset)
    {
        if (mc.gameSettings.ofFastRender)
            return;
        if (currentIterations != iterations || (framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight))
        {
            initFramebuffers(iterations);
            currentIterations = iterations;
        }

        ColorUtil.setAlphaLimit(0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        GL11.glClearColor(0, 0, 0, 0);
        renderFBO(framebufferList.get(1), framebufferTexture, kawaseDown, offset);

        //Downsample
        for (int i = 1; i < iterations; i++) {
            renderFBO(framebufferList.get(i + 1), framebufferList.get(i).framebufferTexture, kawaseDown, offset);
        }

        //Upsample
        for (int i = iterations; i > 1; i--) {
            renderFBO(framebufferList.get(i - 1), framebufferList.get(i).framebufferTexture, kawaseUp, offset);
        }

        final Framebuffer lastBuffer = framebufferList.get(0);
        lastBuffer.framebufferClear();
        lastBuffer.bindFramebuffer(false);
        kawaseUp.useProgram();
        kawaseUp.setUniformFloat("offset", offset, offset);
        kawaseUp.setUniformInt("texture", 0);
        kawaseUp.setUniformInt("tempInt", 1);
        kawaseUp.setUniformInt("texture2", 16);
        kawaseUp.setUniformFloat("eachPixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
        kawaseUp.setUniformFloat("resolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE16);
        GLUtils.bindTexture(framebufferTexture);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
        GLUtils.bindTexture(framebufferList.get(1).framebufferTexture);
        ShaderImpl.drawQuads();
        kawaseUp.unUseProgram();


        GlStateManager.clearColor(0, 0, 0, 0);
        mc.getFramebuffer().bindFramebuffer(false);
        GLUtils.bindTexture(framebufferList.get(0).framebufferTexture);
        ColorUtil.setAlphaLimit(0);
        GLUtils.enableBlend();
        ShaderImpl.drawQuads();
        GlStateManager.bindTexture(0);
        ColorUtil.setAlphaLimit(0);
        GLUtils.enableBlend();
    }

    private static void renderFBO(final Framebuffer framebuffer, final int framebufferTexture, final ShaderImpl shader, final float offset)
    {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        shader.useProgram();
        GLUtils.bindTexture(framebufferTexture);
        shader.setUniformFloat("offset", offset, offset);
        shader.setUniformInt("texture", 0);
        shader.setUniformInt("tempInt", 0);
        shader.setUniformFloat("eachPixel", 1.0f / framebuffer.framebufferWidth, 1.0f / framebuffer.framebufferHeight);
        shader.setUniformFloat("resolution", framebuffer.framebufferWidth, framebuffer.framebufferHeight);
        ShaderImpl.drawQuads();
        shader.unUseProgram();
    }

}
