package net.minecraft.client.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable
{
  private static final List keybindArray = ;
  private static final IntHashMap hash = new IntHashMap();
  private static final Set keybindSet = Sets.newHashSet();
  
  private final String keyDescription;
  
  private final int keyCodeDefault;
  private final String keyCategory;
  private int keyCode;
  public boolean pressed;
  private int pressTime;
  private static final String __OBFID = "CL_00000628";
  
  public static void onTick(int keyCode)
  {
    if (keyCode != 0)
    {
      KeyBinding var1 = (KeyBinding)hash.lookup(keyCode);
      
      if (var1 != null)
      {
        pressTime += 1;
      }
    }
  }
  
  public static void setKeyBindState(int keyCode, boolean pressed)
  {
    if (keyCode != 0)
    {
      KeyBinding var2 = (KeyBinding)hash.lookup(keyCode);
      
      if (var2 != null)
      {
        pressed = pressed;
      }
    }
  }
  
  public static void unPressAllKeys()
  {
    Iterator var0 = keybindArray.iterator();
    
    while (var0.hasNext())
    {
      KeyBinding var1 = (KeyBinding)var0.next();
      var1.unpressKey();
    }
  }
  
  public static void resetKeyBindingArrayAndHash()
  {
    hash.clearMap();
    Iterator var0 = keybindArray.iterator();
    
    while (var0.hasNext())
    {
      KeyBinding var1 = (KeyBinding)var0.next();
      hash.addKey(keyCode, var1);
    }
  }
  
  public static Set getKeybinds()
  {
    return keybindSet;
  }
  
  public KeyBinding(String description, int keyCode, String category)
  {
    keyDescription = description;
    this.keyCode = keyCode;
    keyCodeDefault = keyCode;
    keyCategory = category;
    keybindArray.add(this);
    hash.addKey(keyCode, this);
    keybindSet.add(category);
  }
  
  public boolean getIsKeyPressed()
  {
    return pressed;
  }
  
  public String getKeyCategory()
  {
    return keyCategory;
  }
  
  public boolean isPressed()
  {
    if (pressTime == 0)
    {
      return false;
    }
    

    pressTime -= 1;
    return true;
  }
  

  public void unpressKey()
  {
    pressTime = 0;
    pressed = false;
  }
  
  public String getKeyDescription()
  {
    return keyDescription;
  }
  
  public int getKeyCodeDefault()
  {
    return keyCodeDefault;
  }
  
  public int getKeyCode()
  {
    return keyCode;
  }
  
  public void setKeyCode(int keyCode)
  {
    this.keyCode = keyCode;
  }
  
  public int compareTo(KeyBinding p_compareTo_1_)
  {
    int var2 = I18n.format(keyCategory, new Object[0]).compareTo(I18n.format(keyCategory, new Object[0]));
    
    if (var2 == 0)
    {
      var2 = I18n.format(keyDescription, new Object[0]).compareTo(I18n.format(keyDescription, new Object[0]));
    }
    
    return var2;
  }
  
  public int compareTo(Object p_compareTo_1_)
  {
    return compareTo((KeyBinding)p_compareTo_1_);
  }
  
  public boolean isKeyDown()
  {
    return pressed;
  }
}
