/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   9:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.util.MovingObjectPosition;
/*  13:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ 
/*  17:    */ public class ItemBucket
/*  18:    */   extends Item
/*  19:    */ {
/*  20:    */   public Block isFull;
/*  21:    */   private static final String __OBFID = "CL_00000000";
/*  22:    */   
/*  23:    */   public ItemBucket(Block p_i45331_1_)
/*  24:    */   {
/*  25: 20 */     this.maxStackSize = 1;
/*  26: 21 */     this.isFull = p_i45331_1_;
/*  27: 22 */     setCreativeTab(CreativeTabs.tabMisc);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  31:    */   {
/*  32: 30 */     boolean var4 = this.isFull == Blocks.air;
/*  33: 31 */     MovingObjectPosition var5 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var4);
/*  34: 33 */     if (var5 == null) {
/*  35: 35 */       return par1ItemStack;
/*  36:    */     }
/*  37: 39 */     if (var5.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/*  38:    */     {
/*  39: 41 */       int var6 = var5.blockX;
/*  40: 42 */       int var7 = var5.blockY;
/*  41: 43 */       int var8 = var5.blockZ;
/*  42: 45 */       if (!par2World.canMineBlock(par3EntityPlayer, var6, var7, var8)) {
/*  43: 47 */         return par1ItemStack;
/*  44:    */       }
/*  45: 50 */       if (var4)
/*  46:    */       {
/*  47: 52 */         if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) {
/*  48: 54 */           return par1ItemStack;
/*  49:    */         }
/*  50: 57 */         Material var9 = par2World.getBlock(var6, var7, var8).getMaterial();
/*  51: 58 */         int var10 = par2World.getBlockMetadata(var6, var7, var8);
/*  52: 60 */         if ((var9 == Material.water) && (var10 == 0))
/*  53:    */         {
/*  54: 62 */           par2World.setBlockToAir(var6, var7, var8);
/*  55: 63 */           return func_150910_a(par1ItemStack, par3EntityPlayer, Items.water_bucket);
/*  56:    */         }
/*  57: 66 */         if ((var9 == Material.lava) && (var10 == 0))
/*  58:    */         {
/*  59: 68 */           par2World.setBlockToAir(var6, var7, var8);
/*  60: 69 */           return func_150910_a(par1ItemStack, par3EntityPlayer, Items.lava_bucket);
/*  61:    */         }
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65: 74 */         if (this.isFull == Blocks.air) {
/*  66: 76 */           return new ItemStack(Items.bucket);
/*  67:    */         }
/*  68: 79 */         if (var5.sideHit == 0) {
/*  69: 81 */           var7--;
/*  70:    */         }
/*  71: 84 */         if (var5.sideHit == 1) {
/*  72: 86 */           var7++;
/*  73:    */         }
/*  74: 89 */         if (var5.sideHit == 2) {
/*  75: 91 */           var8--;
/*  76:    */         }
/*  77: 94 */         if (var5.sideHit == 3) {
/*  78: 96 */           var8++;
/*  79:    */         }
/*  80: 99 */         if (var5.sideHit == 4) {
/*  81:101 */           var6--;
/*  82:    */         }
/*  83:104 */         if (var5.sideHit == 5) {
/*  84:106 */           var6++;
/*  85:    */         }
/*  86:109 */         if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) {
/*  87:111 */           return par1ItemStack;
/*  88:    */         }
/*  89:114 */         if ((tryPlaceContainedLiquid(par2World, var6, var7, var8)) && (!par3EntityPlayer.capabilities.isCreativeMode)) {
/*  90:116 */           return new ItemStack(Items.bucket);
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:121 */     return par1ItemStack;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_)
/*  98:    */   {
/*  99:127 */     if (p_150910_2_.capabilities.isCreativeMode) {
/* 100:129 */       return p_150910_1_;
/* 101:    */     }
/* 102:131 */     if (--p_150910_1_.stackSize <= 0) {
/* 103:133 */       return new ItemStack(p_150910_3_);
/* 104:    */     }
/* 105:137 */     if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_))) {
/* 106:139 */       p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
/* 107:    */     }
/* 108:142 */     return p_150910_1_;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3, int par4)
/* 112:    */   {
/* 113:151 */     if (this.isFull == Blocks.air) {
/* 114:153 */       return false;
/* 115:    */     }
/* 116:157 */     Material var5 = par1World.getBlock(par2, par3, par4).getMaterial();
/* 117:158 */     boolean var6 = !var5.isSolid();
/* 118:160 */     if ((!par1World.isAirBlock(par2, par3, par4)) && (!var6)) {
/* 119:162 */       return false;
/* 120:    */     }
/* 121:166 */     if ((par1World.provider.isHellWorld) && (this.isFull == Blocks.flowing_water))
/* 122:    */     {
/* 123:168 */       par1World.playSoundEffect(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);
/* 124:170 */       for (int var7 = 0; var7 < 8; var7++) {
/* 125:172 */         par1World.spawnParticle("largesmoke", par2 + Math.random(), par3 + Math.random(), par4 + Math.random(), 0.0D, 0.0D, 0.0D);
/* 126:    */       }
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:177 */       if ((!par1World.isClient) && (var6) && (!var5.isLiquid())) {
/* 131:179 */         par1World.func_147480_a(par2, par3, par4, true);
/* 132:    */       }
/* 133:182 */       par1World.setBlock(par2, par3, par4, this.isFull, 0, 3);
/* 134:    */     }
/* 135:185 */     return true;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBucket
 * JD-Core Version:    0.7.0.1
 */