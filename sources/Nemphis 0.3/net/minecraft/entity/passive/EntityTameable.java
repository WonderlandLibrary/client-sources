/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class EntityTameable
extends EntityAnimal
implements IEntityOwnable {
    protected EntityAISit aiSit;
    private static final String __OBFID = "CL_00001561";

    public EntityTameable(World worldIn) {
        super(worldIn);
        this.aiSit = new EntityAISit(this);
        this.func_175544_ck();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf(0));
        this.dataWatcher.addObject(17, "");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        if (this.func_152113_b() == null) {
            tagCompound.setString("OwnerUUID", "");
        } else {
            tagCompound.setString("OwnerUUID", this.func_152113_b());
        }
        tagCompound.setBoolean("Sitting", this.isSitting());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        String var2 = "";
        if (tagCompund.hasKey("OwnerUUID", 8)) {
            var2 = tagCompund.getString("OwnerUUID");
        } else {
            String var3 = tagCompund.getString("Owner");
            var2 = PreYggdrasilConverter.func_152719_a(var3);
        }
        if (var2.length() > 0) {
            this.func_152115_b(var2);
            this.setTamed(true);
        }
        this.aiSit.setSitting(tagCompund.getBoolean("Sitting"));
        this.setSitting(tagCompund.getBoolean("Sitting"));
    }

    protected void playTameEffect(boolean p_70908_1_) {
        EnumParticleTypes var2 = EnumParticleTypes.HEART;
        if (!p_70908_1_) {
            var2 = EnumParticleTypes.SMOKE_NORMAL;
        }
        int var3 = 0;
        while (var3 < 7) {
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(var2, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var4, var6, var8, new int[0]);
            ++var3;
        }
    }

    @Override
    public void handleHealthUpdate(byte p_70103_1_) {
        if (p_70103_1_ == 7) {
            this.playTameEffect(true);
        } else if (p_70103_1_ == 6) {
            this.playTameEffect(false);
        } else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }

    public boolean isTamed() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 4) != 0) {
            return true;
        }
        return false;
    }

    public void setTamed(boolean p_70903_1_) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70903_1_) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
        this.func_175544_ck();
    }

    protected void func_175544_ck() {
    }

    public boolean isSitting() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void setSitting(boolean p_70904_1_) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70904_1_) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    @Override
    public String func_152113_b() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void func_152115_b(String p_152115_1_) {
        this.dataWatcher.updateObject(17, p_152115_1_);
    }

    public EntityLivingBase func_180492_cm() {
        try {
            UUID var1 = UUID.fromString(this.func_152113_b());
            return var1 == null ? null : this.worldObj.getPlayerEntityByUUID(var1);
        }
        catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean func_152114_e(EntityLivingBase p_152114_1_) {
        if (p_152114_1_ == this.func_180492_cm()) {
            return true;
        }
        return false;
    }

    public EntityAISit getAISit() {
        return this.aiSit;
    }

    public boolean func_142018_a(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
        return true;
    }

    @Override
    public Team getTeam() {
        EntityLivingBase var1;
        if (this.isTamed() && (var1 = this.func_180492_cm()) != null) {
            return var1.getTeam();
        }
        return super.getTeam();
    }

    @Override
    public boolean isOnSameTeam(EntityLivingBase p_142014_1_) {
        if (this.isTamed()) {
            EntityLivingBase var2 = this.func_180492_cm();
            if (p_142014_1_ == var2) {
                return true;
            }
            if (var2 != null) {
                return var2.isOnSameTeam(p_142014_1_);
            }
        }
        return super.isOnSameTeam(p_142014_1_);
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages") && this.hasCustomName() && this.func_180492_cm() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.func_180492_cm()).addChatMessage(this.getCombatTracker().func_151521_b());
        }
        super.onDeath(cause);
    }

    @Override
    public Entity getOwner() {
        return this.func_180492_cm();
    }
}

