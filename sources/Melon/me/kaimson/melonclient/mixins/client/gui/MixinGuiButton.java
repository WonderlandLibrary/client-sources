package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.*;
import java.awt.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ avs.class })
public abstract class MixinGuiButton
{
    @Shadow
    public int h;
    @Shadow
    public int i;
    @Shadow
    protected int f;
    @Shadow
    protected int g;
    @Shadow
    public boolean m;
    @Shadow
    public boolean l;
    @Shadow
    protected boolean n;
    @Shadow
    public String j;
    
    @Shadow
    protected abstract int a(final boolean p0);
    
    @Shadow
    protected abstract void b(final ave p0, final int p1, final int p2);
    
    @Inject(method = { "drawButton" }, at = { @At("HEAD") }, cancellable = true)
    public void drawButton(final ave mc, final int mouseX, final int mouseY, final CallbackInfo ci) {
        if (this.m) {
            this.n = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
            GLRectUtils.drawRoundedOutline(this.h, this.i, this.h + this.f, this.i + this.g, 3.0f, 2.0f, this.l ? (this.n ? Client.getMainColor(255) : Client.getMainColor(150)) : Client.getMainColor(100));
            GLRectUtils.drawRoundedRect((float)this.h, (float)this.i, (float)(this.h + this.f), (float)(this.i + this.g), 3.0f, this.l ? (this.n ? new Color(0, 0, 0, 100).getRGB() : new Color(30, 30, 30, 100).getRGB()) : new Color(70, 70, 70, 50).getRGB());
            if (SettingsManager.INSTANCE.buttonFont.getBoolean()) {
                Client.textRenderer.drawCenteredString(this.j.toUpperCase(), (float)(this.h + this.f / 2), (float)(this.i + (this.g - 10) / 2), this.l ? (this.n ? 16777120 : 14737632) : 10526880);
            }
            else {
                FontUtils.drawCenteredString(this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, this.l ? (this.n ? 16777120 : 14737632) : 10526880);
            }
            this.b(mc, mouseX, mouseY);
        }
        ci.cancel();
    }
}
