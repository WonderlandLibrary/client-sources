/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  9:   */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/* 10:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 11:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 12:   */ import net.minecraft.util.Session;
/* 13:   */ 
/* 14:   */ public class Freecam
/* 15:   */   extends NodusModule
/* 16:   */ {
/* 17:12 */   private EntityOtherPlayerMP freecamEntity = null;
/* 18:   */   private double freecamX;
/* 19:   */   private double freecamY;
/* 20:   */   private double freecamZ;
/* 21:   */   private double freecamPitch;
/* 22:   */   private double freecamYaw;
/* 23:   */   
/* 24:   */   public Freecam()
/* 25:   */   {
/* 26:21 */     super("Freecam", Category.PLAYER);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void onEnable()
/* 30:   */   {
/* 31:26 */     if (Nodus.theNodus.getMinecraft().theWorld == null) {
/* 32:27 */       return;
/* 33:   */     }
/* 34:28 */     this.freecamX = Nodus.theNodus.getMinecraft().thePlayer.posX;
/* 35:29 */     this.freecamY = Nodus.theNodus.getMinecraft().thePlayer.posY;
/* 36:30 */     this.freecamZ = Nodus.theNodus.getMinecraft().thePlayer.posZ;
/* 37:31 */     this.freecamPitch = Nodus.theNodus.getMinecraft().thePlayer.rotationPitch;
/* 38:32 */     this.freecamYaw = Nodus.theNodus.getMinecraft().thePlayer.rotationYaw;
/* 39:33 */     this.freecamEntity = new EntityOtherPlayerMP(Nodus.theNodus.getMinecraft().theWorld, new GameProfile("", Nodus.theNodus.getMinecraft().session.getUsername()));
/* 40:34 */     this.freecamEntity.setPositionAndRotation(Nodus.theNodus.getMinecraft().thePlayer.posX, Nodus.theNodus.getMinecraft().thePlayer.posY - Nodus.theNodus.getMinecraft().thePlayer.height + 0.17D, Nodus.theNodus.getMinecraft().thePlayer.posZ, Nodus.theNodus.getMinecraft().thePlayer.rotationYaw, Nodus.theNodus.getMinecraft().thePlayer.rotationPitch);
/* 41:35 */     this.freecamEntity.inventory.copyInventory(Nodus.theNodus.getMinecraft().thePlayer.inventory);
/* 42:36 */     Nodus.theNodus.getMinecraft().theWorld.addEntityToWorld(100, this.freecamEntity);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void onDisable()
/* 46:   */   {
/* 47:41 */     Nodus.theNodus.getMinecraft().theWorld.removeEntityFromWorld(100);
/* 48:42 */     Nodus.theNodus.getMinecraft().thePlayer.setPositionAndRotation(this.freecamX, this.freecamY, this.freecamZ, (float)this.freecamYaw, (float)this.freecamPitch);
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Freecam
 * JD-Core Version:    0.7.0.1
 */