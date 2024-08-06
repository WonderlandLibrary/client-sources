package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface ClientPlayerEntityAccessor {
    @Accessor
    float getLastYaw();

    @Accessor
    float getLastPitch();

    @Accessor
    void setLastSneaking(boolean flag);

    @Accessor
    void setLastSprinting(boolean flag);

    @Accessor
    double getLastX();

    @Accessor
    double getLastBaseY();

    @Accessor
    double getLastZ();
}
