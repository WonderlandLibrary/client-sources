package net.minecraft.entity.monster;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

class GuardianMoveHelper extends EntityMoveHelper
{
    private EntityGuardian field_179930_g;
    
    public GuardianMoveHelper() {
        super(EntityGuardian.this);
        this.field_179930_g = EntityGuardian.this;
    }
    
    @Override
    public void onUpdateMoveHelper() {
        if (this.update && !this.field_179930_g.getNavigator().noPath()) {
            final double var1 = this.posX - this.field_179930_g.posX;
            double var2 = this.posY - this.field_179930_g.posY;
            final double var3 = this.posZ - this.field_179930_g.posZ;
            double var4 = var1 * var1 + var2 * var2 + var3 * var3;
            var4 = MathHelper.sqrt_double(var4);
            var2 /= var4;
            final float var5 = (float)(Math.atan2(var3, var1) * 180.0 / 3.141592653589793) - 90.0f;
            this.field_179930_g.rotationYaw = this.limitAngle(this.field_179930_g.rotationYaw, var5, 30.0f);
            this.field_179930_g.renderYawOffset = this.field_179930_g.rotationYaw;
            final float var6 = (float)(this.speed * this.field_179930_g.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
            this.field_179930_g.setAIMoveSpeed(this.field_179930_g.getAIMoveSpeed() + (var6 - this.field_179930_g.getAIMoveSpeed()) * 0.125f);
            double var7 = Math.sin((this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.5) * 0.05;
            final double var8 = Math.cos(this.field_179930_g.rotationYaw * 3.1415927f / 180.0f);
            final double var9 = Math.sin(this.field_179930_g.rotationYaw * 3.1415927f / 180.0f);
            final EntityGuardian field_179930_g = this.field_179930_g;
            field_179930_g.motionX += var7 * var8;
            final EntityGuardian field_179930_g2 = this.field_179930_g;
            field_179930_g2.motionZ += var7 * var9;
            var7 = Math.sin((this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.75) * 0.05;
            final EntityGuardian field_179930_g3 = this.field_179930_g;
            field_179930_g3.motionY += var7 * (var9 + var8) * 0.25;
            final EntityGuardian field_179930_g4 = this.field_179930_g;
            field_179930_g4.motionY += this.field_179930_g.getAIMoveSpeed() * var2 * 0.1;
            final EntityLookHelper var10 = this.field_179930_g.getLookHelper();
            final double var11 = this.field_179930_g.posX + var1 / var4 * 2.0;
            final double var12 = this.field_179930_g.getEyeHeight() + this.field_179930_g.posY + var2 / var4 * 1.0;
            final double var13 = this.field_179930_g.posZ + var3 / var4 * 2.0;
            double var14 = var10.func_180423_e();
            double var15 = var10.func_180422_f();
            double var16 = var10.func_180421_g();
            if (!var10.func_180424_b()) {
                var14 = var11;
                var15 = var12;
                var16 = var13;
            }
            this.field_179930_g.getLookHelper().setLookPosition(var14 + (var11 - var14) * 0.125, var15 + (var12 - var15) * 0.125, var16 + (var13 - var16) * 0.125, 10.0f, 40.0f);
            EntityGuardian.access$200(this.field_179930_g, true);
        }
        else {
            this.field_179930_g.setAIMoveSpeed(0.0f);
            EntityGuardian.access$200(this.field_179930_g, false);
        }
    }
}
