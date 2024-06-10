/*    */ package nightmare.gui.window.impl;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.fonts.impl.Fonts;
/*    */ import nightmare.gui.window.Window;
/*    */ import nightmare.utils.ColorUtils;
/*    */ import nightmare.utils.render.BlurUtils;
/*    */ 
/*    */ public class Inventory
/*    */   extends Window {
/* 16 */   private static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public Inventory() {
/* 19 */     super("Inventory", 30, 30, 180, 60, true, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 24 */     int startX = getX() + 2;
/* 25 */     int startY = getY() + 2;
/* 26 */     int curIndex = 0;
/*    */     
/* 28 */     if (Nightmare.instance.moduleManager.getModuleByName("Blur").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Inventory").getValBoolean()) {
/* 29 */       BlurUtils.drawBlurRect(getX(), getY(), getWidth(), getHeight());
/*    */     }
/*    */     
/* 32 */     Gui.func_73734_a(getX(), getY(), getWidth(), getHeight(), ColorUtils.getBackgroundColor());
/* 33 */     Gui.func_73734_a(getX(), getY() - 14, getWidth(), getY(), ColorUtils.getClientColor());
/*    */     
/* 35 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString("Inventory", (getX() + 4), (getY() - 10), -1, false);
/* 36 */     for (int i = 9; i < 36; i++) {
/* 37 */       ItemStack slot = mc.field_71439_g.field_71071_by.field_70462_a[i];
/* 38 */       if (slot == null) {
/* 39 */         startX += 20;
/* 40 */         curIndex++;
/*    */         
/* 42 */         if (curIndex > 8) {
/* 43 */           curIndex = 0;
/* 44 */           startY += 20;
/* 45 */           startX = getX() + 2;
/*    */         }
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 51 */         drawItemStack(slot, startX, startY);
/* 52 */         startX += 20;
/* 53 */         curIndex++;
/* 54 */         if (curIndex > 8) {
/* 55 */           curIndex = 0;
/* 56 */           startY += 20;
/* 57 */           startX = getX() + 2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   private void drawItemStack(ItemStack stack, int x, int y) {
/* 63 */     GlStateManager.func_179094_E();
/* 64 */     RenderHelper.func_74520_c();
/* 65 */     GlStateManager.func_179118_c();
/* 66 */     GlStateManager.func_179086_m(256);
/* 67 */     (mc.func_175599_af()).field_77023_b = -150.0F;
/* 68 */     GlStateManager.func_179140_f();
/* 69 */     GlStateManager.func_179097_i();
/* 70 */     GlStateManager.func_179084_k();
/* 71 */     GlStateManager.func_179145_e();
/* 72 */     GlStateManager.func_179126_j();
/* 73 */     GlStateManager.func_179140_f();
/* 74 */     GlStateManager.func_179097_i();
/* 75 */     GlStateManager.func_179090_x();
/* 76 */     GlStateManager.func_179118_c();
/* 77 */     GlStateManager.func_179084_k();
/* 78 */     GlStateManager.func_179147_l();
/* 79 */     GlStateManager.func_179141_d();
/* 80 */     GlStateManager.func_179098_w();
/* 81 */     GlStateManager.func_179145_e();
/* 82 */     GlStateManager.func_179126_j();
/* 83 */     mc.func_175599_af().func_175042_a(stack, x, y);
/* 84 */     mc.func_175599_af().func_180453_a(mc.field_71466_p, stack, x, y, null);
/* 85 */     (mc.func_175599_af()).field_77023_b = 0.0F;
/* 86 */     GlStateManager.func_179141_d();
/* 87 */     RenderHelper.func_74518_a();
/* 88 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\window\impl\Inventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */