package ClassSub;

import net.minecraft.client.gui.*;
import cn.Hanabi.*;
import net.minecraft.client.*;

public class Class123
{
    public GuiScreen screen;
    public String text;
    public int animatedY;
    public boolean init;
    public String icon;
    public Class140 handler;
    
    
    public Class123(final String text, final String icon, final GuiScreen screen) {
        this.animatedY = 0;
        this.init = false;
        this.handler = new Class140(0);
        this.screen = screen;
        this.text = text;
        this.icon = icon;
    }
    
    public void draw(final int n, final int n2, final int n3, final int animatedY) {
        if (!this.init) {
            this.animatedY = animatedY;
            this.init = true;
        }
        if (this.isHovering(n, n2, n3 - 32, animatedY - 32, n3 + 32, animatedY + 32)) {
            this.animatedY = (int)Class246.getAnimationState(this.animatedY, animatedY - 10, 200.0);
        }
        else {
            this.animatedY = (int)Class246.getAnimationState(this.animatedY, animatedY, 200.0);
        }
        Class246.circle(n3, this.animatedY, 30.0f, Class15.WHITE.c);
        if (this.isHovering(n, n2, n3 - 32, animatedY - 32, n3 + 32, animatedY + 32)) {
            Hanabi.INSTANCE.fontManager.wqy18.drawString(this.text, n3 - 18.0f, animatedY + 40, Class15.WHITE.c);
        }
        final String icon = this.icon;
        switch (icon) {
            case "юд?": {
                Hanabi.INSTANCE.fontManager.icon70.drawString(this.icon, n3 - 17.0f, this.animatedY - 19, Class15.GREY.c);
                break;
            }
            case "юд?": {
                Hanabi.INSTANCE.fontManager.icon100.drawString(this.icon, n3 - 24.0f, this.animatedY - 28, Class15.GREY.c);
                break;
            }
            case "юд?": {
                Hanabi.INSTANCE.fontManager.icon70.drawString(this.icon, n3 - 17.0f, this.animatedY - 20, Class15.GREY.c);
                break;
            }
            case "юд?": {
                Hanabi.INSTANCE.fontManager.icon70.drawString(this.icon, n3 - 17.0f, this.animatedY - 19, Class15.GREY.c);
                break;
            }
            case "юд?": {
                Hanabi.INSTANCE.fontManager.icon70.drawString(this.icon, n3 - 17.0f, this.animatedY - 19, Class15.GREY.c);
                break;
            }
        }
    }
    
    public void onClick(final int n, final int n2, final int n3, final int n4) {
        if (this.isHovering(n, n2, n3 - 32, n4 - 32, n3 + 32, n4 + 32) && this.handler.canExcecute()) {
            if (this.screen != null) {
                Minecraft.getMinecraft().displayGuiScreen(this.screen);
            }
            else {
                Minecraft.getMinecraft().shutdown();
            }
        }
    }
    
    private boolean isHovering(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
}
