/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class BlockDeadBush
/* 13:   */   extends BlockBush
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000224";
/* 16:   */   
/* 17:   */   protected BlockDeadBush()
/* 18:   */   {
/* 19:19 */     super(Material.vine);
/* 20:20 */     float var1 = 0.4F;
/* 21:21 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean func_149854_a(Block p_149854_1_)
/* 25:   */   {
/* 26:26 */     return (p_149854_1_ == Blocks.sand) || (p_149854_1_ == Blocks.hardened_clay) || (p_149854_1_ == Blocks.stained_hardened_clay) || (p_149854_1_ == Blocks.dirt);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 30:   */   {
/* 31:31 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/* 35:   */   {
/* 36:36 */     if ((!p_149636_1_.isClient) && (p_149636_2_.getCurrentEquippedItem() != null) && (p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears))
/* 37:   */     {
/* 38:38 */       p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/* 39:39 */       dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.deadbush, 1, p_149636_6_));
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:43 */       super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDeadBush
 * JD-Core Version:    0.7.0.1
 */