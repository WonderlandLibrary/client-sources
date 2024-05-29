package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.Wrapper;
import dev.event.Event;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.world.WorldSettings;

public final class Freecam extends Module {

    private final EventListener<MotionEvent> motionEventEventListener = event -> {

    };

    private final EventListener<BoundingBoxEvent> boundingBoxEventEventListener = event -> {
        if(mc.thePlayer != null) {
            event.cancel();
        }
    };

    private final EventListener<PushOutOfBlockEvent> pushOutOfBlockEventEventListener = event -> {
        if(mc.thePlayer != null) {
            event.cancel();
        }
    };

    private final EventListener<PacketReceiveEvent> packetReceiveEventEventListener = event -> {
        if (event.getPacket() instanceof C03PacketPlayer) {
            event.cancel();
        }
    };

    @Override
    public void onEnable(){
        if(mc.thePlayer != null) {
            mc.thePlayer.capabilities.allowFlying = true;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(mc.thePlayer != null) {
            mc.thePlayer.capabilities.allowFlying = false;
            mc.thePlayer.capabilities.isFlying = false;
        }
        super.onDisable();
    }

    public Freecam() {
        super("Freecam", Category.PLAYER, "allows you to look around freely");
    }
}
