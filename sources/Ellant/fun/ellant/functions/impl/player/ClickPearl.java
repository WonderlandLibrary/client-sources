package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.Ellant;
import fun.ellant.events.EventKey;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.api.FunctionRegistry;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "ClickPearl", type = Category.PLAYER, desc = "Бросает пёрл по нажатию кнопки")
public class ClickPearl extends Function {
    final ModeSetting mod = new ModeSetting("Мод", "Обычный");
    final BindSetting throwKey = new BindSetting("Кнопка", -98);
    final StopWatch stopWatch = new StopWatch();
    final InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    final ItemCooldown itemCooldown;

    long delay;
    boolean throwPearl;

    public ClickPearl(ItemCooldown itemCooldown) {
        this.itemCooldown = itemCooldown;
        addSettings(throwKey);
    }

    @Subscribe
    public void onKey(EventKey e) {
        throwPearl = e.getKey() == throwKey.get();
    }

    @Subscribe
    private void onMotion(EventMotion e) {
        if (throwPearl) {
            if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL)) {
                if (mod.is("HolyWorld") && stopWatch.passed(2000)) { // Example: 2 seconds cooldown for HolyWorld mode
                    boolean isOffhandEnderPearl = mc.player.getHeldItemOffhand().getItem() instanceof EnderPearlItem;
                    if (isOffhandEnderPearl) {
                        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        mc.player.swingArm(Hand.MAIN_HAND);
                        stopWatch.reset(); // Reset the stopwatch after use
                        moveHandToHotbar();
                    } else {
                        int slot = findPearlAndThrow();
                        if (slot > 8) {
                            mc.playerController.pickItem(slot);
                            stopWatch.reset(); // Reset the stopwatch after use
                            moveHandToHotbar();
                        }
                    }
                } else if (mod.is("Обычный")) { // Normal mode without delay
                    int slot = findPearlAndThrow();
                    if (slot > 8) {
                        mc.playerController.pickItem(slot);
                    }
                }
            }
            throwPearl = false;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        this.handUtil.onEventPacket(e);
    }

    private int findPearlAndThrow() {
        int hbSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ENDER_PEARL, true);
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            }
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);

            FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();
            ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
            ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(Items.ENDER_PEARL);

            if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
                itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
            }

            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
            this.delay = System.currentTimeMillis();
            return hbSlot;
        }

        int invSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ENDER_PEARL, false);

        if (invSlot != -1) {
            handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);

            FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();
            ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
            ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(Items.ENDER_PEARL);

            if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
                itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
            }
            this.delay = System.currentTimeMillis();
            return invSlot;
        }
        return -1;
    }

    private void moveHandToHotbar() {
        // Move the item to the hotbar slot after using Ender Pearl in HolyWorld mode
        int hotbarSlot = InventoryUtil.getInstance().getFirstEmptyHotbarSlot(); // Get first empty slot in hotbar
        if (hotbarSlot != -1) {
            mc.playerController.pickItem(hotbarSlot);
        } else {
            // Handle if no empty slot is found (optional)
            // Example: move item to a specific slot in hotbar or inventory
            // mc.playerController.pickItem(9); // Example: move to slot 9 in hotbar
        }
    }

    @Override
    public void onDisable() {
        throwPearl = false;
        delay = 0;
        super.onDisable();
    }
}
