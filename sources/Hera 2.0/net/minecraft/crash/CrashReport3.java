/*    */ package net.minecraft.crash;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ 
/*    */ class CrashReport3
/*    */   implements Callable
/*    */ {
/*    */   final CrashReport field_71490_a;
/*    */   
/*    */   CrashReport3(CrashReport p_i1340_1_) {
/* 12 */     this.field_71490_a = p_i1340_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public String call() {
/* 17 */     return OpenGlHelper.func_183029_j();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\crash\CrashReport3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */