package cn.Hanabi.injection.mixins;

import net.minecraft.world.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ BlockModelRenderer.class })
public abstract class MixinBlockModelRenderer
{
    @Shadow
    public abstract boolean renderModelStandard(final IBlockAccess p0, final IBakedModel p1, final Block p2, final BlockPos p3, final WorldRenderer p4, final boolean p5);
    
    @Shadow
    public abstract boolean renderModelAmbientOcclusion(final IBlockAccess p0, final IBakedModel p1, final Block p2, final BlockPos p3, final WorldRenderer p4, final boolean p5);
}
