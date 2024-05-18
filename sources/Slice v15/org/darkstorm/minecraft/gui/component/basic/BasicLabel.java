package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.Label.TextAlignment;

public class BasicLabel extends org.darkstorm.minecraft.gui.component.AbstractComponent implements org.darkstorm.minecraft.gui.component.Label
{
  protected String text;
  protected Label.TextAlignment horizontalAlignment = Label.TextAlignment.LEFT;
  protected Label.TextAlignment verticalAlignment = Label.TextAlignment.CENTER;
  
  public BasicLabel() {}
  
  public BasicLabel(String text)
  {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  public Label.TextAlignment getHorizontalAlignment() {
    return horizontalAlignment;
  }
  
  public Label.TextAlignment getVerticalAlignment() {
    return verticalAlignment;
  }
  
  public void setHorizontalAlignment(Label.TextAlignment alignment) {
    horizontalAlignment = alignment;
  }
  
  public void setVerticalAlignment(Label.TextAlignment alignment) {
    verticalAlignment = alignment;
  }
}
