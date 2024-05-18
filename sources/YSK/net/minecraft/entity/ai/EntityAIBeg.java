package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityAIBeg extends EntityAIBase
{
    private int timeoutCounter;
    private EntityPlayer thePlayer;
    private EntityWolf theWolf;
    private World worldObject;
    private float minPlayerDistance;
    
    @Override
    public void resetTask() {
        this.theWolf.setBegging("".length() != 0);
        this.thePlayer = null;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0f, this.theWolf.getVerticalFaceSpeed());
        this.timeoutCounter -= " ".length();
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (!this.thePlayer.isEntityAlive()) {
            n = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (this.theWolf.getDistanceSqToEntity(this.thePlayer) > this.minPlayerDistance * this.minPlayerDistance) {
            n = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (this.timeoutCounter > 0 && this.hasPlayerGotBoneInHand(this.thePlayer)) {
            n = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public EntityAIBeg(final EntityWolf theWolf, final float minPlayerDistance) {
        this.theWolf = theWolf;
        this.worldObject = theWolf.worldObj;
        this.minPlayerDistance = minPlayerDistance;
        this.setMutexBits("  ".length());
    }
    
    @Override
    public void startExecuting() {
        this.theWolf.setBegging(" ".length() != 0);
        this.timeoutCounter = (0x6C ^ 0x44) + this.theWolf.getRNG().nextInt(0x77 ^ 0x5F);
    }
    
    private boolean hasPlayerGotBoneInHand(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        int n;
        if (currentItem == null) {
            n = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (!this.theWolf.isTamed() && currentItem.getItem() == Items.bone) {
            n = " ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n = (this.theWolf.isBreedingItem(currentItem) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
        int n;
        if (this.thePlayer == null) {
            n = "".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n = (this.hasPlayerGotBoneInHand(this.thePlayer) ? 1 : 0);
        }
        return n != 0;
    }
}
