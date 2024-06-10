/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.ItemEnchantedBook;
/*  10:    */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  11:    */ import net.minecraft.tileentity.TileEntityChest;
/*  12:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  13:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class WorldGenDungeons
/*  17:    */   extends WorldGenerator
/*  18:    */ {
/*  19: 14 */   private static final WeightedRandomChestContent[] field_111189_a = { new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 10), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 10), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
/*  20:    */   private static final String __OBFID = "CL_00000425";
/*  21:    */   
/*  22:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  23:    */   {
/*  24: 19 */     byte var6 = 3;
/*  25: 20 */     int var7 = par2Random.nextInt(2) + 2;
/*  26: 21 */     int var8 = par2Random.nextInt(2) + 2;
/*  27: 22 */     int var9 = 0;
/*  28: 27 */     for (int var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; var10++) {
/*  29: 29 */       for (int var11 = par4 - 1; var11 <= par4 + var6 + 1; var11++) {
/*  30: 31 */         for (int var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; var12++)
/*  31:    */         {
/*  32: 33 */           Material var13 = par1World.getBlock(var10, var11, var12).getMaterial();
/*  33: 35 */           if ((var11 == par4 - 1) && (!var13.isSolid())) {
/*  34: 37 */             return false;
/*  35:    */           }
/*  36: 40 */           if ((var11 == par4 + var6 + 1) && (!var13.isSolid())) {
/*  37: 42 */             return false;
/*  38:    */           }
/*  39: 45 */           if (((var10 == par3 - var7 - 1) || (var10 == par3 + var7 + 1) || (var12 == par5 - var8 - 1) || (var12 == par5 + var8 + 1)) && (var11 == par4) && (par1World.isAirBlock(var10, var11, var12)) && (par1World.isAirBlock(var10, var11 + 1, var12))) {
/*  40: 47 */             var9++;
/*  41:    */           }
/*  42:    */         }
/*  43:    */       }
/*  44:    */     }
/*  45: 53 */     if ((var9 >= 1) && (var9 <= 5))
/*  46:    */     {
/*  47: 55 */       for (var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; var10++) {
/*  48: 57 */         for (int var11 = par4 + var6; var11 >= par4 - 1; var11--) {
/*  49: 59 */           for (int var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; var12++) {
/*  50: 61 */             if ((var10 != par3 - var7 - 1) && (var11 != par4 - 1) && (var12 != par5 - var8 - 1) && (var10 != par3 + var7 + 1) && (var11 != par4 + var6 + 1) && (var12 != par5 + var8 + 1)) {
/*  51: 63 */               par1World.setBlockToAir(var10, var11, var12);
/*  52: 65 */             } else if ((var11 >= 0) && (!par1World.getBlock(var10, var11 - 1, var12).getMaterial().isSolid())) {
/*  53: 67 */               par1World.setBlockToAir(var10, var11, var12);
/*  54: 69 */             } else if (par1World.getBlock(var10, var11, var12).getMaterial().isSolid()) {
/*  55: 71 */               if ((var11 == par4 - 1) && (par2Random.nextInt(4) != 0)) {
/*  56: 73 */                 par1World.setBlock(var10, var11, var12, Blocks.mossy_cobblestone, 0, 2);
/*  57:    */               } else {
/*  58: 77 */                 par1World.setBlock(var10, var11, var12, Blocks.cobblestone, 0, 2);
/*  59:    */               }
/*  60:    */             }
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64: 84 */       var10 = 0;
/*  65: 86 */       while (var10 < 2)
/*  66:    */       {
/*  67: 88 */         int var11 = 0;
/*  68: 92 */         while (var11 < 3)
/*  69:    */         {
/*  70: 96 */           int var12 = par3 + par2Random.nextInt(var7 * 2 + 1) - var7;
/*  71: 97 */           int var14 = par5 + par2Random.nextInt(var8 * 2 + 1) - var8;
/*  72: 99 */           if (par1World.isAirBlock(var12, par4, var14))
/*  73:    */           {
/*  74:101 */             int var15 = 0;
/*  75:103 */             if (par1World.getBlock(var12 - 1, par4, var14).getMaterial().isSolid()) {
/*  76:105 */               var15++;
/*  77:    */             }
/*  78:108 */             if (par1World.getBlock(var12 + 1, par4, var14).getMaterial().isSolid()) {
/*  79:110 */               var15++;
/*  80:    */             }
/*  81:113 */             if (par1World.getBlock(var12, par4, var14 - 1).getMaterial().isSolid()) {
/*  82:115 */               var15++;
/*  83:    */             }
/*  84:118 */             if (par1World.getBlock(var12, par4, var14 + 1).getMaterial().isSolid()) {
/*  85:120 */               var15++;
/*  86:    */             }
/*  87:123 */             if (var15 == 1)
/*  88:    */             {
/*  89:125 */               par1World.setBlock(var12, par4, var14, Blocks.chest, 0, 2);
/*  90:126 */               WeightedRandomChestContent[] var16 = WeightedRandomChestContent.func_92080_a(field_111189_a, new WeightedRandomChestContent[] { Items.enchanted_book.func_92114_b(par2Random) });
/*  91:127 */               TileEntityChest var17 = (TileEntityChest)par1World.getTileEntity(var12, par4, var14);
/*  92:129 */               if (var17 == null) {
/*  93:    */                 break;
/*  94:    */               }
/*  95:131 */               WeightedRandomChestContent.generateChestContents(par2Random, var16, var17, 8);
/*  96:    */               
/*  97:    */ 
/*  98:134 */               break;
/*  99:    */             }
/* 100:    */           }
/* 101:138 */           var11++;
/* 102:    */         }
/* 103:143 */         var10++;
/* 104:    */       }
/* 105:148 */       par1World.setBlock(par3, par4, par5, Blocks.mob_spawner, 0, 2);
/* 106:149 */       TileEntityMobSpawner var18 = (TileEntityMobSpawner)par1World.getTileEntity(par3, par4, par5);
/* 107:151 */       if (var18 != null) {
/* 108:153 */         var18.func_145881_a().setMobID(pickMobSpawner(par2Random));
/* 109:    */       } else {
/* 110:157 */         System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
/* 111:    */       }
/* 112:160 */       return true;
/* 113:    */     }
/* 114:164 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private String pickMobSpawner(Random par1Random)
/* 118:    */   {
/* 119:173 */     int var2 = par1Random.nextInt(4);
/* 120:174 */     return var2 == 3 ? "Spider" : var2 == 2 ? "Zombie" : var2 == 1 ? "Zombie" : var2 == 0 ? "Skeleton" : "";
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenDungeons
 * JD-Core Version:    0.7.0.1
 */