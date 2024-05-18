package net.minecraft.src;

import java.util.*;

public class EntityAIFollowParent extends EntityAIBase
{
    EntityAnimal childAnimal;
    EntityAnimal parentAnimal;
    float field_75347_c;
    private int field_75345_d;
    
    public EntityAIFollowParent(final EntityAnimal par1EntityAnimal, final float par2) {
        this.childAnimal = par1EntityAnimal;
        this.field_75347_c = par2;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        final List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.boundingBox.expand(8.0, 4.0, 8.0));
        EntityAnimal var2 = null;
        double var3 = Double.MAX_VALUE;
        for (final EntityAnimal var5 : var1) {
            if (var5.getGrowingAge() >= 0) {
                final double var6 = this.childAnimal.getDistanceSqToEntity(var5);
                if (var6 > var3) {
                    continue;
                }
                var3 = var6;
                var2 = var5;
            }
        }
        if (var2 == null) {
            return false;
        }
        if (var3 < 9.0) {
            return false;
        }
        this.parentAnimal = var2;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        if (!this.parentAnimal.isEntityAlive()) {
            return false;
        }
        final double var1 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        return var1 >= 9.0 && var1 <= 256.0;
    }
    
    @Override
    public void startExecuting() {
        this.field_75345_d = 0;
    }
    
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }
    
    @Override
    public void updateTask() {
        final int field_75345_d = this.field_75345_d - 1;
        this.field_75345_d = field_75345_d;
        if (field_75345_d <= 0) {
            this.field_75345_d = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_75347_c);
        }
    }
}
