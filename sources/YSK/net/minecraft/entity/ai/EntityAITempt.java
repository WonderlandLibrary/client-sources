package net.minecraft.entity.ai;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.item.*;

public class EntityAITempt extends EntityAIBase
{
    private static final String[] I;
    private double pitch;
    private boolean scaredByPlayerMovement;
    private int delayTemptCounter;
    private double targetY;
    private boolean avoidWater;
    private Item temptItem;
    private EntityPlayer temptingPlayer;
    private boolean isRunning;
    private double yaw;
    private double targetZ;
    private double targetX;
    private EntityCreature temptedEntity;
    private double speed;
    
    @Override
    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002) {
                    return "".length() != 0;
                }
                if (Math.abs(this.temptingPlayer.rotationPitch - this.pitch) > 5.0 || Math.abs(this.temptingPlayer.rotationYaw - this.yaw) > 5.0) {
                    return "".length() != 0;
                }
            }
            else {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }
            this.pitch = this.temptingPlayer.rotationPitch;
            this.yaw = this.temptingPlayer.rotationYaw;
        }
        return this.shouldExecute();
    }
    
    static {
        I();
    }
    
    @Override
    public void updateTask() {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0f, this.temptedEntity.getVerticalFaceSpeed());
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator().clearPathEntity();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
        }
    }
    
    @Override
    public void startExecuting() {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = (" ".length() != 0);
        this.avoidWater = ((PathNavigateGround)this.temptedEntity.getNavigator()).getAvoidsWater();
        ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater("".length() != 0);
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAITempt(final EntityCreature temptedEntity, final double speed, final Item temptItem, final boolean scaredByPlayerMovement) {
        this.temptedEntity = temptedEntity;
        this.speed = speed;
        this.temptItem = temptItem;
        this.scaredByPlayerMovement = scaredByPlayerMovement;
        this.setMutexBits("   ".length());
        if (!(temptedEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(EntityAITempt.I["".length()]);
        }
    }
    
    @Override
    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = (0x12 ^ 0x76);
        this.isRunning = ("".length() != 0);
        ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater(this.avoidWater);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005\u0006\u0012>) \u0007\u0013?<4H\f$;p\u001c\u0018;<p\u000e\u000e9y\u0004\r\f;-\u0017\u0007\u0000'", "PhaKY");
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            this.delayTemptCounter -= " ".length();
            return "".length() != 0;
        }
        this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return "".length() != 0;
        }
        final ItemStack currentEquippedItem = this.temptingPlayer.getCurrentEquippedItem();
        int n;
        if (currentEquippedItem == null) {
            n = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else if (currentEquippedItem.getItem() == this.temptItem) {
            n = " ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
}
