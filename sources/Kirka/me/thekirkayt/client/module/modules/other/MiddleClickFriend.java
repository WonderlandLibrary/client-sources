/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import me.thekirkayt.client.Client;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.MouseEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

@Module.Mod(displayName="MiddleClickFriend")
public class MiddleClickFriend
extends Module {
    @EventTarget
    private void onMouseClick(MouseEvent event) {
        if (event.getKey() == 2 && ClientUtils.mc().objectMouseOver != null && ClientUtils.mc().objectMouseOver.entityHit != null && ClientUtils.mc().objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)ClientUtils.mc().objectMouseOver.entityHit;
            String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.removeFriend(name);
                ClientUtils.sendMessage("Removed " + name);
                Client.getNotificationManager().addInfo(String.valueOf(name) + " Is not longer your Friend!");
            } else {
                FriendManager.addFriend(name, name);
                ClientUtils.sendMessage("Added " + name);
                Client.getNotificationManager().addInfo(String.valueOf(name) + " Is now your Friend!");
            }
        }
    }
}

