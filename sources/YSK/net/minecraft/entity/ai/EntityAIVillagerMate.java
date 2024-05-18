package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.village.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIVillagerMate extends EntityAIBase
{
    private World worldObj;
    private EntityVillager mate;
    private int matingTimeout;
    private EntityVillager villagerObj;
    Village villageObj;
    
    @Override
    public void startExecuting() {
        this.matingTimeout = 226 + 4 - 214 + 284;
        this.villagerObj.setMating(" ".length() != 0);
    }
    
    public EntityAIVillagerMate(final EntityVillager villagerObj) {
        this.villagerObj = villagerObj;
        this.worldObj = villagerObj.worldObj;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.matingTimeout >= 0 && this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate("".length() != 0)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void giveBirth() {
        final EntityVillager child = this.villagerObj.createChild((EntityAgeable)this.mate);
        this.mate.setGrowingAge(429 + 2761 - 2519 + 5329);
        this.villagerObj.setGrowingAge(687 + 1522 + 3241 + 550);
        this.mate.setIsWillingToMate("".length() != 0);
        this.villagerObj.setIsWillingToMate("".length() != 0);
        child.setGrowingAge(-(23194 + 1553 - 18572 + 17825));
        child.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(child);
        this.worldObj.setEntityState(child, (byte)(0xA7 ^ 0xAB));
    }
    
    @Override
    public void resetTask() {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating("".length() != 0);
    }
    
    private boolean checkSufficientDoorsPresentForNewVillager() {
        if (!this.villageObj.isMatingSeason()) {
            return "".length() != 0;
        }
        if (this.villageObj.getNumVillagers() < (int)(this.villageObj.getNumVillageDoors() * 0.35)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        this.matingTimeout -= " ".length();
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0f, 30.0f);
        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25) {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (this.matingTimeout == 0 && this.mate.isMating()) {
            this.giveBirth();
        }
        if (this.villagerObj.getRNG().nextInt(0x20 ^ 0x3) == 0) {
            this.worldObj.setEntityState(this.villagerObj, (byte)(0xA9 ^ 0xA5));
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() != 0) {
            return "".length() != 0;
        }
        if (this.villagerObj.getRNG().nextInt(1 + 350 + 81 + 68) != 0) {
            return "".length() != 0;
        }
        this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.villagerObj), "".length());
        if (this.villageObj == null) {
            return "".length() != 0;
        }
        if (!this.checkSufficientDoorsPresentForNewVillager() || !this.villagerObj.getIsWillingToMate(" ".length() != 0)) {
            return "".length() != 0;
        }
        final EntityVillager nearestEntityWithinAABB = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0, 3.0, 8.0), this.villagerObj);
        if (nearestEntityWithinAABB == null) {
            return "".length() != 0;
        }
        this.mate = nearestEntityWithinAABB;
        if (this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(" ".length() != 0)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
