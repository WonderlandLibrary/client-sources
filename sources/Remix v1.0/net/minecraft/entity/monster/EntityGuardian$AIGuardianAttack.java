package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

class AIGuardianAttack extends EntityAIBase
{
    private EntityGuardian field_179456_a;
    private int field_179455_b;
    
    public AIGuardianAttack() {
        this.field_179456_a = EntityGuardian.this;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.field_179456_a.getAttackTarget();
        return var1 != null && var1.isEntityAlive();
    }
    
    @Override
    public boolean continueExecuting() {
        return super.continueExecuting() && (this.field_179456_a.func_175461_cl() || this.field_179456_a.getDistanceSqToEntity(this.field_179456_a.getAttackTarget()) > 9.0);
    }
    
    @Override
    public void startExecuting() {
        this.field_179455_b = -10;
        this.field_179456_a.getNavigator().clearPathEntity();
        this.field_179456_a.getLookHelper().setLookPositionWithEntity(this.field_179456_a.getAttackTarget(), 90.0f, 90.0f);
        this.field_179456_a.isAirBorne = true;
    }
    
    @Override
    public void resetTask() {
        EntityGuardian.access$000(this.field_179456_a, 0);
        this.field_179456_a.setAttackTarget(null);
        EntityGuardian.access$100(this.field_179456_a).func_179480_f();
    }
    
    @Override
    public void updateTask() {
        final EntityLivingBase var1 = this.field_179456_a.getAttackTarget();
        this.field_179456_a.getNavigator().clearPathEntity();
        this.field_179456_a.getLookHelper().setLookPositionWithEntity(var1, 90.0f, 90.0f);
        if (!this.field_179456_a.canEntityBeSeen(var1)) {
            this.field_179456_a.setAttackTarget(null);
        }
        else {
            ++this.field_179455_b;
            if (this.field_179455_b == 0) {
                EntityGuardian.access$000(this.field_179456_a, this.field_179456_a.getAttackTarget().getEntityId());
                this.field_179456_a.worldObj.setEntityState(this.field_179456_a, (byte)21);
            }
            else if (this.field_179455_b >= this.field_179456_a.func_175464_ck()) {
                float var2 = 1.0f;
                if (this.field_179456_a.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                    var2 += 2.0f;
                }
                if (this.field_179456_a.func_175461_cl()) {
                    var2 += 2.0f;
                }
                var1.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.field_179456_a, this.field_179456_a), var2);
                var1.attackEntityFrom(DamageSource.causeMobDamage(this.field_179456_a), (float)this.field_179456_a.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
                this.field_179456_a.setAttackTarget(null);
            }
            else if (this.field_179455_b < 60 || this.field_179455_b % 20 == 0) {}
            super.updateTask();
        }
    }
}
