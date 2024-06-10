/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  12:    */ import net.minecraft.util.ChatComponentTranslation;
/*  13:    */ import net.minecraft.util.ChatStyle;
/*  14:    */ import net.minecraft.util.EnumChatFormatting;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ 
/*  18:    */ public class CommandHandler
/*  19:    */   implements ICommandManager
/*  20:    */ {
/*  21: 19 */   private static final Logger logger = ;
/*  22: 22 */   private final Map commandMap = new HashMap();
/*  23: 25 */   private final Set commandSet = new HashSet();
/*  24:    */   private static final String __OBFID = "CL_00001765";
/*  25:    */   
/*  26:    */   public int executeCommand(ICommandSender par1ICommandSender, String par2Str)
/*  27:    */   {
/*  28: 30 */     par2Str = par2Str.trim();
/*  29: 32 */     if (par2Str.startsWith("/")) {
/*  30: 34 */       par2Str = par2Str.substring(1);
/*  31:    */     }
/*  32: 37 */     String[] var3 = par2Str.split(" ");
/*  33: 38 */     String var4 = var3[0];
/*  34: 39 */     var3 = dropFirstString(var3);
/*  35: 40 */     ICommand var5 = (ICommand)this.commandMap.get(var4);
/*  36: 41 */     int var6 = getUsernameIndex(var5, var3);
/*  37: 42 */     int var7 = 0;
/*  38:    */     try
/*  39:    */     {
/*  40: 47 */       if (var5 == null) {
/*  41: 49 */         throw new CommandNotFoundException();
/*  42:    */       }
/*  43: 52 */       if (var5.canCommandSenderUseCommand(par1ICommandSender))
/*  44:    */       {
/*  45: 54 */         if (var6 > -1)
/*  46:    */         {
/*  47: 56 */           EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(par1ICommandSender, var3[var6]);
/*  48: 57 */           String var21 = var3[var6];
/*  49: 58 */           EntityPlayerMP[] var10 = var8;
/*  50: 59 */           int var11 = var8.length;
/*  51: 61 */           for (int var12 = 0; var12 < var11; var12++)
/*  52:    */           {
/*  53: 63 */             EntityPlayerMP var13 = var10[var12];
/*  54: 64 */             var3[var6] = var13.getCommandSenderName();
/*  55:    */             try
/*  56:    */             {
/*  57: 68 */               var5.processCommand(par1ICommandSender, var3);
/*  58: 69 */               var7++;
/*  59:    */             }
/*  60:    */             catch (CommandException var16)
/*  61:    */             {
/*  62: 73 */               ChatComponentTranslation var15 = new ChatComponentTranslation(var16.getMessage(), var16.getErrorOjbects());
/*  63: 74 */               var15.getChatStyle().setColor(EnumChatFormatting.RED);
/*  64: 75 */               par1ICommandSender.addChatMessage(var15);
/*  65:    */             }
/*  66:    */           }
/*  67: 79 */           var3[var6] = var21;
/*  68:    */         }
/*  69:    */         else
/*  70:    */         {
/*  71: 83 */           var5.processCommand(par1ICommandSender, var3);
/*  72: 84 */           var7++;
/*  73:    */         }
/*  74:    */       }
/*  75:    */       else
/*  76:    */       {
/*  77: 89 */         ChatComponentTranslation var20 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
/*  78: 90 */         var20.getChatStyle().setColor(EnumChatFormatting.RED);
/*  79: 91 */         par1ICommandSender.addChatMessage(var20);
/*  80:    */       }
/*  81:    */     }
/*  82:    */     catch (WrongUsageException var17)
/*  83:    */     {
/*  84: 96 */       ChatComponentTranslation var9 = new ChatComponentTranslation("commands.generic.usage", new Object[] { new ChatComponentTranslation(var17.getMessage(), var17.getErrorOjbects()) });
/*  85: 97 */       var9.getChatStyle().setColor(EnumChatFormatting.RED);
/*  86: 98 */       par1ICommandSender.addChatMessage(var9);
/*  87:    */     }
/*  88:    */     catch (CommandException var18)
/*  89:    */     {
/*  90:102 */       ChatComponentTranslation var9 = new ChatComponentTranslation(var18.getMessage(), var18.getErrorOjbects());
/*  91:103 */       var9.getChatStyle().setColor(EnumChatFormatting.RED);
/*  92:104 */       par1ICommandSender.addChatMessage(var9);
/*  93:    */     }
/*  94:    */     catch (Throwable var19)
/*  95:    */     {
/*  96:108 */       ChatComponentTranslation var9 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
/*  97:109 */       var9.getChatStyle().setColor(EnumChatFormatting.RED);
/*  98:110 */       par1ICommandSender.addChatMessage(var9);
/*  99:111 */       logger.error("Couldn't process command", var19);
/* 100:    */     }
/* 101:114 */     return var7;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public ICommand registerCommand(ICommand par1ICommand)
/* 105:    */   {
/* 106:122 */     List var2 = par1ICommand.getCommandAliases();
/* 107:123 */     this.commandMap.put(par1ICommand.getCommandName(), par1ICommand);
/* 108:124 */     this.commandSet.add(par1ICommand);
/* 109:126 */     if (var2 != null)
/* 110:    */     {
/* 111:128 */       Iterator var3 = var2.iterator();
/* 112:130 */       while (var3.hasNext())
/* 113:    */       {
/* 114:132 */         String var4 = (String)var3.next();
/* 115:133 */         ICommand var5 = (ICommand)this.commandMap.get(var4);
/* 116:135 */         if ((var5 == null) || (!var5.getCommandName().equals(var4))) {
/* 117:137 */           this.commandMap.put(var4, par1ICommand);
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:142 */     return par1ICommand;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static String[] dropFirstString(String[] par0ArrayOfStr)
/* 125:    */   {
/* 126:150 */     String[] var1 = new String[par0ArrayOfStr.length - 1];
/* 127:152 */     for (int var2 = 1; var2 < par0ArrayOfStr.length; var2++) {
/* 128:154 */       var1[(var2 - 1)] = par0ArrayOfStr[var2];
/* 129:    */     }
/* 130:157 */     return var1;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List getPossibleCommands(ICommandSender par1ICommandSender, String par2Str)
/* 134:    */   {
/* 135:165 */     String[] var3 = par2Str.split(" ", -1);
/* 136:166 */     String var4 = var3[0];
/* 137:168 */     if (var3.length == 1)
/* 138:    */     {
/* 139:170 */       ArrayList var8 = new ArrayList();
/* 140:171 */       Iterator var6 = this.commandMap.entrySet().iterator();
/* 141:173 */       while (var6.hasNext())
/* 142:    */       {
/* 143:175 */         Map.Entry var7 = (Map.Entry)var6.next();
/* 144:177 */         if ((CommandBase.doesStringStartWith(var4, (String)var7.getKey())) && (((ICommand)var7.getValue()).canCommandSenderUseCommand(par1ICommandSender))) {
/* 145:179 */           var8.add(var7.getKey());
/* 146:    */         }
/* 147:    */       }
/* 148:183 */       return var8;
/* 149:    */     }
/* 150:187 */     if (var3.length > 1)
/* 151:    */     {
/* 152:189 */       ICommand var5 = (ICommand)this.commandMap.get(var4);
/* 153:191 */       if (var5 != null) {
/* 154:193 */         return var5.addTabCompletionOptions(par1ICommandSender, dropFirstString(var3));
/* 155:    */       }
/* 156:    */     }
/* 157:197 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List getPossibleCommands(ICommandSender par1ICommandSender)
/* 161:    */   {
/* 162:206 */     ArrayList var2 = new ArrayList();
/* 163:207 */     Iterator var3 = this.commandSet.iterator();
/* 164:209 */     while (var3.hasNext())
/* 165:    */     {
/* 166:211 */       ICommand var4 = (ICommand)var3.next();
/* 167:213 */       if (var4.canCommandSenderUseCommand(par1ICommandSender)) {
/* 168:215 */         var2.add(var4);
/* 169:    */       }
/* 170:    */     }
/* 171:219 */     return var2;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Map getCommands()
/* 175:    */   {
/* 176:227 */     return this.commandMap;
/* 177:    */   }
/* 178:    */   
/* 179:    */   private int getUsernameIndex(ICommand par1ICommand, String[] par2ArrayOfStr)
/* 180:    */   {
/* 181:235 */     if (par1ICommand == null) {
/* 182:237 */       return -1;
/* 183:    */     }
/* 184:241 */     for (int var3 = 0; var3 < par2ArrayOfStr.length; var3++) {
/* 185:243 */       if ((par1ICommand.isUsernameIndex(par2ArrayOfStr, var3)) && (PlayerSelector.matchesMultiplePlayers(par2ArrayOfStr[var3]))) {
/* 186:245 */         return var3;
/* 187:    */       }
/* 188:    */     }
/* 189:249 */     return -1;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandHandler
 * JD-Core Version:    0.7.0.1
 */