/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class BlockFalling
/*  11:    */   extends Block
/*  12:    */ {
/*  13:    */   public static boolean field_149832_M;
/*  14:    */   private static final String __OBFID = "CL_00000240";
/*  15:    */   
/*  16:    */   public BlockFalling()
/*  17:    */   {
/*  18: 17 */     super(Material.sand);
/*  19: 18 */     setCreativeTab(CreativeTabs.tabBlock);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BlockFalling(Material p_i45405_1_)
/*  23:    */   {
/*  24: 23 */     super(p_i45405_1_);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  28:    */   {
/*  29: 28 */     p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, func_149738_a(p_149726_1_));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  33:    */   {
/*  34: 33 */     p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, func_149738_a(p_149695_1_));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  38:    */   {
/*  39: 41 */     if (!p_149674_1_.isClient) {
/*  40: 43 */       func_149830_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void func_149830_m(World p_149830_1_, int p_149830_2_, int p_149830_3_, int p_149830_4_)
/*  45:    */   {
/*  46: 49 */     if ((func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_)) && (p_149830_3_ >= 0))
/*  47:    */     {
/*  48: 51 */       byte var8 = 32;
/*  49: 53 */       if ((!field_149832_M) && (p_149830_1_.checkChunksExist(p_149830_2_ - var8, p_149830_3_ - var8, p_149830_4_ - var8, p_149830_2_ + var8, p_149830_3_ + var8, p_149830_4_ + var8)))
/*  50:    */       {
/*  51: 55 */         if (!p_149830_1_.isClient)
/*  52:    */         {
/*  53: 57 */           EntityFallingBlock var9 = new EntityFallingBlock(p_149830_1_, p_149830_2_ + 0.5F, p_149830_3_ + 0.5F, p_149830_4_ + 0.5F, this, p_149830_1_.getBlockMetadata(p_149830_2_, p_149830_3_, p_149830_4_));
/*  54: 58 */           func_149829_a(var9);
/*  55: 59 */           p_149830_1_.spawnEntityInWorld(var9);
/*  56:    */         }
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 64 */         p_149830_1_.setBlockToAir(p_149830_2_, p_149830_3_, p_149830_4_);
/*  61: 66 */         while ((func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_)) && (p_149830_3_ > 0)) {
/*  62: 68 */           p_149830_3_--;
/*  63:    */         }
/*  64: 71 */         if (p_149830_3_ > 0) {
/*  65: 73 */           p_149830_1_.setBlock(p_149830_2_, p_149830_3_, p_149830_4_, this);
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void func_149829_a(EntityFallingBlock p_149829_1_) {}
/*  72:    */   
/*  73:    */   public int func_149738_a(World p_149738_1_)
/*  74:    */   {
/*  75: 83 */     return 2;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean func_149831_e(World p_149831_0_, int p_149831_1_, int p_149831_2_, int p_149831_3_)
/*  79:    */   {
/*  80: 88 */     Block var4 = p_149831_0_.getBlock(p_149831_1_, p_149831_2_, p_149831_3_);
/*  81: 90 */     if (var4.blockMaterial == Material.air) {
/*  82: 92 */       return true;
/*  83:    */     }
/*  84: 94 */     if (var4 == Blocks.fire) {
/*  85: 96 */       return true;
/*  86:    */     }
/*  87:100 */     Material var5 = var4.blockMaterial;
/*  88:101 */     return var5 == Material.water;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void func_149828_a(World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_) {}
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFalling
 * JD-Core Version:    0.7.0.1
 */