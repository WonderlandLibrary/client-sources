/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.enchantment.Enchantment;
/*   5:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.nbt.NBTTagList;
/*   9:    */ import net.minecraft.server.MinecraftServer;
/*  10:    */ 
/*  11:    */ public class CommandEnchant
/*  12:    */   extends CommandBase
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00000377";
/*  15:    */   
/*  16:    */   public String getCommandName()
/*  17:    */   {
/*  18: 16 */     return "enchant";
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getRequiredPermissionLevel()
/*  22:    */   {
/*  23: 24 */     return 2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  27:    */   {
/*  28: 29 */     return "commands.enchant.usage";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  32:    */   {
/*  33: 34 */     if (par2ArrayOfStr.length < 2) {
/*  34: 36 */       throw new WrongUsageException("commands.enchant.usage", new Object[0]);
/*  35:    */     }
/*  36: 40 */     EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/*  37: 41 */     int var4 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 0, Enchantment.enchantmentsList.length - 1);
/*  38: 42 */     int var5 = 1;
/*  39: 43 */     ItemStack var6 = var3.getCurrentEquippedItem();
/*  40: 45 */     if (var6 == null) {
/*  41: 47 */       throw new CommandException("commands.enchant.noItem", new Object[0]);
/*  42:    */     }
/*  43: 51 */     Enchantment var7 = Enchantment.enchantmentsList[var4];
/*  44: 53 */     if (var7 == null) {
/*  45: 55 */       throw new NumberInvalidException("commands.enchant.notFound", new Object[] { Integer.valueOf(var4) });
/*  46:    */     }
/*  47: 57 */     if (!var7.canApply(var6)) {
/*  48: 59 */       throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*  49:    */     }
/*  50: 63 */     if (par2ArrayOfStr.length >= 3) {
/*  51: 65 */       var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], var7.getMinLevel(), var7.getMaxLevel());
/*  52:    */     }
/*  53: 68 */     if (var6.hasTagCompound())
/*  54:    */     {
/*  55: 70 */       NBTTagList var8 = var6.getEnchantmentTagList();
/*  56: 72 */       if (var8 != null) {
/*  57: 74 */         for (int var9 = 0; var9 < var8.tagCount(); var9++)
/*  58:    */         {
/*  59: 76 */           short var10 = var8.getCompoundTagAt(var9).getShort("id");
/*  60: 78 */           if (Enchantment.enchantmentsList[var10] != null)
/*  61:    */           {
/*  62: 80 */             Enchantment var11 = Enchantment.enchantmentsList[var10];
/*  63: 82 */             if (!var11.canApplyTogether(var7)) {
/*  64: 84 */               throw new CommandException("commands.enchant.cantCombine", new Object[] { var7.getTranslatedName(var5), var11.getTranslatedName(var8.getCompoundTagAt(var9).getShort("lvl")) });
/*  65:    */             }
/*  66:    */           }
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70: 91 */     var6.addEnchantment(var7, var5);
/*  71: 92 */     notifyAdmins(par1ICommandSender, "commands.enchant.success", new Object[0]);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  75:    */   {
/*  76:103 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getListOfPlayers()) : null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected String[] getListOfPlayers()
/*  80:    */   {
/*  81:108 */     return MinecraftServer.getServer().getAllUsernames();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  85:    */   {
/*  86:116 */     return par2 == 0;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandEnchant
 * JD-Core Version:    0.7.0.1
 */