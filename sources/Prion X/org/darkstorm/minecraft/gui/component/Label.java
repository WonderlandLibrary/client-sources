package org.darkstorm.minecraft.gui.component;

public abstract interface Label extends TextComponent { public abstract TextAlignment getHorizontalAlignment();
  
  public static enum TextAlignment { CENTER, 
    LEFT, 
    RIGHT, 
    TOP, 
    BOTTOM;
  }
  
  public abstract TextAlignment getVerticalAlignment();
  
  public abstract void setHorizontalAlignment(TextAlignment paramTextAlignment);
  
  public abstract void setVerticalAlignment(TextAlignment paramTextAlignment);
}
