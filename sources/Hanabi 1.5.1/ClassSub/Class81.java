package ClassSub;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.awt.*;
import cn.Hanabi.*;
import cn.Hanabi.utils.fontmanager.*;
import java.util.*;

public class Class81
{
    float x;
    float y;
    float radius;
    String logo;
    public String title;
    ScaledResolution sr;
    public Boolean active;
    double state;
    Class33 sb;
    
    
    public Class81(final float x, final float y, final float radius, final String title, final ScaledResolution sr, final Class33 sb) {
        this.active = false;
        this.state = 0.0;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.title = title;
        this.sr = sr;
        this.sb = sb;
        if (title.equals("Combat")) {
            this.logo = HanabiFonts.ICON_CLICKGUI_COMBAT;
        }
        else if (title.equals("Movement")) {
            this.logo = HanabiFonts.ICON_CLICKGUI_MOVEMENT;
        }
        else if (title.equals("Render")) {
            this.logo = HanabiFonts.ICON_CLICKGUI_RENDER;
        }
        else if (title.equals("Player")) {
            this.logo = HanabiFonts.ICON_CLICKGUI_PLAYER;
        }
        else if (title.equals("World")) {
            this.logo = HanabiFonts.ICON_CLICKGUI_WORLD;
        }
        else {
            this.logo = HanabiFonts.ICON_CLICKGUI_GHOST;
        }
    }
    
    public void drawButton() {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        if (this.active) {
            this.state = this.getAnimationState(this.state, 5.0, 35.0);
            Class246.drawRoundedRect((int)this.x - 45, (int)this.y - 16, (int)this.x + 45, (int)this.y + 17, 15.0f, Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(47, 116, 253).getRGB());
        }
    }
    
    public void drawOther() {
        UnicodeFontRenderer unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon20;
        UnicodeFontRenderer unicodeFontRenderer2 = Hanabi.INSTANCE.fontManager.raleway20;
        if (this.sr.getScaledHeight() < 330) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon25;
            unicodeFontRenderer2 = Hanabi.INSTANCE.fontManager.raleway12;
        }
        else if (this.sr.getScaledHeight() < 550) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon40;
            unicodeFontRenderer2 = Hanabi.INSTANCE.fontManager.raleway17;
        }
        if (Class135.theme.isCurrentMode("Light")) {
            unicodeFontRenderer.drawString(this.logo, this.x - this.radius / 2.0f, this.y - unicodeFontRenderer.getStringHeight(this.logo) / 2.0f, ((boolean)this.active) ? new Color(255, 255, 255).getRGB() : new Color(-14848033).brighter().getRGB());
            unicodeFontRenderer2.drawString(this.title, this.x - unicodeFontRenderer.getStringWidth(this.logo) / 2, this.y - unicodeFontRenderer2.getStringHeight(this.title) / 2.0f, ((boolean)this.active) ? new Color(255, 255, 255).getRGB() : new Color(-14848033).brighter().getRGB());
        }
        else {
            unicodeFontRenderer.drawString(this.logo, this.x - this.radius / 2.0f, this.y - unicodeFontRenderer.getStringHeight(this.logo) / 2.0f, ((boolean)this.active) ? new Color(255, 255, 255).getRGB() : new Color(47, 116, 253).getRGB());
            unicodeFontRenderer2.drawString(this.title, this.x - unicodeFontRenderer.getStringWidth(this.logo) / 2, this.y - unicodeFontRenderer2.getStringHeight(this.title) / 2.0f, ((boolean)this.active) ? new Color(255, 255, 255).getRGB() : new Color(47, 116, 253).getRGB());
        }
    }
    
    public void onPress() {
        final Iterator<Class81> iterator = this.sb.button.iterator();
        while (iterator.hasNext()) {
            iterator.next().active = false;
        }
        this.active = true;
        this.state = 0.0;
    }
    
    public boolean isPressed(final int n, final int n2) {
        return this.isHovering(n, n2, this.x - this.radius / 1.5, this.y - this.radius / 3.5, this.x + this.radius / 1.5, this.y + this.radius / 3.5) && !this.active;
    }
    
    private boolean isHovering(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    public double getAnimationState(double n, final double n2, final double n3) {
        final float n4 = (float)(Class246.delta * n3);
        if (n < n2) {
            if (n + n4 < n2) {
                n += n4;
            }
            else {
                n = n2;
            }
        }
        else if (n - n4 > n2) {
            n -= n4;
        }
        else {
            n = n2;
        }
        return n;
    }
}
