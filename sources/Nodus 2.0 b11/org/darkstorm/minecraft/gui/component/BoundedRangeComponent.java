/* 1:  */ package org.darkstorm.minecraft.gui.component;
/* 2:  */ 
/* 3:  */ public abstract interface BoundedRangeComponent
/* 4:  */   extends Component
/* 5:  */ {
/* 6:  */   public abstract double getValue();
/* 7:  */   
/* 8:  */   public abstract double getMinimumValue();
/* 9:  */   
/* ::  */   public abstract double getMaximumValue();
/* ;:  */   
/* <:  */   public abstract double getIncrement();
/* =:  */   
/* >:  */   public abstract ValueDisplay getValueDisplay();
/* ?:  */   
/* @:  */   public abstract void setValue(double paramDouble);
/* A:  */   
/* B:  */   public abstract void setMinimumValue(double paramDouble);
/* C:  */   
/* D:  */   public abstract void setMaximumValue(double paramDouble);
/* E:  */   
/* F:  */   public abstract void setIncrement(double paramDouble);
/* G:  */   
/* H:  */   public abstract void setValueDisplay(ValueDisplay paramValueDisplay);
/* I:  */   
/* J:  */   public static enum ValueDisplay
/* K:  */   {
/* L:5 */     DECIMAL,  INTEGER,  PERCENTAGE,  NONE;
/* M:  */   }
/* N:  */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.BoundedRangeComponent
 * JD-Core Version:    0.7.0.1
 */