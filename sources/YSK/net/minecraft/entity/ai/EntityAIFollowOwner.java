package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class EntityAIFollowOwner extends EntityAIBase
{
    private boolean field_75344_i;
    private PathNavigate petPathfinder;
    private EntityTameable thePet;
    private static final String[] I;
    float maxDist;
    float minDist;
    private EntityLivingBase theOwner;
    private int field_75343_h;
    World theWorld;
    private double followSpeed;
    
    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(" ".length() != 0);
    }
    
    @Override
    public void startExecuting() {
        this.field_75343_h = "".length();
        this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
        ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater("".length() != 0);
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIFollowOwner(final EntityTameable thePet, final double followSpeed, final float minDist, final float maxDist) {
        this.thePet = thePet;
        this.theWorld = thePet.worldObj;
        this.followSpeed = followSpeed;
        this.petPathfinder = thePet.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits("   ".length());
        if (!(thePet.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(EntityAIFollowOwner.I["".length()]);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("/\u0017\u001f'\t\n\u0016\u001e&\u001c\u001eY\u0001=\u001bZ\r\u0015\"\u001cZ\u001f\u0003 Y<\u0016\u0000>\u0016\r6\u001b<\u001c\b>\u00033\u0015", "zylRy");
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase owner = this.thePet.getOwner();
        if (owner == null) {
            return "".length() != 0;
        }
        if (owner instanceof EntityPlayer && ((EntityPlayer)owner).isSpectator()) {
            return "".length() != 0;
        }
        if (this.thePet.isSitting()) {
            return "".length() != 0;
        }
        if (this.thePet.getDistanceSqToEntity(owner) < this.minDist * this.minDist) {
            return "".length() != 0;
        }
        this.theOwner = owner;
        return " ".length() != 0;
    }
    
    @Override
    public void updateTask() {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0f, this.thePet.getVerticalFaceSpeed());
        if (!this.thePet.isSitting() && (this.field_75343_h -= " ".length()) <= 0) {
            this.field_75343_h = (0xBF ^ 0xB5);
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed) && !this.thePet.getLeashed() && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                final int n = MathHelper.floor_double(this.theOwner.posX) - "  ".length();
                final int n2 = MathHelper.floor_double(this.theOwner.posZ) - "  ".length();
                final int floor_double = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);
                int i = "".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                while (i <= (0x69 ^ 0x6D)) {
                    int j = "".length();
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                    while (j <= (0x91 ^ 0x95)) {
                        if ((i < " ".length() || j < " ".length() || i > "   ".length() || j > "   ".length()) && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(n + i, floor_double - " ".length(), n2 + j)) && this.func_181065_a(new BlockPos(n + i, floor_double, n2 + j)) && this.func_181065_a(new BlockPos(n + i, floor_double + " ".length(), n2 + j))) {
                            this.thePet.setLocationAndAngles(n + i + 0.5f, floor_double, n2 + j + 0.5f, this.thePet.rotationYaw, this.thePet.rotationPitch);
                            this.petPathfinder.clearPathEntity();
                            return;
                        }
                        ++j;
                    }
                    ++i;
                }
            }
        }
    }
    
    static {
        I();
    }
    
    @Override
    public boolean continueExecuting() {
        if (!this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.thePet.isSitting()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean func_181065_a(final BlockPos blockPos) {
        final Block block = this.theWorld.getBlockState(blockPos).getBlock();
        int n;
        if (block == Blocks.air) {
            n = " ".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else if (block.isFullCube()) {
            n = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
}
