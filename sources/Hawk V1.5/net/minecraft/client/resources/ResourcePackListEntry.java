package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
   protected final GuiScreenResourcePacks field_148315_b;
   private static final ResourceLocation field_148316_c = new ResourceLocation("textures/gui/resource_packs.png");
   protected final Minecraft field_148317_a;
   private static final String __OBFID = "CL_00000821";

   protected boolean func_148314_g() {
      List var1 = this.field_148315_b.func_146962_b(this);
      int var2 = var1.indexOf(this);
      return var2 > 0 && ((ResourcePackListEntry)var1.get(var2 - 1)).func_148310_d();
   }

   public void setSelected(int var1, int var2, int var3) {
   }

   protected abstract void func_148313_c();

   protected boolean func_148307_h() {
      List var1 = this.field_148315_b.func_146962_b(this);
      int var2 = var1.indexOf(this);
      return var2 >= 0 && var2 < var1.size() - 1 && ((ResourcePackListEntry)var1.get(var2 + 1)).func_148310_d();
   }

   protected boolean func_148310_d() {
      return true;
   }

   public ResourcePackListEntry(GuiScreenResourcePacks var1) {
      this.field_148315_b = var1;
      this.field_148317_a = Minecraft.getMinecraft();
   }

   protected boolean func_148308_f() {
      return this.field_148315_b.hasResourcePackEntry(this);
   }

   protected boolean func_148309_e() {
      return !this.field_148315_b.hasResourcePackEntry(this);
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.func_148313_c();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      int var9;
      if ((this.field_148317_a.gameSettings.touchscreen || var8) && this.func_148310_d()) {
         this.field_148317_a.getTextureManager().bindTexture(field_148316_c);
         Gui.drawRect((double)var2, (double)var3, (double)(var2 + 32), (double)(var3 + 32), -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var10 = var6 - var2;
         var9 = var7 - var3;
         if (this.func_148309_e()) {
            if (var10 < 32) {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if (this.func_148308_f()) {
               if (var10 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148314_g()) {
               if (var10 < 32 && var10 > 16 && var9 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148307_h()) {
               if (var10 < 32 && var10 > 16 && var9 > 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      String var13 = this.func_148312_b();
      var9 = this.field_148317_a.fontRendererObj.getStringWidth(var13);
      if (var9 > 157) {
         var13 = String.valueOf((new StringBuilder(String.valueOf(this.field_148317_a.fontRendererObj.trimStringToWidth(var13, 157 - this.field_148317_a.fontRendererObj.getStringWidth("..."))))).append("..."));
      }

      this.field_148317_a.fontRendererObj.drawStringWithShadow(var13, (double)((float)(var2 + 32 + 2)), (double)((float)(var3 + 1)), 16777215);
      List var11 = this.field_148317_a.fontRendererObj.listFormattedStringToWidth(this.func_148311_a(), 157);

      for(int var12 = 0; var12 < 2 && var12 < var11.size(); ++var12) {
         this.field_148317_a.fontRendererObj.drawStringWithShadow((String)var11.get(var12), (double)((float)(var2 + 32 + 2)), (double)((float)(var3 + 12 + 10 * var12)), 8421504);
      }

   }

   protected abstract String func_148312_b();

   protected abstract String func_148311_a();

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.func_148310_d() && var5 <= 32) {
         if (this.func_148309_e()) {
            this.field_148315_b.func_146962_b(this).remove(this);
            this.field_148315_b.func_146963_h().add(0, this);
            this.field_148315_b.func_175288_g();
            return true;
         }

         if (var5 < 16 && this.func_148308_f()) {
            this.field_148315_b.func_146962_b(this).remove(this);
            this.field_148315_b.func_146964_g().add(0, this);
            this.field_148315_b.func_175288_g();
            return true;
         }

         List var7;
         int var8;
         if (var5 > 16 && var6 < 16 && this.func_148314_g()) {
            var7 = this.field_148315_b.func_146962_b(this);
            var8 = var7.indexOf(this);
            var7.remove(this);
            var7.add(var8 - 1, this);
            this.field_148315_b.func_175288_g();
            return true;
         }

         if (var5 > 16 && var6 > 16 && this.func_148307_h()) {
            var7 = this.field_148315_b.func_146962_b(this);
            var8 = var7.indexOf(this);
            var7.remove(this);
            var7.add(var8 + 1, this);
            this.field_148315_b.func_175288_g();
            return true;
         }
      }

      return false;
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }
}
