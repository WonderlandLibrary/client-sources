package mods.togglesprint.me.imfr0zen.guiapi.components;

public abstract interface GuiComponent
{
  public abstract void render(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void mouseClicked(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void keyTyped(int paramInt, char paramChar);
  
  public abstract int getWidth();
  
  public abstract int getHeight();
}
