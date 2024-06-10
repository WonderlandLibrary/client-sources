/*   1:    */ package org.darkstorm.minecraft.gui.component.basic;
/*   2:    */ 
/*   3:    */ import org.darkstorm.minecraft.gui.component.AbstractComponent;
/*   4:    */ import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
/*   5:    */ import org.darkstorm.minecraft.gui.component.Slider;
/*   6:    */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*   7:    */ import org.darkstorm.minecraft.gui.listener.SliderListener;
/*   8:    */ 
/*   9:    */ public class BasicSlider
/*  10:    */   extends AbstractComponent
/*  11:    */   implements Slider
/*  12:    */ {
/*  13:    */   private String text;
/*  14:    */   private String suffix;
/*  15:    */   private double value;
/*  16:    */   private double minimum;
/*  17:    */   private double maximum;
/*  18:    */   private double increment;
/*  19:    */   private BoundedRangeComponent.ValueDisplay display;
/*  20: 10 */   private boolean changing = false;
/*  21:    */   private double startValue;
/*  22:    */   
/*  23:    */   public BasicSlider()
/*  24:    */   {
/*  25: 14 */     this("");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public BasicSlider(String text)
/*  29:    */   {
/*  30: 18 */     this(text, 0.0D);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public BasicSlider(String text, double value)
/*  34:    */   {
/*  35: 22 */     this(text, value, 0.0D, 100.0D);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public BasicSlider(String text, double value, double minimum, double maximum)
/*  39:    */   {
/*  40: 26 */     this(text, value, minimum, maximum, 1);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public BasicSlider(String text, double value, double minimum, double maximum, int increment)
/*  44:    */   {
/*  45: 30 */     this(text, value, minimum, maximum, increment, BoundedRangeComponent.ValueDisplay.DECIMAL);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public BasicSlider(String text, double value, double minimum, double maximum, double increment, BoundedRangeComponent.ValueDisplay display)
/*  49:    */   {
/*  50: 34 */     this.text = (text != null ? text : "");
/*  51: 35 */     this.minimum = Math.max(0.0D, Math.min(minimum, maximum));
/*  52: 36 */     this.maximum = Math.max(0.0D, Math.max(minimum, maximum));
/*  53: 37 */     value = Math.max(minimum, Math.min(maximum, value));
/*  54: 38 */     this.value = (value - Math.round(value % increment / increment) * increment);
/*  55: 39 */     this.increment = Math.min(maximum, Math.max(0.0005D, increment));
/*  56: 40 */     this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.DECIMAL);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getText()
/*  60:    */   {
/*  61: 45 */     return this.text;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setText(String text)
/*  65:    */   {
/*  66: 50 */     this.text = (text != null ? text : "");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public double getValue()
/*  70:    */   {
/*  71: 55 */     return this.value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public double getMinimumValue()
/*  75:    */   {
/*  76: 60 */     return this.minimum;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public double getMaximumValue()
/*  80:    */   {
/*  81: 65 */     return this.maximum;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public double getIncrement()
/*  85:    */   {
/*  86: 69 */     return this.increment;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public BoundedRangeComponent.ValueDisplay getValueDisplay()
/*  90:    */   {
/*  91: 74 */     return this.display;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isValueChanging()
/*  95:    */   {
/*  96: 79 */     return this.changing;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getContentSuffix()
/* 100:    */   {
/* 101: 84 */     return this.suffix;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setValue(double value)
/* 105:    */   {
/* 106: 89 */     double oldValue = this.value;
/* 107: 90 */     value = Math.max(this.minimum, Math.min(this.maximum, value));
/* 108: 91 */     this.value = (value - Math.round(value % this.increment / this.increment) * this.increment);
/* 109: 92 */     if ((!this.changing) && (oldValue != this.value)) {
/* 110: 93 */       fireChange();
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setMinimumValue(double minimum)
/* 115:    */   {
/* 116: 98 */     this.minimum = Math.max(0.0D, Math.min(this.maximum, minimum));
/* 117: 99 */     setValue(this.value);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setMaximumValue(double maximum)
/* 121:    */   {
/* 122:104 */     this.maximum = Math.max(maximum, this.minimum);
/* 123:105 */     setValue(this.value);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIncrement(double increment)
/* 127:    */   {
/* 128:109 */     this.increment = Math.min(this.maximum, Math.max(0.0005D, increment));
/* 129:110 */     setValue(this.value);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setValueDisplay(BoundedRangeComponent.ValueDisplay display)
/* 133:    */   {
/* 134:114 */     this.display = (display != null ? display : BoundedRangeComponent.ValueDisplay.DECIMAL);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setValueChanging(boolean changing)
/* 138:    */   {
/* 139:119 */     if (changing != this.changing)
/* 140:    */     {
/* 141:120 */       this.changing = changing;
/* 142:121 */       if (changing) {
/* 143:122 */         this.startValue = this.value;
/* 144:123 */       } else if (this.startValue != this.value) {
/* 145:124 */         fireChange();
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setContentSuffix(String suffix)
/* 151:    */   {
/* 152:130 */     this.suffix = suffix;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void addSliderListener(SliderListener listener)
/* 156:    */   {
/* 157:135 */     addListener(listener);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void removeSliderListener(SliderListener listener)
/* 161:    */   {
/* 162:140 */     removeListener(listener);
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void fireChange()
/* 166:    */   {
/* 167:144 */     for (ComponentListener listener : getListeners()) {
/* 168:145 */       if ((listener instanceof SliderListener)) {
/* 169:146 */         ((SliderListener)listener).onSliderValueChanged(this);
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicSlider
 * JD-Core Version:    0.7.0.1
 */