package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;

@ModuleData(name = "LightningDetector", description = "Detects lightning and sends coordinates", category = ModuleCategory.MISCELLANEOUS)
public class LightningDetectorModule extends Module {

    @Listen
    public void onPacket(PacketEvent packetEvent) {
        if(packetEvent.getPacket() instanceof S2CPacketSpawnGlobalEntity) {
            S2CPacketSpawnGlobalEntity packet = (S2CPacketSpawnGlobalEntity) packetEvent.getPacket();

            int x = packet.func_149051_d() / 32;
            int y = packet.func_149050_e() / 32;
            int z = packet.func_149049_f() / 32;

            sendMessage("§7Detected lightning at §c" + x + " " + y + " " + z + "§7.");
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}