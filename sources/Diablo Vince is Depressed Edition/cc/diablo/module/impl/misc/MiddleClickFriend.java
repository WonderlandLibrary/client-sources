/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.friend.FriendManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriend
extends Module {
    public MiddleClickFriend() {
        super("MiddleClickFriend", "Adds friends", 0, Category.Misc);
    }

    @Override
    public void onEnable() {
        this.setDisplayName("MCF");
    }

    @Subscribe
    public void onTick(UpdateEvent event) {
        if (MiddleClickFriend.mc.gameSettings.keyBindPickBlock.pressed) {
            EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().objectMouseOver.entityHit;
            String name = player.getName();
            if (FriendManager.isFriend(name)) {
                ChatHelper.addChat((Object)((Object)ChatColor.RED) + "Removed " + (Object)((Object)ChatColor.WHITE) + player.getName() + " as a friend");
                FriendManager.removeFriend(player.getName());
            } else {
                ChatHelper.addChat((Object)((Object)ChatColor.GREEN) + "Added " + (Object)((Object)ChatColor.WHITE) + player.getName() + " as a friend");
                FriendManager.addFriendToList(player.getName(), player.getName());
            }
            MiddleClickFriend.mc.gameSettings.keyBindPickBlock.pressed = false;
        }
    }
}

