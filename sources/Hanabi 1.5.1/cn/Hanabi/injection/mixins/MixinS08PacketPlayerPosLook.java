package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ S08PacketPlayerPosLook.class })
public class MixinS08PacketPlayerPosLook implements IS08PacketPlayerPosLook
{
    @Shadow
    private float yaw;
    @Shadow
    private float pitch;
    
    @Override
    public void setYaw(final float y) {
        this.yaw = y;
    }
    
    @Override
    public void setPitch(final float p) {
        this.pitch = p;
    }
}
