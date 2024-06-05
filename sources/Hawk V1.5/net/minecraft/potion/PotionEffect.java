package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect {
   private boolean isSplashPotion;
   private static final String __OBFID = "CL_00001529";
   private int amplifier;
   private boolean isPotionDurationMax;
   private boolean showParticles;
   private static final Logger LOGGER = LogManager.getLogger();
   private int potionID;
   private int duration;
   private boolean isAmbient;

   public String toString() {
      String var1 = "";
      if (this.getAmplifier() > 0) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(this.getEffectName()))).append(" x ").append(this.getAmplifier() + 1).append(", Duration: ").append(this.getDuration()));
      } else {
         var1 = String.valueOf((new StringBuilder(String.valueOf(this.getEffectName()))).append(", Duration: ").append(this.getDuration()));
      }

      if (this.isSplashPotion) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(", Splash: true"));
      }

      if (!this.showParticles) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(", Particles: false"));
      }

      return Potion.potionTypes[this.potionID].isUsable() ? String.valueOf((new StringBuilder("(")).append(var1).append(")")) : var1;
   }

   public PotionEffect(int var1, int var2, int var3) {
      this(var1, var2, var3, false, true);
   }

   public int hashCode() {
      return this.potionID;
   }

   public void performEffect(EntityLivingBase var1) {
      if (this.duration > 0) {
         Potion.potionTypes[this.potionID].performEffect(var1, this.amplifier);
      }

   }

   public PotionEffect(PotionEffect var1) {
      this.potionID = var1.potionID;
      this.duration = var1.duration;
      this.amplifier = var1.amplifier;
      this.isAmbient = var1.isAmbient;
      this.showParticles = var1.showParticles;
   }

   public int getDuration() {
      return this.duration;
   }

   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound var0) {
      byte var1 = var0.getByte("Id");
      if (var1 >= 0 && var1 < Potion.potionTypes.length && Potion.potionTypes[var1] != null) {
         byte var2 = var0.getByte("Amplifier");
         int var3 = var0.getInteger("Duration");
         boolean var4 = var0.getBoolean("Ambient");
         boolean var5 = true;
         if (var0.hasKey("ShowParticles", 1)) {
            var5 = var0.getBoolean("ShowParticles");
         }

         return new PotionEffect(var1, var3, var2, var4, var5);
      } else {
         return null;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof PotionEffect)) {
         return false;
      } else {
         PotionEffect var2 = (PotionEffect)var1;
         return this.potionID == var2.potionID && this.amplifier == var2.amplifier && this.duration == var2.duration && this.isSplashPotion == var2.isSplashPotion && this.isAmbient == var2.isAmbient;
      }
   }

   public int getPotionID() {
      return this.potionID;
   }

   public void setPotionDurationMax(boolean var1) {
      this.isPotionDurationMax = var1;
   }

   public PotionEffect(int var1, int var2) {
      this(var1, var2, 0);
   }

   public void combine(PotionEffect var1) {
      if (this.potionID != var1.potionID) {
         LOGGER.warn("This method should only be called for matching effects!");
      }

      if (var1.amplifier > this.amplifier) {
         this.amplifier = var1.amplifier;
         this.duration = var1.duration;
      } else if (var1.amplifier == this.amplifier && this.duration < var1.duration) {
         this.duration = var1.duration;
      } else if (!var1.isAmbient && this.isAmbient) {
         this.isAmbient = var1.isAmbient;
      }

      this.showParticles = var1.showParticles;
   }

   public boolean getIsPotionDurationMax() {
      return this.isPotionDurationMax;
   }

   private int deincrementDuration() {
      return --this.duration;
   }

   public String getEffectName() {
      return Potion.potionTypes[this.potionID].getName();
   }

   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound var1) {
      var1.setByte("Id", (byte)this.getPotionID());
      var1.setByte("Amplifier", (byte)this.getAmplifier());
      var1.setInteger("Duration", this.getDuration());
      var1.setBoolean("Ambient", this.getIsAmbient());
      var1.setBoolean("ShowParticles", this.func_180154_f());
      return var1;
   }

   public int getAmplifier() {
      return this.amplifier;
   }

   public boolean getIsAmbient() {
      return this.isAmbient;
   }

   public boolean func_180154_f() {
      return this.showParticles;
   }

   public PotionEffect(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.potionID = var1;
      this.duration = var2;
      this.amplifier = var3;
      this.isAmbient = var4;
      this.showParticles = var5;
   }

   public boolean onUpdate(EntityLivingBase var1) {
      if (this.duration > 0) {
         if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
            this.performEffect(var1);
         }

         this.deincrementDuration();
      }

      return this.duration > 0;
   }

   public void setSplashPotion(boolean var1) {
      this.isSplashPotion = var1;
   }
}
