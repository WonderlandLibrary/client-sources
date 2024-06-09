package com.client.glowclient.sponge.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;

@Mixin({ EntityBoat.class })
public abstract class MixinEntityBoat extends Entity
{
    @Shadow
    private float field_184472_g;
    @Shadow
    private float field_184474_h;
    @Shadow
    private float field_184475_as;
    @Shadow
    private int field_184476_at;
    @Shadow
    private double field_70281_h;
    @Shadow
    private double field_184477_av;
    @Shadow
    private double field_184478_aw;
    @Shadow
    private double field_70273_g;
    @Shadow
    private double field_184479_ay;
    @Shadow
    private boolean field_184480_az;
    @Shadow
    private boolean field_184459_aA;
    @Shadow
    private boolean field_184461_aB;
    @Shadow
    private boolean field_184463_aC;
    @Shadow
    private double field_184465_aD;
    @Shadow
    private float field_184467_aE;
    @Shadow
    private EntityBoat.Status field_184469_aF;
    @Shadow
    private EntityBoat.Status field_184471_aG;
    @Shadow
    private double field_184473_aH;
    
    public MixinEntityBoat() {
        super((World)null);
    }
    
    @Shadow
    public float getWaterLevelAbove() {
        return 1.0f;
    }
    
    @Shadow
    public void setPaddleState(final boolean b, final boolean b2) {
    }
    
    @Overwrite
    private void controlBoat() {
        if (this.isBeingRidden()) {
            float n = 0.0f;
            if (!HookTranslator.v2) {
                if (this.leftInputDown) {
                    --this.deltaRotation;
                }
                if (this.rightInputDown) {
                    ++this.deltaRotation;
                }
            }
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                n += 0.005f;
            }
            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                n += 0.04f;
            }
            if (this.backInputDown) {
                n -= 0.005f;
            }
            this.motionX += MathHelper.sin(-this.rotationYaw * 0.017453292f) * n;
            this.motionZ += MathHelper.cos(this.rotationYaw * 0.017453292f) * n;
            this.setPaddleState((this.rightInputDown && !this.leftInputDown) || this.forwardInputDown, (this.leftInputDown && !this.rightInputDown) || this.forwardInputDown);
        }
    }
    
    @Overwrite
    private void updateMotion() {
        double n;
        if (HookTranslator.v1) {
            n = 0.0;
        }
        else {
            n = -0.03999999910593033;
        }
        double n2 = n;
        double n3 = 0.0;
        this.momentum = 0.05f;
        if (this.previousStatus == EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.ON_LAND) {
            this.waterLevel = this.getEntityBoundingBox().minY + this.height;
            this.setPosition(this.posX, this.getWaterLevelAbove() - this.height + 0.101, this.posZ);
            this.motionY = 0.0;
            this.lastYd = 0.0;
            this.status = EntityBoat.Status.IN_WATER;
        }
        else {
            if (this.status == EntityBoat.Status.IN_WATER) {
                n3 = (this.waterLevel - this.getEntityBoundingBox().minY) / this.height;
                this.momentum = 0.9f;
            }
            else if (this.status == EntityBoat.Status.UNDER_FLOWING_WATER) {
                n2 = -7.0E-4;
                this.momentum = 0.9f;
            }
            else if (this.status == EntityBoat.Status.UNDER_WATER) {
                n3 = 0.009999999776482582;
                this.momentum = 0.45f;
            }
            else if (this.status == EntityBoat.Status.IN_AIR) {
                this.momentum = 0.9f;
            }
            else if (this.status == EntityBoat.Status.ON_LAND) {
                this.momentum = this.boatGlide;
                if (this.getControllingPassenger() instanceof EntityPlayer) {
                    this.boatGlide /= 2.0f;
                }
            }
            this.motionX *= this.momentum;
            this.motionZ *= this.momentum;
            this.deltaRotation *= this.momentum;
            this.motionY += n2;
            if (n3 > 0.0) {
                this.motionY += n3 * 0.06153846016296973;
                this.motionY *= 0.75;
            }
        }
    }
    
    @Overwrite
    protected void applyYawToEntity(final Entity entity) {
        entity.setRenderYawOffset(this.rotationYaw);
        final float wrapDegrees = MathHelper.wrapDegrees(entity.rotationYaw - this.rotationYaw);
        final float clamp = MathHelper.clamp(wrapDegrees, -105.0f, 105.0f);
        if (!HookTranslator.v8) {
            entity.prevRotationYaw += clamp - wrapDegrees;
            entity.rotationYaw += clamp - wrapDegrees;
        }
        entity.setRotationYawHead(entity.rotationYaw);
    }
}
