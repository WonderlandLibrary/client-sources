package com.sun.jna.z.a.e.a.a.a.e.a;

import com.sun.jna.z.a.e.a.a.a.e.*;
import org.lwjgl.opengl.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import net.minecraft.client.*;
import com.sun.jna.z.a.*;
import org.lwjgl.input.*;
import com.sun.jna.z.a.f.*;
import java.awt.*;

public class a extends b<g>
{
    private final i e;
    private static final String[] f;
    
    a(final i a) {
        super(g.class);
        this.e = a;
        this.b = Color.WHITE;
        this.c = new Color(128, 128, 128, 192);
    }
    
    protected void a(final g a) {
        GL11.glPushMatrix();
        final int a2 = i.f ? 1 : 0;
        this.a(a, false);
        final Rectangle a3 = a.j();
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        final float a4 = 0.3f;
        final Point a5 = a.b();
        k a6 = a.k();
    Label_0218_Outer:
        while (true) {
            boolean b = false;
            while (true) {
                while (a6 != null) {
                    final Point point = a5;
                    point.x -= a6.d();
                    final Point point2 = a5;
                    point2.y -= a6.e();
                    a6 = a6.k();
                    if (a2 != 0) {
                        final boolean c;
                        int n = (c = a.c()) ? 1 : 0;
                        Label_0353: {
                            if (a2 == 0) {
                                g g = null;
                                Label_0345: {
                                    if (b && Minecraft.func_71410_x().field_71462_r != null) {
                                        final boolean b2 = (n = (a.b() ? 1 : 0)) != 0;
                                        if (a2 != 0) {
                                            break Label_0353;
                                        }
                                        if (b2) {
                                            g = a;
                                            if (a2 != 0) {
                                                break Label_0345;
                                            }
                                            if (a.d() != null) {
                                                final int a7 = a.d().length * 17 + 1;
                                                GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
                                                GL11.glBegin(7);
                                                GL11.glVertex2d(1.0, (double)a3.height);
                                                GL11.glVertex2d((double)(a3.width - 1), (double)a3.height);
                                                GL11.glVertex2d((double)(a3.width - 1), (double)(a3.height + a7));
                                                GL11.glVertex2d(1.0, (double)(a3.height + a7));
                                                GL11.glEnd();
                                            }
                                        }
                                    }
                                    g = a;
                                }
                                n = a.b(g.m());
                            }
                        }
                        final int a7 = n;
                        final String a8 = a.b();
                        this.e.a().func_78276_b(a8, a3.width / 2 - this.e.a().func_78256_a(a8) / 2 - 1, a3.height / 2 - this.e.a().field_78288_b / 2, a7);
                        final int c2 = a.c() ? 1 : 0;
                        if (a2 == 0) {
                            if (c2 != 0) {
                                final e b3 = h.d.b;
                                final Color m = a.m();
                                final float a10 = (a3.width - 10) * 4;
                                final float a11 = 14.0f;
                                String a12;
                                if (a.b()) {
                                    final String[] a9 = a.f;
                                    a12 = a9[1];
                                }
                                else {
                                    a12 = a.f[0];
                                }
                                b3.a(m, a10, a11, a12, 0.25f);
                            }
                            GL11.glEnable(3553);
                            GL11.glEnable(2884);
                        }
                        GL11.glDisable(c2);
                        this.a(a, true);
                        GL11.glPopMatrix();
                        return;
                    }
                    if (a2 != 0) {
                        break;
                    }
                }
                int n;
                b = ((n = (a3.contains(a5) ? 1 : 0)) != 0);
                if (a2 != 0) {
                    continue;
                }
                break;
            }
            if (b && Minecraft.func_71410_x().field_71462_r != null) {
                GL11.glColor4f(0.3f, 0.3f, 0.3f, Mouse.isButtonDown(0) ? 0.3f : 0.2f);
                GL11.glBegin(7);
                GL11.glVertex2d(0.0, (double)a4);
                GL11.glVertex2d((double)a3.width, (double)a4);
                GL11.glVertex2d((double)a3.width, (double)(a3.height - a4));
                GL11.glVertex2d(0.0, (double)(a3.height - a4));
                GL11.glEnd();
            }
            continue Label_0218_Outer;
        }
    }
    
    protected Dimension b(final g a) {
        return new Dimension(this.e.a().func_78256_a(a.b()) + 4, this.e.a().field_78288_b + 4);
    }
    
    protected Rectangle[] c(final g a) {
        return new Rectangle[] { new Rectangle(0, 0, a.f(), a.g()) };
    }
    
    protected void a(final g a, final Point a, final int a) {
        final int a2 = i.f ? 1 : 0;
        final int x = a.x;
        final int f = a.f();
        if (a2 == 0) {
            if (x > f) {
                return;
            }
            final int y = a.y;
            a.g();
        }
        if (x <= f) {
            a.a(a);
        }
    }
    
    static {
        final String[] f2 = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "(*1\u0002(*").length();
        int char1 = 3;
        int n2 = -1;
        Label_0021: {
            break Label_0021;
            do {
                char1 = s.charAt(n2);
                ++n2;
                final String s2 = s;
                final int n3 = n2;
                final char[] charArray = s2.substring(n3, n3 + char1).toCharArray();
                int length2;
                int n5;
                final int n4 = n5 = (length2 = charArray.length);
                int n6 = 0;
                while (true) {
                    Label_0187: {
                        if (n4 > 1) {
                            break Label_0187;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 'M';
                                    break;
                                }
                                case 1: {
                                    c2 = 'R';
                                    break;
                                }
                                case 2: {
                                    c2 = '^';
                                    break;
                                }
                                case 3: {
                                    c2 = '¾';
                                    break;
                                }
                                case 4: {
                                    c2 = '\u001e';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u0096';
                                    break;
                                }
                                default: {
                                    c2 = '\u009b';
                                    break;
                                }
                            }
                            charArray[length2] = (char)(c ^ c2);
                            ++n6;
                        } while (n4 == 0);
                    }
                    if (n4 > n6) {
                        continue;
                    }
                    break;
                }
                f2[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        f = f2;
    }
}
