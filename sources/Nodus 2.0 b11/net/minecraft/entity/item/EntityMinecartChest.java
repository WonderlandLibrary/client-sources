/*  1:   */ package net.minecraft.entity.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.util.DamageSource;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityMinecartChest
/* 10:   */   extends EntityMinecartContainer
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00001671";
/* 13:   */   
/* 14:   */   public EntityMinecartChest(World par1World)
/* 15:   */   {
/* 16:15 */     super(par1World);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public EntityMinecartChest(World par1World, double par2, double par4, double par6)
/* 20:   */   {
/* 21:20 */     super(par1World, par2, par4, par6);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void killMinecart(DamageSource par1DamageSource)
/* 25:   */   {
/* 26:25 */     super.killMinecart(par1DamageSource);
/* 27:26 */     func_145778_a(Item.getItemFromBlock(Blocks.chest), 1, 0.0F);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getSizeInventory()
/* 31:   */   {
/* 32:34 */     return 27;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getMinecartType()
/* 36:   */   {
/* 37:39 */     return 1;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Block func_145817_o()
/* 41:   */   {
/* 42:44 */     return Blocks.chest;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getDefaultDisplayTileOffset()
/* 46:   */   {
/* 47:49 */     return 8;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartChest
 * JD-Core Version:    0.7.0.1
 */