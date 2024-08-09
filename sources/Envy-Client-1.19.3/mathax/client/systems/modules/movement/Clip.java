package mathax.client.systems.modules.movement;

import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import net.minecraft.item.Items;

public class Clip extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    double startHeight;

    public Clip() {
        super(Categories.Movement, Items.SPRUCE_BOAT, "Clip", "Vertically clips on bind");

    }
    private final Setting<Double> clip = sgGeneral.add(new DoubleSetting.Builder()
        .name("clip")
        .description("The clip amount.")
        .defaultValue(10)
        .min(1)
        .sliderRange(1, 10)
        .build()
    );

    // should clip
    private final Setting<Boolean> ShouldClip = sgGeneral.add(new BoolSetting.Builder()
        .name("Clip")
        .description("Should clip.")
        .defaultValue(false)
        .build()
    );
    @Override
    public boolean onActivate() {

        startHeight = mc.player.getY();
        if (ShouldClip.get()) {
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + clip.get(), mc.player.getZ());
            (Modules.get().get(Clip.class)).forceToggle(false);
        }
        return false;
    }


}
