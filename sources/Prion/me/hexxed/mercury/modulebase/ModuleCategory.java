package me.hexxed.mercury.modulebase;


public enum ModuleCategory
{
  NONE("§3"), 
  WORLD("§3"), 
  COMBAT("§3"), 
  RENDER("§3"), 
  MISC("§3"), 
  PLAYER("§3"), 
  MOVEMENT("§3"),  EXPLOITS("§3"),  MODES("§3");
  
  private String color;
  
  private ModuleCategory(String color)
  {
    this.color = color;
  }
  
  public String getColor() {
    return color;
  }
}
