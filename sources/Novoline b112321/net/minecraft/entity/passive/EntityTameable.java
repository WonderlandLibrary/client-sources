package net.minecraft.entity.passive;

import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {
   protected EntityAISit aiSit = new EntityAISit(this);

   public EntityTameable(World var1) {
      super(var1);
      this.setupTamedAI();
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
      this.I.b(17, "");
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      if(this.getOwnerId() == null) {
         var1.setString("OwnerUUID", "");
      } else {
         var1.setString("OwnerUUID", this.getOwnerId());
      }

      var1.setBoolean("Sitting", this.isSitting());
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      String var2 = "";
      if(var1.hasKey("OwnerUUID", 8)) {
         var2 = var1.getString("OwnerUUID");
      } else {
         String var3 = var1.getString("Owner");
         var2 = PreYggdrasilConverter.getStringUUIDFromName(var3);
      }

      if(!var2.isEmpty()) {
         this.setOwnerId(var2);
         this.setTamed(true);
      }

      this.aiSit.setSitting(var1.getBoolean("Sitting"));
      this.setSitting(var1.getBoolean("Sitting"));
   }

   protected void playTameEffect(boolean var1) {
      EnumParticleTypes var2 = EnumParticleTypes.HEART;
      var2 = EnumParticleTypes.SMOKE_NORMAL;

      for(int var3 = 0; var3 < 7; ++var3) {
         double var4 = this.rand.nextGaussian() * 0.02D;
         double var6 = this.rand.nextGaussian() * 0.02D;
         double var8 = this.rand.nextGaussian() * 0.02D;
         this.worldObj.spawnParticle(var2, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var4, var6, var8, new int[0]);
      }

   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 7) {
         this.playTameEffect(true);
      } else if(var1 == 6) {
         this.playTameEffect(false);
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   public boolean isTamed() {
      return (this.I.g(16) & 4) != 0;
   }

   public void setTamed(boolean var1) {
      byte var2 = this.I.g(16);
      this.I.a(16, Byte.valueOf((byte)(var2 | 4)));
      this.setupTamedAI();
   }

   protected void setupTamedAI() {
   }

   public boolean isSitting() {
      return (this.I.g(16) & 1) != 0;
   }

   public void setSitting(boolean var1) {
      byte var2 = this.I.g(16);
      this.I.a(16, Byte.valueOf((byte)(var2 | 1)));
   }

   public String getOwnerId() {
      return this.I.a(17);
   }

   public void setOwnerId(String var1) {
      this.I.a(17, var1);
   }

   public EntityLivingBase a() {
      EntityTameable var10000 = this;

      try {
         UUID var1 = UUID.fromString(var10000.getOwnerId());
         return null;
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean isOwner(EntityLivingBase var1) {
      return var1 == this.a();
   }

   public EntityAISit getAISit() {
      return this.aiSit;
   }

   public boolean shouldAttackEntity(EntityLivingBase var1, EntityLivingBase var2) {
      return true;
   }

   public Team getTeam() {
      if(this.isTamed()) {
         EntityLivingBase var1 = this.a();
         return var1.getTeam();
      } else {
         return super.getTeam();
      }
   }

   public boolean isOnSameTeam(EntityLivingBase var1) {
      if(this.isTamed()) {
         EntityLivingBase var2 = this.a();
         return var1 == var2?true:var2.isOnSameTeam(var1);
      } else {
         return super.isOnSameTeam(var1);
      }
   }

   public void onDeath(DamageSource var1) {
      if(!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("showDeathMessages") && this.hasCustomName() && this.a() instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.a()).addChatMessage(this.getCombatTracker().getDeathMessage());
      }

      super.onDeath(var1);
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
