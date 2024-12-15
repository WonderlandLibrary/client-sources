package com.alan.clients.module.impl.movement.inventorymove;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
;
public class WatchdogInventoryMove extends Mode<InventoryMove> {
    public WatchdogInventoryMove(String name, InventoryMove parent) {
        super(name, parent);
    }
    private boolean speed2 = false;
    private final KeyBinding[] AFFECTED_BINDINGS = new KeyBinding[]{
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindJump
    };

    @EventLink
    private final Listener<PreMotionEvent> preUpdateEventListener = event -> {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat || mc.currentScreen == this.getClickGUI())
            return;




        for (final KeyBinding bind : AFFECTED_BINDINGS) {
            bind.setPressed(GameSettings.isKeyDown(bind));
        }


    };

    @EventLink
    private final Listener<PacketSendEvent> packetSendEventListener = event -> {
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.currentScreen instanceof GuiInventory|| mc.currentScreen instanceof GuiChest){
            if(getModule(Speed.class).isEnabled() && !speed2){
                mc.thePlayer.motionZ *= -.1;
                mc.thePlayer.motionX *= -.1;
                //MoveUtil.stop();
                speed2 = true;
            }


            if(mc.thePlayer.onGroundTicks < 10 && !(Math.abs(mc.thePlayer.posY - Math.round(mc.thePlayer.posY)) > 0.03) && !(mc.currentScreen instanceof GuiChest)) {
                MoveUtil.strafe(.037);
            } else if(!mc.thePlayer.onGround) {
                MoveUtil.stop();
            } else{
                MoveUtil.strafe(.037);
            }

            if(mc.thePlayer.isJumping && mc.thePlayer.onGround ){
                MoveUtil.stop();
            } else if(mc.thePlayer.isJumping ){
            MoveUtil.stop();
            }



        } else if(speed2){
            getModule(Speed.class).setEnabled(true);
            speed2 = false;
        }


    };

}
