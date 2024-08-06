package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.networking.PacketOutboundEvent;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.player.WindowClickEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.combat.KillAura;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.networking.PacketUtil;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.system.Stopwatch;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@ModuleInfo(name = "InventoryManager", description = "Automatically manages your inventory.", aliases = {"InvManager", "Manager"}, category = Category.PLAYER)
public final class InventoryManager extends Module {

    private final ModeSetting<InventoryMode> modeSetting = new ModeSetting<>("Mode", InventoryMode.SPOOF);
    private final BooleanSetting cleanSetting = new BooleanSetting("Clean", true);
    private final BooleanSetting sortSetting = new BooleanSetting("Sort", true);
    private final BooleanSetting autoArmorSetting = new BooleanSetting("Auto Armor", true);
    private final BooleanSetting whileFightingSetting = new BooleanSetting("While Fighting", false);
    private final DoubleSetting delaySetting = new DoubleSetting("Click Delay", 50, 0, 300, 10);
    private final DoubleSetting blocksSetting = new DoubleSetting("Blocks", 64, 0, 512, 64, cleanSetting::getValue);
    private final DoubleSetting arrowsSetting = new DoubleSetting("Arrows", 64, 0, 512, 64, cleanSetting::getValue);

    private KillAura killAura;
    private List<ItemStack> blocks, arrows;
    private final Stopwatch interactionsTimer = new Stopwatch();
    private boolean spoofOpened;

    @Override
    public Supplier<Object> getSuffix() {
        return delaySetting::getInt;
    }

    @EventHandler
    private final Listener<WindowClickEvent> windowClickListener = e -> interactionsTimer.reset();

    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundEventListener = e -> {
        if (modeSetting.getValue() == InventoryMode.SPOOF && spoofOpened) {
            if (e.getPacket() instanceof C16PacketClientStatus)
                e.setCancelled(true);
            if (e.getPacket() instanceof C0DPacketCloseWindow) {
                C0DPacketCloseWindow p = e.getPacket();
                e.setCancelled(p.windowId == mc.thePlayer.inventoryContainer.windowId);
            }
        }
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        final boolean shouldBeOpen = modeSetting.getValue() == InventoryMode.SPOOF ||
                (modeSetting.getValue() == InventoryMode.OPEN && mc.currentScreen instanceof GuiInventory)
                || modeSetting.getValue() == InventoryMode.NONE;

        if (!shouldBeOpen) return;

        if (killAura == null)
            killAura = Client.INSTANCE.getModuleManager().getModule(KillAura.class);

        final boolean fighting = mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY &&
                mc.objectMouseOver.entityHit.hurtResistantTime >= 10 ||
                killAura.getNextTarget() != null && killAura.getNextTarget().hurtResistantTime >= 10;

        if (fighting && !whileFightingSetting.getValue()) return;
        if(mc.thePlayer.isUsingItem()) return;

        int bestSwordSlot = -1,
                bestPickaxeSlot = -1,
                bestShovelSlot = -1,
                bestAxeSlot = -1,
                bestFoodSlot = -1,
                bestBowSlot = -1;

        float bestSwordDamage = -1,
                bestPickaxeDamage = -1,
                bestShovelDamage = -1,
                bestAxeDamage = -1,
                bestFoodDamage = -1,
                bestBowDamage = -1,
                totalBlockSize = 0,
                totalArrowSize = 0;

        int[] bestArmorSlots = new int[4];
        float[] bestArmorDamage = new float[4];
        Arrays.fill(bestArmorSlots, -1);
        Arrays.fill(bestArmorDamage, -1);
        if (blocks == null) {
            blocks = new ArrayList<>();
            arrows = new ArrayList<>();
        }
        blocks.clear();
        arrows.clear();

        for (int slot = InventoryUtil.INCLUDE_ARMOR; slot < InventoryUtil.END; slot++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
            if (stack == null) continue;
            final float damage = InventoryUtil.getItemDamage(stack);
            if (damage > 0) {
                if (stack.getItem() instanceof ItemSword) {
                    if(bestSwordSlot != -1 && bestSwordDamage == damage) {
                        final ItemStack bestStack = mc.thePlayer.inventoryContainer.getSlot(bestSwordSlot).getStack();
                        final float bestDurability = bestStack.getMaxDamage() - bestStack.getItemDamage(),
                                durability = stack.getMaxDamage() - stack.getItemDamage();
                        if(durability != 0 && bestDurability <= durability)
                            click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                        continue;
                    }
                    if (bestSwordDamage < damage) {
                        bestSwordDamage = damage;
                        bestSwordSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemBow) {
                    if(bestBowSlot != -1 && bestBowDamage == damage) {
                        final ItemStack bestStack = mc.thePlayer.inventoryContainer.getSlot(bestBowSlot).getStack();
                        final float bestDurability = bestStack.getMaxDamage() - bestStack.getItemDamage(),
                                durability = stack.getMaxDamage() - stack.getItemDamage();
                        if(durability != 0 && bestDurability <= durability)
                            click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                        continue;
                    }
                    if (bestBowDamage < damage) {
                        bestBowDamage = damage;
                        bestBowSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemPickaxe) {
                    if(bestPickaxeSlot != -1 && bestPickaxeDamage == damage) {
                        final ItemStack bestStack = mc.thePlayer.inventoryContainer.getSlot(bestPickaxeSlot).getStack();
                        final float bestDurability = bestStack.getMaxDamage() - bestStack.getItemDamage(),
                                durability = stack.getMaxDamage() - stack.getItemDamage();
                        if(durability != 0 && bestDurability <= durability)
                            click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                        continue;
                    }
                    if (bestPickaxeDamage < damage) {
                        bestPickaxeDamage = damage;
                        bestPickaxeSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemAxe) {
                    if(bestAxeSlot != -1 && bestAxeDamage == damage) {
                        final ItemStack bestStack = mc.thePlayer.inventoryContainer.getSlot(bestAxeSlot).getStack();
                        final float bestDurability = bestStack.getMaxDamage() - bestStack.getItemDamage(),
                                durability = stack.getMaxDamage() - stack.getItemDamage();
                        if(durability != 0 && bestDurability <= durability)
                            click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                        continue;
                    }
                    if (bestAxeDamage < damage) {
                        bestAxeDamage = damage;
                        bestAxeSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemSpade) {
                    if(bestShovelSlot != -1 && bestShovelDamage == damage) {
                        final ItemStack bestStack = mc.thePlayer.inventoryContainer.getSlot(bestShovelSlot).getStack();
                        final float bestDurability = bestStack.getMaxDamage() - bestStack.getItemDamage(),
                                durability = stack.getMaxDamage() - stack.getItemDamage();
                        if(durability != 0 && bestDurability <= durability)
                            click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                        continue;
                    }
                    if (bestShovelDamage < damage) {
                        bestShovelDamage = damage;
                        bestShovelSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemFood && stack.getItem() != Items.golden_apple) {
                    if (bestFoodDamage < damage) {
                        bestFoodDamage = damage;
                        bestFoodSlot = slot;
                    }
                }
                if (stack.getItem() instanceof ItemArmor) {
                    ItemArmor armor = (ItemArmor) stack.getItem();
                    if (bestArmorDamage[armor.armorType] < damage) {
                        bestArmorDamage[armor.armorType] = damage;
                        bestArmorSlots[armor.armorType] = slot;
                    }
                }
                if (stack.getItem() instanceof ItemBlock && blocksSetting.getValue() > 0) {
                    final int size = MathHelper.ceiling_float_int(blocksSetting.getFloat() / (float)blocksSetting.getIncrement());
                    blocks.add(stack);
                    if (blocks.size() > size) {
                        blocks.sort(Comparator.comparingInt(b -> ((ItemStack)b).stackSize).reversed());
                        final ItemStack worstStack = blocks.get(blocks.size() - 1);
                        blocks.set(blocks.size() - 1, stack);
                        blocks.remove(worstStack);
                    }
                }
            }
            if(stack.getItem() == Items.arrow) {
                final int size = MathHelper.ceiling_float_int(arrowsSetting.getFloat() / (float)arrowsSetting.getIncrement());
                arrows.add(stack);
                if (arrows.size() > size) {
                    arrows.sort(Comparator.comparingInt(b -> ((ItemStack)b).stackSize).reversed());
                    final ItemStack worstStack = arrows.get(arrows.size() - 1);
                    arrows.set(arrows.size() - 1, stack);
                    arrows.remove(worstStack);
                }
            }
        }
        if (autoArmorSetting.getValue()) {
            for (int i = 0; i < bestArmorSlots.length; i++) {
                final int slot = bestArmorSlots[i];
                if (slot != -1 && interactionsTimer.hasElapsed(delaySetting.getValue().longValue()) &&
                        mc.thePlayer.inventoryContainer.getSlot(i + InventoryUtil.INCLUDE_ARMOR).getStack() == null)
                    click(slot, 0, InventoryUtil.ClickType.SHIFT_CLICK);
            }
        }
        final int[] slots = { bestSwordSlot, bestBowSlot, bestPickaxeSlot, bestAxeSlot, bestShovelSlot, bestFoodSlot };
        if (sortSetting.getValue()) {
            int currentSlot = InventoryUtil.HOTBAR;
            for (int item : slots) {
                if (item != -1) {
                    if (item != currentSlot && interactionsTimer.hasElapsed(delaySetting.getValue().longValue())) {
                        click(item, currentSlot - InventoryUtil.HOTBAR, InventoryUtil.ClickType.SWAP_WITH_HOTBAR);
                    }
                    currentSlot++;
                }
            }
        }
        if (cleanSetting.getValue()) {
            for (int slot = InventoryUtil.INCLUDE_ARMOR; slot < InventoryUtil.END; slot++) {
                final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                if (stack == null || EnumChatFormatting.stripFormatting(stack.getDisplayName()).contains("(Right Click)")) continue;
                final float damage = InventoryUtil.getItemDamage(stack);
                if (interactionsTimer.hasElapsed(delaySetting.getValue().longValue())) {
                    if (stack.getItem() instanceof ItemSword && slot != bestSwordSlot ||
                            stack.getItem() instanceof ItemBow && slot != bestBowSlot ||
                            stack.getItem() instanceof ItemPickaxe && slot != bestPickaxeSlot ||
                            stack.getItem() instanceof ItemAxe && slot != bestAxeSlot ||
                            stack.getItem() instanceof ItemSpade && slot != bestShovelSlot ||
                            stack.getItem() instanceof ItemFood && slot != bestFoodSlot &&
                            !(stack.getItem() instanceof ItemAppleGold) ||
                            stack.getItem() instanceof ItemArmor &&
                                    slot != bestArmorSlots[((ItemArmor) stack.getItem()).armorType] ||
                            stack.getItem() == Items.arrow && !arrows.contains(stack) ||
                            stack.getItem() instanceof ItemBlock && !blocks.contains(stack) ||
                            stack.getItem() != Items.arrow && damage == 0
                    ) click(slot, 1, InventoryUtil.ClickType.DROP_ITEM);
                }
            }
        }
    };

    private void click(int slot, int button, InventoryUtil.ClickType mode) {
        open();
        InventoryUtil.windowClick(slot, button, mode);
    }

    private void open() {
        if (!spoofOpened && modeSetting.getValue() == InventoryMode.SPOOF) {
            if (!(mc.currentScreen instanceof GuiInventory))
                PacketUtil.sendPacketNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            interactionsTimer.reset();
            spoofOpened = true;
        }
    }

    private void close() {
        if (spoofOpened && modeSetting.getValue() == InventoryMode.SPOOF) {
            if (!(mc.currentScreen instanceof GuiInventory))
                PacketUtil.sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
            spoofOpened = false;
        }
    }

    public enum InventoryMode implements SerializableEnum {
        SPOOF("Spoof"), OPEN("Open"), NONE("None");

        final String name;

        InventoryMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum ItemElement {
        SWORD, BOW, PICKAXE, SHOVEL, GOLDEN_APPLE, HEAD, BUCKETS
    }

}
