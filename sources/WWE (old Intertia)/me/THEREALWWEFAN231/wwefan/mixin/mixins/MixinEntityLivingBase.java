package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { EntityLivingBase.class }, priority = 9999)
public class MixinEntityLivingBase extends MixinEntity
{
    public MixinEntityLivingBase() {
        super();
    }
    
    @Shadow
    public boolean func_184613_cA() {
        return false;
    }
    
    @Shadow
    public void func_70664_aZ() {
    }
}
