/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.util.ChatComponentTranslation;
/*  6:   */ import net.minecraft.world.EnumDifficulty;
/*  7:   */ 
/*  8:   */ public class CommandDifficulty
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000422";
/* 12:   */   
/* 13:   */   public String getCommandName()
/* 14:   */   {
/* 15:14 */     return "difficulty";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getRequiredPermissionLevel()
/* 19:   */   {
/* 20:22 */     return 2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 24:   */   {
/* 25:27 */     return "commands.difficulty.usage";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 29:   */   {
/* 30:32 */     if (par2ArrayOfStr.length > 0)
/* 31:   */     {
/* 32:34 */       EnumDifficulty var3 = func_147201_h(par1ICommandSender, par2ArrayOfStr[0]);
/* 33:35 */       MinecraftServer.getServer().func_147139_a(var3);
/* 34:36 */       notifyAdmins(par1ICommandSender, "commands.difficulty.success", new Object[] { new ChatComponentTranslation(var3.getDifficultyResourceKey(), new Object[0]) });
/* 35:   */     }
/* 36:   */     else
/* 37:   */     {
/* 38:40 */       throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected EnumDifficulty func_147201_h(ICommandSender p_147201_1_, String p_147201_2_)
/* 43:   */   {
/* 44:46 */     return (!p_147201_2_.equalsIgnoreCase("peaceful")) && (!p_147201_2_.equalsIgnoreCase("p")) ? EnumDifficulty.EASY : (!p_147201_2_.equalsIgnoreCase("easy")) && (!p_147201_2_.equalsIgnoreCase("e")) ? EnumDifficulty.NORMAL : (!p_147201_2_.equalsIgnoreCase("normal")) && (!p_147201_2_.equalsIgnoreCase("n")) ? EnumDifficulty.HARD : (!p_147201_2_.equalsIgnoreCase("hard")) && (!p_147201_2_.equalsIgnoreCase("h")) ? EnumDifficulty.getDifficultyEnum(parseIntBounded(p_147201_1_, p_147201_2_, 0, 3)) : EnumDifficulty.PEACEFUL;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 48:   */   {
/* 49:54 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "peaceful", "easy", "normal", "hard" }) : null;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandDifficulty
 * JD-Core Version:    0.7.0.1
 */