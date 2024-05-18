package net.minecraft.entity.passive;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    protected Block spawnableBlock;
    private static final String[] I;
    private int inLove;
    private EntityPlayer playerInLove;
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY), MathHelper.floor_double(this.posZ));
        if (this.worldObj.getBlockState(blockPos.down()).getBlock() == this.spawnableBlock && this.worldObj.getLight(blockPos) > (0x5A ^ 0x52) && super.getCanSpawnHere()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        float n;
        if (this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.grass) {
            n = 10.0f;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n = this.worldObj.getLightBrightness(blockPos) - 0.5f;
        }
        return n;
    }
    
    @Override
    protected void updateAITasks() {
        if (this.getGrowingAge() != 0) {
            this.inLove = "".length();
        }
        super.updateAITasks();
    }
    
    @Override
    public boolean interact(final EntityPlayer inLove) {
        final ItemStack currentItem = inLove.inventory.getCurrentItem();
        if (currentItem != null) {
            if (this.isBreedingItem(currentItem) && this.getGrowingAge() == 0 && this.inLove <= 0) {
                this.consumeItemFromStack(inLove, currentItem);
                this.setInLove(inLove);
                return " ".length() != 0;
            }
            if (this.isChild() && this.isBreedingItem(currentItem)) {
                this.consumeItemFromStack(inLove, currentItem);
                this.func_175501_a((int)(-this.getGrowingAge() / (0x9C ^ 0x88) * 0.1f), " ".length() != 0);
                return " ".length() != 0;
            }
        }
        return super.interact(inLove);
    }
    
    public void setInLove(final EntityPlayer playerInLove) {
        this.inLove = 116 + 275 + 103 + 106;
        this.playerInLove = playerInLove;
        this.worldObj.setEntityState(this, (byte)(0x7 ^ 0x15));
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u000f\b\u0005\u0003\u0011#", "FfIlg");
        EntityAnimal.I[" ".length()] = I("\u00134\u001e\u001a2?", "ZZRuD");
    }
    
    static {
        I();
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        int n;
        if (itemStack == null) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (itemStack.getItem() == Items.wheat) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void resetInLove() {
        this.inLove = "".length();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityAnimal.I["".length()], this.inLove);
    }
    
    @Override
    public int getTalkInterval() {
        return 0x37 ^ 0x4F;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.inLove = "".length();
        return super.attackEntityFrom(damageSource, n);
    }
    
    public EntityAnimal(final World world) {
        super(world);
        this.spawnableBlock = Blocks.grass;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x28 ^ 0x3A)) {
            int i = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i < (0x9A ^ 0x9D)) {
                this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
                ++i;
            }
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.inLove = nbtTagCompound.getInteger(EntityAnimal.I[" ".length()]);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = "".length();
        }
        if (this.inLove > 0) {
            this.inLove -= " ".length();
            if (this.inLove % (0xF ^ 0x5) == 0) {
                this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
            }
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return " ".length() + this.worldObj.rand.nextInt("   ".length());
    }
    
    public boolean isInLove() {
        if (this.inLove > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityPlayer getPlayerInLove() {
        return this.playerInLove;
    }
    
    protected void consumeItemFromStack(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
            if (itemStack.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return "".length() != 0;
    }
    
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        int n;
        if (entityAnimal == this) {
            n = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (entityAnimal.getClass() != this.getClass()) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.isInLove() && entityAnimal.isInLove()) {
            n = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
}
