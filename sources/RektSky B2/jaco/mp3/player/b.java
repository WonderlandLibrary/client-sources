package jaco.mp3.player;

import java.net.*;
import jaco.mp3.a.*;

final class b extends Thread
{
    final /* synthetic */ MP3Player a;
    
    b(final MP3Player a) {
        this.a = a;
    }
    
    @Override
    public final void run() {
        final jaco.mp3.a.b b = new jaco.mp3.a.b();
        z z;
        try {
            z = new z(this.a.c.get(this.a.d).openStream());
        }
        catch (Exception ex) {
            z = null;
            ex.printStackTrace();
        }
        if (z != null) {
            jaco.mp3.a.a.a a;
            try {
                (a = new F()).a(b);
            }
            catch (Exception ex2) {
                a = null;
                try {
                    z.a();
                }
                catch (Exception ex5) {}
                ex2.printStackTrace();
            }
            if (a != null) {
                try {
                    while (!this.a.i) {
                        if (this.a.g) {
                            Thread.sleep(100L);
                        }
                        else {
                            final D b2;
                            if ((b2 = z.b()) == null) {
                                break;
                            }
                            final A a2 = (A)b.a(b2, z);
                            a.a(a2.a(), 0, a2.b());
                            z.d();
                        }
                    }
                }
                catch (Exception ex3) {
                    ex3.printStackTrace();
                }
                if (!this.a.i) {
                    a.c();
                }
                a.a();
            }
            try {
                z.a();
            }
            catch (Exception ex4) {
                ex4.printStackTrace();
            }
        }
        if (!this.a.i) {
            new a(this).start();
        }
        MP3Player.a(this.a, false);
        MP3Player.b(this.a, true);
    }
}
