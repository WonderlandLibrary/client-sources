/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAIHurtByTarget
extends EntityAITarget {
    private boolean entityCallsForHelp;
    private int revengeTimerOld;
    private final Class[] field_179447_c;
    private static final String __OBFID = "CL_00001619";

    public /* varargs */ EntityAIHurtByTarget(EntityCreature p_i45885_1_, boolean p_i45885_2_, Class ... p_i45885_3_) {
        super(p_i45885_1_, false);
        this.entityCallsForHelp = p_i45885_2_;
        this.field_179447_c = p_i45885_3_;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        int var1 = this.taskOwner.getRevengeTimer();
        if (var1 != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), false)) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();
        if (this.entityCallsForHelp) {
            double var1 = this.getTargetDistance();
            List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(var1, 10.0, var1));
            for (EntityCreature var5 : var3) {
                if (this.taskOwner == var5 || var5.getAttackTarget() != null || var5.isOnSameTeam(this.taskOwner.getAITarget())) continue;
                boolean var6 = false;
                Class[] var7 = this.field_179447_c;
                int var8 = var7.length;
                int var9 = 0;
                while (var9 < var8) {
                    Class var10 = var7[var9];
                    if (var5.getClass() == var10) {
                        var6 = true;
                        break;
                    }
                    ++var9;
                }
                if (var6) continue;
                this.func_179446_a(var5, this.taskOwner.getAITarget());
            }
        }
        super.startExecuting();
    }

    protected void func_179446_a(EntityCreature p_179446_1_, EntityLivingBase p_179446_2_) {
        p_179446_1_.setAttackTarget(p_179446_2_);
    }
}

