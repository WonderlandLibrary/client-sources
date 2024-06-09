package lunadevs.luna.module.fun;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

//coded by faith

public class SpamKill extends Module{

	public SpamKill() {
		super("SpamKill", Keyboard.KEY_NONE, Category.FUN, false);
	}
	
	public void onUpdate() {
		if(!this.isEnabled) return;
		for(int kek = 0; kek < 500; kek++) {
			Luna.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Luna.getPlayer().posX, Luna.getPlayer().posY - 0.05D, Luna.getPlayer().posZ, false));
			Luna.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Luna.getPlayer().posX, Luna.getPlayer().posY, Luna.getPlayer().posZ, false));
		}
	}
	
	public String getValue() {
		return null;
	}
	
}
