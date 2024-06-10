/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemShears
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000062";
/* 14:   */   
/* 15:   */   public ItemShears()
/* 16:   */   {
/* 17:16 */     setMaxStackSize(1);
/* 18:17 */     setMaxDamage(238);
/* 19:18 */     setCreativeTab(CreativeTabs.tabTools);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
/* 23:   */   {
/* 24:23 */     if ((p_150894_3_.getMaterial() != Material.leaves) && (p_150894_3_ != Blocks.web) && (p_150894_3_ != Blocks.tallgrass) && (p_150894_3_ != Blocks.vine) && (p_150894_3_ != Blocks.tripwire)) {
/* 25:25 */       return super.onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
/* 26:   */     }
/* 27:29 */     p_150894_1_.damageItem(1, p_150894_7_);
/* 28:30 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean func_150897_b(Block p_150897_1_)
/* 32:   */   {
/* 33:36 */     return (p_150897_1_ == Blocks.web) || (p_150897_1_ == Blocks.redstone_wire) || (p_150897_1_ == Blocks.tripwire);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/* 37:   */   {
/* 38:41 */     return (p_150893_2_ != Blocks.web) && (p_150893_2_.getMaterial() != Material.leaves) ? super.func_150893_a(p_150893_1_, p_150893_2_) : p_150893_2_ == Blocks.wool ? 5.0F : 15.0F;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemShears
 * JD-Core Version:    0.7.0.1
 */