package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * Created by myche on 2/5/2017.
 */
public class NoWeb extends Module {

    public NoWeb() {
        super("NoWeb", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e) {
        if (e.type == EventType.PRE) {
            if (mc.thePlayer.isInsideOfMaterial(Material.web)) {
                mc.thePlayer.motionY = 0;
                if (mc.thePlayer.moveStrafing != 0 || mc.thePlayer.moveForward != 0) {
                    mc.thePlayer.setSpeed(0.10);
                }
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + (mc.gameSettings.keyBindJump.isPressed() ? 0.0625
                        : mc.gameSettings.keyBindSneak.isPressed() ? -0.0625 : 0), mc.thePlayer.posZ + mc.thePlayer.motionZ, false));

                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX,
                        mc.theWorld.getHeight(), mc.thePlayer.posZ + mc.thePlayer.motionZ, true));
            }
        }
    }

}
