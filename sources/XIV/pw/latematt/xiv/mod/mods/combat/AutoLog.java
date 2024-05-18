package pw.latematt.xiv.mod.mods.combat;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;

import java.util.Objects;

/**
 * @author Jack
 */

public class AutoLog extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final ClampedValue<Float> health = new ClampedValue<>("autolog_health", 6.0F, 1.0F, 20.0F);

    public AutoLog() {
        super("AutoLog", ModType.COMBAT, Keyboard.KEY_NONE, 9868950);
        Command.newCommand().cmd("autolog").description("Base command for the AutoLog mod.").arguments("<action>").aliases("al").handler(this).build();
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.PRE)) {
            if (mc.thePlayer.getHealth() <= health.getValue()) {
                mc.playerController.attackEntity(mc.thePlayer, mc.thePlayer); // Using this as an alternative method to hotbarslot -999 or w/e
                this.toggle();
            }
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "health":
                    if (arguments.length >= 3) {
                        String newHealthString = arguments[2];
                        try {
                            float newHealth = arguments[2].equalsIgnoreCase("-d") ? health.getDefault() : Float.parseFloat(newHealthString);
                            if (health.getValue() > health.getMax())
                                health.setValue(health.getMax());
                            else if (health.getValue() < health.getMin())
                                health.setValue(health.getMin());

                            health.setValue(newHealth);
                            ChatLogger.print(String.format("AutoLog Health set to %s", health.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newHealthString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autolog health <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: health");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: autolog <action>");
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
