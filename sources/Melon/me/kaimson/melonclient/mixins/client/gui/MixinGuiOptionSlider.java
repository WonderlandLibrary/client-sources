package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;

@Mixin({ awj.class })
public class MixinGuiOptionSlider extends avs
{
    @Shadow
    private float p;
    @Shadow
    private avh.a q;
    @Shadow
    public boolean o;
    
    public MixinGuiOptionSlider() {
        super(0, 0, 0, "");
    }
    
    protected void b(final ave mc, final int mouseX, final int mouseY) {
        if (this.m) {
            if (this.o) {
                this.p = (mouseX - (this.h + 4)) / (float)(this.f - 8);
                this.p = ns.a(this.p, 0.0f, 1.0f);
                final float f = this.q.d(this.p);
                mc.t.a(this.q, f);
                this.p = this.q.c(f);
                this.j = mc.t.c(this.q);
            }
            GLRectUtils.drawRoundedRect((float)this.h, (float)this.i, (float)(this.h + (int)(this.p * (this.f - 8)) + 8), (float)(this.i + 20), 2.0f, Client.getMainColor(40));
            GLRectUtils.drawRoundedOutline(this.h, this.i, this.h + (int)(this.p * (this.f - 8)) + 8, this.i + 20, 2.0f, 2.0f, Client.getMainColor(255));
            GLRectUtils.drawRoundedOutline(this.h + (int)(this.p * (this.f - 8)), this.i, this.h + (int)(this.p * (this.f - 8)) + 8, this.i + 20, 2.0f, 2.0f, Client.getMainColor(255));
        }
    }
}
