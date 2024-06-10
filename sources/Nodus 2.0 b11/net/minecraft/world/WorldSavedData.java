/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ 
/*  5:   */ public abstract class WorldSavedData
/*  6:   */ {
/*  7:   */   public final String mapName;
/*  8:   */   private boolean dirty;
/*  9:   */   private static final String __OBFID = "CL_00000580";
/* 10:   */   
/* 11:   */   public WorldSavedData(String par1Str)
/* 12:   */   {
/* 13:16 */     this.mapName = par1Str;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);
/* 17:   */   
/* 18:   */   public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
/* 19:   */   
/* 20:   */   public void markDirty()
/* 21:   */   {
/* 22:34 */     setDirty(true);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setDirty(boolean par1)
/* 26:   */   {
/* 27:42 */     this.dirty = par1;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean isDirty()
/* 31:   */   {
/* 32:50 */     return this.dirty;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldSavedData
 * JD-Core Version:    0.7.0.1
 */