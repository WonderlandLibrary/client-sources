package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.UI.Notification.Notification;
import Ascii4UwUWareClient.UI.Notification.NotificationManager;
import Ascii4UwUWareClient.UI.Notification.NotificationType;
import net.minecraft.network.play.server.S45PacketTitle;

import java.util.Timer;
import java.util.TimerTask;

public class AutoGG extends Module {

    public AutoGG() {
        super("AutoGG", new String[]{"TPAccept, autotp"}, ModuleType.Misc);

    }

    @EventHandler
    private void onPacket(EventPacketReceive ep) {
        if (ep.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle packet = (S45PacketTitle) ep.getPacket();
            if (packet.getType().equals(S45PacketTitle.Type.TITLE)) {
                String text = packet.getMessage().getUnformattedText();
                if (text.equals("You Won")) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mc.thePlayer.sendChatMessage(" GG [You got fucked by UWUware] <3");
                            this.cancel();
                        }
                    }, 1340L);
                    Client.instance.getModuleManager().getModuleByClass(Killaura.class).setEnabled(false);
                    NotificationManager.show(new Notification(NotificationType.ERROR, "Disable", "Killaura(Auto)", 1));
                } else if (text.equals("You Lost")
                         && Client.instance.getModuleManager().getModuleByClass(Killaura.class).isEnabled()) {
                     Client.instance.getModuleManager().getModuleByClass(Killaura.class).setEnabled(false);
                    NotificationManager.show(new Notification(NotificationType.ERROR, "Disable", "Killaura(Auto)", 1));
                }
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
