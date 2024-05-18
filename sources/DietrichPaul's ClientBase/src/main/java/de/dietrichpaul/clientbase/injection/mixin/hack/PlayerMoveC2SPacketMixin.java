package de.dietrichpaul.clientbase.injection.mixin.hack;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoveC2SPacket.class)
public class PlayerMoveC2SPacketMixin {
    @Shadow
    @Final
    @Mutable
    protected boolean onGround;

    @Mixin(PlayerMoveC2SPacket.class)
    public interface IAccessor {
        @Accessor
        void setOnGround(final boolean state);
    }
}
