package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoveC2SPacket.class)
public interface AccessorPlayerMoveC2SPacket {

    @Accessor("onGround")
    @Mutable
    void hookSetOnGround(boolean onGround);

    @Accessor("x")
    @Mutable
    void hookSetX(double x);

    @Accessor("y")
    @Mutable
    void hookSetY(double y);

    @Accessor("z")
    @Mutable
    void hookSetZ(double z);

    @Accessor("yaw")
    @Mutable
    void hookSetYaw(float yaw);

    @Accessor("pitch")
    @Mutable
    void hookSetPitch(float pitch);
}