/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.command.NodusCommand;
/*  6:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  7:   */ import me.connorm.Nodus.module.modules.Breadcrumbs;
/*  8:   */ 
/*  9:   */ public class BreadcrumbClear
/* 10:   */   extends NodusCommand
/* 11:   */ {
/* 12:   */   public BreadcrumbClear()
/* 13:   */   {
/* 14:10 */     super("breadcrumbs");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 18:   */   {
/* 19:16 */     if (commandArgs[0].equalsIgnoreCase("clear"))
/* 20:   */     {
/* 21:18 */       Nodus.theNodus.moduleManager.breadcrumbsModule.theCrumbs.clear();
/* 22:19 */       Nodus.theNodus.addChatMessage("Cleared the breadcrumb line.");
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getDescription()
/* 27:   */   {
/* 28:26 */     return "Clears the breadcrumb line";
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getSyntax()
/* 32:   */   {
/* 33:32 */     return "clear";
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getConsoleDisplay()
/* 37:   */   {
/* 38:38 */     return "breadcrumbs";
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.BreadcrumbClear
 * JD-Core Version:    0.7.0.1
 */