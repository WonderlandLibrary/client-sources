/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class BlockRedstoneLight
/* 11:   */   extends Block
/* 12:   */ {
/* 13:   */   private final boolean field_150171_a;
/* 14:   */   private static final String __OBFID = "CL_00000297";
/* 15:   */   
/* 16:   */   public BlockRedstoneLight(boolean p_i45421_1_)
/* 17:   */   {
/* 18:17 */     super(Material.redstoneLight);
/* 19:18 */     this.field_150171_a = p_i45421_1_;
/* 20:20 */     if (p_i45421_1_) {
/* 21:22 */       setLightLevel(1.0F);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 26:   */   {
/* 27:28 */     if (!p_149726_1_.isClient) {
/* 28:30 */       if ((this.field_150171_a) && (!p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))) {
/* 29:32 */         p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, 4);
/* 30:34 */       } else if ((!this.field_150171_a) && (p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))) {
/* 31:36 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, Blocks.lit_redstone_lamp, 0, 2);
/* 32:   */       }
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 37:   */   {
/* 38:43 */     if (!p_149695_1_.isClient) {
/* 39:45 */       if ((this.field_150171_a) && (!p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))) {
/* 40:47 */         p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, 4);
/* 41:49 */       } else if ((!this.field_150171_a) && (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))) {
/* 42:51 */         p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.lit_redstone_lamp, 0, 2);
/* 43:   */       }
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 48:   */   {
/* 49:61 */     if ((!p_149674_1_.isClient) && (this.field_150171_a) && (!p_149674_1_.isBlockIndirectlyGettingPowered(p_149674_2_, p_149674_3_, p_149674_4_))) {
/* 50:63 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_lamp, 0, 2);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 55:   */   {
/* 56:69 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 60:   */   {
/* 61:77 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/* 62:   */   }
/* 63:   */   
/* 64:   */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 65:   */   {
/* 66:86 */     return new ItemStack(Blocks.redstone_lamp);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneLight
 * JD-Core Version:    0.7.0.1
 */