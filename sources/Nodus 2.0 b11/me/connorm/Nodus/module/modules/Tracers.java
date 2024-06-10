/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import me.connorm.Nodus.Nodus;
/*  6:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  7:   */ import me.connorm.Nodus.module.core.Category;
/*  8:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  9:   */ import me.connorm.lib.EventTarget;
/* 10:   */ import net.minecraft.client.Minecraft;
/* 11:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 12:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 13:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 14:   */ import net.minecraft.entity.player.EntityPlayer;
/* 15:   */ import org.lwjgl.opengl.GL11;
/* 16:   */ 
/* 17:   */ public class Tracers
/* 18:   */   extends NodusModule
/* 19:   */ {
/* 20:   */   public Tracers()
/* 21:   */   {
/* 22:20 */     super("Tracers", Category.DISPLAY);
/* 23:   */   }
/* 24:   */   
/* 25:   */   @EventTarget
/* 26:   */   public void renderWorld(EventRenderWorld theEvent)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:28 */       Nodus.theNodus.getMinecraft().gameSettings.viewBobbing = false;
/* 31:29 */       GL11.glPushMatrix();
/* 32:30 */       Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 33:31 */       GL11.glEnable(3042);
/* 34:32 */       GL11.glEnable(2848);
/* 35:33 */       GL11.glDisable(2929);
/* 36:34 */       GL11.glDisable(2896);
/* 37:35 */       GL11.glDisable(3553);
/* 38:36 */       GL11.glBlendFunc(770, 771);
/* 39:37 */       GL11.glEnable(3042);
/* 40:38 */       GL11.glLineWidth(1.0F);
/* 41:39 */       for (Iterator playerIterator = Nodus.theNodus.getMinecraft().theWorld.loadedEntityList.iterator(); playerIterator.hasNext();)
/* 42:   */       {
/* 43:41 */         Object theObject = playerIterator.next();
/* 44:42 */         if ((theObject != Nodus.theNodus.getMinecraft().thePlayer) && (theObject != null)) {
/* 45:44 */           if ((theObject instanceof EntityPlayer))
/* 46:   */           {
/* 47:46 */             EntityPlayer entity = (EntityPlayer)theObject;
/* 48:47 */             double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - RenderManager.renderPosX;
/* 49:48 */             double posY = entity.lastTickPosY + 1.0D + (entity.posY - entity.lastTickPosY) - RenderManager.renderPosY;
/* 50:49 */             double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - RenderManager.renderPosZ;
/* 51:   */             
/* 52:51 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 53:52 */             GL11.glBegin(2);
/* 54:53 */             GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 55:54 */             GL11.glVertex3d(posX, posY, posZ);
/* 56:55 */             GL11.glEnd();
/* 57:   */           }
/* 58:   */         }
/* 59:   */       }
/* 60:57 */       GL11.glDisable(3042);
/* 61:58 */       GL11.glEnable(3553);
/* 62:59 */       GL11.glEnable(2929);
/* 63:60 */       GL11.glDisable(2848);
/* 64:61 */       GL11.glDisable(3042);
/* 65:62 */       GL11.glEnable(2896);
/* 66:63 */       Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(1.0D);
/* 67:64 */       GL11.glPopMatrix();
/* 68:   */     }
/* 69:   */     catch (Exception localException) {}
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Tracers
 * JD-Core Version:    0.7.0.1
 */