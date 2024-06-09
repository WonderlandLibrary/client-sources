// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.MouseEvent;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;

@Module.Mod(displayName = "MiddleClickFriend",enabled = true)
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
