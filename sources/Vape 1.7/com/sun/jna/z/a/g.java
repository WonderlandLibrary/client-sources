package com.sun.jna.z.a;

import net.minecraft.client.*;
import com.sun.jna.z.a.f.*;
import com.sun.jna.z.a.d.*;
import net.minecraftforge.client.event.*;
import md.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class g
{
    Entity a;
    c b;
    c c;
    Minecraft d;
    boolean e;
    double f;
    Entity g;
    boolean h;
    m i;
    private static final String[] j;
    
    public g() {
        this.f = 3.0;
        this.i = new m();
        final e a2 = com.sun.jna.z.a.h.d.a;
        final String[] a = com.sun.jna.z.a.g.j;
        this.b = a2.a(a[1]);
        this.c = com.sun.jna.z.a.h.d.a.a(a[0]);
        this.d = Minecraft.func_71410_x();
    }
    
    public void a(final MouseEvent a) {
        boolean a2 = false;
        boolean a3 = false;
        final int n = com.sun.jna.z.a.h.n;
        double a4 = 3.0;
        final int a5 = n;
        double a6 = 0.0;
        c c2;
        final c c = c2 = this.b;
        Label_0067: {
            if (a5 == 0) {
                if (c != null) {
                    final a a7 = (a)this.b;
                    final a c3;
                    final j j = (j)(c3 = (a)(c2 = a7));
                    if (a5 != 0) {
                        break Label_0067;
                    }
                    if (j.c()) {
                        a2 = true;
                        a4 = a7.n.a();
                    }
                }
                a c3;
                c2 = (c3 = (a)this.c);
            }
        }
        boolean c4 = false;
        final boolean b;
        Label_0111: {
            Label_0110: {
                if (a5 == 0) {
                    if (c == null) {
                        break Label_0110;
                    }
                    c2 = this.c;
                }
                final j a8 = (j)c2;
                b = (c4 = a8.c());
                if (a5 != 0) {
                    break Label_0111;
                }
                if (b) {
                    a3 = true;
                    a6 = a8.n.a();
                }
            }
            final boolean b2;
            c4 = (b2 = a2);
        }
        Label_0124: {
            if (a5 == 0) {
                if (b) {
                    break Label_0124;
                }
                c4 = a3;
            }
            if (!c4) {
                return;
            }
        }
        Minecraft minecraft2;
        final Minecraft minecraft = minecraft2 = this.d;
        if (a5 == 0) {
            if (minecraft.field_71451_h == null) {
                return;
            }
            final Minecraft d;
            minecraft2 = (d = this.d);
        }
        if (a5 == 0) {
            if (minecraft.field_71441_e == null) {
                return;
            }
            this.d.field_147125_j = null;
            minecraft2 = this.d;
        }
        double a9 = minecraft2.field_71442_b.func_78757_d();
        this.d.field_71476_x = this.d.field_71451_h.func_70614_a(a9, 0.0f);
        double a10 = a9;
        final Vec3 a11 = this.d.field_71451_h.func_70666_h(0.0f);
        double func_78749_i;
        final int n2 = (int)(func_78749_i = (this.d.field_71442_b.func_78749_i() ? 1 : 0));
        Label_0263: {
            final double n3;
            Label_0261: {
                if (a5 == 0) {
                    if (n2 != 0) {
                        a9 = 6.0;
                        a10 = 6.0;
                        if (a5 == 0) {
                            break Label_0263;
                        }
                    }
                    n3 = a9;
                    if (a5 != 0) {
                        break Label_0261;
                    }
                    func_78749_i = dcmpl(n3, 3.0);
                }
                if (func_78749_i > 0) {
                    a10 = 3.0;
                }
            }
            a9 = n3;
        }
        final MovingObjectPosition field_71476_x = this.d.field_71476_x;
        Label_0294: {
            if (a5 == 0) {
                if (field_71476_x == null) {
                    break Label_0294;
                }
                final MovingObjectPosition field_71476_x2 = this.d.field_71476_x;
            }
            a10 = field_71476_x.field_72307_f.func_72438_d(a11);
        }
        final boolean b3 = a2;
        Label_0483: {
            g g = null;
            Label_0477: {
                if (a5 == 0) {
                    if (!b3) {
                        break Label_0483;
                    }
                    a10 = a4;
                    a9 = a4;
                    g = this;
                    if (a5 != 0) {
                        break Label_0477;
                    }
                    final boolean e = this.e;
                }
                if (b3) {
                    g = this;
                    if (a5 != 0) {
                        break Label_0477;
                    }
                    if (((a)this.b).o.a()) {
                        g g2 = this;
                        final double f;
                        Label_0470: {
                            Label_0467: {
                                if (a5 == 0) {
                                    if (this.g != null) {
                                        double field_70172_ad;
                                        final int n4 = (int)(field_70172_ad = this.g.field_70172_ad);
                                        if (a5 == 0) {
                                            if (n4 > 18) {
                                                this.h = true;
                                            }
                                            f = this.f;
                                            if (a5 != 0) {
                                                break Label_0470;
                                            }
                                            field_70172_ad = dcmpg(f, a4);
                                        }
                                        if (field_70172_ad < 0) {
                                            g2 = this;
                                            if (a5 != 0) {
                                                break Label_0467;
                                            }
                                            if (this.h) {
                                                g2 = this;
                                                if (a5 != 0) {
                                                    break Label_0467;
                                                }
                                                if (this.g.field_70172_ad <= 18) {
                                                    this.h = false;
                                                    this.f += 0.5;
                                                    g g3 = this;
                                                    if (a5 == 0) {
                                                        if (this.f > a4) {
                                                            this.f = a4;
                                                        }
                                                        g3 = this;
                                                    }
                                                    g3.i.c();
                                                }
                                            }
                                        }
                                    }
                                    a10 = this.f;
                                    g2 = this;
                                }
                            }
                            final double f2 = g2.f;
                        }
                        a9 = f;
                        if (a5 == 0) {
                            break Label_0483;
                        }
                    }
                }
                g = this;
            }
            g.f = 3.0;
        }
        final Vec3 a12 = this.d.field_71451_h.func_70676_i(0.0f);
        final Vec3 a13 = a11.func_72441_c(a12.field_72450_a * a9, a12.field_72448_b * a9, a12.field_72449_c * a9);
        this.a = null;
        Vec3 a14 = null;
        final float a15 = 1.0f;
        final List a16 = this.d.field_71441_e.func_72839_b((Entity)this.d.field_71451_h, this.d.field_71451_h.field_70121_D.func_72321_a(a12.field_72450_a * a9, a12.field_72448_b * a9, a12.field_72449_c * a9).func_72314_b((double)a15, (double)a15, (double)a15));
        double a17 = a10;
        int a18 = 0;
    Label_1079:
        while (true) {
        Label_1078_Outer:
            while (true) {
                g g5 = null;
                Label_1050: {
                    while (true) {
                        while (a18 < a16.size()) {
                            final Entity a19 = a16.get(a18);
                            if (a5 == 0) {
                                final boolean func_70067_L = a19.func_70067_L();
                                if (a5 != 0) {
                                    if (func_70067_L) {
                                        this.e = false;
                                        this.i.c();
                                    }
                                    final g g4 = this;
                                    g4.g = this.a;
                                    return;
                                }
                                Label_0840: {
                                    if (func_70067_L) {
                                        final float a20 = (float)(a19.func_70111_Y() + (a3 ? a6 : 0.0));
                                        final AxisAlignedBB a21 = a19.field_70121_D.func_72314_b((double)a20, (double)a20, (double)a20);
                                        final MovingObjectPosition a22 = a21.func_72327_a(a11, a13);
                                        double func_72318_a;
                                        final int n5 = (int)(func_72318_a = (a21.func_72318_a(a11) ? 1 : 0));
                                        Label_0772: {
                                            if (a5 == 0) {
                                                if (n5 == 0) {
                                                    break Label_0772;
                                                }
                                                final int n6;
                                                func_72318_a = (n6 = dcmpg(0.0, a17));
                                            }
                                            Label_0738: {
                                                if (a5 == 0) {
                                                    if (n5 < 0) {
                                                        break Label_0738;
                                                    }
                                                    func_72318_a = dcmpl(a17, 0.0);
                                                }
                                                if (func_72318_a != 0) {
                                                    break Label_0840;
                                                }
                                            }
                                            this.a = a19;
                                            final MovingObjectPosition movingObjectPosition = a22;
                                            a14 = ((a5 == 0 && movingObjectPosition == null) ? a11 : movingObjectPosition.field_72307_f);
                                            a17 = 0.0;
                                            if (a5 == 0) {
                                                break Label_0840;
                                            }
                                        }
                                        if (a22 != null) {
                                            final double a23 = a11.func_72438_d(a22.field_72307_f);
                                            final double n7 = dcmpg(a23, a17);
                                            Label_0808: {
                                                if (a5 == 0) {
                                                    if (n7 < 0) {
                                                        break Label_0808;
                                                    }
                                                    final double n8 = dcmpl(a17, 0.0);
                                                }
                                                if (n7 != 0) {
                                                    break Label_0840;
                                                }
                                            }
                                            if (a19 == this.d.field_71451_h.field_70154_o) {}
                                            this.a = a19;
                                            a14 = a22.field_72307_f;
                                            a17 = a23;
                                        }
                                    }
                                }
                                ++a18;
                            }
                            if (a5 != 0) {
                                break;
                            }
                        }
                        Entity entity2;
                        final Entity entity = entity2 = this.a;
                        Label_0979: {
                            if (a5 == 0) {
                                g g6 = null;
                                Label_0972: {
                                    Label_0971: {
                                        if (entity != null) {
                                            final double n9 = dcmpg(a17, a10);
                                            if (a5 == 0) {
                                                if (n9 >= 0) {
                                                    final g g4 = this;
                                                    g5 = this;
                                                    g6 = this;
                                                    if (a5 != 0) {
                                                        break Label_0972;
                                                    }
                                                    if (this.d.field_71476_x != null) {
                                                        break Label_0971;
                                                    }
                                                }
                                                final EntityClientPlayerMP entityClientPlayerMP = (EntityClientPlayerMP)(entity2 = (Entity)this.d.field_71439_g);
                                                if (a5 != 0) {
                                                    break Label_0979;
                                                }
                                                entityClientPlayerMP.func_70685_l(this.a);
                                            }
                                            if (n9 != 0) {
                                                this.d.field_71476_x = new MovingObjectPosition(this.a, a14);
                                                final Entity entity3 = entity2 = this.a;
                                                if (a5 != 0) {
                                                    break Label_0979;
                                                }
                                                if (entity3 instanceof EntityLivingBase) {
                                                    final EntityLivingBase a24 = (EntityLivingBase)this.a;
                                                    final EntityLivingBase entityLivingBase = (EntityLivingBase)(entity2 = (Entity)a24);
                                                    if (a5 != 0) {
                                                        break Label_0979;
                                                    }
                                                    if (!entityLivingBase.field_70128_L) {
                                                        this.d.field_147125_j = this.a;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    final g g4 = this;
                                    g5 = this;
                                    g6 = this;
                                }
                                if (a5 != 0) {
                                    break Label_1050;
                                }
                                entity2 = g6.g;
                            }
                        }
                        if (entity2 != null) {
                            final boolean equals = this.g.equals((Object)this.a);
                            if (a5 == 0) {
                                if (equals) {
                                    this.e = true;
                                    this.i.c();
                                    if (a5 == 0) {
                                        continue;
                                    }
                                }
                                final g g4 = this;
                                if (a5 != 0) {
                                    continue Label_1079;
                                }
                                this.i.a(100L);
                            }
                            if (!equals) {
                                continue;
                            }
                            this.e = false;
                            this.i.c();
                            if (a5 == 0) {
                                continue;
                            }
                        }
                        break;
                    }
                    final g g4 = this;
                    g5 = this;
                }
                if (a5 == 0) {
                    g5.i.a(100L);
                    continue Label_1078_Outer;
                }
                break;
            }
            continue Label_1079;
        }
    }
    
    static {
        final String[] i = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "%\u0007\u00dcU`?E\u001e\u0005?\u000b\u00c9tg").length();
        int char1 = 8;
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
                    Label_0185: {
                        if (n4 > 1) {
                            break Label_0185;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 'm';
                                    break;
                                }
                                case 1: {
                                    c2 = 'n';
                                    break;
                                }
                                case 2: {
                                    c2 = '¨';
                                    break;
                                }
                                case 3: {
                                    c2 = '\u0017';
                                    break;
                                }
                                case 4: {
                                    c2 = '\u000f';
                                    break;
                                }
                                case 5: {
                                    c2 = 'G';
                                    break;
                                }
                                default: {
                                    c2 = ' ';
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
                i[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        j = i;
    }
}
