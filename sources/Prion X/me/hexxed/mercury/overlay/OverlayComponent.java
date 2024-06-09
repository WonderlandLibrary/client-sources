package me.hexxed.mercury.overlay;

import net.minecraft.client.Minecraft;

public class OverlayComponent
{
  protected Minecraft mc = Minecraft.getMinecraft();
  
  private int x = 0;
  
  private int y = 0;
  
  private String name;
  
  private String xy;
  private boolean chat;
  
  public OverlayComponent(String name, int x, int y, boolean chat, String xy)
  {
    this.x = x;
    this.y = y;
    this.name = name;
    this.chat = chat;
    this.xy = xy;
  }
  
  public void renderComponent() {}
  
  public String getName() {
    return name;
  }
  
  public void setXAndY(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public String getXandYString() {
    return xy;
  }
  
  public boolean getDisplayedWhileChat() {
    return chat;
  }
}
