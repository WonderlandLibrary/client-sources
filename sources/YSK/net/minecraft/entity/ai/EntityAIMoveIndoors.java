package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.util.*;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private int insidePosX;
    private VillageDoorInfo doorInfo;
    private int insidePosZ;
    private EntityCreature entityObj;
    
    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
        this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
        this.doorInfo = null;
    }
    
    @Override
    public boolean shouldExecute() {
        final BlockPos blockPos = new BlockPos(this.entityObj);
        if ((this.entityObj.worldObj.isDaytime() && (!this.entityObj.worldObj.isRaining() || this.entityObj.worldObj.getBiomeGenForCoords(blockPos).canSpawnLightningBolt())) || this.entityObj.worldObj.provider.getHasNoSky()) {
            return "".length() != 0;
        }
        if (this.entityObj.getRNG().nextInt(0x58 ^ 0x6A) != 0) {
            return "".length() != 0;
        }
        if (this.insidePosX != -" ".length() && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
            return "".length() != 0;
        }
        final Village nearestVillage = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockPos, 0xCA ^ 0xC4);
        if (nearestVillage == null) {
            return "".length() != 0;
        }
        this.doorInfo = nearestVillage.getDoorInfo(blockPos);
        if (this.doorInfo != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAIMoveIndoors(final EntityCreature entityObj) {
        this.insidePosX = -" ".length();
        this.insidePosZ = -" ".length();
        this.entityObj = entityObj;
        this.setMutexBits(" ".length());
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.entityObj.getNavigator().noPath()) {
            n = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        this.insidePosX = -" ".length();
        final BlockPos insideBlockPos = this.doorInfo.getInsideBlockPos();
        final int x = insideBlockPos.getX();
        final int y = insideBlockPos.getY();
        final int z = insideBlockPos.getZ();
        if (this.entityObj.getDistanceSq(insideBlockPos) > 256.0) {
            final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 0x67 ^ 0x69, "   ".length(), new Vec3(x + 0.5, y, z + 0.5));
            if (randomTargetBlockTowards != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord, 1.0);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            this.entityObj.getNavigator().tryMoveToXYZ(x + 0.5, y, z + 0.5, 1.0);
        }
    }
}
