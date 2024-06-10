/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.AxisAlignedBB;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class BlockWeb
/* 13:   */   extends Block
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000333";
/* 16:   */   
/* 17:   */   public BlockWeb()
/* 18:   */   {
/* 19:18 */     super(Material.field_151569_G);
/* 20:19 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 24:   */   {
/* 25:24 */     p_149670_5_.setInWeb();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isOpaqueCube()
/* 29:   */   {
/* 30:29 */     return false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 34:   */   {
/* 35:38 */     return null;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getRenderType()
/* 39:   */   {
/* 40:46 */     return 1;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean renderAsNormalBlock()
/* 44:   */   {
/* 45:51 */     return false;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 49:   */   {
/* 50:56 */     return Items.string;
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected boolean canSilkHarvest()
/* 54:   */   {
/* 55:61 */     return true;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockWeb
 * JD-Core Version:    0.7.0.1
 */