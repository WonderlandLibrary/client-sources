package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase implements IEntityLivingBase
{
    @Shadow
    protected abstract int getArmSwingAnimationEnd();
    
    @Override
    public int runGetArmSwingAnimationEnd() {
        return this.getArmSwingAnimationEnd();
    }
}
