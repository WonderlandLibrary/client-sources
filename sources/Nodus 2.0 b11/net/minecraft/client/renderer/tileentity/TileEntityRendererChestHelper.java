/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.tileentity.TileEntityChest;
/*  6:   */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  7:   */ 
/*  8:   */ public class TileEntityRendererChestHelper
/*  9:   */ {
/* 10:10 */   public static TileEntityRendererChestHelper instance = new TileEntityRendererChestHelper();
/* 11:11 */   private TileEntityChest field_147717_b = new TileEntityChest(0);
/* 12:12 */   private TileEntityChest field_147718_c = new TileEntityChest(1);
/* 13:13 */   private TileEntityEnderChest field_147716_d = new TileEntityEnderChest();
/* 14:   */   private static final String __OBFID = "CL_00000946";
/* 15:   */   
/* 16:   */   public void func_147715_a(Block p_147715_1_, int p_147715_2_, float p_147715_3_)
/* 17:   */   {
/* 18:18 */     if (p_147715_1_ == Blocks.ender_chest) {
/* 19:20 */       TileEntityRendererDispatcher.instance.func_147549_a(this.field_147716_d, 0.0D, 0.0D, 0.0D, 0.0F);
/* 20:22 */     } else if (p_147715_1_ == Blocks.trapped_chest) {
/* 21:24 */       TileEntityRendererDispatcher.instance.func_147549_a(this.field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F);
/* 22:   */     } else {
/* 23:28 */       TileEntityRendererDispatcher.instance.func_147549_a(this.field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper
 * JD-Core Version:    0.7.0.1
 */