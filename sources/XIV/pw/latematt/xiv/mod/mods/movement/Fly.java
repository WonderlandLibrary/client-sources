package pw.latematt.xiv.mod.mods.movement;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

import java.util.Objects;

/**
 * @author TehNeon
 */
public class Fly extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Value<Boolean> doDamage = new Value<>("fly_damage", true);
    private final Value<Boolean> slowfall = new Value<>("fly_slowfall", true);
    private final Value<Boolean> cap = new Value<>("fly_cap", false);
    private final ClampedValue<Double> verticalSpeed = new ClampedValue<>("fly_vertical", 0.5, 0.0, 2.0);
    private double yCap;

    public Fly() {
        super("Fly", ModType.MOVEMENT, Keyboard.KEY_M, 0xFF4B97F6);
        Command.newCommand().cmd("fly").aliases("flight").description("Base command for the Fly mod.").arguments("<action>").handler(this).build();
    }

    public void onEventCalled(MotionUpdateEvent event) {
        double motionY = slowfall.getValue() ? -0.005D : 0;

        mc.thePlayer.isAirBorne = false;

        if (cap.getValue() && mc.thePlayer.getEntityBoundingBox().maxY < yCap || !cap.getValue()) {
            if (mc.gameSettings.keyBindJump.getIsKeyPressed())
                motionY += verticalSpeed.getValue();
            setTag("");
        } else {
            setTag("*");
        }

        if (mc.gameSettings.keyBindSneak.getIsKeyPressed())
            motionY -= verticalSpeed.getValue();

        mc.thePlayer.motionY = motionY;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "vertical":
                    if (arguments.length >= 3) {
                        String newVerticalString = arguments[2];
                        try {
                            double newVertical = arguments[2].equalsIgnoreCase("-d") ? verticalSpeed.getDefault() : Double.parseDouble(newVerticalString);
                            verticalSpeed.setValue(newVertical);
                            if (verticalSpeed.getValue() > verticalSpeed.getMax())
                                verticalSpeed.setValue(verticalSpeed.getMax());
                            else if (verticalSpeed.getValue() < verticalSpeed.getMin())
                                verticalSpeed.setValue(verticalSpeed.getMin());

                            ChatLogger.print(String.format("Fly vertical speed set to %s", verticalSpeed.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newVerticalString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: fly vertical <number>");
                    }
                    break;
                case "damage":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            doDamage.setValue(doDamage.getDefault());
                        } else {
                            doDamage.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        doDamage.setValue(!doDamage.getValue());
                    }
                    ChatLogger.print(String.format("Fly will %s take damage on enable.", doDamage.getValue() ? "now" : "no longer"));
                    break;
                case "slowfall":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            slowfall.setValue(slowfall.getDefault());
                        } else {
                            slowfall.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        slowfall.setValue(!slowfall.getValue());
                    }
                    ChatLogger.print(String.format("Fly will %s slowly fall.", slowfall.getValue() ? "now" : "no longer"));
                    break;
                case "limit":
                case "cap":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            cap.setValue(cap.getDefault());
                        } else {
                            cap.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        cap.setValue(!cap.getValue());
                    }
                    ChatLogger.print(String.format("Fly will %s cap at start height.", cap.getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: damage, vertical, slowfall, cap");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fly <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
        if (Objects.nonNull(mc.thePlayer)) {
            if (doDamage.getValue() && mc.thePlayer.onGround)
                EntityUtils.damagePlayer(1);

            yCap = mc.thePlayer.getEntityBoundingBox().maxY;
        }
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}