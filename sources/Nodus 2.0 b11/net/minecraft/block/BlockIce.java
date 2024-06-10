/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.world.EnumSkyBlock;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.WorldProvider;
/*  14:    */ 
/*  15:    */ public class BlockIce
/*  16:    */   extends BlockBreakable
/*  17:    */ {
/*  18:    */   private static final String __OBFID = "CL_00000259";
/*  19:    */   
/*  20:    */   public BlockIce()
/*  21:    */   {
/*  22: 21 */     super("ice", Material.ice, false);
/*  23: 22 */     this.slipperiness = 0.98F;
/*  24: 23 */     setTickRandomly(true);
/*  25: 24 */     setCreativeTab(CreativeTabs.tabBlock);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getRenderBlockPass()
/*  29:    */   {
/*  30: 32 */     return 1;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  34:    */   {
/*  35: 37 */     return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, 1 - p_149646_5_);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/*  39:    */   {
/*  40: 42 */     p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/*  41: 43 */     p_149636_2_.addExhaustion(0.025F);
/*  42: 45 */     if ((canSilkHarvest()) && (EnchantmentHelper.getSilkTouchModifier(p_149636_2_)))
/*  43:    */     {
/*  44: 47 */       ItemStack var9 = createStackedBlock(p_149636_6_);
/*  45: 49 */       if (var9 != null) {
/*  46: 51 */         dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, var9);
/*  47:    */       }
/*  48:    */     }
/*  49:    */     else
/*  50:    */     {
/*  51: 56 */       if (p_149636_1_.provider.isHellWorld)
/*  52:    */       {
/*  53: 58 */         p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
/*  54: 59 */         return;
/*  55:    */       }
/*  56: 62 */       int var7 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
/*  57: 63 */       dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, var7);
/*  58: 64 */       Material var8 = p_149636_1_.getBlock(p_149636_3_, p_149636_4_ - 1, p_149636_5_).getMaterial();
/*  59: 66 */       if ((var8.blocksMovement()) || (var8.isLiquid())) {
/*  60: 68 */         p_149636_1_.setBlock(p_149636_3_, p_149636_4_, p_149636_5_, Blocks.flowing_water);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int quantityDropped(Random p_149745_1_)
/*  66:    */   {
/*  67: 78 */     return 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  71:    */   {
/*  72: 86 */     if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11 - getLightOpacity())
/*  73:    */     {
/*  74: 88 */       if (p_149674_1_.provider.isHellWorld)
/*  75:    */       {
/*  76: 90 */         p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
/*  77: 91 */         return;
/*  78:    */       }
/*  79: 94 */       dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
/*  80: 95 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.water);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getMobilityFlag()
/*  85:    */   {
/*  86:101 */     return 0;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockIce
 * JD-Core Version:    0.7.0.1
 */