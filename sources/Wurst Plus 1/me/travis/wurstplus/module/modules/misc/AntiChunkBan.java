package me.travis.wurstplus.module.modules.misc;

import me.travis.wurstplus.event.events.EventReceivePacket;
import me.travis.wurstplus.event.events.EventStageable;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChunkData;

@Module.Info(name = "AntiChunkBan", category = Module.Category.MISC)

public class AntiChunkBan extends Module {

    private static long startTime = 0;
    private double delayTime = 10.0;
    private Setting<ModeThing> modeThing = register(Settings.e("Mode", ModeThing.PACKET));
    private Setting<Boolean> disable = register(Settings.b("Disable for Kill mode", false));

    private enum ModeThing {
        PACKET, KILL
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        if (modeThing.getValue().equals(ModeThing.KILL)) {
            if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                if (startTime == 0) startTime = System.currentTimeMillis();
                if (startTime + delayTime <= System.currentTimeMillis()) {
                    if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                        Minecraft.getMinecraft().playerController.connection.sendPacket(new CPacketChatMessage("/kill"));
                    }
                    if (mc.player.getHealth() <= 0) {
                        mc.player.respawnPlayer();
                        mc.displayGuiScreen(null);
                        if (disable.getValue()) {
                            this.disable();
                        }
                    }
                    startTime = System.currentTimeMillis();
                }
            }
        }
    }

    @EventHandler
    private Listener<EventReceivePacket> packetEventSendListener = new Listener<EventReceivePacket>(event -> {
        if (modeThing.getValue().equals(ModeThing.PACKET)) {
            if (event.getStage() == EventStageable.EventStage.PRE) {
                if (event.getPacket() instanceof SPacketChunkData) {
                    event.setCanceled(true);
                }
            }
        }
    });
}