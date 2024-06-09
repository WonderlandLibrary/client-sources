/*     */ package me.eagler.module.modules.movement;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.PlayerUtils;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ 
/*     */ public class Glide
/*     */   extends Module
/*     */ {
/*  13 */   private TimeHelper time = new TimeHelper();
/*     */   
/*  15 */   private float xzBoost = 0.0F;
/*  16 */   private float yBoost = -0.1F;
/*     */   
/*  18 */   private float timerSpeed = 1.0F;
/*     */   
/*  20 */   private int counter = 0;
/*     */   
/*     */   public Glide() {
/*  23 */     super("Glide", Category.Movement);
/*     */     
/*  25 */     ArrayList<String> options = new ArrayList<String>();
/*     */     
/*  27 */     options.add("CubeCraft");
/*  28 */     options.add("CubeCraft2");
/*     */     
/*  30 */     this.settingManager.addSetting(new Setting("GlideMode", this, "CubeCraft", options));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  35 */     String mode = this.settingManager.getSettingByName("GlideMode").getMode();
/*     */     
/*  37 */     if (mode.equalsIgnoreCase("CubeCraft"))
/*     */     {
/*  39 */       PlayerUtils.sendMessage("Place a block before landing.", true);
/*     */     }
/*     */ 
/*     */     
/*  43 */     this.time.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  49 */     this.mc.timer.timerSpeed = 1.0F;
/*     */     
/*  51 */     this.counter = 0;
/*     */     
/*  53 */     this.mc.thePlayer.motionX = 0.0D;
/*  54 */     this.mc.thePlayer.motionZ = 0.0D;
/*     */     
/*  56 */     this.mc.timer.timerSpeed = 1.0F;
/*     */     
/*  58 */     Speed.setSpeed(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  64 */     String mode = this.settingManager.getSettingByName("GlideMode").getMode();
/*     */     
/*  66 */     setExtraTag(mode);
/*     */     
/*  68 */     if (mode.equalsIgnoreCase("CubeCraft")) {
/*     */       
/*  70 */       if (PlayerUtils.playeriswalking())
/*     */       {
/*  72 */         if (this.counter < 2) {
/*     */           
/*  74 */           if (this.counter == 1) {
/*     */             
/*  76 */             this.mc.thePlayer.motionY = 0.01D;
/*     */           }
/*     */           else {
/*     */             
/*  80 */             this.mc.thePlayer.motionY = -0.01D;
/*     */           } 
/*     */ 
/*     */           
/*  84 */           this.counter++;
/*     */         }
/*     */         else {
/*     */           
/*  88 */           this.counter = 0;
/*     */         } 
/*     */ 
/*     */         
/*  92 */         this.mc.timer.timerSpeed = 0.2F;
/*  93 */         Speed.setSpeed(2.0D);
/*     */       }
/*     */       else
/*     */       {
/*  97 */         this.mc.thePlayer.motionX = 0.0D;
/*  98 */         this.mc.thePlayer.motionZ = 0.0D;
/*     */         
/* 100 */         this.mc.timer.timerSpeed = 1.0F;
/*     */       }
/*     */     
/*     */     }
/* 104 */     else if (mode.equalsIgnoreCase("CubeCraft2")) {
/*     */       
/* 106 */       if (PlayerUtils.playeriswalking()) {
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
/* 128 */         if (this.counter < 5)
/*     */         {
/* 130 */           if (this.counter == 4) {
/*     */             
/* 132 */             this.mc.thePlayer.motionY = -0.01D;
/*     */             
/* 134 */             this.mc.timer.timerSpeed = 0.25F;
/*     */           }
/* 136 */           else if (this.counter == 3) {
/*     */             
/* 138 */             this.mc.thePlayer.motionY = 0.5D;
/*     */             
/* 140 */             this.mc.timer.timerSpeed = 0.25F;
/*     */           }
/* 142 */           else if (this.counter == 2) {
/*     */             
/* 144 */             this.mc.thePlayer.motionY = -0.01D;
/*     */             
/* 146 */             this.mc.timer.timerSpeed = 0.4F;
/*     */           }
/* 148 */           else if (this.counter == 1) {
/*     */             
/* 150 */             this.mc.thePlayer.motionY = 0.5D;
/*     */             
/* 152 */             this.mc.timer.timerSpeed = 0.25F;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 159 */           Speed.setSpeed(2.3D);
/*     */ 
/*     */ 
/*     */           
/* 163 */           this.counter++;
/*     */         }
/*     */         else
/*     */         {
/* 167 */           this.mc.timer.timerSpeed = 1.0F;
/*     */           
/* 169 */           Speed.setSpeed(0.25D);
/*     */           
/* 171 */           toggle();
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 177 */         this.mc.thePlayer.motionX = 0.0D;
/* 178 */         this.mc.thePlayer.motionZ = 0.0D;
/*     */         
/* 180 */         this.mc.timer.timerSpeed = 1.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\Glide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */