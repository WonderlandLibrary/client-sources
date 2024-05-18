// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.event.EventTarget;
import me.chrest.client.friend.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import me.chrest.utils.ClientUtils;
import me.chrest.event.MouseEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "MiddleClickFriend", enabled = true)
public class MiddleClickFriend extends Module
{
    @EventTarget
    private void onMouseClick(final MouseEvent event) {
        if (event.getKey() == 2 && ClientUtils.mc().objectMouseOver != null && ClientUtils.mc().objectMouseOver.entityHit != null && ClientUtils.mc().objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)ClientUtils.mc().objectMouseOver.entityHit;
            final String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.removeFriend(name);
                ClientUtils.sendMessage("Removed: " + name);
            }
            else {
                FriendManager.addFriend(name, name);
                ClientUtils.sendMessage("Added: " + name);
            }
        }
    }
}
