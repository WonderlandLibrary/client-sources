package us.dev.direkt.module.internal.player;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.combat.ArmorBreaker;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author BFCE
 */
@ModData(label = "Auto Tool", category = ModCategory.PLAYER)
public class AutoTool extends ToggleableModule {

    @Exposed(description = "Automatically switch to tools")
    private Property<Boolean> tools = new Property<>("Tools", true);

    @Exposed(description = "Automatically switch to weapons")
    private Property<Boolean> weapons = new Property<>("Weapons", false);

    @Exposed(description = "Switch back to your previous item when done")
    private Property<Boolean> undo = new Property<>("Undo", true);

    private boolean hasSwitched = false;
    private int lastSlot;

    @Listener
    protected Link<EventGameTick> onGameTick = new Link<>(event -> {
        if (undo.getValue() && !Wrapper.getGameSettings().keyBindAttack.isKeyDown() && this.hasSwitched) {

            if (lastSlot >= 36 && lastSlot <= 44) {
                Wrapper.getPlayer().inventory.currentItem = lastSlot % 9;
            } else {
                Wrapper.getPlayerController().windowClick(Wrapper.getPlayer().inventoryContainer.windowId, lastSlot, Wrapper.getPlayer().inventory.currentItem, ClickType.SWAP, Wrapper.getPlayer());
            }

            this.hasSwitched = false;
        }
    });

    @Listener
    protected Link<EventSendPacket> onPacketSend = new Link<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity
                && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK
                && this.weapons.getValue() && !Direkt.getInstance().getModuleManager().getModule(ArmorBreaker.class).isRunning()) {
            float itemEffectiveness = -1;
            byte slot = -1;
            for (byte i = 9; i <= 44; i++) {
                ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
                if (stack != null && stack.getItem() instanceof ItemSword) {
                    ItemSword is = (ItemSword) stack.getItem();
                    float tempItemEffectiveness = is.getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("sharpness"), stack);
                    if (itemEffectiveness < tempItemEffectiveness) {
                        itemEffectiveness = tempItemEffectiveness;
                        slot = i;
                    }
                }
            }

            this.lastSlot = slot;

            if (slot >= 36 && slot <= 44)
                Wrapper.getPlayer().inventory.currentItem = slot % 9;
            else
                Wrapper.getPlayerController().windowClick(Wrapper.getPlayer().inventoryContainer.windowId, slot, Wrapper.getPlayer().inventory.currentItem, ClickType.SWAP, Wrapper.getPlayer());

        } else if (event.getPacket() instanceof CPacketPlayerDigging && this.tools.getValue()) {
            CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();

            if (packet.getAction().equals(CPacketPlayerDigging.Action.START_DESTROY_BLOCK)) {
                float itemEffectiveness = -1;
                ItemStack bestItem = null;
                byte slot = -1;
                for (byte i = 9; i <= 44; i++) {
                    ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
                    if (stack != null && isUsableTool(stack)) {
                        float tempItemEffectiveness = stack.getStrVsBlock(Wrapper.getBlock(packet.getPosition()).getDefaultState());
                        if(tempItemEffectiveness > 1)
                        if (itemEffectiveness < tempItemEffectiveness && Wrapper.getBlock(packet.getPosition()).getBlockHardness(Wrapper.getBlock(packet.getPosition()).getDefaultState(), Wrapper.getWorld(), packet.getPosition()) != 0) {
                            itemEffectiveness = tempItemEffectiveness;
                            bestItem = stack;
                            slot = i;
                        }
                    }
                }

                try {
                if (slot >= 36 && slot <= 44) {
                    if (!hasSwitched)
                        this.lastSlot = Wrapper.getPlayer().inventory.currentItem + 36;
                    Wrapper.getPlayer().inventory.currentItem = slot % 9;
                } else {
                    if (!hasSwitched)
                        this.lastSlot = slot;
                    Wrapper.getPlayerController().windowClick(Wrapper.getPlayer().inventoryContainer.windowId, slot, Wrapper.getPlayer().inventory.currentItem, ClickType.SWAP, Wrapper.getPlayer());
                }

                this.hasSwitched = true;
                } catch(ArrayIndexOutOfBoundsException e) {
                	;
                }
            }
        }
    }, new PacketFilter<>(CPacketUseEntity.class, CPacketPlayerDigging.class));

    private static boolean isUsableTool(ItemStack stack) {
        return stack.getItem() instanceof ItemPickaxe ||
                stack.getItem() instanceof ItemSpade ||
                stack.getItem() instanceof ItemAxe;
    }

}
