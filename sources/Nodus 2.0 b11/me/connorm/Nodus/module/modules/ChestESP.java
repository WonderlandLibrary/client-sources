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
/* 17:   */ import net.minecraft.tileentity.TileEntityChest;
/* 18:   */ import org.lwjgl.opengl.GL11;
/* 19:   */ 
/* 20:   */ public class ChestESP
/* 21:   */   extends NodusModule
/* 22:   */ {
/* 23:21 */   public boolean tracersEnabled = false;
/* 24:   */   
/* 25:   */   public ChestESP()
/* 26:   */   {
/* 27:25 */     super("ChestESP", Category.DISPLAY);
/* 28:   */   }
/* 29:   */   
/* 30:   */   @EventTarget
/* 31:   */   public void renderWorld(EventRenderWorld theEvent)
/* 32:   */   {
/* 33:31 */     Nodus.theNodus.getMinecraft().gameSettings.viewBobbing = false;
/* 34:32 */     for (Iterator chestIterator = Nodus.theNodus.getMinecraft().theWorld.field_147482_g.iterator(); chestIterator.hasNext();)
/* 35:   */     {
/* 36:34 */       Object o = chestIterator.next();
/* 37:35 */       if ((o instanceof TileEntityChest))
/* 38:   */       {
/* 39:37 */         TileEntityChest chest = (TileEntityChest)o;
/* 40:38 */         Nodus.theNodus.getMinecraft().gameSettings.viewBobbing = false;
/* 41:39 */         drawESP(chest, chest.field_145851_c, chest.field_145848_d, chest.field_145849_e, 1.0F);
/* 42:   */       }
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void drawESP(TileEntityChest chest, double x, double y, double z, float f)
/* 47:   */   {
/* 48:46 */     if ((chest.field_145987_o != 0) || (chest.field_145983_q != 0) || (chest.field_145982_r != 0))
/* 49:   */     {
/* 50:48 */       Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(f);
/* 51:49 */       Color chestColor = ColorUtils.hexToRGBA(ColorUtils.chestESPColor);
/* 52:50 */       RenderUtils.drawESP(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, chestColor.getRed(), chestColor.getBlue(), chestColor.getGreen(), 0.449999988079071D);
/* 53:51 */       if (!this.tracersEnabled) {
/* 54:52 */         return;
/* 55:   */       }
/* 56:53 */       GL11.glPushMatrix();
/* 57:54 */       GL11.glEnable(3042);
/* 58:55 */       GL11.glEnable(2848);
/* 59:56 */       GL11.glDisable(2929);
/* 60:57 */       GL11.glDisable(2896);
/* 61:58 */       GL11.glDisable(3553);
/* 62:59 */       GL11.glBlendFunc(770, 771);
/* 63:60 */       GL11.glEnable(3042);
/* 64:61 */       GL11.glLineWidth(1.0F);
/* 65:62 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.45F);
/* 66:63 */       GL11.glBegin(2);
/* 67:64 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 68:65 */       GL11.glVertex3d(x + 0.5D - RenderManager.renderPosX, y + 0.5D - RenderManager.renderPosY, z + 0.5D - RenderManager.renderPosZ);
/* 69:66 */       GL11.glEnd();
/* 70:67 */       GL11.glDisable(3042);
/* 71:68 */       GL11.glEnable(3553);
/* 72:69 */       GL11.glEnable(2929);
/* 73:70 */       GL11.glDisable(2848);
/* 74:71 */       GL11.glDisable(3042);
/* 75:72 */       GL11.glEnable(2896);
/* 76:73 */       GL11.glPopMatrix();
/* 77:74 */       Nodus.theNodus.getMinecraft().entityRenderer.enableLightmap(f);
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.ChestESP
 * JD-Core Version:    0.7.0.1
 */