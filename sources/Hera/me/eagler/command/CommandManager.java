/*    */ package me.eagler.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import me.eagler.Client;
/*    */ import me.eagler.command.commands.Bind;
/*    */ import me.eagler.command.commands.Config;
/*    */ import me.eagler.command.commands.Toggle;
/*    */ import me.eagler.module.Module;
/*    */ 
/*    */ 
/*    */ public class CommandManager
/*    */ {
/*    */   private List<Command> commands;
/*    */   
/*    */   public void load() {
/* 17 */     this.commands = new CopyOnWriteArrayList<Command>();
/*    */     
/* 19 */     loadCommands();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadCommands() {
/* 25 */     addCommand((Command)new Toggle());
/* 26 */     addCommand((Command)new Bind());
/* 27 */     addCommand((Command)new Config());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCommand(Command command) {
/* 34 */     this.commands.add(command);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Command> getCommands() {
/* 40 */     return this.commands;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void callCommand(String input) {
/* 46 */     String[] split = input.split(" ");
/*    */     
/* 48 */     String cmd = split[0];
/*    */     
/* 50 */     String args = input.substring(cmd.length()).trim();
/*    */     
/* 52 */     for (Command command : getCommands()) {
/*    */       
/* 54 */       if (command.getCommand().equalsIgnoreCase(cmd)) {
/*    */         
/*    */         try {
/*    */           
/* 58 */           command.onCommand(args, args.split(" "));
/*    */         }
/* 60 */         catch (Exception exception) {}
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onSendChatMessage(String message) {
/* 70 */     if (message.startsWith(".")) {
/*    */       
/* 72 */       callCommand(message.substring(1));
/*    */       
/* 74 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 78 */     for (Module module : Client.instance.getModuleManager().getModules()) {
/*    */       
/* 80 */       if (module.isEnabled())
/*    */       {
/* 82 */         return true;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 88 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\CommandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */