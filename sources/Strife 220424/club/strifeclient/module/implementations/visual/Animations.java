package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "Animations", description = "Change your item animations.", category = Category.VISUAL)
public final class Animations extends Module {

    public final ModeSetting<AnimationsMode> modeSetting = new ModeSetting<>("Mode", AnimationsMode.OLD);
    public final ModeSetting<HitMode> hitModeSetting = new ModeSetting<>("Hit Mode", HitMode.NORMAL);
    public final DoubleSetting scaleSetting = new DoubleSetting("Scale", 1, 0.3, 2, 0.05);
    public final BooleanSetting alwaysScaleSetting = new BooleanSetting("Always scale", false, () -> scaleSetting.getValue() != 1);
    public final DoubleSetting slowdownSetting = new DoubleSetting("Slowdown", 0, 0, 90, 1);
    public final DoubleSetting xSetting = new DoubleSetting("X", 0, -1, 1, 0.001);
    public final DoubleSetting ySetting = new DoubleSetting("Y", 0, -1, 1, 0.001);
    public final DoubleSetting zSetting = new DoubleSetting("Z", 0, -1, 1, 0.001);
    public final BooleanSetting alwaysTranslate = new BooleanSetting("Always translate", false,
            () -> xSetting.getValue() != 0 || ySetting.getValue() != 0 || zSetting.getValue() != 0);

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }

    public enum AnimationsMode implements SerializableEnum {
        VANILLA("Vanilla"), OLD("Old"), LUNAR("Lunar"), SWANK("Swank"), EXHIBITION("Exhibition"),
        STELLA("Stella"), SPIN("Spin"), DORTWARE("Dortware"), SMOOTH("Smooth");

        final String name;

        AnimationsMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum HitMode implements SerializableEnum {
        NORMAL("Normal"), SMOOTH("Smooth");

        final String name;

        HitMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
