package gq.vapu.czfclient.Module.Modules.Blatant;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.awt.*;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", new String[]{"Nofalldamage"}, ModuleType.Blatant);
        this.setColor(new Color(242, 137, 73).getRGB());
    }

//	@EventHandler
//	private void onUpdate(EventPreUpdate e) {
////		if (!MoveUtils.isOnGround((double) 1.0E-4) && mc.thePlayer.motionY < 0.0&& ModuleManager.getModuleByClass(VanillaZoomFly.class).isEnabled()) {
////			e.setOnground(true);
////			//System.out.println("Hello World ");
////		}else if (!MoveUtils.isOnGround((double) 1.0E-4) && mc.thePlayer.motionY < 0.0&&this.mc.thePlayer.fallDistance > 2.0f) {
////			e.setOnground(true);
////		}
////		if (this.mc.thePlayer.fallDistance > 3.0f) {
////			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
////			this.mc.thePlayer.fallDistance = 0.0f;
////		}
////		if(mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3) {
////			mc.thePlayer.motionY = 0.0;
////			mc.thePlayer.fallDistance = 0.0f;
////			mc.thePlayer.motionX *= 0.6;
////			mc.thePlayer.motionZ *= 0.6;
////			boolean needSpoof = true;
////		}
//	}

    @EventHandler
    public void onPacket(EventPacketSend e) {
        Packet packet = EventPacketSend.getPacket();
        if (EventPacketSend.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance > 2.0F) {
            C03PacketPlayer c03 = (C03PacketPlayer) packet;
            c03.onGround = true;
        }
    }
}
