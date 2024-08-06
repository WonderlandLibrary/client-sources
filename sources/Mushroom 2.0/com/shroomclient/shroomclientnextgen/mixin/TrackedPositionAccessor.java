package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.entity.TrackedPosition;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TrackedPosition.class)
public interface TrackedPositionAccessor {
    @Accessor
    Vec3d getPos();
}
