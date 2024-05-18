package mods.togglesprint.me.imfr0zen.guiapi.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mods.togglesprint.me.imfr0zen.guiapi.components.Button;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;

public class ExampleClickListener
  implements ActionListener
{
  Button button;
  
  public ExampleClickListener(Button modulebutton)
  {
    this.button = modulebutton;
  }
  
  public void actionPerformed(ActionEvent actionevent)
  {
    Module m = Jannik.getModuleManager().getModuleByName(this.button.getText());
    m.toggleModule();
  }
}
