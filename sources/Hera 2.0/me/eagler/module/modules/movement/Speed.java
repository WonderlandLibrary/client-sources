/*     */ package me.eagler.module.modules.movement;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.PlayerUtils;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.MovementInput;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Speed
/*     */   extends Module
/*     */ {
/*  18 */   private TimeHelper time = new TimeHelper();
/*     */   
/*  20 */   private int counter = 0;
/*     */   
/*     */   public Speed() {
/*  23 */     super("Speed", "Speed", 0, Category.Movement);
/*     */     
/*  25 */     ArrayList<String> options = new ArrayList<String>();
/*     */     
/*  27 */     options.add("CubeCraft");
/*  28 */     options.add("CCOnGround");
/*  29 */     options.add("Gomme");
/*     */     
/*  31 */     this.settingManager.addSetting(new Setting("SpeedMode", this, "CubeCraft", options));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  36 */     this.mc.timer.timerSpeed = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  42 */     String mode = this.settingManager.getSettingByName("SpeedMode").getMode();
/*     */     
/*  44 */     setExtraTag(mode);
/*     */     
/*  46 */     if (Client.instance.getModuleManager().getModuleByName("Knockback").isEnabled())
/*     */     {
/*  48 */       if (this.settingManager.getSettingByName("KBMode").getMode().equalsIgnoreCase("Reverse"))
/*     */       {
/*     */ 
/*     */         
/*  52 */         if (this.mc.thePlayer.hurtTime > 0) {
/*     */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if (mode.equalsIgnoreCase("CubeCraft")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       this.mc.gameSettings.keyBindJump.pressed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       if (PlayerUtils.playeriswalking())
/*     */       {
/* 124 */         if (this.mc.thePlayer.onGround)
/*     */         {
/* 126 */           this.mc.thePlayer.jump();
/* 127 */           this.mc.thePlayer.motionY = 0.42D;
/* 128 */           this.mc.timer.timerSpeed = 1.0F;
/*     */         }
/*     */         else
/*     */         {
/* 132 */           this.mc.timer.timerSpeed = 1.0F;
/* 133 */           double speed = 1.01D;
/*     */           
/* 135 */           if (this.time.hasReached(1000L)) {
/*     */             
/* 137 */             this.time.reset();
/*     */           }
/* 139 */           else if (this.time.hasReached(500L)) {
/*     */             
/* 141 */             speed = 1.02D;
/*     */           } 
/*     */ 
/*     */           
/* 145 */           double Motion = Math.sqrt(
/* 146 */               this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
/*     */           
/* 148 */           if (this.mc.thePlayer.hurtTime < 5)
/*     */           {
/* 150 */             this.mc.thePlayer.motionX = -Math.sin(getDirection()) * speed * Motion;
/* 151 */             this.mc.thePlayer.motionZ = Math.cos(getDirection()) * speed * Motion;
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 159 */     else if (mode.equalsIgnoreCase("CCOnGround")) {
/*     */       
/* 161 */       this.mc.gameSettings.keyBindJump.pressed = false;
/*     */       
/* 163 */       if (this.mc.thePlayer.onGround)
/*     */       {
/* 165 */         if (this.counter < 2)
/*     */         {
/* 167 */           if (this.counter == 1) {
/*     */ 
/*     */ 
/*     */             
/* 171 */             setSpeed(0.001D);
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 177 */             setSpeed(1.5D);
/*     */           } 
/*     */ 
/*     */           
/* 181 */           this.counter++;
/*     */         }
/*     */         else
/*     */         {
/* 185 */           this.counter = 0;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 191 */     else if (mode.equalsIgnoreCase("Gomme")) {
/*     */       
/* 193 */       this.mc.gameSettings.keyBindJump.pressed = false;
/*     */       
/* 195 */       if (PlayerUtils.playeriswalking())
/*     */       {
/* 197 */         if (this.mc.thePlayer.onGround) {
/*     */           
/* 199 */           this.mc.thePlayer.jump();
/* 200 */           this.mc.thePlayer.motionY = 0.42D;
/* 201 */           this.mc.timer.timerSpeed = 1.0F;
/*     */         }
/*     */         else {
/*     */           
/* 205 */           this.mc.timer.timerSpeed = 1.0F;
/* 206 */           double speed = 1.007D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 218 */           double Motion = Math.sqrt(
/* 219 */               this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
/*     */           
/* 221 */           if (this.mc.thePlayer.hurtTime < 5) {
/*     */             
/* 223 */             this.mc.thePlayer.motionX = -Math.sin(getDirection()) * speed * Motion;
/* 224 */             this.mc.thePlayer.motionZ = Math.cos(getDirection()) * speed * Motion;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDirection() {
/* 237 */     float var1 = this.mc.thePlayer.rotationYaw;
/* 238 */     if (this.mc.thePlayer.moveForward < 0.0F)
/* 239 */       var1 += 180.0F; 
/* 240 */     float forward = 1.0F;
/* 241 */     if (this.mc.thePlayer.moveForward < 0.0F) {
/* 242 */       forward = -0.5F;
/* 243 */     } else if (this.mc.thePlayer.moveForward > 0.0F) {
/* 244 */       forward = 0.5F;
/*     */     } 
/* 246 */     if (this.mc.thePlayer.moveStrafing > 0.0F)
/* 247 */       var1 -= 90.0F * forward; 
/* 248 */     if (this.mc.thePlayer.moveStrafing < 0.0F)
/* 249 */       var1 += 90.0F * forward; 
/* 250 */     var1 *= 0.017453292F;
/* 251 */     return var1;
/*     */   }
/*     */   
/*     */   public static void setSpeed(double speed) {
/* 255 */     Minecraft mc = Minecraft.getMinecraft();
/* 256 */     MovementInput movementInput = mc.thePlayer.movementInput;
/* 257 */     float forward = movementInput.moveForward;
/* 258 */     float strafe = movementInput.moveStrafe;
/* 259 */     float yaw = mc.thePlayer.rotationYaw;
/* 260 */     if (forward == 0.0F && strafe == 0.0F) {
/* 261 */       mc.thePlayer.motionX = 0.0D;
/* 262 */       mc.thePlayer.motionZ = 0.0D;
/* 263 */     } else if (forward != 0.0F) {
/* 264 */       if (strafe >= 1.0F) {
/* 265 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 266 */         strafe = 0.0F;
/* 267 */       } else if (strafe <= -1.0F) {
/* 268 */         yaw += ((forward > 0.0F) ? 45 : -45);
/* 269 */         strafe = 0.0F;
/*     */       } 
/* 271 */       if (forward > 0.0F) {
/* 272 */         forward = 1.0F;
/* 273 */       } else if (forward < 0.0F) {
/* 274 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/* 277 */     double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 278 */     double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 279 */     double motionX = forward * speed * mx + strafe * speed * mz;
/* 280 */     double motionZ = forward * speed * mz - strafe * speed * mx;
/* 281 */     mc.thePlayer.motionX = forward * speed * mx + strafe * speed * mz;
/* 282 */     mc.thePlayer.motionZ = forward * speed * mz - strafe * speed * mz;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\Speed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */