package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private double targetZ;
    private double targetX;
    private EntityHorse horseHost;
    private double targetY;
    private double speed;
    
    @Override
    public void startExecuting() {
        this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.horseHost.isTame() || this.horseHost.riddenByEntity == null) {
            return "".length() != 0;
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.horseHost, 0x32 ^ 0x37, 0x3 ^ 0x7);
        if (randomTarget == null) {
            return "".length() != 0;
        }
        this.targetX = randomTarget.xCoord;
        this.targetY = randomTarget.yCoord;
        this.targetZ = randomTarget.zCoord;
        return " ".length() != 0;
    }
    
    @Override
    public boolean continueExecuting() {
        if (!this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAIRunAroundLikeCrazy(final EntityHorse horseHost, final double speed) {
        this.horseHost = horseHost;
        this.speed = speed;
        this.setMutexBits(" ".length());
    }
    
    @Override
    public void updateTask() {
        if (this.horseHost.getRNG().nextInt(0xA8 ^ 0x9A) == 0) {
            if (this.horseHost.riddenByEntity instanceof EntityPlayer) {
                final int temper = this.horseHost.getTemper();
                final int maxTemper = this.horseHost.getMaxTemper();
                if (maxTemper > 0 && this.horseHost.getRNG().nextInt(maxTemper) < temper) {
                    this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
                    this.horseHost.worldObj.setEntityState(this.horseHost, (byte)(0xAD ^ 0xAA));
                    return;
                }
                this.horseHost.increaseTemper(0x4F ^ 0x4A);
            }
            this.horseHost.riddenByEntity.mountEntity(null);
            this.horseHost.riddenByEntity = null;
            this.horseHost.makeHorseRearWithSound();
            this.horseHost.worldObj.setEntityState(this.horseHost, (byte)(0x66 ^ 0x60));
        }
    }
}
