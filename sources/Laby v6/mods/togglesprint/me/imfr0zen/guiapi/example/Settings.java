package mods.togglesprint.me.imfr0zen.guiapi.example;

import mods.togglesprint.me.imfr0zen.guiapi.components.Button;
import mods.togglesprint.me.imfr0zen.guiapi.components.GuiSlider;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ExtendListener;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ValueListener;

public class Settings
  extends ExtendListener
{
  Button button;
  public static int ered;
  public static int egreen;
  public static int eblue;
  public static int ealpha;
  public static int dred;
  public static int dgreen;
  public static int dblue;
  public static int dalpha;
  public static int ared;
  public static int agreen;
  public static int ablue;
  
  public Settings(Button modulebutton)
  {
    this.button = modulebutton;
  }
  
  public void addComponents()
  {
    if (this.button.getText().equals("Enabled Button Color"))
    {
      GuiSlider eRed = new GuiSlider("Red", 0.0F, 255.0F, ered, 0);
      eRed.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.ered = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.ered = (int)value;
        }
      });
      add(eRed);
    }
    if (this.button.getText().equals("Enabled Button Color"))
    {
      GuiSlider eGreen = new GuiSlider("Green", 0.0F, 255.0F, egreen, 0);
      eGreen.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.egreen = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.egreen = (int)value;
        }
      });
      add(eGreen);
    }
    if (this.button.getText().equals("Enabled Button Color"))
    {
      GuiSlider eBlue = new GuiSlider("Blue", 0.0F, 255.0F, eblue, 0);
      eBlue.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.eblue = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.eblue = (int)value;
        }
      });
      add(eBlue);
    }
    if (this.button.getText().equals("Enabled Button Color"))
    {
      GuiSlider eAlpha = new GuiSlider("Alpha", 0.0F, 255.0F, ealpha, 0);
      eAlpha.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.ealpha = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.ealpha = (int)value;
        }
      });
      add(eAlpha);
    }
    if (this.button.getText().equals("Disabled Button Color"))
    {
      GuiSlider dRed = new GuiSlider("Red", 0.0F, 255.0F, dred, 0);
      dRed.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.dred = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.dred = (int)value;
        }
      });
      add(dRed);
    }
    if (this.button.getText().equals("Disabled Button Color"))
    {
      GuiSlider dGreen = new GuiSlider("Green", 0.0F, 255.0F, dgreen, 0);
      dGreen.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.dgreen = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.dgreen = (int)value;
        }
      });
      add(dGreen);
    }
    if (this.button.getText().equals("Disabled Button Color"))
    {
      GuiSlider dBlue = new GuiSlider("Blue", 0.0F, 255.0F, dblue, 0);
      dBlue.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.dblue = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.dblue = (int)value;
        }
      });
      add(dBlue);
    }
    if (this.button.getText().equals("Disabled Button Color"))
    {
      GuiSlider dAlpha = new GuiSlider("Alpha", 0.0F, 255.0F, dalpha, 0);
      dAlpha.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.dalpha = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.dalpha = (int)value;
        }
      });
      add(dAlpha);
    }
    if (this.button.getText().equals("ArrayList Color"))
    {
      GuiSlider aRed = new GuiSlider("Red", 0.0F, 255.0F, ared, 0);
      aRed.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.ared = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.ared = (int)value;
        }
      });
      add(aRed);
    }
    if (this.button.getText().equals("ArrayList Color"))
    {
      GuiSlider aGreen = new GuiSlider("Green", 0.0F, 255.0F, agreen, 0);
      aGreen.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.agreen = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.agreen = (int)value;
        }
      });
      add(aGreen);
    }
    if (this.button.getText().equals("ArrayList Color"))
    {
      GuiSlider aBlue = new GuiSlider("Blue", 0.0F, 255.0F, ablue, 0);
      aBlue.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Settings.ablue = (int)value;
        }
        
        public void valueChanged(float value)
        {
          Settings.ablue = (int)value;
        }
      });
      add(aBlue);
    }
  }
}
