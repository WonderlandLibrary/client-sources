package net.minecraft.client.renderer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.util.*;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean hasActivePotionEffects;
    
    public InventoryEffectRenderer(final Container p_i1089_1_) {
        super(p_i1089_1_);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.func_175378_g();
    }
    
    protected void func_175378_g() {
        if (!InventoryEffectRenderer.mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 160 + (InventoryEffectRenderer.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
        }
        else {
            this.guiLeft = (InventoryEffectRenderer.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.hasActivePotionEffects) {
            this.drawActivePotionEffects();
        }
    }
    
    private void drawActivePotionEffects() {
        final int var1 = this.guiLeft - 124;
        int var2 = this.guiTop;
        final boolean var3 = true;
        final Collection var4 = InventoryEffectRenderer.mc.thePlayer.getActivePotionEffects();
        if (!var4.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            int var5 = 33;
            if (var4.size() > 5) {
                var5 = 132 / (var4.size() - 1);
            }
            for (final PotionEffect var7 : InventoryEffectRenderer.mc.thePlayer.getActivePotionEffects()) {
                final Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                InventoryEffectRenderer.mc.getTextureManager().bindTexture(InventoryEffectRenderer.inventoryBackground);
                this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
                if (var8.hasStatusIcon()) {
                    final int var9 = var8.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }
                String var10 = I18n.format(var8.getName(), new Object[0]);
                if (var7.getAmplifier() == 1) {
                    var10 = var10 + " " + I18n.format("enchantment.level.2", new Object[0]);
                }
                else if (var7.getAmplifier() == 2) {
                    var10 = var10 + " " + I18n.format("enchantment.level.3", new Object[0]);
                }
                else if (var7.getAmplifier() == 3) {
                    var10 = var10 + " " + I18n.format("enchantment.level.4", new Object[0]);
                }
                this.fontRendererObj.func_175063_a(var10, (float)(var1 + 10 + 18), (float)(var2 + 6), 16777215);
                final String var11 = Potion.getDurationString(var7);
                this.fontRendererObj.func_175063_a(var11, (float)(var1 + 10 + 18), (float)(var2 + 6 + 10), 8355711);
                var2 += var5;
            }
        }
    }
}
