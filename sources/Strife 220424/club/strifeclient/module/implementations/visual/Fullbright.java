package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ModeSetting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(name = "Fullbright", description = "Allows you to see dark areas in full view.", category = Category.VISUAL)
public final class Fullbright extends Module {
    private float oldGamma;
    private final ModeSetting<FullbrightMode> modeSetting = new ModeSetting<>("Mode", FullbrightMode.GAMMA);

    public Fullbright() {
        oldGamma = mc.gameSettings.gammaSetting;
        modeSetting.addChangeCallback((old, value) -> {
            if (old == FullbrightMode.POTION)
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
            if (old == FullbrightMode.GAMMA)
                mc.gameSettings.gammaSetting = oldGamma;
            if (value.getValue() == FullbrightMode.POTION)
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 5201, 9));
            if (value.getValue() == FullbrightMode.GAMMA) {
                oldGamma = mc.gameSettings.gammaSetting;
                mc.gameSettings.gammaSetting = 10000f;
            }
        });
    }

    public enum FullbrightMode implements SerializableEnum {
        GAMMA("Gamma"), POTION("Potion");

        final String name;

        FullbrightMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
