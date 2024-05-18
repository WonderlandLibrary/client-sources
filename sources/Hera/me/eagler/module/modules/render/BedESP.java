/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.module.modules.player.BedDestroyer;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class BedESP
/*    */   extends Module
/*    */ {
/*    */   public BedESP() {
/* 16 */     super("BedESP", Category.Render);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 21 */     if (Client.instance.getModuleManager().getModuleByName("BedDestroyer").isEnabled())
/*    */     {
/* 23 */       if (BedDestroyer.pos != null) {
/*    */         
/* 25 */         BlockPos blockPos = BedDestroyer.pos;
/*    */         
/* 27 */         double x = blockPos.getX() - RenderManager.renderPosX;
/* 28 */         double y = blockPos.getY() - RenderManager.renderPosY;
/* 29 */         double z = blockPos.getZ() - RenderManager.renderPosZ;
/*    */         
/* 31 */         int id = Block.getIdFromBlock(this.mc.theWorld.getBlockState(blockPos).getBlock());
/*    */         
/* 33 */         if (id == 26) {
/*    */           
/* 35 */           Color c = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 30);
/*    */           
/* 37 */           Client.instance.getRenderHelper().renderBoxWithOutline(x, y - 0.75D, z + 0.5D, 1.0F, 0.6F, c);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\BedESP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */