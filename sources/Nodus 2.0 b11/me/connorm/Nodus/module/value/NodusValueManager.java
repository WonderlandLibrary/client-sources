/*  1:   */ package me.connorm.Nodus.module.value;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class NodusValueManager
/*  6:   */ {
/*  7: 7 */   private ArrayList<NodusValue> theValues = new ArrayList();
/*  8:   */   public NodusValue forceFieldAPS;
/*  9:   */   public NodusValue forceFieldRange;
/* 10:   */   
/* 11:   */   public NodusValueManager()
/* 12:   */   {
/* 13:14 */     this.forceFieldAPS = new NodusValue("ForceField APS", 10.0F, 1, 20);
/* 14:15 */     this.forceFieldAPS = new NodusValue("ForceField Range", 4.0F, 1, 6);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public NodusValue[] getValues()
/* 18:   */   {
/* 19:20 */     return (NodusValue[])this.theValues.toArray(new NodusValue[this.theValues.size()]);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.value.NodusValueManager
 * JD-Core Version:    0.7.0.1
 */