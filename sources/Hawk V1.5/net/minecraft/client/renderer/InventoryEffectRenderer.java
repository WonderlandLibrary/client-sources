package net.minecraft.client.renderer;

import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer extends GuiContainer {
   private static final String __OBFID = "CL_00000755";
   private boolean hasActivePotionEffects;

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      if (this.hasActivePotionEffects) {
         this.drawActivePotionEffects();
      }

   }

   protected void func_175378_g() {
      if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
         this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
         this.hasActivePotionEffects = true;
      } else {
         this.guiLeft = (this.width - this.xSize) / 2;
         this.hasActivePotionEffects = false;
      }

   }

   public InventoryEffectRenderer(Container var1) {
      super(var1);
   }

   private void drawActivePotionEffects() {
      int var1 = this.guiLeft - 124;
      int var2 = this.guiTop;
      boolean var3 = true;
      Collection var4 = this.mc.thePlayer.getActivePotionEffects();
      if (!var4.isEmpty()) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int var5 = 33;
         if (var4.size() > 5) {
            var5 = 132 / (var4.size() - 1);
         }

         for(Iterator var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5) {
            PotionEffect var7 = (PotionEffect)var6.next();
            Potion var8 = Potion.potionTypes[var7.getPotionID()];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(inventoryBackground);
            this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
            if (var8.hasStatusIcon()) {
               int var9 = var8.getStatusIconIndex();
               this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
            }

            String var11 = I18n.format(var8.getName());
            if (var7.getAmplifier() == 1) {
               var11 = String.valueOf((new StringBuilder(String.valueOf(var11))).append(" ").append(I18n.format("enchantment.level.2")));
            } else if (var7.getAmplifier() == 2) {
               var11 = String.valueOf((new StringBuilder(String.valueOf(var11))).append(" ").append(I18n.format("enchantment.level.3")));
            } else if (var7.getAmplifier() == 3) {
               var11 = String.valueOf((new StringBuilder(String.valueOf(var11))).append(" ").append(I18n.format("enchantment.level.4")));
            }

            this.fontRendererObj.drawStringWithShadow(var11, (double)((float)(var1 + 10 + 18)), (double)((float)(var2 + 6)), 16777215);
            String var10 = Potion.getDurationString(var7);
            this.fontRendererObj.drawStringWithShadow(var10, (double)((float)(var1 + 10 + 18)), (double)((float)(var2 + 6 + 10)), 8355711);
         }
      }

   }

   public void initGui() {
      super.initGui();
      this.func_175378_g();
   }
}
