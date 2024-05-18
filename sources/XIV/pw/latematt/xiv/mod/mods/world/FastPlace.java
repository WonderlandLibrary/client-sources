package pw.latematt.xiv.mod.mods.world;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.PlaceBlockEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;

/**
 * @author Rederpz
 */
public class FastPlace extends Mod implements Listener<PlaceBlockEvent>, CommandHandler {
    private final ClampedValue<Integer> placeDelay = new ClampedValue<>("fastplace_delay", 0, 0, 4);

    public FastPlace() {
        super("FastPlace", ModType.WORLD, Keyboard.KEY_K, 0xFFFF9933);
        Command.newCommand().cmd("fastplace").description("Base command for the FastPlace mod.").arguments("<action>").aliases("fplace").handler(this).build();
    }

    @Override
    public void onEventCalled(PlaceBlockEvent event) {
        event.setPlaceDelay(placeDelay.getValue());
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "delay":
                    if (arguments.length >= 3) {
                        String newDelayString = arguments[2];
                        try {
                            int newPlaceDelay = arguments[2].equalsIgnoreCase("-d") ? placeDelay.getDefault() : Integer.parseInt(newDelayString);
                            placeDelay.setValue(newPlaceDelay);
                            if (placeDelay.getValue() > placeDelay.getMax())
                                placeDelay.setValue(placeDelay.getMax());
                            else if (placeDelay.getValue() < placeDelay.getMin())
                                placeDelay.setValue(placeDelay.getMin());

                            ChatLogger.print(String.format("FastPlace Place Delay set to %s", placeDelay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: fastplace delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fastplace <action>");
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
