package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "ChestStealer", type = Category.Miscellaneous)
public class ChestStealer extends Function {


    public ChestStealer() {
    }

    final StopWatch stopWatch = new StopWatch();


    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player.openContainer instanceof ChestContainer container) {
            IInventory lowerChestInventory = container.getLowerChestInventory();
            for (int index = 0; index < lowerChestInventory.getSizeInventory(); ++index) {
                ItemStack stack = lowerChestInventory.getStackInSlot(index);
                if (!shouldMoveItem(container, index)) {
                    continue;
                }
                if (isContainerEmpty(stack)) {
                    continue;
                }
                if (35 == 0.0f) {
                    moveItem(container, index, lowerChestInventory.getSizeInventory());
                } else {
                    if (stopWatch.isReached(35)) {
                        mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                        stopWatch.reset();
                    }
                }
            }
        }
    }

    private boolean shouldMoveItem(ChestContainer container, int index) {
        ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(index);
        return itemStack.getItem() != Item.getItemById(0);
    }

    private void moveItem(ChestContainer container, int index, int multi) {
        for (int i = 0; i < multi; i++) {
            mc.playerController.windowClick(container.windowId, index + i, 0, ClickType.QUICK_MOVE, mc.player);
        }
    }

    public boolean mystHoly(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (item == Items.PLAYER_HEAD
                || item == Items.TOTEM_OF_UNDYING
                || item == Items.TNT
                || item == Items.NETHER_STAR
                || item == Items.PRISMARINE_SHARD
                || item == Items.EVOKER_SPAWN_EGG
                || item == Items.PRISMARINE_CRYSTALS
                || item == Items.FIRE_CHARGE
                || item == Items.EXPERIENCE_BOTTLE
                || item == Items.ENCHANTED_BOOK
                || item == Items.CARVED_PUMPKIN
                || item == Items.SPLASH_POTION
        );
    }

    private boolean isContainerEmpty(ItemStack stack) {
        return !mystHoly(stack);
    }

}
