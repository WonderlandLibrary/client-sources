package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;

public class EntityPig extends EntityAnimal
{
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    
    public EntityPig(final World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 0.9f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3f));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, false));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public boolean canBeSteered() {
        final ItemStack var1 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return var1 != null && var1.getItem() == Items.carrot_on_a_stick;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Saddle", this.getSaddled());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.setSaddled(tagCompund.getBoolean("Saddle"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }
    
    @Override
    protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        if (super.interact(p_70085_1_)) {
            return true;
        }
        if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
            p_70085_1_.mountEntity(this);
            return true;
        }
        return false;
    }
    
    @Override
    protected Item getDropItem() {
        return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_porkchop, 1);
            }
            else {
                this.dropItem(Items.porkchop, 1);
            }
        }
        if (this.getSaddled()) {
            this.dropItem(Items.saddle, 1);
        }
    }
    
    public boolean getSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setSaddled(final boolean p_70900_1_) {
        if (p_70900_1_) {
            this.dataWatcher.updateObject(16, 1);
        }
        else {
            this.dataWatcher.updateObject(16, 0);
        }
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        if (!this.worldObj.isRemote) {
            final EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(var2);
            this.setDead();
        }
    }
    
    @Override
    public void fall(final float distance, final float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        if (distance > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }
    
    @Override
    public EntityPig createChild(final EntityAgeable p_90011_1_) {
        return new EntityPig(this.worldObj);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.getItem() == Items.carrot;
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
}
