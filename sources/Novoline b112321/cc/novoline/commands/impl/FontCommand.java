package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.visual.HUD;
import java.io.IOException;
import net.Uj;
import net.a_E;
import net.minecraft.command.CommandException;
import org.jetbrains.annotations.NotNull;

public class FontCommand extends NovoCommand {
   public FontCommand(@NotNull Novoline var1) {
      super(var1, "font");
   }

   public void process(String[] var1) throws CommandException, IOException {
      a_E.b();
      HUD var3 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      switch(var1.length) {
      case 1:
         var3.a(var1[0], 20);
      case 2:
         var3.a(var1[0], Integer.parseInt(var1[1]));
      default:
         this.a("Usage", ".font <font name> (path to file) <size>", new Uj[0]);
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
