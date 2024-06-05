package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry$1;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public abstract class ResourcePackListEntry implements GuiListExtended$IGuiListEntry {
   private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
   private static final IChatComponent field_183020_d = new ChatComponentTranslation("resourcePack.incompatible", new Object[0]);
   private static final IChatComponent field_183021_e = new ChatComponentTranslation("resourcePack.incompatible.old", new Object[0]);
   private static final IChatComponent field_183022_f = new ChatComponentTranslation("resourcePack.incompatible.new", new Object[0]);
   protected final Minecraft mc;
   protected final GuiScreenResourcePacks resourcePacksGUI;

   public ResourcePackListEntry(GuiScreenResourcePacks var1) {
      this.resourcePacksGUI = var1;
      this.mc = Minecraft.getInstance();
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = this.func_183019_a();
      if(var9 != 1) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.drawRect(var2 - 1, var3 - 1, var2 + var4 - 9, var3 + var5 + 1, -8978432);
      }

      this.func_148313_c();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      String var10 = this.func_148312_b();
      String var11 = this.func_148311_a();
      if(!this.mc.gameSettings.touchscreen) {
         ;
      }

      if(this.func_148310_d()) {
         this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
         Gui.drawRect(var2, var3, var2 + 32, var3 + 32, -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var12 = var6 - var2;
         int var13 = var7 - var3;
         if(var9 < 1) {
            var10 = field_183020_d.getFormattedText();
            var11 = field_183021_e.getFormattedText();
         } else if(var9 > 1) {
            var10 = field_183020_d.getFormattedText();
            var11 = field_183022_f.getFormattedText();
         }

         if(this.func_148309_e()) {
            if(var12 < 32) {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if(this.func_148308_f()) {
               if(var12 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if(this.func_148314_g()) {
               if(var12 < 32 && var12 > 16 && var13 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if(this.func_148307_h()) {
               if(var12 < 32 && var12 > 16 && var13 > 16) {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      int var15 = this.mc.fontRendererObj.d(var10);
      if(var15 > 157) {
         var10 = this.mc.fontRendererObj.trimStringToWidth(var10, 157 - this.mc.fontRendererObj.d("...")) + "...";
      }

      this.mc.fontRendererObj.drawStringWithShadow(var10, (float)(var2 + 32 + 2), (float)(var3 + 1), 16777215);
      List var16 = this.mc.fontRendererObj.listFormattedStringToWidth(var11, 157);

      for(int var14 = 0; var14 < 2 && var14 < var16.size(); ++var14) {
         this.mc.fontRendererObj.drawStringWithShadow((String)var16.get(var14), (float)(var2 + 32 + 2), (float)(var3 + 12 + 10 * var14), 8421504);
      }

   }

   protected abstract int func_183019_a();

   protected abstract String func_148311_a();

   protected abstract String func_148312_b();

   protected abstract void func_148313_c();

   protected boolean func_148310_d() {
      return true;
   }

   protected boolean func_148309_e() {
      return !this.resourcePacksGUI.hasResourcePackEntry(this);
   }

   protected boolean func_148308_f() {
      return this.resourcePacksGUI.hasResourcePackEntry(this);
   }

   protected boolean func_148314_g() {
      List var1 = this.resourcePacksGUI.getListContaining(this);
      int var2 = var1.indexOf(this);
      return ((ResourcePackListEntry)var1.get(var2 - 1)).func_148310_d();
   }

   protected boolean func_148307_h() {
      List var1 = this.resourcePacksGUI.getListContaining(this);
      int var2 = var1.indexOf(this);
      return var2 < var1.size() - 1 && ((ResourcePackListEntry)var1.get(var2 + 1)).func_148310_d();
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(this.func_148310_d() && var5 <= 32) {
         if(this.func_148309_e()) {
            this.resourcePacksGUI.markChanged();
            int var11 = this.func_183019_a();
            if(var11 != 1) {
               String var13 = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
               String var9 = I18n.format("resourcePack.incompatible.confirm." + (var11 > 1?"new":"old"), new Object[0]);
               this.mc.displayGuiScreen(new GuiYesNo(new ResourcePackListEntry$1(this), var13, var9, 0));
            } else {
               this.resourcePacksGUI.getListContaining(this).remove(this);
               this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
            }

            return true;
         }

         if(var5 < 16 && this.func_148308_f()) {
            this.resourcePacksGUI.getListContaining(this).remove(this);
            this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }

         if(var5 > 16 && var6 < 16 && this.func_148314_g()) {
            List var10 = this.resourcePacksGUI.getListContaining(this);
            int var12 = var10.indexOf(this);
            var10.remove(this);
            var10.add(var12 - 1, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }

         if(var5 > 16 && var6 > 16 && this.func_148307_h()) {
            List var7 = this.resourcePacksGUI.getListContaining(this);
            int var8 = var7.indexOf(this);
            var7.remove(this);
            var7.add(var8 + 1, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }
      }

      return false;
   }

   public void setSelected(int var1, int var2, int var3) {
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }
}
