/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  5:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  6:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  7:   */ import net.minecraft.inventory.Container;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.server.MinecraftServer;
/* 10:   */ import net.minecraft.util.RegistryNamespaced;
/* 11:   */ 
/* 12:   */ public class CommandClearInventory
/* 13:   */   extends CommandBase
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000218";
/* 16:   */   
/* 17:   */   public String getCommandName()
/* 18:   */   {
/* 19:14 */     return "clear";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 23:   */   {
/* 24:19 */     return "commands.clear.usage";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getRequiredPermissionLevel()
/* 28:   */   {
/* 29:27 */     return 2;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 33:   */   {
/* 34:32 */     EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/* 35:33 */     Item var4 = par2ArrayOfStr.length >= 2 ? getItemByText(par1ICommandSender, par2ArrayOfStr[1]) : null;
/* 36:34 */     int var5 = par2ArrayOfStr.length >= 3 ? parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;
/* 37:36 */     if ((par2ArrayOfStr.length >= 2) && (var4 == null)) {
/* 38:38 */       throw new CommandException("commands.clear.failure", new Object[] { var3.getCommandSenderName() });
/* 39:   */     }
/* 40:42 */     int var6 = var3.inventory.clearInventory(var4, var5);
/* 41:43 */     var3.inventoryContainer.detectAndSendChanges();
/* 42:45 */     if (!var3.capabilities.isCreativeMode) {
/* 43:47 */       var3.updateHeldItem();
/* 44:   */     }
/* 45:50 */     if (var6 == 0) {
/* 46:52 */       throw new CommandException("commands.clear.failure", new Object[] { var3.getCommandSenderName() });
/* 47:   */     }
/* 48:56 */     notifyAdmins(par1ICommandSender, "commands.clear.success", new Object[] { var3.getCommandSenderName(), Integer.valueOf(var6) });
/* 49:   */   }
/* 50:   */   
/* 51:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 52:   */   {
/* 53:66 */     return par2ArrayOfStr.length == 2 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Item.itemRegistry.getKeys()) : par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, func_147209_d()) : null;
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected String[] func_147209_d()
/* 57:   */   {
/* 58:71 */     return MinecraftServer.getServer().getAllUsernames();
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 62:   */   {
/* 63:79 */     return par2 == 0;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandClearInventory
 * JD-Core Version:    0.7.0.1
 */