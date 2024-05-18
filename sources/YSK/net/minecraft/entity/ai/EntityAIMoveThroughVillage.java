package net.minecraft.entity.ai;

import net.minecraft.village.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private PathEntity entityPathNavigate;
    private static final String[] I;
    private List<VillageDoorInfo> doorList;
    private boolean isNocturnal;
    private EntityCreature theEntity;
    private double movementSpeed;
    private VillageDoorInfo doorInfo;
    
    @Override
    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return "".length() != 0;
        }
        final float n = this.theEntity.width + 4.0f;
        if (this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > n * n) {
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void resizeDoorList() {
        if (this.doorList.size() > (0x42 ^ 0x4D)) {
            this.doorList.remove("".length());
        }
    }
    
    private VillageDoorInfo findNearestDoor(final Village village) {
        VillageDoorInfo villageDoorInfo = null;
        int n = 637955522 + 1556933738 - 1003313272 + 955907659;
        final Iterator<VillageDoorInfo> iterator = village.getVillageDoorInfoList().iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo2 = iterator.next();
            final int distanceSquared = villageDoorInfo2.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
            if (distanceSquared < n && !this.doesDoorListContain(villageDoorInfo2)) {
                villageDoorInfo = villageDoorInfo2;
                n = distanceSquared;
            }
        }
        return villageDoorInfo;
    }
    
    @Override
    public boolean shouldExecute() {
        this.resizeDoorList();
        if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
            return "".length() != 0;
        }
        final Village nearestVillage = this.theEntity.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.theEntity), "".length());
        if (nearestVillage == null) {
            return "".length() != 0;
        }
        this.doorInfo = this.findNearestDoor(nearestVillage);
        if (this.doorInfo == null) {
            return "".length() != 0;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final boolean enterDoors = pathNavigateGround.getEnterDoors();
        pathNavigateGround.setBreakDoors("".length() != 0);
        this.entityPathNavigate = pathNavigateGround.getPathToPos(this.doorInfo.getDoorBlockPos());
        pathNavigateGround.setBreakDoors(enterDoors);
        if (this.entityPathNavigate != null) {
            return " ".length() != 0;
        }
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 0x85 ^ 0x8F, 0x18 ^ 0x1F, new Vec3(this.doorInfo.getDoorBlockPos().getX(), this.doorInfo.getDoorBlockPos().getY(), this.doorInfo.getDoorBlockPos().getZ()));
        if (randomTargetBlockTowards == null) {
            return "".length() != 0;
        }
        pathNavigateGround.setBreakDoors("".length() != 0);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord);
        pathNavigateGround.setBreakDoors(enterDoors);
        if (this.entityPathNavigate != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    public EntityAIMoveThroughVillage(final EntityCreature theEntity, final double movementSpeed, final boolean isNocturnal) {
        this.doorList = (List<VillageDoorInfo>)Lists.newArrayList();
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.isNocturnal = isNocturnal;
        this.setMutexBits(" ".length());
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(EntityAIMoveThroughVillage.I["".length()]);
        }
    }
    
    @Override
    public void resetTask() {
        if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }
    
    private boolean doesDoorListContain(final VillageDoorInfo villageDoorInfo) {
        final Iterator<VillageDoorInfo> iterator = this.doorList.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (villageDoorInfo.getDoorBlockPos().equals(iterator.next().getDoorBlockPos())) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(";:\u0006\u0019?\u001e;\u0007\u0018*\nt\u0018\u0003-N2\u001a\u001eo#;\u0003\t\u001b\u0006&\u001a\u0019(\u0006\u0002\u001c\u0000#\u000f3\u0010+ \u000f8", "nTulO");
    }
}
