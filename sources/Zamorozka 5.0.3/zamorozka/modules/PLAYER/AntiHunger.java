package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Wrapper;

public class AntiHunger extends Module {

	public AntiHunger() {
		super("AntiHunger", Keyboard.KEY_NONE, Category.PLAYER);
	}

	@EventTarget
	public void receivePacket(EventReceivePacket event) {
		if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if (Minecraft.getMinecraft().player.fallDistance > 0 || Minecraft.getMinecraft().playerController.isHittingBlock) {
                packet.onGround = true;
            } else {
                packet.onGround = false;
            }
        }
        if (event.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }
}