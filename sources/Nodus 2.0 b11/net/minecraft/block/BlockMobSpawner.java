/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.tileentity.TileEntity;
/*  7:   */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class BlockMobSpawner
/* 11:   */   extends BlockContainer
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000269";
/* 14:   */   
/* 15:   */   protected BlockMobSpawner()
/* 16:   */   {
/* 17:16 */     super(Material.rock);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 21:   */   {
/* 22:24 */     return new TileEntityMobSpawner();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 26:   */   {
/* 27:29 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int quantityDropped(Random p_149745_1_)
/* 31:   */   {
/* 32:37 */     return 0;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 36:   */   {
/* 37:45 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/* 38:46 */     int var8 = 15 + p_149690_1_.rand.nextInt(15) + p_149690_1_.rand.nextInt(15);
/* 39:47 */     dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isOpaqueCube()
/* 43:   */   {
/* 44:52 */     return false;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 48:   */   {
/* 49:60 */     return Item.getItemById(0);
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockMobSpawner
 * JD-Core Version:    0.7.0.1
 */