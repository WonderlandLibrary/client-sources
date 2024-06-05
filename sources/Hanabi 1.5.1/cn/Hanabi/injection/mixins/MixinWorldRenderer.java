package cn.Hanabi.injection.mixins;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import java.nio.*;

@Mixin({ WorldRenderer.class })
public abstract class MixinWorldRenderer
{
    @Shadow
    private boolean noColor;
    @Shadow
    private IntBuffer rawIntBuffer;
    
    @Shadow
    public abstract int getColorIndex(final int p0);
}
