package net.SliceClient.module;

import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;

public class Module
{
  private String name;
  protected int bind;
  private Category category;
  private boolean isEnabled;
  private String modulename;
  private boolean outboundpacketcancelled;
  protected int color;
  private String displayName;
  private boolean isToggled;
  private String[] modNames;
  public boolean State;
  private int keyBind;
  
  public Module(String name, Category category, int color)
  {
    this.name = name;
    this.category = category;
    this.color = color; }
  
  public static Minecraft mc = ;
  
  public String getName() {
    return name;
  }
  
  public void Toggle()
  {
    State = (!State);
    if (State) {
      onEnable();
    } else {
      onDisable();
    }
  }
  
  public int getBind() {
    return bind;
  }
  
  public int getColor() {
    return color;
  }
  
  public Category getCategory() {
    return category;
  }
  
  public boolean getState() {
    return isEnabled;
  }
  
  public void setState(boolean state) {
    if (Minecraft.thePlayer != null) {
      Minecraft.thePlayer.playSound("random.pop", 20.0F, 1.0F);
    }
    onToggle();
    if (state) {
      onEnable();
      isEnabled = true;
    } else {
      onDisable();
      isEnabled = false;
    }
  }
  
  public void setBind(int bind) {
    this.bind = bind;
  }
  
  public void toggleModule() {
    setState(!getState());
  }
  
  public void setOutboundPacketCancelled(boolean state) {
    outboundpacketcancelled = state;
  }
  

  public void onToggle() {}
  

  public void onPreMotionUpdate() {}
  
  public void onEnable()
  {
    EventManager.register(this);
  }
  
  public void onDisable() {
    EventManager.unregister(this);
  }
  
  public void onUpdate() {}
  
  public void onRender() {}
  
  public void setColor(int color)
  {
    this.color = color;
  }
  
  public final boolean isCategory(Category s) {
    if (s == category)
      return true;
    return false;
  }
  

  public void onTick() {}
  
  public void init(int i, String name) {}
  
  public String getModuleName()
  {
    return modulename;
  }
  
  public int getKeyBind() {
    return keyBind;
  }
  
  public boolean isEnabled() {
    return isEnabled;
  }
  


  public void onPostUpdate() {}
  

  public void onRender3d() {}
  

  public void setEnabled(boolean enabled)
  {
    if (enabled)
    {
      isEnabled = true;
      onEnable();
    }
    else
    {
      isEnabled = false;
      onDisable();
    }
  }
  



  public void onPostMotionUpdate() {}
  


  public void onRender2() {}
  


  public boolean getToggled()
  {
    return isToggled;
  }
  

  public void setToggled(boolean toggle)
  {
    if (toggle)
    {
      onEnable();
      isToggled = true;
    }
    else
    {
      onDisable();
      isToggled = false;
    }
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public String getDisplayName() {
    return displayName;
  }
  
  public String[] getModNames()
  {
    return modNames;
  }
  
  public boolean isDisabled()
  {
    return !State;
  }
}
