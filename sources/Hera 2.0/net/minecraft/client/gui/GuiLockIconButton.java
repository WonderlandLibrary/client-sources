/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiLockIconButton
/*    */   extends GuiButton
/*    */ {
/*    */   private boolean field_175231_o = false;
/*    */   
/*    */   public GuiLockIconButton(int p_i45538_1_, int p_i45538_2_, int p_i45538_3_) {
/* 12 */     super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_175230_c() {
/* 17 */     return this.field_175231_o;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_175229_b(boolean p_175229_1_) {
/* 22 */     this.field_175231_o = p_175229_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 30 */     if (this.visible) {
/*    */       Icon guilockiconbutton$icon;
/* 32 */       mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
/* 33 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 34 */       boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*    */ 
/*    */       
/* 37 */       if (this.field_175231_o) {
/*    */         
/* 39 */         if (!this.enabled)
/*    */         {
/* 41 */           guilockiconbutton$icon = Icon.LOCKED_DISABLED;
/*    */         }
/* 43 */         else if (flag)
/*    */         {
/* 45 */           guilockiconbutton$icon = Icon.LOCKED_HOVER;
/*    */         }
/*    */         else
/*    */         {
/* 49 */           guilockiconbutton$icon = Icon.LOCKED;
/*    */         }
/*    */       
/* 52 */       } else if (!this.enabled) {
/*    */         
/* 54 */         guilockiconbutton$icon = Icon.UNLOCKED_DISABLED;
/*    */       }
/* 56 */       else if (flag) {
/*    */         
/* 58 */         guilockiconbutton$icon = Icon.UNLOCKED_HOVER;
/*    */       }
/*    */       else {
/*    */         
/* 62 */         guilockiconbutton$icon = Icon.UNLOCKED;
/*    */       } 
/*    */       
/* 65 */       drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.func_178910_a(), guilockiconbutton$icon.func_178912_b(), this.width, this.height);
/*    */     } 
/*    */   }
/*    */   
/*    */   enum Icon
/*    */   {
/* 71 */     LOCKED(0, 146),
/* 72 */     LOCKED_HOVER(0, 166),
/* 73 */     LOCKED_DISABLED(0, 186),
/* 74 */     UNLOCKED(20, 146),
/* 75 */     UNLOCKED_HOVER(20, 166),
/* 76 */     UNLOCKED_DISABLED(20, 186);
/*    */     
/*    */     private final int field_178914_g;
/*    */     
/*    */     private final int field_178920_h;
/*    */     
/*    */     Icon(int p_i45537_3_, int p_i45537_4_) {
/* 83 */       this.field_178914_g = p_i45537_3_;
/* 84 */       this.field_178920_h = p_i45537_4_;
/*    */     }
/*    */ 
/*    */     
/*    */     public int func_178910_a() {
/* 89 */       return this.field_178914_g;
/*    */     }
/*    */ 
/*    */     
/*    */     public int func_178912_b() {
/* 94 */       return this.field_178920_h;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiLockIconButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */