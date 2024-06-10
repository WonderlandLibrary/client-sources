/*  1:   */ package me.connorm.Nodus.module.value;
/*  2:   */ 
/*  3:   */ public class NodusValue
/*  4:   */ {
/*  5:   */   private String valueName;
/*  6:   */   private float rawValue;
/*  7:   */   private int minimumValue;
/*  8:   */   private int maximumValue;
/*  9:   */   
/* 10:   */   public NodusValue(String valueName, float rawValue, int minimumValue, int maximumValue)
/* 11:   */   {
/* 12:12 */     this.valueName = valueName;
/* 13:13 */     this.rawValue = rawValue;
/* 14:14 */     this.minimumValue = minimumValue;
/* 15:15 */     this.maximumValue = maximumValue;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:20 */     return this.valueName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public float getValue()
/* 24:   */   {
/* 25:25 */     return this.rawValue;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getMinimumValue()
/* 29:   */   {
/* 30:30 */     return this.minimumValue;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getMaximumValue()
/* 34:   */   {
/* 35:35 */     return this.maximumValue;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setValue(float newValue)
/* 39:   */   {
/* 40:40 */     this.rawValue = newValue;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.value.NodusValue
 * JD-Core Version:    0.7.0.1
 */