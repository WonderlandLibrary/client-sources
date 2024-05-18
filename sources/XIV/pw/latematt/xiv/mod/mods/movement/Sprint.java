package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.item.ItemBow;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.player.FastUse;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class Sprint extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Value<Boolean> shotBow = new Value<>("sprint_shotbow", false);

    public Sprint() {
        super("Sprint", ModType.MOVEMENT, Keyboard.KEY_B, 0xFF72B190);
        Command.newCommand().cmd("sprint").description("Base command for Sprint mod.").arguments("<action>").handler(this).build();
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
            mc.thePlayer.setSprinting(canSprint());
        }
    }

    public boolean canSprint() {
        boolean movingForward = mc.thePlayer.movementInput.moveForward > 0;
        boolean strafing = mc.thePlayer.movementInput.moveStrafe != 0;
        boolean moving = movingForward && strafing || movingForward;

        boolean sneaking = mc.thePlayer.isSneaking();
        boolean collided = mc.thePlayer.isCollidedHorizontally;
        boolean hungry = mc.thePlayer.getFoodStats().getFoodLevel() <= 6 && !mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.capabilities.isCreativeMode;

        return moving && !sneaking && !collided && !hungry && !shouldSlowdown();
    }

    public boolean shouldSlowdown() {
        if (shotBow.getValue() && mc.thePlayer.getItemInUse() != null) {
            if (mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)
                return true;
            int maxUseDuration = mc.thePlayer.getItemInUse().getMaxItemUseDuration();
            FastUse fastUse = (FastUse) XIV.getInstance().getModManager().find("fastuse");
            if (fastUse != null && fastUse.isEnabled())
                maxUseDuration = fastUse.getTicksToWait().getValue();
            if (mc.thePlayer.getItemInUseDuration() > maxUseDuration - 2)
                return true;
        }

        return false;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "shotbow":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            shotBow.setValue(shotBow.getDefault());
                        } else {
                            shotBow.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        shotBow.setValue(!shotBow.getValue());
                    }
                    ChatLogger.print(String.format("Sprint will %s bypass Shotbow's anticheat.", (shotBow.getValue() ? "now" : "no longer")));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: shotbow");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: sprint <action>");
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
