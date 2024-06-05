package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.combat.AntiBot;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.a_E;

public final class PanicCommand extends NovoCommand {
   public PanicCommand(Novoline var1) {
      super(var1, "p", "Disable modules", "panic");
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length > 1) {
         this.notifyError("Use .panic or .panic (module type)");
      } else {
         if(var1.length == 0) {
            ObjectIterator var3 = this.novoline.getModuleManager().getModuleManager().values().iterator();
            if(var3.hasNext()) {
               ModuleHolder var4 = (ModuleHolder)var3.next();
               AbstractModule var5 = var4.getModule();
               if(var5.isEnabled() && !(var5 instanceof AntiBot)) {
                  var5.toggle();
               }
            }
         }

         ObjectIterator var6 = this.novoline.getModuleManager().getModuleManager().values().iterator();
         if(var6.hasNext()) {
            ModuleHolder var7 = (ModuleHolder)var6.next();
            AbstractModule var8 = var7.getModule();
            if(var8.isEnabled() && !(var8 instanceof AntiBot) && var8.getType().toString().equalsIgnoreCase(var1[0])) {
               var8.toggle();
            }
         }

      }
   }
}
