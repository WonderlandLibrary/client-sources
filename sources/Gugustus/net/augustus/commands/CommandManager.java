package net.augustus.commands;

import java.util.ArrayList;
import java.util.List;
import net.augustus.Augustus;
import net.augustus.commands.commands.*;
import net.augustus.events.EventChat;
import net.lenni0451.eventapi.manager.EventManager;
import net.lenni0451.eventapi.reflection.EventTarget;

public class CommandManager {
   private List<Command> commands = new ArrayList<>();

   public CommandManager() {
      EventManager.register(this);
      this.commands.add(new CommandBind());
      this.commands.add(new CommandHelp());
      this.commands.add(new CommandInGameName());
      this.commands.add(new CommandToggle());
      this.commands.add(new CommandBlockESP());
      this.commands.add(new CommandFucker());
      this.commands.add(new CommandTest());
      this.commands.add(new CommandClicker());
   }

   @EventTarget
   public void onEventChat(EventChat eventChat) {
      String message = eventChat.getMessage();

      for(Command command : Augustus.getInstance().getCommandManager().getCommands()) {
         String[] strings = message.split(" ");
         if (strings.length > 0 && strings[0].equalsIgnoreCase(command.getCommand())) {
            command.commandAction(strings);
            eventChat.setCanceled(true);
         }
      }
   }

   public List<Command> getCommands() {
      return this.commands;
   }

   public void setCommands(List<Command> commands) {
      this.commands = commands;
   }
}
