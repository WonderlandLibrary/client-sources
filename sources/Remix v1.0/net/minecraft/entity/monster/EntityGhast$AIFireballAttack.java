package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

class AIFireballAttack extends EntityAIBase
{
    public int field_179471_a;
    private EntityGhast field_179470_b;
    
    AIFireballAttack() {
        this.field_179470_b = EntityGhast.this;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179470_b.getAttackTarget() != null;
    }
    
    @Override
    public void startExecuting() {
        this.field_179471_a = 0;
    }
    
    @Override
    public void resetTask() {
        this.field_179470_b.func_175454_a(false);
    }
    
    @Override
    public void updateTask() {
        final EntityLivingBase var1 = this.field_179470_b.getAttackTarget();
        final double var2 = 64.0;
        if (var1.getDistanceSqToEntity(this.field_179470_b) < var2 * var2 && this.field_179470_b.canEntityBeSeen(var1)) {
            final World var3 = this.field_179470_b.worldObj;
            ++this.field_179471_a;
            if (this.field_179471_a == 10) {
                var3.playAuxSFXAtEntity(null, 1007, new BlockPos(this.field_179470_b), 0);
            }
            if (this.field_179471_a == 20) {
                final double var4 = 4.0;
                final Vec3 var5 = this.field_179470_b.getLook(1.0f);
                final double var6 = var1.posX - (this.field_179470_b.posX + var5.xCoord * var4);
                final double var7 = var1.getEntityBoundingBox().minY + var1.height / 2.0f - (0.5 + this.field_179470_b.posY + this.field_179470_b.height / 2.0f);
                final double var8 = var1.posZ - (this.field_179470_b.posZ + var5.zCoord * var4);
                var3.playAuxSFXAtEntity(null, 1008, new BlockPos(this.field_179470_b), 0);
                final EntityLargeFireball var9 = new EntityLargeFireball(var3, this.field_179470_b, var6, var7, var8);
                var9.field_92057_e = this.field_179470_b.func_175453_cd();
                var9.posX = this.field_179470_b.posX + var5.xCoord * var4;
                var9.posY = this.field_179470_b.posY + this.field_179470_b.height / 2.0f + 0.5;
                var9.posZ = this.field_179470_b.posZ + var5.zCoord * var4;
                var3.spawnEntityInWorld(var9);
                this.field_179471_a = -40;
            }
        }
        else if (this.field_179471_a > 0) {
            --this.field_179471_a;
        }
        this.field_179470_b.func_175454_a(this.field_179471_a > 10);
    }
}
