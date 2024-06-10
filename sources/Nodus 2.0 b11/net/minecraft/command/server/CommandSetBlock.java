/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.command.CommandBase;
/*   6:    */ import net.minecraft.command.CommandException;
/*   7:    */ import net.minecraft.command.ICommandSender;
/*   8:    */ import net.minecraft.command.WrongUsageException;
/*   9:    */ import net.minecraft.nbt.JsonToNBT;
/*  10:    */ import net.minecraft.nbt.NBTBase;
/*  11:    */ import net.minecraft.nbt.NBTException;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.util.ChunkCoordinates;
/*  15:    */ import net.minecraft.util.IChatComponent;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import net.minecraft.util.RegistryNamespaced;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class CommandSetBlock
/*  21:    */   extends CommandBase
/*  22:    */ {
/*  23:    */   private static final String __OBFID = "CL_00000949";
/*  24:    */   
/*  25:    */   public String getCommandName()
/*  26:    */   {
/*  27: 23 */     return "setblock";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getRequiredPermissionLevel()
/*  31:    */   {
/*  32: 31 */     return 2;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  36:    */   {
/*  37: 36 */     return "commands.setblock.usage";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  41:    */   {
/*  42: 41 */     if (par2ArrayOfStr.length >= 4)
/*  43:    */     {
/*  44: 43 */       int var3 = par1ICommandSender.getPlayerCoordinates().posX;
/*  45: 44 */       int var4 = par1ICommandSender.getPlayerCoordinates().posY;
/*  46: 45 */       int var5 = par1ICommandSender.getPlayerCoordinates().posZ;
/*  47: 46 */       var3 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var3, par2ArrayOfStr[0]));
/*  48: 47 */       var4 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]));
/*  49: 48 */       var5 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var5, par2ArrayOfStr[2]));
/*  50: 49 */       Block var6 = CommandBase.getBlockByText(par1ICommandSender, par2ArrayOfStr[3]);
/*  51: 50 */       int var7 = 0;
/*  52: 52 */       if (par2ArrayOfStr.length >= 5) {
/*  53: 54 */         var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[4], 0, 15);
/*  54:    */       }
/*  55: 57 */       World var8 = par1ICommandSender.getEntityWorld();
/*  56: 59 */       if (!var8.blockExists(var3, var4, var5)) {
/*  57: 61 */         throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*  58:    */       }
/*  59: 65 */       NBTTagCompound var9 = new NBTTagCompound();
/*  60: 66 */       boolean var10 = false;
/*  61: 68 */       if ((par2ArrayOfStr.length >= 7) && (var6.hasTileEntity()))
/*  62:    */       {
/*  63: 70 */         String var11 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 6).getUnformattedText();
/*  64:    */         try
/*  65:    */         {
/*  66: 74 */           NBTBase var12 = JsonToNBT.func_150315_a(var11);
/*  67: 76 */           if (!(var12 instanceof NBTTagCompound)) {
/*  68: 78 */             throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
/*  69:    */           }
/*  70: 81 */           var9 = (NBTTagCompound)var12;
/*  71: 82 */           var10 = true;
/*  72:    */         }
/*  73:    */         catch (NBTException var13)
/*  74:    */         {
/*  75: 86 */           throw new CommandException("commands.setblock.tagError", new Object[] { var13.getMessage() });
/*  76:    */         }
/*  77:    */       }
/*  78: 90 */       if (par2ArrayOfStr.length >= 6) {
/*  79: 92 */         if (par2ArrayOfStr[5].equals("destroy")) {
/*  80: 94 */           var8.func_147480_a(var3, var4, var5, true);
/*  81: 96 */         } else if ((par2ArrayOfStr[5].equals("keep")) && (!var8.isAirBlock(var3, var4, var5))) {
/*  82: 98 */           throw new CommandException("commands.setblock.noChange", new Object[0]);
/*  83:    */         }
/*  84:    */       }
/*  85:102 */       if (!var8.setBlock(var3, var4, var5, var6, var7, 3)) {
/*  86:104 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*  87:    */       }
/*  88:108 */       if (var10)
/*  89:    */       {
/*  90:110 */         TileEntity var14 = var8.getTileEntity(var3, var4, var5);
/*  91:112 */         if (var14 != null)
/*  92:    */         {
/*  93:114 */           var9.setInteger("x", var3);
/*  94:115 */           var9.setInteger("y", var4);
/*  95:116 */           var9.setInteger("z", var5);
/*  96:117 */           var14.readFromNBT(var9);
/*  97:    */         }
/*  98:    */       }
/*  99:121 */       notifyAdmins(par1ICommandSender, "commands.setblock.success", new Object[0]);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:127 */       throw new WrongUsageException("commands.setblock.usage", new Object[0]);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 108:    */   {
/* 109:136 */     return par2ArrayOfStr.length == 6 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "replace", "destroy", "keep" }) : par2ArrayOfStr.length == 4 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()) : null;
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandSetBlock
 * JD-Core Version:    0.7.0.1
 */