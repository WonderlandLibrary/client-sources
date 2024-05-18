/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Tracers
/*    */   extends Module
/*    */ {
/*    */   public Tracers() {
/* 16 */     super("Tracers", Category.Render);
/*    */     
/* 18 */     this.settingManager.addSetting(new Setting("Distance", this, true));
/* 19 */     this.settingManager.addSetting(new Setting("LineWidth", this, 2.0D, 1.0D, 5.0D, true));
/* 20 */     this.settingManager.addSetting(new Setting("LineHeight", this, 0.2D, 0.1D, 2.0D, false));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 25 */     float lineWidth = (float)this.settingManager.getSettingByName("LineWidth").getValue();
/* 26 */     float lineHeight = (float)this.settingManager.getSettingByName("LineHeight").getValue();
/*    */     
/* 28 */     for (Entity e : this.mc.theWorld.loadedEntityList) {
/*    */       
/* 30 */       if (e instanceof EntityPlayer) {
/*    */         
/* 32 */         EntityPlayer ep = (EntityPlayer)e;
/*    */         
/* 34 */         double x = this.mc.thePlayer.posX - RenderManager.renderPosX;
/* 35 */         double y = this.mc.thePlayer.posY - RenderManager.renderPosY + lineHeight;
/* 36 */         double z = this.mc.thePlayer.posZ - RenderManager.renderPosZ;
/*    */ 
/*    */ 
/*    */         
/* 40 */         double x2 = ep.posX - RenderManager.renderPosX;
/* 41 */         double y2 = ep.posY - RenderManager.renderPosY + lineHeight;
/* 42 */         double z2 = ep.posZ - RenderManager.renderPosZ;
/*    */         
/* 44 */         Color c = Color.white;
/*    */         
/* 46 */         if (this.settingManager.getSettingByName("Distance").getBoolean()) {
/*    */           
/* 48 */           if (this.mc.thePlayer.getDistanceToEntity((Entity)ep) <= 20.0F)
/*    */           {
/* 50 */             c = Color.red;
/*    */           }
/*    */ 
/*    */           
/* 54 */           if (this.mc.thePlayer.getDistanceToEntity((Entity)ep) <= 50.0F && this.mc.thePlayer.getDistanceToEntity((Entity)ep) > 20.0F)
/*    */           {
/* 56 */             c = Color.yellow;
/*    */           }
/*    */ 
/*    */           
/* 60 */           if (this.mc.thePlayer.getDistanceToEntity((Entity)ep) > 50.0F)
/*    */           {
/* 62 */             c = Color.green;
/*    */           }
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 68 */         if (ep != this.mc.thePlayer && !ep.isInvisible() && !ep.isInvisibleToPlayer((EntityPlayer)this.mc.thePlayer))
/*    */         {
/* 70 */           Client.instance.getRenderHelper().renderLine(x, y, z, x2, y2, z2, lineWidth, c);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\Tracers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */