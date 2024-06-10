package me.sleepyfish.smok.rats.impl.other;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.entities.friend.FriendUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.entity.Entity;

// Class from SMok Client by SleepyFish
public class Add_Friends extends Rat {

    private Timer timer = new Timer();

    public Add_Friends() {
        super("Add Friends", Rat.Category.Other, "Add and remove friends with middle click");
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork() && MouseUtils.isButtonDown(2) && this.timer.delay(1000L)) {
            Entity target = mc.objectMouseOver.entityHit;
            if (target != null) {
                if (!FriendUtils.getIgnoreFriends().contains(target)) {
                    ClientUtils.notify("Add Friends", "Added Friend: " + target.getName(), Notification.Icon.Check, 1L);
                    FriendUtils.getIgnoreFriends().add(target);
                    FriendUtils.getRenderFriends().add(target);
                    this.timer.reset();
                } else {
                    ClientUtils.notify("Add Friends", "Removed Friend: " + target.getName(), Notification.Icon.Bell, 1L);
                    FriendUtils.getIgnoreFriends().remove(target);
                    FriendUtils.getRenderFriends().remove(target);
                    this.timer.reset();
                }
            } else {
                ClientUtils.notify("Add Friends", "No Entity found", Notification.Icon.No, 1L);
                this.timer.reset();
            }
        }
    }

}