package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final int potionID;
   private int duration;
   private int amplifier;
   private boolean isSplashPotion;
   private boolean isAmbient;
   private boolean isPotionDurationMax;
   private boolean showParticles;

   public PotionEffect(int var1, int var2) {
      this(var1, var2, 0);
   }

   public PotionEffect(int var1, int var2, int var3) {
      this(var1, var2, var3, false, true);
   }

   public PotionEffect(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.potionID = var1;
      this.duration = var2;
      this.amplifier = var3;
      this.isAmbient = var4;
      this.showParticles = var5;
   }

   public PotionEffect(PotionEffect var1) {
      this.potionID = var1.potionID;
      this.duration = var1.duration;
      this.amplifier = var1.amplifier;
      this.isAmbient = var1.isAmbient;
      this.showParticles = var1.showParticles;
   }

   public void combine(PotionEffect var1) {
      if(this.potionID != var1.potionID) {
         LOGGER.warn("This method should only be called for matching effects!");
      }

      if(var1.amplifier > this.amplifier) {
         this.amplifier = var1.amplifier;
         this.duration = var1.duration;
      } else if(var1.amplifier == this.amplifier && this.duration < var1.duration) {
         this.duration = var1.duration;
      } else if(!var1.isAmbient && this.isAmbient) {
         this.isAmbient = var1.isAmbient;
      }

      this.showParticles = var1.showParticles;
   }

   public int getPotionID() {
      return this.potionID;
   }

   public int getDuration() {
      return this.duration;
   }

   public int getAmplifier() {
      return this.amplifier;
   }

   public void setSplashPotion(boolean var1) {
      this.isSplashPotion = var1;
   }

   public boolean getIsAmbient() {
      return this.isAmbient;
   }

   public boolean getIsShowParticles() {
      return this.showParticles;
   }

   public boolean onUpdate(EntityLivingBase var1) {
      if(this.duration > 0) {
         if(Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
            this.performEffect(var1);
         }

         this.deincrementDuration();
      }

      return this.duration > 0;
   }

   private void deincrementDuration() {
      --this.duration;
   }

   public void performEffect(EntityLivingBase var1) {
      if(this.duration > 0) {
         Potion.potionTypes[this.potionID].performEffect(var1, this.amplifier);
      }

   }

   public String getEffectName() {
      return Potion.potionTypes[this.potionID].getName();
   }

   public int hashCode() {
      return this.potionID;
   }

   public String toString() {
      String var1 = "";
      if(this.getAmplifier() > 0) {
         var1 = this.getEffectName() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
      } else {
         var1 = this.getEffectName() + ", Duration: " + this.getDuration();
      }

      if(this.isSplashPotion) {
         var1 = var1 + ", Splash: true";
      }

      if(!this.showParticles) {
         var1 = var1 + ", Particles: false";
      }

      return Potion.potionTypes[this.potionID].isUsable()?"(" + var1 + ")":var1;
   }

   public boolean equals(Object var1) {
      if(!(var1 instanceof PotionEffect)) {
         return false;
      } else {
         PotionEffect var2 = (PotionEffect)var1;
         return this.potionID == var2.potionID && this.amplifier == var2.amplifier && this.duration == var2.duration && this.isSplashPotion == var2.isSplashPotion && this.isAmbient == var2.isAmbient;
      }
   }

   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound var1) {
      var1.setByte("Id", (byte)this.getPotionID());
      var1.setByte("Amplifier", (byte)this.getAmplifier());
      var1.setInteger("Duration", this.getDuration());
      var1.setBoolean("Ambient", this.getIsAmbient());
      var1.setBoolean("ShowParticles", this.getIsShowParticles());
      return var1;
   }

   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound var0) {
      byte var1 = var0.getByte("Id");
      if(var1 < Potion.potionTypes.length && Potion.potionTypes[var1] != null) {
         byte var2 = var0.getByte("Amplifier");
         int var3 = var0.getInteger("Duration");
         boolean var4 = var0.getBoolean("Ambient");
         boolean var5 = true;
         if(var0.hasKey("ShowParticles", 1)) {
            var5 = var0.getBoolean("ShowParticles");
         }

         return new PotionEffect(var1, var3, var2, var4, var5);
      } else {
         return null;
      }
   }

   public void setPotionDurationMax(boolean var1) {
      this.isPotionDurationMax = var1;
   }

   public boolean getIsPotionDurationMax() {
      return this.isPotionDurationMax;
   }

   public void setShowParticles(boolean var1) {
      this.showParticles = var1;
   }
}
