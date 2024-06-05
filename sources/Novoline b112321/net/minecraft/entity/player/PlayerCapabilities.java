package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {
   private boolean g;
   private boolean b;
   private boolean f;
   private boolean d;
   private boolean e = true;
   private float flySpeed = 0.05F;
   private float walkSpeed = 0.1F;

   public void writeCapabilitiesToNBT(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      var2.setBoolean("invulnerable", this.g);
      var2.setBoolean("flying", this.b);
      var2.setBoolean("mayfly", this.f);
      var2.setBoolean("instabuild", this.d);
      var2.setBoolean("mayBuild", this.e);
      var2.setFloat("flySpeed", this.flySpeed);
      var2.setFloat("walkSpeed", this.walkSpeed);
      var1.setTag("abilities", var2);
   }

   public void readCapabilitiesFromNBT(NBTTagCompound var1) {
      if(var1.hasKey("abilities", 10)) {
         NBTTagCompound var2 = var1.getCompoundTag("abilities");
         this.g = var2.getBoolean("invulnerable");
         this.b = var2.getBoolean("flying");
         this.f = var2.getBoolean("mayfly");
         this.d = var2.getBoolean("instabuild");
         if(var2.hasKey("flySpeed", 99)) {
            this.flySpeed = var2.getFloat("flySpeed");
            this.walkSpeed = var2.getFloat("walkSpeed");
         }

         if(var2.hasKey("mayBuild", 1)) {
            this.e = var2.getBoolean("mayBuild");
         }
      }

   }

   public void b(boolean var1) {
      this.g = var1;
   }

   public void c(boolean var1) {
      this.b = var1;
   }

   public void d(boolean var1) {
      this.f = var1;
   }

   public void a(boolean var1) {
      this.d = var1;
   }

   public void e(boolean var1) {
      this.e = var1;
   }

   public void setFlySpeed(float var1) {
      this.flySpeed = var1;
   }

   public void setPlayerWalkSpeed(float var1) {
      this.walkSpeed = var1;
   }

   public boolean d() {
      return this.g;
   }

   public boolean e() {
      return this.b;
   }

   public boolean c() {
      return this.f;
   }

   public boolean f() {
      return this.d;
   }

   public boolean b() {
      return this.e;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }
}
