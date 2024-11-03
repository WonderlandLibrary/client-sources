package net.silentclient.client.mixin.mixins;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import net.silentclient.client.mixin.ducks.EntityLookHelperExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLookHelper.class)
public class EntityLookHelperMixin implements EntityLookHelperExt {
    @Shadow private EntityLiving entity;

    @Override
    public Object client$getEntity() {
        return this.entity;
    }
}
