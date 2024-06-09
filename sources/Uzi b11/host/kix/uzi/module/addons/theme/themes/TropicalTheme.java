package host.kix.uzi.module.addons.theme.themes;

import host.kix.uzi.Uzi;
import host.kix.uzi.module.Module;
import host.kix.uzi.module.addons.theme.ObjectRenderer;
import host.kix.uzi.module.addons.theme.Theme;
import host.kix.uzi.module.addons.theme.components.Watermark;
import host.kix.uzi.module.modules.render.Overlay;
import host.kix.uzi.ui.tab.TabGui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Kix on 6/11/2017.
 * Made for the Uzi Universal project.
 */
public class TropicalTheme extends Theme {

    public TropicalTheme() {
        super("Tropical");
        addRenderers(new WatermarkObjectRenderer());
    }

    private class WatermarkObjectRenderer extends ObjectRenderer<Watermark> {

        public WatermarkObjectRenderer() {
            super(Watermark.class);
        }

        @Override
        protected void render(Watermark object) {
            if (!mc.gameSettings.showDebugInfo) {
                if (Overlay.watermark.getValue())
                    watermark();
                if (Overlay.arrayList.getValue())
                    arrayList();
                if (Overlay.tabbedGui.getValue())
                    TabGui.drawTabGui();
                if (Overlay.coordinates.getValue())
                    coordinates();
                if(Overlay.potionStatus.getValue())
                    potionStatus();
            }
        }

        private void watermark() {
            if (Overlay.edge.getValue()) {
                mc.fontRendererObj.drawStringWithShadow("uzi \2477b" + Uzi.BUILD, 3, 2, 0xFFFFFFFF);
            } else {
                mc.fontRendererObj.drawStringWithShadow("Uzi \2477b" + Uzi.BUILD, 3, 2, 0xFFFFFFFF);
            }
        }

        private void arrayList() {
            ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int yCount = 2;
            ArrayList<Module> mods = new ArrayList<>();
            mods.addAll(Uzi.getInstance().getModuleManager().getContents());
            mods.sort(new TropicalTheme.WatermarkObjectRenderer.ModuleComparator());
            for (Module module : mods) {
                if (module.isEnabled() && !module.isHidden()) {
                    mc.fontRendererObj.drawStringWithShadow(Overlay.edge.getValue() ? module.getName().toLowerCase() : module.getName(), var2.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 1, yCount, module.getColor().getRGB());
                    yCount += mc.fontRendererObj.FONT_HEIGHT;
                }
            }
        }

        private void coordinates() {
            ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            double x = Math.round(mc.thePlayer.posX);
            double y = Math.round(mc.thePlayer.posY);
            double z = Math.round(mc.thePlayer.posZ);
            int locY = scaledRes.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 2;
            if (mc.currentScreen instanceof GuiChat)
                locY = scaledRes.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 16;
            mc.fontRendererObj.drawStringWithShadow(String.format("XYZ: %s, %s, %s", x, y, z).replace(".0", ""), 2, locY, new Color(0xA8A8A8).getRGB());
        }

        private void potionStatus() {
            ScaledResolution scaledResolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int x = scaledResolution.getScaledWidth() - 2;
            int y = scaledResolution.getScaledHeight() - 10;
            if (mc.ingameGUI.getChatGUI().getChatOpen())
                y -= 13;
            for (Object o : mc.thePlayer.getActivePotionEffects()) {
                PotionEffect effect = (PotionEffect) o;
                String name = I18n.format(effect.getEffectName());

                if (effect.getAmplifier() == 1)
                    name = name + " " + I18n.format("enchantment.level.2");
                else if (effect.getAmplifier() == 2)
                    name = name + " " + I18n.format("enchantment.level.3");
                else if (effect.getAmplifier() == 3)
                    name = name + " " + I18n.format("enchantment.level.4");
                else if (effect.getAmplifier() > 0)
                    name = name + " " + (effect.getAmplifier() + 1);

                int var1 = effect.getDuration() / 20;
                int var2 = var1 / 60;
                var1 %= 60;
                char color = '7';
                if (var2 == 0)
                    if (var1 <= 5)
                        color = '4';
                    else if (var1 <= 10)
                        color = 'c';
                    else if (var1 <= 15)
                        color = '6';
                    else if (var1 <= 20)
                        color = 'e';

                name = String.format("%s \247%s%s", name, color, Potion.getDurationString(effect));
                mc.fontRendererObj.drawStringWithShadow(Overlay.edge.getValue() ? name.toLowerCase() : name, x - mc.fontRendererObj.getStringWidth(name), y, Potion.potionTypes[effect.getPotionID()].getLiquidColor());
                y -= 10;
            }

        }


        public class ModuleComparator implements Comparator<Module> {
            @Override
            public int compare(Module o1, Module o2) {
                if (mc.fontRendererObj.getStringWidth(o1.getName()) < mc.fontRendererObj.getStringWidth(o2.getName())) {
                    return 1;
                } else if (mc.fontRendererObj.getStringWidth(o1.getName()) > mc.fontRendererObj.getStringWidth(o2.getName())) {
                    return -1;
                } else {
                    return 0;
                }
            }

        }

    }

}
