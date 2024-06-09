/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChestESP
/*    */   extends Module
/*    */ {
/*    */   public ChestESP() {
/* 23 */     super("ChestESP", Category.Render);
/*    */     
/* 25 */     ArrayList<String> options = new ArrayList<String>();
/*    */     
/* 27 */     options.add("Normal");
/* 28 */     options.add("Skull");
/* 29 */     options.add("Hive");
/*    */     
/* 31 */     this.settingManager.addSetting(new Setting("ChestESPMode", this, "Normal", options));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 36 */     String mode = this.settingManager.getSettingByName("ChestESPMode").getMode();
/*    */     
/* 38 */     setExtraTag(mode);
/*    */     
/* 40 */     for (Object e : this.mc.theWorld.loadedTileEntityList) {
/*    */       
/* 42 */       if (mode.equalsIgnoreCase("Normal")) {
/*    */         
/* 44 */         if (e instanceof TileEntityChest) {
/*    */           
/* 46 */           TileEntityChest chest = (TileEntityChest)e;
/*    */           
/* 48 */           double x = chest.getPos().getX() - RenderManager.renderPosX;
/* 49 */           double y = chest.getPos().getY() - RenderManager.renderPosY;
/* 50 */           double z = chest.getPos().getZ() - RenderManager.renderPosZ;
/*    */           
/* 52 */           Color c = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
/*    */           
/* 54 */           Client.instance.getRenderHelper().renderBoxWithOutline(x + 0.5D, y - 0.5D, z + 0.5D, 1.0F, 1.0F, c);
/*    */         } 
/*    */         continue;
/*    */       } 
/* 58 */       if (mode.equalsIgnoreCase("Skull"))
/*    */       {
/* 60 */         if (e instanceof TileEntitySkull) {
/*    */ 
/*    */ 
/*    */           
/* 64 */           TileEntitySkull skull = (TileEntitySkull)e;
/*    */           
/* 66 */           double x = skull.getPos().getX() - RenderManager.renderPosX;
/* 67 */           double y = skull.getPos().getY() - RenderManager.renderPosY;
/* 68 */           double z = skull.getPos().getZ() - RenderManager.renderPosZ;
/*    */           
/* 70 */           Color color = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
/*    */         } 
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     if (mode.equalsIgnoreCase("Hive"))
/*    */     {
/* 82 */       for (Entity e : this.mc.theWorld.loadedEntityList) {
/*    */         
/* 84 */         if (e instanceof EntityArmorStand) {
/*    */           
/* 86 */           EntityArmorStand armorStand = (EntityArmorStand)e;
/*    */           
/* 88 */           double x = armorStand.getPosition().getX() - RenderManager.renderPosX;
/* 89 */           double y = armorStand.getPosition().getY() - RenderManager.renderPosY;
/* 90 */           double z = armorStand.getPosition().getZ() - RenderManager.renderPosZ;
/*    */           
/* 92 */           Color c = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 30);
/*    */           
/* 94 */           Client.instance.getRenderHelper().renderBoxWithOutline(x + 0.5249999761581421D, y + 0.30000001192092896D, z + 0.5249999761581421D, 0.6F, 0.6F, c);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\ChestESP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */