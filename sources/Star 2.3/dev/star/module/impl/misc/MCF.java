package dev.star.module.impl.misc;

import dev.star.commands.impl.FriendCommand;
import dev.star.event.impl.game.TickEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public class MCF extends Module {

    private boolean wasDown;

    public MCF() {
        super("MCF", Category.MISC, "middle click friends");
    }

    @Override
    public void onTickEvent(TickEvent event) {
        if (mc.inGameHasFocus) {
            boolean down = mc.gameSettings.keyBindPickBlock.isKeyDown();
            if (down && !wasDown) {
                if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) mc.objectMouseOver.entityHit;
                    String name = StringUtils.stripControlCodes(player.getName());
                    if (FriendCommand.isFriend(name)) {
                        FriendCommand.friends.removeIf(f -> f.equalsIgnoreCase(name));
                        NotificationManager.post(NotificationType.SUCCESS, "Friend Manager", "You are no longer friends with " + name + "!", 2);
                    } else {
                        FriendCommand.friends.add(name);
                        NotificationManager.post(NotificationType.SUCCESS, "Friend Manager", "You are now friends with " + name + "!", 2);
                    }
                    FriendCommand.save();
                    wasDown = true;
                }
            } else if (!down) {
                wasDown = false;
            }
        }
    }

}
