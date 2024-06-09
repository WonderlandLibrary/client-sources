// 
// Decompiled by Procyon v0.5.36
// 

package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Wrapper;
import net.minecraft.item.ItemStack;

import java.awt.*;


public class DurabiltyWarning extends Module {

    public DurabiltyWarning() {
        super("DurabilityWarning", Category.COMBAT, "Tells you when your armor gets low");
    }

    private Setting threshold;
    private Setting espG;
    private Setting espB;
    private Setting rainbow;
    private Setting espR;
    Setting x;
    Setting y;
    public void setup() { ;
        threshold = new Setting("DurabiltyPercent", this, 50, 1, 100.0, true, "ArmorWarningThreshold");
        AuroraMod.getInstance().settingsManager.rSetting(threshold);
        rainbow = new Setting("Rainbow", this, false, "ArmorWarningrainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        espR = new Setting("EspRed", this, 255, 0, 255, true, "ArmorWarningRed");
        AuroraMod.getInstance().settingsManager.rSetting(espR);
        espG = new Setting("EspGreen", this, 0, 0, 255, true, "ArmorWarningGreen");
        AuroraMod.getInstance().settingsManager.rSetting(espG);
        espB = new Setting("EspBlue", this, 0, 0, 255, true, "ArmorWarningBlue");
        AuroraMod.getInstance().settingsManager.rSetting(espB);
        x = new Setting("X", this, 2, 0, 1000, true, "ArmorWarningX");
        y = new Setting("Y", this, 12, 0, 1000, true, "ArmorWarningY");
        AuroraMod.getInstance().settingsManager.rSetting(x);
        AuroraMod.getInstance().settingsManager.rSetting(y);
    }

    
    @Override
    public void onRender() {
        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
        int rgb = Color.HSBtoRGB(hue[0], 1, 1);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (this.shouldMend(0) || this.shouldMend(1) || this.shouldMend(2) || this.shouldMend(3)) {
            final String text = "Durability below " + String.valueOf(this.threshold.getValInt() + "%");
            final int divider = getScale();
            if (rainbow.getValBoolean()) {

                DurabiltyWarning.mc.fontRenderer.drawStringWithShadow(text, (int) x.getValDouble(),
                        (int) y.getValDouble(), new Color(r, g, b).getRGB());
            } else {
                DurabiltyWarning.mc.fontRenderer.drawStringWithShadow(text, (int) x.getValDouble(),
                        (int) y.getValDouble(), new Color(espR.getValInt(), espG.getValInt(), espB.getValInt()).getRGB());

            }
        }
    }
    
    private boolean shouldMend(final int i) {
        return ((ItemStack) DurabiltyWarning.mc.player.inventory.armorInventory.get(i)).getMaxDamage()
                != 0 && 100 * ((ItemStack) DurabiltyWarning.mc.player.inventory.armorInventory.get(i)).getItemDamage()
                / ((ItemStack) DurabiltyWarning.mc.player.inventory.armorInventory.get(i)).getMaxDamage()
                > reverseNumber(this.threshold.getValInt(), 1, 100);
    }
    
    public static int reverseNumber(final int num, final int min, final int max) {
        return max + min - num;
    }
    
    public static int getScale() {
        int scaleFactor = 0;
        int scale = Wrapper.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        while (scaleFactor < scale && Wrapper.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }
}
