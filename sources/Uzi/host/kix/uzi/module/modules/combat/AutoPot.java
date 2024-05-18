package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

import java.io.*;

/**
 * Created by myche on 2/4/2017.
 */
public class AutoPot extends Module {

    private Value<Float> health = new Value("Health", 18.0F, 0F, 20F);
    private Value<Integer> delay = new Value("Delay", 500, 0, 1000);
    private final Stopwatch stopwatch = new Stopwatch();
    private boolean potting;

    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT);
        add(health);
        add(delay);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("autopot") {
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
        if (e.type == EventType.PRE) {
            if (updateCounter() == 0)
                return;
            if (mc.thePlayer.getHealth() <= health.getValue()
                    && stopwatch.hasCompleted(delay.getValue())) {
                if (doesHotbarHavePots()) {
                    potting = true;
                    e.pitch = 90;
                }
            }
        } else {
            if (e.type == EventType.POST) {
                if (updateCounter() == 0)
                    return;
                if (mc.thePlayer.getHealth() <= health.getValue()
                        && stopwatch.hasCompleted(delay.getValue())) {
                    if (doesHotbarHavePots()) {
                        splashPot();
                        potting = false;
                    } else {
                        getPotsFromInventory();
                    }
                    stopwatch.reset();
                }
            }
        }
    }

    private boolean doesHotbarHavePots() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSplashHealthPot(stack))
                return true;
        }
        return false;
    }

    private void getPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }
            if (isStackSplashHealthPot(stack)) {
                mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
                break;
            }
        }
    }

    private boolean isStackSplashHealthPot(ItemStack stack) {
        if (stack == null)
            return false;
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.heal.id)
                        return true;
                }
            }
        }
        return false;
    }

    private void splashPot() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }

            if (isStackSplashHealthPot(stack)) {
                final int oldslot = mc.thePlayer.inventory.currentItem;
                potting = true;
                mc.getNetHandler().addToSendQueue(
                        new C03PacketPlayer.C05PacketPlayerLook(
                                mc.thePlayer.rotationYaw, 90,
                                mc.thePlayer.onGround));
                mc.getNetHandler().addToSendQueue(
                        new C09PacketHeldItemChange(index - 36));
                mc.playerController.updateController();
                mc.getNetHandler().addToSendQueue(
                        new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1,
                                stack, 0, 0, 0));
                mc.getNetHandler().addToSendQueue(
                        new C09PacketHeldItemChange(oldslot));
                potting = false;
                mc.getNetHandler().addToSendQueue(
                        new C03PacketPlayer.C05PacketPlayerLook(
                                mc.thePlayer.rotationYaw,
                                mc.thePlayer.rotationPitch,
                                mc.thePlayer.onGround));
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
            if (isStackSplashHealthPot(stack)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }

}
