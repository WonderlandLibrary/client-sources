package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class FastReport extends Module {
    private boolean down;

    public FastReport() {
        super("FastReport", new String[]{"middleclickfriends", "middleclick"}, ModuleType.World);
        this.setColor(new Color(241, 175, 67).getRGB());
    }

    @EventHandler
    private void onClick(EventPreUpdate e) {
        if (Mouse.isButtonDown(2) && !this.down) {
            if (mc.objectMouseOver.entityHit != null) {
                EntityPlayer player = (EntityPlayer) mc.objectMouseOver.entityHit;
                String playername = player.getName();
                if (!FriendManager.isFriend(playername)) {
                    mc.thePlayer.sendChatMessage("/wdr " + playername + " Killaura Scaffold ka");
                } else {
                    mc.thePlayer.sendChatMessage("/wdr " + playername + " Killaura Scaffold ka");
                }
            }
            this.down = true;
        }
        if (!Mouse.isButtonDown(2)) {
            this.down = false;
        }
    }
}
