package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityPlayer.class })
public class MixinEntityPlayer implements IEntityPlayer
{
    @Shadow
    public int itemInUseCount;
    
    @Override
    public void setItemInUseCount(final int i) {
        this.itemInUseCount = i;
    }
}
