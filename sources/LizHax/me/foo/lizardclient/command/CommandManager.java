/*    */ package me.foo.lizardclient.command;
/*    */ import java.util.ArrayList;
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.commands.Bind;
/*    */ import me.foo.lizardclient.commands.CommandList;
/*    */ import me.foo.lizardclient.commands.Espblocks;
/*    */ import me.foo.lizardclient.commands.Ghost;
/*    */ import me.foo.lizardclient.commands.Help;
/*    */ import me.foo.lizardclient.commands.Toggle;
/*    */ 
/*    */ public class CommandManager {
/*    */   public CommandManager() {
/* 13 */     this.commands = new ArrayList<>();
/* 14 */     addCommand((Command)new Bind());
/* 15 */     addCommand((Command)new Toggle());
/* 16 */     addCommand((Command)new Ghost());
/* 17 */     addCommand((Command)new Help());
/* 18 */     addCommand((Command)new CommandList());
/* 19 */     addCommand((Command)new Espblocks());
/*    */   }
/*    */   private ArrayList<Command> commands;
/*    */   public void addCommand(Command c) {
/* 23 */     this.commands.add(c);
/*    */   }
/*    */   
/*    */   public ArrayList<Command> getCommands() {
/* 27 */     return this.commands;
/*    */   }
/*    */   
/*    */   public void callCommand(String input) {
/* 31 */     String[] split = input.split(" ");
/* 32 */     String command = split[0];
/* 33 */     String args = input.substring(command.length()).trim();
/* 34 */     for (Command c : getCommands()) {
/* 35 */       if (c.getAlias().equalsIgnoreCase(command)) {
/*    */         try {
/* 37 */           c.onCommand(args, args.split(" "));
/* 38 */         } catch (Exception e) {
/* 39 */           Client.addChatMessage("§4Invalid Syntax");
/* 40 */           Client.addChatMessage(c.getSyntax());
/*    */         } 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 46 */     Client.addChatMessage("§4Invalid Command");
/*    */   }
/*    */ }