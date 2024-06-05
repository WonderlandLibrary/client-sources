package cn.Hanabi.injection.mixins;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Block.class })
public abstract class MixinBlock
{
    @Shadow
    protected double minX;
    @Shadow
    protected double minY;
    @Shadow
    protected double minZ;
    @Shadow
    protected double maxX;
    @Shadow
    protected double maxY;
    @Shadow
    protected double maxZ;
    int blockID;
    
    public MixinBlock() {
        this.blockID = 0;
    }
    
    @Shadow
    public abstract boolean isBlockNormalCube();
}
