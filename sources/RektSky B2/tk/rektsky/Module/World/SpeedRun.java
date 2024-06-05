package tk.rektsky.Module.World;

import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;

public class SpeedRun
{
    public void onEvent(final Event event) {
        if (event instanceof PacketReceiveEvent && ((PacketReceiveEvent)event).getPacket() instanceof S02PacketChat && ((S02PacketChat)((PacketReceiveEvent)event).getPacket()).getChatComponent().getUnformattedText().contains("Seu objetivo \u00e9 proteger sua cama enquanto tenta destruir as camas de ilhas advers\u00e1rias e eliminar os jogadores inimigos. Use os min\u00e9rios gerados em sua il")) {
            Client.notify(new Notification.PopupMessage("Speedrun", "Speedrun Timer starts!", ColorUtil.NotificationColors.GREEN, 40));
        }
    }
}
