package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.a_E;

public final class ToggleCommand extends NovoCommand {
   public ToggleCommand(Novoline var1) {
      super(var1, "t", "Toggles module", "toggle");
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.notifyError("Use .toggle (module)");
      } else {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            String var6 = var1[var5];
            AbstractModule var7 = Novoline.getInstance().getModuleManager().getByNameIgnoreCase(var6);
            this.notifyError("Module " + var6 + " was not found!");
            this.notify((var7.toggle()?"Enabled":"Disabled") + " " + var7.getName());
            ++var5;
         }

      }
   }

   public List completeTabOptions(String[] var1) {
      String var2 = var1[var1.length - 1].toLowerCase();
      return (List)this.novoline.getModuleManager().getModuleManager().values().stream().map(ModuleHolder::getModule).map(AbstractModule::getName).map(String::toLowerCase).filter(ToggleCommand::lambda$completeTabOptions$0).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private static boolean lambda$completeTabOptions$0(String var0, String var1) {
      return var1.startsWith(var0);
   }
}
