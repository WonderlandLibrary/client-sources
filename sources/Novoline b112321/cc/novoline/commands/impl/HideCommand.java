package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.AbstractModule;
import java.util.Arrays;
import net.a_E;

public final class HideCommand extends NovoCommand {
   public HideCommand(Novoline var1) {
      super(var1, "Hide", "Hides modules from arraylist", (Iterable)Arrays.asList(new String[]{"hide", "h"}));
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length == 1) {
         String var3 = var1[0];
         AbstractModule var4 = this.novoline.getModuleManager().getByNameIgnoreCase(var3);
         if(var4 == null) {
            this.notifyError("Module " + var3 + " was not found!");
            return;
         }

         var4.setHidden(!var4.isHidden());
         this.notify((var4.isHidden()?"Hidden":"Shown") + " " + var4.getName());
      }

      this.notifyError("Use .hide <module> to hide a module!");
   }
}
