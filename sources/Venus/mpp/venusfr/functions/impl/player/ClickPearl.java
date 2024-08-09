/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.player.ItemCooldown;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import mpp.venusfr.venusfr;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

@FunctionRegister(name="ClickPearl", type=Category.Player)
public class ClickPearl
extends Function {
    private final BindSetting throwKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430", -98);
    private final StopWatch stopWatch = new StopWatch();
    private final InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    private final ItemCooldown itemCooldown;
    private long delay;
    private boolean throwPearl;

    public ClickPearl(ItemCooldown itemCooldown) {
        this.itemCooldown = itemCooldown;
        this.addSettings(this.throwKey);
    }

    @Subscribe
    public void onKey(EventKey eventKey) {
        this.throwPearl = eventKey.getKey() == ((Integer)this.throwKey.get()).intValue();
    }

    @Subscribe
    private void onMotion(EventMotion eventMotion) {
        if (this.throwPearl) {
            if (!ClickPearl.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL)) {
                boolean bl = ClickPearl.mc.player.getHeldItemOffhand().getItem() instanceof EnderPearlItem;
                if (bl) {
                    ClickPearl.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    ClickPearl.mc.player.swingArm(Hand.MAIN_HAND);
                } else {
                    int n = this.findPearlAndThrow();
                    if (n > 8) {
                        ClickPearl.mc.playerController.pickItem(n);
                    }
                }
            }
            this.throwPearl = false;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        this.handUtil.onEventPacket(eventPacket);
    }

    private int findPearlAndThrow() {
        int n = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ENDER_PEARL, false);
        if (n != -1) {
            this.handUtil.setOriginalSlot(ClickPearl.mc.player.inventory.currentItem);
            if (n != ClickPearl.mc.player.inventory.currentItem) {
                ClickPearl.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            }
            ClickPearl.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            ClickPearl.mc.player.swingArm(Hand.MAIN_HAND);
            FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
            ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
            ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(Items.ENDER_PEARL);
            if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
                itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
            }
            if (n != ClickPearl.mc.player.inventory.currentItem) {
                ClickPearl.mc.player.connection.sendPacket(new CHeldItemChangePacket(ClickPearl.mc.player.inventory.currentItem));
            }
            this.delay = System.currentTimeMillis();
            return n;
        }
        int n2 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ENDER_PEARL, true);
        if (n2 != -1) {
            this.handUtil.setOriginalSlot(ClickPearl.mc.player.inventory.currentItem);
            ClickPearl.mc.playerController.pickItem(n2);
            ClickPearl.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            ClickPearl.mc.player.swingArm(Hand.MAIN_HAND);
            FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
            ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
            ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(Items.ENDER_PEARL);
            if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
                itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
            }
            this.delay = System.currentTimeMillis();
            return n2;
        }
        return 1;
    }

    @Override
    public void onDisable() {
        this.throwPearl = false;
        this.delay = 0L;
        super.onDisable();
    }
}

