package net.minecraft.pathfinding;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class PathNavigateClimber extends PathNavigateGround
{
    private BlockPos targetPosition;
    
    public PathNavigateClimber(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    @Override
    public void onUpdateNavigation() {
        if (!this.noPath()) {
            super.onUpdateNavigation();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (this.targetPosition != null) {
            final double n = this.theEntity.width * this.theEntity.width;
            if (this.theEntity.getDistanceSqToCenter(this.targetPosition) >= n && (this.theEntity.posY <= this.targetPosition.getY() || this.theEntity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())) >= n)) {
                this.theEntity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.targetPosition = null;
            }
        }
    }
    
    @Override
    public PathEntity getPathToEntityLiving(final Entity entity) {
        this.targetPosition = new BlockPos(entity);
        return super.getPathToEntityLiving(entity);
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean tryMoveToEntityLiving(final Entity entity, final double speed) {
        final PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        if (pathToEntityLiving != null) {
            return this.setPath(pathToEntityLiving, speed);
        }
        this.targetPosition = new BlockPos(entity);
        this.speed = speed;
        return " ".length() != 0;
    }
    
    @Override
    public PathEntity getPathToPos(final BlockPos targetPosition) {
        this.targetPosition = targetPosition;
        return super.getPathToPos(targetPosition);
    }
}
