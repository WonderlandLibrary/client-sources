/*  1:   */ package net.minecraft.entity.player;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ 
/*  5:   */ public class PlayerCapabilities
/*  6:   */ {
/*  7:   */   public boolean disableDamage;
/*  8:   */   public boolean isFlying;
/*  9:   */   public boolean allowFlying;
/* 10:   */   public boolean isCreativeMode;
/* 11:22 */   public boolean allowEdit = true;
/* 12:23 */   private float flySpeed = 0.05F;
/* 13:24 */   private float walkSpeed = 0.1F;
/* 14:   */   private static final String __OBFID = "CL_00001708";
/* 15:   */   
/* 16:   */   public void writeCapabilitiesToNBT(NBTTagCompound par1NBTTagCompound)
/* 17:   */   {
/* 18:29 */     NBTTagCompound var2 = new NBTTagCompound();
/* 19:30 */     var2.setBoolean("invulnerable", this.disableDamage);
/* 20:31 */     var2.setBoolean("flying", this.isFlying);
/* 21:32 */     var2.setBoolean("mayfly", this.allowFlying);
/* 22:33 */     var2.setBoolean("instabuild", this.isCreativeMode);
/* 23:34 */     var2.setBoolean("mayBuild", this.allowEdit);
/* 24:35 */     var2.setFloat("flySpeed", this.flySpeed);
/* 25:36 */     var2.setFloat("walkSpeed", this.walkSpeed);
/* 26:37 */     par1NBTTagCompound.setTag("abilities", var2);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void readCapabilitiesFromNBT(NBTTagCompound par1NBTTagCompound)
/* 30:   */   {
/* 31:42 */     if (par1NBTTagCompound.func_150297_b("abilities", 10))
/* 32:   */     {
/* 33:44 */       NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("abilities");
/* 34:45 */       this.disableDamage = var2.getBoolean("invulnerable");
/* 35:46 */       this.isFlying = var2.getBoolean("flying");
/* 36:47 */       this.allowFlying = var2.getBoolean("mayfly");
/* 37:48 */       this.isCreativeMode = var2.getBoolean("instabuild");
/* 38:50 */       if (var2.func_150297_b("flySpeed", 99))
/* 39:   */       {
/* 40:52 */         this.flySpeed = var2.getFloat("flySpeed");
/* 41:53 */         this.walkSpeed = var2.getFloat("walkSpeed");
/* 42:   */       }
/* 43:56 */       if (var2.func_150297_b("mayBuild", 1)) {
/* 44:58 */         this.allowEdit = var2.getBoolean("mayBuild");
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float getFlySpeed()
/* 50:   */   {
/* 51:65 */     return this.flySpeed;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setFlySpeed(float par1)
/* 55:   */   {
/* 56:70 */     this.flySpeed = par1;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public float getWalkSpeed()
/* 60:   */   {
/* 61:75 */     return this.walkSpeed;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setPlayerWalkSpeed(float par1)
/* 65:   */   {
/* 66:80 */     this.walkSpeed = par1;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.player.PlayerCapabilities
 * JD-Core Version:    0.7.0.1
 */