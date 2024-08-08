/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CrystalAura
/*    */   extends Module
/*    */ {
/*    */   public CrystalAura() {
/* 14 */     super("CrystalAura", Category.COMBAT, 45);
/*    */   }
/*    */   
/*    */   public void onPreUpdate() {
/* 18 */     for (Entity e : this.mc.world.loadedEntityList) {
/* 19 */       if (e.getDistance(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ) <= 4.0D && e instanceof net.minecraft.entity.item.EntityEnderCrystal)
/* 20 */         this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, e); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\CrystalAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */