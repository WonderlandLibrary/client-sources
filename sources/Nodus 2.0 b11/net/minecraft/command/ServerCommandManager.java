/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.command.server.CommandAchievement;
/*   6:    */ import net.minecraft.command.server.CommandBanIp;
/*   7:    */ import net.minecraft.command.server.CommandBanPlayer;
/*   8:    */ import net.minecraft.command.server.CommandBlockLogic;
/*   9:    */ import net.minecraft.command.server.CommandBroadcast;
/*  10:    */ import net.minecraft.command.server.CommandDeOp;
/*  11:    */ import net.minecraft.command.server.CommandEmote;
/*  12:    */ import net.minecraft.command.server.CommandListBans;
/*  13:    */ import net.minecraft.command.server.CommandListPlayers;
/*  14:    */ import net.minecraft.command.server.CommandMessage;
/*  15:    */ import net.minecraft.command.server.CommandMessageRaw;
/*  16:    */ import net.minecraft.command.server.CommandOp;
/*  17:    */ import net.minecraft.command.server.CommandPardonIp;
/*  18:    */ import net.minecraft.command.server.CommandPardonPlayer;
/*  19:    */ import net.minecraft.command.server.CommandPublishLocalServer;
/*  20:    */ import net.minecraft.command.server.CommandSaveAll;
/*  21:    */ import net.minecraft.command.server.CommandSaveOff;
/*  22:    */ import net.minecraft.command.server.CommandSaveOn;
/*  23:    */ import net.minecraft.command.server.CommandScoreboard;
/*  24:    */ import net.minecraft.command.server.CommandSetBlock;
/*  25:    */ import net.minecraft.command.server.CommandSetDefaultSpawnpoint;
/*  26:    */ import net.minecraft.command.server.CommandStop;
/*  27:    */ import net.minecraft.command.server.CommandSummon;
/*  28:    */ import net.minecraft.command.server.CommandTeleport;
/*  29:    */ import net.minecraft.command.server.CommandTestFor;
/*  30:    */ import net.minecraft.command.server.CommandTestForBlock;
/*  31:    */ import net.minecraft.command.server.CommandWhitelist;
/*  32:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  33:    */ import net.minecraft.server.MinecraftServer;
/*  34:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  35:    */ import net.minecraft.util.ChatComponentTranslation;
/*  36:    */ import net.minecraft.util.ChatStyle;
/*  37:    */ import net.minecraft.util.EnumChatFormatting;
/*  38:    */ import net.minecraft.world.GameRules;
/*  39:    */ import net.minecraft.world.WorldServer;
/*  40:    */ 
/*  41:    */ public class ServerCommandManager
/*  42:    */   extends CommandHandler
/*  43:    */   implements IAdminCommand
/*  44:    */ {
/*  45:    */   private static final String __OBFID = "CL_00000922";
/*  46:    */   
/*  47:    */   public ServerCommandManager()
/*  48:    */   {
/*  49: 42 */     registerCommand(new CommandTime());
/*  50: 43 */     registerCommand(new CommandGameMode());
/*  51: 44 */     registerCommand(new CommandDifficulty());
/*  52: 45 */     registerCommand(new CommandDefaultGameMode());
/*  53: 46 */     registerCommand(new CommandKill());
/*  54: 47 */     registerCommand(new CommandToggleDownfall());
/*  55: 48 */     registerCommand(new CommandWeather());
/*  56: 49 */     registerCommand(new CommandXP());
/*  57: 50 */     registerCommand(new CommandTeleport());
/*  58: 51 */     registerCommand(new CommandGive());
/*  59: 52 */     registerCommand(new CommandEffect());
/*  60: 53 */     registerCommand(new CommandEnchant());
/*  61: 54 */     registerCommand(new CommandEmote());
/*  62: 55 */     registerCommand(new CommandShowSeed());
/*  63: 56 */     registerCommand(new CommandHelp());
/*  64: 57 */     registerCommand(new CommandDebug());
/*  65: 58 */     registerCommand(new CommandMessage());
/*  66: 59 */     registerCommand(new CommandBroadcast());
/*  67: 60 */     registerCommand(new CommandSetSpawnpoint());
/*  68: 61 */     registerCommand(new CommandSetDefaultSpawnpoint());
/*  69: 62 */     registerCommand(new CommandGameRule());
/*  70: 63 */     registerCommand(new CommandClearInventory());
/*  71: 64 */     registerCommand(new CommandTestFor());
/*  72: 65 */     registerCommand(new CommandSpreadPlayers());
/*  73: 66 */     registerCommand(new CommandPlaySound());
/*  74: 67 */     registerCommand(new CommandScoreboard());
/*  75: 68 */     registerCommand(new CommandAchievement());
/*  76: 69 */     registerCommand(new CommandSummon());
/*  77: 70 */     registerCommand(new CommandSetBlock());
/*  78: 71 */     registerCommand(new CommandTestForBlock());
/*  79: 72 */     registerCommand(new CommandMessageRaw());
/*  80: 74 */     if (MinecraftServer.getServer().isDedicatedServer())
/*  81:    */     {
/*  82: 76 */       registerCommand(new CommandOp());
/*  83: 77 */       registerCommand(new CommandDeOp());
/*  84: 78 */       registerCommand(new CommandStop());
/*  85: 79 */       registerCommand(new CommandSaveAll());
/*  86: 80 */       registerCommand(new CommandSaveOff());
/*  87: 81 */       registerCommand(new CommandSaveOn());
/*  88: 82 */       registerCommand(new CommandBanIp());
/*  89: 83 */       registerCommand(new CommandPardonIp());
/*  90: 84 */       registerCommand(new CommandBanPlayer());
/*  91: 85 */       registerCommand(new CommandListBans());
/*  92: 86 */       registerCommand(new CommandPardonPlayer());
/*  93: 87 */       registerCommand(new CommandServerKick());
/*  94: 88 */       registerCommand(new CommandListPlayers());
/*  95: 89 */       registerCommand(new CommandWhitelist());
/*  96: 90 */       registerCommand(new CommandSetPlayerTimeout());
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100: 94 */       registerCommand(new CommandPublishLocalServer());
/* 101:    */     }
/* 102: 97 */     CommandBase.setAdminCommander(this);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object... par4ArrayOfObj)
/* 106:    */   {
/* 107:106 */     boolean var5 = true;
/* 108:108 */     if (((par1ICommandSender instanceof CommandBlockLogic)) && (!MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"))) {
/* 109:110 */       var5 = false;
/* 110:    */     }
/* 111:113 */     ChatComponentTranslation var6 = new ChatComponentTranslation("chat.type.admin", new Object[] { par1ICommandSender.getCommandSenderName(), new ChatComponentTranslation(par3Str, par4ArrayOfObj) });
/* 112:114 */     var6.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 113:115 */     var6.getChatStyle().setItalic(Boolean.valueOf(true));
/* 114:117 */     if (var5)
/* 115:    */     {
/* 116:119 */       Iterator var7 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
/* 117:121 */       while (var7.hasNext())
/* 118:    */       {
/* 119:123 */         EntityPlayerMP var8 = (EntityPlayerMP)var7.next();
/* 120:125 */         if ((var8 != par1ICommandSender) && (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(var8.getCommandSenderName()))) {
/* 121:127 */           var8.addChatMessage(var6);
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:132 */     if (par1ICommandSender != MinecraftServer.getServer()) {
/* 126:134 */       MinecraftServer.getServer().addChatMessage(var6);
/* 127:    */     }
/* 128:137 */     if ((par2 & 0x1) != 1) {
/* 129:139 */       par1ICommandSender.addChatMessage(new ChatComponentTranslation(par3Str, par4ArrayOfObj));
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.ServerCommandManager
 * JD-Core Version:    0.7.0.1
 */