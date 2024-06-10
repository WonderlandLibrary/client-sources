package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Sprint", renderName = "Sprint", category = Category.MOVEMENT)
public class SprintModule extends Module {

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEvent = new Listener<>(event -> {
        if(MovementUtil.canSprint()) {
            mc.thePlayer.setSprinting(true);
        }
    });

    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundListener = new Listener<>(event -> {
       if(event.packet() instanceof C0BPacketEntityAction) {
           C0BPacketEntityAction action = (C0BPacketEntityAction) event.packet();
           if(action.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING && MovementUtil.canSprint()) {
               // set sprinting (fixes pulsing bugs)
               mc.thePlayer.setSprinting(true);
               event.cancel();
           }
       }
    });

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isPressed());
        super.onDisable();
    }

    @Override
    public void init() {
        toggle();
        super.init();
    }

}
