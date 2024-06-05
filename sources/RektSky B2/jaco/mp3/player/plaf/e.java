package jaco.mp3.player.plaf;

import jaco.mp3.player.*;
import java.awt.image.*;
import jaco.a.*;

final class e implements c
{
    private /* synthetic */ MP3PlayerUICompact a;
    private final /* synthetic */ BufferedImage b;
    private final /* synthetic */ BufferedImage c;
    private final /* synthetic */ BufferedImage d;
    private final /* synthetic */ BufferedImage e;
    private final /* synthetic */ BufferedImage f;
    private final /* synthetic */ BufferedImage g;
    
    e(final MP3PlayerUICompact a, final BufferedImage b, final BufferedImage c, final BufferedImage d, final BufferedImage e, final BufferedImage f, final BufferedImage g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }
    
    @Override
    public final void a() {
        this.a.a.setIcon(jaco.a.a.a(this.b));
        this.a.a.setRolloverIcon(jaco.a.a.a(this.c));
        this.a.a.setPressedIcon(jaco.a.a.a(this.d));
    }
    
    @Override
    public final void b() {
        this.a.a.setIcon(jaco.a.a.a(this.e));
        this.a.a.setRolloverIcon(jaco.a.a.a(this.f));
        this.a.a.setPressedIcon(jaco.a.a.a(this.g));
    }
}
