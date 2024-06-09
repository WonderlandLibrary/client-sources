package events;

import net.minecraft.client.gui.GuiScreen;
import darkmagician6.EventCancellable;

public class EventDisplayScreen
  extends EventCancellable
{
  private GuiScreen guiScreen;
  
  public EventDisplayScreen(GuiScreen guiScreen)
  {
    this.guiScreen = guiScreen;
  }
  
  public void setGuiScreen(GuiScreen guiScreen)
  {
    this.guiScreen = guiScreen;
  }
  
  public GuiScreen getGuiScreen()
  {
    return this.guiScreen;
  }
}
