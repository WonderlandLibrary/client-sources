package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ C03PacketPlayer.class })
public class MixinC03PacketPlayer implements IC03PacketPlayer
{
    @Shadow
    protected boolean onGround;
    @Shadow
    protected double y;
    @Shadow
    protected float yaw;
    @Shadow
    protected float pitch;
    @Shadow
    protected boolean rotating;
    
    @Override
    public boolean isOnGround() {
        return this.onGround;
    }
    
    @Override
    public void setOnGround(final boolean b) {
        this.onGround = b;
    }
    
    @Override
    public void setPosY(final double y2) {
        this.y = y2;
    }
    
    @Override
    public void setYaw(final float f) {
        this.yaw = f;
    }
    
    @Override
    public float getYaw() {
        return this.yaw;
    }
    
    @Override
    public void setPitch(final float f) {
        this.pitch = f;
    }
    
    @Override
    public float getPitch() {
        return this.pitch;
    }
    
    @Override
    public void setRotate(final boolean b) {
        this.rotating = b;
    }
    
    @Override
    public boolean getRotate() {
        return this.rotating;
    }
}
