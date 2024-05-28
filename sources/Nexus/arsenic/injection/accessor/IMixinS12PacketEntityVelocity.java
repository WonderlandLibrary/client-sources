package arsenic.injection.accessor;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = S12PacketEntityVelocity.class)
public interface IMixinS12PacketEntityVelocity {
    @Accessor
    void setMotionX(int motionX);
    @Accessor
    void setMotionY(int motionY);
    @Accessor
    void setMotionZ(int motionZ);
}
