/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ 
/* 11:   */ public class BlockMelon
/* 12:   */   extends Block
/* 13:   */ {
/* 14:   */   private IIcon field_150201_a;
/* 15:   */   private static final String __OBFID = "CL_00000267";
/* 16:   */   
/* 17:   */   protected BlockMelon()
/* 18:   */   {
/* 19:18 */     super(Material.field_151572_C);
/* 20:19 */     setCreativeTab(CreativeTabs.tabBlock);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 24:   */   {
/* 25:27 */     return (p_149691_1_ != 1) && (p_149691_1_ != 0) ? this.blockIcon : this.field_150201_a;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 29:   */   {
/* 30:32 */     return Items.melon;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int quantityDropped(Random p_149745_1_)
/* 34:   */   {
/* 35:40 */     return 3 + p_149745_1_.nextInt(5);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/* 39:   */   {
/* 40:48 */     int var3 = quantityDropped(p_149679_2_) + p_149679_2_.nextInt(1 + p_149679_1_);
/* 41:50 */     if (var3 > 9) {
/* 42:52 */       var3 = 9;
/* 43:   */     }
/* 44:55 */     return var3;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 48:   */   {
/* 49:60 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 50:61 */     this.field_150201_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockMelon
 * JD-Core Version:    0.7.0.1
 */