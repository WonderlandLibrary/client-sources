package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.KeyEvent;
import wtf.diablo.client.friend.FriendRepository;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@ModuleMetaData(name = "Middle Click Friend", description = "Middle click to add a friend", category = ModuleCategoryEnum.MISC)
public final class MiddleClickFriendModule extends AbstractModule {
    @EventHandler
    private final Listener<KeyEvent> keyEventListener = event -> {
        if (Mouse.isButtonDown(2)) {

            final EntityPlayer entityMouseOver = mc.objectMouseOver.entityHit instanceof EntityPlayer ? (EntityPlayer) mc.objectMouseOver.entityHit : null;

            if (entityMouseOver != null) {
                final FriendRepository friendRepository = Diablo.getInstance().getFriendRepository();

                final boolean friend = friendRepository.isFriend(entityMouseOver.getName());

                if (!friend) {
                    friendRepository.addFriend(entityMouseOver.getName());
                    ChatUtil.addChatMessage("Added " + entityMouseOver.getName() + " as a friend.");
                } else {
                    friendRepository.removeFriend(entityMouseOver.getName());
                    ChatUtil.addChatMessage("Removed " + entityMouseOver.getName() + " as a friend.");
                }

                final Notification notification = new Notification("Middle Click Friend",(friend ? "Removed " : "Added ") + entityMouseOver.getName() + " as a friend.", 5000L, NotificationType.SUCCESS);
                Diablo.getInstance().getNotificationManager().addNotification(notification);
            }
        }
    };
}
