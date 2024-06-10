/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import me.connorm.Nodus.Nodus;
/*  7:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  8:   */ import me.connorm.Nodus.module.core.Category;
/*  9:   */ import me.connorm.Nodus.module.core.NodusModule;
/* 10:   */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/* 11:   */ import me.connorm.Nodus.utils.RenderUtils;
/* 12:   */ import me.connorm.lib.EventTarget;
/* 13:   */ import net.minecraft.client.Minecraft;
/* 14:   */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/* 15:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 16:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 17:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 18:   */ import net.minecraft.util.AxisAlignedBB;
/* 19:   */ import net.minecraft.util.Timer;
/* 20:   */ import org.lwjgl.opengl.GL11;
/* 21:   */ 
/* 22:   */ public class PlayerESP
/* 23:   */   extends NodusModule
/* 24:   */ {
/* 25:   */   public PlayerESP()
/* 26:   */   {
/* 27:24 */     super("PlayerESP", Category.DISPLAY);
/* 28:   */   }
/* 29:   */   
/* 30:   */   @EventTarget
/* 31:   */   public void renderWorld(EventRenderWorld theEvent)
/* 32:   */   {
/* 33:30 */     for (Iterator playerIterator = Nodus.theNodus.getMinecraft().theWorld.loadedEntityList.iterator(); playerIterator.hasNext();)
/* 34:   */     {
/* 35:32 */       Object theObject = playerIterator.next();
/* 36:33 */       if ((theObject instanceof EntityOtherPlayerMP))
/* 37:   */       {
/* 38:35 */         EntityOtherPlayerMP thePlayer = (EntityOtherPlayerMP)theObject;
/* 39:36 */         double xPos = thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 40:37 */         double yPos = thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 41:38 */         double zPos = thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 42:39 */         Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 43:40 */         Color espColor = ColorUtils.hexToRGBA(ColorUtils.playerESPColor);
/* 44:41 */         drawPlayerESP(xPos - RenderManager.renderPosX, yPos - RenderManager.renderPosY, zPos - RenderManager.renderPosZ, thePlayer, thePlayer.height - 0.1D, thePlayer.width - 0.12D, 0.45F, 0.45F, espColor);
/* 45:42 */         Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(1.0D);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   private void drawPlayerESP(double xPos, double yPos, double zPos, EntityOtherPlayerMP thePlayer, double e, double f, float alpha, float alpha2, Color rgb)
/* 51:   */   {
/* 52:49 */     GL11.glPushMatrix();
/* 53:50 */     GL11.glEnable(3042);
/* 54:51 */     Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 55:52 */     GL11.glColor4f(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), alpha);
/* 56:53 */     GL11.glDisable(3553);
/* 57:54 */     GL11.glDisable(2896);
/* 58:55 */     GL11.glDisable(2929);
/* 59:56 */     GL11.glDepthMask(false);
/* 60:57 */     GL11.glLineWidth(1.0F);
/* 61:58 */     GL11.glBlendFunc(770, 771);
/* 62:59 */     GL11.glEnable(2848);
/* 63:60 */     RenderUtils.drawBoundingBox(new AxisAlignedBB(xPos - f, yPos + 0.1D, zPos - f, xPos + f, yPos + e + 0.25D, zPos + f));
/* 64:61 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha2);
/* 65:62 */     RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(xPos - f, yPos + 0.1D, zPos - f, xPos + f, yPos + e + 0.25D, zPos + f));
/* 66:63 */     GL11.glDepthMask(true);
/* 67:64 */     GL11.glEnable(2929);
/* 68:65 */     GL11.glEnable(3553);
/* 69:66 */     GL11.glEnable(2896);
/* 70:67 */     GL11.glDisable(2848);
/* 71:68 */     GL11.glDisable(3042);
/* 72:69 */     GL11.glPopMatrix();
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.PlayerESP
 * JD-Core Version:    0.7.0.1
 */