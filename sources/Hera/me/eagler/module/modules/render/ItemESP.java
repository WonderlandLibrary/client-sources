/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ 
/*    */ public class ItemESP
/*    */   extends Module
/*    */ {
/*    */   public ItemESP() {
/* 15 */     super("ItemESP", Category.Render);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 20 */     for (Entity e : this.mc.theWorld.loadedEntityList) {
/*    */       
/* 22 */       if (e instanceof EntityItem) {
/*    */         
/* 24 */         EntityItem item = (EntityItem)e;
/*    */         
/* 26 */         double x = item.getPosition().getX() - RenderManager.renderPosX;
/* 27 */         double y = item.getPosition().getY() - RenderManager.renderPosY;
/* 28 */         double z = item.getPosition().getZ() - RenderManager.renderPosZ;
/*    */         
/* 30 */         Color c = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
/*    */         
/* 32 */         Client.instance.getRenderHelper().renderBoxWithOutline(x, y - 0.5D, z, 0.5F, 0.5F, c);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\ItemESP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */