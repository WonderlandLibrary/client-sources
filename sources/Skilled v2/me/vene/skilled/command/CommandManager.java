 package me.vene.skilled.command;
 
 import java.util.HashMap;
 import java.util.Map;
 import me.vene.skilled.command.commands.FriendCommand;
 import me.vene.skilled.command.commands.LimitCommand;

 public class CommandManager
 {
   private Map<Class<? extends Command>, Command> commands = new HashMap<Class<? extends Command>, Command>(); public CommandManager() {
     for (Class<? extends Command> commandClass : COMMANDS) {
       try {
    	   this.commands.put(commandClass, commandClass.newInstance());
       }
       catch (Throwable t) {}
   }
}
   
   public <T extends Command> T getCommand(Class<T> clazz) {
     return (T)this.commands.get(clazz);
   }
   
   public static Class[] COMMANDS = new Class[] { FriendCommand.class, LimitCommand.class };
 }
