/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
/*    */ 
/*    */ public class AutoMLG
/*    */   extends Module
/*    */ {
/* 15 */   private long last = 0L;
/*    */   
/*    */   public AutoMLG() {
/* 18 */     super("AutoMLG", Category.PLAYER, 23);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClientTick(ClientTickEvent event) {
/* 24 */     if (this.mc.player.fallDistance >= 3.0F && System.currentTimeMillis() - this.last > 100L) {
/* 25 */       Vec3d posVec = this.mc.player.getPositionVector();
/* 26 */       RayTraceResult result = this.mc.world.rayTraceBlocks(posVec, posVec.addVector(0.0D, -5.329999923706055D, 0.0D), true, true, false);
/* 27 */       if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
/* 28 */         EnumHand hand = EnumHand.MAIN_HAND;
/* 29 */         if (this.mc.player.getHeldItemOffhand().getItem() == Items.WATER_BUCKET) { hand = EnumHand.OFF_HAND; }
/* 30 */         else if (this.mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET)
/* 31 */         { for (int i = 0; i < 9; i++) {
/* 32 */             if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
/* 33 */               this.mc.player.inventory.currentItem = i;
/* 34 */               this.mc.player.rotationPitch = 90.0F;
/* 35 */               this.last = System.currentTimeMillis();
/*    */               
/*    */               return;
/*    */             } 
/*    */           } 
/*    */           return; }
/*    */         
/* 42 */         this.mc.player.rotationPitch = 90.0F;
/* 43 */         this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, hand);
/* 44 */         this.last = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\AutoMLG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */