/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  6:   */ import me.connorm.Nodus.module.core.Category;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import me.connorm.Nodus.module.modules.utils.SearchUtils;
/*  9:   */ import me.connorm.Nodus.utils.RenderUtils;
/* 10:   */ import me.connorm.lib.EventTarget;
/* 11:   */ import net.minecraft.block.Block;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 14:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 15:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 16:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 17:   */ 
/* 18:   */ public class Search
/* 19:   */   extends NodusModule
/* 20:   */ {
/* 21:20 */   public ArrayList<String> searchBlocks = new ArrayList();
/* 22:22 */   public int searchRadius = SearchUtils.searchRange;
/* 23:   */   
/* 24:   */   public Search()
/* 25:   */   {
/* 26:26 */     super("Search", Category.DISPLAY);
/* 27:   */   }
/* 28:   */   
/* 29:   */   @EventTarget
/* 30:   */   public void renderWorld(EventRenderWorld theEvent)
/* 31:   */   {
/* 32:32 */     for (int yRadius = this.searchRadius; yRadius >= -this.searchRadius; yRadius--) {
/* 33:34 */       for (int zRadius = -this.searchRadius; zRadius <= this.searchRadius; zRadius++) {
/* 34:36 */         for (int xRadius = -this.searchRadius; xRadius <= this.searchRadius; xRadius++)
/* 35:   */         {
/* 36:38 */           int xPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posX + xRadius);
/* 37:39 */           int yPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posY + yRadius);
/* 38:40 */           int zPos = (int)Math.round(Nodus.theNodus.getMinecraft().thePlayer.posZ + zRadius);
/* 39:   */           
/* 40:42 */           Block currentBlock = Nodus.theNodus.getMinecraft().theWorld.getBlock(xPos, yPos, zPos);
/* 41:44 */           if (isSearchBlock(currentBlock.getLocalizedName()))
/* 42:   */           {
/* 43:47 */             Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 44:48 */             RenderUtils.drawNukerESP(xPos - RenderManager.renderPosX, yPos - RenderManager.renderPosY, zPos - RenderManager.renderPosZ, 0.6D, 0.6D, 0.6D, 0.3D);
/* 45:49 */             Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(1.0D);
/* 46:   */           }
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean isSearchBlock(String blockName)
/* 53:   */   {
/* 54:57 */     if (this.searchBlocks.contains(blockName)) {
/* 55:59 */       return true;
/* 56:   */     }
/* 57:61 */     return false;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Search
 * JD-Core Version:    0.7.0.1
 */