/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ 
/*  5:   */ public class TileEntityComparator
/*  6:   */   extends TileEntity
/*  7:   */ {
/*  8:   */   private int field_145997_a;
/*  9:   */   private static final String __OBFID = "CL_00000349";
/* 10:   */   
/* 11:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 12:   */   {
/* 13:12 */     super.writeToNBT(p_145841_1_);
/* 14:13 */     p_145841_1_.setInteger("OutputSignal", this.field_145997_a);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 18:   */   {
/* 19:18 */     super.readFromNBT(p_145839_1_);
/* 20:19 */     this.field_145997_a = p_145839_1_.getInteger("OutputSignal");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int func_145996_a()
/* 24:   */   {
/* 25:24 */     return this.field_145997_a;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void func_145995_a(int p_145995_1_)
/* 29:   */   {
/* 30:29 */     this.field_145997_a = p_145995_1_;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityComparator
 * JD-Core Version:    0.7.0.1
 */