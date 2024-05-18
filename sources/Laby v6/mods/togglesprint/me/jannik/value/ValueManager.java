package mods.togglesprint.me.jannik.value;

import java.util.ArrayList;
import mods.togglesprint.me.jannik.value.values.Values;
//LabyClient src by Exeptiq
public class ValueManager
{
  private ArrayList<Value> floatValues = new ArrayList();
  private ArrayList<Value> booleanValues = new ArrayList();
  
  public void addFloatValues(Value floatValues)
  {
    this.floatValues.add(floatValues);
  }
  
  public void addBooleanValues(Value booleanValues)
  {
    this.booleanValues.add(booleanValues);
  }
  
  public ValueManager()
  {
    addFloatValues(Values.autoclicker_delay);
    addFloatValues(Values.hitbox_hitbox);
    addFloatValues(Values.range_range);
    addFloatValues(Values.triggerbot_delay);
    addBooleanValues(Values.triggerbot_legitautoblock);
    
    addBooleanValues(Values.keepsprint_fakefov);
    addFloatValues(Values.velocity_xz);
    addFloatValues(Values.velocity_y);
    
    addBooleanValues(Values.chestesp_chest);
    addBooleanValues(Values.chestesp_enderchest);
    addBooleanValues(Values.hud_drawrect);
    addBooleanValues(Values.hud_leftarraylist);
    addFloatValues(Values.hud_y);
    
    addBooleanValues(Values.automlg_cobweb);
    addBooleanValues(Values.automlg_water);
    addFloatValues(Values.autosoup_health);
    addFloatValues(Values.cheststealer_delay);
    addBooleanValues(Values.cheststealer_closechest);
    
    addBooleanValues(Values.eagle_click);
  }
  
  public ArrayList<Value> getFloatValues()
  {
    return this.floatValues;
  }
  
  public ArrayList<Value> getBooleanValues()
  {
    return this.booleanValues;
  }
  
  public Value getFloatValueByName(String name)
  {
    for (Value v : this.floatValues) {
      if (name.equals(v.getFloatName())) {
        return v;
      }
    }
    return null;
  }
  
  public Value getBooleanValueByName(String name)
  {
    for (Value v : this.booleanValues) {
      if (name.equals(v.getBooleanName())) {
        return v;
      }
    }
    return null;
  }
}
