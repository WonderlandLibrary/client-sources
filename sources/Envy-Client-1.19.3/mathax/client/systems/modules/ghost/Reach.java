package mathax.client.systems.modules.ghost;

import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;

public class Reach extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> reach = sgGeneral.add(new DoubleSetting.Builder()
        .name("reach")
        .description("Your reach modifier.")
        .defaultValue(3.5)
        .min(0)
        .sliderRange(0, 7.5)
        .build()
    );
    private final Setting<Boolean> SwordOnly = sgGeneral.add(new BoolSetting.Builder()
        .name("Sword Only")
        .description("Only reach if have a sword equipped")
        .defaultValue(false)
        .build()
    );

    public Reach() {
        super(Categories.Ghost, Items.COMMAND_BLOCK, "reach", "Gives you super long arms.");
    }

    private boolean isHoldingSword() {
        ItemStack heldItem = mc.player.getMainHandStack();
        return heldItem.getItem() instanceof SwordItem;
    }

    public float getReach() {
        if (!isActive() || !isHoldingSword() && SwordOnly.get()) return mc.interactionManager.getCurrentGameMode().isCreative() ? 5.0F : 4.5F;
        return reach.get().floatValue();
    }

    @Override
    public String getInfoString() {
        return reach.get().toString();
    }
}
