/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class RegistryDefaulted
/*  4:   */   extends RegistrySimple
/*  5:   */ {
/*  6:   */   private final Object defaultObject;
/*  7:   */   private static final String __OBFID = "CL_00001198";
/*  8:   */   
/*  9:   */   public RegistryDefaulted(Object par1Obj)
/* 10:   */   {
/* 11:13 */     this.defaultObject = par1Obj;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Object getObject(Object par1Obj)
/* 15:   */   {
/* 16:18 */     Object var2 = super.getObject(par1Obj);
/* 17:19 */     return var2 == null ? this.defaultObject : var2;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.RegistryDefaulted
 * JD-Core Version:    0.7.0.1
 */