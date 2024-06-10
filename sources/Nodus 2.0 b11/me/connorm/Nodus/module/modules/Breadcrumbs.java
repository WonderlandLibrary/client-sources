/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import me.connorm.Nodus.Nodus;
/*  6:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  7:   */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*  8:   */ import me.connorm.Nodus.module.core.Category;
/*  9:   */ import me.connorm.Nodus.module.core.NodusModule;
/* 10:   */ import me.connorm.lib.EventTarget;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 13:   */ import net.minecraft.client.renderer.EntityRenderer;
/* 14:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 15:   */ import org.lwjgl.opengl.GL11;
/* 16:   */ 
/* 17:   */ public class Breadcrumbs
/* 18:   */   extends NodusModule
/* 19:   */ {
/* 20:20 */   public ArrayList theCrumbs = new ArrayList();
/* 21:21 */   public double lastX = 0.0D;
/* 22:22 */   public double lastY = 0.0D;
/* 23:23 */   public double lastZ = 0.0D;
/* 24:   */   
/* 25:   */   public Breadcrumbs()
/* 26:   */   {
/* 27:27 */     super("Breadcrumbs", Category.DISPLAY);
/* 28:   */   }
/* 29:   */   
/* 30:   */   @EventTarget
/* 31:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 32:   */   {
/* 33:33 */     EntityClientPlayerMP thePlayer = (EntityClientPlayerMP)theEvent.getPlayer();
/* 34:34 */     boolean xPos = thePlayer.posX != this.lastX;
/* 35:35 */     boolean yPos = thePlayer.posY - thePlayer.getEyeHeight() != this.lastY - thePlayer.getEyeHeight();
/* 36:36 */     boolean zPos = thePlayer.posZ != this.lastZ;
/* 37:   */     
/* 38:38 */     double[] var5 = 
/* 39:39 */       {
/* 40:40 */       this.lastX, this.lastY - thePlayer.getEyeHeight(), this.lastZ, thePlayer.posX, thePlayer.posY - thePlayer.getEyeHeight(), thePlayer.posZ };
/* 41:   */     
/* 42:   */ 
/* 43:43 */     this.theCrumbs.add(var5);
/* 44:   */     
/* 45:45 */     this.lastX = thePlayer.posX;
/* 46:46 */     this.lastY = thePlayer.posY;
/* 47:47 */     this.lastZ = thePlayer.posZ;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @EventTarget
/* 51:   */   public void renderWorld(EventRenderWorld theEvent)
/* 52:   */   {
/* 53:53 */     Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(0.0D);
/* 54:54 */     GL11.glPushMatrix();
/* 55:55 */     GL11.glBlendFunc(770, 771);
/* 56:56 */     GL11.glLineWidth(1.5F);
/* 57:57 */     GL11.glDisable(2896);
/* 58:58 */     GL11.glDisable(2929);
/* 59:59 */     GL11.glDisable(3553);
/* 60:60 */     GL11.glEnable(3042);
/* 61:61 */     GL11.glDepthMask(false);
/* 62:62 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 63:63 */     Iterator yPos = this.theCrumbs.iterator();
/* 64:65 */     while (yPos.hasNext())
/* 65:   */     {
/* 66:67 */       double[] zPos = (double[])yPos.next();
/* 67:68 */       double var5 = RenderManager.renderPosX - zPos[0];
/* 68:69 */       double var7 = RenderManager.renderPosY - zPos[1];
/* 69:70 */       double var9 = RenderManager.renderPosZ - zPos[2];
/* 70:71 */       double var11 = RenderManager.renderPosX - zPos[3];
/* 71:72 */       double var13 = RenderManager.renderPosY - zPos[4];
/* 72:73 */       double var15 = RenderManager.renderPosZ - zPos[5];
/* 73:74 */       GL11.glBegin(1);
/* 74:75 */       GL11.glVertex3d(-var5, -var7, -var9);
/* 75:76 */       GL11.glVertex3d(-var11, -var13, -var15);
/* 76:77 */       GL11.glEnd();
/* 77:   */     }
/* 78:80 */     GL11.glDepthMask(true);
/* 79:81 */     GL11.glEnable(2896);
/* 80:82 */     GL11.glDisable(3042);
/* 81:83 */     GL11.glEnable(3553);
/* 82:84 */     GL11.glEnable(2929);
/* 83:85 */     GL11.glPopMatrix();
/* 84:86 */     Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(0.0D);
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void onEnable()
/* 88:   */   {
/* 89:92 */     if (Nodus.theNodus.getMinecraft().thePlayer == null) {
/* 90:93 */       return;
/* 91:   */     }
/* 92:94 */     EntityClientPlayerMP thePlayer = Nodus.theNodus.getMinecraft().thePlayer;
/* 93:95 */     this.lastX = thePlayer.posX;
/* 94:96 */     this.lastY = thePlayer.posY;
/* 95:97 */     this.lastZ = thePlayer.posZ;
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Breadcrumbs
 * JD-Core Version:    0.7.0.1
 */