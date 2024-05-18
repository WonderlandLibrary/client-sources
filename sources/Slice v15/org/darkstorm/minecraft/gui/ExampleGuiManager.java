package org.darkstorm.minecraft.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.ComboBox;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
import org.darkstorm.minecraft.gui.component.basic.BasicComboBox;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.component.basic.BasicProgressBar;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.ComboBoxListener;
import org.darkstorm.minecraft.gui.theme.ComponentUI;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;


















public final class ExampleGuiManager
  extends AbstractGuiManager
{
  private final AtomicBoolean setup;
  
  private class ModuleFrame
    extends BasicFrame
  {
    private ModuleFrame() {}
    
    private ModuleFrame(String title)
    {
      super();
    }
  }
  

  public ExampleGuiManager()
  {
    setup = new AtomicBoolean();
  }
  
  public void setup()
  {
    if (!setup.compareAndSet(false, true)) {
      return;
    }
    createTestFrame();
    










































    resizeComponents();
    Minecraft minecraft = Minecraft.getMinecraft();
    Dimension maxSize = recalculateSizes();
    int offsetX = 5;int offsetY = 5;
    int scale = gameSettingsguiScale;
    if (scale == 0)
      scale = 1000;
    int scaleFactor = 0;
    while ((scaleFactor < scale) && (Minecraft.displayWidth / (scaleFactor + 1) >= 320) && (Minecraft.displayHeight / (scaleFactor + 1) >= 240))
      scaleFactor++;
    for (Frame frame : getFrames()) {
      frame.setX(offsetX);
      frame.setY(offsetY);
      offsetX += width + 5;
      if (offsetX + width + 5 > Minecraft.displayWidth / scaleFactor) {
        offsetX = 5;
        offsetY += height + 5;
      }
    }
  }
  
  private void createTestFrame() {
    Theme theme = getTheme();
    Frame testFrame = new BasicFrame("Frame");
    testFrame.setTheme(theme);
    
    testFrame.add(new BasicLabel("TEST LOL"), new Constraint[0]);
    testFrame.add(new BasicLabel("TEST 23423"), new Constraint[0]);
    testFrame.add(new BasicLabel("TE123123123ST LOL"), new Constraint[0]);
    testFrame.add(new BasicLabel("31243 LO3242L432"), new Constraint[0]);
    BasicButton testButton = new BasicButton("Duplicate this frame!");
    testButton.addButtonListener(new ButtonListener()
    {
      public void onButtonPress(Button button)
      {
        ExampleGuiManager.this.createTestFrame();
      }
    });
    testFrame.add(new BasicCheckButton("This is a checkbox"), new Constraint[0]);
    testFrame.add(testButton, new Constraint[0]);
    ComboBox comboBox = new BasicComboBox(new String[] { "Simple theme", "Other theme", "Other theme 2" });
    comboBox.addComboBoxListener(new ComboBoxListener()
    {
      public void onComboBoxSelectionChanged(ComboBox comboBox)
      {
        Theme theme;
        switch (comboBox.getSelectedIndex()) {
        case 0: 
          theme = new SimpleTheme();
          break;
        case 1: 
        case 2: 
        default: 
          return;
        }
        
        
        Theme theme;
        
        setTheme(theme);
      }
    });
    testFrame.add(comboBox, new Constraint[0]);
    Slider slider = new BasicSlider("Test");
    slider.setContentSuffix("things");
    slider.setValueDisplay(BoundedRangeComponent.ValueDisplay.INTEGER);
    testFrame.add(slider, new Constraint[0]);
    testFrame.add(new BasicProgressBar(50.0D, 0.0D, 100.0D, 1.0D, BoundedRangeComponent.ValueDisplay.PERCENTAGE), new Constraint[0]);
    
    testFrame.setX(50);
    testFrame.setY(50);
    Dimension defaultDimension = theme.getUIForComponent(testFrame).getDefaultSize(testFrame);
    testFrame.setWidth(width);
    testFrame.setHeight(height);
    testFrame.layoutChildren();
    testFrame.setVisible(true);
    testFrame.setMinimized(true);
    addFrame(testFrame);
  }
  
  protected void resizeComponents()
  {
    Theme theme = getTheme();
    Frame[] frames = getFrames();
    Button enable = new BasicButton("Enable");
    Button disable = new BasicButton("Disable");
    Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
    Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
    int buttonWidth = Math.max(width, width);
    int buttonHeight = Math.max(height, height);
    for (Frame frame : frames) {
      if ((frame instanceof ModuleFrame)) {
        for (Component component : frame.getChildren()) {
          if ((component instanceof Button)) {
            component.setWidth(buttonWidth);
            component.setHeight(buttonHeight);
          }
        }
      }
    }
    recalculateSizes();
  }
  
  private Dimension recalculateSizes() {
    Frame[] frames = getFrames();
    int maxWidth = 0;int maxHeight = 0;
    for (Frame frame : frames) {
      Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
      maxWidth = Math.max(maxWidth, width);
      frame.setHeight(height);
      if (frame.isMinimized()) {
        for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame))
          maxHeight = Math.max(maxHeight, height);
      } else
        maxHeight = Math.max(maxHeight, height);
    }
    for (Frame frame : frames) {
      frame.setWidth(maxWidth);
      frame.layoutChildren();
    }
    return new Dimension(maxWidth, maxHeight);
  }
}
