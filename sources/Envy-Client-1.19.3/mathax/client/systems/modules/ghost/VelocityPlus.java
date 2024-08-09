package mathax.client.systems.modules.ghost;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.DamageBoostUtil;
import mathax.client.utils.player.MoveUtilV;
import net.minecraft.item.Items;

public class VelocityPlus extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    //todo Add Packet Cancel Mode and Percentage Mode
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Sprint Method to use")
        .defaultValue(Mode.Boost)
        .build()
    );
    //todo figure out a way to detect what damage type we took and add a damage type blacklist (example explosions)

    public VelocityPlus() {
        super(Categories.Combat, Items.ICE, "Velocity-Plus", "Modifies KnockBack");
    }


    double VelZ = 0;

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mode.get() == Mode.Boost) {
            if (mc.player.hurtTime >= 1) {
                MoveUtilV.strafe();
            }
        }
        if (mode.get() == Mode.Dev) {

        }
    }
    public enum Mode {
        Boost("Boost"),
        Dev("Dev");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;

        }
    }

    @Override
    public String getInfoString() {
        return mode.get().toString();
    }
}
