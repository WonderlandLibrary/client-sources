/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  4:   */ import net.minecraft.util.ChatComponentTranslation;
/*  5:   */ import net.minecraft.util.DamageSource;
/*  6:   */ 
/*  7:   */ public class CommandKill
/*  8:   */   extends CommandBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000570";
/* 11:   */   
/* 12:   */   public String getCommandName()
/* 13:   */   {
/* 14:13 */     return "kill";
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getRequiredPermissionLevel()
/* 18:   */   {
/* 19:21 */     return 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 23:   */   {
/* 24:26 */     return "commands.kill.usage";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 28:   */   {
/* 29:31 */     EntityPlayerMP var3 = getCommandSenderAsPlayer(par1ICommandSender);
/* 30:32 */     var3.attackEntityFrom(DamageSource.outOfWorld, 3.4028235E+38F);
/* 31:33 */     par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.kill.success", new Object[0]));
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandKill
 * JD-Core Version:    0.7.0.1
 */