package lol.point.returnclient.module.impl.client;

import lol.point.Return;
import lol.point.returnclient.events.impl.render.EventBlur;
import lol.point.returnclient.events.impl.render.EventGlow;
import lol.point.returnclient.events.impl.render.EventRender2D;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.ColorSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.render.FastFontRenderer;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

import java.awt.*;

@ModuleInfo(
        name = "Watermark",
        description = "draws a distinctive watermark",
        category = Category.CLIENT,
        hidden = true
)
public class Watermark extends Module {
    private final StringSetting mode = new StringSetting("Design", new String[]{"Modern", "Box", "Logo", "Simple", "Moropheles"});

    private final NumberSetting xOffset = new NumberSetting("X offset", 5, 0, 10);
    private final NumberSetting yOffset = new NumberSetting("Y offset", 5, 0, 10);

    private final BooleanSetting version = new BooleanSetting("Show version", true).hideSetting(() -> !mode.is("Simple"));
    private final BooleanSetting customColor = new BooleanSetting("Use custom color", false);
    private final ColorSetting color = new ColorSetting("Custom color", new Color(255, 3, 3)).hideSetting(() -> !customColor.value);

    private final FastFontRenderer modern = Return.INSTANCE.fontManager.getFont("Nano-Regular 48");
    private final FastFontRenderer box = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private final FastFontRenderer big = Return.INSTANCE.fontManager.getFont("Nano-Regular 200");
    private final FastFontRenderer small = Return.INSTANCE.fontManager.getFont("Nano-Regular 20");
    private final FastFontRenderer moropheles = Return.INSTANCE.fontManager.getFont("Roboto-Regular 20");
    private final FastFontRenderer morophelesSession = Return.INSTANCE.fontManager.getFont("Roboto-Regular 16");

    public Watermark() {
        addSettings(mode, xOffset, yOffset, version, customColor, color);
    }

    @Subscribe
    private final Listener<EventRender2D> onRender = new Listener<>(eventRender2D -> {
        Color gradient1, gradient2;
        if (customColor.value) {
            gradient1 = color.color;
            gradient2 = color.color.darker().darker();
        } else {
            gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
            gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;
        }

        switch (mode.value) {
            case "Simple" -> mc.fontRendererObj.drawStringWithShadow("R§feturn " + (version.value ? Return.INSTANCE.version : ""), xOffset.value.floatValue(), yOffset.value.floatValue(), gradient1.getRGB());
            case "Logo" -> {
                big.drawStringWithShadow("R", xOffset.value.floatValue(), -20 + yOffset.value.floatValue(), gradient1);
                small.drawStringWithShadow("eturn", xOffset.value.floatValue() + 14, -20 + yOffset.value.floatValue() + 67, gradient2);
            }
            case "Modern" -> modern.drawStringWithShadow("R§feturn", xOffset.value.floatValue(), yOffset.value.floatValue(), gradient1);
            case "Box" -> {
                String text = "Return Client | " + Return.INSTANCE.version + " | " + mc.session.getUsername();
                float width = box.getWidth(text) + 3;
                RenderUtil.rectangle(xOffset.value.floatValue() - 2, yOffset.value.floatValue() + 2, width, 16, 0);
                RenderUtil.gradientH(xOffset.value.floatValue() - 2, yOffset.value.floatValue(), width, 2, gradient1, gradient2);
                RenderUtil.gradientH(xOffset.value.floatValue() - 2, yOffset.value.floatValue() - 2 + 20, width, 2, gradient2, gradient1);
                box.drawString(text, xOffset.value.floatValue(), yOffset.value.floatValue() + 5.5f, new Color(255, 255, 255));
            }
            case "Moropheles" -> {
                moropheles.drawStringWithShadow("Moropheles", xOffset.value.floatValue(), yOffset.value.floatValue(), -1);

                RenderUtil.gradientH(xOffset.value.floatValue(), yOffset.value.floatValue() + moropheles.getHeight("Moropheles") + 4, 120, 1, gradient1, gradient2);
                RenderUtil.gradientV(xOffset.value.floatValue(), yOffset.value.floatValue() + moropheles.getHeight("Moropheles") + 4, 1, 60, gradient1, gradient2);
                RenderUtil.rectangle(xOffset.value.floatValue() + 1, yOffset.value.floatValue() + moropheles.getHeight("Moropheles") + 5, 120, 60, new Color(0,0,0, 20));
            }
        }
    });

    @Subscribe
    private final Listener<EventBlur> onBlur = new Listener<>(event -> {
        switch (mode.value) {
            case "Moropheles" -> {
                RenderUtil.rectangle(xOffset.value.floatValue(), yOffset.value.floatValue()+ moropheles.getHeight("Moropheles") + 5, 120, 60, new Color(0,0,0, 20));
            }
        }
    });

    @Subscribe
    private final Listener<EventGlow> onGlow = new Listener<>(event -> {
        Color gradient1, gradient2;

        if (customColor.value) {
            gradient1 = color.color;
            gradient2 = color.color.darker().darker();
        } else {
            gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
            gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;
        }

        switch (mode.value) {
            case "Simple" -> mc.fontRendererObj.drawStringWithShadow("R§feturn " + (version.value ? Return.INSTANCE.version : ""), xOffset.value.floatValue(), yOffset.value.floatValue(), gradient1.getRGB());
            case "Logo" -> {
                big.drawStringWithShadow("R", xOffset.value.floatValue(), -20 + yOffset.value.floatValue(), gradient1);
                small.drawStringWithShadow("eturn", xOffset.value.floatValue() + 14, -20 + yOffset.value.floatValue() + 67, gradient2);
            }
            case "Modern" -> modern.drawStringWithShadow("R§feturn", xOffset.value.floatValue(), yOffset.value.floatValue(), gradient1);
            case "Box" -> {
                String text = "Return Client | " + Return.INSTANCE.version + " | " + mc.session.getUsername();
                float width = box.getWidth(text) + 3;

                RenderUtil.gradientH(xOffset.value.floatValue() - 2, yOffset.value.floatValue(), width, 20,
                        gradient1, gradient2);
            }
        }
    });
}
