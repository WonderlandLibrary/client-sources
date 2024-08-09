package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.item.Items;

public class Suicide extends Module {
    public Suicide() {super(Categories.Combat, Items.BEDROCK, "Suicide", "Speeeeeeeed");}
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> offhand = sgGeneral.add(new BoolSetting.Builder()
        .name("Offhand")
        .description("Doesn't hold totem.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> disableDeath = sgGeneral.add(new BoolSetting.Builder()
        .name("Disable On Death")
        .description("Disables the module on death.")
        .defaultValue(true)
        .build()
    );

    @EventHandler(priority = 6969)
    private void onDeath(OpenScreenEvent event) {
        if (event.screen instanceof DeathScreen && disableDeath.get()) {
            toggle();
            info("died");
        }
    }
}

