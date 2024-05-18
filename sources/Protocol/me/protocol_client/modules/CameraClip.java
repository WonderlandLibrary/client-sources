package me.protocol_client.modules;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class CameraClip extends Module{

	public CameraClip() {
		super("Camera Clip", "cameraclip", 0, Category.RENDER, new String[]{"suicide", "kys", "fuckingendme", "killme", "amandatodd"});
		setShowing(false);
	}
	public void runCmd(String s){
		  int i = 0;
          while ((double)i < 860.0) {
              this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.049, this.mc.thePlayer.posZ, false));
              this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
              ++i;
          }
          this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
	}
}
