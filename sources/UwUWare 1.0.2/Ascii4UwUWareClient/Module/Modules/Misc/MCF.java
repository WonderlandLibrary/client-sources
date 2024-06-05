package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.Manager.FriendManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.Notification.Notification;
import Ascii4UwUWareClient.UI.Notification.NotificationManager;
import Ascii4UwUWareClient.UI.Notification.NotificationType;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class MCF extends Module {
    private boolean down;

    public MCF() {
        super("MCF", new String[]{"middleclickfriends", "middleclick"}, ModuleType.Misc);
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
                    NotificationManager.show(new Notification(NotificationType.INFO, "Friend System:", "Added: " + playername + " to your friendlist.", 5));

                } else {
                    this.mc.thePlayer.sendChatMessage(".f del " + playername);
                    NotificationManager.show(new Notification(NotificationType.INFO, "Friend System:", "Removed: " + playername + " from your friendlist.", 5));

                }
            }
            this.down = true;
        }
        if (!Mouse.isButtonDown((int) 2)) {
            this.down = false;
        }
    }
}
