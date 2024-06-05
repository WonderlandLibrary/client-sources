/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.managers.FriendManager;
import drunkclient.beta.UTILS.helper.Helper;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class MCF
extends Module {
    private static final String NotificationManager = null;
    private boolean down;

    public MCF() {
        super("MiddleClick", new String[]{"middleclickfriends", "middleclick"}, Type.MISC, "No");
        this.setColor(new Color(241, 175, 67).getRGB());
    }

    @EventHandler
    private void onClick(EventPreUpdate e) {
        if (Mouse.isButtonDown((int)2) && !this.down) {
            if (MCF.mc.objectMouseOver.entityHit != null) {
                EntityPlayer player = (EntityPlayer)MCF.mc.objectMouseOver.entityHit;
                String playername = player.getName();
                if (!FriendManager.isFriend(playername)) {
                    Minecraft.thePlayer.sendChatMessage(".f add " + playername);
                    Helper.sendMessage("Friend System:Added: " + playername + " to your friendlist.");
                } else {
                    Minecraft.thePlayer.sendChatMessage(".f del " + playername);
                    Helper.sendMessage("Friend System:Removed: " + playername + " from your friendlist.");
                }
            }
            this.down = true;
        }
        if (Mouse.isButtonDown((int)2)) return;
        this.down = false;
    }
}

