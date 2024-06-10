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
/* 14:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 15:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 16:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 17:   */ import net.minecraft.entity.EntityLiving;
/* 18:   */ import net.minecraft.entity.monster.EntityMob;
/* 19:   */ import net.minecraft.entity.passive.EntityAnimal;
/* 20:   */ import net.minecraft.util.AxisAlignedBB;
/* 21:   */ import net.minecraft.util.Timer;
/* 22:   */ import org.lwjgl.opengl.GL11;
/* 23:   */ 
/* 24:   */ public class MobESP
/* 25:   */   extends NodusModule
/* 26:   */ {
/* 27:   */   public MobESP()
/* 28:   */   {
/* 29:27 */     super("MobESP", Category.DISPLAY);
/* 30:   */   }
/* 31:   */   
/* 32:   */   @EventTarget
/* 33:   */   public void renderWorld(EventRenderWorld theEvent)
/* 34:   */   {
/* 35:33 */     for (Iterator mobIterator = Nodus.theNodus.getMinecraft().theWorld.loadedEntityList.iterator(); mobIterator.hasNext();)
/* 36:   */     {
/* 37:35 */       Object theObject = mobIterator.next();
/* 38:36 */       if (((theObject instanceof EntityMob)) || ((theObject instanceof EntityAnimal)))
/* 39:   */       {
/* 40:38 */         boolean isMob = theObject instanceof EntityMob;
/* 41:39 */         EntityLiving theEntity = (EntityLiving)theObject;
/* 42:40 */         double xPos = theEntity.lastTickPosX + (theEntity.posX - theEntity.lastTickPosX) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 43:41 */         double yPos = theEntity.lastTickPosY + (theEntity.posY - theEntity.lastTickPosY) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 44:42 */         double zPos = theEntity.lastTickPosZ + (theEntity.posZ - theEntity.lastTickPosZ) * Nodus.theNodus.getMinecraft().timer.renderPartialTicks;
/* 45:43 */         Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 46:44 */         Color espColor = isMob ? ColorUtils.hexToRGBA(ColorUtils.aggressiveESPColor) : ColorUtils.hexToRGBA(ColorUtils.passiveESPColor);
/* 47:45 */         drawMobESP(xPos - RenderManager.renderPosX, yPos - RenderManager.renderPosY, zPos - RenderManager.renderPosZ, theEntity, theEntity.height - 0.1D, theEntity.width, 0.45F, 0.45F, espColor);
/* 48:46 */         Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(1.0D);
/* 49:   */       }
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   private void drawMobESP(double xPos, double yPos, double zPos, EntityLiving theEntity, double e, double f, float alpha, float alpha2, Color rgb)
/* 54:   */   {
/* 55:53 */     GL11.glPushMatrix();
/* 56:54 */     GL11.glEnable(3042);
/* 57:55 */     Minecraft.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 58:56 */     GL11.glDisable(3553);
/* 59:57 */     GL11.glDisable(2896);
/* 60:58 */     GL11.glDisable(2929);
/* 61:59 */     GL11.glDepthMask(false);
/* 62:60 */     GL11.glLineWidth(0.8F);
/* 63:61 */     GL11.glBlendFunc(770, 771);
/* 64:62 */     GL11.glEnable(2848);
/* 65:63 */     GL11.glColor4f(rgb.getRed(), rgb.getGreen(), rgb.getRed(), alpha);
/* 66:64 */     RenderUtils.drawBoundingBox(new AxisAlignedBB(xPos - f, yPos + 0.1D, zPos - f, xPos + f, yPos + e + 0.25D, zPos + f));
/* 67:65 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha2);
/* 68:66 */     RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(xPos - f, yPos + 0.1D, zPos - f, xPos + f, yPos + e + 0.25D, zPos + f));
/* 69:67 */     GL11.glDepthMask(true);
/* 70:68 */     GL11.glEnable(2929);
/* 71:69 */     GL11.glEnable(3553);
/* 72:70 */     GL11.glEnable(2896);
/* 73:71 */     GL11.glDisable(2848);
/* 74:72 */     GL11.glDisable(3042);
/* 75:73 */     GL11.glPopMatrix();
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.MobESP
 * JD-Core Version:    0.7.0.1
 */