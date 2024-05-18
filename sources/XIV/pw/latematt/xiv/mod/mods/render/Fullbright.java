package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.LoadWorldEvent;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class Fullbright extends Mod implements Listener<LoadWorldEvent>, CommandHandler {
    private final ClampedValue<Float> brightness = new ClampedValue<>("fullbright_brightness", 0.4F, 0.0F, 1.0F);
    public final Value<Boolean> potion = new Value<>("fullbright_potion", false);
    private final Listener motionUpdateListener;

    public Fullbright() {
        super("Fullbright", ModType.RENDER, Keyboard.KEY_C, 0xFFFCFDCD);
        Command.newCommand().cmd("fullbright").description("Base command for the Fullbright mod.").arguments("<action>").aliases("bright", "b").handler(this).build();

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) { // Change this if needed, never done it on world load event but that might work
                if (potion.getValue()) {
                    mc.thePlayer.addPotionEffect(new PotionEffect(Potion.NIGHT_VISION.getId(), 16350, 0));
                }
            }
        };
    }

    public void editTable(WorldClient world, float value) {
        if (world == null)
            return;
        final float[] light = world.provider.lightBrightnessTable;
        for (int index = 0; index < light.length; index++) {
            if (light[index] > value) {
                continue;
            }
            light[index] = value;
        }
    }

    @Override
    public void onEventCalled(LoadWorldEvent event) {
        if (!potion.getValue()) {
            editTable(event.getWorld(), brightness.getValue());
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "brightness":
                    if (arguments.length >= 3) {
                        String newBrightnessString = arguments[2];
                        try {
                            float newBrightness = arguments[2].equalsIgnoreCase("-d") ? brightness.getDefault() : Float.parseFloat(newBrightnessString);
                            brightness.setValue(newBrightness);
                            if (brightness.getValue() > brightness.getMax())
                                brightness.setValue(brightness.getMax());
                            else if (brightness.getValue() < brightness.getMin())
                                brightness.setValue(brightness.getMin());

                            editTable(mc.theWorld, brightness.getValue());
                            ChatLogger.print(String.format("Fullbright Brightness set to %s", brightness.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newBrightnessString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: fullbright brightness <number>");
                    }
                    break;
                case "potion":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            potion.setValue(potion.getDefault());
                        } else {
                            potion.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        potion.setValue(!potion.getValue());
                    }
                    ChatLogger.print(String.format("Fullbright will %s give night vision.", (potion.getValue() ? "now" : "no longer")));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: brightness, potion");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fullbright <action>");
        }
    }

    @Override
    public void onEnabled() {
        if (mc.theWorld != null && !potion.getValue()) {
            editTable(mc.theWorld, brightness.getValue());
        }
        XIV.getInstance().getListenerManager().add(this, motionUpdateListener);
    }

    @Override
    public void onDisabled() {
        if (mc.theWorld != null) {
            if (potion.getValue()) {
                mc.thePlayer.removePotionEffect(Potion.NIGHT_VISION.getId());
            } else {
                for (int var2 = 0; var2 <= 15; ++var2) {
                    float var3 = 1.0F - (float) var2 / 15.0F;
                    mc.theWorld.provider.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F);
                }
            }
        }
        XIV.getInstance().getListenerManager().remove(this);
        XIV.getInstance().getListenerManager().remove(motionUpdateListener);
    }
}
