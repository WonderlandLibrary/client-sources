package net.futureclient.client;

import net.minecraft.client.renderer.GlStateManager;
import java.util.Iterator;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.client.Minecraft;

public class Wa extends pa implements H
{
    private boolean D;
    private Minecraft k;
    
    public Wa(final String s) {
        final int k = 15;
        super(s);
        this.k = Minecraft.getMinecraft();
        this.K = k;
    }
    
    public void B(final int n, final int n2, final int n3) {
        if (n3 == 0 && this.e(n, n2)) {
            this.D = !this.D;
            this.M();
            this.k.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    public void b(final int n, final int n2, final int n3) {
        if (n3 != 0 || this.M(n, n2)) {}
    }
    
    public boolean e(final int n, final int n2) {
        final Iterator<XA> iterator = KC.M().M().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().E) {
                return false;
            }
        }
        return n >= this.M() && n <= this.M() + this.e() + 6.0f && n2 >= this.e() && n2 <= this.e() + this.K;
    }
    
    @Override
    public void M(final int n, final int n2, final int n3) {
        if (n3 == 0 && this.M(n, n2)) {
            this.D = !this.D;
            this.M();
            this.k.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    public boolean M() {
        return this.D;
    }
    
    @Override
    public void M(final int n, final int n2, final float n3) {
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final ye ye = (ye)pg.M().M().M((Class)ye.class);
        final float j = this.j;
        final float a = this.a;
        final float n4 = this.j + this.d;
        final float n5 = this.a + this.K;
        int n6;
        Wa wa;
        if (this.M()) {
            if (!this.M(n, n2)) {
                n6 = ye.L.getRGB() - 1728053248;
                wa = this;
            }
            else {
                n6 = ye.L.getRGB() + 1879048192;
                wa = this;
            }
        }
        else if (!this.M(n, n2)) {
            n6 = 861230421;
            wa = this;
        }
        else {
            n6 = -2007673515;
            wa = this;
        }
        int n7;
        if (wa.M()) {
            final boolean m = this.M(n, n2);
            final ye ye2 = ye;
            n7 = (m ? (ye2.L.getRGB() + 1879048192) : (ye2.L.getRGB() - 1728053248));
        }
        else {
            n7 = (this.M(n, n2) ? -1722460843 : 1431655765);
        }
        xG.B(j, a, n4, n5, n6, n7);
        if (gd.E.M()) {
            GlStateManager.enableBlend();
            gd.p.M(this.M(), this.j + 2.0f, (double)(this.a + 4.0f), this.M() ? -1 : -5592406);
            GlStateManager.disableBlend();
            return;
        }
        this.k.fontRenderer.drawStringWithShadow(this.M(), this.j + 2.0f, this.a + 4.0f, this.M() ? -1 : -5592406);
    }
    
    @Override
    public int M() {
        return 14;
    }
    
    public boolean M(final int n, final int n2) {
        final Iterator<XA> iterator = KC.M().M().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().E) {
                return false;
            }
        }
        return n >= this.M() && n <= this.M() + this.e() - 1.0f && n2 >= this.e() && n2 <= this.e() + this.K;
    }
    
    @Override
    public void M() {
    }
    
    public void M(final boolean d) {
        this.D = d;
    }
}
