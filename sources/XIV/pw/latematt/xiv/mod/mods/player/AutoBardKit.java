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
public class AutoBardKit extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Item helmet = Items.golden_helmet;
    private final Item chestplate = Items.golden_chestplate;
    private final Item leggings = Items.golden_leggings;
    private final Item boots = Items.golden_boots;
    private final ClampedValue<Long> delay = new ClampedValue<>("autobardkit_delay", 250L, 0L, 1000L);
    private final Timer time = new Timer();

    public AutoBardKit() {
        super("AutoBardKit", ModType.PLAYER, Keyboard.KEY_NONE, 0xFFE3CC4D);
        Command.newCommand().cmd("autobardkit").description("Base command for the AutoBardKit mod.").aliases("abardkit", "autobk", "abk").arguments("<action>").handler(this).build();
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.PRE)) {
            int selectedSlotId = -1;
            if (time.hasReached(delay.getValue())) {
                if (mc.thePlayer.inventory.armorItemInSlot(2) == null) {
                    int slotId = InventoryUtils.getSlotID(chestplate);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(1) == null) {
                    int slotId = InventoryUtils.getSlotID(leggings);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(0) == null) {
                    int slotId = InventoryUtils.getSlotID(boots);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }

                if (mc.thePlayer.inventory.armorItemInSlot(3) == null) {
                    int slotId = InventoryUtils.getSlotID(helmet);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
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

                            ChatLogger.print(String.format("AutoBardKit Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autobardkit delay <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: autobardkit <action>");
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
