package com.client.glowclient;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class MC
{
    public final EnumFacing G;
    public final int d;
    public final JB L;
    public final RenderChunk A;
    public final Set<EnumFacing> B;
    public final RA b;
    
    public MC(final JB l, final RenderChunk a, final RA b, final EnumFacing g, final int d) {
        final Class<EnumFacing> clazz = EnumFacing.class;
        this.L = l;
        super();
        this.B = (Set<EnumFacing>)EnumSet.noneOf((Class<Enum>)clazz);
        this.A = a;
        this.b = b;
        this.G = g;
        this.d = d;
    }
}
