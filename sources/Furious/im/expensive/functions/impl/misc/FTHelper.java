package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventKey;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BindSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(
        name = "FTHelper",
        type = Category.Miscellaneous
)
public class FTHelper extends Function {

private final BindSetting disorientationKey = new BindSetting("Кнопка дезоринтации", -1);
private final BindSetting trapKey = new BindSetting("Кнопка трапки", -1);
private final BindSetting blatantKey = new BindSetting("Кнопка явной пыли", -1);
private final BindSetting otrigaKey = new BindSetting("Кнопка зелье отрыжки", -1);
private final BindSetting serkaKey = new BindSetting("Кнопка серной кислоты", -1);

final StopWatch stopWatch = new StopWatch();
InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
long delay;
        boolean disorientationThrow, trapThrow, blatantThrow, serkaThrow, otrigaThrow;

public FTHelper() {
    addSettings(disorientationKey, trapKey, blatantKey, serkaKey, otrigaKey);
}

@Subscribe
private void onKey(EventKey e) {
    if (e.getKey() == disorientationKey.get()) {
        disorientationThrow = true;
    }
    if (e.getKey() == trapKey.get()) {
        trapThrow = true;
    }
    if (e.getKey() == blatantKey.get()) {
        blatantThrow = true;
    }
    if (e.getKey() == otrigaKey.get()) {
        otrigaThrow = true;
    }
    if (e.getKey() == serkaKey.get()) {
        serkaThrow = true;
    }
}

@Subscribe
private void onUpdate(EventUpdate e) {
    if (disorientationThrow) {
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
        int hbSlot = getItemForName("дезориентация", true);
        int invSlot = getItemForName("дезориентация", false);

        if (invSlot == -1 && hbSlot == -1) {
            print("Дезориентация не найдена!");
            disorientationThrow = false;
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
            print("Использовал дезориентацию!");
            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
        }
        disorientationThrow = false;
    }

    if (trapThrow) {
        int hbSlot = getItemForName("трапка", true);
        int invSlot = getItemForName("трапка", false);


        if (invSlot == -1 && hbSlot == -1) {
            print("Трапка не найдена");
            trapThrow = false;
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(Items.NETHERITE_SCRAP)) {
            print("Использовал трапку!");
            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        }
        trapThrow = false;
    }
    if (serkaThrow) {
        int hbSlot = getItemForName("серная", true);
        int invSlot = getItemForName("серная", false);

        if (invSlot == -1 && hbSlot == -1) {
            print("Серная кислота не найдена");
            serkaThrow = false;
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
            print("Использовал серную кислоту!");
            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        }
        serkaThrow = false;
    }
    if (otrigaThrow) {
        int hbSlot = getItemForName("отрыжки", true);
        int invSlot = getItemForName("отрыжки", false);

        if (invSlot == -1 && hbSlot == -1) {
            print("Зелье отрыжки не найдена");
            otrigaThrow = false;
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
            print("Использовал зелье отрыжки!");
            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        }
        otrigaThrow = false;
    }
    if (blatantThrow) {
        int hbSlot = getItemForName("явная", true);
        int invSlot = getItemForName("явная", false);

        if (invSlot == -1 && hbSlot == -1) {
            print("Явная пыль не найдена");
            blatantThrow = false;
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(Items.TNT)) {
            print("Использовал явную пыль!");
            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        }
        blatantThrow = false;
    }
    this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
}

@Subscribe
private void onPacket(EventPacket e) {
    this.handUtil.onEventPacket(e);
}

private int findAndTrowItem(int hbSlot, int invSlot) {
    if (hbSlot != -1) {
        this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
        mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
        mc.player.swingArm(Hand.MAIN_HAND);
        this.delay = System.currentTimeMillis();
        return hbSlot;
    }
    if (invSlot != -1) {
        handUtil.setOriginalSlot(mc.player.inventory.currentItem);
        mc.playerController.pickItem(invSlot);
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
        mc.player.swingArm(Hand.MAIN_HAND);
        this.delay = System.currentTimeMillis();
        return invSlot;
    }
    return -1;
}

@Override
public void onDisable() {
    disorientationThrow = false;
    trapThrow = false;
    blatantThrow = false;
    otrigaThrow = false;
    serkaThrow = false;
    delay = 0;
    super.onDisable();
}

private int getItemForName(String name, boolean inHotBar) {
    int firstSlot = inHotBar ? 0 : 9;
    int lastSlot = inHotBar ? 9 : 36;
    for (int i = firstSlot; i < lastSlot; i++) {
        ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

        if (itemStack.getItem() instanceof AirItem) {
            continue;
        }

        String displayName = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString());
        if (displayName != null && displayName.toLowerCase().contains(name)) {
            return i;
        }
    }
    return -1;
}
}