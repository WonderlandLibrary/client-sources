package me.kaimson.melonclient.gui.elements.settings;

import me.kaimson.melonclient.utils.*;
import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import java.awt.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.lwjgl.opengl.*;

public class SettingElementCheckbox extends SettingElement
{
    private int bgFade;
    
    public SettingElementCheckbox(final int x, final int y, final int width, final int height, final Setting setting, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, setting, consumer, parent);
        this.bgFade = 50;
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        if (this.setting.getBoolean()) {
            this.renderCheckmark(0.5f, new Color(0, 0, 0, 50).getRGB());
            this.renderCheckmark(0.0f, new Color(255, 255, 255, 255).getRGB());
        }
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 2.0f, Client.getMainColor(this.bgFade));
        GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 2.0f, 2.0f, Client.getMainColor(255));
    }
    
    @Override
    public void update() {
        if (this.hovered && this.bgFade + 10 < 150) {
            this.bgFade += 10;
        }
        else if (!this.hovered && this.bgFade - 10 > 50) {
            this.bgFade -= 10;
        }
    }
    
    private void renderCheckmark(final float offset, final int color) {
        GuiUtils.setGlColor(color);
        bfl.x();
        GL11.glLineWidth(5.0f);
        GL11.glBegin(1);
        GL11.glVertex2f(this.getX() + 2 + offset, this.getY() + this.height / 2 + offset);
        GL11.glVertex2f(this.getX() + this.width / 3 + 1 + offset, this.getY() + this.height / 3 * 2 + 1 + offset);
        GL11.glVertex2f(this.getX() + this.width / 3 + 1 + offset, this.getY() + this.height / 3 * 2 + 1 + offset);
        GL11.glVertex2f(this.getX() + this.width - 2 + offset, this.getY() + 3 + offset);
        GL11.glEnd();
        bfl.w();
    }
}
