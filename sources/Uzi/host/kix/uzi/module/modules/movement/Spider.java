package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * Created by myche on 2/5/2017.
 */
public class Spider extends Module {

    public Spider() {
        super("Spider", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e) {
        if (e.type == EventType.PRE) {
            if (mc.gameSettings.keyBindSneak.isPressed())
                mc.thePlayer.motionY = 0;
            if (!mc.thePlayer.isCollidedVertically && mc.thePlayer.motionY == 0) {
                if (mc.gameSettings.keyBindRight.isPressed()) {
                    if (getDirection().equalsIgnoreCase("WEST")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ - 0.1, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("NORTH")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + 0.1, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("EAST")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ + 0.1, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("SOUTH")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX - 0.1, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                } else if (mc.gameSettings.keyBindLeft.isPressed()) {
                    if (getDirection().equalsIgnoreCase("WEST")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ + 0.1, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("NORTH")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX - 0.1, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("EAST")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ - 0.1, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                    else if (getDirection().equalsIgnoreCase("SOUTH")) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + 0.1, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
                    }
                }
            }
            if (mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.motionY = 0;
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + (mc.gameSettings.keyBindSneak.isPressed() ? 0 : 0.0624), mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
            }
        }
        }

    public String getDirection() {
        return mc.func_175606_aa().getHorizontalFacing().name();
    }

    }


