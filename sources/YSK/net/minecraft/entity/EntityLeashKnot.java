package net.minecraft.entity;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityLeashKnot extends EntityHanging
{
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        if (n < 1024.0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getHeightPixels() {
        return 0xA7 ^ 0xAE;
    }
    
    public static EntityLeashKnot getKnotForPosition(final World world, final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final Iterator<EntityLeashKnot> iterator = world.getEntitiesWithinAABB((Class<? extends EntityLeashKnot>)EntityLeashKnot.class, new AxisAlignedBB(x - 1.0, y - 1.0, z - 1.0, x + 1.0, y + 1.0, z + 1.0)).iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityLeashKnot entityLeashKnot = iterator.next();
            if (entityLeashKnot.getHangingPosition().equals(blockPos)) {
                return entityLeashKnot;
            }
        }
        return null;
    }
    
    @Override
    public float getEyeHeight() {
        return -0.0625f;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    @Override
    public int getWidthPixels() {
        return 0x27 ^ 0x2E;
    }
    
    public void updateFacingWithBoundingBox(final EnumFacing enumFacing) {
    }
    
    @Override
    public boolean onValidSurface() {
        return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
    }
    
    public static EntityLeashKnot createKnot(final World world, final BlockPos blockPos) {
        final EntityLeashKnot entityLeashKnot = new EntityLeashKnot(world, blockPos);
        entityLeashKnot.forceSpawn = (" ".length() != 0);
        world.spawnEntityInWorld(entityLeashKnot);
        return entityLeashKnot;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        final ItemStack heldItem = entityPlayer.getHeldItem();
        int n = "".length();
        if (heldItem != null && heldItem.getItem() == Items.lead && !this.worldObj.isRemote) {
            final double n2 = 7.0;
            final Iterator<EntityLiving> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityLiving>)EntityLiving.class, new AxisAlignedBB(this.posX - n2, this.posY - n2, this.posZ - n2, this.posX + n2, this.posY + n2, this.posZ + n2)).iterator();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityLiving entityLiving = iterator.next();
                if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == entityPlayer) {
                    entityLiving.setLeashedToEntity(this, " ".length() != 0);
                    n = " ".length();
                }
            }
        }
        if (!this.worldObj.isRemote && n == 0) {
            this.setDead();
            if (entityPlayer.capabilities.isCreativeMode) {
                final double n3 = 7.0;
                final Iterator<EntityLiving> iterator2 = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityLiving>)EntityLiving.class, new AxisAlignedBB(this.posX - n3, this.posY - n3, this.posZ - n3, this.posX + n3, this.posY + n3, this.posZ + n3)).iterator();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final EntityLiving entityLiving2 = iterator2.next();
                    if (entityLiving2.getLeashed() && entityLiving2.getLeashedToEntity() == this) {
                        entityLiving2.clearLeashed(" ".length() != 0, "".length() != 0);
                    }
                }
            }
        }
        return " ".length() != 0;
    }
    
    public EntityLeashKnot(final World world, final BlockPos blockPos) {
        super(world, blockPos);
        this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875, this.posY - 0.25 + 0.125, this.posZ - 0.1875, this.posX + 0.1875, this.posY + 0.25 + 0.125, this.posZ + 0.1875));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void onBroken(final Entity entity) {
    }
    
    @Override
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        return "".length() != 0;
    }
    
    public EntityLeashKnot(final World world) {
        super(world);
    }
}
