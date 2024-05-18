package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private static final String[] I;
    private int previousBreakProgress;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return "".length() != 0;
        }
        if (!this.theEntity.worldObj.getGameRules().getBoolean(EntityAIBreakDoor.I["".length()])) {
            return "".length() != 0;
        }
        final BlockDoor doorBlock = this.doorBlock;
        int n;
        if (BlockDoor.isOpen(this.theEntity.worldObj, this.doorPosition)) {
            n = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    static {
        I();
    }
    
    public EntityAIBreakDoor(final EntityLiving entityLiving) {
        super(entityLiving);
        this.previousBreakProgress = -" ".length();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(0x6C ^ 0x78) == 0) {
            this.theEntity.worldObj.playAuxSFX(605 + 348 - 936 + 993, this.doorPosition, "".length());
        }
        this.breakingTime += " ".length();
        final int previousBreakProgress = (int)(this.breakingTime / 240.0f * 10.0f);
        if (previousBreakProgress != this.previousBreakProgress) {
            this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, previousBreakProgress);
            this.previousBreakProgress = previousBreakProgress;
        }
        if (this.breakingTime == 210 + 136 - 139 + 33 && this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            this.theEntity.worldObj.setBlockToAir(this.doorPosition);
            this.theEntity.worldObj.playAuxSFX(819 + 74 - 509 + 628, this.doorPosition, "".length());
            this.theEntity.worldObj.playAuxSFX(146 + 332 - 282 + 1805, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
        }
    }
    
    @Override
    public boolean continueExecuting() {
        final double distanceSq = this.theEntity.getDistanceSq(this.doorPosition);
        if (this.breakingTime <= 103 + 136 - 200 + 201) {
            final BlockDoor doorBlock = this.doorBlock;
            if (!BlockDoor.isOpen(this.theEntity.worldObj, this.doorPosition) && distanceSq < 4.0) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\b8\u0013(=\f2\u0017\u0006!\u0002", "eWqoO");
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = "".length();
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, -" ".length());
    }
}
