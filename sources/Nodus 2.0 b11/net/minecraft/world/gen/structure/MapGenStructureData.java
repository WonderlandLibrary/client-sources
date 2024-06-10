/*  1:   */ package net.minecraft.world.gen.structure;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ import net.minecraft.world.WorldSavedData;
/*  5:   */ 
/*  6:   */ public class MapGenStructureData
/*  7:   */   extends WorldSavedData
/*  8:   */ {
/*  9: 8 */   private NBTTagCompound field_143044_a = new NBTTagCompound();
/* 10:   */   private static final String __OBFID = "CL_00000510";
/* 11:   */   
/* 12:   */   public MapGenStructureData(String par1Str)
/* 13:   */   {
/* 14:13 */     super(par1Str);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 18:   */   {
/* 19:21 */     this.field_143044_a = par1NBTTagCompound.getCompoundTag("Features");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 23:   */   {
/* 24:29 */     par1NBTTagCompound.setTag("Features", this.field_143044_a);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void func_143043_a(NBTTagCompound par1NBTTagCompound, int par2, int par3)
/* 28:   */   {
/* 29:34 */     this.field_143044_a.setTag(func_143042_b(par2, par3), par1NBTTagCompound);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static String func_143042_b(int par1, int par2)
/* 33:   */   {
/* 34:39 */     return "[" + par1 + "," + par2 + "]";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public NBTTagCompound func_143041_a()
/* 38:   */   {
/* 39:44 */     return this.field_143044_a;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenStructureData
 * JD-Core Version:    0.7.0.1
 */