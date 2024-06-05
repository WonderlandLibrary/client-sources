package net.minecraft.client.renderer;

import java.util.Collection;
import net.aHz;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer extends aHz {
   private boolean hasActivePotionEffects;

   public InventoryEffectRenderer(Container var1) {
      super(var1);
   }

   public void initGui() {
      super.initGui();
      this.updateActivePotionEffects();
   }

   protected void updateActivePotionEffects() {
      this.R = (this.width - this.y) / 2;
      this.hasActivePotionEffects = false;
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      this.drawActivePotionEffects();
   }

   private void drawActivePotionEffects() {
      int var1 = this.R - 124;
      int var2 = this.W;
      Collection var3 = this.mc.player.getActivePotionEffects();
      if(!var3.isEmpty()) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int var4 = 33;
         if(var3.size() > 5) {
            var4 = 132 / (var3.size() - 1);
         }

         for(PotionEffect var6 : this.mc.player.getActivePotionEffects()) {
            Potion var7 = Potion.potionTypes[var6.getPotionID()];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(v);
            this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
            if(var7.hasStatusIcon()) {
               int var8 = var7.getStatusIconIndex();
               this.drawTexturedModalRect(var1 + 6, var2 + 7, var8 % 8 * 18, 198 + var8 / 8 * 18, 18, 18);
            }

            String var10 = I18n.format(var7.getName(), new Object[0]);
            if(var6.getAmplifier() == 1) {
               var10 = var10 + " " + I18n.format("enchantment.level.2", new Object[0]);
            } else if(var6.getAmplifier() == 2) {
               var10 = var10 + " " + I18n.format("enchantment.level.3", new Object[0]);
            } else if(var6.getAmplifier() == 3) {
               var10 = var10 + " " + I18n.format("enchantment.level.4", new Object[0]);
            }

            this.fontRendererObj.drawStringWithShadow(var10, (float)(var1 + 10 + 18), (float)(var2 + 6), 16777215);
            String var9 = Potion.getDurationString(var6);
            this.fontRendererObj.drawStringWithShadow(var9, (float)(var1 + 10 + 18), (float)(var2 + 6 + 10), 8355711);
            var2 += var4;
         }
      }

   }
}
