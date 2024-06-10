/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.command.CommandBase;
/*  6:   */ import net.minecraft.command.ICommandSender;
/*  7:   */ import net.minecraft.command.WrongUsageException;
/*  8:   */ import net.minecraft.server.MinecraftServer;
/*  9:   */ import net.minecraft.server.management.ServerConfigurationManager;
/* 10:   */ 
/* 11:   */ public class CommandOp
/* 12:   */   extends CommandBase
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000694";
/* 15:   */   
/* 16:   */   public String getCommandName()
/* 17:   */   {
/* 18:16 */     return "op";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getRequiredPermissionLevel()
/* 22:   */   {
/* 23:24 */     return 3;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 27:   */   {
/* 28:29 */     return "commands.op.usage";
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 32:   */   {
/* 33:34 */     if ((par2ArrayOfStr.length == 1) && (par2ArrayOfStr[0].length() > 0))
/* 34:   */     {
/* 35:36 */       MinecraftServer.getServer().getConfigurationManager().addOp(par2ArrayOfStr[0]);
/* 36:37 */       notifyAdmins(par1ICommandSender, "commands.op.success", new Object[] { par2ArrayOfStr[0] });
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:41 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 45:   */   {
/* 46:50 */     if (par2ArrayOfStr.length == 1)
/* 47:   */     {
/* 48:52 */       String var3 = par2ArrayOfStr[(par2ArrayOfStr.length - 1)];
/* 49:53 */       ArrayList var4 = new ArrayList();
/* 50:54 */       String[] var5 = MinecraftServer.getServer().getAllUsernames();
/* 51:55 */       int var6 = var5.length;
/* 52:57 */       for (int var7 = 0; var7 < var6; var7++)
/* 53:   */       {
/* 54:59 */         String var8 = var5[var7];
/* 55:61 */         if ((!MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(var8)) && (doesStringStartWith(var3, var8))) {
/* 56:63 */           var4.add(var8);
/* 57:   */         }
/* 58:   */       }
/* 59:67 */       return var4;
/* 60:   */     }
/* 61:71 */     return null;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandOp
 * JD-Core Version:    0.7.0.1
 */