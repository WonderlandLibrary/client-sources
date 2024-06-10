/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.file.NodusFileManager;
/*  6:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import org.lwjgl.input.Keyboard;
/*  9:   */ 
/* 10:   */ public class Keybind
/* 11:   */   extends NodusCommand
/* 12:   */ {
/* 13:   */   public Keybind()
/* 14:   */   {
/* 15:13 */     super("keybind");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:21 */       if (commandArgs[0].equalsIgnoreCase("set")) {
/* 23:23 */         for (NodusModule theModule : Nodus.theNodus.moduleManager.getModules()) {
/* 24:25 */           if (theModule.getName().replace(" ", "").equalsIgnoreCase(commandArgs[1]))
/* 25:   */           {
/* 26:27 */             if (Keyboard.getKeyIndex(commandArgs[2].toUpperCase()) == 0)
/* 27:   */             {
/* 28:29 */               Nodus.theNodus.addChatMessage("Invalid key.");
/* 29:30 */               return;
/* 30:   */             }
/* 31:32 */             theModule.setKeyBind(Keyboard.getKeyIndex(commandArgs[2].toUpperCase()));
/* 32:33 */             Nodus.theNodus.addChatMessage("Mod " + theModule.getName() + " has been bound to " + Keyboard.getKeyName(theModule.getKeyBind()));
/* 33:34 */             Nodus.theNodus.fileManager.saveKeyBinds();
/* 34:35 */             break;
/* 35:   */           }
/* 36:   */         }
/* 37:   */       }
/* 38:40 */       if (commandArgs[0].equalsIgnoreCase("del")) {
/* 39:42 */         for (NodusModule theModule : Nodus.theNodus.moduleManager.getModules()) {
/* 40:44 */           if (theModule.getName().equalsIgnoreCase(commandArgs[1]))
/* 41:   */           {
/* 42:46 */             theModule.setKeyBind(0);
/* 43:47 */             Nodus.theNodus.addChatMessage("Keybind cleared from mod " + theModule.getName());
/* 44:48 */             Nodus.theNodus.fileManager.saveKeyBinds();
/* 45:   */           }
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:   */     catch (Exception commandSyntaxException)
/* 50:   */     {
/* 51:54 */       commandSyntaxException.printStackTrace();
/* 52:55 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String getDescription()
/* 57:   */   {
/* 58:62 */     return "Binds a key to a module";
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getSyntax()
/* 62:   */   {
/* 63:68 */     return "set <mod> <key>, del <mod>";
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String getConsoleDisplay()
/* 67:   */   {
/* 68:74 */     return "keybind";
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Keybind
 * JD-Core Version:    0.7.0.1
 */