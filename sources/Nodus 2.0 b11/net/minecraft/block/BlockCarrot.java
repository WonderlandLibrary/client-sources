/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ 
/*  8:   */ public class BlockCarrot
/*  9:   */   extends BlockCrops
/* 10:   */ {
/* 11:   */   private IIcon[] field_149868_a;
/* 12:   */   private static final String __OBFID = "CL_00000212";
/* 13:   */   
/* 14:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 15:   */   {
/* 16:18 */     if (p_149691_2_ < 7)
/* 17:   */     {
/* 18:20 */       if (p_149691_2_ == 6) {
/* 19:22 */         p_149691_2_ = 5;
/* 20:   */       }
/* 21:25 */       return this.field_149868_a[(p_149691_2_ >> 1)];
/* 22:   */     }
/* 23:29 */     return this.field_149868_a[3];
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected Item func_149866_i()
/* 27:   */   {
/* 28:35 */     return Items.carrot;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected Item func_149865_P()
/* 32:   */   {
/* 33:40 */     return Items.carrot;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 37:   */   {
/* 38:45 */     this.field_149868_a = new IIcon[4];
/* 39:47 */     for (int var2 = 0; var2 < this.field_149868_a.length; var2++) {
/* 40:49 */       this.field_149868_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_stage_" + var2);
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCarrot
 * JD-Core Version:    0.7.0.1
 */