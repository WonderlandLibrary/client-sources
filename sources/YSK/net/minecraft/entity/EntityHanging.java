package net.minecraft.entity;

import net.minecraft.entity.player.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import java.util.*;

public abstract class EntityHanging extends Entity
{
    private static final String[] I;
    protected BlockPos hangingPosition;
    private int tickCounter1;
    public EnumFacing facingDirection;
    
    @Override
    public boolean hitByEntity(final Entity entity) {
        int n;
        if (entity instanceof EntityPlayer) {
            n = (this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f) ? 1 : 0);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    protected void updateFacingWithBoundingBox(final EnumFacing facingDirection) {
        Validate.notNull((Object)facingDirection);
        Validate.isTrue(facingDirection.getAxis().isHorizontal());
        this.facingDirection = facingDirection;
        final float n = this.facingDirection.getHorizontalIndex() * (0x3 ^ 0x59);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        this.updateBoundingBox();
    }
    
    @Override
    protected void entityInit() {
    }
    
    public BlockPos getHangingPosition() {
        return this.hangingPosition;
    }
    
    private void updateBoundingBox() {
        if (this.facingDirection != null) {
            final double n = this.hangingPosition.getX() + 0.5;
            final double n2 = this.hangingPosition.getY() + 0.5;
            final double n3 = this.hangingPosition.getZ() + 0.5;
            final double func_174858_a = this.func_174858_a(this.getWidthPixels());
            final double func_174858_a2 = this.func_174858_a(this.getHeightPixels());
            final double n4 = n - this.facingDirection.getFrontOffsetX() * 0.46875;
            final double n5 = n3 - this.facingDirection.getFrontOffsetZ() * 0.46875;
            final double posY = n2 + func_174858_a2;
            final EnumFacing rotateYCCW = this.facingDirection.rotateYCCW();
            final double posX = n4 + func_174858_a * rotateYCCW.getFrontOffsetX();
            final double posZ = n5 + func_174858_a * rotateYCCW.getFrontOffsetZ();
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            double n6 = this.getWidthPixels();
            final double n7 = this.getHeightPixels();
            double n8 = this.getWidthPixels();
            if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
                n8 = 1.0;
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                n6 = 1.0;
            }
            final double n9 = n6 / 32.0;
            final double n10 = n7 / 32.0;
            final double n11 = n8 / 32.0;
            this.setEntityBoundingBox(new AxisAlignedBB(posX - n9, posY - n10, posZ - n11, posX + n9, posY + n10, posZ + n11));
        }
    }
    
    @Override
    public void addVelocity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.hangingPosition = new BlockPos(nbtTagCompound.getInteger(EntityHanging.I[0x4B ^ 0x4F]), nbtTagCompound.getInteger(EntityHanging.I[0x5C ^ 0x59]), nbtTagCompound.getInteger(EntityHanging.I[0xB5 ^ 0xB3]));
        EnumFacing enumFacing;
        if (nbtTagCompound.hasKey(EntityHanging.I[0x33 ^ 0x34], 0x4C ^ 0x2F)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte(EntityHanging.I[0x5B ^ 0x53]));
            this.hangingPosition = this.hangingPosition.offset(enumFacing);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (nbtTagCompound.hasKey(EntityHanging.I[0x82 ^ 0x8B], 0x7C ^ 0x1F)) {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte(EntityHanging.I[0xCC ^ 0xC6]));
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            enumFacing = EnumFacing.getHorizontal(nbtTagCompound.getByte(EntityHanging.I[0xAF ^ 0xA4]));
        }
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    @Override
    protected boolean shouldSetPosAfterLoading() {
        return "".length() != 0;
    }
    
    public abstract void onBroken(final Entity p0);
    
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void moveEntity(final double n, final double n2, final double n3) {
        if (!this.worldObj.isRemote && !this.isDead && n * n + n2 * n2 + n3 * n3 > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    private static void I() {
        (I = new String[0x81 ^ 0x8D])["".length()] = I("\u001c\u000b\u0004\u0005\u001e=", "Zjglp");
        EntityHanging.I[" ".length()] = I(";&4\u0006\u000f", "oOXcW");
        EntityHanging.I["  ".length()] = I("\u0012\u0001\u000f\u00128", "Fhcwa");
        EntityHanging.I["   ".length()] = I(",\u00024\"\u0003", "xkXGY");
        EntityHanging.I[0xA2 ^ 0xA6] = I("9;\u001a\u0000\"", "mRvez");
        EntityHanging.I[0xD ^ 0x8] = I("<1\u001b1\f", "hXwTU");
        EntityHanging.I[0xA9 ^ 0xAF] = I("\u0015\u0000\u0006&\u0000", "AijCZ");
        EntityHanging.I[0x51 ^ 0x56] = I("\u0011\u0006\u0001\u0015/!\u0006\u001c\u001e", "UospL");
        EntityHanging.I[0xB ^ 0x3] = I("\u001e%&\u0001\u0017.%;\n", "ZLTdt");
        EntityHanging.I[0xB6 ^ 0xBF] = I("\u0016+\u0012\u0004 7", "PJqmN");
        EntityHanging.I[0x5D ^ 0x57] = I("*\u0014\u000e\u0004\u0002\u000b", "lumml");
        EntityHanging.I[0x7A ^ 0x71] = I("\u0015,;", "QEIaS");
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(damageSource.getEntity());
        }
        return " ".length() != 0;
    }
    
    public EntityHanging(final World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }
    
    public abstract int getHeightPixels();
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final int tickCounter1 = this.tickCounter1;
        this.tickCounter1 = tickCounter1 + " ".length();
        if (tickCounter1 == (0xF6 ^ 0x92) && !this.worldObj.isRemote) {
            this.tickCounter1 = "".length();
            if (!this.isDead && !this.onValidSurface()) {
                this.setDead();
                this.onBroken(null);
            }
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return " ".length() != 0;
    }
    
    private double func_174858_a(final int n) {
        double n2;
        if (n % (0x9 ^ 0x29) == 0) {
            n2 = 0.5;
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n2 = 0.0;
        }
        return n2;
    }
    
    @Override
    public EnumFacing getHorizontalFacing() {
        return this.facingDirection;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte(EntityHanging.I["".length()], (byte)this.facingDirection.getHorizontalIndex());
        nbtTagCompound.setInteger(EntityHanging.I[" ".length()], this.getHangingPosition().getX());
        nbtTagCompound.setInteger(EntityHanging.I["  ".length()], this.getHangingPosition().getY());
        nbtTagCompound.setInteger(EntityHanging.I["   ".length()], this.getHangingPosition().getZ());
    }
    
    public EntityHanging(final World world, final BlockPos hangingPosition) {
        this(world);
        this.hangingPosition = hangingPosition;
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final BlockPos hangingPosition = this.hangingPosition;
        this.hangingPosition = new BlockPos(posX, posY, posZ);
        if (!this.hangingPosition.equals(hangingPosition)) {
            this.updateBoundingBox();
            this.isAirBorne = (" ".length() != 0);
        }
    }
    
    static {
        I();
    }
    
    public boolean onValidSurface() {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
            return "".length() != 0;
        }
        final int max = Math.max(" ".length(), this.getWidthPixels() / (0x37 ^ 0x27));
        final int max2 = Math.max(" ".length(), this.getHeightPixels() / (0xA6 ^ 0xB6));
        final BlockPos offset = this.hangingPosition.offset(this.facingDirection.getOpposite());
        final EnumFacing rotateYCCW = this.facingDirection.rotateYCCW();
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < max) {
            int j = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (j < max2) {
                final Block block = this.worldObj.getBlockState(offset.offset(rotateYCCW, i).up(j)).getBlock();
                if (!block.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(block)) {
                    return "".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        final Iterator<Entity> iterator = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()).iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next() instanceof EntityHanging) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    public abstract int getWidthPixels();
}
