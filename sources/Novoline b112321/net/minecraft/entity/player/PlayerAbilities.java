package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerAbilities {
   private boolean disabledDamage;
   private boolean isFlying;
   private boolean allowFlying;
   private boolean isCreative;
   private boolean allowEdit = true;
   private float flySpeed = 0.05F;
   private float walkSpeed = 0.1F;

   public void writeCapabilitiesToNBT(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      var2.setBoolean("invulnerable", this.disabledDamage);
      var2.setBoolean("flying", this.isFlying);
      var2.setBoolean("mayfly", this.allowFlying);
      var2.setBoolean("instabuild", this.isCreative);
      var2.setBoolean("mayBuild", this.allowEdit);
      var2.setFloat("flySpeed", this.flySpeed);
      var2.setFloat("walkSpeed", this.walkSpeed);
      var1.setTag("abilities", var2);
   }

   public void readCapabilitiesFromNBT(NBTTagCompound var1) {
      if(var1.hasKey("abilities", 10)) {
         NBTTagCompound var2 = var1.getCompoundTag("abilities");
         this.disabledDamage = var2.getBoolean("invulnerable");
         this.isFlying = var2.getBoolean("flying");
         this.allowFlying = var2.getBoolean("mayfly");
         this.isCreative = var2.getBoolean("instabuild");
         if(var2.hasKey("flySpeed", 99)) {
            this.flySpeed = var2.getFloat("flySpeed");
            this.walkSpeed = var2.getFloat("walkSpeed");
         }

         if(var2.hasKey("mayBuild", 1)) {
            this.allowEdit = var2.getBoolean("mayBuild");
         }
      }

   }

   public void setDisabledDamage(boolean var1) {
      this.disabledDamage = var1;
   }

   public void setFlying(boolean var1) {
      this.isFlying = var1;
   }

   public void setAllowFlying(boolean var1) {
      this.allowFlying = var1;
   }

   public void setCreative(boolean var1) {
      this.isCreative = var1;
   }

   public void setAllowEdit(boolean var1) {
      this.allowEdit = var1;
   }

   public void setFlySpeed(float var1) {
      this.flySpeed = var1;
   }

   public void setWalkSpeed(float var1) {
      this.walkSpeed = var1;
   }

   public boolean isDisabledDamage() {
      return this.disabledDamage;
   }

   public boolean isFlying() {
      return this.isFlying;
   }

   public boolean isAllowFlying() {
      return this.allowFlying;
   }

   public boolean isCreative() {
      return this.isCreative;
   }

   public boolean isAllowEdit() {
      return this.allowEdit;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }
}
