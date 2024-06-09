package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.Render2DEvent;
import io.github.liticane.clients.feature.event.impl.render.ShaderEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.util.player.ColorUtil;
import me.Godwhitelight.CustomFontRenderer;
import me.Godwhitelight.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

//todo: Fix the rainbow and color being gofyy ah
@Module.Info(name = "Arraylist", category = Module.Category.VISUAL, autoEnabled = true)
public class Arraylist extends Module {
    public StringProperty font = new StringProperty("Font", this, "Product", "Product", "Vanilla");
    private final BooleanProperty background = new BooleanProperty("Background", this, false);
    private final BooleanProperty outline = new BooleanProperty("Outline", this, false);
    public NumberProperty alpha = new NumberProperty("Alpha", this, 100, 0, 255, 5);
    private int textColor;

    @SubscribeEvent
    private final EventListener<ShaderEvent> onShader = e -> {
        ScaledResolution resolution = new ScaledResolution(IMethods.mc);
        int index = 0;

        List<Module> list = Client.INSTANCE.getModuleManager().stream()
                .filter(Module::isToggled)
                .sorted(Comparator.comparingDouble(module -> -FontUtil.product.getStringWidth(module.getDisplayName())))
                .toList();

        for (Module module : list) {
            String moduleName = module.getDisplayName();
            int moduleWidth = FontUtil.product.getStringWidth(moduleName) + 4;
            int bgX = resolution.getScaledWidth() - moduleWidth - 2;
            int bgY = 2 + index * (FontUtil.product.getHeight() + 4);
            if (outline.isToggled()) {
                Gui.drawRect(958, bgY - 2, bgX + moduleWidth + 2, bgY + FontUtil.product.getHeight() + 2, textColor);
            }

            if (background.isToggled()) {
                int backroundcolor = e.isBloom()
                        ? Client.INSTANCE.getModuleManager().get(Effects.class).arraylistcolor.is("Black")
                                ? new Color(0, 0, 0, 255).getRGB()
                                : textColor
                        : Color.WHITE.getRGB();
                Gui.drawRect(bgX, bgY - 2, bgX + moduleWidth, bgY + FontUtil.product.getHeight() + 2, backroundcolor);
            }

            if (!background.isToggled()) {
                FontUtil.product.drawStringWithShadow(
                        moduleName,
                        bgX + 2,
                        bgY + 1,
                        textColor
                );
            }

            ++index;
        }

    };

    @SubscribeEvent
    private final EventListener<Render2DEvent> onRender2D = e -> {
        ScaledResolution resolution = new ScaledResolution(IMethods.mc);
        int index = 0;

        List<Module> list = Client.INSTANCE.getModuleManager().stream()
                .filter(Module::isToggled)
                .sorted(Comparator.comparingDouble(module -> -FontUtil.product.getStringWidth(module.getDisplayName())))
                .toList();

        for (Module module : list) {
            textColor = Client.INSTANCE.getModuleManager().get(Theme.class).rainbow.isToggled() ?  ColorUtil.rainbow(3 + (index * 20), 0.6f, Client.INSTANCE.getModuleManager().get(Theme.class).speed.getValue()).getRGB() : ColorUtil.interpolateColorsBackAndForth((int)Client.INSTANCE.getModuleManager().get(Theme.class).speed.getValue(), 3 + (index * 20), Client.INSTANCE.getThemeManager().getTheme().getFirstColor(), Client.INSTANCE.getThemeManager().getTheme().getSecondColor(), false).getRGB();
            String moduleName = module.getDisplayName();
            int moduleWidth = FontUtil.product.getStringWidth(moduleName) + 4;
            int bgX = resolution.getScaledWidth() - moduleWidth - 2;
            int bgY = 2 + index * (FontUtil.product.getHeight() + 4);
            if(background.isToggled()) {
                Gui.drawRect(bgX, bgY - 2, bgX + moduleWidth, bgY + FontUtil.product.getHeight() + 2, new Color(0, 0, 0, (int)alpha.getValue()).getRGB());
            }
            if(outline.isToggled()) {
                Gui.drawRect(958, bgY - 2, bgX + moduleWidth + 2, bgY + FontUtil.product.getHeight() + 2, textColor);
            }
            FontUtil.product.drawStringWithShadow(
                    moduleName,
                    bgX + 2,
                    bgY + 1,
                    textColor
            );

            ++index;
        }
    };

}
