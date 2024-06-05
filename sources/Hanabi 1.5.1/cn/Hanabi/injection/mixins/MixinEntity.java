package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.Combat.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements IEntity
{
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public boolean onGround;
    @Shadow
    private int nextStepDistance;
    @Shadow
    private int fire;
    @Shadow
    private AxisAlignedBB boundingBox;
    @Shadow
    public World worldObj;
    
    @Override
    public int getNextStepDistance() {
        return this.nextStepDistance;
    }
    
    @Override
    public void setNextStepDistance(final int distance) {
        this.nextStepDistance = distance;
    }
    
    @Override
    public int getFire() {
        return this.fire;
    }
    
    @Override
    public void setFire(final int i) {
        this.fire = i;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    @Overwrite
    public float getCollisionBorderSize() {
        if (ModManager.getModule("Hitbox").isEnabled()) {
            return 0.1f + Hitbox.getSize();
        }
        return 0.1f;
    }
}
