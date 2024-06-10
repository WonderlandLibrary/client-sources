/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   5:    */ import net.minecraft.server.MinecraftServer;
/*   6:    */ 
/*   7:    */ public class CommandXP
/*   8:    */   extends CommandBase
/*   9:    */ {
/*  10:    */   private static final String __OBFID = "CL_00000398";
/*  11:    */   
/*  12:    */   public String getCommandName()
/*  13:    */   {
/*  14: 13 */     return "xp";
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int getRequiredPermissionLevel()
/*  18:    */   {
/*  19: 21 */     return 2;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  23:    */   {
/*  24: 26 */     return "commands.xp.usage";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  28:    */   {
/*  29: 31 */     if (par2ArrayOfStr.length <= 0) {
/*  30: 33 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*  31:    */     }
/*  32: 37 */     String var4 = par2ArrayOfStr[0];
/*  33: 38 */     boolean var5 = (var4.endsWith("l")) || (var4.endsWith("L"));
/*  34: 40 */     if ((var5) && (var4.length() > 1)) {
/*  35: 42 */       var4 = var4.substring(0, var4.length() - 1);
/*  36:    */     }
/*  37: 45 */     int var6 = parseInt(par1ICommandSender, var4);
/*  38: 46 */     boolean var7 = var6 < 0;
/*  39: 48 */     if (var7) {
/*  40: 50 */       var6 *= -1;
/*  41:    */     }
/*  42:    */     EntityPlayerMP var3;
/*  43:    */     EntityPlayerMP var3;
/*  44: 55 */     if (par2ArrayOfStr.length > 1) {
/*  45: 57 */       var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[1]);
/*  46:    */     } else {
/*  47: 61 */       var3 = getCommandSenderAsPlayer(par1ICommandSender);
/*  48:    */     }
/*  49: 64 */     if (var5)
/*  50:    */     {
/*  51: 66 */       if (var7)
/*  52:    */       {
/*  53: 68 */         var3.addExperienceLevel(-var6);
/*  54: 69 */         notifyAdmins(par1ICommandSender, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(var6), var3.getCommandSenderName() });
/*  55:    */       }
/*  56:    */       else
/*  57:    */       {
/*  58: 73 */         var3.addExperienceLevel(var6);
/*  59: 74 */         notifyAdmins(par1ICommandSender, "commands.xp.success.levels", new Object[] { Integer.valueOf(var6), var3.getCommandSenderName() });
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 79 */       if (var7) {
/*  65: 81 */         throw new WrongUsageException("commands.xp.failure.widthdrawXp", new Object[0]);
/*  66:    */       }
/*  67: 84 */       var3.addExperience(var6);
/*  68: 85 */       notifyAdmins(par1ICommandSender, "commands.xp.success", new Object[] { Integer.valueOf(var6), var3.getCommandSenderName() });
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  73:    */   {
/*  74: 95 */     return par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getAllUsernames()) : null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected String[] getAllUsernames()
/*  78:    */   {
/*  79:100 */     return MinecraftServer.getServer().getAllUsernames();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  83:    */   {
/*  84:108 */     return par2 == 1;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandXP
 * JD-Core Version:    0.7.0.1
 */