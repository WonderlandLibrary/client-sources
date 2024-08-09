package mathax.client.mixin;

import mathax.client.mixininterface.IPlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerMoveC2SPacket.class)
public class PlayerMoveC2SPacketMixin implements IPlayerMoveC2SPacket {
    @Unique
    private int tag;

    @Override
    public void setNbt(int tag) { this.tag = tag; }

    @Override
    public int getNbt() { return this.tag; }
}
