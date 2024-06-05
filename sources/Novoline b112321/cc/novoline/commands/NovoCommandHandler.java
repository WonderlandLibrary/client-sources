package cc.novoline.commands;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.BindCommand;
import cc.novoline.commands.impl.ChatCommand;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.commands.impl.DirectMessageCommand;
import cc.novoline.commands.impl.DirectMessageCommand$ReplyCommand;
import cc.novoline.commands.impl.FindBountyCommand;
import cc.novoline.commands.impl.FontCommand;
import cc.novoline.commands.impl.FriendCommand;
import cc.novoline.commands.impl.HideCommand;
import cc.novoline.commands.impl.IGNCommand;
import cc.novoline.commands.impl.KillsultsCommand;
import cc.novoline.commands.impl.NameCommand;
import cc.novoline.commands.impl.PanicCommand;
import cc.novoline.commands.impl.RenameCommand;
import cc.novoline.commands.impl.TargetCommand;
import cc.novoline.commands.impl.ToggleCommand;
import cc.novoline.commands.impl.VClipCommand;
import cc.novoline.commands.impl.WaypointCommand;
import net.minecraft.command.CommandHandler;
import org.jetbrains.annotations.NotNull;

public class NovoCommandHandler extends CommandHandler {
   public NovoCommandHandler(@NotNull Novoline var1) {
      super(".");
      this.registerCommands(var1);
   }

   public void registerCommands(Novoline var1) {
      this.registerCommand(new TargetCommand(var1));
      this.registerCommand(new BindCommand(var1));
      this.registerCommand(new ConfigCommand(var1));
      this.registerCommand(new FriendCommand(var1));
      this.registerCommand(new NameCommand(var1));
      this.registerCommand(new ToggleCommand(var1));
      this.registerCommand(new VClipCommand(var1));
      this.registerCommand(new WaypointCommand(var1));
      this.registerCommand(new PanicCommand(var1));
      this.registerCommand(new HideCommand(var1));
      this.registerCommand(new ChatCommand(var1));
      this.registerCommand(new DirectMessageCommand(var1));
      this.registerCommand(new DirectMessageCommand$ReplyCommand(var1));
      this.registerCommand(new KillsultsCommand(var1));
      this.registerCommand(new RenameCommand(var1));
      this.registerCommand(new FindBountyCommand(var1));
      this.registerCommand(new IGNCommand(var1));
      this.registerCommand(new FontCommand(var1));
   }
}
