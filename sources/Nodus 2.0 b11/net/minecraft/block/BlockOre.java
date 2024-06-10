/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.MathHelper;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class BlockOre
/* 13:   */   extends Block
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000282";
/* 16:   */   
/* 17:   */   public BlockOre()
/* 18:   */   {
/* 19:18 */     super(Material.rock);
/* 20:19 */     setCreativeTab(CreativeTabs.tabBlock);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 24:   */   {
/* 25:24 */     return this == Blocks.quartz_ore ? Items.quartz : this == Blocks.emerald_ore ? Items.emerald : this == Blocks.lapis_ore ? Items.dye : this == Blocks.diamond_ore ? Items.diamond : this == Blocks.coal_ore ? Items.coal : Item.getItemFromBlock(this);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int quantityDropped(Random p_149745_1_)
/* 29:   */   {
/* 30:32 */     return this == Blocks.lapis_ore ? 4 + p_149745_1_.nextInt(5) : 1;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/* 34:   */   {
/* 35:40 */     if ((p_149679_1_ > 0) && (Item.getItemFromBlock(this) != getItemDropped(0, p_149679_2_, p_149679_1_)))
/* 36:   */     {
/* 37:42 */       int var3 = p_149679_2_.nextInt(p_149679_1_ + 2) - 1;
/* 38:44 */       if (var3 < 0) {
/* 39:46 */         var3 = 0;
/* 40:   */       }
/* 41:49 */       return quantityDropped(p_149679_2_) * (var3 + 1);
/* 42:   */     }
/* 43:53 */     return quantityDropped(p_149679_2_);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 47:   */   {
/* 48:62 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/* 49:64 */     if (getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_) != Item.getItemFromBlock(this))
/* 50:   */     {
/* 51:66 */       int var8 = 0;
/* 52:68 */       if (this == Blocks.coal_ore) {
/* 53:70 */         var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 0, 2);
/* 54:72 */       } else if (this == Blocks.diamond_ore) {
/* 55:74 */         var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
/* 56:76 */       } else if (this == Blocks.emerald_ore) {
/* 57:78 */         var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
/* 58:80 */       } else if (this == Blocks.lapis_ore) {
/* 59:82 */         var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 2, 5);
/* 60:84 */       } else if (this == Blocks.quartz_ore) {
/* 61:86 */         var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 2, 5);
/* 62:   */       }
/* 63:89 */       dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int damageDropped(int p_149692_1_)
/* 68:   */   {
/* 69:98 */     return this == Blocks.lapis_ore ? 4 : 0;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockOre
 * JD-Core Version:    0.7.0.1
 */