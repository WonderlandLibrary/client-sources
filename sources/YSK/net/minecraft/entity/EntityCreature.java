package net.minecraft.entity;

import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;

public abstract class EntityCreature extends EntityLiving
{
    private EntityAIBase aiBase;
    private boolean isMovementAITaskSet;
    private BlockPos homePosition;
    public static final AttributeModifier FLEEING_SPEED_MODIFIER;
    public static final UUID FLEEING_SPEED_MODIFIER_UUID;
    private float maximumHomeDistance;
    private static final String[] I;
    
    public BlockPos getHomePosition() {
        return this.homePosition;
    }
    
    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }
    
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            final Entity leashedToEntity = this.getLeashedToEntity();
            this.setHomePosAndDistance(new BlockPos((int)leashedToEntity.posX, (int)leashedToEntity.posY, (int)leashedToEntity.posZ), 0x2F ^ 0x2A);
            final float distanceToEntity = this.getDistanceToEntity(leashedToEntity);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (distanceToEntity > 10.0f) {
                    this.clearLeashed(" ".length() != 0, " ".length() != 0);
                }
                return;
            }
            if (!this.isMovementAITaskSet) {
                this.tasks.addTask("  ".length(), this.aiBase);
                if (this.getNavigator() instanceof PathNavigateGround) {
                    ((PathNavigateGround)this.getNavigator()).setAvoidsWater("".length() != 0);
                }
                this.isMovementAITaskSet = (" ".length() != 0);
            }
            this.func_142017_o(distanceToEntity);
            if (distanceToEntity > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(leashedToEntity, 1.0);
            }
            if (distanceToEntity > 6.0f) {
                final double n = (leashedToEntity.posX - this.posX) / distanceToEntity;
                final double n2 = (leashedToEntity.posY - this.posY) / distanceToEntity;
                final double n3 = (leashedToEntity.posZ - this.posZ) / distanceToEntity;
                this.motionX += n * Math.abs(n) * 0.4;
                this.motionY += n2 * Math.abs(n2) * 0.4;
                this.motionZ += n3 * Math.abs(n3) * 0.4;
            }
            if (distanceToEntity > 10.0f) {
                this.clearLeashed(" ".length() != 0, " ".length() != 0);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else if (!this.getLeashed() && this.isMovementAITaskSet) {
            this.isMovementAITaskSet = ("".length() != 0);
            this.tasks.removeTask(this.aiBase);
            if (this.getNavigator() instanceof PathNavigateGround) {
                ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
            }
            this.detachHome();
        }
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPosition(new BlockPos(this));
    }
    
    static {
        I();
        FLEEING_SPEED_MODIFIER_UUID = UUID.fromString(EntityCreature.I["".length()]);
        FLEEING_SPEED_MODIFIER = new AttributeModifier(EntityCreature.FLEEING_SPEED_MODIFIER_UUID, EntityCreature.I[" ".length()], 2.0, "  ".length()).setSaved("".length() != 0);
    }
    
    public boolean isWithinHomeDistanceFromPosition(final BlockPos blockPos) {
        int n;
        if (this.maximumHomeDistance == -1.0f) {
            n = " ".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (this.homePosition.distanceSq(blockPos) < this.maximumHomeDistance * this.maximumHomeDistance) {
            n = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void setHomePosAndDistance(final BlockPos homePosition, final int n) {
        this.homePosition = homePosition;
        this.maximumHomeDistance = n;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("<_WN\t=\\_Z\n8V/Z|:[]Zp=_]Z~HV\\3}:XW3{8", "ynnwH");
        EntityCreature.I[" ".length()] = I("\u0014\u001e\u000b\b\u001d<\u0015N\u001e\u00047\u0017\nM\u0016=\u001c\u001b\u001e", "Rrnmt");
    }
    
    public boolean hasPath() {
        int n;
        if (this.navigator.noPath()) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public boolean hasHome() {
        if (this.maximumHomeDistance != -1.0f) {
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere() && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void func_142017_o(final float n) {
    }
    
    public float getBlockPathWeight(final BlockPos blockPos) {
        return 0.0f;
    }
    
    public EntityCreature(final World world) {
        super(world);
        this.homePosition = BlockPos.ORIGIN;
        this.maximumHomeDistance = -1.0f;
        this.aiBase = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
}
