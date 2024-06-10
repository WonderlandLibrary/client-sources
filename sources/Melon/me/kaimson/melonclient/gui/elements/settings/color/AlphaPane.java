package me.kaimson.melonclient.gui.elements.settings.color;

import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class AlphaPane extends SettingElementPane
{
    public int alpha;
    
    public AlphaPane(final int x, final int y, final int width, final int height) {
        super(x, y, width, height, 0, 255);
        this.alpha = 255;
    }
    
    public void renderPane(final int rgb, final int mouseX, final int mouseY) {
        this.renderPane(mouseX, mouseY);
        bfl.w();
        ave.A().P().a(Client.TRANSPARENT);
        final bfx tessellator = bfx.a();
        final bfd worldRenderer = tessellator.c();
        worldRenderer.a(7, bms.g);
        worldRenderer.b((double)this.getX(), (double)(this.getY() + this.height), 0.0).a(0.0, 4.0).d();
        worldRenderer.b((double)(this.getX() + this.width), (double)(this.getY() + this.height), 0.0).a(1.25, 4.0).d();
        worldRenderer.b((double)(this.getX() + this.width), (double)this.getY(), 0.0).a(1.25, 0.0).d();
        worldRenderer.b((double)this.getX(), (double)this.getY(), 0.0).a(0.0, 0.0).d();
        tessellator.b();
        GLRectUtils.drawGradientRect(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, rgb | 0xFF000000, rgb | 0xFF000000, rgb, rgb, 0);
        bfl.x();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glLineWidth(1.0f);
        worldRenderer.a(1, bms.e);
        worldRenderer.b((double)this.getX(), (double)(this.getY() + this.height - this.alpha * (float)this.height / this.max), 0.0).d();
        worldRenderer.b((double)(this.getX() + this.width), (double)(this.getY() + this.height - this.alpha * (float)this.height / this.max), 0.0).d();
        tessellator.b();
        GLRectUtils.drawRoundedOutline(this.getX() - 0.5f, this.getY() - 0.2f, this.getX() + this.width + 0.4f, this.getY() + this.height + 0.4f, 2.0f, 1.5f, new Color(200, 200, 200, 255).getRGB());
    }
    
    @Override
    public void dragging(final int mouseX, final int mouseY) {
        this.alpha = ns.a((this.height - (mouseY - this.getY())) * this.max / this.height, this.min, this.max);
    }
}
