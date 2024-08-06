package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.visual.esp.TwoDimensionalESP;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.ModeEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.util.world.WorldUtil;

import java.util.function.Supplier;

@ModuleInfo(name = "ESP", description = "Extra Sensory Perception. See players from far away.", category = Category.VISUAL)
public final class ESP extends Module {

    public final ModeSetting<ESPMode> modeSetting = new ModeSetting<>("Mode", ESPMode.TWO_DIMENSIONAL);
    public final BooleanSetting selfSetting = new BooleanSetting("Self", false);
    public final MultiSelectSetting<WorldUtil.Target> targetsSetting = new MultiSelectSetting<>("Targets", WorldUtil.Target.PLAYERS);

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }

    public enum ESPMode implements ModeEnum<ESP> {
        TWO_DIMENSIONAL(new TwoDimensionalESP(), "2D");
        final Mode<ESPMode> mode;
        final String name;
        ESPMode(final Mode<ESPMode> mode, final String name) {
            this.mode = mode;
            this.name = name;
        }
        @Override
        public Mode<ESPMode> getMode() {
            return mode;
        }
        @Override
        public String getName() {
            return name;
        }
    }
}
