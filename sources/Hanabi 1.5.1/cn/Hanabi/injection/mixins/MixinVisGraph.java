package cn.Hanabi.injection.mixins;

import net.minecraft.client.renderer.chunk.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraft.util.*;

@Mixin({ VisGraph.class })
public abstract class MixinVisGraph
{
    @Shadow
    private BitSet field_178612_d;
    @Shadow
    private static int[] field_178613_e;
    @Shadow
    private int field_178611_f;
    
    private static int getIndex(final BlockPos pos) {
        return getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
    }
    
    private static int getIndex(final int x, final int y, final int z) {
        return x << 0 | y << 8 | z << 4;
    }
    
    @Shadow
    public abstract Set<EnumFacing> func_178604_a(final int p0);
}
