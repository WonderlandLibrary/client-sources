package me.kaimson.melonclient.gui;

import me.kaimson.melonclient.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public class GuiButtonIcon extends avs
{
    private final jy ICON;
    
    public GuiButtonIcon(final int buttonId, final int x, final int y, final int width, final int height, final String iconName) {
        super(buttonId, x, y, width, height, "");
        this.ICON = new jy("melonclient/icons/" + iconName);
    }
    
    public void a(final ave mc, final int mouseX, final int mouseY) {
        if (this.m) {
            this.n = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
            GLRectUtils.drawRoundedOutline(this.h, this.i, this.h + this.f, this.i + this.g, 3.0f, 2.0f, this.l ? (this.n ? Client.getMainColor(255) : Client.getMainColor(150)) : Client.getMainColor(100));
            GLRectUtils.drawRoundedRect((float)this.h, (float)this.i, (float)(this.h + this.f), (float)(this.i + this.g), 3.0f, this.l ? (this.n ? new Color(0, 0, 0, 100).getRGB() : new Color(30, 30, 30, 100).getRGB()) : new Color(70, 70, 70, 50).getRGB());
            mc.P().a(this.ICON);
            final int b = 10;
            bfl.l();
            GuiUtils.setGlColor(new Color(0, 0, 0, 75).getRGB());
            GuiUtils.drawScaledCustomSizeModalRect(this.h + (this.f - b) / 2.0f + 0.75f, this.i + (this.g - b) / 2.0f + 0.75f, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
            GuiUtils.setGlColor(Client.getMainColor(255));
            GuiUtils.a(this.h + (this.f - b) / 2, this.i + (this.g - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
        }
    }
}
