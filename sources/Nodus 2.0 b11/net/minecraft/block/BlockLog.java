/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.util.IIcon;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public abstract class BlockLog
/* 11:   */   extends BlockRotatedPillar
/* 12:   */ {
/* 13:   */   protected IIcon[] field_150167_a;
/* 14:   */   protected IIcon[] field_150166_b;
/* 15:   */   private static final String __OBFID = "CL_00000266";
/* 16:   */   
/* 17:   */   public BlockLog()
/* 18:   */   {
/* 19:18 */     super(Material.wood);
/* 20:19 */     setCreativeTab(CreativeTabs.tabBlock);
/* 21:20 */     setHardness(2.0F);
/* 22:21 */     setStepSound(soundTypeWood);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static int func_150165_c(int p_150165_0_)
/* 26:   */   {
/* 27:26 */     return p_150165_0_ & 0x3;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int quantityDropped(Random p_149745_1_)
/* 31:   */   {
/* 32:34 */     return 1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 36:   */   {
/* 37:39 */     return Item.getItemFromBlock(this);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 41:   */   {
/* 42:44 */     byte var7 = 4;
/* 43:45 */     int var8 = var7 + 1;
/* 44:47 */     if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
/* 45:49 */       for (int var9 = -var7; var9 <= var7; var9++) {
/* 46:51 */         for (int var10 = -var7; var10 <= var7; var10++) {
/* 47:53 */           for (int var11 = -var7; var11 <= var7; var11++) {
/* 48:55 */             if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves)
/* 49:   */             {
/* 50:57 */               int var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11);
/* 51:59 */               if ((var12 & 0x8) == 0) {
/* 52:61 */                 p_149749_1_.setBlockMetadataWithNotify(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11, var12 | 0x8, 4);
/* 53:   */               }
/* 54:   */             }
/* 55:   */           }
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected IIcon func_150163_b(int p_150163_1_)
/* 62:   */   {
/* 63:72 */     return this.field_150167_a[(p_150163_1_ % this.field_150167_a.length)];
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected IIcon func_150161_d(int p_150161_1_)
/* 67:   */   {
/* 68:77 */     return this.field_150166_b[(p_150161_1_ % this.field_150166_b.length)];
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLog
 * JD-Core Version:    0.7.0.1
 */