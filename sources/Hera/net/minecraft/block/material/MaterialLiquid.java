/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialLiquid
/*    */   extends Material
/*    */ {
/*    */   public MaterialLiquid(MapColor color) {
/*  7 */     super(color);
/*  8 */     setReplaceable();
/*  9 */     setNoPushMobility();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLiquid() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\material\MaterialLiquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */