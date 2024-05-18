/*    */ package me.eagler.module.modules.player;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.utils.TimeHelper;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestModule
/*    */   extends Module
/*    */ {
/*    */   private TimeHelper time;
/*    */   private BlockPos prevPos;
/*    */   
/*    */   public TestModule() {
/* 18 */     super("TestModule", Category.Player);
/*    */ 
/*    */     
/* 21 */     this.time = new TimeHelper();
/*    */     
/* 23 */     this.prevPos = null;
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 27 */     this.prevPos = this.mc.thePlayer.getPosition();
/*    */   }
/*    */   
/*    */   public void onDisable() {}
/*    */   
/*    */   public void onUpdate() {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\TestModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */