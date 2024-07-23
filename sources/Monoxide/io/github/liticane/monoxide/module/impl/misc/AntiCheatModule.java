package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.anticheat.AntiCheat;
import io.github.liticane.monoxide.anticheat.data.PlayerData;

@ModuleData(category = ModuleCategory.MISCELLANEOUS, description = "Detects other cheaters", name = "HackerDetector")
public class AntiCheatModule extends Module {

    @Listen
    public final void onUpdate(UpdateEvent updateEvent) {
        if (mc.thePlayer != null) {
            AntiCheat.INSTANCE.handlePlayers();
        }
    }

    @Listen
    public void onPacket(PacketEvent packetEvent) {
        if (mc.thePlayer != null) {
            AntiCheat.INSTANCE.getPlayers().values().stream().filter(d -> d.getTicksExisted() > 80).forEach(d -> d.handle(packetEvent.getPacket()));
        }
    }

    @Override
    public void onEnable() {
        AntiCheat.INSTANCE.getPlayers().clear();

        mc.theWorld.playerEntities
            .forEach(e -> AntiCheat.INSTANCE.getPlayers().put(e.getUniqueID(), new PlayerData(e)));
    }

    @Override
    public void onDisable() {
        AntiCheat.INSTANCE.getPlayers().clear();
    }

}
