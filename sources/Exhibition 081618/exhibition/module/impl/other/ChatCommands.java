package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventChat;
import exhibition.management.command.Command;
import exhibition.management.command.CommandManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import java.util.Arrays;

public class ChatCommands extends Module {
   private static final String KEY_PREFIX = "CHAT-PREFIX";

   public ChatCommands(ModuleData data) {
      super(data);
      this.settings.put("CHAT-PREFIX", new Setting("CHAT-PREFIX", ".", "Command prefix."));
   }

   @RegisterEvent(
      events = {EventChat.class}
   )
   public void onEvent(Event event) {
      EventChat ec = (EventChat)event;
      String prefix = (String)((Setting)this.settings.get("CHAT-PREFIX")).getValue();
      if (ec.getText().startsWith(prefix)) {
         event.setCancelled(true);
         String[] commandBits = ec.getText().substring(prefix.length()).split(" ");
         String commandName = commandBits[0];
         Command command = (Command)CommandManager.commandMap.get(commandName);
         if (command != null) {
            if (commandBits.length > 1) {
               String[] commandArguments = (String[])Arrays.copyOfRange(commandBits, 1, commandBits.length);
               command.fire(commandArguments);
            } else {
               command.fire((String[])null);
            }

         }
      }
   }
}
