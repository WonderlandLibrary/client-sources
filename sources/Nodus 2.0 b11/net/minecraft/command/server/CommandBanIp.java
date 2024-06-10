/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.regex.Matcher;
/*   6:    */ import java.util.regex.Pattern;
/*   7:    */ import net.minecraft.command.CommandBase;
/*   8:    */ import net.minecraft.command.ICommandSender;
/*   9:    */ import net.minecraft.command.PlayerNotFoundException;
/*  10:    */ import net.minecraft.command.WrongUsageException;
/*  11:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  12:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  13:    */ import net.minecraft.server.MinecraftServer;
/*  14:    */ import net.minecraft.server.management.BanEntry;
/*  15:    */ import net.minecraft.server.management.BanList;
/*  16:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ 
/*  19:    */ public class CommandBanIp
/*  20:    */   extends CommandBase
/*  21:    */ {
/*  22: 18 */   public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*  23:    */   private static final String __OBFID = "CL_00000139";
/*  24:    */   
/*  25:    */   public String getCommandName()
/*  26:    */   {
/*  27: 23 */     return "ban-ip";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getRequiredPermissionLevel()
/*  31:    */   {
/*  32: 31 */     return 3;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/*  36:    */   {
/*  37: 39 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive()) && (super.canCommandSenderUseCommand(par1ICommandSender));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  41:    */   {
/*  42: 44 */     return "commands.banip.usage";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  46:    */   {
/*  47: 49 */     if ((par2ArrayOfStr.length >= 1) && (par2ArrayOfStr[0].length() > 1))
/*  48:    */     {
/*  49: 51 */       Matcher var3 = field_147211_a.matcher(par2ArrayOfStr[0]);
/*  50: 52 */       IChatComponent var4 = null;
/*  51: 54 */       if (par2ArrayOfStr.length >= 2) {
/*  52: 56 */         var4 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 1);
/*  53:    */       }
/*  54: 59 */       if (var3.matches())
/*  55:    */       {
/*  56: 61 */         func_147210_a(par1ICommandSender, par2ArrayOfStr[0], var4 == null ? null : var4.getUnformattedText());
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 65 */         EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
/*  61: 67 */         if (var5 == null) {
/*  62: 69 */           throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
/*  63:    */         }
/*  64: 72 */         func_147210_a(par1ICommandSender, var5.getPlayerIP(), var4 == null ? null : var4.getUnformattedText());
/*  65:    */       }
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69: 77 */       throw new WrongUsageException("commands.banip.usage", new Object[0]);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  74:    */   {
/*  75: 86 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void func_147210_a(ICommandSender p_147210_1_, String p_147210_2_, String p_147210_3_)
/*  79:    */   {
/*  80: 91 */     BanEntry var4 = new BanEntry(p_147210_2_);
/*  81: 92 */     var4.setBannedBy(p_147210_1_.getCommandSenderName());
/*  82: 94 */     if (p_147210_3_ != null) {
/*  83: 96 */       var4.setBanReason(p_147210_3_);
/*  84:    */     }
/*  85: 99 */     MinecraftServer.getServer().getConfigurationManager().getBannedIPs().put(var4);
/*  86:100 */     List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerList(p_147210_2_);
/*  87:101 */     String[] var6 = new String[var5.size()];
/*  88:102 */     int var7 = 0;
/*  89:    */     EntityPlayerMP var9;
/*  90:105 */     for (Iterator var8 = var5.iterator(); var8.hasNext(); var6[(var7++)] = var9.getCommandSenderName())
/*  91:    */     {
/*  92:107 */       var9 = (EntityPlayerMP)var8.next();
/*  93:108 */       var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
/*  94:    */     }
/*  95:111 */     if (var5.isEmpty()) {
/*  96:113 */       notifyAdmins(p_147210_1_, "commands.banip.success", new Object[] { p_147210_2_ });
/*  97:    */     } else {
/*  98:117 */       notifyAdmins(p_147210_1_, "commands.banip.success.players", new Object[] { p_147210_2_, joinNiceString(var6) });
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandBanIp
 * JD-Core Version:    0.7.0.1
 */