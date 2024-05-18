package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.PlayerDeathEvent;

/**
 * @author Matthew
 */
public class Drown implements CommandHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Listener motionUpdateListener, playerDeathListener;

    public Drown() {
        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
                    for (int index = 0; index <= 10; index++) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
                    }
                }
            }
        };

        playerDeathListener = new Listener<PlayerDeathEvent>() {
            @Override
            public void onEventCalled(PlayerDeathEvent event) {
                XIV.getInstance().getListenerManager().remove(motionUpdateListener, playerDeathListener);
            }
        };
    }

    @Override
    public void onCommandRan(String message) {
        XIV.getInstance().getListenerManager().remove(motionUpdateListener, playerDeathListener);

        if (mc.thePlayer.isInWater()) {
            for (int i = 0; i <= mc.thePlayer.getAir(); i++) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
            }

            XIV.getInstance().getListenerManager().add(motionUpdateListener, playerDeathListener);
        }
    }
}
