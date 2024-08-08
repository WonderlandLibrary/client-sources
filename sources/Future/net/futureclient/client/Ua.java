package net.futureclient.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class Ua extends Wa
{
    private Minecraft D;
    private Ea k;
    
    public Ua(final Ea k) {
        final int d = 15;
        super("");
        this.D = Minecraft.getMinecraft();
        this.k = k;
        this.d = d;
    }
    
    private void e() {
        if (Keyboard.getEventKeyState() && this.M()) {
            final int eventKey;
            Ua ua;
            if ((eventKey = Keyboard.getEventKey()) == 211 || eventKey == pg.M().M().M(String.format("%sToggle", "ClickGui")).M()) {
                ua = this;
                this.M(this.k, 0);
            }
            else {
                ua = this;
                this.M(this.k, eventKey);
            }
            ua.M(false);
        }
    }
    
    @Override
    public void M(final int n, final int n2, final int n3) {
        super.B(n, n2, n3);
    }
    
    private int M(final Ea ea) {
        return pg.M().M().M(String.format("%sToggle", ea.M())).M();
    }
    
    private void M(final Ea ea, final int n) {
        pg.M().M().M(String.format("%sToggle", ea.M())).M(n);
    }
    
    @Override
    public void M(final int n, final int n2, final float n3) {
        this.e();
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final ye ye = (ye)pg.M().M().M((Class)ye.class);
        xG.M(this.j, this.a, this.j + this.d + 7.0f, this.a + this.K, this.M() ? (this.e(n, n2) ? (ye.L.getRGB() + 1879048192) : (ye.L.getRGB() - 1728053248)) : (this.e(n, n2) ? -2007673515 : 290805077));
        if (gd.E.M()) {
            GlStateManager.enableBlend();
            final KH p3 = gd.p;
            String format;
            Ua ua;
            if (this.M()) {
                format = "Press a Key...";
                ua = this;
            }
            else {
                format = String.format("Keybind §7%s", Keyboard.getKeyName(this.M(this.k)));
                ua = this;
            }
            p3.M(format, ua.j + 2.0f, (double)(this.a + 4.0f), 16777215);
            GlStateManager.disableBlend();
            return;
        }
        final FontRenderer fontRenderer = this.D.fontRenderer;
        String format2;
        Ua ua2;
        if (this.M()) {
            format2 = "Press a Key...";
            ua2 = this;
        }
        else {
            format2 = String.format("Keybind §7%s", Keyboard.getKeyName(this.M(this.k)));
            ua2 = this;
        }
        fontRenderer.drawStringWithShadow(format2, ua2.j + 2.0f, this.a + 4.0f, 16777215);
    }
}
