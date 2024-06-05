package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;

class AISlimeFaceRandom extends EntityAIBase
{
    private EntitySlime field_179461_a;
    private float field_179459_b;
    private int field_179460_c;
    
    public AISlimeFaceRandom() {
        this.field_179461_a = EntitySlime.this;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179461_a.getAttackTarget() == null && (this.field_179461_a.onGround || this.field_179461_a.isInWater() || this.field_179461_a.func_180799_ab());
    }
    
    @Override
    public void updateTask() {
        final int field_179460_c = this.field_179460_c - 1;
        this.field_179460_c = field_179460_c;
        if (field_179460_c <= 0) {
            this.field_179460_c = 40 + this.field_179461_a.getRNG().nextInt(60);
            this.field_179459_b = (float)this.field_179461_a.getRNG().nextInt(360);
        }
        ((SlimeMoveHelper)this.field_179461_a.getMoveHelper()).func_179920_a(this.field_179459_b, false);
    }
}
