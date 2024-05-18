package net.minecraft.client.renderer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.util.*;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private static final String[] I;
    private boolean hasActivePotionEffects;
    
    private static void I() {
        (I = new String[0xC4 ^ 0xC2])["".length()] = I("B", "beyTl");
        InventoryEffectRenderer.I[" ".length()] = I("<=1:%7'?7*-}>72<?|`", "YSRRD");
        InventoryEffectRenderer.I["  ".length()] = I("j", "JYHCk");
        InventoryEffectRenderer.I["   ".length()] = I("\u001d\u0003\u0002\u0000\u0014\u0016\u0019\f\r\u001b\fC\r\r\u0003\u001d\u0001O[", "xmahu");
        InventoryEffectRenderer.I[0xA3 ^ 0xA7] = I("T", "tJNGI");
        InventoryEffectRenderer.I[0xB2 ^ 0xB7] = I("\u0015\u001b:\r\u0007\u001e\u00014\u0000\b\u0004[5\u0000\u0010\u0015\u0019wQ", "puYef");
    }
    
    public InventoryEffectRenderer(final Container container) {
        super(container);
    }
    
    static {
        I();
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.updateActivePotionEffects();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        if (this.hasActivePotionEffects) {
            this.drawActivePotionEffects();
        }
    }
    
    protected void updateActivePotionEffects() {
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 83 + 5 - 20 + 92 + (this.width - this.xSize - (36 + 33 + 71 + 60)) / "  ".length();
            this.hasActivePotionEffects = (" ".length() != 0);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.guiLeft = (this.width - this.xSize) / "  ".length();
            this.hasActivePotionEffects = ("".length() != 0);
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void drawActivePotionEffects() {
        final int n = this.guiLeft - (0xD8 ^ 0xA4);
        int guiTop = this.guiTop;
        final Collection<PotionEffect> activePotionEffects = this.mc.thePlayer.getActivePotionEffects();
        if (!activePotionEffects.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            int n2 = 0x19 ^ 0x38;
            if (activePotionEffects.size() > (0x40 ^ 0x45)) {
                n2 = (101 + 91 - 189 + 129) / (activePotionEffects.size() - " ".length());
            }
            final Iterator<PotionEffect> iterator = this.mc.thePlayer.getActivePotionEffects().iterator();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final PotionEffect potionEffect = iterator.next();
                final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.getTextureManager().bindTexture(InventoryEffectRenderer.inventoryBackground);
                this.drawTexturedModalRect(n, guiTop, "".length(), 139 + 159 - 173 + 41, 87 + 120 - 161 + 94, 0x7D ^ 0x5D);
                if (potion.hasStatusIcon()) {
                    final int statusIconIndex = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(n + (0x4F ^ 0x49), guiTop + (0x9D ^ 0x9A), "".length() + statusIconIndex % (0x3C ^ 0x34) * (0x18 ^ 0xA), 124 + 81 - 180 + 173 + statusIconIndex / (0x4B ^ 0x43) * (0xAC ^ 0xBE), 0x6F ^ 0x7D, 0x2F ^ 0x3D);
                }
                String s = I18n.format(potion.getName(), new Object["".length()]);
                if (potionEffect.getAmplifier() == " ".length()) {
                    s = String.valueOf(s) + InventoryEffectRenderer.I["".length()] + I18n.format(InventoryEffectRenderer.I[" ".length()], new Object["".length()]);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else if (potionEffect.getAmplifier() == "  ".length()) {
                    s = String.valueOf(s) + InventoryEffectRenderer.I["  ".length()] + I18n.format(InventoryEffectRenderer.I["   ".length()], new Object["".length()]);
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else if (potionEffect.getAmplifier() == "   ".length()) {
                    s = String.valueOf(s) + InventoryEffectRenderer.I[0x54 ^ 0x50] + I18n.format(InventoryEffectRenderer.I[0x23 ^ 0x26], new Object["".length()]);
                }
                this.fontRendererObj.drawStringWithShadow(s, n + (0x62 ^ 0x68) + (0x82 ^ 0x90), guiTop + (0x2C ^ 0x2A), 15704255 + 180162 - 4496508 + 5389306);
                this.fontRendererObj.drawStringWithShadow(Potion.getDurationString(potionEffect), n + (0x85 ^ 0x8F) + (0x69 ^ 0x7B), guiTop + (0x5 ^ 0x3) + (0xA9 ^ 0xA3), 1507153 + 4002565 - 3563365 + 6409358);
                guiTop += n2;
            }
        }
    }
}
