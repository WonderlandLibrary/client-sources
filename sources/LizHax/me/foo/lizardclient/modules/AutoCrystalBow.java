/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class AutoCrystalBow
/*    */   extends Module
/*    */ {
/*    */   public AutoCrystalBow() {
/* 18 */     super("AutoCrystalBow", Category.COMBAT, 24);
/*    */   }
/*    */   
/*    */   public void onPreUpdate() {
/* 22 */     for (Entity e : this.mc.world.loadedEntityList) {
/* 23 */       if (e.getDistance(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ) <= 3.0D && e instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 24 */         if (this.mc.player.getHeldItemMainhand().getItem() != Items.BOW) {
/* 25 */           for (int i = 0; i < 9; i++) {
/* 26 */             if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.BOW) {
/* 27 */               Vec3d posVec = this.mc.player.getPositionVector();
/* 28 */               RayTraceResult result = this.mc.world.rayTraceBlocks(posVec, posVec.addVector(0.0D, -5.329999923706055D, 0.0D), true, true, false);
/*    */               
/* 30 */               if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY) {
/* 31 */                 this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
/*    */               }
/*    */             } 
/*    */           } 
/*    */           continue;
/*    */         } 
/* 37 */         this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\AutoCrystalBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */