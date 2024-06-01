package com.polarware.module.impl.other;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;
import net.minecraft.client.entity.EntityOtherPlayerMP;

@ModuleInfo(name = "module.other.cheatdetector.name", description = "module.other.cheatdetector.description", category = Category.OTHER)
public final class CheatDetectorModule extends Module {

    public ModeValue alertType = new ModeValue("Alert Type", this)
            .add(new SubMode("ClientSide"))
            .add(new SubMode("ServerSide"))
            .setDefault("ClientSide");

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        Client.INSTANCE.getCheatDetector().incrementTick();
    };

    @Override
    protected void onDisable() {
        Client.INSTANCE.getCheatDetector().playerMap.clear();
    }

    @Override
    protected void onEnable() {
        Client.INSTANCE.getCheatDetector().playerMap.clear();
        mc.theWorld.playerEntities.forEach(entityPlayer -> {
            if (entityPlayer != mc.thePlayer) {
                Client.INSTANCE.getCheatDetector().getRegistrationListener().handleSpawn((EntityOtherPlayerMP) entityPlayer);
            }
        });
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Client.INSTANCE.getCheatDetector().playerMap.values().forEach(playerData -> playerData.handle(event.getPacket()));
    };
}
