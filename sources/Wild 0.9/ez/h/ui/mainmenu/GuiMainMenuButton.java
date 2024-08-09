package ez.h.ui.mainmenu;

import java.awt.*;
import ez.h.ui.fonts.*;

public class GuiMainMenuButton extends bja
{
    public void render(final bib bib, final int n, final int n2, final float n3) {
        if (this.m) {
            final bip k = bib.k;
            this.a(this.n = (n >= this.h && n2 >= this.i && n < this.h + this.f && n2 < this.i + this.g));
            bus.m();
            bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
            bus.a(bus.r.l, bus.l.j);
            this.a(bib, n, n2);
            int rgb = new Color(107 + 241 - 175 + 82, 218 + 220 - 208 + 25, 95 + 221 - 143 + 82, 0x1D ^ 0x67).getRGB();
            if (!this.l) {
                rgb = 2180293 + 10256318 - 4420983 + 2511252;
            }
            else if (this.n) {
                rgb = -1;
            }
            bus.G();
            CFontManager.montserratmedium.drawString(this.j, (float)this.h, this.i + this.g / 4.0f, rgb);
            bus.I();
            bus.H();
        }
    }
    
    public GuiMainMenuButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, 94 + 109 - 53 + 50, 0xD ^ 0x19, s);
    }
    
    public GuiMainMenuButton(final int k, final int h, final int i, final int f, final int g, final String j) {
        super(k, h, i, f, g, j);
        this.f = 140 + 193 - 260 + 127;
        this.g = (0x45 ^ 0x51);
        this.l = true;
        this.m = true;
        this.k = k;
        this.h = h;
        this.i = i;
        this.f = f;
        this.g = g;
        this.j = j;
    }
}
