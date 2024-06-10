/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class BlockHugeMushroom
/* 12:   */   extends Block
/* 13:   */ {
/* 14:13 */   private static final String[] field_149793_a = { "skin_brown", "skin_red" };
/* 15:   */   private final int field_149792_b;
/* 16:   */   private IIcon[] field_149794_M;
/* 17:   */   private IIcon field_149795_N;
/* 18:   */   private IIcon field_149796_O;
/* 19:   */   private static final String __OBFID = "CL_00000258";
/* 20:   */   
/* 21:   */   public BlockHugeMushroom(Material p_i45412_1_, int p_i45412_2_)
/* 22:   */   {
/* 23:22 */     super(p_i45412_1_);
/* 24:23 */     this.field_149792_b = p_i45412_2_;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 28:   */   {
/* 29:31 */     return p_149691_2_ == 15 ? this.field_149795_N : p_149691_2_ == 14 ? this.field_149794_M[this.field_149792_b] : ((p_149691_2_ == 3) || (p_149691_2_ == 6) || (p_149691_2_ == 9)) && (p_149691_1_ == 5) ? this.field_149794_M[this.field_149792_b] : ((p_149691_2_ == 1) || (p_149691_2_ == 4) || (p_149691_2_ == 7)) && (p_149691_1_ == 4) ? this.field_149794_M[this.field_149792_b] : (p_149691_2_ >= 7) && (p_149691_2_ <= 9) && (p_149691_1_ == 3) ? this.field_149794_M[this.field_149792_b] : (p_149691_2_ >= 1) && (p_149691_2_ <= 3) && (p_149691_1_ == 2) ? this.field_149794_M[this.field_149792_b] : (p_149691_2_ >= 1) && (p_149691_2_ <= 9) && (p_149691_1_ == 1) ? this.field_149794_M[this.field_149792_b] : (p_149691_2_ == 10) && (p_149691_1_ > 1) ? this.field_149795_N : this.field_149796_O;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int quantityDropped(Random p_149745_1_)
/* 33:   */   {
/* 34:39 */     int var2 = p_149745_1_.nextInt(10) - 7;
/* 35:41 */     if (var2 < 0) {
/* 36:43 */       var2 = 0;
/* 37:   */     }
/* 38:46 */     return var2;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 42:   */   {
/* 43:51 */     return Item.getItemById(Block.getIdFromBlock(Blocks.brown_mushroom) + this.field_149792_b);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 47:   */   {
/* 48:59 */     return Item.getItemById(Block.getIdFromBlock(Blocks.brown_mushroom) + this.field_149792_b);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 52:   */   {
/* 53:64 */     this.field_149794_M = new IIcon[field_149793_a.length];
/* 54:66 */     for (int var2 = 0; var2 < this.field_149794_M.length; var2++) {
/* 55:68 */       this.field_149794_M[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_149793_a[var2]);
/* 56:   */     }
/* 57:71 */     this.field_149796_O = p_149651_1_.registerIcon(getTextureName() + "_" + "inside");
/* 58:72 */     this.field_149795_N = p_149651_1_.registerIcon(getTextureName() + "_" + "skin_stem");
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockHugeMushroom
 * JD-Core Version:    0.7.0.1
 */