package com.sun.jna.z.a.e.a.a.a;

import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

class w extends g
{
    final com.sun.jna.z.a.e.a.a.a.k o;
    
    w(final com.sun.jna.z.a.e.a.a.a.k a, final String a) {
        this.o = a;
        super(a);
    }
    
    @Override
    public void c() {
        final int a = MathHelper.func_76128_c(Minecraft.func_71410_x().field_71439_g.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
        this.b(Direction.field_82373_c[a]);
    }
}
