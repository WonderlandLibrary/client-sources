/*  1:   */ package net.minecraft.entity.ai.attributes;
/*  2:   */ 
/*  3:   */ public abstract class BaseAttribute
/*  4:   */   implements IAttribute
/*  5:   */ {
/*  6:   */   private final String unlocalizedName;
/*  7:   */   private final double defaultValue;
/*  8:   */   private boolean shouldWatch;
/*  9:   */   private static final String __OBFID = "CL_00001565";
/* 10:   */   
/* 11:   */   protected BaseAttribute(String par1Str, double par2)
/* 12:   */   {
/* 13:12 */     this.unlocalizedName = par1Str;
/* 14:13 */     this.defaultValue = par2;
/* 15:15 */     if (par1Str == null) {
/* 16:17 */       throw new IllegalArgumentException("Name cannot be null!");
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getAttributeUnlocalizedName()
/* 21:   */   {
/* 22:23 */     return this.unlocalizedName;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public double getDefaultValue()
/* 26:   */   {
/* 27:28 */     return this.defaultValue;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean getShouldWatch()
/* 31:   */   {
/* 32:33 */     return this.shouldWatch;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public BaseAttribute setShouldWatch(boolean par1)
/* 36:   */   {
/* 37:38 */     this.shouldWatch = par1;
/* 38:39 */     return this;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int hashCode()
/* 42:   */   {
/* 43:44 */     return this.unlocalizedName.hashCode();
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.BaseAttribute
 * JD-Core Version:    0.7.0.1
 */