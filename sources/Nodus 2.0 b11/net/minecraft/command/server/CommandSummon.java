/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Set;
/*   5:    */ import net.minecraft.command.CommandBase;
/*   6:    */ import net.minecraft.command.ICommandSender;
/*   7:    */ import net.minecraft.command.WrongUsageException;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityList;
/*  10:    */ import net.minecraft.entity.EntityLiving;
/*  11:    */ import net.minecraft.nbt.JsonToNBT;
/*  12:    */ import net.minecraft.nbt.NBTBase;
/*  13:    */ import net.minecraft.nbt.NBTException;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.util.ChunkCoordinates;
/*  16:    */ import net.minecraft.util.IChatComponent;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class CommandSummon
/*  20:    */   extends CommandBase
/*  21:    */ {
/*  22:    */   private static final String __OBFID = "CL_00001158";
/*  23:    */   
/*  24:    */   public String getCommandName()
/*  25:    */   {
/*  26: 24 */     return "summon";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getRequiredPermissionLevel()
/*  30:    */   {
/*  31: 32 */     return 2;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  35:    */   {
/*  36: 37 */     return "commands.summon.usage";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  40:    */   {
/*  41: 42 */     if (par2ArrayOfStr.length >= 1)
/*  42:    */     {
/*  43: 44 */       String var3 = par2ArrayOfStr[0];
/*  44: 45 */       double var4 = par1ICommandSender.getPlayerCoordinates().posX + 0.5D;
/*  45: 46 */       double var6 = par1ICommandSender.getPlayerCoordinates().posY;
/*  46: 47 */       double var8 = par1ICommandSender.getPlayerCoordinates().posZ + 0.5D;
/*  47: 49 */       if (par2ArrayOfStr.length >= 4)
/*  48:    */       {
/*  49: 51 */         var4 = func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]);
/*  50: 52 */         var6 = func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[2]);
/*  51: 53 */         var8 = func_110666_a(par1ICommandSender, var8, par2ArrayOfStr[3]);
/*  52:    */       }
/*  53: 56 */       World var10 = par1ICommandSender.getEntityWorld();
/*  54: 58 */       if (!var10.blockExists((int)var4, (int)var6, (int)var8))
/*  55:    */       {
/*  56: 60 */         notifyAdmins(par1ICommandSender, "commands.summon.outOfWorld", new Object[0]);
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 64 */         NBTTagCompound var11 = new NBTTagCompound();
/*  61: 65 */         boolean var12 = false;
/*  62: 67 */         if (par2ArrayOfStr.length >= 5)
/*  63:    */         {
/*  64: 69 */           IChatComponent var13 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 4);
/*  65:    */           try
/*  66:    */           {
/*  67: 73 */             NBTBase var14 = JsonToNBT.func_150315_a(var13.getUnformattedText());
/*  68: 75 */             if (!(var14 instanceof NBTTagCompound))
/*  69:    */             {
/*  70: 77 */               notifyAdmins(par1ICommandSender, "commands.summon.tagError", new Object[] { "Not a valid tag" });
/*  71: 78 */               return;
/*  72:    */             }
/*  73: 81 */             var11 = (NBTTagCompound)var14;
/*  74: 82 */             var12 = true;
/*  75:    */           }
/*  76:    */           catch (NBTException var17)
/*  77:    */           {
/*  78: 86 */             notifyAdmins(par1ICommandSender, "commands.summon.tagError", new Object[] { var17.getMessage() });
/*  79: 87 */             return;
/*  80:    */           }
/*  81:    */         }
/*  82: 91 */         var11.setString("id", var3);
/*  83: 92 */         Entity var18 = EntityList.createEntityFromNBT(var11, var10);
/*  84: 94 */         if (var18 != null)
/*  85:    */         {
/*  86: 96 */           var18.setLocationAndAngles(var4, var6, var8, var18.rotationYaw, var18.rotationPitch);
/*  87: 98 */           if ((!var12) && ((var18 instanceof EntityLiving))) {
/*  88:100 */             ((EntityLiving)var18).onSpawnWithEgg(null);
/*  89:    */           }
/*  90:103 */           var10.spawnEntityInWorld(var18);
/*  91:104 */           Entity var19 = var18;
/*  92:106 */           for (NBTTagCompound var15 = var11; var15.func_150297_b("Riding", 10); var15 = var15.getCompoundTag("Riding"))
/*  93:    */           {
/*  94:108 */             Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), var10);
/*  95:110 */             if (var16 != null)
/*  96:    */             {
/*  97:112 */               var16.setLocationAndAngles(var4, var6, var8, var16.rotationYaw, var16.rotationPitch);
/*  98:113 */               var10.spawnEntityInWorld(var16);
/*  99:114 */               var19.mountEntity(var16);
/* 100:    */             }
/* 101:117 */             var19 = var16;
/* 102:    */           }
/* 103:120 */           notifyAdmins(par1ICommandSender, "commands.summon.success", new Object[0]);
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:124 */           notifyAdmins(par1ICommandSender, "commands.summon.failed", new Object[0]);
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:130 */       throw new WrongUsageException("commands.summon.usage", new Object[0]);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 118:    */   {
/* 119:139 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, func_147182_d()) : null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected String[] func_147182_d()
/* 123:    */   {
/* 124:144 */     return (String[])EntityList.func_151515_b().toArray(new String[0]);
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandSummon
 * JD-Core Version:    0.7.0.1
 */