package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

import java.util.Collection;

/**
 * @author avox | lmao
 * @since on 11.08.2020.
 */
public class Effects extends Mod {

    Gui gui = new Gui();

    public Effects() {
        super("Effects", Keyboard.KEY_NONE, false, ModCategory.HUD);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void drawHUD(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.RENDER2D)
            return;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        int i = Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom() ? scaledResolution.getScaledWidth() / 2 - 91: 6; // Left
        int j = Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom() ? Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT:  165; // Top
        int longest = 0;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();

        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;

            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }

            for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];

                if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                    String s1 = I18n.format(potion.getName(), new Object[0]);

                    if (potioneffect.getAmplifier() == 1) {
                        s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 2) {
                        s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 3) {
                        s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                    }

                    if (longest < mc.fontRendererObj.getStringWidth(s1))
                        longest = mc.fontRendererObj.getStringWidth(s1);
                    mc.fontRendererObj.drawStringWithShadow(s1 + " " +  Potion.getDurationString(potioneffect), (float) (i)-mc.fontRendererObj.getStringWidth(s1 + " " +  Potion.getDurationString(potioneffect)) - 2, scaledResolution.getScaledHeight() - j, 16777215);
                    j += mc.fontRendererObj.FONT_HEIGHT;
                } else {


                    Gui.drawRect(i, j + 2, longest, j + 28, 0xFF202020);

                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                    if (potion.hasStatusIcon()) {
                        int i1 = potion.getStatusIconIndex();
                        gui.drawTexturedModalRect(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                    }

                    String s1 = I18n.format(potion.getName(), new Object[0]);

                    if (potioneffect.getAmplifier() == 1) {
                        s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 2) {
                        s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 3) {
                        s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                    }

                    if (longest < mc.fontRendererObj.getStringWidth(s1))
                        longest = mc.fontRendererObj.getStringWidth(s1);
                    mc.fontRendererObj.drawStringWithShadow(s1, (float) (i + 10 + 18), (float) (j + 6), 16777215);
                    String s = Potion.getDurationString(potioneffect);
                    mc.fontRendererObj.drawStringWithShadow(s, (float) (i + 10 + 18), (float) (j + 6 + 10), 8355711);
                    j += l;
                }
            }
        }
    }
}