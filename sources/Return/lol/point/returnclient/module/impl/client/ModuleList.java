package lol.point.returnclient.module.impl.client;

import lol.point.Return;
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
import lol.point.returnclient.util.system.ColorUtil;
import lol.point.returnclient.util.system.StringUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(
        name = "ModuleList",
        description = "draws a list of enabled modules",
        category = Category.CLIENT,
        hidden = true
)
public class ModuleList extends Module {

    private final StringSetting shownModules = new StringSetting("Shown modules", "Useful", new String[]{"All", "Useful", "Bound"});

    private final NumberSetting xOffset = new NumberSetting("X offset", 5, 0, 10);
    private final NumberSetting yOffset = new NumberSetting("Y offset", 5, 0, 10);

    private final StringSetting moduleColor = new StringSetting("Module color", new String[]{"Follow theme", "Static", "Gradient", "Rainbow"});
    private final ColorSetting staticColor = new ColorSetting("Static color", new Color(255, 255, 255)).hideSetting(() -> !moduleColor.is("Static"));
    private final ColorSetting gradientColor1 = new ColorSetting("Gradient color 1", new Color(0, 0, 255)).hideSetting(() -> !moduleColor.is("Gradient"));
    private final ColorSetting gradientColor2 = new ColorSetting("Gradient color 2", new Color(255, 255, 255)).hideSetting(() -> !moduleColor.is("Gradient"));
    private final StringSetting font = StringSetting.fontSetting("Font", "productsans-Regular");
    private final NumberSetting fontSize = new NumberSetting("Font size", 21, 10, 25, 0);
    private final BooleanSetting fontShadow = new BooleanSetting("Font shadow", true);
    private final BooleanSetting background = new BooleanSetting("Background", false);
    private final NumberSetting backgroundOpacity = new NumberSetting("Background Opacity", 109, 0, 255, 0);

    private final BooleanSetting suffix = new BooleanSetting("Suffix", false);
    private final StringSetting suffixSeparator = new StringSetting("Suffix separator", "-", new String[]{"none", "-", "#", "/", "+", "@", "*", "%", "="}).hideSetting(() -> !suffix.value);
    private final StringSetting suffixDecoration = new StringSetting("Suffix decoration", new String[]{"none", "(suffix)", "[suffix]", "{suffix}", "<suffix>", "<-suffix->", "/suffix\\"}).hideSetting(() -> !suffix.value);
    private final StringSetting suffixColor = new StringSetting("Suffix color", "White", new String[]{"Default", "Light gray", "Dark gray", "White", "Black"}).hideSetting(() -> !suffix.value);
    private final BooleanSetting nameSpacing = new BooleanSetting("Name spacing", false);
    private final StringSetting caseMode = new StringSetting("Case", new String[]{"Regular", "Lowercase", "Uppercase"});

    public ModuleList() {
        addSettings(shownModules, xOffset, yOffset,
                moduleColor, staticColor, gradientColor1, gradientColor2,
                font, fontSize, fontShadow,
                background, backgroundOpacity,
                suffix, suffixSeparator, suffixDecoration, suffixColor,
                nameSpacing, caseMode);
    }

    @Subscribe
    private final Listener<EventRender2D> onRender = new Listener<>(eventRender2D -> {
        final FastFontRenderer customFont = Return.INSTANCE.fontManager.getFont(font.value + " " + fontSize.value.intValue());
        ScaledResolution sr = eventRender2D.scaledResolution;

        float xOffset = this.xOffset.value.floatValue() + 2;
        float yOffset = this.yOffset.value.floatValue();

        List<Module> modules = Return.INSTANCE.moduleManager.getEnabledModules();
        Comparator<Module> stringWidthComparator = Comparator.comparingDouble(module -> customFont.getWidth(getNewModuleName(module)));
        modules.sort(stringWidthComparator.reversed());

        modules.removeIf(module -> module.hidden);

        switch (shownModules.value) {
            case "Useful" -> {
                modules.removeIf(module -> module.category == Category.RENDER);
                modules.removeIf(module -> module.category == Category.CLIENT);
            }
            case "Bound" -> modules.removeIf(module -> module.key == 0);
        }

        int counter = 0;
        for (Module module : modules) {
            int color;
            color = switch (moduleColor.value) {
                case "Follow theme" -> {
                    Color gradient1, gradient2;
                    gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
                    gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

                    yield ColorUtil.fadeBetween(gradient1.getRGB(), gradient2.getRGB(), (counter * 150L));
                }
                case "Rainbow" -> ColorUtil.getRainbowColor(counter);
                case "Gradient" ->
                        ColorUtil.fadeBetween(gradientColor1.color.getRGB(), gradientColor2.color.getRGB(), (counter * 150L));
                case "Static" -> staticColor.color.getRGB();
                default -> -1;
            };

            float x = sr.getScaledWidth() - customFont.getWidth(getNewModuleName(module)) - xOffset, y = yOffset;

            if (background.value) {
                float textWidth = customFont.getWidth(getNewModuleName(module));
                float adjustedWidth = textWidth + 2;

                RenderUtil.rectangle(x, y, adjustedWidth, customFont.getHeight(getNewModuleName(module)) + 1.2, true, new Color(0, 0, 0, backgroundOpacity.value.intValue()));
            }

            customFont.drawString(getNewModuleName(module), x + 1, y + 0.5f, color, fontShadow.value);
            yOffset += customFont.getHeight(getNewModuleName(module)) + 1.2f;
            counter++;
        }
    });

    private String getNewModuleName(Module module) {
        if (module == null || module.name.isEmpty()) {
            return null;
        }

        String moduleName = module.name;
        String spacedModuleName = nameSpacing.value ? moduleName.replaceAll("([A-Z]+)", " $1") : moduleName;

        if (suffix.value && module.getSuffix() != null) {
            spacedModuleName += StringUtil.getColorCodeFromString(suffixColor.value) + (suffixSeparator.is("none") ? "" : " " + suffixSeparator.value);
            switch (suffixDecoration.value) {
                case "(suffix)" -> spacedModuleName += " (" + module.getSuffix() + ")";
                case "[suffix]" -> spacedModuleName += " [" + module.getSuffix() + "]";
                case "{suffix}" -> spacedModuleName += " {" + module.getSuffix() + "}";
                case "<suffix>" -> spacedModuleName += " <" + module.getSuffix() + ">";
                case "<-suffix->" -> spacedModuleName += " <-" + module.getSuffix() + "->";
                case "/suffix\\" -> spacedModuleName += " /" + module.getSuffix() + "\\";
                case "none" -> spacedModuleName += " " + module.getSuffix();
            }
        }

        spacedModuleName = spacedModuleName.trim();

        return switch (caseMode.value) {
            default -> spacedModuleName;
            case "Lowercase" -> spacedModuleName.toLowerCase();
            case "Uppercase" -> spacedModuleName.toUpperCase();
        };
    }

    @Subscribe
    private Listener<EventGlow> onGlow = new Listener<>(event -> {
        final FastFontRenderer customFont = Return.INSTANCE.fontManager.getFont(font.value + " " + fontSize.value.intValue());
        ScaledResolution sr = new ScaledResolution(mc);

        float xOffset = this.xOffset.value.floatValue() + 2;
        float yOffset = this.yOffset.value.floatValue();

        List<Module> modules = Return.INSTANCE.moduleManager.getEnabledModules();
        Comparator<Module> stringWidthComparator = Comparator.comparingDouble(module -> customFont.getWidth(getNewModuleName(module)));
        modules.sort(stringWidthComparator.reversed());

        modules.removeIf(module -> module.hidden);

        switch (shownModules.value) {
            case "Useful" -> {
                modules.removeIf(module -> module.category == Category.RENDER);
                modules.removeIf(module -> module.category == Category.CLIENT);
            }
            case "Bound" -> modules.removeIf(module -> module.key == 0);
        }

        int counter = 0;
        for (Module module : modules) {
            int color;
            color = switch (moduleColor.value) {
                case "Follow theme" -> {
                    Color gradient1, gradient2;
                    gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
                    gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

                    yield ColorUtil.fadeBetween(gradient1.getRGB(), gradient2.getRGB(), (counter * 150L));
                }
                case "Rainbow" -> ColorUtil.getRainbowColor(counter);
                case "Gradient" ->
                        ColorUtil.fadeBetween(gradientColor1.color.getRGB(), gradientColor2.color.getRGB(), (counter * 150L));
                case "Static" -> staticColor.color.getRGB();
                default -> -1;
            };

            float x = sr.getScaledWidth() - customFont.getWidth(getNewModuleName(module)) - xOffset, y = yOffset;

            if (background.value) {
                float textWidth = customFont.getWidth(getNewModuleName(module));
                float adjustedWidth = textWidth + 2;

                RenderUtil.rectangle(x, y, adjustedWidth, customFont.getHeight(getNewModuleName(module)) + 1.2, color);
            } else {
                customFont.drawString(getNewModuleName(module), x + 2, y + 0.5f, color, fontShadow.value);
            }

            yOffset += customFont.getHeight(getNewModuleName(module)) + 1.2f;
            counter++;
        }
    });
}
