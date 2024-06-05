package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.utils.messages.MessageFactory;
import java.util.Arrays;
import net.Uj;
import net.a_E;
import net.minecraft.command.CommandException;
import org.jetbrains.annotations.NotNull;

public class DirectMessageCommand extends NovoCommand {
   public DirectMessageCommand(@NotNull Novoline var1) {
      super(var1, "message", "Directly message an online user", (Iterable)Arrays.asList(new String[]{"dm", "msg", "m"}));
   }

   public void process(String[] var1) throws CommandException {
      int[] var2 = a_E.b();
      if(var1.length < 2) {
         this.a("Message help:", ".message / .dm / .msg / .m", new Uj[]{MessageFactory.a("(username) (message)", "directly message a user")});
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
