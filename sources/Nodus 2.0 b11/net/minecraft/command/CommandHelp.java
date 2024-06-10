/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.event.ClickEvent;
/*   9:    */ import net.minecraft.event.ClickEvent.Action;
/*  10:    */ import net.minecraft.server.MinecraftServer;
/*  11:    */ import net.minecraft.util.ChatComponentTranslation;
/*  12:    */ import net.minecraft.util.ChatStyle;
/*  13:    */ import net.minecraft.util.EnumChatFormatting;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ 
/*  16:    */ public class CommandHelp
/*  17:    */   extends CommandBase
/*  18:    */ {
/*  19:    */   private static final String __OBFID = "CL_00000529";
/*  20:    */   
/*  21:    */   public String getCommandName()
/*  22:    */   {
/*  23: 20 */     return "help";
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getRequiredPermissionLevel()
/*  27:    */   {
/*  28: 28 */     return 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  32:    */   {
/*  33: 33 */     return "commands.help.usage";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List getCommandAliases()
/*  37:    */   {
/*  38: 38 */     return Arrays.asList(new String[] { "?" });
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  42:    */   {
/*  43: 43 */     List var3 = getSortedPossibleCommands(par1ICommandSender);
/*  44: 44 */     byte var4 = 7;
/*  45: 45 */     int var5 = (var3.size() - 1) / var4;
/*  46: 46 */     boolean var6 = false;
/*  47:    */     try
/*  48:    */     {
/*  49: 51 */       var13 = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(par1ICommandSender, par2ArrayOfStr[0], 1, var5 + 1) - 1;
/*  50:    */     }
/*  51:    */     catch (NumberInvalidException var12)
/*  52:    */     {
/*  53:    */       int var13;
/*  54: 55 */       Map var8 = getCommands();
/*  55: 56 */       ICommand var9 = (ICommand)var8.get(par2ArrayOfStr[0]);
/*  56: 58 */       if (var9 != null) {
/*  57: 60 */         throw new WrongUsageException(var9.getCommandUsage(par1ICommandSender), new Object[0]);
/*  58:    */       }
/*  59: 63 */       if (MathHelper.parseIntWithDefault(par2ArrayOfStr[0], -1) != -1) {
/*  60: 65 */         throw var12;
/*  61:    */       }
/*  62: 68 */       throw new CommandNotFoundException();
/*  63:    */     }
/*  64:    */     int var13;
/*  65: 71 */     int var7 = Math.min((var13 + 1) * var4, var3.size());
/*  66: 72 */     ChatComponentTranslation var14 = new ChatComponentTranslation("commands.help.header", new Object[] { Integer.valueOf(var13 + 1), Integer.valueOf(var5 + 1) });
/*  67: 73 */     var14.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  68: 74 */     par1ICommandSender.addChatMessage(var14);
/*  69: 76 */     for (int var15 = var13 * var4; var15 < var7; var15++)
/*  70:    */     {
/*  71: 78 */       ICommand var10 = (ICommand)var3.get(var15);
/*  72: 79 */       ChatComponentTranslation var11 = new ChatComponentTranslation(var10.getCommandUsage(par1ICommandSender), new Object[0]);
/*  73: 80 */       var11.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + var10.getCommandName() + " "));
/*  74: 81 */       par1ICommandSender.addChatMessage(var11);
/*  75:    */     }
/*  76: 84 */     if ((var13 == 0) && ((par1ICommandSender instanceof EntityPlayer)))
/*  77:    */     {
/*  78: 86 */       ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
/*  79: 87 */       var16.getChatStyle().setColor(EnumChatFormatting.GREEN);
/*  80: 88 */       par1ICommandSender.addChatMessage(var16);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected List getSortedPossibleCommands(ICommandSender par1ICommandSender)
/*  85:    */   {
/*  86: 97 */     List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(par1ICommandSender);
/*  87: 98 */     Collections.sort(var2);
/*  88: 99 */     return var2;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Map getCommands()
/*  92:    */   {
/*  93:104 */     return MinecraftServer.getServer().getCommandManager().getCommands();
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandHelp
 * JD-Core Version:    0.7.0.1
 */