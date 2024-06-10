/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class RegistryNamespacedDefaultedByKey
/*  4:   */   extends RegistryNamespaced
/*  5:   */ {
/*  6:   */   private final String field_148760_d;
/*  7:   */   private Object field_148761_e;
/*  8:   */   private static final String __OBFID = "CL_00001196";
/*  9:   */   
/* 10:   */   public RegistryNamespacedDefaultedByKey(String p_i45127_1_)
/* 11:   */   {
/* 12:11 */     this.field_148760_d = p_i45127_1_;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void addObject(int p_148756_1_, String p_148756_2_, Object p_148756_3_)
/* 16:   */   {
/* 17:19 */     if (this.field_148760_d.equals(p_148756_2_)) {
/* 18:21 */       this.field_148761_e = p_148756_3_;
/* 19:   */     }
/* 20:24 */     super.addObject(p_148756_1_, p_148756_2_, p_148756_3_);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getObject(String p_148751_1_)
/* 24:   */   {
/* 25:29 */     Object var2 = super.getObject(p_148751_1_);
/* 26:30 */     return var2 == null ? this.field_148761_e : var2;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object getObjectForID(int p_148754_1_)
/* 30:   */   {
/* 31:38 */     Object var2 = super.getObjectForID(p_148754_1_);
/* 32:39 */     return var2 == null ? this.field_148761_e : var2;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Object getObject(Object par1Obj)
/* 36:   */   {
/* 37:44 */     return getObject((String)par1Obj);
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.RegistryNamespacedDefaultedByKey
 * JD-Core Version:    0.7.0.1
 */