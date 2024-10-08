// 
// Decompiled by Procyon v0.5.36
// 

package moonsense.ui.elements.settings.color;

import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.WorldRenderer;
import java.awt.Color;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import moonsense.ui.utils.GuiUtils;

public class HuePane extends SettingElementPane
{
    public int hue;
    
    public HuePane(final int x, final int y, final int width, final int height) {
        super(x, y, width, height, 0, 360);
    }
    
    @Override
    public void renderPane(final int mouseX, final int mouseY) {
        super.renderPane(mouseX, mouseY);
        for (int i = 0; i < 64; i += 8) {
            final int c1 = GuiUtils.hsvToRgb(i * 360 / 64, 100, 100) | 0xFF000000;
            final int c2 = GuiUtils.hsvToRgb((i + 8) * 360 / 64, 100, 100) | 0xFF000000;
            this.huePaneSection(this.getX(), this.getY() + i / 8 * 6, this.getX() + this.width, this.getY() + i / 8 * 6 + 6, c1, c1, c2, c2);
        }
        GlStateManager.disableTexture2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glLineWidth(1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(this.x, this.y + this.hue * this.height / this.max, 0.0);
        worldRenderer.addVertex(this.x + this.width, this.y + this.hue * this.height / this.max, 0.0);
        tessellator.draw();
        GuiUtils.drawRoundedOutline(this.getX() - 0.5f, this.getY() - 0.2f, this.getX() + this.width + 0.4f, this.getY() + this.height + 0.4f, 2.0f, 1.5f, new Color(200, 200, 200, 255).getRGB());
    }
    
    private void huePaneSection(final int left, final int top, final int right, final int bottom, final int ctl, final int ctr, final int cbl, final int cbr) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(9);
        final float[] f1 = new Color(cbl).getRGBColorComponents(null);
        GL11.glColor4f(f1[0], f1[1], f1[2], 1.0f);
        GL11.glVertex2f((float)left, (float)bottom);
        final float[] f2 = new Color(cbr).getRGBColorComponents(null);
        GL11.glColor4f(f2[0], f2[1], f2[2], 1.0f);
        GL11.glVertex2f((float)right, (float)bottom);
        final float[] f3 = new Color(ctr).getRGBColorComponents(null);
        GL11.glColor4f(f3[0], f3[1], f3[2], 1.0f);
        GL11.glVertex2f((float)right, (float)top);
        final float[] f4 = new Color(ctl).getRGBColorComponents(null);
        GL11.glColor4f(f4[0], f4[1], f4[2], 1.0f);
        GL11.glVertex2f((float)left, (float)top);
        GL11.glEnd();
    }
    
    @Override
    public void dragging(final int mouseX, final int mouseY) {
        this.hue = MathHelper.clamp_int((mouseY - this.y) * this.max / this.height, this.min, this.max);
    }
}
