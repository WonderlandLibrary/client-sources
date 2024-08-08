package net.futureclient.client;

import org.lwjgl.input.Mouse;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.futureclient.client.utils.NumberValue;
import net.minecraft.client.Minecraft;

public class Ra extends pa
{
    private Minecraft a;
    private NumberValue D;
    private boolean k;
    
    public Ra(final NumberValue d) {
        super(d.M()[0]);
        this.a = Minecraft.getMinecraft();
        this.D = d;
    }
    
    private boolean M() {
        return this.k;
    }
    
    private void M(final boolean k) {
        this.k = k;
    }
    
    @Override
    public void M(final int n, final int n2, final float n3) {
        this.M(n, n2);
        int n4;
        if (this.D.B().doubleValue() > this.D.M().doubleValue()) {
            n4 = this.e() + 7;
        }
        else {
            n4 = (int)(this.D.B().doubleValue() / this.D.M().doubleValue() * (this.e() + 7));
        }
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final ye ye = (ye)pg.M().M().M((Class)ye.class);
        xG.M(this.M() + this.D, this.e() + this.M, this.M() + n4 + this.D, this.e() + this.M() + 1.0f + this.M, this.M(n, n2) ? (ye.L.getRGB() + 1879048192) : (ye.L.getRGB() - 1728053248));
        if (gd.E.M()) {
            GlStateManager.enableBlend();
            gd.p.M(String.format("%s§7 %s", this.M(), this.D.B()), this.j + 2.0f, (double)(this.a + 4.0f), 16777215);
            GlStateManager.disableBlend();
            return;
        }
        this.a.fontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.M(), this.D.B()), this.j + 2.0f, this.a + 4.0f, 16777215);
    }
    
    private boolean M(final int n, final int n2) {
        final Iterator<XA> iterator = KC.M().M().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().E) {
                return false;
            }
        }
        return n >= this.M() && n <= this.M() + (this.e() + 7) && n2 >= this.e() && n2 <= this.e() + this.K;
    }
    
    @Override
    public void M(final int n, final int n2, final int n3) {
        super.M(n, n2, n3);
        if (this.M(n, n2) && n3 == 0) {
            final boolean b = true;
            this.a.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            this.M(b);
        }
    }
    
    private void M(final int n, final int n2) {
        if (!Mouse.isButtonDown(0)) {
            this.M(false);
        }
        if (this.M() && this.M(n, n2)) {
            this.D.M(Math.round((n - this.M()) / (this.e() + 7) * this.D.M().doubleValue() * (1.0 / this.D.a.doubleValue())) / (1.0 / this.D.a.doubleValue()));
            if (this.D.B().doubleValue() < this.D.b().doubleValue()) {
                this.D.M(this.D.b());
                return;
            }
            if (this.D.B().doubleValue() > this.D.M().doubleValue()) {
                this.D.M(this.D.M());
            }
        }
    }
    
    @Override
    public int M() {
        return 14;
    }
}
