package zamorozka.modules.PLAYER;

import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AntiFire extends Module{

	public AntiFire() {
		super("AntiFire", 0, Category.PLAYER);
	}

	@Override
	public void onUpdate() {
		if(!getState()){
			return;
		}
		if(!mc.player.capabilities.isCreativeMode){
			if(mc.player.isBurning() && mc.player.onGround){
				mc.player.connection.sendPacket(new CPacketEntityAction());
			}
		}
	}
}

