package org.darkstorm.minecraft.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.modulebase.Values.FlyMode;
import me.hexxed.mercury.modulebase.Values.SwiftMode;
import me.hexxed.mercury.util.Util;
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
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.ComboBoxListener;
import org.darkstorm.minecraft.gui.listener.SliderListener;
import org.darkstorm.minecraft.gui.theme.ComponentUI;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.lwjgl.input.Keyboard;


























public final class MercuryGuiManager
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
  

  public MercuryGuiManager()
  {
    setup = new AtomicBoolean();
  }
  
  public void setup()
  {
    if (!setup.compareAndSet(false, true)) {
      return;
    }
    
    createValuesFrame();
    


    Map<ModuleCategory, ModuleFrame> categoryFrames = new HashMap();
    for (Module module : ModuleManager.moduleList) {
      if (!module.isCategory(ModuleCategory.NONE)) {
        ModuleFrame frame = (ModuleFrame)categoryFrames.get(module.getCategory());
        if (frame == null) {
          String name = module.getCategory().name().toLowerCase();
          name = Character.toUpperCase(name.charAt(0)) + 
            name.substring(1);
          frame = new ModuleFrame(name, null);
          frame.setTheme(getTheme());
          frame.setLayoutManager(new GridLayoutManager(1, 0));
          frame.setVisible(true);
          frame.setClosable(false);
          frame.setMinimized(true);
          frame.setPinnable(false);
          addFrame(frame);
          categoryFrames.put(module.getCategory(), frame);
        }
        
        final Module updateModule = module;
        Button button = new BasicButton(module.isEnabled() ? "ON" : 
          "OFF")
          {
            public void update()
            {
              setText(updateModule.getModuleName() + (!Keyboard.getKeyName(updateModule.getKeybind()).equalsIgnoreCase("NONE") ? " (" + Keyboard.getKeyName(updateModule.getKeybind()) + ")" : ""));
              if (updateModule.isEnabled()) {
                setBackgroundColor(new Color(9, 92, 92, 180));
              } else {
                setBackgroundColor(new Color(Color.CYAN.darker().getRed(), Color.CYAN.darker().getGreen(), Color.CYAN.darker().getBlue(), 128));
              }
            }
          };
          button.addButtonListener(new ButtonListener()
          {
            public void onButtonPress(Button button) {
              updateModule.toggle();
            }
          });
          frame.add(button, new Constraint[] { GridLayoutManager.HorizontalGridConstraint.FILL });
        }
      }
      


      resizeComponents();
      Minecraft minecraft = Minecraft.getMinecraft();
      Dimension maxSize = recalculateSizes();
      int offsetX = 5;int offsetY = 5;
      int scale = gameSettings.guiScale;
      if (scale == 0)
        scale = 1000;
      int scaleFactor = 0;
      while ((scaleFactor < scale) && (displayWidth / (scaleFactor + 1) >= 320) && (displayHeight / (scaleFactor + 1) >= 240))
        scaleFactor++;
      for (Frame frame : getFrames()) {
        frame.setX(offsetX);
        frame.setY(offsetY);
        offsetX += width + 5;
        if (offsetX + width + 5 > displayWidth / scaleFactor) {
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
          MercuryGuiManager.this.createTestFrame();
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
    
    private void createValuesFrame() {
      Theme theme = getTheme();
      Frame valueframe = new BasicFrame("Values");
      valueframe.setTheme(theme);
      
      BasicCheckButton ch = new BasicCheckButton("Sprint in all directions");
      ch.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuesbwsprint = (!getValuesbwsprint);
        }
      });
      ch.setSelected(getValuesbwsprint);
      valueframe.add(ch, new Constraint[0]);
      

      BasicCheckButton array = new BasicCheckButton("Sort Arraylist");
      array.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuessortarraylist = (!getValuessortarraylist);
        }
      });
      array.setSelected(getValuessortarraylist);
      valueframe.add(array, new Constraint[0]);
      
      BasicCheckButton cclimb = new BasicCheckButton("Use fast Checker Climb");
      cclimb.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuesfastcclimb = (!getValuesfastcclimb);
        }
      });
      cclimb.setSelected(getValuesfastcclimb);
      valueframe.add(cclimb, new Constraint[0]);
      
      Slider stepheight = new BasicSlider("Step Height");
      stepheight.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      stepheight.setValue(getValuesStepHeight);
      stepheight.setMaximumValue(20.0D);
      stepheight.setMinimumValue(0.0D);
      stepheight.setIncrement(0.1D);
      stepheight.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesStepHeight = slider.getValue();
        }
      });
      valueframe.add(stepheight, new Constraint[0]);
      
      Slider glidespeed = new BasicSlider("Glide speed");
      glidespeed.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      glidespeed.setValue(getValuesglidespeed * 100.0D);
      glidespeed.setMaximumValue(15.0D);
      glidespeed.setMinimumValue(0.0D);
      glidespeed.setIncrement(0.1D);
      glidespeed.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesglidespeed = (slider.getValue() / 100.0D);
          if (slider.getValue() <= 0.1D) {
            getValuesglidespeed = 0.005D;
          }
        }
      });
      valueframe.add(glidespeed, new Constraint[0]);
      
      Slider horvelocity = new BasicSlider("Horizontal Velocity");
      horvelocity.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      horvelocity.setValue(getValueshorvelocity);
      horvelocity.setMaximumValue(5.0D);
      horvelocity.setMinimumValue(-5.0D);
      horvelocity.setIncrement(0.1D);
      horvelocity.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValueshorvelocity = slider.getValue();
        }
      });
      valueframe.add(horvelocity, new Constraint[0]);
      
      Slider vervelocity = new BasicSlider("Vertical Velocity");
      vervelocity.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      vervelocity.setValue(getValuesvervelocity);
      vervelocity.setMaximumValue(5.0D);
      vervelocity.setMinimumValue(0.0D);
      vervelocity.setIncrement(0.1D);
      vervelocity.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesvervelocity = Double.valueOf(slider.getValue()).doubleValue();
        }
      });
      valueframe.add(vervelocity, new Constraint[0]);
      
      Slider fly = new BasicSlider("Fly speed");
      fly.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      fly.setValue(getValuesFlySpeed);
      fly.setMaximumValue(10.0D);
      fly.setMinimumValue(1.0D);
      fly.setIncrement(0.1D);
      fly.setValue(getValuesFlySpeed);
      fly.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesFlySpeed = slider.getValue();
        }
      });
      valueframe.add(fly, new Constraint[0]);
      
      valueframe.add(new BasicLabel("Killaura settings:"), new Constraint[0]);
      BasicButton kaButton = new BasicButton("Click to open");
      kaButton.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          MercuryGuiManager.this.createKillauraFrame();
        }
      });
      valueframe.add(kaButton, new Constraint[0]);
      
      valueframe.add(new BasicLabel("Swift settings:"), new Constraint[0]);
      BasicButton swiftButton = new BasicButton("Click to open");
      swiftButton.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          MercuryGuiManager.this.createSwiftFrame();
        }
      });
      valueframe.add(swiftButton, new Constraint[0]);
      
      valueframe.add(new BasicLabel("Flight mode"), new Constraint[0]);
      ComboBox flymode = new BasicComboBox(new String[] { "Normal", "Tight", "NC+", "Glide" });
      flymode.addComboBoxListener(new ComboBoxListener()
      {
        public void onComboBoxSelectionChanged(ComboBox comboBox)
        {
          switch (comboBox.getSelectedIndex()) {
          case 0: 
            getValuesflymode = Values.FlyMode.NORMAL;
            break;
          case 1: 
            getValuesflymode = Values.FlyMode.TIGHT;
            break;
          case 2: 
            getValuesflymode = Values.FlyMode.NCP;
            break;
          case 3: 
            getValuesflymode = Values.FlyMode.GLIDE;
            break;
          
          }
          
        }
      });
      valueframe.add(flymode, new Constraint[0]);
      
      valueframe.setX(50);
      valueframe.setY(50);
      Dimension defaultDimension = theme.getUIForComponent(valueframe).getDefaultSize(valueframe);
      valueframe.setWidth(width);
      valueframe.setHeight(height);
      valueframe.layoutChildren();
      valueframe.setVisible(true);
      valueframe.setMinimized(true);
      valueframe.setClosable(false);
      valueframe.setPinnable(false);
      addFrame(valueframe);
    }
    
    private void createKillauraFrame() {
      Theme theme = getTheme();
      Frame kaFrame = new BasicFrame("Killaura");
      kaFrame.setTheme(theme);
      
      BasicCheckButton invisibles = new BasicCheckButton("Attack invisibles");
      invisibles.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuesinvisibles = (!getValuesinvisibles);
          Util.sendInfo((getValuesinvisibles ? "Now" : "No longer") + " attack invisible players.");
        }
      });
      invisibles.setSelected(getValuesinvisibles);
      kaFrame.add(invisibles, new Constraint[0]);
      
      BasicCheckButton autoblock = new BasicCheckButton("Block when attacking");
      autoblock.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuesautoblock = (!getValuesautoblock);
          Util.sendInfo((getValuesautoblock ? "Now" : "No longer") + " autoblocking for you.");
        }
      });
      autoblock.setSelected(getValuesautoblock);
      kaFrame.add(autoblock, new Constraint[0]);
      
      Slider speed = new BasicSlider("Speed (CPS)");
      speed.setValueDisplay(BoundedRangeComponent.ValueDisplay.INTEGER);
      speed.setValue(getValuescps);
      speed.setMaximumValue(15.0D);
      speed.setMinimumValue(1.0D);
      speed.setIncrement(0.1D);
      speed.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuescps = ((int)slider.getValue());
        }
      });
      kaFrame.add(speed, new Constraint[0]);
      
      Slider distance = new BasicSlider("Distance");
      distance.setValueDisplay(BoundedRangeComponent.ValueDisplay.DECIMAL);
      distance.setValue(getValuesdistance + 0.1D);
      distance.setMaximumValue(6.0D);
      distance.setMinimumValue(1.0D);
      distance.setIncrement(1.0E-5D);
      distance.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesdistance = slider.getValue();
        }
        
      });
      kaFrame.add(distance, new Constraint[0]);
      
      Slider ticks = new BasicSlider("Ticks existed");
      ticks.setValueDisplay(BoundedRangeComponent.ValueDisplay.INTEGER);
      ticks.setValue(getValuesticks);
      ticks.setMaximumValue(80.0D);
      ticks.setMinimumValue(0.0D);
      ticks.setIncrement(1.0D);
      ticks.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuesticks = ((int)slider.getValue());
        }
      });
      kaFrame.add(ticks, new Constraint[0]);
      
      kaFrame.setX(2);
      kaFrame.setY(100);
      Dimension defaultDimension = theme.getUIForComponent(kaFrame).getDefaultSize(kaFrame);
      kaFrame.setWidth(width);
      kaFrame.setHeight(height);
      kaFrame.layoutChildren();
      kaFrame.setVisible(true);
      kaFrame.setMinimized(false);
      kaFrame.setClosable(true);
      kaFrame.setPinnable(false);
      addFrame(kaFrame);
    }
    
    private void createSwiftFrame() {
      Theme theme = getTheme();
      Frame swiftFrame = new BasicFrame("Swift");
      swiftFrame.setTheme(theme);
      
      BasicCheckButton swifttimer = new BasicCheckButton("Use timer with swift");
      swifttimer.addButtonListener(new ButtonListener()
      {
        public void onButtonPress(Button button)
        {
          getValuesonlymotion = (!getValuesonlymotion);
          Util.sendInfo("Swift " + (getValuesonlymotion ? "will no longer" : "will now") + " use timer");
        }
        
      });
      swifttimer.setSelected(!getValuesonlymotion);
      swiftFrame.add(swifttimer, new Constraint[0]);
      
      Slider speed = new BasicSlider("Swift speed");
      speed.setValueDisplay(BoundedRangeComponent.ValueDisplay.INTEGER);
      speed.setValue(getValuessprintspeed);
      speed.setMaximumValue(20.0D);
      speed.setMinimumValue(1.0D);
      speed.setIncrement(0.1D);
      speed.addSliderListener(new SliderListener()
      {
        public void onSliderValueChanged(Slider slider)
        {
          getValuessprintspeed = ((float)slider.getValue());
          getValueswalkspeed = ((float)slider.getValue());
        }
      });
      swiftFrame.add(speed, new Constraint[0]);
      
      swiftFrame.add(new BasicLabel("Swift mode"), new Constraint[0]);
      ComboBox swiftmode = new BasicComboBox(new String[] { "Combat", "Sanic", "Timer" });
      swiftmode.addComboBoxListener(new ComboBoxListener()
      {
        public void onComboBoxSelectionChanged(ComboBox comboBox)
        {
          switch (comboBox.getSelectedIndex()) {
          case 0: 
            getValuesswiftmode = Values.SwiftMode.COMBAT;
            break;
          case 1: 
            getValuesswiftmode = Values.SwiftMode.SANIC;
            break;
          
          case 2: 
            getValuesswiftmode = Values.SwiftMode.TIMER;
            break;
          

          }
          
        }
      });
      int i = 0;
      switch (getValuesswiftmode) {
      case SANIC: 
        i = 0;
        break;
      case TIMER: 
        i = 1;
        break;
      
      case COMBAT: 
        i = 2;
        break;
      
      default: 
        return;
      }
      swiftmode.setSelectedIndex(i);
      swiftFrame.add(swiftmode, new Constraint[0]);
      
      swiftFrame.setX(2);
      swiftFrame.setY(150);
      Dimension defaultDimension = theme.getUIForComponent(swiftFrame).getDefaultSize(swiftFrame);
      swiftFrame.setWidth(width);
      swiftFrame.setHeight(height);
      swiftFrame.layoutChildren();
      swiftFrame.setVisible(true);
      swiftFrame.setMinimized(false);
      swiftFrame.setClosable(true);
      swiftFrame.setPinnable(false);
      addFrame(swiftFrame);
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
