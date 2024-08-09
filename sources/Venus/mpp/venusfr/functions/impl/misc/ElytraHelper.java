/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name="ElytraHelper", type=Category.Misc)
public class ElytraHelper
extends Function {
    private final BindSetting swapChestKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0441\u0432\u0430\u043f\u0430", -1);
    private final BindSetting fireWorkKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0444\u0435\u0435\u0440\u0432\u0435\u0440\u043a\u043e\u0432", -1);
    private final BooleanSetting autoFly = new BooleanSetting("\u0410\u0432\u0442\u043e \u0432\u0437\u043b\u0451\u0442", true);
    private final InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    private ItemStack currentStack = ItemStack.EMPTY;
    public static StopWatch stopWatch = new StopWatch();
    private long delay;
    private boolean fireworkUsed;

    public ElytraHelper() {
        this.addSettings(this.swapChestKey, this.fireWorkKey, this.autoFly);
    }

    @Subscribe
    private void onEventKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.swapChestKey.get()).intValue() && stopWatch.isReached(100L)) {
            this.changeChestPlate(this.currentStack);
            stopWatch.reset();
        }
        if (eventKey.getKey() == ((Integer)this.fireWorkKey.get()).intValue() && stopWatch.isReached(200L)) {
            this.fireworkUsed = true;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        this.currentStack = ElytraHelper.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (((Boolean)this.autoFly.get()).booleanValue() && this.currentStack.getItem() == Items.ELYTRA) {
            if (ElytraHelper.mc.player.isOnGround()) {
                ElytraHelper.mc.player.jump();
            } else if (ElytraItem.isUsable(this.currentStack) && !ElytraHelper.mc.player.isElytraFlying()) {
                ElytraHelper.mc.player.startFallFlying();
                ElytraHelper.mc.player.connection.sendPacket(new CEntityActionPacket(ElytraHelper.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            }
        }
        if (this.fireworkUsed) {
            int n;
            int n2 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, false);
            int n3 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, true);
            if (n3 == -1 && n2 == -1) {
                this.print("\u0424\u0435\u0435\u0440\u0432\u0435\u0440\u043a\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b!");
                this.fireworkUsed = false;
                return;
            }
            if (!ElytraHelper.mc.player.getCooldownTracker().hasCooldown(Items.FIREWORK_ROCKET) && (n = this.findAndTrowItem(n2, n3)) > 8) {
                ElytraHelper.mc.playerController.pickItem(n);
            }
            this.fireworkUsed = false;
        }
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        this.handUtil.onEventPacket(eventPacket);
    }

    private int findAndTrowItem(int n, int n2) {
        if (n != -1) {
            this.handUtil.setOriginalSlot(ElytraHelper.mc.player.inventory.currentItem);
            if (n != ElytraHelper.mc.player.inventory.currentItem) {
                ElytraHelper.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            }
            ElytraHelper.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            ElytraHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            if (n != ElytraHelper.mc.player.inventory.currentItem) {
                ElytraHelper.mc.player.connection.sendPacket(new CHeldItemChangePacket(ElytraHelper.mc.player.inventory.currentItem));
            }
            ElytraHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n;
        }
        if (n2 != -1) {
            this.handUtil.setOriginalSlot(ElytraHelper.mc.player.inventory.currentItem);
            ElytraHelper.mc.playerController.pickItem(n2);
            ElytraHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            ElytraHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n2;
        }
        return 1;
    }

    private void changeChestPlate(ItemStack itemStack) {
        int n;
        if (ElytraHelper.mc.currentScreen != null) {
            return;
        }
        if (itemStack.getItem() != Items.ELYTRA) {
            n = this.getItemSlot(Items.ELYTRA);
            if (n >= 0) {
                InventoryUtil.moveItem(n, 6);
                this.print(TextFormatting.RED + "\u0421\u0432\u0430\u043f\u043d\u0443\u043b \u043d\u0430 \u044d\u043b\u0438\u0442\u0440\u0443!");
                return;
            }
            this.print("\u042d\u043b\u0438\u0442\u0440\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
        }
        if ((n = this.getChestPlateSlot()) >= 0) {
            InventoryUtil.moveItem(n, 6);
            this.print(TextFormatting.RED + "\u0421\u0432\u0430\u043f\u043d\u0443\u043b \u043d\u0430 \u043d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a!");
        } else {
            this.print("\u041d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
        }
    }

    private int getChestPlateSlot() {
        Item[] itemArray;
        for (Item item : itemArray = new Item[]{Items.NETHERITE_CHESTPLATE, Items.DIAMOND_CHESTPLATE}) {
            for (int i = 0; i < 36; ++i) {
                Item item2 = ElytraHelper.mc.player.inventory.getStackInSlot(i).getItem();
                if (item2 != item) continue;
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return 1;
    }

    @Override
    public void onDisable() {
        stopWatch.reset();
        super.onDisable();
    }

    private int getItemSlot(Item item) {
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = ElytraHelper.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != item) continue;
            n = i;
            break;
        }
        if (n < 9 && n != -1) {
            n += 36;
        }
        return n;
    }
}

