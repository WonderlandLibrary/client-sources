package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.util.*;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean field_74222_o;
    
    public InventoryEffectRenderer(final Container par1Container) {
        super(par1Container);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (!Minecraft.thePlayer.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
            this.field_74222_o = true;
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        if (this.field_74222_o) {
            this.displayDebuffEffects();
        }
    }
    
    private void displayDebuffEffects() {
        final int var1 = this.guiLeft - 124;
        int var2 = this.guiTop;
        final Collection var3 = Minecraft.thePlayer.getActivePotionEffects();
        if (!var3.isEmpty()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
            int var4 = 33;
            if (var3.size() > 5) {
                var4 = 132 / (var3.size() - 1);
            }
            for (final PotionEffect var6 : Minecraft.thePlayer.getActivePotionEffects()) {
                final Potion var7 = Potion.potionTypes[var6.getPotionID()];
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.renderEngine.bindTexture("/gui/inventory.png");
                this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
                if (var7.hasStatusIcon()) {
                    final int var8 = var7.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var8 % 8 * 18, 198 + var8 / 8 * 18, 18, 18);
                }
                String var9 = StatCollector.translateToLocal(var7.getName());
                if (var6.getAmplifier() == 1) {
                    var9 = String.valueOf(var9) + " II";
                }
                else if (var6.getAmplifier() == 2) {
                    var9 = String.valueOf(var9) + " III";
                }
                else if (var6.getAmplifier() == 3) {
                    var9 = String.valueOf(var9) + " IV";
                }
                this.fontRenderer.drawStringWithShadow(var9, var1 + 10 + 18, var2 + 6, 16777215);
                final String var10 = Potion.getDurationString(var6);
                this.fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
                var2 += var4;
            }
        }
    }
}
