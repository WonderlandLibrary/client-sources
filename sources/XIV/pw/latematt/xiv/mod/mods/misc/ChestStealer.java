package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;

/**
 * @author Matthew
 */
public class ChestStealer extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    public final ClampedValue<Long> delay = new ClampedValue<>("cheststealer_delay", 125L, 0L, 1000L);
    private final Timer timer = new Timer();

    public ChestStealer() {
        super("ChestStealer", ModType.MISCELLANEOUS, Keyboard.KEY_NONE, 0xFFE58144);
        Command.newCommand().cmd("cheststealer").description("Base command for ChestStealer mod.").arguments("<action>").aliases("chests", "cstealer", "cs").handler(this).build();
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                if (isChestEmpty(chest) || isInventoryFull())
                    mc.thePlayer.closeScreen();

                for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); index++) {
                    ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                    if (stack == null)
                        continue;
                    if (timer.hasReached(delay.getValue())) {
                        mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                        timer.reset();
                    }
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

                            ChatLogger.print(String.format("ChestStealer Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: cheststealer delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: cheststealer <action>");
        }
    }

    private boolean isChestEmpty(GuiChest chest) {
        for (int index = 0; index <= chest.getLowerChestInventory().getSizeInventory(); index++) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null)
                return false;
        }

        return true;
    }

    public boolean isInventoryFull() {
        for (int index = 9; index <= 44; index++) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                return false;
        }

        return true;
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
