package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;

public class ExternalGui
  extends Module
{
  public ExternalGui()
  {
    super("ExternalGui", Category.RENDER);
  }
  
  public void onEnabled()
  {
    mods.togglesprint.me.jannik.gui.ExternalGui e = new mods.togglesprint.me.jannik.gui.ExternalGui();
    e.setVisible(true);
  }
  
  @EventTarget
  private void onUpdate(EventUpdate event)
  {
    toggleModule();
  }
}
