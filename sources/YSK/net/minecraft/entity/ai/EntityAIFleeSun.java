package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIFleeSun extends EntityAIBase
{
    private double shelterZ;
    private double shelterY;
    private World theWorld;
    private double movementSpeed;
    private double shelterX;
    private EntityCreature theCreature;
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.theCreature.getNavigator().noPath()) {
            n = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    private Vec3 findPossibleShelter() {
        final Random rng = this.theCreature.getRNG();
        final BlockPos blockPos = new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ);
        int i = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (i < (0x20 ^ 0x2A)) {
            final BlockPos add = blockPos.add(rng.nextInt(0xBF ^ 0xAB) - (0x2A ^ 0x20), rng.nextInt(0xC1 ^ 0xC7) - "   ".length(), rng.nextInt(0x4A ^ 0x5E) - (0x10 ^ 0x1A));
            if (!this.theWorld.canSeeSky(add) && this.theCreature.getBlockPathWeight(add) < 0.0f) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            ++i;
        }
        return null;
    }
    
    public EntityAIFleeSun(final EntityCreature theCreature, final double movementSpeed) {
        this.theCreature = theCreature;
        this.movementSpeed = movementSpeed;
        this.theWorld = theCreature.worldObj;
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return "".length() != 0;
        }
        if (!this.theCreature.isBurning()) {
            return "".length() != 0;
        }
        if (!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ))) {
            return "".length() != 0;
        }
        final Vec3 possibleShelter = this.findPossibleShelter();
        if (possibleShelter == null) {
            return "".length() != 0;
        }
        this.shelterX = possibleShelter.xCoord;
        this.shelterY = possibleShelter.yCoord;
        this.shelterZ = possibleShelter.zCoord;
        return " ".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }
}
