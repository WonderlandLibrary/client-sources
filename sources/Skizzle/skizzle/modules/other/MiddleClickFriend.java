/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.entity.player.EntityPlayer;
import skizzle.events.Event;
import skizzle.events.listeners.EventMouseClick;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;

public class MiddleClickFriend
extends Module {
    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMouseClick) {
            MiddleClickFriend Nigga2;
            EventMouseClick Nigga3 = (EventMouseClick)Nigga;
            if (Nigga3.mouseButton == 2 && Nigga3.state == 1 && Nigga2.mc.objectMouseOver.entityHit != null && Nigga2.mc.objectMouseOver.entityHit instanceof EntityPlayer && !Nigga2.mc.objectMouseOver.entityHit.getName().equals("") && !Nigga2.mc.objectMouseOver.entityHit.getName().equals(" ")) {
                Friend Nigga4 = new Friend(Nigga2.mc.objectMouseOver.entityHit.getName(), Nigga2.mc.objectMouseOver.entityHit.getName());
                if (FriendManager.isFriend(Nigga4)) {
                    FriendManager.removeFriend(FriendManager.getFriend(Nigga2.mc.objectMouseOver.entityHit.getName()));
                } else {
                    FriendManager.addFriend(Nigga4);
                }
            }
        }
    }

    public MiddleClickFriend() {
        super(Qprot0.0("\uf15d\u71c2\uca00\ua7e0\ud88b\u74cf\u8c0c\u9d31\u570b\u73ce\ufeec\uaf2a\u73f4\u7244\uacbb\u6d7b\u42ed"), 0, Module.Category.OTHER);
        MiddleClickFriend Nigga;
        Nigga.onDisable();
    }
}

