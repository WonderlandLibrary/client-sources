package mods.togglesprint.me.imfr0zen.guiapi.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mods.togglesprint.me.imfr0zen.guiapi.components.Button;
import mods.togglesprint.me.imfr0zen.guiapi.components.GuiGetKey;
import mods.togglesprint.me.imfr0zen.guiapi.components.GuiSlider;
import mods.togglesprint.me.imfr0zen.guiapi.components.GuiToggleButton;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ExtendListener;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.KeyListener;
import mods.togglesprint.me.imfr0zen.guiapi.listeners.ValueListener;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;

public class ExampleExtendListener
  extends ExtendListener
{
  Button button;
  
  public ExampleExtendListener(Button modulebutton)
  {
    this.button = modulebutton;
  }
  
  public void addComponents()
  {
    final Module m = Jannik.getModuleManager().getModuleByName(this.button.getText());
    
    GuiGetKey getKeyBind = new GuiGetKey(m.getKeyBind());
    getKeyBind.addKeyListener(new KeyListener()
    {
      public void keyChanged(int keyBind)
      {
        if (m != null) {
          if (keyBind == 211) {
            m.setKeyBind(0);
          } else {
            m.setKeyBind(keyBind);
          }
        }
      }
    });
    add(getKeyBind);
    if (m.getName().equals("Range"))
    {
      GuiSlider range_range = new GuiSlider("Range", 1.0F, 9.0F, Values.range_range.getFloatValue(), 1);
      range_range.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.range_range.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.range_range.setFloatValue(value);
        }
      });
      add(range_range);
    }
    if (m.getName().equals("ChestStealer"))
    {
      GuiSlider cheststealer_delay = new GuiSlider("Delay", 0.0F, 100.0F, Values.cheststealer_delay.getFloatValue(), 0);
      cheststealer_delay.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.cheststealer_delay.setFloatValue((int)value);
        }
        
        public void valueChanged(float value)
        {
          Values.cheststealer_delay.setFloatValue((int)value);
        }
      });
      add(cheststealer_delay);
    }
    if (m.getName().equals("ChestStealer"))
    {
      final GuiToggleButton cheststealer_closechest = new GuiToggleButton("CloseChest");
      cheststealer_closechest.setToggled(Values.cheststealer_closechest.getBooleanValue());
      cheststealer_closechest.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.cheststealer_closechest.setBooleanValue(cheststealer_closechest.isToggled());
        }
      });
      add(cheststealer_closechest);
    }
    if (m.getName().equals("KeepSprint"))
    {
      final GuiToggleButton keepsprint_fakefov = new GuiToggleButton("FakeFOV");
      keepsprint_fakefov.setToggled(Values.keepsprint_fakefov.getBooleanValue());
      keepsprint_fakefov.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.keepsprint_fakefov.setBooleanValue(keepsprint_fakefov.isToggled());
        }
      });
      add(keepsprint_fakefov);
    }
    if (m.getName().equals("TriggerBot"))
    {
      GuiSlider triggerbot_delay = new GuiSlider("Delay", 0.0F, 500.0F, Values.triggerbot_delay.getFloatValue(), 0);
      triggerbot_delay.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.triggerbot_delay.setFloatValue((int)value);
        }
        
        public void valueChanged(float value)
        {
          Values.triggerbot_delay.setFloatValue((int)value);
        }
      });
      add(triggerbot_delay);
    }
    if (m.getName().equals("TriggerBot"))
    {
      final GuiToggleButton triggerbot_legitautoblock = new GuiToggleButton("Legit AutoBlock");
      triggerbot_legitautoblock.setToggled(Values.triggerbot_legitautoblock.getBooleanValue());
      triggerbot_legitautoblock.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.triggerbot_legitautoblock.setBooleanValue(triggerbot_legitautoblock.isToggled());
        }
      });
      add(triggerbot_legitautoblock);
    }
    if (m.getName().equals("Hud"))
    {
      final GuiToggleButton hud_drawrect = new GuiToggleButton("Draw Rect");
      hud_drawrect.setToggled(Values.hud_drawrect.getBooleanValue());
      hud_drawrect.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.hud_drawrect.setBooleanValue(hud_drawrect.isToggled());
        }
      });
      add(hud_drawrect);
    }
    if (m.getName().equals("Hud"))
    {
      final GuiToggleButton hud_leftarraylist = new GuiToggleButton("Left ArrayList");
      hud_leftarraylist.setToggled(Values.hud_leftarraylist.getBooleanValue());
      hud_leftarraylist.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.hud_leftarraylist.setBooleanValue(hud_leftarraylist.isToggled());
        }
      });
      add(hud_leftarraylist);
    }
    if (m.getName().equals("Hud"))
    {
      GuiSlider hud_y = new GuiSlider("Y", 0.0F, 250.0F, Values.hud_y.getFloatValue(), 0);
      hud_y.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.hud_y.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.hud_y.setFloatValue(value);
        }
      });
      add(hud_y);
    }
    if (m.getName().equals("Eagle"))
    {
      final GuiToggleButton eagle_click = new GuiToggleButton("Click");
      eagle_click.setToggled(Values.eagle_click.getBooleanValue());
      eagle_click.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.eagle_click.setBooleanValue(eagle_click.isToggled());
        }
      });
      add(eagle_click);
    }
    if (m.getName().equals("HitBox"))
    {
      GuiSlider hitbox_hitbox = new GuiSlider("HitBox", 10.0F, 160.0F, Values.hitbox_hitbox.getFloatValue(), 0);
      hitbox_hitbox.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.hitbox_hitbox.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.hitbox_hitbox.setFloatValue(value);
        }
      });
      add(hitbox_hitbox);
    }
    if (m.getName().equals("Velocity"))
    {
      GuiSlider velocity_xz = new GuiSlider("X/Z", 0.0F, 200.0F, Values.velocity_xz.getFloatValue(), 0);
      velocity_xz.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.velocity_xz.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.velocity_xz.setFloatValue(value);
        }
      });
      add(velocity_xz);
    }
    if (m.getName().equals("Velocity"))
    {
      GuiSlider velocity_y = new GuiSlider("Y", 0.0F, 200.0F, Values.velocity_y.getFloatValue(), 0);
      velocity_y.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.velocity_y.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.velocity_y.setFloatValue(value);
        }
      });
      add(velocity_y);
    }
    if (m.getName().equals("AutoMLG"))
    {
      final GuiToggleButton automlg_cobweb = new GuiToggleButton("Cobweb");
      automlg_cobweb.setToggled(Values.automlg_cobweb.getBooleanValue());
      automlg_cobweb.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.automlg_cobweb.setBooleanValue(automlg_cobweb.isToggled());
        }
      });
      add(automlg_cobweb);
    }
    if (m.getName().equals("AutoMLG"))
    {
      final GuiToggleButton automlg_water = new GuiToggleButton("Water");
      automlg_water.setToggled(Values.automlg_water.getBooleanValue());
      automlg_water.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.automlg_water.setBooleanValue(automlg_water.isToggled());
        }
      });
      add(automlg_water);
    }
    if (m.getName().equals("ChestEsp"))
    {
      final GuiToggleButton chestesp_chest = new GuiToggleButton("Chest");
      chestesp_chest.setToggled(Values.chestesp_chest.getBooleanValue());
      chestesp_chest.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.chestesp_chest.setBooleanValue(chestesp_chest.isToggled());
        }
      });
      add(chestesp_chest);
    }
    if (m.getName().equals("ChestEsp"))
    {
      final GuiToggleButton chestesp_enderchest = new GuiToggleButton("EnderChest");
      chestesp_enderchest.setToggled(Values.chestesp_enderchest.getBooleanValue());
      chestesp_enderchest.addClickListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent actionevent)
        {
          Values.chestesp_enderchest.setBooleanValue(chestesp_enderchest.isToggled());
        }
      });
      add(chestesp_enderchest);
    }
    if (m.getName().equals("AutoClicker"))
    {
      GuiSlider autoclicker_delay = new GuiSlider("Delay", 0.0F, 500.0F, Values.autoclicker_delay.getFloatValue(), 0);
      autoclicker_delay.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.autoclicker_delay.setFloatValue((int)value);
        }
        
        public void valueChanged(float value)
        {
          Values.autoclicker_delay.setFloatValue((int)value);
        }
      });
      add(autoclicker_delay);
    }
    if (m.getName().equals("AutoSoup"))
    {
      GuiSlider autosoup_health = new GuiSlider("Health", 0.0F, 20.0F, Values.autosoup_health.getFloatValue(), 0);
      autosoup_health.addValueListener(new ValueListener()
      {
        public void valueUpdated(float value)
        {
          Values.autosoup_health.setFloatValue(value);
        }
        
        public void valueChanged(float value)
        {
          Values.autosoup_health.setFloatValue(value);
        }
      });
      add(autosoup_health);
    }
  }
}
