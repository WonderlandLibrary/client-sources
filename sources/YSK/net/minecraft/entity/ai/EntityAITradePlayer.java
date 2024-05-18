package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;

public class EntityAITradePlayer extends EntityAIBase
{
    private EntityVillager villager;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.villager.setCustomer(null);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.villager.isEntityAlive()) {
            return "".length() != 0;
        }
        if (this.villager.isInWater()) {
            return "".length() != 0;
        }
        if (!this.villager.onGround) {
            return "".length() != 0;
        }
        if (this.villager.velocityChanged) {
            return "".length() != 0;
        }
        final EntityPlayer customer = this.villager.getCustomer();
        int n;
        if (customer == null) {
            n = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (this.villager.getDistanceSqToEntity(customer) > 16.0) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = ((customer.openContainer instanceof Container) ? 1 : 0);
        }
        return n != 0;
    }
    
    public EntityAITradePlayer(final EntityVillager villager) {
        this.villager = villager;
        this.setMutexBits(0x1F ^ 0x1A);
    }
}
