package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import net.minecraft.command.CommandException;
import org.jetbrains.annotations.NotNull;

public final class DirectMessageCommand$ReplyCommand extends NovoCommand {
   public DirectMessageCommand$ReplyCommand(@NotNull Novoline var1) {
      super(var1, "reply", "Replies to the last messaged user", "r");
   }

   public void process(String[] var1) throws CommandException {
   }
}
