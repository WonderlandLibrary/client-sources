/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.item.Item;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public abstract class BlockRotatedPillar
/* 10:   */   extends Block
/* 11:   */ {
/* 12:   */   protected IIcon field_150164_N;
/* 13:   */   private static final String __OBFID = "CL_00000302";
/* 14:   */   
/* 15:   */   protected BlockRotatedPillar(Material p_i45425_1_)
/* 16:   */   {
/* 17:16 */     super(p_i45425_1_);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRenderType()
/* 21:   */   {
/* 22:24 */     return 31;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 26:   */   {
/* 27:29 */     int var10 = p_149660_9_ & 0x3;
/* 28:30 */     byte var11 = 0;
/* 29:32 */     switch (p_149660_5_)
/* 30:   */     {
/* 31:   */     case 0: 
/* 32:   */     case 1: 
/* 33:36 */       var11 = 0;
/* 34:37 */       break;
/* 35:   */     case 2: 
/* 36:   */     case 3: 
/* 37:41 */       var11 = 8;
/* 38:42 */       break;
/* 39:   */     case 4: 
/* 40:   */     case 5: 
/* 41:46 */       var11 = 4;
/* 42:   */     }
/* 43:49 */     return var10 | var11;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 47:   */   {
/* 48:57 */     int var3 = p_149691_2_ & 0xC;
/* 49:58 */     int var4 = p_149691_2_ & 0x3;
/* 50:59 */     return (var3 == 8) && ((p_149691_1_ == 2) || (p_149691_1_ == 3)) ? func_150161_d(var4) : (var3 == 4) && ((p_149691_1_ == 5) || (p_149691_1_ == 4)) ? func_150161_d(var4) : (var3 == 0) && ((p_149691_1_ == 1) || (p_149691_1_ == 0)) ? func_150161_d(var4) : func_150163_b(var4);
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected abstract IIcon func_150163_b(int paramInt);
/* 54:   */   
/* 55:   */   protected IIcon func_150161_d(int p_150161_1_)
/* 56:   */   {
/* 57:66 */     return this.field_150164_N;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int damageDropped(int p_149692_1_)
/* 61:   */   {
/* 62:74 */     return p_149692_1_ & 0x3;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int func_150162_k(int p_150162_1_)
/* 66:   */   {
/* 67:79 */     return p_150162_1_ & 0x3;
/* 68:   */   }
/* 69:   */   
/* 70:   */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 71:   */   {
/* 72:88 */     return new ItemStack(Item.getItemFromBlock(this), 1, func_150162_k(p_149644_1_));
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRotatedPillar
 * JD-Core Version:    0.7.0.1
 */