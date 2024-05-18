package pw.latematt.xiv.mod.mods.player;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import pw.latematt.xiv.utils.InventoryUtils;
import pw.latematt.xiv.value.ClampedValue;

import java.util.Objects;

/**
 * @author Matthew
 */
public class AutoArmor extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Item[] helmets = {Items.diamond_helmet, Items.iron_helmet, Items.golden_helmet, Items.chainmail_helmet, Items.leather_helmet};
    private final Item[] chestplates = {Items.diamond_chestplate, Items.iron_chestplate, Items.golden_chestplate, Items.chainmail_chestplate, Items.leather_chestplate};
    private final Item[] leggings = {Items.diamond_leggings, Items.iron_leggings, Items.golden_leggings, Items.chainmail_leggings, Items.leather_leggings};
    private final Item[] boots = {Items.diamond_boots, Items.iron_boots, Items.golden_boots, Items.chainmail_boots, Items.leather_boots};
    private final ClampedValue<Long> delay = new ClampedValue<>("autoarmor_delay", 250L, 0L, 1000L);
    private final Timer time = new Timer();

    public AutoArmor() {
        super("AutoArmor", ModType.PLAYER, Keyboard.KEY_NONE, 0xFF5976EC);
        Command.newCommand().cmd("autoarmor").description("Base command for the AutoArmor mod.").aliases("aarmor", "aa").arguments("<action>").handler(this).build();
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.PRE)) {
            int selectedSlotId = -1;
            if (time.hasReached(delay.getValue())) {
                if (mc.thePlayer.inventory.armorItemInSlot(2) == null) {
                    for (Item item : chestplates) {
                        int slotId = InventoryUtils.getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(1) == null) {
                    for (Item item : leggings) {
                        int slotId = InventoryUtils.getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(0) == null) {
                    for (Item item : boots) {
                        int slotId = InventoryUtils.getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(3) == null) {
                    for (Item item : helmets) {
                        int slotId = InventoryUtils.getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (selectedSlotId != -1) {
                    if (selectedSlotId < 9) {
                        selectedSlotId += 36;
                    }
                    mc.playerController.windowClick(0, selectedSlotId, 0, 1, mc.thePlayer);
                    time.reset();
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

                            ChatLogger.print(String.format("AutoArmor Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autoarmor delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: autoarmor <action>");
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
