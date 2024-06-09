/*     */ package me.eagler.module.modules.movement;
/*     */ 
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvMove
/*     */   extends Module
/*     */ {
/*     */   public InvMove() {
/*  18 */     super("InvMove", Category.Movement);
/*     */     
/*  20 */     this.settingManager.addSetting(new Setting("Rotate", this, true));
/*  21 */     this.settingManager.addSetting(new Setting("RYSpeed", this, 3.0D, 1.0D, 10.0D, true));
/*  22 */     this.settingManager.addSetting(new Setting("RPSpeed", this, 3.0D, 1.0D, 10.0D, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*     */     try {
/*  29 */       KeyBinding[] keys = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, 
/*  30 */           this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindSprint, 
/*  31 */           this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindJump };
/*     */       
/*  33 */       if (this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer || 
/*  34 */         this.mc.currentScreen instanceof net.minecraft.client.gui.Gui)
/*     */       {
/*  36 */         if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat || 
/*  37 */           this.mc.currentScreen instanceof me.eagler.gui.GuiChangeName || this.mc.currentScreen instanceof me.eagler.gui.GuiFriends || 
/*  38 */           this.mc.currentScreen instanceof me.eagler.gui.GuiAddFriend) {
/*     */           return;
/*     */         }
/*  41 */         int l = keys.length;
/*     */         
/*  43 */         for (int i = 0; i < l - 1; i++)
/*     */         {
/*  45 */           KeyBinding bind = keys[i];
/*  46 */           bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  52 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender() {
/*  59 */     boolean rotate = this.settingManager.getSettingByName("Rotate").getBoolean();
/*     */     
/*  61 */     double yawSpeed = this.settingManager.getSettingByName("RYSpeed").getValue();
/*     */     
/*  63 */     double pitchSpeed = this.settingManager.getSettingByName("RPSpeed").getValue();
/*     */     
/*  65 */     if (rotate) {
/*     */       
/*  67 */       setExtraTag(yawSpeed);
/*     */       
/*  69 */       if (this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer || 
/*  70 */         this.mc.currentScreen instanceof net.minecraft.client.gui.Gui) {
/*     */         
/*  72 */         if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat || 
/*  73 */           this.mc.currentScreen instanceof me.eagler.gui.GuiChangeName || this.mc.currentScreen instanceof me.eagler.gui.GuiFriends || 
/*  74 */           this.mc.currentScreen instanceof me.eagler.gui.GuiAddFriend) {
/*     */           return;
/*     */         }
/*  77 */         if (Keyboard.isKeyDown(200)) {
/*     */           
/*  79 */           if (this.mc.thePlayer.rotationPitch > -90.0D + pitchSpeed)
/*     */           {
/*  81 */             this.mc.thePlayer.rotationPitch = (float)(this.mc.thePlayer.rotationPitch - pitchSpeed);
/*     */           }
/*     */           else
/*     */           {
/*  85 */             this.mc.thePlayer.rotationPitch = -90.0F;
/*     */           }
/*     */         
/*     */         }
/*  89 */         else if (Keyboard.isKeyDown(208)) {
/*     */           
/*  91 */           if (this.mc.thePlayer.rotationPitch < 90.0D - pitchSpeed)
/*     */           {
/*  93 */             this.mc.thePlayer.rotationPitch = (float)(this.mc.thePlayer.rotationPitch + pitchSpeed);
/*     */           }
/*     */           else
/*     */           {
/*  97 */             this.mc.thePlayer.rotationPitch = 90.0F;
/*     */           }
/*     */         
/*     */         }
/* 101 */         else if (Keyboard.isKeyDown(205)) {
/*     */           
/* 103 */           this.mc.thePlayer.rotationYaw = (float)(this.mc.thePlayer.rotationYaw + yawSpeed);
/*     */         }
/* 105 */         else if (Keyboard.isKeyDown(203)) {
/*     */           
/* 107 */           this.mc.thePlayer.rotationYaw = (float)(this.mc.thePlayer.rotationYaw - yawSpeed);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\InvMove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */