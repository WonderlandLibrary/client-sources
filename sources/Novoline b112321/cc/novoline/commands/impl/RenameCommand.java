package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.utils.messages.MessageFactory;
import java.util.Arrays;
import java.util.Iterator;
import net.Uj;
import net.a_E;

public final class RenameCommand extends NovoCommand {
   public RenameCommand(Novoline var1) {
      super(var1, "rename", "Renames the given module", (Iterable)Arrays.asList(new String[]{"modulerename", "modulerename"}));
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length == 1 && var1[0].equalsIgnoreCase("reset")) {
         EnumModuleType[] var3 = EnumModuleType.values();
         int var4 = var3.length;
         int var5 = 0;
         if(var5 < var4) {
            EnumModuleType var6 = var3[var5];
            Iterator var7 = this.novoline.getModuleManager().getModuleManager().getByCategory(var6).iterator();
            if(var7.hasNext()) {
               AbstractModule var8 = (AbstractModule)var7.next();
               var8.setDisplayNameProperty(var8.getVanillaDisplayName());
               this.notify("all module names reseted");
            }

            ++var5;
         }
      }

      if(var1.length == 2 && this.novoline.getModuleManager().getByNameIgnoreCase(var1[0]) != null) {
         AbstractModule var9 = this.novoline.getModuleManager().getByNameIgnoreCase(var1[0]);
         if(var1[1].equalsIgnoreCase("reset")) {
            var9.setDisplayNameProperty(var9.getVanillaDisplayName());
            this.notify(var1[0] + " name reseted");
         }

         String var10 = var1[1].replace("_", " ").replace("&", "§");
         var9.setDisplayNameProperty(var10);
         this.notify(var1[0] + " renamed to " + var10);
      }

      this.a("Rename help:", ".rename", new Uj[]{MessageFactory.a("(module name) (new name)", "rename module"), MessageFactory.a("(module name) reset", "resets module name"), MessageFactory.a("reset", "resets names for all modules")});
   }
}
