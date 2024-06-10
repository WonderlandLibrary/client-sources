/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  6:   */ import me.connorm.Nodus.module.core.Category;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import me.connorm.Nodus.utils.RenderUtils;
/*  9:   */ import me.connorm.lib.EventTarget;
/* 10:   */ import net.minecraft.block.Block;
/* 11:   */ import net.minecraft.block.material.Material;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 14:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 15:   */ import net.minecraft.client.network.NetHandlerPlayClient;
/* 16:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 17:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 18:   */ import net.minecraft.entity.player.EntityPlayer;
/* 19:   */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/* 20:   */ 
/* 21:   */ public class Nuker
/* 22:   */   extends NodusModule
/* 23:   */ {
/* 24:18 */   int xPos = -1;
/* 25:19 */   int yPos = -1;
/* 26:20 */   int zPos = -1;
/* 27:21 */   double nukerRadius = 3.0D;
/* 28:   */   
/* 29:   */   public Nuker()
/* 30:   */   {
/* 31:25 */     super("Nuker", Category.WORLD);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @EventTarget
/* 35:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 36:   */   {
/* 37:31 */     for (int yRadius = (int)this.nukerRadius; yRadius >= (int)-this.nukerRadius; yRadius--) {
/* 38:33 */       for (int zRadius = (int)-this.nukerRadius; zRadius <= this.nukerRadius; zRadius++) {
/* 39:35 */         for (int xRadius = (int)-this.nukerRadius; xRadius <= this.nukerRadius; xRadius++)
/* 40:   */         {
/* 41:37 */           this.xPos = ((int)Math.round(theEvent.getPlayer().posX + xRadius));
/* 42:38 */           this.yPos = ((int)Math.round(theEvent.getPlayer().posY + yRadius));
/* 43:39 */           this.zPos = ((int)Math.round(theEvent.getPlayer().posZ + zRadius));
/* 44:   */           
/* 45:41 */           Block block = Nodus.theNodus.getMinecraft().theWorld.getBlock(this.xPos, this.yPos, this.zPos);
/* 46:43 */           if (Nodus.theNodus.getMinecraft().theWorld.getBlockMaterial(block) != Material.air)
/* 47:   */           {
/* 48:45 */             Nodus.theNodus.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, this.xPos, this.yPos, this.zPos, 1));
/* 49:46 */             Nodus.theNodus.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, this.xPos, this.yPos, this.zPos, 1));
/* 50:   */           }
/* 51:   */         }
/* 52:   */       }
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   @EventTarget
/* 57:   */   public void renderWorld(EventRenderWorld theEvent)
/* 58:   */   {
/* 59:55 */     for (int yRadius = (int)this.nukerRadius; yRadius >= (int)-this.nukerRadius; yRadius--) {
/* 60:57 */       for (int zRadius = (int)-this.nukerRadius; zRadius <= this.nukerRadius; zRadius++) {
/* 61:59 */         for (int xRadius = (int)-this.nukerRadius; xRadius <= this.nukerRadius; xRadius++)
/* 62:   */         {
/* 63:61 */           int xPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posX + xRadius);
/* 64:62 */           int yPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posY + yRadius);
/* 65:63 */           int zPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posZ + zRadius);
/* 66:   */           
/* 67:65 */           Block block = Nodus.theNodus.getMinecraft().theWorld.getBlock(xPos, yPos, zPos);
/* 68:66 */           if (Nodus.theNodus.getMinecraft().theWorld.getBlockMaterial(block) != Material.air)
/* 69:   */           {
/* 70:68 */             Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 71:69 */             RenderUtils.drawNukerESP(xPos - RenderManager.renderPosX, yPos - RenderManager.renderPosY, zPos - RenderManager.renderPosZ, 0.6D, 0.6D, 0.6D, 0.3D);
/* 72:70 */             Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(1.0D);
/* 73:   */           }
/* 74:   */         }
/* 75:   */       }
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Nuker
 * JD-Core Version:    0.7.0.1
 */