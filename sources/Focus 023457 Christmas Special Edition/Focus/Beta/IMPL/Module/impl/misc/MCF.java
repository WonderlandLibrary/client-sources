package Focus.Beta.IMPL.Module.impl.misc;

import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.managers.FriendManager;
import Focus.Beta.UTILS.helper.Helper;

import java.awt.*;

import javax.management.Notification;

public class MCF extends Module {
    private static final String NotificationManager = null;
	private boolean down;

    public MCF() {
        super("MiddleClick", new String[]{"middleclickfriends", "middleclick"}, Type.MISC, "No");
        this.setColor(new Color(241, 175, 67).getRGB());
    }

    @EventHandler
    private void onClick(EventPreUpdate e) {
        if (Mouse.isButtonDown((int) 2) && !this.down) {
            if (this.mc.objectMouseOver.entityHit != null) {
                EntityPlayer player = (EntityPlayer) this.mc.objectMouseOver.entityHit;
                String playername = player.getName();
                if (!FriendManager.isFriend(playername)) {
                    this.mc.thePlayer.sendChatMessage(".f add " + playername);
                    Helper.sendMessage("Friend System:" + "Added: " + playername + " to your friendlist.");

                } else {
                    this.mc.thePlayer.sendChatMessage(".f del " + playername);
                   Helper.sendMessage("Friend System:" + "Removed: " + playername + " from your friendlist.");

                }
            }
            this.down = true;
        }
        if (!Mouse.isButtonDown((int) 2)) {
            this.down = false;
        }
    }
}
