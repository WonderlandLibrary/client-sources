/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.gui.FontRenderer;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public abstract class TileEntitySpecialRenderer
/* 10:   */ {
/* 11:   */   protected TileEntityRendererDispatcher field_147501_a;
/* 12:   */   private static final String __OBFID = "CL_00000964";
/* 13:   */   
/* 14:   */   public abstract void renderTileEntityAt(TileEntity paramTileEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);
/* 15:   */   
/* 16:   */   protected void bindTexture(ResourceLocation p_147499_1_)
/* 17:   */   {
/* 18:18 */     TextureManager var2 = this.field_147501_a.field_147553_e;
/* 19:20 */     if (var2 != null) {
/* 20:22 */       var2.bindTexture(p_147499_1_);
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void func_147497_a(TileEntityRendererDispatcher p_147497_1_)
/* 25:   */   {
/* 26:28 */     this.field_147501_a = p_147497_1_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void func_147496_a(World p_147496_1_) {}
/* 30:   */   
/* 31:   */   public FontRenderer func_147498_b()
/* 32:   */   {
/* 33:35 */     return this.field_147501_a.func_147548_a();
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 * JD-Core Version:    0.7.0.1
 */