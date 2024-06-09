package me.teus.eclipse.modules.impl.movement;

import me.teus.eclipse.events.player.EventMotionUpdate;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.lemon.event.bus.Listener;

@Info(name = "Flight", displayName = "Flight", category = Category.MOVEMENT)
public class Flight extends Module {

    public NumberValue vanillaSpeed = new NumberValue("Vanilla Speed", 25, 5, 50, 1);
    public NumberValue vanillaVerticalSpeed = new NumberValue("Vanilla Vertical Speed", 1, 0.2, 9, 0.2);

    public Flight(){
        addValues(vanillaSpeed, vanillaVerticalSpeed);
    }

    @Override
    public void onEnable(){
        fuckDamagfe();
    }

    public Listener<EventMotionUpdate> eventMortionUpdateListener = event -> {
        ok(event);
    };

    public void ok(EventMotionUpdate event) {
        MoveUtils.setSpeed(vanillaSpeed.getValue() / 4);

        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            event.setY(vanillaVerticalSpeed.getValue());
        } else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
            event.setY(-vanillaVerticalSpeed.getValue());
        } else {
            event.setY(0);
        }
        mc.thePlayer.motionY = 0;
    }

    public void fuckDamagfe() {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
    }
}
