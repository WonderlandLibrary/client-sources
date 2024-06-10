/*  1:   */ package net.minecraft.nbt;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ 
/*  7:   */ public class NBTTagEnd
/*  8:   */   extends NBTBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00001219";
/* 11:   */   
/* 12:   */   void load(DataInput par1DataInput, int par2)
/* 13:   */     throws IOException
/* 14:   */   {}
/* 15:   */   
/* 16:   */   void write(DataOutput par1DataOutput)
/* 17:   */     throws IOException
/* 18:   */   {}
/* 19:   */   
/* 20:   */   public byte getId()
/* 21:   */   {
/* 22:26 */     return 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:31 */     return "END";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public NBTBase copy()
/* 31:   */   {
/* 32:39 */     return new NBTTagEnd();
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagEnd
 * JD-Core Version:    0.7.0.1
 */