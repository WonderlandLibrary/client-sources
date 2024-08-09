package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventCooldown;
import im.expensive.events.EventKey;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BindSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.*;
import net.minecraft.potion.Effects;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "AutoSwap", type = Category.Combat)
public class AutoSwap extends Function {
    final ModeSetting swapMode = new ModeSetting("Тип", "Умный", "Умный", "По бинду");
    final ModeSetting itemType = new ModeSetting("Предмет", "Щит", "Щит", "Геплы", "Тотем", "Шар");
    final ModeSetting swapType = new ModeSetting("Свапать на", "Геплы", "Щит", "Геплы", "Тотем", "Шар");

    final BindSetting keyToSwap = new BindSetting("Кнопка", -1).setVisible(() -> swapMode.is("По бинду"));
    final SliderSetting health = new SliderSetting("Здоровье", 11.0F, 5.0F, 19.0F, 0.5F).setVisible(() -> swapMode.is("Умный"));
    final StopWatch stopWatch = new StopWatch();
    boolean shieldIsCooldown;
    int oldItem = -1;

    final StopWatch delay = new StopWatch();
    final AutoTotem autoTotem;

    public AutoSwap(AutoTotem autoTotem) {
        this.autoTotem = autoTotem;
        addSettings(swapMode, itemType, swapType, keyToSwap, health);
    }

    @Subscribe
    public void onEventKey(EventKey e) {
        if (!swapMode.is("По бинду")) {
            return;
        }

        ItemStack offhandItemStack = mc.player.getHeldItemOffhand();
        boolean isOffhandNotEmpty = !(offhandItemStack.getItem() instanceof AirItem);

        if (e.isKeyDown(keyToSwap.get()) && stopWatch.isReached(200)) {
            Item currentItem = offhandItemStack.getItem();
            boolean isHoldingSwapItem = currentItem == getSwapItem();
            boolean isHoldingSelectedItem = currentItem == getSelectedItem();
            int selectedItemSlot = getSlot(getSelectedItem());
            int swapItemSlot = getSlot(getSwapItem());

            if (selectedItemSlot >= 0) {
                if (!isHoldingSelectedItem) {
                    InventoryUtil.moveItem(selectedItemSlot, 45, isOffhandNotEmpty);
                    stopWatch.reset();
                    return;
                }
            }
            if (swapItemSlot >= 0) {
                if (!isHoldingSwapItem) {
                    InventoryUtil.moveItem(swapItemSlot, 45, isOffhandNotEmpty);
                    stopWatch.reset();
                }
            }
        }
    }

    @Subscribe
    private void onCooldown(EventCooldown e) {
        shieldIsCooldown = isCooldown(e);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (!swapMode.is("Умный")) {
            return;
        }

        Item currentItem = mc.player.getHeldItemOffhand().getItem();

        if (stopWatch.isReached(400L)) {
            swapIfShieldIsBroken(currentItem);
            swapIfHealthToLow(currentItem);
            stopWatch.reset();
        }
        boolean isRightClickWithGoldenAppleActive = false;

        if (currentItem == Items.GOLDEN_APPLE && !mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE)) {
            isRightClickWithGoldenAppleActive = mc.gameSettings.keyBindUseItem.isKeyDown();
        }


        if (isRightClickWithGoldenAppleActive) {
            stopWatch.reset();
        }
    }

    @Override
    public void onDisable() {
        shieldIsCooldown = false;
        oldItem = -1;
        super.onDisable();
    }

    private void swapIfHealthToLow(Item currentItem) {
        boolean isOffhandNotEmpty = !(currentItem instanceof AirItem);
        boolean isHoldingGoldenApple = currentItem == getSwapItem();
        boolean isHoldingSelectedItem = currentItem == getSelectedItem();
        boolean gappleIsNotCooldown = !mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);

        int goldenAppleSlot = getSlot(getSwapItem());

        if (shieldIsCooldown || !gappleIsNotCooldown) {
            return;
        }

        if (isLowHealth() && !isHoldingGoldenApple && isHoldingSelectedItem) {
            InventoryUtil.moveItem(goldenAppleSlot, 45, isOffhandNotEmpty);
            if (isOffhandNotEmpty && oldItem == -1) {
                oldItem = goldenAppleSlot;
            }
        } else if (!isLowHealth() && isHoldingGoldenApple && oldItem >= 0) {
            InventoryUtil.moveItem(oldItem, 45, isOffhandNotEmpty);
            oldItem = -1;
        }
    }

    private void swapIfShieldIsBroken(Item currentItem) {
        boolean isOffhandNotEmpty = !(currentItem instanceof AirItem);
        boolean isHoldingGoldenApple = currentItem == getSwapItem();
        boolean isHoldingSelectedItem = currentItem == getSelectedItem();
        boolean gappleIsNotCooldown = !mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
        int goldenAppleSlot = getSlot(getSwapItem());

        if (shieldIsCooldown && !isHoldingGoldenApple && isHoldingSelectedItem && gappleIsNotCooldown) {
            InventoryUtil.moveItem(goldenAppleSlot, 45, isOffhandNotEmpty);
            if (isOffhandNotEmpty && oldItem == -1) {
                oldItem = goldenAppleSlot;
            }
            print(shieldIsCooldown + "");
        } else if (!shieldIsCooldown && isHoldingGoldenApple && oldItem >= 0) {
            InventoryUtil.moveItem(oldItem, 45, isOffhandNotEmpty);
            oldItem = -1;
        }
    }

    private boolean isLowHealth() {
        float currentHealth = mc.player.getHealth() + (mc.player.isPotionActive(Effects.ABSORPTION) ? mc.player.getAbsorptionAmount() : 0.0f);
        return currentHealth <= health.get();
    }

    private boolean isCooldown(EventCooldown cooldown) {
        Item item = cooldown.getItem();


        if (!itemType.is("Shield")) {
            return false;
        } else {
            return cooldown.isAdded() && item instanceof ShieldItem;
        }
    }

    private Item getSwapItem() {
        return getItemByType(swapType.get());
    }

    private Item getSelectedItem() {
        return getItemByType(itemType.get());
    }

    private Item getItemByType(String itemType) {
        return switch (itemType) {
            case "Щит" -> Items.SHIELD;
            case "Тотем" -> Items.TOTEM_OF_UNDYING;
            case "Геплы" -> Items.GOLDEN_APPLE;
            case "Шар" -> Items.PLAYER_HEAD;
            default -> Items.AIR;
        };
    }

    private int getSlot(Item item) {
        int finalSlot = -1;
        for (int i = 0; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                if (mc.player.inventory.getStackInSlot(i).isEnchanted()) {
                    finalSlot = i;
                    break;
                } else {
                    finalSlot = i;
                }
            }
        }
        if (finalSlot < 9 && finalSlot != -1) {
            finalSlot = finalSlot + 36;
        }
        return finalSlot;
    }
}