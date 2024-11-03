package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerMoveC2SPacket.class)
public interface PlayerMoveC2SPacketAccessor {
    @Accessor
    double getX();

    @Mutable
    @Accessor
    void setX(double x);

    @Accessor
    double getY();

    @Mutable
    @Accessor
    void setY(double y);

    @Accessor
    double getZ();

    @Mutable
    @Accessor
    void setZ(double z);

    @Accessor
    float getYaw();

    @Mutable
    @Accessor
    void setYaw(float yaw);

    @Accessor
    float getPitch();

    @Mutable
    @Accessor
    void setPitch(float pitch);

    @Accessor
    boolean isOnGround();

    @Mutable
    @Accessor
    void setOnGround(boolean onGround);

    @Accessor
    boolean isChangePosition();

    @Mutable
    @Accessor
    void setChangePosition(boolean changePosition);

    @Accessor
    boolean isChangeLook();

    @Mutable
    @Accessor
    void setChangeLook(boolean changeLook);

    @Invoker("<init>")
    static PlayerMoveC2SPacket createPlayerMoveC2SPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changePosition, boolean changeLook) {
        throw new UnsupportedOperationException();
    }
}
