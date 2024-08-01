package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Animations", description = "Changes the animation of the sword", category = ModuleCategoryEnum.RENDER)
public final class AnimationsModule extends AbstractModule {

    private final ModeSetting<EnumAnimationMode> mode = new ModeSetting<>("Mode", EnumAnimationMode.SWING);
    private final NumberSetting<Double> x = new NumberSetting<>("X", 0D, -1D, 2D, 0.05D);
    private final NumberSetting<Double> y = new NumberSetting<>("Y", 0D, -1D, 2D, 0.05D);
    private final NumberSetting<Double> z = new NumberSetting<>("Z", 0D, -1D, 2D, 0.05D);
    private final NumberSetting<Double> swordSlowdown = new NumberSetting<>("Sword Slowdown", 1D, 1D, 10D, 0.05D);

    public AnimationsModule() {
        this.registerSettings(mode, swordSlowdown, x, y, z);
    }

    public double getX() {
        return x.getValue();
    }

    public double getY() {
        return y.getValue();
    }

    public double getZ() {
        return z.getValue();
    }

    public double getSwordSlowdown() {
        return swordSlowdown.getValue();
    }

    public EnumAnimationMode getMode() {
        return mode.getValue();
    }

    public enum EnumAnimationMode implements IMode {
        SWING("Swing"),
        EXHIBITION("Exhibition"),
        SLIDE("Slide"),
        SLIDE_2("Slide 2"),
        SLIDE_3("Slide 3"),
        SWANG("Swang"),
        SWANK("Swank"),
        SWONG("Swong"),
        SWAING("Swaing"),
        SIGMA("Sigma"),
        SPINNING("Spinning"),
        PUNCH("Punch"),
        STELLA("Stella"),
        STYLES("Styles"),
        NUDGE("Nudge");

        private final String name;

        EnumAnimationMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
