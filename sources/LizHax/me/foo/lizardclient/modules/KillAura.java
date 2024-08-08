/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KillAura
/*    */   extends Module
/*    */ {
/* 14 */   private long last = 0L;
/*    */   
/*    */   public KillAura() {
/* 17 */     super("KillAura", Category.COMBAT, 24);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPreUpdate() {
/* 23 */     super.onPreUpdate();
/* 24 */     for (Entity e : this.mc.world.loadedEntityList) {
/* 25 */       if (e.getDistance(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ) <= 5.0D && e instanceof EntityPlayer && 
/* 26 */         this.mc.player.canAttackPlayer((EntityPlayer)e) && !(e instanceof net.minecraft.client.entity.EntityPlayerSP) && 
/* 27 */         System.currentTimeMillis() - this.last > 50L) {
/* 28 */         this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, e);
/* 29 */         this.last = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\KillAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */