package net.futureclient.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.client.Minecraft;

public class sa extends Wa
{
    private Minecraft D;
    private R k;
    
    public sa(final R k) {
        super(k.M()[0]);
        this.D = Minecraft.getMinecraft();
        this.k = k;
    }
    
    @Override
    public int M() {
        return 14;
    }
    
    @Override
    public void M(final int n, final int n2, final int n3) {
        super.B(n, n2, n3);
        if (this.e(n, n2)) {
            if (n3 == 0) {
                this.k.M();
                return;
            }
            if (n3 == 1) {
                this.k.e();
                this.D.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
    }
    
    @Override
    public void M() {
    }
    
    @Override
    public void M(final int n, final int n2, final float n3) {
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final ye ye = (ye)pg.M().M().M((Class)ye.class);
        xG.M(this.j, this.a, this.j + this.d + 7.0f, this.a + this.K, this.M() ? (this.e(n, n2) ? (ye.L.getRGB() + 1879048192) : (ye.L.getRGB() - 1728053248)) : (this.e(n, n2) ? -2007673515 : 290805077));
        if (gd.E.M()) {
            GlStateManager.enableBlend();
            gd.p.M(String.format("%s§7 %s", this.M(), this.k.M()), this.j + 2.0f, (double)(this.a + 4.0f), 16777215);
            GlStateManager.disableBlend();
            return;
        }
        this.D.fontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.M(), this.k.M()), this.j + 2.0f, this.a + 4.0f, 16777215);
    }
    
    @Override
    public boolean M() {
        return true;
    }
}
