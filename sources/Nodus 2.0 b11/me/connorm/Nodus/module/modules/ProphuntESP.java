/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.Nodus.utils.RenderUtils;
/*  8:   */ import me.connorm.lib.EventTarget;
/*  9:   */ import net.minecraft.client.Minecraft;
/* 10:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 11:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 12:   */ import net.minecraft.entity.item.EntityFallingBlock;
/* 13:   */ import net.minecraft.util.Timer;
/* 14:   */ 
/* 15:   */ public class ProphuntESP
/* 16:   */   extends NodusModule
/* 17:   */ {
/* 18:   */   public ProphuntESP()
/* 19:   */   {
/* 20:17 */     super("ProphuntESP", Category.DISPLAY);
/* 21:   */   }
/* 22:   */   
/* 23:   */   @EventTarget
/* 24:   */   public void renderWorld(EventRenderWorld theEvent)
/* 25:   */   {
/* 26:23 */     for (Object theObject : Nodus.theNodus.getMinecraft().theWorld.loadedEntityList) {
/* 27:25 */       if ((theObject instanceof EntityFallingBlock))
/* 28:   */       {
/* 29:27 */         EntityFallingBlock theEntity = (EntityFallingBlock)theObject;
/* 30:28 */         double xPos = theEntity.lastTickPosX + (theEntity.posX - theEntity.lastTickPosX) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 31:29 */         double yPos = theEntity.lastTickPosY + (theEntity.posY - theEntity.lastTickPosY) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 32:30 */         double zPos = theEntity.lastTickPosZ + (theEntity.posZ - theEntity.lastTickPosZ) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 33:31 */         RenderUtils.drawESP(xPos - 0.5D - RenderManager.renderPosX, yPos - 0.5D - RenderManager.renderPosY, zPos - 0.5D - RenderManager.renderPosZ, 0.0D, 0.0D, 0.0D, 0.3D);
/* 34:   */       }
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.ProphuntESP
 * JD-Core Version:    0.7.0.1
 */