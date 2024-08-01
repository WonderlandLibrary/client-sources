package wtf.diablo.client.shader.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.shader.ShaderImpl;
import wtf.diablo.client.shader.actual.ShaderConstants;
import wtf.diablo.client.util.render.ColorUtil;

public final class RoundedRectangleShader extends ShaderImpl {

    public RoundedRectangleShader() {
        super(ShaderConstants.ROUNDED_RECTANGLE);
    }

    public void render(final double x, final double y, final double width, final double height, final double radius, final int color) {
        GlStateManager.enableBlend();
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


        useProgram();
        this.setupUniforms(x, y, width, height, radius, radius, radius, radius, color, 1);
        this.drawCanvas(x - 2, y - 2, width + 4, height + 4);
        unUseProgram();


        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
        GlStateManager.disableBlend();
    }

    public void render(final double x, final double y, final double width, final double height, final double radius, final int color, final float scaleFactor) {
        GlStateManager.enableBlend();
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        this.useProgram();
        this.setupUniforms(x, y, width, height, radius, radius, radius, radius, color, scaleFactor);
        this.drawCanvas(x - 2, y - 2, width + 4, height + 4);
        this.unUseProgram();

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
        GlStateManager.disableBlend();
    }

    public void render(final double x, final double y, final double width, final double height, final double topLeft, final double topRight,
                       final double bottomRight, final double bottomLeft, final int color) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        useProgram();
        setupUniforms(x, y, width, height, topLeft, topRight, bottomRight, bottomLeft, color, 1);
        drawCanvas(x - 2, y - 2, width + 4, height + 4);
        unUseProgram();

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
        GlStateManager.disableBlend();
    }


    public void setupUniforms(final double x, final double y, final double width, final double height, final double topLeft, final double topRight,
                              final double bottomRight, final double bottomLeft, final int color, final float scale) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final float scaleFactor = sr.getScaleFactor() * scale;
        this.setUniformFloat("position", (float) x * scaleFactor, (float) (sr.getScaledHeight() * scaleFactor - height * scaleFactor - y * scaleFactor));
        this.setUniformFloat("size", (float) width * scaleFactor, (float) height * scaleFactor);
        setUniformFloat("radius", (float) topRight * 2, (float) bottomRight * 2, (float) topLeft * 2, (float) bottomLeft * 2);

        final double[] c = ColorUtil.getRGBA(color);
        this.setUniformFloat("color", (float) c[0], (float) c[1], (float) c[2], (float) c[3]);
    }
}