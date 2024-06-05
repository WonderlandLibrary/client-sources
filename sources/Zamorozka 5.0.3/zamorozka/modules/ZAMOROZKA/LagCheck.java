package zamorozka.modules.ZAMOROZKA;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.modules.TRAFFIC.Fly;
import zamorozka.modules.TRAFFIC.LongJump;
import zamorozka.modules.TRAFFIC.SpeedHack;
import zamorozka.modules.TRAFFIC.Sprint;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.TimerHelper;

public class LagCheck extends Module {

    public LagCheck() {
        super("LagBackChecker", 0, Category.Zamorozka);
    }
}
