/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class AABBPool
/*   7:    */ {
/*   8:    */   private final int maxNumCleans;
/*   9:    */   private final int numEntriesToRemove;
/*  10: 19 */   private final List listAABB = new ArrayList();
/*  11:    */   private int nextPoolIndex;
/*  12:    */   private int maxPoolIndex;
/*  13:    */   private int numCleans;
/*  14:    */   private static final String __OBFID = "CL_00000609";
/*  15:    */   
/*  16:    */   public AABBPool(int par1, int par2)
/*  17:    */   {
/*  18: 35 */     this.maxNumCleans = par1;
/*  19: 36 */     this.numEntriesToRemove = par2;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public AxisAlignedBB getAABB(double par1, double par3, double par5, double par7, double par9, double par11)
/*  23:    */   {
/*  24:    */     AxisAlignedBB var13;
/*  25: 47 */     if (this.nextPoolIndex >= this.listAABB.size())
/*  26:    */     {
/*  27: 49 */       AxisAlignedBB var13 = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
/*  28: 50 */       this.listAABB.add(var13);
/*  29:    */     }
/*  30:    */     else
/*  31:    */     {
/*  32: 54 */       var13 = (AxisAlignedBB)this.listAABB.get(this.nextPoolIndex);
/*  33: 55 */       var13.setBounds(par1, par3, par5, par7, par9, par11);
/*  34:    */     }
/*  35: 58 */     this.nextPoolIndex += 1;
/*  36: 59 */     return var13;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void cleanPool()
/*  40:    */   {
/*  41: 68 */     if (this.nextPoolIndex > this.maxPoolIndex) {
/*  42: 70 */       this.maxPoolIndex = this.nextPoolIndex;
/*  43:    */     }
/*  44: 73 */     if (this.numCleans++ == this.maxNumCleans)
/*  45:    */     {
/*  46: 75 */       int var1 = Math.max(this.maxPoolIndex, this.listAABB.size() - this.numEntriesToRemove);
/*  47: 77 */       while (this.listAABB.size() > var1) {
/*  48: 79 */         this.listAABB.remove(var1);
/*  49:    */       }
/*  50: 82 */       this.maxPoolIndex = 0;
/*  51: 83 */       this.numCleans = 0;
/*  52:    */     }
/*  53: 86 */     this.nextPoolIndex = 0;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void clearPool()
/*  57:    */   {
/*  58: 94 */     this.nextPoolIndex = 0;
/*  59: 95 */     this.listAABB.clear();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getlistAABBsize()
/*  63:    */   {
/*  64:100 */     return this.listAABB.size();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getnextPoolIndex()
/*  68:    */   {
/*  69:105 */     return this.nextPoolIndex;
/*  70:    */   }
/*  71:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.AABBPool
 * JD-Core Version:    0.7.0.1
 */