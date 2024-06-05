package alphentus.utils;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 01/08/2020.
 */
public class BlurUtil {

    private static final Minecraft MC = Minecraft.getMinecraft();
    private ResourceLocation resourceLocation;
    private ResourceLocation resourceLocationNormalBlurs;
    private ShaderGroup shaderGroup;
    private Framebuffer framebuffer;

    private ShaderGroup shaderGroup2;
    private Framebuffer framebuffer2;

    private int lastFactor;
    private int lastWidth;
    private int lastHeight;

    public BlurUtil() {
        this.resourceLocation = new ResourceLocation("client/blur.json");
        this.resourceLocationNormalBlurs = new ResourceLocation("shaders/post/blur.json");
    }

    public final void init() {
        try {
            this.shaderGroup = new ShaderGroup(MC.getTextureManager(), MC.getResourceManager(), MC.getFramebuffer(), resourceLocation);
            this.shaderGroup.createBindFramebuffers(MC.displayWidth, MC.displayHeight);
            this.framebuffer = shaderGroup.mainFramebuffer;

            this.shaderGroup2 = new ShaderGroup(MC.getTextureManager(), MC.getResourceManager(), MC.getFramebuffer(), resourceLocation);
            this.shaderGroup2.createBindFramebuffers(MC.displayWidth, MC.displayHeight);
            this.framebuffer2 = shaderGroup2.mainFramebuffer;

        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setValues(int strength) {
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);

    }

    public final void blur(double x, double y, double areaWidth, double areaHeight, int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(MC);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GLUtil.makeScissorBox((int) x, (int) y + 1, (int) x + (int) areaWidth, (int) y + (int) areaHeight - 1);

        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(MC.timer.renderPartialTicks);
        setValues(blurStrength);
        MC.getFramebuffer().bindFramebuffer(false);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public final void blurWholeScreen(int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(MC);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;

        framebuffer2.bindFramebuffer(true);
        shaderGroup2.loadShaderGroup(MC.timer.renderPartialTicks);
        this.shaderGroup2.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(blurStrength);
        this.shaderGroup2.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(blurStrength);
        MC.getFramebuffer().bindFramebuffer(false);

    }


    private boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return (lastFactor != scaleFactor || lastWidth != width || lastHeight != height);
    }

}
