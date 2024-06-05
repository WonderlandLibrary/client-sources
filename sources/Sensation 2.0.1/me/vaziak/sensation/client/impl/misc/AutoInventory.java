package me.vaziak.sensation.client.impl.misc;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.ItemsProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.misc.cheststealer.ChestStealer;
import me.vaziak.sensation.utils.anthony.Action;
import me.vaziak.sensation.utils.anthony.ContainerUtil;
import me.vaziak.sensation.utils.anthony.ItemUtil;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;

/**
 * @author antja03
 */
public class AutoInventory extends Module {
    private StringsProperty forceSlow = new StringsProperty("Force Slowdown", "Force the player to slowdown.", true, false, new String[]{"Sprint cancel", "Stop movement", "Move delay"});

    private BooleanProperty spoof = new BooleanProperty("Spoof Inventory Open", "Spoof opening the inventory", null,
            true);
    private BooleanProperty onlyInvProperty = new BooleanProperty("Only In Inventory", "Only clean your inventory while it is opened.", () -> !spoof.getValue(),
            false);

    private BooleanProperty openInvProperty = new BooleanProperty("Open Inventory", "Opens your inventory automatically when it needs to be cleaned.", () -> onlyInvProperty.getValue(),
            false);

    private StringsProperty automationsProperty = new StringsProperty("Automated Tasks", "What tasks will be automated by the cheat.", true, false, new String[]{"Clean", "Equip Armor"});

    private ItemsProperty whitelistedItemsProperty = new ItemsProperty("Whitelisted Items", "Items that can't be removed during cleaning.", () -> automationsProperty.getValue().get("Clean"),
            false, new ArrayList<>());

    private StringsProperty whitelistedPropertiesProperty = new StringsProperty("Whitelisted Properties", "Item properties that will prevent an item from being dropped.", () -> automationsProperty.getValue().get("Clean"),
            true, false, new String[]{"Custom Displayname", "Best Tool", "Best Sword", "Best Armor", "Food", "Blocks"});

    private DoubleProperty dropDelayProperty = new DoubleProperty("Cleaning Delay", "Delay before each item is dropped. (MS)", () -> automationsProperty.getValue().get("Clean"),
            150, 0, 250, 10, "");

    private DoubleProperty equipDelayProperty = new DoubleProperty("Equip Delay", "Delay before each item is equipped. (MS)", () -> automationsProperty.getValue().get("Equip Armor"),
            150, 0, 250, 10, "");
 

    private TimerUtil dropStopwatch = new TimerUtil();
    private TimerUtil equipStopwatch = new TimerUtil();
    public boolean cleaning;
    public boolean equipping; 
    private boolean guiOpenedByMod;
    private final ArrayList<Action> clickQueue;
    private boolean openedinv = false;;
    public ArrayList<Item> whitelistedItem = new ArrayList();
    
    public AutoInventory() {
        super("Auto Inventory", Category.MISC);
        registerValue(spoof, forceSlow, onlyInvProperty, openInvProperty, automationsProperty, whitelistedItemsProperty, whitelistedPropertiesProperty, forceSlow, dropDelayProperty, equipDelayProperty);
        clickQueue = new ArrayList<>();
    	openedinv = false;
    }

    @Override
    public void onEnable() {
    	openedinv = false;
        cleaning = false;
        equipping = false;
        guiOpenedByMod = false;
        clickQueue.clear();
    }

    @Collect
    public void onSendPacket(SendPacketEvent event) {
    	if (event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation) {
    		if (openedinv) {
    	//		event.setCancelled(true);
    		}
    	}
        if (event.getPacket() instanceof C0DPacketCloseWindow) {
            openedinv = false;
        }
        if (event.getPacket() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction packet = (C0BPacketEntityAction)event.getPacket();
            if (packet.getAction() == packet.getAction().OPEN_INVENTORY) {
                openedinv = true;
            }
        }
    }
    
    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (!playerUpdateEvent.isPre() || mc.thePlayer.isUsingItem())//Made it on pre to be better, derp
            return;

            ChestStealer cs = (ChestStealer) Sensation.instance.cheatManager.getCheatRegistry().get("Chest Stealer");
            if (cs.getState() && cs.stealing)
                return;

            if (onlyInvProperty.getValue()
                    && !openInvProperty.getValue()
                    && !(mc.currentScreen instanceof GuiInventory)
                    && !(mc.currentScreen instanceof GuiContainerCreative))
                return;

            if (!clickQueue.isEmpty()) {
                clickQueue.get(0).execute();
                clickQueue.remove(clickQueue.get(0));
            } else {
            	if (!equipArmor()) {
            		equipping = false;
            		if (!clean()) {
            			cleaning = false;
            		}
                    
                }
            }

            if (guiOpenedByMod && !cleaning && !equipping) {
                mc.displayGuiScreen(null);
                guiOpenedByMod = false;
                for (KeyBinding keyBinding : new KeyBinding[]{
                        mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
                        mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight,
                        mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint}) {
                    KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
                }
            }
        
    }

    private boolean equipArmor() {
        if (automationsProperty.getValue().get("Equip Armor")) {

            for (int i = 9; i < 45; i++) {
                if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                    continue;

                ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (!(stackInSlot.getItem() instanceof ItemArmor))
                    continue;

                if (ContainerUtil.getArmorItemsEquipSlot(stackInSlot, false) == -1)
                    continue;

                if (mc.thePlayer.getEquipmentInSlot(ContainerUtil.getArmorItemsEquipSlot(stackInSlot, true)) == null) {
                    if (equipStopwatch.hasPassed(equipDelayProperty.getValue().intValue())) {
                        int finalI = i;

                        stopMoving();
                        if (!openedinv && spoof.getValue()) {
            				mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                        }

                        clickQueue.add(() -> mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, finalI, 0, 0, mc.thePlayer));
                        clickQueue.add(() -> mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, ContainerUtil.getArmorItemsEquipSlot(stackInSlot, false), 0, 0, mc.thePlayer));
                        
                        if (openedinv && !(mc.currentScreen instanceof GuiInventory) && spoof.getValue()) {
                        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0DPacketCloseWindow(0));
                        }

                        stopMoving();
                        return true;
                    }
                } else {
                    ItemStack stackInEquipmentSlot = mc.thePlayer.getEquipmentInSlot(ContainerUtil.getArmorItemsEquipSlot(stackInSlot, true));
                    if (ItemUtil.compareProtection(stackInSlot, stackInEquipmentSlot) == stackInSlot) {
                        if (equipStopwatch.hasPassed(equipDelayProperty.getValue().intValue())) {
                            int finalI1 = i;
                            stopMoving();
                            if (!openedinv && spoof.getValue()) {
                				mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                            	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                            }

                            clickQueue.add(() -> mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, finalI1, 0, 0, mc.thePlayer));
                            clickQueue.add(() -> mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, ContainerUtil.getArmorItemsEquipSlot(stackInSlot, false), 0, 0, mc.thePlayer));
                            clickQueue.add(() -> mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, finalI1, 0, 0, mc.thePlayer));
                            if (openedinv && !(mc.currentScreen instanceof GuiInventory) && spoof.getValue()) {
                            	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0DPacketCloseWindow(0));
                            }

                            stopMoving();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    
    
    public boolean clean() {
        if (automationsProperty.getValue().get("Clean")) {
            if (mc.thePlayer == null)
                return false;

            ArrayList<Integer> uselessItem = new ArrayList<Integer>();
            for (int i = 0; i < 45; i++) {

                if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                    continue;

                ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (mc.thePlayer.inventory.armorItemInSlot(0) == stackInSlot
                        || mc.thePlayer.inventory.armorItemInSlot(1) == stackInSlot
                        || mc.thePlayer.inventory.armorItemInSlot(2) == stackInSlot
                        || mc.thePlayer.inventory.armorItemInSlot(3) == stackInSlot)
                    continue;

                if (isGarbo(i))
                    uselessItem.add(i);

            }

            if (uselessItem.size() > 0) {
                cleaning = true;
                if (onlyInvProperty.getValue() && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiContainerCreative)) {
                    if (openInvProperty.getValue()) {
                        if (mc.currentScreen != null)
                            return false;

                        mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
                        dropStopwatch.reset();
                        if (!guiOpenedByMod) guiOpenedByMod = true;
                    }
                }
                if (dropStopwatch.hasPassed(dropDelayProperty.getValue().intValue())) {
                    if (!(mc.thePlayer.inventory.currentItem == uselessItem.get(0))) {

                        stopMoving();
                    	if (!openedinv && spoof.getValue()) {
            				mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                    	}
                    	
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, uselessItem.get(0), 1, 4, mc.thePlayer);

                        if (openedinv && !(mc.currentScreen instanceof GuiInventory) && spoof.getValue()) {
                        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0DPacketCloseWindow(0));
                        }
                        stopMoving();
                    }
                    uselessItem.remove(0);
                    dropStopwatch.reset();
                }
                return true;
            }
        }
        return false;
    }

    private boolean isGarbo(int slot) {
        ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();

        if ((whitelistedPropertiesProperty.getValue().get("Custom Displayname") && stackInSlot.getDisplayName().contains("(Right Click)")))
        	return false;

        if ((whitelistedPropertiesProperty.getValue().get("Custom Displayname") && stackInSlot.getDisplayName().contains("§"))
        	&& !stackInSlot.getDisplayName().contains("§f"))
        	return false;

        if ((whitelistedPropertiesProperty.getValue().get("Custom Displayname") && stackInSlot.getDisplayName().contains("§"))
        	&& !stackInSlot.getDisplayName().contains("§f"))
        	return false;
        
        if (whitelistedPropertiesProperty.getValue().get("Custom Displayname") && stackInSlot.hasDisplayName())
            return false;

        if (whitelistedPropertiesProperty.getValue().get("Best Sword")) {
            if (stackInSlot.getItem() instanceof ItemSword) {
                for (int i = 0; i < 44; i++) {
                    if (i == slot) continue;
                    if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack stackAtIndex = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (stackAtIndex.getItem() instanceof ItemSword) {
                            if (ItemUtil.compareDamage(stackInSlot, stackAtIndex) == stackAtIndex) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }

        if (whitelistedPropertiesProperty.getValue().get("Best Tool")) {
            if (stackInSlot.getItem() instanceof ItemAxe || stackInSlot.getItem() instanceof ItemBow || stackInSlot.getItem() instanceof ItemFishingRod || stackInSlot.getItem() instanceof ItemPickaxe || Item.getIdFromItem(stackInSlot.getItem()) == 346) {
                for (int i = 44; i > 0; i--) {
                    if (i == slot) continue;
                    if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack stackAtIndex = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if ((stackAtIndex.getItem() instanceof ItemSword || stackAtIndex.getItem() instanceof ItemAxe || stackAtIndex.getItem() instanceof ItemBow || stackAtIndex.getItem() instanceof ItemFishingRod || stackAtIndex.getItem() instanceof ItemAxe || stackAtIndex.getItem() instanceof ItemPickaxe || Item.getIdFromItem(stackAtIndex.getItem()) == 346)) {
                            if (Item.getIdFromItem(stackAtIndex.getItem()) == Item.getIdFromItem(stackInSlot.getItem())) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }

        if (whitelistedPropertiesProperty.getValue().get("Best Armor")) {
            if (isBestArmorItem(stackInSlot))
                return false;
        }

        if (whitelistedPropertiesProperty.getValue().get("Food")) {
            if (stackInSlot.getItem() instanceof ItemFood)
                return false;
        }

        if (whitelistedPropertiesProperty.getValue().get("Blocks")) {
            if (stackInSlot.getItem() instanceof ItemBlock)
                return false;
        }

        if (whitelistedItemsProperty.getValue().contains(stackInSlot.getItem()))
            return false;

        return true;
    }

    private boolean isBestTool(ItemStack itemStack) {
        return false;
    }

    private boolean isBestArmorItem(ItemStack armorStack) {
        if (armorStack.getItem() instanceof ItemArmor) {
            int equipSlot = ContainerUtil.getArmorItemsEquipSlot(armorStack, true);

            if (equipSlot == -1)
                return false;

            if (mc.thePlayer.getEquipmentInSlot(equipSlot) == null) {
                for (int slotNum = 44; slotNum > 0; slotNum--) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(slotNum).getHasStack())
                        continue;

                    ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(slotNum).getStack();

                    if (!(stackInSlot.getItem() instanceof ItemArmor))
                        continue;

                    if (ContainerUtil.getArmorItemsEquipSlot(stackInSlot, true) == equipSlot
                            && compareArmorItems(armorStack, stackInSlot) == stackInSlot)
                        return false;
                }
            } else {
                if (compareArmorItems(armorStack, mc.thePlayer.getEquipmentInSlot(equipSlot)) == mc.thePlayer.getEquipmentInSlot(equipSlot))
                    return false;
            }

            return true;
        }
        return false;
    }

    private ItemStack compareArmorItems(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null)
            return null;

        if (!(item1.getItem() instanceof ItemArmor && item2.getItem() instanceof ItemArmor))
            return null;

        if (ContainerUtil.getArmorItemsEquipSlot(item1, true) != ContainerUtil.getArmorItemsEquipSlot(item2, true))
            return null;

        double item1Protection = ItemUtil.getArmorProtection(item1);
        double item2Protection = ItemUtil.getArmorProtection(item2);

        if (item1Protection != item2Protection) {
            if (item1Protection > item2Protection)
                return item1;
            else
                return item2;
        } else {
            if (item1.getMaxDamage() > item2.getMaxDamage())
                return item2;
            else
                return item1;
        }
    }
    
    public void stopMoving() {
        if (forceSlow.getValue().get("Sprint cancel")) {
        	mc.thePlayer.setSprinting(false);
        }
        if (forceSlow.getValue().get("Stop movement")) {
        	mc.thePlayer.setSpeed(0);
        }
    }
}
