/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.init.Items;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class BlockPotato
/* 12:   */   extends BlockCrops
/* 13:   */ {
/* 14:   */   private IIcon[] field_149869_a;
/* 15:   */   private static final String __OBFID = "CL_00000286";
/* 16:   */   
/* 17:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 18:   */   {
/* 19:20 */     if (p_149691_2_ < 7)
/* 20:   */     {
/* 21:22 */       if (p_149691_2_ == 6) {
/* 22:24 */         p_149691_2_ = 5;
/* 23:   */       }
/* 24:27 */       return this.field_149869_a[(p_149691_2_ >> 1)];
/* 25:   */     }
/* 26:31 */     return this.field_149869_a[3];
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected Item func_149866_i()
/* 30:   */   {
/* 31:37 */     return Items.potato;
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected Item func_149865_P()
/* 35:   */   {
/* 36:42 */     return Items.potato;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 40:   */   {
/* 41:50 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/* 42:52 */     if (!p_149690_1_.isClient) {
/* 43:54 */       if ((p_149690_5_ >= 7) && (p_149690_1_.rand.nextInt(50) == 0)) {
/* 44:56 */         dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.poisonous_potato));
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 50:   */   {
/* 51:63 */     this.field_149869_a = new IIcon[4];
/* 52:65 */     for (int var2 = 0; var2 < this.field_149869_a.length; var2++) {
/* 53:67 */       this.field_149869_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_stage_" + var2);
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPotato
 * JD-Core Version:    0.7.0.1
 */