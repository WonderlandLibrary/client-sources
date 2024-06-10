/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.item.EntityItem;
/*   5:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   6:    */ import net.minecraft.item.Item;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.JsonToNBT;
/*   9:    */ import net.minecraft.nbt.NBTBase;
/*  10:    */ import net.minecraft.nbt.NBTException;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.server.MinecraftServer;
/*  13:    */ import net.minecraft.util.IChatComponent;
/*  14:    */ import net.minecraft.util.RegistryNamespaced;
/*  15:    */ 
/*  16:    */ public class CommandGive
/*  17:    */   extends CommandBase
/*  18:    */ {
/*  19:    */   private static final String __OBFID = "CL_00000502";
/*  20:    */   
/*  21:    */   public String getCommandName()
/*  22:    */   {
/*  23: 20 */     return "give";
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getRequiredPermissionLevel()
/*  27:    */   {
/*  28: 28 */     return 2;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  32:    */   {
/*  33: 33 */     return "commands.give.usage";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  37:    */   {
/*  38: 38 */     if (par2ArrayOfStr.length < 2) {
/*  39: 40 */       throw new WrongUsageException("commands.give.usage", new Object[0]);
/*  40:    */     }
/*  41: 44 */     EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/*  42: 45 */     Item var4 = getItemByText(par1ICommandSender, par2ArrayOfStr[1]);
/*  43: 46 */     int var5 = 1;
/*  44: 47 */     int var6 = 0;
/*  45: 49 */     if (par2ArrayOfStr.length >= 3) {
/*  46: 51 */       var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
/*  47:    */     }
/*  48: 54 */     if (par2ArrayOfStr.length >= 4) {
/*  49: 56 */       var6 = parseInt(par1ICommandSender, par2ArrayOfStr[3]);
/*  50:    */     }
/*  51: 59 */     ItemStack var7 = new ItemStack(var4, var5, var6);
/*  52: 61 */     if (par2ArrayOfStr.length >= 5)
/*  53:    */     {
/*  54: 63 */       String var8 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 4).getUnformattedText();
/*  55:    */       try
/*  56:    */       {
/*  57: 67 */         NBTBase var9 = JsonToNBT.func_150315_a(var8);
/*  58: 69 */         if (!(var9 instanceof NBTTagCompound))
/*  59:    */         {
/*  60: 71 */           notifyAdmins(par1ICommandSender, "commands.give.tagError", new Object[] { "Not a valid tag" });
/*  61: 72 */           return;
/*  62:    */         }
/*  63: 75 */         var7.setTagCompound((NBTTagCompound)var9);
/*  64:    */       }
/*  65:    */       catch (NBTException var10)
/*  66:    */       {
/*  67: 79 */         notifyAdmins(par1ICommandSender, "commands.give.tagError", new Object[] { var10.getMessage() });
/*  68: 80 */         return;
/*  69:    */       }
/*  70:    */     }
/*  71: 84 */     EntityItem var11 = var3.dropPlayerItemWithRandomChoice(var7, false);
/*  72: 85 */     var11.delayBeforeCanPickup = 0;
/*  73: 86 */     var11.func_145797_a(var3.getCommandSenderName());
/*  74: 87 */     notifyAdmins(par1ICommandSender, "commands.give.success", new Object[] { var7.func_151000_E(), Integer.valueOf(var5), var3.getCommandSenderName() });
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  78:    */   {
/*  79: 96 */     return par2ArrayOfStr.length == 2 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Item.itemRegistry.getKeys()) : par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getPlayers()) : null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected String[] getPlayers()
/*  83:    */   {
/*  84:101 */     return MinecraftServer.getServer().getAllUsernames();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/*  88:    */   {
/*  89:109 */     return par2 == 0;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandGive
 * JD-Core Version:    0.7.0.1
 */