package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.block.BlockSlime;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Rederpz
 */
public class SlimeJump extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Value<Double> multiplier = new Value<>("slimejump_mulitplier", 0.1D);

    public SlimeJump() {
        super("SlimeJump", ModType.MOVEMENT, Keyboard.KEY_NONE, 0xFF66FF33);
        Command.newCommand().cmd("slimejump").description("Base command for SlimeJump mod.").arguments("<action>").aliases("sj").handler(this).build();
    }

    public void onEventCalled(MotionUpdateEvent event) {
        if (BlockUtils.getBlock(mc.thePlayer, -0.5D) instanceof BlockSlime) {
            if (mc.thePlayer.onGround) {
                if (mc.thePlayer.motionY > 0) {
                    mc.thePlayer.motionY += multiplier.getValue();
                }
            }
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "multiplier":
                case "m":
                case "h":
                case "height":
                    if (arguments.length >= 3) {
                        String newMultiplierString = arguments[2];
                        try {
                            double newMultiplier = arguments[2].equalsIgnoreCase("-d") ? multiplier.getDefault() : Double.parseDouble(newMultiplierString);
                            multiplier.setValue(newMultiplier);
                            ChatLogger.print(String.format("SlimeJump multiplier set to %s", multiplier.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newMultiplierString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: slimejump multiplier <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: multiplier");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: slimejump <action>");
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
