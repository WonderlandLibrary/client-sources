/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.block.material.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Glide
/*    */   extends Module
/*    */ {
/*    */   public Glide() {
/* 13 */     super("Glide", Category.MOVEMENT, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPreUpdate() {
/* 19 */     if (this.mc.player.motionY < 0.0D && this.mc.player.isAirBorne && !this.mc.player.isInWater() && !this.mc.player.isOnLadder() && 
/* 20 */       !this.mc.player.isInsideOfMaterial(Material.LAVA)) {
/* 21 */       this.mc.player.motionY = -0.125D;
/* 22 */       this.mc.player.jumpMovementFactor *= 1.12337F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Glide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */