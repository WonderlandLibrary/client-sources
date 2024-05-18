package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class EntityEnderCrystal extends Entity
{
    public int health;
    public int innerRotation;
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public EntityEnderCrystal(final World world) {
        super(world);
        this.preventEntitySpawning = (" ".length() != 0);
        this.setSize(2.0f, 2.0f);
        this.health = (0x2D ^ 0x28);
        this.innerRotation = this.rand.nextInt(81677 + 26335 - 72310 + 64298);
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            this.health = "".length();
            if (this.health <= 0) {
                this.setDead();
                if (!this.worldObj.isRemote) {
                    this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0f, " ".length() != 0);
                }
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return " ".length() != 0;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public EntityEnderCrystal(final World world, final double n, final double n2, final double n3) {
        this(world);
        this.setPosition(n, n2, n3);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0x10 ^ 0x18, this.health);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.innerRotation += " ".length();
        this.dataWatcher.updateObject(0xA6 ^ 0xAE, this.health);
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posY);
        final int floor_double3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.provider instanceof WorldProviderEnd && this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3)).getBlock() != Blocks.fire) {
            this.worldObj.setBlockState(new BlockPos(floor_double, floor_double2, floor_double3), Blocks.fire.getDefaultState());
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
}
