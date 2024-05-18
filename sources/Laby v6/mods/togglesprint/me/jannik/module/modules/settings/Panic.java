package mods.togglesprint.me.jannik.module.modules.settings;

import javax.swing.JOptionPane;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;

public class Panic
  extends Module
{
  public Panic()
  {
    super("Panic", Category.SETTINGS);
  }
  
  public void onEnabled()
  {
    int panic = JOptionPane.showConfirmDialog(null, "Panic ?");
    if (panic == 0) {
      startPanic();
    } else {
      Jannik.getModuleManager().getModuleByClass(Panic.class).setEnabled(false);
    }
  }
  
  private void startPanic()
  {
    for (Module m : Jannik.getModuleManager().getModules())
    {
      m.setEnabled(false);
      m.setKeyBind(0);
    }
  }
}
