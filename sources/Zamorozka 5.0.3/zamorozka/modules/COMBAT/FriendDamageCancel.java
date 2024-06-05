package zamorozka.modules.COMBAT;

import net.minecraft.network.play.client.CPacketUseEntity;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FriendDamageCancel extends Module {

	public FriendDamageCancel() {
		super("FriendDamageCancel", 0, Category.COMBAT);
	}

	@EventTarget
	public void onPacket(EventPacket e) {
		indexer index = new indexer();
		if (e.getPacket() instanceof CPacketUseEntity) {
			CPacketUseEntity packet = (CPacketUseEntity) e.getPacket();
			if (packet.getAction() == CPacketUseEntity.Action.ATTACK
					&& index.getFriends().isFriend(mc.objectMouseOver.entityHit.getName())) {
				e.setCancelled(true);
			}
		}
	}
}
