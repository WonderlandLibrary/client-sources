package me.travis.wurstplus.gui.wurstplus.theme.wurstplus;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.gui.wurstplus.component.ActiveModules;
import me.travis.wurstplus.gui.font.CFontRenderer;
import me.travis.wurstplus.gui.rgui.component.AlignedComponent;
import me.travis.wurstplus.gui.rgui.render.AbstractComponentUI;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.util.ColourUtils;
import me.travis.wurstplus.util.Wrapper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

public class wurstplusActiveModulesUI extends AbstractComponentUI<ActiveModules> {

    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Comic Sans MS", 0, 18), true, false);
    
    @Override
    public void renderComponent(ActiveModules component, FontRenderer f) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        FontRenderer renderer = Wrapper.getFontRenderer();
        List<Module> mods = ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparing(module -> cFontRenderer.getStringWidth(module.getName()+(module.getHudInfo()==null?"":module.getHudInfo()+" "))*(component.sort_up?-1:1)))
                .collect(Collectors.toList());

        final int[] y = {2};

        if (component.getParent().getY() < 26 && Wrapper.getPlayer().getActivePotionEffects().size()>0 && component.getParent().getOpacity() == 0)
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());

        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};

        boolean lAlign = component.getAlignment() == AlignedComponent.Alignment.LEFT;
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

        mods.stream().forEach(module -> {
            int rgb = Color.HSBtoRGB(hue[0], 1, 1);
            String s = module.getHudInfo();
            String text = module.getName() + (s==null?"" : " " + Command.SECTIONSIGN() + "7" + s);
            int textwidth = cFontRenderer.getStringWidth(text);
            int textheight = renderer.getFontHeight()+1;
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;
            final int trgb = ColourUtils.toRGBA(red, green, blue, 255);
            cFontRenderer.drawStringWithShadow(text, xFunc.apply(textwidth), y[0], trgb);
            float[] arrf = hue;
            arrf[0] = arrf[0] + 0.02f;
            int[] arrn = y;
            arrn[0] = arrn[0] + textheight;
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
