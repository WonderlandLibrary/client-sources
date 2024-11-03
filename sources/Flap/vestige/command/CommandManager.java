package vestige.command;

import java.util.ArrayList;
import java.util.Iterator;
import vestige.Flap;
import vestige.command.impl.Bind;
import vestige.command.impl.Config;
import vestige.command.impl.Toggle;
import vestige.command.impl.Tp;
import vestige.event.Listener;
import vestige.event.impl.ChatSendEvent;
import vestige.util.misc.LogUtil;

public class CommandManager {
   public final ArrayList<Command> commands = new ArrayList();

   public CommandManager() {
      Flap.instance.getEventManager().register(this);
      this.commands.add(new Toggle());
      this.commands.add(new Bind());
      this.commands.add(new Config());
      this.commands.add(new Tp());
   }

   @Listener
   public void onChatSend(ChatSendEvent event) {
      String message = event.getMessage();
      if (message.startsWith(".")) {
         event.setCancelled(true);
         String commandName = "";

         for(int i = 0; i < message.length(); ++i) {
            if (i > 0) {
               char c = message.charAt(i);
               if (c == ' ') {
                  break;
               }

               commandName = commandName + c;
            }
         }

         Command command = this.getCommandByName(commandName);
         if (command != null) {
            String commandWithoutDot = message.substring(1);
            String[] commandParts = commandWithoutDot.split(" ");
            command.onCommand(commandParts);
         } else {
            LogUtil.addChatMessage("&c&l[FLAP]&r &fCommand not found");
         }
      }

   }

   public Command getCommandByName(String name) {
      Iterator var2 = this.commands.iterator();

      while(true) {
         Command command;
         do {
            do {
               if (!var2.hasNext()) {
                  return null;
               }

               command = (Command)var2.next();
               if (command.getName().equalsIgnoreCase(name)) {
                  return command;
               }
            } while(command.getAliases() == null);
         } while(command.getAliases().length <= 0);

         String[] var4 = command.getAliases();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String alias = var4[var6];
            if (alias.equalsIgnoreCase(name)) {
               return command;
            }
         }
      }
   }
}
