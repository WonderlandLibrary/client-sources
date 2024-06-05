package client.metaware.impl.module.misc;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(renderName = "FastEat", name = "FastEat", category = Category.PLAYER)
public class FastEat extends Module {
    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Verus);
    public DoubleProperty timer = new DoubleProperty("Ammount", 2, 2, 5, 1, () ->  mode.getValue() == Mode.Timer);

    public enum Mode{
        Verus, Packet, Timer
    }

    @EventHandler
    private Listener<UpdatePlayerEvent> eventListener = event -> {
        setSuffix(mode.getValue().toString());
        switch (mode.getValue()) {
            case Verus:{
                if(mc.thePlayer.isEating()) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                }
                break;
            }
            case Packet:{
                if(mc.thePlayer.isEating()) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer(false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer(false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer(false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer(false));
                }
                break;
            }
            case Timer:{
                if(mc.thePlayer.isEating()) {
                    mc.timer.timerSpeed = timer.getValue().floatValue();
                }
                break;
            }
        }
    };
}
