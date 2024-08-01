package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import com.google.common.base.Stopwatch;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import org.apache.commons.lang3.RandomUtils;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

import java.util.concurrent.TimeUnit;

@ModuleMetaData(
        name = "Chest Stealer",
        description = "Automatically steals items from chests.",
        category = ModuleCategoryEnum.MISC
)
public final class ChestStealerModule extends AbstractModule {
    private final NumberSetting<Integer> minDelay = new NumberSetting<>("Min Delay", 0, 0, 1000, 25);
    private final NumberSetting<Integer> maxDelay = new NumberSetting<>("Max Delay", 0, 0, 1000, 25);
    private final BooleanSetting autoClose = new BooleanSetting("Auto Close", true);
    private final BooleanSetting onlyChests = new BooleanSetting("Only Chests", true);
    private final BooleanSetting whitelist = new BooleanSetting("Whitelist", true);

    private final Stopwatch stopwatch = Stopwatch.createUnstarted();


    public ChestStealerModule() {
        this.registerSettings(minDelay, maxDelay, autoClose, onlyChests, whitelist);
    }

    private int delay;

    @Override
    protected void onEnable() {
        super.onEnable();
        stopwatch.start();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        stopwatch.reset();
    }

    @EventHandler
    private final Listener<MotionEvent> updateListener = e -> {
        this.delay = generateDelay();
        this.setSuffix((minDelay.getValue() + maxDelay.getValue() / 2) + "ms");

        if (mc.currentScreen instanceof GuiChest) {
            executeStealerTask();
        }
    };

    private void executeStealerTask() {
        boolean containerEmpty = true;

        final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
        if (!(container.getLowerChestInventory().getName().equals("Chest")) && onlyChests.getValue()) {
            return;
        }

        for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
            if (container.getLowerChestInventory().getStackInSlot(i) != null) {
                final ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);

                if (whitelist.getValue() && !isWhitelistedItem(itemStack.getItem())) {
                    continue;
                }

                containerEmpty = false;
                if (stopwatch.elapsed(TimeUnit.MILLISECONDS) >= delay) {
                    takeItemFromSlot(container.windowId, i);
                }
            }
        }

        if (autoClose.getValue() && containerEmpty) {
            mc.thePlayer.closeScreen();
        }
    }

    private void takeItemFromSlot(final int windowId, final int slot) {
        mc.playerController.windowClick(windowId, slot, 0, 1, mc.thePlayer);
        stopwatch.reset();
        stopwatch.start();
    }

    private static boolean isWhitelistedItem(final Item item) {
        return item instanceof ItemArmor || item instanceof ItemBlock || item instanceof ItemPotion ||
                item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemFood ||
                item instanceof ItemBucket || item instanceof ItemEnderPearl || item instanceof ItemEgg ||
                item instanceof ItemSnowball || item instanceof ItemBow || item instanceof ItemFishingRod ||
                item instanceof ItemFlintAndSteel || item.getUnlocalizedName().equals("item.arrow");
    }

    private int generateDelay() {
        return RandomUtils.nextInt(minDelay.getValue(), maxDelay.getValue());
    }
}
