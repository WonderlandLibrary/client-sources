/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialLogic
/*    */   extends Material
/*    */ {
/*    */   public MaterialLogic(MapColor color) {
/*  7 */     super(color);
/*  8 */     setAdventureModeExempt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 32 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\material\MaterialLogic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */