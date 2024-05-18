/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class SetVisibility
/*    */ {
/*  9 */   private static final int COUNT_FACES = (EnumFacing.values()).length;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   private final BitSet bitSet = new BitSet(COUNT_FACES * COUNT_FACES);
/*    */ 
/*    */ 
/*    */   
/*    */   public void setManyVisible(Set<EnumFacing> p_178620_1_) {
/* 19 */     for (EnumFacing enumfacing : p_178620_1_) {
/*    */       
/* 21 */       for (EnumFacing enumfacing1 : p_178620_1_)
/*    */       {
/* 23 */         setVisible(enumfacing, enumfacing1, true);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
/* 30 */     this.bitSet.set(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
/* 31 */     this.bitSet.set(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAllVisible(boolean visible) {
/* 36 */     this.bitSet.set(0, this.bitSet.size(), visible);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 41 */     return this.bitSet.get(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder stringbuilder = new StringBuilder();
/* 47 */     stringbuilder.append(' '); byte b; int i;
/*    */     EnumFacing[] arrayOfEnumFacing;
/* 49 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*    */       
/* 51 */       stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
/*    */       b++; }
/*    */     
/* 54 */     stringbuilder.append('\n');
/*    */     
/* 56 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing2 = arrayOfEnumFacing[b];
/*    */       
/* 58 */       stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0)); byte b1; int j;
/*    */       EnumFacing[] arrayOfEnumFacing1;
/* 60 */       for (j = (arrayOfEnumFacing1 = EnumFacing.values()).length, b1 = 0; b1 < j; ) { EnumFacing enumfacing1 = arrayOfEnumFacing1[b1];
/*    */         
/* 62 */         if (enumfacing2 == enumfacing1) {
/*    */           
/* 64 */           stringbuilder.append("  ");
/*    */         }
/*    */         else {
/*    */           
/* 68 */           boolean flag = isVisible(enumfacing2, enumfacing1);
/* 69 */           stringbuilder.append(' ').append(flag ? 89 : 110);
/*    */         } 
/*    */         b1++; }
/*    */       
/* 73 */       stringbuilder.append('\n');
/*    */       b++; }
/*    */     
/* 76 */     return stringbuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\chunk\SetVisibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */