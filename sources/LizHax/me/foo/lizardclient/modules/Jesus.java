/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.block.material.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jesus
/*    */   extends Module
/*    */ {
/*    */   public Jesus() {
/* 13 */     super("Jesus", Category.PLAYER, 35);
/*    */   }
/*    */   
/*    */   public void onPreUpdate() {
/* 17 */     if (this.mc.player.isInWater() || this.mc.player.isInsideOfMaterial(Material.LAVA))
/* 18 */       this.mc.player.motionY = 0.10000000149011612D; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Jesus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */