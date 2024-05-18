package net.minecraft.pathfinding;

import net.minecraft.world.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathNavigateSwimmer extends PathNavigate
{
    @Override
    protected void removeSunnyPath() {
        super.removeSunnyPath();
    }
    
    public PathNavigateSwimmer(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5, this.theEntity.posZ);
    }
    
    @Override
    protected boolean canNavigate() {
        return this.isInLiquid();
    }
    
    @Override
    protected PathFinder getPathFinder() {
        return new PathFinder(new SwimNodeProcessor());
    }
    
    @Override
    protected void pathFollow() {
        final Vec3 entityPosition = this.getEntityPosition();
        final float n = this.theEntity.width * this.theEntity.width;
        final int n2 = 0x2C ^ 0x2A;
        if (entityPosition.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < n) {
            this.currentPath.incrementPathIndex();
        }
        int i = Math.min(this.currentPath.getCurrentPathIndex() + n2, this.currentPath.getCurrentPathLength() - " ".length());
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i > this.currentPath.getCurrentPathIndex()) {
            final Vec3 vectorFromIndex = this.currentPath.getVectorFromIndex(this.theEntity, i);
            if (vectorFromIndex.squareDistanceTo(entityPosition) <= 36.0 && this.isDirectPathBetweenPoints(entityPosition, vectorFromIndex, "".length(), "".length(), "".length())) {
                this.currentPath.setCurrentPathIndex(i);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            else {
                --i;
            }
        }
        this.checkForStuck(entityPosition);
    }
    
    @Override
    protected boolean isDirectPathBetweenPoints(final Vec3 vec3, final Vec3 vec4, final int n, final int n2, final int n3) {
        final MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(vec3, new Vec3(vec4.xCoord, vec4.yCoord + this.theEntity.height * 0.5, vec4.zCoord), "".length() != 0, " ".length() != 0, "".length() != 0);
        if (rayTraceBlocks != null && rayTraceBlocks.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}
