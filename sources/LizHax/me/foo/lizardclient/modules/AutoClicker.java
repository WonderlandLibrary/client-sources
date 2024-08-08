/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ public class AutoClicker
/*    */   extends Module
/*    */ {
/* 13 */   private long last = 0L;
/*    */   
/*    */   public AutoClicker() {
/* 16 */     super("AutoClicker", Category.COMBAT, 0);
/*    */   }
/*    */   
/*    */   public void onPreUpdate() {
/* 20 */     Vec3d posVec = this.mc.player.getPositionVector();
/* 21 */     RayTraceResult result = this.mc.world.rayTraceBlocks(posVec, posVec.addVector(5.0D, 0.0D, 0.0D), false, true, false);
/*    */     
/* 23 */     if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && 
/* 24 */       System.currentTimeMillis() - this.last > 50L) {
/* 25 */       this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, result.entityHit);
/* 26 */       this.last = System.currentTimeMillis();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\AutoClicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */