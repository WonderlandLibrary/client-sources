/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import org.darkstorm.minecraft.gui.component.AbstractComponent;
/*  4:   */ import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
/*  5:   */ import org.darkstorm.minecraft.gui.component.ProgressBar;
/*  6:   */ 
/*  7:   */ public class BasicProgressBar
/*  8:   */   extends AbstractComponent
/*  9:   */   implements ProgressBar
/* 10:   */ {
/* 11:   */   private double value;
/* 12:   */   private double minimum;
/* 13:   */   private double maximum;
/* 14:   */   private double increment;
/* 15:   */   private BoundedRangeComponent.ValueDisplay display;
/* 16:   */   private boolean indeterminate;
/* 17:   */   
/* 18:   */   public BasicProgressBar()
/* 19:   */   {
/* 20:11 */     this(0.0D);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public BasicProgressBar(double value)
/* 24:   */   {
/* 25:15 */     this(value, 0.0D, 100.0D);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public BasicProgressBar(double value, double minimum, double maximum)
/* 29:   */   {
/* 30:19 */     this(value, minimum, maximum, 1);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public BasicProgressBar(double value, double minimum, double maximum, int increment)
/* 34:   */   {
/* 35:23 */     this(value, minimum, maximum, increment, BoundedRangeComponent.ValueDisplay.NONE);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public BasicProgressBar(double value, double minimum, double maximum, double increment, BoundedRangeComponent.ValueDisplay display)
/* 39:   */   {
/* 40:27 */     this.minimum = Math.max(0.0D, Math.min(minimum, maximum));
/* 41:28 */     this.maximum = Math.max(0.0D, Math.max(minimum, maximum));
/* 42:29 */     value = Math.max(minimum, Math.min(maximum, value));
/* 43:30 */     this.value = (value - Math.round(value % increment / increment) * increment);
/* 44:31 */     this.increment = Math.min(maximum, Math.max(0.0005D, increment));
/* 45:32 */     this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.NONE);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public double getValue()
/* 49:   */   {
/* 50:37 */     return this.value;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public double getMinimumValue()
/* 54:   */   {
/* 55:42 */     return this.minimum;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public double getMaximumValue()
/* 59:   */   {
/* 60:47 */     return this.maximum;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public double getIncrement()
/* 64:   */   {
/* 65:51 */     return this.increment;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public BoundedRangeComponent.ValueDisplay getValueDisplay()
/* 69:   */   {
/* 70:56 */     return this.display;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public boolean isIndeterminate()
/* 74:   */   {
/* 75:61 */     return this.indeterminate;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void setValue(double value)
/* 79:   */   {
/* 80:66 */     value = Math.max(this.minimum, Math.min(this.maximum, value));
/* 81:67 */     this.value = (value - Math.round(value % this.increment / this.increment) * this.increment);
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void setMinimumValue(double minimum)
/* 85:   */   {
/* 86:72 */     this.minimum = Math.max(0.0D, Math.min(this.maximum, minimum));
/* 87:73 */     setValue(this.value);
/* 88:   */   }
/* 89:   */   
/* 90:   */   public void setMaximumValue(double maximum)
/* 91:   */   {
/* 92:78 */     this.maximum = Math.max(maximum, this.minimum);
/* 93:79 */     setValue(this.value);
/* 94:   */   }
/* 95:   */   
/* 96:   */   public void setIncrement(double increment)
/* 97:   */   {
/* 98:83 */     this.increment = Math.min(this.maximum, Math.max(0.0005D, increment));
/* 99:84 */     setValue(this.value);
/* :0:   */   }
/* :1:   */   
/* :2:   */   public void setValueDisplay(BoundedRangeComponent.ValueDisplay display)
/* :3:   */   {
/* :4:88 */     this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.NONE);
/* :5:   */   }
/* :6:   */   
/* :7:   */   public void setIndeterminate(boolean indeterminate)
/* :8:   */   {
/* :9:93 */     this.indeterminate = indeterminate;
/* ;0:   */   }
/* ;1:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicProgressBar
 * JD-Core Version:    0.7.0.1
 */