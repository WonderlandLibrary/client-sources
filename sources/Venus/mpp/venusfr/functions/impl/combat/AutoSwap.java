/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventCooldown;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.AutoTotem;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.potion.Effects;

@FunctionRegister(name="AutoSwap", type=Category.Combat)
public class AutoSwap
extends Function {
    private final ModeSetting swapMode = new ModeSetting("\u0422\u0438\u043f", "\u0423\u043c\u043d\u044b\u0439", "\u0423\u043c\u043d\u044b\u0439", "\u041f\u043e \u0431\u0438\u043d\u0434\u0443");
    private final ModeSetting itemType = new ModeSetting("\u041f\u0440\u0435\u0434\u043c\u0435\u0442", "\u0429\u0438\u0442", "\u0429\u0438\u0442", "\u0413\u0435\u043f\u043b\u044b", "\u0422\u043e\u0442\u0435\u043c", "\u0428\u0430\u0440");
    private final ModeSetting swapType = new ModeSetting("\u0421\u0432\u0430\u043f\u0430\u0442\u044c \u043d\u0430", "\u0413\u0435\u043f\u043b\u044b", "\u0429\u0438\u0442", "\u0413\u0435\u043f\u043b\u044b", "\u0422\u043e\u0442\u0435\u043c", "\u0428\u0430\u0440");
    private final BindSetting keyToSwap = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430", -1).setVisible(this::lambda$new$0);
    private final SliderSetting health = new SliderSetting("\u0417\u0434\u043e\u0440\u043e\u0432\u044c\u0435", 11.0f, 5.0f, 19.0f, 0.5f).setVisible(this::lambda$new$1);
    private final StopWatch stopWatch = new StopWatch();
    private boolean shieldIsCooldown;
    private int oldItem = -1;
    private final StopWatch delay = new StopWatch();
    private final AutoTotem autoTotem;

    public AutoSwap(AutoTotem autoTotem) {
        this.autoTotem = autoTotem;
        this.addSettings(this.swapMode, this.itemType, this.swapType, this.keyToSwap, this.health);
    }

    @Subscribe
    public void onEventKey(EventKey eventKey) {
        boolean bl;
        if (!this.swapMode.is("\u041f\u043e \u0431\u0438\u043d\u0434\u0443")) {
            return;
        }
        ItemStack itemStack = AutoSwap.mc.player.getHeldItemOffhand();
        boolean bl2 = bl = !(itemStack.getItem() instanceof AirItem);
        if (eventKey.isKeyDown((Integer)this.keyToSwap.get()) && this.stopWatch.isReached(200L)) {
            Item item = itemStack.getItem();
            boolean bl3 = item == this.getSwapItem();
            boolean bl4 = item == this.getSelectedItem();
            int n = this.getSlot(this.getSelectedItem());
            int n2 = this.getSlot(this.getSwapItem());
            if (n >= 0 && !bl4) {
                InventoryUtil.moveItem(n, 45, bl);
                this.stopWatch.reset();
                return;
            }
            if (n2 >= 0 && !bl3) {
                InventoryUtil.moveItem(n2, 45, bl);
                this.stopWatch.reset();
            }
        }
    }

    @Subscribe
    private void onCooldown(EventCooldown eventCooldown) {
        this.shieldIsCooldown = this.isCooldown(eventCooldown);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (!this.swapMode.is("\u0423\u043c\u043d\u044b\u0439")) {
            return;
        }
        Item item = AutoSwap.mc.player.getHeldItemOffhand().getItem();
        if (this.stopWatch.isReached(400L)) {
            this.swapIfShieldIsBroken(item);
            this.swapIfHealthToLow(item);
            this.stopWatch.reset();
        }
        boolean bl = false;
        if (item == Items.GOLDEN_APPLE && !AutoSwap.mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE)) {
            bl = AutoSwap.mc.gameSettings.keyBindUseItem.isKeyDown();
        }
        if (bl) {
            this.stopWatch.reset();
        }
    }

    @Override
    public void onDisable() {
        this.shieldIsCooldown = false;
        this.oldItem = -1;
        super.onDisable();
    }

    private void swapIfHealthToLow(Item item) {
        boolean bl = !(item instanceof AirItem);
        boolean bl2 = item == this.getSwapItem();
        boolean bl3 = item == this.getSelectedItem();
        boolean bl4 = !AutoSwap.mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
        int n = this.getSlot(this.getSwapItem());
        if (this.shieldIsCooldown || !bl4) {
            return;
        }
        if (this.isLowHealth() && !bl2 && bl3) {
            InventoryUtil.moveItem(n, 45, bl);
            if (bl && this.oldItem == -1) {
                this.oldItem = n;
            }
        } else if (!this.isLowHealth() && bl2 && this.oldItem >= 0) {
            InventoryUtil.moveItem(this.oldItem, 45, bl);
            this.oldItem = -1;
        }
    }

    private void swapIfShieldIsBroken(Item item) {
        boolean bl = !(item instanceof AirItem);
        boolean bl2 = item == this.getSwapItem();
        boolean bl3 = item == this.getSelectedItem();
        boolean bl4 = !AutoSwap.mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
        int n = this.getSlot(this.getSwapItem());
        if (this.shieldIsCooldown && !bl2 && bl3 && bl4) {
            InventoryUtil.moveItem(n, 45, bl);
            if (bl && this.oldItem == -1) {
                this.oldItem = n;
            }
            this.print("" + this.shieldIsCooldown);
        } else if (!this.shieldIsCooldown && bl2 && this.oldItem >= 0) {
            InventoryUtil.moveItem(this.oldItem, 45, bl);
            this.oldItem = -1;
        }
    }

    private boolean isLowHealth() {
        float f = AutoSwap.mc.player.getHealth() + (AutoSwap.mc.player.isPotionActive(Effects.ABSORPTION) ? AutoSwap.mc.player.getAbsorptionAmount() : 0.0f);
        return f <= ((Float)this.health.get()).floatValue();
    }

    private boolean isCooldown(EventCooldown eventCooldown) {
        Item item = eventCooldown.getItem();
        if (!this.itemType.is("Shield")) {
            return true;
        }
        return eventCooldown.isAdded() && item instanceof ShieldItem;
    }

    private Item getSwapItem() {
        return this.getItemByType((String)this.swapType.get());
    }

    private Item getSelectedItem() {
        return this.getItemByType((String)this.itemType.get());
    }

    private Item getItemByType(String string) {
        return switch (string) {
            case "\u0429\u0438\u0442" -> Items.SHIELD;
            case "\u0422\u043e\u0442\u0435\u043c" -> Items.TOTEM_OF_UNDYING;
            case "\u0413\u0435\u043f\u043b\u044b" -> Items.GOLDEN_APPLE;
            case "\u0428\u0430\u0440" -> Items.PLAYER_HEAD;
            default -> Items.AIR;
        };
    }

    private int getSlot(Item item) {
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            if (AutoSwap.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            if (AutoSwap.mc.player.inventory.getStackInSlot(i).isEnchanted()) {
                n = i;
                break;
            }
            n = i;
        }
        if (n < 9 && n != -1) {
            n += 36;
        }
        return n;
    }

    private Boolean lambda$new$1() {
        return this.swapMode.is("\u0423\u043c\u043d\u044b\u0439");
    }

    private Boolean lambda$new$0() {
        return this.swapMode.is("\u041f\u043e \u0431\u0438\u043d\u0434\u0443");
    }
}

