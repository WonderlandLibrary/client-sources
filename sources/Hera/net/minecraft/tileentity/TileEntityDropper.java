/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityDropper
/*    */   extends TileEntityDispenser
/*    */ {
/*    */   public String getName() {
/* 10 */     return hasCustomName() ? this.customName : "container.dropper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 15 */     return "minecraft:dropper";
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\tileentity\TileEntityDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */