package net.silentclient.client.mixin.mixins;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityLookHelper;
import net.silentclient.client.mixin.ducks.EntityLivingExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLiving.class)
public class EntityLivingMixin implements EntityLivingExt {
    @Shadow private EntityLookHelper lookHelper;

    @Shadow @Final protected EntityAITasks tasks;

    @Override
    public void client$setLookHelper(Object var1) {
        this.lookHelper = (EntityLookHelper) var1;
    }

    @Override
    public Object client$getTasks() {
        return this.tasks;
    }
}
