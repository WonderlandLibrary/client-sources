// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.block.state.pattern;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import me.chrest.utils.ClientUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import com.google.common.base.Predicate;

public class BlockHelper implements Predicate
{
    private final Block block;
    private static final String __OBFID = "CL_00002020";
    
    private BlockHelper(final Block p_i45654_1_) {
        this.block = p_i45654_1_;
    }
    
    public static BlockHelper forBlock(final Block p_177642_0_) {
        return new BlockHelper(p_177642_0_);
    }
    
    public boolean isBlockEqualTo(final IBlockState p_177643_1_) {
        return p_177643_1_ != null && p_177643_1_.getBlock() == this.block;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.isBlockEqualTo((IBlockState)p_apply_1_);
    }
    
    public static Block getBlockAtPos(final BlockPos inBlockPos) {
        final IBlockState s = ClientUtils.world().getBlockState(inBlockPos);
        return s.getBlock();
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        final EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
        EnumFacing[] arrayOfEnumFacing1;
        for (int j = (arrayOfEnumFacing1 = orderedValues).length, i = 0; i < j; ++i) {
            final EnumFacing facing = arrayOfEnumFacing1[i];
            final Entity temp = new EntitySnowball(ClientUtils.world());
            temp.posX = pos.getX() + 0.5;
            temp.posY = pos.getY() + 0.5;
            temp.posZ = pos.getZ() + 0.5;
            final Entity entity4;
            final Entity entity = entity4 = temp;
            entity4.posX += facing.getDirectionVec().getX() * 0.5;
            final Entity entity5;
            final Entity entity2 = entity5 = temp;
            entity5.posY += facing.getDirectionVec().getY() * 0.5;
            final Entity entity6;
            final Entity entity3 = entity6 = temp;
            entity6.posZ += facing.getDirectionVec().getZ() * 0.5;
            if (ClientUtils.player().canEntityBeSeen(temp)) {
                return facing;
            }
        }
        return null;
    }
    
    public static float getYawChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - ClientUtils.player().posX;
        final double deltaZ = entity.posZ - ClientUtils.player().posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(ClientUtils.player().rotationYaw - (float)yawToEntity));
    }
    
    public static float getPitchChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - ClientUtils.player().posX;
        final double deltaZ = entity.posZ - ClientUtils.player().posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - ClientUtils.player().posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(ClientUtils.player().rotationPitch - (float)pitchToEntity);
    }
    
    public static float[] getAngles(final Entity e) {
        return new float[] { getYawChangeToEntity(e) + ClientUtils.player().rotationYaw, getPitchChangeToEntity(e) + ClientUtils.player().rotationPitch };
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing) {
        final Entity temp = new EntitySnowball(ClientUtils.world());
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        try {
            final Entity entity4;
            final Entity entity = entity4 = temp;
            entity4.posX += facing.getDirectionVec().getX() * 0.25;
            final Entity entity5;
            final Entity entity2 = entity5 = temp;
            entity5.posY += facing.getDirectionVec().getY() * 0.25;
            final Entity entity6;
            final Entity entity3 = entity6 = temp;
            entity6.posZ += facing.getDirectionVec().getZ() * 0.25;
        }
        catch (NullPointerException ex) {}
        return getAngles(temp);
    }
    
    public static float[] getBlockRotations(final double x, final double y, final double z) {
        double var4 = x - ClientUtils.x() + 0.5;
        final double var5 = z - ClientUtils.z() + 0.5;
        final double var6 = y - (ClientUtils.y() + ClientUtils.player().getEyeHeight() - 1.0);
        var4 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var7 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[] { var7, (float)(-(Math.atan2(var6, var4) * 180.0 / 3.141592653589793)) };
    }
}
