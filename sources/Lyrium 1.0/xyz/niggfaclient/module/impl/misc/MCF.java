// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.Client;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MouseEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "MCF", description = "Adds a player as friend with middle button", cat = Category.MISC)
public class MCF extends Module
{
    @EventLink
    private final Listener<MouseEvent> mouseEventListener;
    
    public MCF() {
        String name;
        this.mouseEventListener = (e -> {
            if (e.getButton() == 2 && this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                name = this.mc.thePlayer.getName();
                if (!Client.getInstance().getFriendManager().isFriend(name)) {
                    Client.getInstance().getFriendManager().addFriend(name);
                    Printer.addMessage("Added " + name + " as a friend!");
                }
                else {
                    Client.getInstance().getFriendManager().removeFriend(name);
                    Printer.addMessage("Removed " + name + " as a friend!");
                }
            }
        });
    }
}
