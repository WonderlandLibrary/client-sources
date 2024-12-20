package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.GuiManager;
import me.zeroeightsix.kami.util.Wrapper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

/**
 * Created by 086 on 4/08/2017.
 * Updated by FINZ0 on 14 December 2019
 */
public class KamiActiveModulesUI extends AbstractComponentUI<ActiveModules> {

    @Override
    public void renderComponent(ActiveModules component, FontRenderer f) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        FontRenderer renderer = Wrapper.getFontRenderer();
        List<Module> mods = ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> renderer.getStringWidth(module.getName() + (module.getHudInfo() == null ? "" : module.getHudInfo() + " ")) * (component.sort_up ? -1 : 1)))
                .collect(Collectors.toList());

        final int[] y = {2};

        if (component.getParent().getY() < 26 && Wrapper.getPlayer().getActivePotionEffects().size() > 0 && component.getParent().getOpacity() == 0) {
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());
        }

        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};

        Function<Integer, Integer> xFunc;
        switch (component.getAlignment()) {
            case RIGHT:
                xFunc = i -> component.getWidth() - i;
                break;
            case CENTER:
                xFunc = i -> component.getWidth() / 2 - i / 2;
                break;
            case LEFT:
            default:
                xFunc = i -> 0;
                break;
        }

        mods.forEach(module -> {

            int rgb = Color.HSBtoRGB(hue[0], 1, 1);
            String s = module.getHudInfo();
            String text = module.getName() + (s == null ? "" : " " + KamiMod.getInstance().guiManager.getTextColor() + s);
            int textwidth = renderer.getStringWidth(text);
            int textheight = renderer.getFontHeight() + 1;

            int red = 0;
            int green = 0;
            int blue = 0;

            if (KamiMod.getInstance().guiManager.getModuleListMode().equals(GuiManager.ModuleListMode.STATIC)) {
                red = KamiMod.getInstance().guiManager.getModuleListRed();
                green = KamiMod.getInstance().guiManager.getModuleListGreen();
                blue = KamiMod.getInstance().guiManager.getModuleListBlue();
            }

            if (KamiMod.getInstance().guiManager.getModuleListMode().equals(GuiManager.ModuleListMode.RAINBOW)) {
                red = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = rgb & 0xFF;
            }

            renderer.drawStringWithShadow(xFunc.apply(textwidth), y[0], red, green, blue, text);
            hue[0] += .02f;
            y[0] += textheight;

        });

        component.setHeight(y[0]);

        GL11.glEnable(GL11.GL_CULL_FACE);
        glDisable(GL_BLEND);
    }

    @Override
    public void handleSizeComponent(ActiveModules component) {
        component.setWidth(100);
        component.setHeight(100);
    }

}
