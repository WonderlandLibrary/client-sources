package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    void setYaw(float yaw);

    @Accessor
    void setPitch(float pitch);
}
