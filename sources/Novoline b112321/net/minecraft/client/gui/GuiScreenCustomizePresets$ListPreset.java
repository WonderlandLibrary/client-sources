package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiScreenCustomizePresets;
import net.minecraft.client.gui.GuiScreenCustomizePresets$Info;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

class GuiScreenCustomizePresets$ListPreset extends GuiSlot {
   public int field_178053_u;
   final GuiScreenCustomizePresets this$0;

   public GuiScreenCustomizePresets$ListPreset(GuiScreenCustomizePresets var1) {
      super(var1.mc, var1.width, var1.height, 80, var1.height - 32, 38);
      this.this$0 = var1;
      this.field_178053_u = -1;
   }

   protected int getSize() {
      return GuiScreenCustomizePresets.access$000().size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.field_178053_u = var1;
      this.this$0.func_175304_a();
      GuiScreenCustomizePresets.access$200(this.this$0).setText(((GuiScreenCustomizePresets$Info)GuiScreenCustomizePresets.access$000().get(GuiScreenCustomizePresets.access$100(this.this$0).field_178053_u)).field_178954_c.toString());
   }

   protected boolean isSelected(int var1) {
      return var1 == this.field_178053_u;
   }

   protected void drawBackground() {
   }

   private void func_178051_a(int var1, int var2, ResourceLocation var3) {
      int var4 = var1 + 5;
      this.this$0.drawHorizontalLine(var4 - 1, var4 + 32, var2 - 1, -2039584);
      this.this$0.drawHorizontalLine(var4 - 1, var4 + 32, var2 + 32, -6250336);
      this.this$0.drawVerticalLine(var4 - 1, var2 - 1, var2 + 32, -2039584);
      this.this$0.drawVerticalLine(var4 + 32, var2 - 1, var2 + 32, -6250336);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(var3);
      boolean var5 = true;
      boolean var6 = true;
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      var8.begin(7, DefaultVertexFormats.POSITION_TEX);
      var8.pos((double)(var4 + 0), (double)(var2 + 32), 0.0D).tex(0.0D, 1.0D).endVertex();
      var8.pos((double)(var4 + 32), (double)(var2 + 32), 0.0D).tex(1.0D, 1.0D).endVertex();
      var8.pos((double)(var4 + 32), (double)(var2 + 0), 0.0D).tex(1.0D, 0.0D).endVertex();
      var8.pos((double)(var4 + 0), (double)(var2 + 0), 0.0D).tex(0.0D, 0.0D).endVertex();
      var7.draw();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      GuiScreenCustomizePresets$Info var7 = (GuiScreenCustomizePresets$Info)GuiScreenCustomizePresets.access$000().get(var1);
      this.func_178051_a(var2, var3, var7.field_178953_b);
      this.this$0.fontRendererObj.drawString(var7.field_178955_a, (float)(var2 + 32 + 10), (float)(var3 + 14), 16777215);
   }
}
