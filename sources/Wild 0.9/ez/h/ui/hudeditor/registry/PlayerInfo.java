package ez.h.ui.hudeditor.registry;

import ez.h.ui.hudeditor.*;
import ez.h.*;
import ez.h.utils.*;
import ez.h.ui.fonts.*;
import java.awt.*;

public class PlayerInfo extends DraggableElement
{
    public PlayerInfo() {
        super("PlayerInfo", 200.0f, 50.0f);
        this.x = 0.0f;
        this.y = RenderUtils.getScaledScreen()[1] - 15.0f;
    }
    
    @Override
    public void render(final float n, final float n2, final float n3, final boolean b) {
        this.height = 15.0f;
        if (b) {
            super.render(n, n2, n3, b);
            return;
        }
        String s = "";
        if (this.mc.h == null || this.mc.m instanceof bkn || this.mc.v().a(this.mc.af.c()) == null) {
            return;
        }
        final ez.h.features.visual.PlayerInfo playerInfo = (ez.h.features.visual.PlayerInfo)Main.getFeatureByName("PlayerInfo");
        if (playerInfo.fps.enabled) {
            s = s + "FPS " + bib.af() + ((playerInfo.ping.enabled || playerInfo.coords.enabled) ? " | " : "");
        }
        if (playerInfo.ping.enabled) {
            s = s + "Ping " + this.mc.v().a(this.mc.af.c()).c() + (playerInfo.coords.enabled ? " | " : "");
        }
        if (playerInfo.coords.enabled) {
            s = s + "XYZ " + Utils.format("##", this.mc.h.p) + " " + Utils.format("##", this.mc.h.q) + " " + Utils.format("##", this.mc.h.r);
        }
        ScaleUtils.scale_pre();
        RenderUtils.drawBlurredShadow(this.x, this.y, (float)(CFontManager.manrope.getStringWidth(s) + (0x92 ^ 0x9C)), 15.0f, 5, new Color(178310089 + 1701378365 - 1230440663 + 1464681425, true));
        CFontManager.manrope.drawString(s, this.x + 7.0f, this.y + 1.5f, playerInfo.color.getColor().getRGB());
        ScaleUtils.scale_post();
        this.width = (float)(CFontManager.manrope.getStringWidth(s) + (0x4E ^ 0x40));
    }
}
