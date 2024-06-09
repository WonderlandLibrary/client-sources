package com.sun.jna.z.a.e.a.a.a.b;

import net.minecraft.client.gui.*;
import com.sun.jna.z.a.f.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.sun.jna.z.a.*;
import net.minecraft.client.settings.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class a extends FontRenderer
{
    d a;
    public static boolean b;
    private static final String[] c;
    
    public a(final d a) {
        final int b = com.sun.jna.z.a.e.a.a.a.b.a.b ? 1 : 0;
        final GameSettings field_71474_y = Minecraft.func_71410_x().field_71474_y;
        final String[] a2 = com.sun.jna.z.a.e.a.a.a.b.a.c;
        super(field_71474_y, new ResourceLocation(a2[1]), Minecraft.func_71410_x().field_71446_o, false);
        final int a3 = b;
        this.a = a;
        final String a4 = a2[0];
        this.field_78288_b = (int)this.a(a4);
        if (a3 != 0) {
            int a5 = i.g;
            i.g = ++a5;
        }
    }
    
    public int func_78276_b(final String a, int a, int a, final int a) {
        final int a2 = a.b ? 1 : 0;
        if (a2 == 0) {
            if (a == null) {
                return 0;
            }
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
        }
        final boolean a3 = GL11.glIsEnabled(3042);
        final boolean a4 = GL11.glIsEnabled(2896);
        final boolean a5 = GL11.glIsEnabled(3553);
        final boolean a6 = GL11.glIsEnabled(2884);
        int n2;
        int n;
        boolean b6;
        boolean b5;
        boolean b4;
        boolean b3;
        boolean b2;
        final boolean b = b2 = (b3 = (b4 = (b5 = (b6 = ((n = (n2 = (a3 ? 1 : 0))) != 0)))));
        if (a2 == 0) {
            if (!b) {
                GL11.glEnable(3042);
            }
            final boolean b7;
            b2 = (b7 = (b3 = (b4 = (b5 = (b6 = ((n = (n2 = (a4 ? 1 : 0))) != 0))))));
        }
        if (a2 == 0) {
            if (b) {
                GL11.glDisable(2896);
            }
            b3 = (b2 = (b4 = (b5 = (b6 = ((n = (n2 = (a5 ? 1 : 0))) != 0)))));
        }
        if (a2 == 0) {
            if (!b2) {
                GL11.glEnable(3553);
            }
            b4 = (b3 = (b5 = (b6 = ((n = (n2 = (a6 ? 1 : 0))) != 0))));
        }
        if (a2 == 0) {
            if (b3) {
                GL11.glDisable(2884);
            }
            a *= 2;
            a *= 2;
            this.a.a(a, a, a, a);
            b5 = (b4 = (b6 = ((n = (n2 = (a6 ? 1 : 0))) != 0)));
        }
        if (a2 == 0) {
            if (b4) {
                GL11.glEnable(2884);
            }
            b6 = (b5 = ((n = (n2 = (a5 ? 1 : 0))) != 0));
        }
        if (a2 == 0) {
            if (!b5) {
                GL11.glDisable(3553);
            }
            n = ((b6 = ((n2 = (a4 ? 1 : 0)) != 0)) ? 1 : 0);
        }
        if (a2 == 0) {
            if (b6) {
                GL11.glEnable(2896);
            }
            n2 = (n = (a3 ? 1 : 0));
        }
        if (a2 == 0) {
            if (n == 0) {
                GL11.glDisable(3042);
            }
            GL11.glPopMatrix();
            n2 = a;
        }
        return n2;
    }
    
    public int a(final String a, float a, float a, final int a, final int a) {
        final int a2 = a.b ? 1 : 0;
        if (a2 == 0) {
            if (a == null) {
                return 0;
            }
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
        }
        final boolean a3 = GL11.glIsEnabled(3042);
        final boolean a4 = GL11.glIsEnabled(2896);
        final boolean a5 = GL11.glIsEnabled(3553);
        final boolean a6 = GL11.glIsEnabled(2884);
        int n2;
        int n;
        boolean b6;
        boolean b5;
        boolean b4;
        boolean b3;
        boolean b2;
        final boolean b = b2 = (b3 = (b4 = (b5 = (b6 = ((n = (n2 = (a3 ? 1 : 0))) != 0)))));
        if (a2 == 0) {
            if (!b) {
                GL11.glEnable(3042);
            }
            final boolean b7;
            b2 = (b7 = (b3 = (b4 = (b5 = (b6 = ((n = (n2 = (a4 ? 1 : 0))) != 0))))));
        }
        if (a2 == 0) {
            if (b) {
                GL11.glDisable(2896);
            }
            b3 = (b2 = (b4 = (b5 = (b6 = ((n = (n2 = (a5 ? 1 : 0))) != 0)))));
        }
        if (a2 == 0) {
            if (!b2) {
                GL11.glEnable(3553);
            }
            b4 = (b3 = (b5 = (b6 = ((n = (n2 = (a6 ? 1 : 0))) != 0))));
        }
        if (a2 == 0) {
            if (b3) {
                GL11.glDisable(2884);
            }
            a *= 2.0f;
            a *= 2.0f;
            this.a.a(a, a + 1.0f, a + 1.0f, a);
            this.a.a(a, a, a, a);
            b5 = (b4 = (b6 = ((n = (n2 = (a6 ? 1 : 0))) != 0)));
        }
        if (a2 == 0) {
            if (b4) {
                GL11.glEnable(2884);
            }
            b6 = (b5 = ((n = (n2 = (a5 ? 1 : 0))) != 0));
        }
        if (a2 == 0) {
            if (!b5) {
                GL11.glDisable(3553);
            }
            n = ((b6 = ((n2 = (a4 ? 1 : 0)) != 0)) ? 1 : 0);
        }
        if (a2 == 0) {
            if (b6) {
                GL11.glEnable(2896);
            }
            n2 = (n = (a3 ? 1 : 0));
        }
        if (a2 == 0) {
            if (n == 0) {
                GL11.glDisable(3042);
            }
            GL11.glPopMatrix();
            n2 = (int)a;
        }
        return n2;
    }
    
    public int func_78261_a(final String a, int a, int a, final int a) {
        final int a2 = a.b ? 1 : 0;
        if (a2 == 0) {
            if (a == null) {
                return 0;
            }
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
        }
        final boolean a3 = GL11.glIsEnabled(3042);
        final boolean a4 = GL11.glIsEnabled(2896);
        final boolean a5 = GL11.glIsEnabled(3553);
        final boolean a6 = GL11.glIsEnabled(2884);
        int a9;
        int n3;
        int n2;
        final int n = n2 = (n3 = (a9 = (a3 ? 1 : 0)));
        if (a2 == 0) {
            if (n == 0) {
                GL11.glEnable(3042);
            }
            final int n4;
            n2 = (n4 = (n3 = (a9 = (a4 ? 1 : 0))));
        }
        if (a2 == 0) {
            if (n != 0) {
                GL11.glDisable(2896);
            }
            n3 = (n2 = (a9 = (a5 ? 1 : 0)));
        }
        if (a2 == 0) {
            if (n2 == 0) {
                GL11.glEnable(3553);
            }
            a9 = (n3 = (a6 ? 1 : 0));
        }
        if (a2 == 0) {
            if (n3 != 0) {
                GL11.glDisable(2884);
            }
            a *= 2;
            a *= 2;
            a9 = a;
        }
        final Color a7 = a.a(a9);
        final Color a8 = new Color(a7.getBlue(), a7.getGreen(), a7.getRed(), a7.getAlpha());
        this.a.a(a, a + 1.0f, a + 1.0f, -16777216);
        this.a.a(a, a, a, a.b(a8));
        int n7;
        int n6;
        int n5;
        final boolean b = (n5 = (n6 = (n7 = (a6 ? 1 : 0)))) != 0;
        if (a2 == 0) {
            if (b) {
                GL11.glEnable(2884);
            }
            final int n8;
            n5 = (n8 = (n6 = (n7 = (a5 ? 1 : 0))));
        }
        if (a2 == 0) {
            if (!b) {
                GL11.glDisable(3553);
            }
            n6 = (n5 = (n7 = (a4 ? 1 : 0)));
        }
        if (a2 == 0) {
            if (n5 != 0) {
                GL11.glEnable(2896);
            }
            n7 = (n6 = (a3 ? 1 : 0));
        }
        if (a2 == 0) {
            if (n6 == 0) {
                GL11.glDisable(3042);
            }
            GL11.glPopMatrix();
            n7 = a;
        }
        return n7;
    }
    
    public int func_78263_a(final char a) {
        return this.func_78256_a(Character.toString(a));
    }
    
    public int func_78256_a(final String a) {
        final int a2 = a.b ? 1 : 0;
        final int n = (int)(this.a.a(a) / 2.0f);
        if (i.g != 0) {
            a.b = (a2 == 0);
        }
        return n;
    }
    
    public float a(final String a) {
        return this.a.b(a);
    }
    
    static {
        final String[] c2 = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "&]§\u0003\u00ce\u000f_/V®\f\u00c7\u0004V(Oµ\u0015\u00d8\u001dM1H¼\u001e\u00d1(z\u0004{\u0081!\u00ec!q\rt\u0088*\u00e5&h\u0016m\u00973\u00fe?o\u001ff\u009eV\u0099Z\fr\t\u00f3_\u0092\u00173Z¼\u0013\u00de\u001b]4\u0010¢\b\u00c5\u001d\u0017&L§\u000e\u00c2GH)X").length();
        int char1 = 61;
        int n2 = -1;
        Label_0022: {
            break Label_0022;
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
                    Label_0186: {
                        if (n4 > 1) {
                            break Label_0186;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c3 = charArray[n5];
                            char c4 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c4 = 'G';
                                    break;
                                }
                                case 1: {
                                    c4 = '?';
                                    break;
                                }
                                case 2: {
                                    c4 = '\u00c4';
                                    break;
                                }
                                case 3: {
                                    c4 = 'g';
                                    break;
                                }
                                case 4: {
                                    c4 = '«';
                                    break;
                                }
                                case 5: {
                                    c4 = 'i';
                                    break;
                                }
                                default: {
                                    c4 = '8';
                                    break;
                                }
                            }
                            charArray[length2] = (char)(c3 ^ c4);
                            ++n6;
                        } while (n4 == 0);
                    }
                    if (n4 > n6) {
                        continue;
                    }
                    break;
                }
                c2[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        c = c2;
    }
}
