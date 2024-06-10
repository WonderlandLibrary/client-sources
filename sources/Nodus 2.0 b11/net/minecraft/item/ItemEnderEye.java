/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockEndPortalFrame;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.item.EntityEnderEye;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.util.MovingObjectPosition;
/*  12:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  13:    */ import net.minecraft.world.ChunkPosition;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class ItemEnderEye
/*  17:    */   extends Item
/*  18:    */ {
/*  19:    */   private static final String __OBFID = "CL_00000026";
/*  20:    */   
/*  21:    */   public ItemEnderEye()
/*  22:    */   {
/*  23: 20 */     setCreativeTab(CreativeTabs.tabMisc);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  27:    */   {
/*  28: 29 */     Block var11 = par3World.getBlock(par4, par5, par6);
/*  29: 30 */     int var12 = par3World.getBlockMetadata(par4, par5, par6);
/*  30: 32 */     if ((par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) && (var11 == Blocks.end_portal_frame) && (!BlockEndPortalFrame.func_150020_b(var12)))
/*  31:    */     {
/*  32: 34 */       if (par3World.isClient) {
/*  33: 36 */         return true;
/*  34:    */       }
/*  35: 40 */       par3World.setBlockMetadataWithNotify(par4, par5, par6, var12 + 4, 2);
/*  36: 41 */       par3World.func_147453_f(par4, par5, par6, Blocks.end_portal_frame);
/*  37: 42 */       par1ItemStack.stackSize -= 1;
/*  38: 45 */       for (int var13 = 0; var13 < 16; var13++)
/*  39:    */       {
/*  40: 47 */         double var14 = par4 + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
/*  41: 48 */         double var16 = par5 + 0.8125F;
/*  42: 49 */         double var18 = par6 + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
/*  43: 50 */         double var20 = 0.0D;
/*  44: 51 */         double var22 = 0.0D;
/*  45: 52 */         double var24 = 0.0D;
/*  46: 53 */         par3World.spawnParticle("smoke", var14, var16, var18, var20, var22, var24);
/*  47:    */       }
/*  48: 56 */       var13 = var12 & 0x3;
/*  49: 57 */       int var26 = 0;
/*  50: 58 */       int var15 = 0;
/*  51: 59 */       boolean var27 = false;
/*  52: 60 */       boolean var17 = true;
/*  53: 61 */       int var28 = net.minecraft.util.Direction.rotateRight[var13];
/*  54: 66 */       for (int var19 = -2; var19 <= 2; var19++)
/*  55:    */       {
/*  56: 68 */         int var29 = par4 + net.minecraft.util.Direction.offsetX[var28] * var19;
/*  57: 69 */         int var21 = par6 + net.minecraft.util.Direction.offsetZ[var28] * var19;
/*  58: 71 */         if (par3World.getBlock(var29, par5, var21) == Blocks.end_portal_frame)
/*  59:    */         {
/*  60: 73 */           if (!BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var29, par5, var21)))
/*  61:    */           {
/*  62: 75 */             var17 = false;
/*  63: 76 */             break;
/*  64:    */           }
/*  65: 79 */           var15 = var19;
/*  66: 81 */           if (!var27)
/*  67:    */           {
/*  68: 83 */             var26 = var19;
/*  69: 84 */             var27 = true;
/*  70:    */           }
/*  71:    */         }
/*  72:    */       }
/*  73: 89 */       if ((var17) && (var15 == var26 + 2))
/*  74:    */       {
/*  75: 91 */         for (var19 = var26; var19 <= var15; var19++)
/*  76:    */         {
/*  77: 93 */           int var29 = par4 + net.minecraft.util.Direction.offsetX[var28] * var19;
/*  78: 94 */           int var21 = par6 + net.minecraft.util.Direction.offsetZ[var28] * var19;
/*  79: 95 */           var29 += net.minecraft.util.Direction.offsetX[var13] * 4;
/*  80: 96 */           var21 += net.minecraft.util.Direction.offsetZ[var13] * 4;
/*  81: 98 */           if ((par3World.getBlock(var29, par5, var21) != Blocks.end_portal_frame) || (!BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var29, par5, var21))))
/*  82:    */           {
/*  83:100 */             var17 = false;
/*  84:101 */             break;
/*  85:    */           }
/*  86:    */         }
/*  87:107 */         for (var19 = var26 - 1; var19 <= var15 + 1; var19 += 4) {
/*  88:109 */           for (int var29 = 1; var29 <= 3; var29++)
/*  89:    */           {
/*  90:111 */             int var21 = par4 + net.minecraft.util.Direction.offsetX[var28] * var19;
/*  91:112 */             int var30 = par6 + net.minecraft.util.Direction.offsetZ[var28] * var19;
/*  92:113 */             var21 += net.minecraft.util.Direction.offsetX[var13] * var29;
/*  93:114 */             var30 += net.minecraft.util.Direction.offsetZ[var13] * var29;
/*  94:116 */             if ((par3World.getBlock(var21, par5, var30) != Blocks.end_portal_frame) || (!BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var21, par5, var30))))
/*  95:    */             {
/*  96:118 */               var17 = false;
/*  97:119 */               break;
/*  98:    */             }
/*  99:    */           }
/* 100:    */         }
/* 101:124 */         if (var17) {
/* 102:126 */           for (var19 = var26; var19 <= var15; var19++) {
/* 103:128 */             for (int var29 = 1; var29 <= 3; var29++)
/* 104:    */             {
/* 105:130 */               int var21 = par4 + net.minecraft.util.Direction.offsetX[var28] * var19;
/* 106:131 */               int var30 = par6 + net.minecraft.util.Direction.offsetZ[var28] * var19;
/* 107:132 */               var21 += net.minecraft.util.Direction.offsetX[var13] * var29;
/* 108:133 */               var30 += net.minecraft.util.Direction.offsetZ[var13] * var29;
/* 109:134 */               par3World.setBlock(var21, par5, var30, Blocks.end_portal, 0, 2);
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:140 */       return true;
/* 115:    */     }
/* 116:145 */     return false;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 120:    */   {
/* 121:154 */     MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);
/* 122:156 */     if ((var4 != null) && (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (par2World.getBlock(var4.blockX, var4.blockY, var4.blockZ) == Blocks.end_portal_frame)) {
/* 123:158 */       return par1ItemStack;
/* 124:    */     }
/* 125:162 */     if (!par2World.isClient)
/* 126:    */     {
/* 127:164 */       ChunkPosition var5 = par2World.findClosestStructure("Stronghold", (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);
/* 128:166 */       if (var5 != null)
/* 129:    */       {
/* 130:168 */         EntityEnderEye var6 = new EntityEnderEye(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY + 1.62D - par3EntityPlayer.yOffset, par3EntityPlayer.posZ);
/* 131:169 */         var6.moveTowards(var5.field_151329_a, var5.field_151327_b, var5.field_151328_c);
/* 132:170 */         par2World.spawnEntityInWorld(var6);
/* 133:171 */         par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 134:172 */         par2World.playAuxSFXAtEntity(null, 1002, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);
/* 135:174 */         if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 136:176 */           par1ItemStack.stackSize -= 1;
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:181 */     return par1ItemStack;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEnderEye
 * JD-Core Version:    0.7.0.1
 */