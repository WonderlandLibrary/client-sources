package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.utils.messages.MessageFactory;
import net.Uj;
import net.a_E;
import net.minecraft.command.CommandException;

public class ChatCommand extends NovoCommand {
   public ChatCommand(Novoline var1) {
      super(var1, "chat", "Sends a message to the public chat", "c");
   }

   public void process(String[] var1) throws CommandException {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.a("Command help", ".chat (.c)", new Uj[]{MessageFactory.a("(message)", this.description)});
         this.sendEmpty();
      } else {
         String var3 = String.join(" ", var1);
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
