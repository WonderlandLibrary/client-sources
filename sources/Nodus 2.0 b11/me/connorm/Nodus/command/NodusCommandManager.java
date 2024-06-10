/*  1:   */ package me.connorm.Nodus.command;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import me.connorm.Nodus.Nodus;
/*  6:   */ import me.connorm.Nodus.command.commands.Aimbot;
/*  7:   */ import me.connorm.Nodus.command.commands.AllOff;
/*  8:   */ import me.connorm.Nodus.command.commands.BreadcrumbClear;
/*  9:   */ import me.connorm.Nodus.command.commands.Build;
/* 10:   */ import me.connorm.Nodus.command.commands.ChestESP;
/* 11:   */ import me.connorm.Nodus.command.commands.Color;
/* 12:   */ import me.connorm.Nodus.command.commands.Enemy;
/* 13:   */ import me.connorm.Nodus.command.commands.ForceField;
/* 14:   */ import me.connorm.Nodus.command.commands.Friend;
/* 15:   */ import me.connorm.Nodus.command.commands.Help;
/* 16:   */ import me.connorm.Nodus.command.commands.Keybind;
/* 17:   */ import me.connorm.Nodus.command.commands.Search;
/* 18:   */ import me.connorm.Nodus.command.commands.Toggle;
/* 19:   */ import me.connorm.Nodus.command.commands.Xray;
/* 20:   */ import me.connorm.Nodus.event.chat.EventChatSend;
/* 21:   */ import me.connorm.lib.EventManager;
/* 22:   */ import me.connorm.lib.EventTarget;
/* 23:   */ 
/* 24:   */ public class NodusCommandManager
/* 25:   */ {
/* 26:27 */   private List<NodusCommand> theCommands = new ArrayList();
/* 27:   */   public boolean commandResolved;
/* 28:30 */   public char commandPrefix = '.';
/* 29:   */   
/* 30:   */   public NodusCommandManager()
/* 31:   */   {
/* 32:34 */     EventManager.register(this);
/* 33:35 */     this.theCommands.clear();
/* 34:36 */     this.theCommands.add(new Aimbot());
/* 35:37 */     this.theCommands.add(new AllOff());
/* 36:38 */     this.theCommands.add(new BreadcrumbClear());
/* 37:39 */     this.theCommands.add(new Build());
/* 38:40 */     this.theCommands.add(new ChestESP());
/* 39:41 */     this.theCommands.add(new Color());
/* 40:42 */     this.theCommands.add(new Enemy());
/* 41:43 */     this.theCommands.add(new ForceField());
/* 42:44 */     this.theCommands.add(new Friend());
/* 43:45 */     this.theCommands.add(new Help());
/* 44:46 */     this.theCommands.add(new Keybind());
/* 45:47 */     this.theCommands.add(new Search());
/* 46:48 */     this.theCommands.add(new Toggle());
/* 47:49 */     this.theCommands.add(new Xray());
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void runCommands(String commandRun)
/* 51:   */   {
/* 52:54 */     if ((!commandRun.contains(Character.toString(this.commandPrefix))) || (!commandRun.startsWith(Character.toString(this.commandPrefix)))) {
/* 53:55 */       return;
/* 54:   */     }
/* 55:57 */     this.commandResolved = false;
/* 56:58 */     String readString = commandRun.trim().substring(Character.toString(this.commandPrefix).length()).trim();
/* 57:59 */     boolean hasArgs = readString.trim().contains(" ");
/* 58:60 */     String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
/* 59:61 */     String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];
/* 60:63 */     for (NodusCommand theCommand : this.theCommands) {
/* 61:65 */       if (theCommand.getCommandName().trim().equalsIgnoreCase(commandName.trim()))
/* 62:   */       {
/* 63:67 */         theCommand.runCommand(readString, args);
/* 64:68 */         this.commandResolved = true;
/* 65:69 */         break;
/* 66:   */       }
/* 67:   */     }
/* 68:72 */     if (!this.commandResolved) {
/* 69:74 */       Nodus.theNodus.addChatMessage("Command is invalid. Type .help to recieve help.");
/* 70:   */     }
/* 71:   */   }
/* 72:   */   
/* 73:   */   @EventTarget
/* 74:   */   public void sendChatMessage(EventChatSend theEvent)
/* 75:   */   {
/* 76:81 */     if (theEvent.getChatMessage().startsWith(Character.toString(this.commandPrefix)))
/* 77:   */     {
/* 78:83 */       runCommands(theEvent.getChatMessage());
/* 79:84 */       theEvent.setCancelled(true);
/* 80:   */     }
/* 81:   */   }
/* 82:   */   
/* 83:   */   public NodusCommand[] getCommands()
/* 84:   */   {
/* 85:90 */     return (NodusCommand[])this.theCommands.toArray(new NodusCommand[this.theCommands.size()]);
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.NodusCommandManager
 * JD-Core Version:    0.7.0.1
 */