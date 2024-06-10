package me.kaimson.melonclient.mixins.client.gui;

import me.kaimson.melonclient.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ avz.class })
public abstract class MixinGuiButtonLanguage extends avs
{
    private final jy language;
    
    public MixinGuiButtonLanguage(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
        this.language = new jy("melonclient/icons/language.png");
    }
    
    @Overwrite
    public void a(final ave mc, final int mouseX, final int mouseY) {
        if (this.m) {
            this.n = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
            GLRectUtils.drawRoundedOutline(this.h, this.i, this.h + this.f, this.i + this.g, 3.0f, 2.0f, this.l ? (this.n ? Client.getMainColor(255) : Client.getMainColor(150)) : Client.getMainColor(100));
            GLRectUtils.drawRoundedRect((float)this.h, (float)this.i, (float)(this.h + this.f), (float)(this.i + this.g), 3.0f, this.l ? (this.n ? new Color(0, 0, 0, 100).getRGB() : new Color(30, 30, 30, 100).getRGB()) : new Color(70, 70, 70, 50).getRGB());
            mc.P().a(this.language);
            GuiUtils.setGlColor(Client.getMainColor(255));
            bfl.l();
            final int b = 12;
            GuiUtils.drawModalRectWithCustomSizedTexture((float)(this.h + b / 3), this.i + b / 3 - 0.5f, 0.0f, 0.0f, b, b, (float)b, (float)b);
        }
    }
}
