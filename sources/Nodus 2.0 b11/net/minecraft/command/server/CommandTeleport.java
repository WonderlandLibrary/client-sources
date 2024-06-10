/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.command.CommandBase;
/*   5:    */ import net.minecraft.command.ICommandSender;
/*   6:    */ import net.minecraft.command.PlayerNotFoundException;
/*   7:    */ import net.minecraft.command.WrongUsageException;
/*   8:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   9:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  10:    */ import net.minecraft.server.MinecraftServer;
/*  11:    */ 
/*  12:    */ public class CommandTeleport
/*  13:    */   extends CommandBase
/*  14:    */ {
/*  15:    */   private static final String __OBFID = "CL_00001180";
/*  16:    */   
/*  17:    */   public String getCommandName()
/*  18:    */   {
/*  19: 18 */     return "tp";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getRequiredPermissionLevel()
/*  23:    */   {
/*  24: 26 */     return 2;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  28:    */   {
/*  29: 31 */     return "commands.tp.usage";
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  33:    */   {
/*  34: 36 */     if (par2ArrayOfStr.length < 1) {
/*  35: 38 */       throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*  36:    */     }
/*  37:    */     EntityPlayerMP var3;
/*  38:    */     EntityPlayerMP var3;
/*  39: 44 */     if ((par2ArrayOfStr.length != 2) && (par2ArrayOfStr.length != 4))
/*  40:    */     {
/*  41: 46 */       var3 = getCommandSenderAsPlayer(par1ICommandSender);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 50 */       var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/*  46: 52 */       if (var3 == null) {
/*  47: 54 */         throw new PlayerNotFoundException();
/*  48:    */       }
/*  49:    */     }
/*  50: 58 */     if ((par2ArrayOfStr.length != 3) && (par2ArrayOfStr.length != 4))
/*  51:    */     {
/*  52: 60 */       if ((par2ArrayOfStr.length == 1) || (par2ArrayOfStr.length == 2))
/*  53:    */       {
/*  54: 62 */         EntityPlayerMP var11 = getPlayer(par1ICommandSender, par2ArrayOfStr[(par2ArrayOfStr.length - 1)]);
/*  55: 64 */         if (var11 == null) {
/*  56: 66 */           throw new PlayerNotFoundException();
/*  57:    */         }
/*  58: 69 */         if (var11.worldObj != var3.worldObj)
/*  59:    */         {
/*  60: 71 */           notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
/*  61: 72 */           return;
/*  62:    */         }
/*  63: 75 */         var3.mountEntity(null);
/*  64: 76 */         var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
/*  65: 77 */         notifyAdmins(par1ICommandSender, "commands.tp.success", new Object[] { var3.getCommandSenderName(), var11.getCommandSenderName() });
/*  66:    */       }
/*  67:    */     }
/*  68: 80 */     else if (var3.worldObj != null)
/*  69:    */     {
/*  70: 82 */       int var4 = par2ArrayOfStr.length - 3;
/*  71: 83 */       double var5 = func_110666_a(par1ICommandSender, var3.posX, par2ArrayOfStr[(var4++)]);
/*  72: 84 */       double var7 = func_110665_a(par1ICommandSender, var3.posY, par2ArrayOfStr[(var4++)], 0, 0);
/*  73: 85 */       double var9 = func_110666_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[(var4++)]);
/*  74: 86 */       var3.mountEntity(null);
/*  75: 87 */       var3.setPositionAndUpdate(var5, var7, var9);
/*  76: 88 */       notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", new Object[] { var3.getCommandSenderName(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9) });
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  81:    */   {
/*  82: 98 */     return (par2ArrayOfStr.length != 1) && (par2ArrayOfStr.length != 2) ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  86:    */   {
/*  87:106 */     return par2 == 0;
/*  88:    */   }
/*  89:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandTeleport
 * JD-Core Version:    0.7.0.1
 */