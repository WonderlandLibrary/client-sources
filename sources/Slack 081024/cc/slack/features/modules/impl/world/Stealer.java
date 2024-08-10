// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.world;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.Login;
import cc.slack.utils.other.MathTimerUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ModuleInfo(
        name = "Stealer",
        category = Category.WORLD
)
public class Stealer extends Module {

    private final NumberValue<Double> openDelaymax = new NumberValue<>("Open Delay Max", 150D, 0D, 500D, 25D);
    private NumberValue<Double> openDelaymin = new NumberValue<>("Open Delay Min", 125D, 0D, 500D, 25D);
    private final NumberValue<Double> stealDelaymax = new NumberValue<>("Steal Delay Max", 150D, 25D, 500D, 25D);
    private NumberValue<Double> stealDelaymin = new NumberValue<>("Steal Delay Min", 125D, 25D, 500D, 25D);
    private final BooleanValue autoClose = new BooleanValue("Auto Close", true);
    private final NumberValue<Double> autocloseDelaymax = new NumberValue<>("Auto Close Delay Max", 0D, 0D, 500D, 1D);
    private NumberValue<Double> autocloseDelaymin = new NumberValue<>("Auto Close Delay Min", 0D, 0D, 500D, 1D);
    private final BooleanValue collectAll = new BooleanValue("No Filter", false);

    private final AtomicReference<ArrayList<Slot>> sortedSlots = new AtomicReference<>();
    private final AtomicReference<ContainerChest> chest = new AtomicReference<>();
    private final AtomicBoolean inChest = new AtomicBoolean(false);
    private final MathTimerUtil delayTimer = new MathTimerUtil(0);
    private final MathTimerUtil closeTimer = new MathTimerUtil(0);
    private final List<Item> whiteListedItems = Arrays.asList(Items.milk_bucket, Items.golden_apple, Items.potionitem,
            Items.ender_pearl, Items.water_bucket, Items.arrow, Items.bow);

    public Stealer() {
        addSettings(openDelaymax, openDelaymin, stealDelaymax, stealDelaymin, autoClose, autocloseDelaymax, autocloseDelaymin, collectAll);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {

        if (openDelaymin.getValue() > openDelaymax.getValue()) { openDelaymin = openDelaymax; }
        if (stealDelaymin.getValue() > stealDelaymax.getValue()) { stealDelaymin = stealDelaymax; }
        if (autocloseDelaymin.getValue() > autocloseDelaymax.getValue()) { autocloseDelaymin = autocloseDelaymax; }

        if ((mc.getCurrentScreen() != null) && (mc.thePlayer.inventoryContainer != null)
                && (mc.thePlayer.inventoryContainer instanceof ContainerPlayer)
                && (mc.getCurrentScreen() instanceof GuiChest)) {
            if (!inChest.get()) {
                chest.set((ContainerChest) mc.thePlayer.openContainer);
                delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(openDelaymin.getValue(),
                        openDelaymax.getValue() + 0.01));
                delayTimer.start();
                generatePath(chest.get());
                inChest.set(true);
            }

            if (inChest.get() && sortedSlots.get() != null && !sortedSlots.get().isEmpty()) {
                if (delayTimer.hasFinished()) {
                    clickSlot(sortedSlots.get().get(0).s);
                    delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(stealDelaymin.getValue(),
                            stealDelaymax.getValue() + 0.01));
                    delayTimer.start();
                    sortedSlots.get().remove(0);
                }
            }

            if (sortedSlots.get() != null && sortedSlots.get().isEmpty() && autoClose.getValue()) {
                if (collectAll.getValue() && !isChestEmpty(chest.get())) {
                    generatePath(chest.get()); // Regenerar el camino si el cofre no está vacío
                    return;
                }
                if (closeTimer.firstFinish()) {
                    mc.thePlayer.closeScreen();
                    inChest.set(false);
                } else {
                    closeTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(autocloseDelaymin.getValue(),
                            autocloseDelaymax.getValue() + 0.01));
                    closeTimer.start();
                }
            }
        } else {
            inChest.set(false);
        }
    }

    private void generatePath(ContainerChest chest) {
        ArrayList<Slot> slots = IntStream.range(0, chest.getLowerChestInventory().getSizeInventory()).mapToObj(i -> {
            ItemStack itemStack = chest.getInventory().get(i);
            if (itemStack != null) {
                if (collectAll.getValue() || shouldStealItem(itemStack)) {
                    return new Slot(i);
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));

        Slot[] sorted = sort(slots.toArray(new Slot[0]));
        sortedSlots.set(new ArrayList<>(Arrays.asList(sorted)));
    }

    private boolean shouldStealItem(ItemStack stack) {
        Item item = stack.getItem();
        return (item instanceof ItemSword && (PlayerUtil.getBestSword() == null
                || PlayerUtil.isBetterSword(stack, PlayerUtil.getBestSword())))
                || (item instanceof ItemAxe && (PlayerUtil.getBestAxe() == null
                || PlayerUtil.isBetterTool(stack, PlayerUtil.getBestAxe(), Blocks.planks)))
                || (item instanceof ItemPickaxe && (PlayerUtil.getBestAxe() == null
                || PlayerUtil.isBetterTool(stack, PlayerUtil.getBestAxe(), Blocks.stone)))
                || (item instanceof ItemBlock || item instanceof ItemArmor
                || whiteListedItems.contains(item));
    }

    private Slot[] sort(Slot[] in) {
        if (in == null || in.length == 0) {
            return in;
        }
        Slot[] out = new Slot[in.length];
        Slot current = in[ThreadLocalRandom.current().nextInt(0, in.length)];
        for (int i = 0; i < in.length; i++) {
            final Slot currentSlot = current;
            if (i == in.length - 1) {
                out[in.length - 1] = Arrays.stream(in).filter(p -> !p.visited).findAny().orElse(null);
                break;
            }
            out[i] = current;
            current.visit();
            Slot next = Arrays.stream(in).filter(p -> !p.visited)
                    .min(Comparator.comparingDouble(p -> p.getDistance(currentSlot))).orElse(null);
            current = next;
        }
        return out;
    }

    static class Slot {
        final int x;
        final int y;
        final int s;
        boolean visited;

        Slot(int s) {
            this.x = (s + 1) % 10;
            this.y = s / 9;
            this.s = s;
        }

        public double getDistance(Slot s) {
            return Math.abs(this.x - s.x) + Math.abs(this.y - s.y);
        }

        public void visit() {
            visited = true;
        }
    }

    private boolean isChestEmpty(ContainerChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getLowerChestInventory().getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }

    private void clickSlot(int x) {
        if (!Login.pj423j.contains(Login.sha256("true" + Login.yeu13))) {
            System.exit(1);
        }

        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, x, 0, 1, mc.thePlayer);
    }
}
