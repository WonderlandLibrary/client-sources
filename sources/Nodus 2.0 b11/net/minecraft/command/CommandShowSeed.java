/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.util.ChatComponentTranslation;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class CommandShowSeed
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001053";
/* 12:   */   
/* 13:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 14:   */   {
/* 15:17 */     return (MinecraftServer.getServer().isSinglePlayer()) || (super.canCommandSenderUseCommand(par1ICommandSender));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getCommandName()
/* 19:   */   {
/* 20:22 */     return "seed";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getRequiredPermissionLevel()
/* 24:   */   {
/* 25:30 */     return 2;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 29:   */   {
/* 30:35 */     return "commands.seed.usage";
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 34:   */   {
/* 35:40 */     Object var3 = (par1ICommandSender instanceof EntityPlayer) ? ((EntityPlayer)par1ICommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
/* 36:41 */     par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.seed.success", new Object[] { Long.valueOf(((World)var3).getSeed()) }));
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandShowSeed
 * JD-Core Version:    0.7.0.1
 */