/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class PackMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final IChatComponent packDescription;
/*    */   private final int packFormat;
/*    */   
/*    */   public PackMetadataSection(IChatComponent p_i1034_1_, int p_i1034_2_) {
/* 12 */     this.packDescription = p_i1034_1_;
/* 13 */     this.packFormat = p_i1034_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getPackDescription() {
/* 18 */     return this.packDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPackFormat() {
/* 23 */     return this.packFormat;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\data\PackMetadataSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */