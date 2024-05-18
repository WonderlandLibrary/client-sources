package pw.latematt.xiv.mod.mods.world;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.BreakingBlockEvent;
import pw.latematt.xiv.event.events.ResetDamageEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

/**
 * @author Jack
 * @author Matthew
 * @author Rederpz
 */
public class Speedmine extends Mod implements Listener<BreakingBlockEvent>, CommandHandler {
    private final ClampedValue<Double> multiplier = new ClampedValue<>("speedmine_multiplier", 1.25D, 0.75D, 5.0D);
    private final ClampedValue<Integer> hitDelay = new ClampedValue<>("speedmine_hit_delay", 0, 0, 4);
    private final Value<Boolean> saveDamage = new Value<>("speedmine_save_damage", true);

    private final Listener resetDamageListener;

    public Speedmine() {
        super("Speedmine", ModType.WORLD, Keyboard.KEY_G, 0xFF77A24E);
        Command.newCommand().cmd("speedmine").description("Base command for the Speedmine mod.").arguments("<action>").aliases("smine").handler(this).build();

        resetDamageListener = new Listener<ResetDamageEvent>() {
            @Override
            public void onEventCalled(ResetDamageEvent event) {
                if(saveDamage.getValue()) {
                    event.setCancelled(true);
                }
            }
        };
    }

    @Override
    public void onEventCalled(BreakingBlockEvent event) {
        event.setMultiplier(multiplier.getValue());
        event.setHitDelay(hitDelay.getValue());
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "hitdelay":
                    if (arguments.length >= 3) {
                        String newHitDelayString = arguments[2];
                        try {
                            int newHitDelay = arguments[2].equalsIgnoreCase("-d") ? hitDelay.getDefault() : Integer.parseInt(newHitDelayString);
                            hitDelay.setValue(newHitDelay);
                            if (hitDelay.getValue() > hitDelay.getMax())
                                hitDelay.setValue(hitDelay.getMax());
                            else if (hitDelay.getValue() < hitDelay.getMin())
                                hitDelay.setValue(hitDelay.getMin());

                            ChatLogger.print(String.format("Speedmine Hit Delay set to %s", hitDelay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newHitDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: speedmine hitdelay <number>");
                    }
                    break;
                case "multiplier":
                    if (arguments.length >= 3) {
                        String newMultiplierString = arguments[2];
                        try {
                            double newMultiplier = arguments[2].equalsIgnoreCase("-d") ? multiplier.getDefault() : Double.parseDouble(newMultiplierString);
                            multiplier.setValue(newMultiplier);
                            if (multiplier.getValue() > multiplier.getMax())
                                multiplier.setValue(multiplier.getMax());
                            else if (multiplier.getValue() < multiplier.getMin())
                                multiplier.setValue(multiplier.getMin());
                            ChatLogger.print(String.format("Speedmine Multiplier set to %s", multiplier.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newMultiplierString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: speedmine multiplier <number>");
                    }
                    break;
                case "savedamage":
                case "save":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            saveDamage.setValue(saveDamage.getDefault());
                        } else {
                            saveDamage.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        saveDamage.setValue(!saveDamage.getValue());
                    }
                    ChatLogger.print(String.format("Speedmine will %s save block removing.", saveDamage.getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: hitdelay, multiplier, savedamage");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: speedmine <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, resetDamageListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, resetDamageListener);
    }
}
