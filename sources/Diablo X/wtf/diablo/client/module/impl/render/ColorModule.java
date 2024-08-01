package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.render.ColorUtil;

import java.awt.*;

import static wtf.diablo.client.util.render.ColorUtil.PRIMARY_MAIN_COLOR;
import static wtf.diablo.client.util.render.ColorUtil.SECONDARY_MAIN_COLOR;
@ModuleMetaData(
        name = "Color",
        description = "Changes the color of the client GUI",
        category = ModuleCategoryEnum.RENDER
)
public final class ColorModule extends AbstractModule {
    private static final ModeSetting<ColorMode> colorMode = new ModeSetting<>("Color Mode", ColorMode.RAINBOW);
    private static final ColorSetting primaryColor = new ColorSetting("Primary Color", Color.WHITE);
    private static final ColorSetting secondaryColor = new ColorSetting("Secondary Color", Color.WHITE);

    public ColorModule() {
        this.registerSettings(colorMode, primaryColor, secondaryColor);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.toggle();
    }

    public static Color getPrimaryColor() {
        return primaryColor.getValue();
    }

    public static int getColor(final int offset) {
        final ArrayListModule arrayListModule = Diablo.getInstance().getModuleRepository().getModuleInstance(ArrayListModule.class);

        if (arrayListModule == null) {
            return ColorUtil.getRainbow(1, offset, 1, 1);
        }

        switch (colorMode.getValue()) {
            case DIABLO:
                return ColorUtil.fadeBetween(PRIMARY_MAIN_COLOR.getRGB(), SECONDARY_MAIN_COLOR.getRGB(), arrayListModule.getColorSpeed().longValue(), offset * 2);
            case RAINBOW:
                return ColorUtil.getRainbow(arrayListModule.getColorSpeed().intValue(), offset, 1, 1);
            case LIGHT_RAINBOW:
                return ColorUtil.getRainbow(arrayListModule.getColorSpeed().intValue(), offset, 0.5f, 1);
            case DARK_RAINBOW:
                return ColorUtil.getRainbow(arrayListModule.getColorSpeed().intValue(), offset, 1, 0.5f);
            case ASTOLFO:
                // color util for astolfo is a little paki so if u dont do this it will make some weird ass reset thing
                return new Color(ColorUtil.astolfo(arrayListModule.getColorSpeed().intValue() + 2000, offset / 2)).getRGB();
            case WAVE:
                return ColorUtil.getWaveColor(primaryColor.getValue(), arrayListModule.getColorSpeed().intValue(), offset).getRGB();
            case CUSTOM:
                // we can make a theme setting (which is basically mode setting but for themes) and make it customizable for the user to pick which theme they want from the clickgui. *cough* *cough* *hint* *hint* *wink* *wink* *nudge* *nudge* *wink* *wink*
                return new Color(ColorUtil.fadeBetween(primaryColor.getValue().getRGB(), secondaryColor.getValue().getRGB(), arrayListModule.getColorSpeed().longValue(), offset * 2)).getRGB();
        }
        return 0;
    }

    enum ColorMode implements IMode {
        DIABLO("Diablo"),
        RAINBOW("Rainbow"),
        LIGHT_RAINBOW("Light Rainbow"),
        DARK_RAINBOW("Dark Rainbow"),
        ASTOLFO("Astolfo"),
        WAVE("Wave"),
        CUSTOM("Custom");

        private final String name;

        ColorMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
