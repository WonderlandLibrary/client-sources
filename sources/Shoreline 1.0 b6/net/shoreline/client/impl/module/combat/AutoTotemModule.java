package net.shoreline.client.impl.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.screen.slot.SlotActionType;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.PlayerTickEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.player.InventoryUtil;
import net.shoreline.client.util.player.PlayerUtil;
import net.shoreline.client.util.world.EndCrystalUtil;

/**
 * @author xgraza
 * @since 03/29/24
 */
public final class AutoTotemModule extends ToggleModule {

    EnumConfig<OffhandItem> itemConfig = new EnumConfig<>("Item", "The item to wield in your offhand", OffhandItem.TOTEM, OffhandItem.values());
    NumberConfig<Float> healthConfig = new NumberConfig<>("Health", "The health required to fall below before swapping to a totem", 0.0f, 14.0f, 20.0f);
    BooleanConfig gappleConfig = new BooleanConfig("OffhandGapple", "If to equip a golden apple if holding down the item use button", true);
    BooleanConfig crappleConfig = new BooleanConfig("Crapple", "If to use a normal golden apple if Absorption is present", true);
    Config<Boolean> lethalConfig = new BooleanConfig("Lethal", "Calculate lethal damage sources", false);
    // The player inventory sync ID
    private static final int INVENTORY_SYNC_ID = 0;

    public AutoTotemModule() {
        super("AutoTotem", "Automatically replenishes the totem in your offhand", ModuleCategory.COMBAT);
    }

    @Override
    public String getModuleData() {
        return String.valueOf(Managers.INVENTORY.count(Items.TOTEM_OF_UNDYING));
    }

    @EventListener
    public void onPlayerTick(final PlayerTickEvent event) {
        if (mc.currentScreen != null) {
            return;
        }
        // Get the item to wield in our offhand, and make sure we are already not holding the item
        final Item itemToWield = getItemToWield();
        if (PlayerUtil.isHolding(itemToWield)) {
            // ChatUtil.clientSendMessage("Holding item needed to swap, returning");
            return;
        }
//        // Only swap to a golden apple if the item in our main hand is not consumable
//        if ((itemToWield == Items.GOLDEN_APPLE || itemToWield == Items.ENCHANTED_GOLDEN_APPLE)) {
//            return;
//        }
        // Find the item in our inventory
        final int itemSlot = getSlotFor(itemToWield);
        if (itemSlot != -1) {
            // Do another quick swap (equivalent to hovering over an item & pressing F)
            mc.interactionManager.clickSlot(INVENTORY_SYNC_ID,
                    itemSlot < 9 ? itemSlot + 36 : itemSlot, 40, SlotActionType.SWAP, mc.player);
        }
    }

    private int getSlotFor(final Item item) {
        // Only take totems from the hotbar
        final int startSlot = item == Items.TOTEM_OF_UNDYING ? 0 : 9;
        // Search through our inventory
        for (int slot = 35; slot >= startSlot; slot--) {
            final ItemStack itemStack = mc.player.getInventory().getStack(slot);
            if (itemStack.getItem() == item) {
                return slot;
            }
        }
        return -1;
    }

    private Item getItemToWield() {
        // If the player's health (+absorption) falls below the "safe" amount, equip a totem
        final float health = PlayerUtil.getLocalPlayerHealth();
        if (health <= healthConfig.getValue()) {
            return Items.TOTEM_OF_UNDYING;
        }
        // Check fall damage
        final float heartsTaken = (float) PlayerUtil.computeFallDamage(mc.player.fallDistance, 1.0f);
        if (heartsTaken + 0.5f > mc.player.getHealth()) {
            return Items.TOTEM_OF_UNDYING;
        }
        if (lethalConfig.getValue()) {
            for (Entity e : mc.world.getEntities()) {
                if (e == null || !e.isAlive() || !(e instanceof EndCrystalEntity crystal)) {
                    continue;
                }
                if (mc.player.squaredDistanceTo(e) > 144.0) {
                    continue;
                }
                double potential = EndCrystalUtil.getDamageTo(mc.player, crystal.getPos());
                if (health + 0.5 > potential) {
                    continue;
                }
                return Items.TOTEM_OF_UNDYING;
            }
        }
        // If offhand gap is enabled & the use key is pressed down, equip a golden apple.
        if (gappleConfig.getValue() && mc.options.useKey.isPressed() && mc.player.getMainHandStack().getItem() instanceof SwordItem) {
            return getGoldenAppleType();
        }
        return itemConfig.getValue().getItem();
    }

    private Item getGoldenAppleType() {
        if (crappleConfig.getValue() && mc.player.hasStatusEffect(StatusEffects.ABSORPTION) && InventoryUtil.hasItemInInventory(Items.GOLDEN_APPLE, true)) {
            return Items.GOLDEN_APPLE;
        } else {
            return Items.ENCHANTED_GOLDEN_APPLE;
        }
    }

    private enum OffhandItem {
        TOTEM(Items.TOTEM_OF_UNDYING),
        GAPPLE(Items.ENCHANTED_GOLDEN_APPLE),
        CRYSTAL(Items.END_CRYSTAL);

        private final Item item;

        OffhandItem(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }
    }
}
