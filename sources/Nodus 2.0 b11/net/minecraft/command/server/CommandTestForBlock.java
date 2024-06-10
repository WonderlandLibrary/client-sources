/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Set;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.command.CommandBase;
/*   8:    */ import net.minecraft.command.CommandException;
/*   9:    */ import net.minecraft.command.ICommandSender;
/*  10:    */ import net.minecraft.command.NumberInvalidException;
/*  11:    */ import net.minecraft.command.WrongUsageException;
/*  12:    */ import net.minecraft.nbt.JsonToNBT;
/*  13:    */ import net.minecraft.nbt.NBTBase;
/*  14:    */ import net.minecraft.nbt.NBTException;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.util.ChatComponentTranslation;
/*  18:    */ import net.minecraft.util.ChunkCoordinates;
/*  19:    */ import net.minecraft.util.IChatComponent;
/*  20:    */ import net.minecraft.util.MathHelper;
/*  21:    */ import net.minecraft.util.RegistryNamespaced;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public class CommandTestForBlock
/*  25:    */   extends CommandBase
/*  26:    */ {
/*  27:    */   private static final String __OBFID = "CL_00001181";
/*  28:    */   
/*  29:    */   public String getCommandName()
/*  30:    */   {
/*  31: 26 */     return "testforblock";
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getRequiredPermissionLevel()
/*  35:    */   {
/*  36: 34 */     return 2;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  40:    */   {
/*  41: 39 */     return "commands.testforblock.usage";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  45:    */   {
/*  46: 44 */     if (par2ArrayOfStr.length >= 4)
/*  47:    */     {
/*  48: 46 */       int var3 = par1ICommandSender.getPlayerCoordinates().posX;
/*  49: 47 */       int var4 = par1ICommandSender.getPlayerCoordinates().posY;
/*  50: 48 */       int var5 = par1ICommandSender.getPlayerCoordinates().posZ;
/*  51: 49 */       var3 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var3, par2ArrayOfStr[0]));
/*  52: 50 */       var4 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]));
/*  53: 51 */       var5 = MathHelper.floor_double(func_110666_a(par1ICommandSender, var5, par2ArrayOfStr[2]));
/*  54: 52 */       Block var6 = Block.getBlockFromName(par2ArrayOfStr[3]);
/*  55: 54 */       if (var6 == null) {
/*  56: 56 */         throw new NumberInvalidException("commands.setblock.notFound", new Object[] { par2ArrayOfStr[3] });
/*  57:    */       }
/*  58: 60 */       int var7 = -1;
/*  59: 62 */       if (par2ArrayOfStr.length >= 5) {
/*  60: 64 */         var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[4], -1, 15);
/*  61:    */       }
/*  62: 67 */       World var8 = par1ICommandSender.getEntityWorld();
/*  63: 69 */       if (!var8.blockExists(var3, var4, var5)) {
/*  64: 71 */         throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*  65:    */       }
/*  66: 75 */       NBTTagCompound var9 = new NBTTagCompound();
/*  67: 76 */       boolean var10 = false;
/*  68: 78 */       if ((par2ArrayOfStr.length >= 6) && (var6.hasTileEntity()))
/*  69:    */       {
/*  70: 80 */         String var11 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 5).getUnformattedText();
/*  71:    */         try
/*  72:    */         {
/*  73: 84 */           NBTBase var12 = JsonToNBT.func_150315_a(var11);
/*  74: 86 */           if (!(var12 instanceof NBTTagCompound)) {
/*  75: 88 */             throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
/*  76:    */           }
/*  77: 91 */           var9 = (NBTTagCompound)var12;
/*  78: 92 */           var10 = true;
/*  79:    */         }
/*  80:    */         catch (NBTException var14)
/*  81:    */         {
/*  82: 96 */           throw new CommandException("commands.setblock.tagError", new Object[] { var14.getMessage() });
/*  83:    */         }
/*  84:    */       }
/*  85:100 */       Block var15 = var8.getBlock(var3, var4, var5);
/*  86:102 */       if (var15 != var6) {
/*  87:104 */         throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5), var15.getLocalizedName(), var6.getLocalizedName() });
/*  88:    */       }
/*  89:108 */       if (var7 > -1)
/*  90:    */       {
/*  91:110 */         int var16 = var8.getBlockMetadata(var3, var4, var5);
/*  92:112 */         if (var16 != var7) {
/*  93:114 */           throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var16), Integer.valueOf(var7) });
/*  94:    */         }
/*  95:    */       }
/*  96:118 */       if (var10)
/*  97:    */       {
/*  98:120 */         TileEntity var17 = var8.getTileEntity(var3, var4, var5);
/*  99:122 */         if (var17 == null) {
/* 100:124 */           throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5) });
/* 101:    */         }
/* 102:127 */         NBTTagCompound var13 = new NBTTagCompound();
/* 103:128 */         var17.writeToNBT(var13);
/* 104:130 */         if (!func_147181_a(var9, var13)) {
/* 105:132 */           throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5) });
/* 106:    */         }
/* 107:    */       }
/* 108:136 */       par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.testforblock.success", new Object[] { Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5) }));
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:143 */       throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean func_147181_a(NBTBase p_147181_1_, NBTBase p_147181_2_)
/* 117:    */   {
/* 118:149 */     if (p_147181_1_ == p_147181_2_) {
/* 119:151 */       return true;
/* 120:    */     }
/* 121:153 */     if (p_147181_1_ == null) {
/* 122:155 */       return true;
/* 123:    */     }
/* 124:157 */     if (p_147181_2_ == null) {
/* 125:159 */       return false;
/* 126:    */     }
/* 127:161 */     if (!p_147181_1_.getClass().equals(p_147181_2_.getClass())) {
/* 128:163 */       return false;
/* 129:    */     }
/* 130:165 */     if ((p_147181_1_ instanceof NBTTagCompound))
/* 131:    */     {
/* 132:167 */       NBTTagCompound var3 = (NBTTagCompound)p_147181_1_;
/* 133:168 */       NBTTagCompound var4 = (NBTTagCompound)p_147181_2_;
/* 134:169 */       Iterator var5 = var3.func_150296_c().iterator();
/* 135:    */       String var6;
/* 136:    */       NBTBase var7;
/* 137:    */       do
/* 138:    */       {
/* 139:175 */         if (!var5.hasNext()) {
/* 140:177 */           return true;
/* 141:    */         }
/* 142:180 */         var6 = (String)var5.next();
/* 143:181 */         var7 = var3.getTag(var6);
/* 144:183 */       } while (func_147181_a(var7, var4.getTag(var6)));
/* 145:185 */       return false;
/* 146:    */     }
/* 147:189 */     return p_147181_1_.equals(p_147181_2_);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 151:    */   {
/* 152:198 */     return par2ArrayOfStr.length == 4 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()) : null;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandTestForBlock
 * JD-Core Version:    0.7.0.1
 */