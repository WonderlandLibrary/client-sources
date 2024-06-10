/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   4:    */ import net.minecraft.network.NetHandlerPlayServer;
/*   5:    */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*   6:    */ import net.minecraft.util.ChunkCoordinates;
/*   7:    */ 
/*   8:    */ public class CommandPlaySound
/*   9:    */   extends CommandBase
/*  10:    */ {
/*  11:    */   private static final String __OBFID = "CL_00000774";
/*  12:    */   
/*  13:    */   public String getCommandName()
/*  14:    */   {
/*  15: 12 */     return "playsound";
/*  16:    */   }
/*  17:    */   
/*  18:    */   public int getRequiredPermissionLevel()
/*  19:    */   {
/*  20: 20 */     return 2;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  24:    */   {
/*  25: 25 */     return "commands.playsound.usage";
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  29:    */   {
/*  30: 30 */     if (par2ArrayOfStr.length < 2) {
/*  31: 32 */       throw new WrongUsageException(getCommandUsage(par1ICommandSender), new Object[0]);
/*  32:    */     }
/*  33: 36 */     byte var3 = 0;
/*  34: 37 */     int var36 = var3 + 1;
/*  35: 38 */     String var4 = par2ArrayOfStr[var3];
/*  36: 39 */     EntityPlayerMP var5 = getPlayer(par1ICommandSender, par2ArrayOfStr[(var36++)]);
/*  37: 40 */     double var6 = var5.getPlayerCoordinates().posX;
/*  38: 41 */     double var8 = var5.getPlayerCoordinates().posY;
/*  39: 42 */     double var10 = var5.getPlayerCoordinates().posZ;
/*  40: 43 */     double var12 = 1.0D;
/*  41: 44 */     double var14 = 1.0D;
/*  42: 45 */     double var16 = 0.0D;
/*  43: 47 */     if (par2ArrayOfStr.length > var36) {
/*  44: 49 */       var6 = func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[(var36++)]);
/*  45:    */     }
/*  46: 52 */     if (par2ArrayOfStr.length > var36) {
/*  47: 54 */       var8 = func_110665_a(par1ICommandSender, var8, par2ArrayOfStr[(var36++)], 0, 0);
/*  48:    */     }
/*  49: 57 */     if (par2ArrayOfStr.length > var36) {
/*  50: 59 */       var10 = func_110666_a(par1ICommandSender, var10, par2ArrayOfStr[(var36++)]);
/*  51:    */     }
/*  52: 62 */     if (par2ArrayOfStr.length > var36) {
/*  53: 64 */       var12 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[(var36++)], 0.0D, 3.402823466385289E+038D);
/*  54:    */     }
/*  55: 67 */     if (par2ArrayOfStr.length > var36) {
/*  56: 69 */       var14 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[(var36++)], 0.0D, 2.0D);
/*  57:    */     }
/*  58: 72 */     if (par2ArrayOfStr.length > var36) {
/*  59: 74 */       var16 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[(var36++)], 0.0D, 1.0D);
/*  60:    */     }
/*  61: 77 */     double var18 = var12 > 1.0D ? var12 * 16.0D : 16.0D;
/*  62: 78 */     double var20 = var5.getDistance(var6, var8, var10);
/*  63: 80 */     if (var20 > var18)
/*  64:    */     {
/*  65: 82 */       if (var16 <= 0.0D) {
/*  66: 84 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { var5.getCommandSenderName() });
/*  67:    */       }
/*  68: 87 */       double var22 = var6 - var5.posX;
/*  69: 88 */       double var24 = var8 - var5.posY;
/*  70: 89 */       double var26 = var10 - var5.posZ;
/*  71: 90 */       double var28 = Math.sqrt(var22 * var22 + var24 * var24 + var26 * var26);
/*  72: 91 */       double var30 = var5.posX;
/*  73: 92 */       double var32 = var5.posY;
/*  74: 93 */       double var34 = var5.posZ;
/*  75: 95 */       if (var28 > 0.0D)
/*  76:    */       {
/*  77: 97 */         var30 += var22 / var28 * 2.0D;
/*  78: 98 */         var32 += var24 / var28 * 2.0D;
/*  79: 99 */         var34 += var26 / var28 * 2.0D;
/*  80:    */       }
/*  81:102 */       var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var30, var32, var34, (float)var16, (float)var14));
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:106 */       var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var6, var8, var10, (float)var12, (float)var14));
/*  86:    */     }
/*  87:109 */     notifyAdmins(par1ICommandSender, "commands.playsound.success", new Object[] { var4, var5.getCommandSenderName() });
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  91:    */   {
/*  92:118 */     return par2 == 1;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandPlaySound
 * JD-Core Version:    0.7.0.1
 */