package com.sun.jna.z.a.d.a;

import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class a extends c
{
    public static int n;
    
    public a(final String a, final b a, final int a, final int a) {
        final int n = a.n;
        super(a, a, a, a);
        int a2 = n;
        if (com.sun.jna.z.a.i.g != 0) {
            a.n = ++a2;
        }
    }
    
    public static void a() {
        a(a.f.d());
    }
    
    public static void a(final GuiScreen a) {
        final int n = a.n;
        a.f.a.field_71462_r = a;
        final int a2 = n;
        Label_0090: {
            if (a2 == 0) {
                if (a == null) {
                    break Label_0090;
                }
                a.f.a.func_71364_i();
            }
            final ScaledResolution a3 = new ScaledResolution(Minecraft.func_71410_x(), Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
            final int a4 = a3.func_78326_a();
            final int a5 = a3.func_78328_b();
            a.func_146280_a(a.f.a, a4, a5);
            a.f.a.field_71454_w = false;
        }
        if (a2 != 0) {
            int a6 = i.g;
            i.g = ++a6;
        }
    }
}
