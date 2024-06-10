package ac.sapphire.client.mixin.packet;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(S12PacketEntityVelocity.class)
public interface IVelocityPacketAccessor {
    @Accessor
    void setMotionX(int motionX);

    @Accessor
    void setMotionY(int motionY);

    @Accessor
    void setMotionZ(int motionZ);
}
