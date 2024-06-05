package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import net.Uj;
import net.a_E;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import org.jetbrains.annotations.NotNull;

public class IGNCommand extends NovoCommand {
   public IGNCommand(@NotNull Novoline var1) {
      super(var1, "ign");
   }

   public void process(String[] var1) throws CommandException, IOException {
      int[] var2 = a_E.b();
      if(var1.length != 0) {
         this.a("Usage", ".ign", new Uj[0]);
      }

      StringSelection var3 = new StringSelection(Minecraft.getInstance().player.getName());
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var3, var3);
      this.send("Copied ign to your clipboard!");
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
