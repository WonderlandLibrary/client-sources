package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.village.*;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private VillageDoorInfo frontDoor;
    private static final String[] I;
    private EntityCreature entityObj;
    
    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors("".length() != 0);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors("".length() != 0);
    }
    
    public EntityAIRestrictOpenDoor(final EntityCreature entityObj) {
        this.entityObj = entityObj;
        if (!(entityObj.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(EntityAIRestrictOpenDoor.I["".length()]);
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return "".length() != 0;
        }
        final BlockPos blockPos = new BlockPos(this.entityObj);
        final Village nearestVillage = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockPos, 0x37 ^ 0x27);
        if (nearestVillage == null) {
            return "".length() != 0;
        }
        this.frontDoor = nearestVillage.getNearestDoor(blockPos);
        int n;
        if (this.frontDoor == null) {
            n = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (this.frontDoor.getDistanceToInsideBlockSq(blockPos) < 2.25) {
            n = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    static {
        I();
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(":\u00032\u0002\"\u001f\u00023\u00037\u000bM,\u00180O\u00198\u00077O\u000b.\u0005r=\b2\u0003 \u0006\u000e58\"\n\u0003\u0005\u0018=\u001d*.\u0016>", "omAwR");
    }
    
    @Override
    public void resetTask() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(" ".length() != 0);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(" ".length() != 0);
        this.frontDoor = null;
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.entityObj.worldObj.isDaytime()) {
            n = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (!this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.func_179850_c(new BlockPos(this.entityObj))) {
            n = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
}
