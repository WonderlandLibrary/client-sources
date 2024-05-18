package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import wwe.modules.combat.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderManager.class }, priority = 9999)
public class MixinRenderManager
{
    public MixinRenderManager() {
        super();
    }
    
    @Redirect(method = { "renderDebugBoundingBox" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.getEntityBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;"))
    public AxisAlignedBB renderDebugBoundingBox(final Entity entity) {
        final double doubleValue = vlwv.hNxW.qHPF((Class<HitBoxes>)QiaY.class).hitBoxSize.OnGi();
        return (vlwv.hNxW.qHPF((Class<HitBoxes>)QiaY.class).IuiN() && vlwv.hNxW.qHPF((Class<HitBoxes>)QiaY.class).entityBox.OnGi()) ? entity.getEntityBoundingBox().grow(doubleValue, doubleValue, doubleValue) : entity.getEntityBoundingBox();
    }
}
