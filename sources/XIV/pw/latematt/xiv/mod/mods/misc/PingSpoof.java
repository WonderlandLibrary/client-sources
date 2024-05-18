package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;

/**
 * @author Matthew
 */
public class PingSpoof extends Mod implements Listener<SendPacketEvent>, CommandHandler {
    private final ClampedValue<Long> delay = new ClampedValue<>("pingspoof_delay", 2000L, 100L, 20000L);
    private final Timer timer = new Timer();

    public PingSpoof() {
        super("PingSpoof", ModType.MISCELLANEOUS, Keyboard.KEY_NONE, 0xFF8B78E7);
        Command.newCommand().cmd("pingspoof").description("Base command for the PingSpoof mod.").aliases("pspoof", "ps").arguments("<action>").handler(this).build();
    }

    @Override
    public void onEventCalled(SendPacketEvent event) {
        if (!(event.getPacket() instanceof C00PacketKeepAlive))
            return;

        if (!timer.hasReached(delay.getValue()))
            event.setCancelled(true);
        else
            timer.reset();
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

                            ChatLogger.print(String.format("PingSpoof Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: pingspoof delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: pingspoof <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
