package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

import java.io.*;

/**
 * Created by myche on 2/3/2017.
 */
public class AutoSoup extends Module {

    private Value<Integer> delay = new Value("Delay", 500, 0, 1000);
    private Value<Float> health = new Value("Health", 13F, 0F, 20F);
    private final Stopwatch stopwatch = new Stopwatch();

    public AutoSoup() {
        super("AutoSoup", 0, Category.COMBAT);
        add(delay);
        add(health);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("autosoup") {
            @Override
            public void loadFile() {
                try {
                    final BufferedReader reader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String[] arguments = line.split(":");
                        if (arguments.length == 2) {
                            final Value value = findGivenValue(arguments[0]);
                            if (value != null) {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(Boolean.parseBoolean(arguments[1]));
                                } else if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(arguments[1]));
                                } else if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(arguments[1]));
                                } else if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(arguments[1]));
                                } else if (value.getValue() instanceof Long) {
                                    value.setValue(Long.parseLong(arguments[1]));
                                } else if (value.getValue() instanceof String) {
                                    value.setValue(arguments[1]);
                                }
                            }
                        }
                    }
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void saveFile() {
                try {
                    final BufferedWriter writer = new BufferedWriter(new FileWriter(
                            getFile()));
                    for (final Value val : getValues()) {
                        writer.write(val.getName().toLowerCase() + ":"
                                + val.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e) {
        if (updateCounter() == 0)
            return;
        if (mc.thePlayer.getHealth() <= health.getValue() && stopwatch.hasCompleted(delay.getValue())) {
            if (doesHotbarHaveSoups()) {
                eatSoup();
            } else {
                getSoupFromInventory();
            }
            stopwatch.reset();
        }
    }

    private boolean doesHotbarHaveSoups() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSoup(stack))
                return true;
        }
        return false;
    }

    private void eatSoup() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSoup(stack)) {
                stackBowls();
                final int oldslot = mc.thePlayer.inventory.currentItem;
                mc.getNetHandler().addToSendQueue(
                        new C09PacketHeldItemChange(index - 36));
                mc.playerController.updateController();
                mc.getNetHandler().addToSendQueue(
                        new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1,
                                stack, 0, 0, 0));
                mc.getNetHandler().addToSendQueue(
                        new C09PacketHeldItemChange(oldslot));
                break;
            }
        }
    }

    private void getSoupFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        stackBowls();
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }

            if (isStackSoup(stack)) {
                mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
                break;
            }
        }
    }

    private boolean isStackSoup(ItemStack stack) {
        if (stack == null)
            return false;
        return stack.getItem() instanceof ItemSoup;
    }

    private void stackBowls() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }

            if (stack.getItem() == Items.bowl) {
                mc.playerController.windowClick(0, index, 0, 0, mc.thePlayer);
                mc.playerController.windowClick(0, 9, 0, 0, mc.thePlayer);
                break;
            }
        }
    }

    private int updateCounter() {
        int counter = 0;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSoup(stack)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }
}
