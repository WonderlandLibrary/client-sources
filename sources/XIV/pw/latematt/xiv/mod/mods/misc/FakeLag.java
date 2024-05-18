package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.Packet;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Matthew
 */
public class FakeLag extends Mod implements Listener<SendPacketEvent>, CommandHandler {
    private final ClampedValue<Long> delay = new ClampedValue<>("fakelag_delay", 1000L, 100L, 20000L);
    private final List<Packet> packets = new CopyOnWriteArrayList<>();
    private final Listener motionUpdateListener;
    private final Timer timer = new Timer();

    public FakeLag() {
        super("FakeLag", ModType.MISCELLANEOUS, Keyboard.KEY_NONE, 0xFF0B4556);
        Command.newCommand().cmd("fakelag").description("Base command for the FakeLag mod.").aliases("flag", "fl").arguments("<action>").handler(this).build();

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
                    if (timer.hasReached(delay.getValue())) {
                        for (Packet packet : packets) {
                            mc.getNetHandler().getNetworkManager().sendPacket(packet);
                            packets.remove(packet);
                        }
                        timer.reset();
                    }
                }
            }
        };
    }

    @Override
    public void onEventCalled(SendPacketEvent event) {
        if (!mc.thePlayer.isDead) {
            if (!event.isCancelled()) {
                packets.add(event.getPacket());
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "delay":
                case "d":
                    if (arguments.length >= 3) {
                        String newDelayString = arguments[2];
                        try {
                            if (arguments[2].equalsIgnoreCase("-d")) {
                                delay.setValue(delay.getDefault());
                            } else {
                                long newDelay = Long.parseLong(newDelayString);
                                delay.setValue(newDelay);
                                if (delay.getValue() > delay.getMax())
                                    delay.setValue(delay.getMax());
                                else if (delay.getValue() < delay.getMin())
                                    delay.setValue(delay.getMin());
                            }

                            ChatLogger.print(String.format("FakeLag Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: fakelag delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fakelag <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
        XIV.getInstance().getListenerManager().add(motionUpdateListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
        XIV.getInstance().getListenerManager().remove(motionUpdateListener);
        for (Packet packet : packets) {
            mc.getNetHandler().addToSendQueue(packet);
            packets.remove(packet);
        }
    }
}
