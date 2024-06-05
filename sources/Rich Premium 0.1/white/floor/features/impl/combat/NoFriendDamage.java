package white.floor.features.impl.combat;

import net.minecraft.network.play.client.CPacketUseEntity;
import white.floor.event.EventTarget;
import white.floor.event.event.EventPacket;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.friend.FriendManager;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class NoFriendDamage extends Feature {

    public NoFriendDamage() {
        super("DamageFriendFalse", "no team damage.",0, Category.COMBAT);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) e.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK
                    && FriendManager.getFriends().isFriend(mc.objectMouseOver.entityHit.getName())) {
                e.setCancelled(true);
            }
        }
    }
    @Override
    public void onEnable()
    {
        super.onEnable();
    }
    public void onDisable() {
        super.onDisable();
    }
}