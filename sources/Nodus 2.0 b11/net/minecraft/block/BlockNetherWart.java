/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class BlockNetherWart
/*  13:    */   extends BlockBush
/*  14:    */ {
/*  15:    */   private IIcon[] field_149883_a;
/*  16:    */   private static final String __OBFID = "CL_00000274";
/*  17:    */   
/*  18:    */   protected BlockNetherWart()
/*  19:    */   {
/*  20: 20 */     setTickRandomly(true);
/*  21: 21 */     float var1 = 0.5F;
/*  22: 22 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
/*  23: 23 */     setCreativeTab(null);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected boolean func_149854_a(Block p_149854_1_)
/*  27:    */   {
/*  28: 28 */     return p_149854_1_ == Blocks.soul_sand;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  32:    */   {
/*  33: 36 */     return func_149854_a(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  37:    */   {
/*  38: 44 */     int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  39: 46 */     if ((var6 < 3) && (p_149674_5_.nextInt(10) == 0))
/*  40:    */     {
/*  41: 48 */       var6++;
/*  42: 49 */       p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6, 2);
/*  43:    */     }
/*  44: 52 */     super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  48:    */   {
/*  49: 60 */     return p_149691_2_ > 0 ? this.field_149883_a[1] : p_149691_2_ >= 3 ? this.field_149883_a[2] : this.field_149883_a[0];
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getRenderType()
/*  53:    */   {
/*  54: 68 */     return 6;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/*  58:    */   {
/*  59: 76 */     if (!p_149690_1_.isClient)
/*  60:    */     {
/*  61: 78 */       int var8 = 1;
/*  62: 80 */       if (p_149690_5_ >= 3)
/*  63:    */       {
/*  64: 82 */         var8 = 2 + p_149690_1_.rand.nextInt(3);
/*  65: 84 */         if (p_149690_7_ > 0) {
/*  66: 86 */           var8 += p_149690_1_.rand.nextInt(p_149690_7_ + 1);
/*  67:    */         }
/*  68:    */       }
/*  69: 90 */       for (int var9 = 0; var9 < var8; var9++) {
/*  70: 92 */         dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.nether_wart));
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  76:    */   {
/*  77: 99 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int quantityDropped(Random p_149745_1_)
/*  81:    */   {
/*  82:107 */     return 0;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  86:    */   {
/*  87:115 */     return Items.nether_wart;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  91:    */   {
/*  92:120 */     this.field_149883_a = new IIcon[3];
/*  93:122 */     for (int var2 = 0; var2 < this.field_149883_a.length; var2++) {
/*  94:124 */       this.field_149883_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_stage_" + var2);
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockNetherWart
 * JD-Core Version:    0.7.0.1
 */