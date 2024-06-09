package com.sun.jna.z.a;

import net.minecraft.client.*;
import com.sun.jna.z.a.e.a.a.a.f.*;
import com.sun.jna.z.a.e.a.a.a.*;
import com.sun.jna.z.a.f.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import javax.swing.*;
import java.awt.*;
import net.minecraft.item.*;

public class i
{
    public Minecraft a;
    private b b;
    public h c;
    public f d;
    public j e;
    public static i f;
    public static int g;
    
    public i() {
        this.a = Minecraft.func_71410_x();
        i.f = this;
        (this.e = new j()).a(24, 1, com.sun.jna.z.a.f.b.THREE_DIMENSIONAL);
        this.d = new f();
    }
    
    public boolean a() {
        return true;
    }
    
    public Minecraft b() {
        return this.a;
    }
    
    public EntityPlayerSP c() {
        return (EntityPlayerSP)this.a.field_71439_g;
    }
    
    public b d() {
        final int a = com.sun.jna.z.a.h.n;
        final b b = this.b;
        if (a == 0) {
            if (b == null) {
                this.b = new b(this.c);
            }
            final b b2 = this.b;
        }
        return b;
    }
    
    public boolean a(final EntityLivingBase a) {
        final int a2 = com.sun.jna.z.a.h.n;
        final boolean b = a instanceof EntityPlayer;
        EntityLivingBase entityLivingBase2 = null;
        Label_0122: {
            if (a2 == 0) {
                if (b) {
                    final EntityPlayer a3 = (EntityPlayer)a;
                    boolean a4 = false;
                    final ItemStack[] a5 = a3.field_71071_by.field_70460_b;
                    final int a6 = a5.length;
                    int a7 = 0;
                    while (true) {
                        boolean b2 = false;
                        while (a7 < a6) {
                            final ItemStack a8 = a5[a7];
                            if (a2 == 0) {
                                final ItemStack itemStack = a8;
                                if (a2 != 0) {
                                    if (itemStack == null) {
                                        b2 = a4;
                                        if (a2 == 0 && b2) {}
                                    }
                                    return b2;
                                }
                                if (itemStack != null) {
                                    a4 = true;
                                }
                                ++a7;
                            }
                            if (a2 != 0) {
                                break;
                            }
                        }
                        EntityLivingBase entityLivingBase = a;
                        if (a2 == 0) {
                            if (!a.func_82150_aj()) {
                                return b2;
                            }
                            entityLivingBase = a;
                        }
                        entityLivingBase.func_70694_bm();
                        continue;
                    }
                }
                entityLivingBase2 = a;
                if (a2 != 0) {
                    break Label_0122;
                }
                a.func_82150_aj();
            }
            if (!b) {
                return false;
            }
            entityLivingBase2 = a;
        }
        if (entityLivingBase2.func_70694_bm() == null) {
            return true;
        }
        return false;
    }
    
    public boolean a(final Entity a) {
        final int a2 = com.sun.jna.z.a.h.n;
        final boolean b = a instanceof EntityLivingBase;
        if (a2 == 0) {
            if (b) {
                final EntityLivingBase a3 = (EntityLivingBase)a;
                float field_70128_L;
                final boolean b2 = (field_70128_L = (a.field_70128_L ? 1 : 0)) != 0.0f;
                if (a2 == 0) {
                    if (!b2) {
                        final float n = field_70128_L = fcmpg(a3.func_110143_aJ(), 0.0f);
                        if (a2 != 0) {
                            return field_70128_L != 0.0f;
                        }
                        if (n > 0) {
                            field_70128_L = (false ? 1 : 0);
                            return field_70128_L != 0.0f;
                        }
                    }
                    field_70128_L = (true ? 1 : 0);
                }
                return field_70128_L != 0.0f;
            }
            final boolean field_70128_L2 = a.field_70128_L;
        }
        return b;
    }
    
    public boolean b(final Entity a) {
        final int a2 = com.sun.jna.z.a.h.n;
        Entity entity = a;
        if (a2 == 0) {
            if (a == null) {
                return false;
            }
            entity = a;
        }
        boolean equals;
        final boolean b = equals = entity.equals((Object)this.c());
        if (a2 == 0) {
            if (b) {
                return false;
            }
            final boolean b2;
            equals = (b2 = (a instanceof EntityLivingBase));
        }
        if (a2 == 0) {
            if (b) {
                boolean b7;
                boolean b6;
                boolean b5;
                boolean b4;
                final boolean b3 = b4 = (b5 = (b6 = (b7 = this.a((EntityLivingBase)a))));
                if (a2 == 0) {
                    if (b3) {
                        return false;
                    }
                    final boolean b8;
                    b4 = (b8 = (b5 = (b6 = (b7 = i.f.d.a(a)))));
                }
                if (a2 == 0) {
                    if (b3) {
                        return false;
                    }
                    b4 = (b6 = (b7 = i.f.c.l.a()));
                }
                final boolean b9;
                Label_0134: {
                    if (a2 == 0) {
                        if (b4) {
                            b9 = (b6 = (b7 = i.f.d.b(a)));
                            if (a2 != 0) {
                                break Label_0134;
                            }
                            if (b9) {
                                return false;
                            }
                        }
                        i.f.c.j.a();
                    }
                }
                final boolean b10;
                Label_0168: {
                    if (a2 == 0) {
                        if (!b9) {
                            b10 = (b7 = (a instanceof EntityOtherPlayerMP));
                            if (a2 != 0) {
                                break Label_0168;
                            }
                            if (b10) {
                                return false;
                            }
                        }
                        i.f.c.k.a();
                    }
                }
                final boolean b11;
                if (a2 == 0) {
                    if (!b10) {
                        b11 = (a instanceof EntityCreature);
                        if (a2 == 0) {
                            if (b11) {
                                return false;
                            }
                        }
                    }
                }
                return b11;
            }
            equals = false;
        }
        return equals;
    }
    
    public static void a(final String a) {
        JOptionPane.showMessageDialog(null, a, "", 1);
    }
    
    public boolean c(final Entity a) {
        final int a2 = com.sun.jna.z.a.h.n;
        boolean a3;
        final boolean b = a3 = i.f.c.l.a();
        if (a2 == 0) {
            if (!b) {
                return false;
            }
            final boolean b2;
            a3 = (b2 = i.f.d.b(a));
        }
        if (a2 == 0) {
            if (!b) {
                return false;
            }
            a3 = true;
        }
        return a3;
        a3 = false;
        return a3;
    }
    
    protected boolean d(final Entity a) {
        return i.f.d.a(a);
    }
    
    protected boolean e() {
        final int a = com.sun.jna.z.a.h.n;
        i f = this;
        if (a == 0) {
            if (this.c() == null) {
                return true;
            }
            f = i.f;
        }
        final boolean a2 = f.c.m.a();
        if (a == 0 && a2) {
            final ItemStack func_71045_bC = this.c().func_71045_bC();
            if (a == 0) {
                if (func_71045_bC == null) {
                    return true;
                }
                this.c().func_71045_bC();
            }
            boolean b2;
            final boolean b = b2 = (func_71045_bC.func_77973_b() instanceof ItemSword);
            if (a == 0) {
                if (b) {
                    return false;
                }
                final boolean b3;
                b2 = (b3 = (this.c().func_71045_bC().func_77973_b() instanceof ItemAxe));
            }
            if (a == 0) {
                if (b) {
                    return false;
                }
                b2 = true;
            }
            return b2;
            b2 = false;
            return b2;
        }
        return a2;
    }
    
    public int a(final EntityLivingBase a, final EntityLivingBase a) {
        final int a2 = com.sun.jna.z.a.h.n;
        final double a3 = a.field_70165_t - a.field_70165_t;
        final double a4 = a.field_70161_v - a.field_70161_v;
        double n3;
        double n2;
        final double n = n2 = (n3 = dcmpl(a4, 0.0));
        double a5 = 0.0;
        final double n8;
        final double n9;
        Label_0204: {
            final double n4;
            Label_0086: {
                if (a2 == 0) {
                    if (n > 0) {
                        n4 = (n3 = dcmpl(a3, 0.0));
                        if (a2 != 0) {
                            break Label_0086;
                        }
                        if (n4 > 0) {
                            a5 = Math.toDegrees(-Math.atan(a3 / a4));
                            if (a2 == 0) {
                                break Label_0204;
                            }
                            int a6 = i.g;
                            i.g = ++a6;
                        }
                    }
                    final double n5 = dcmpl(a4, 0.0);
                }
            }
            final double n6;
            Label_0126: {
                if (a2 == 0) {
                    if (n > 0) {
                        n6 = dcmpg(a3, 0.0);
                        if (a2 != 0) {
                            break Label_0126;
                        }
                        if (n6 < 0) {
                            a5 = Math.toDegrees(-Math.atan(a3 / a4));
                            if (a2 == 0) {
                                break Label_0204;
                            }
                        }
                    }
                    n2 = dcmpg(a4, 0.0);
                }
            }
            Label_0173: {
                if (a2 == 0) {
                    if (n4 < 0) {
                        final double n7 = dcmpl(a3, 0.0);
                        if (a2 != 0) {
                            break Label_0173;
                        }
                        if (n7 > 0) {
                            a5 = -90.0 + Math.toDegrees(Math.atan(a4 / a3));
                            if (a2 == 0) {
                                break Label_0204;
                            }
                        }
                    }
                    n8 = a4;
                    n9 = 0.0;
                    if (a2 != 0) {
                        break Label_0204;
                    }
                    n3 = dcmpg(n8, n9);
                }
            }
            if (n6 < 0) {
                final double n10 = a3;
                final double n11 = 0.0;
                if (a2 == 0) {
                    if (n10 < n11) {
                        a5 = 90.0 + Math.toDegrees(Math.atan(a4 / a3));
                    }
                }
            }
        }
        final double a7 = Math.sqrt(n8 + n9);
        final int n12;
        final int a8 = n12 = (int)(Math.abs(a5 - a.field_70759_as) % 360.0);
        final int n13 = 180;
        final int a9 = (a2 == 0 && n12 <= n13) ? a8 : (n12 - n13);
        return a9;
    }
}
