package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ S12PacketEntityVelocity.class })
public class MixinS12PacketEntityVelocity implements IS12PacketEntityVelocity
{
    @Shadow
    private int motionX;
    @Shadow
    private int motionY;
    @Shadow
    private int motionZ;
    
    @Override
    public void setX(final int f) {
        this.motionX = f;
    }
    
    @Override
    public void setY(final int f) {
        this.motionY = f;
    }
    
    @Override
    public void setZ(final int f) {
        this.motionZ = f;
    }
}
