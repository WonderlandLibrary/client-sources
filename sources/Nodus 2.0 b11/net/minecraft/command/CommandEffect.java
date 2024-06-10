/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   6:    */ import net.minecraft.potion.Potion;
/*   7:    */ import net.minecraft.potion.PotionEffect;
/*   8:    */ import net.minecraft.server.MinecraftServer;
/*   9:    */ import net.minecraft.util.ChatComponentTranslation;
/*  10:    */ 
/*  11:    */ public class CommandEffect
/*  12:    */   extends CommandBase
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00000323";
/*  15:    */   
/*  16:    */   public String getCommandName()
/*  17:    */   {
/*  18: 16 */     return "effect";
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getRequiredPermissionLevel()
/*  22:    */   {
/*  23: 24 */     return 2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  27:    */   {
/*  28: 29 */     return "commands.effect.usage";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  32:    */   {
/*  33: 34 */     if (par2ArrayOfStr.length < 2) {
/*  34: 36 */       throw new WrongUsageException("commands.effect.usage", new Object[0]);
/*  35:    */     }
/*  36: 40 */     EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/*  37: 42 */     if (par2ArrayOfStr[1].equals("clear"))
/*  38:    */     {
/*  39: 44 */       if (var3.getActivePotionEffects().isEmpty()) {
/*  40: 46 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { var3.getCommandSenderName() });
/*  41:    */       }
/*  42: 49 */       var3.clearActivePotions();
/*  43: 50 */       notifyAdmins(par1ICommandSender, "commands.effect.success.removed.all", new Object[] { var3.getCommandSenderName() });
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 54 */       int var4 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1);
/*  48: 55 */       int var5 = 600;
/*  49: 56 */       int var6 = 30;
/*  50: 57 */       int var7 = 0;
/*  51: 59 */       if ((var4 < 0) || (var4 >= Potion.potionTypes.length) || (Potion.potionTypes[var4] == null)) {
/*  52: 61 */         throw new NumberInvalidException("commands.effect.notFound", new Object[] { Integer.valueOf(var4) });
/*  53:    */       }
/*  54: 64 */       if (par2ArrayOfStr.length >= 3)
/*  55:    */       {
/*  56: 66 */         var6 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 0, 1000000);
/*  57: 68 */         if (Potion.potionTypes[var4].isInstant()) {
/*  58: 70 */           var5 = var6;
/*  59:    */         } else {
/*  60: 74 */           var5 = var6 * 20;
/*  61:    */         }
/*  62:    */       }
/*  63: 77 */       else if (Potion.potionTypes[var4].isInstant())
/*  64:    */       {
/*  65: 79 */         var5 = 1;
/*  66:    */       }
/*  67: 82 */       if (par2ArrayOfStr.length >= 4) {
/*  68: 84 */         var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[3], 0, 255);
/*  69:    */       }
/*  70: 87 */       if (var6 == 0)
/*  71:    */       {
/*  72: 89 */         if (!var3.isPotionActive(var4)) {
/*  73: 91 */           throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName() });
/*  74:    */         }
/*  75: 94 */         var3.removePotionEffect(var4);
/*  76: 95 */         notifyAdmins(par1ICommandSender, "commands.effect.success.removed", new Object[] { new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName() });
/*  77:    */       }
/*  78:    */       else
/*  79:    */       {
/*  80: 99 */         PotionEffect var8 = new PotionEffect(var4, var5, var7);
/*  81:100 */         var3.addPotionEffect(var8);
/*  82:101 */         notifyAdmins(par1ICommandSender, "commands.effect.success", new Object[] { new ChatComponentTranslation(var8.getEffectName(), new Object[0]), Integer.valueOf(var4), Integer.valueOf(var7), var3.getCommandSenderName(), Integer.valueOf(var6) });
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  88:    */   {
/*  89:112 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getAllUsernames()) : null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected String[] getAllUsernames()
/*  93:    */   {
/*  94:117 */     return MinecraftServer.getServer().getAllUsernames();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  98:    */   {
/*  99:125 */     return par2 == 0;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandEffect
 * JD-Core Version:    0.7.0.1
 */