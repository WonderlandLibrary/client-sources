/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;

@FunctionRegister(name="AutoGapple", type=Category.Combat)
public class AutoGapple
extends Function {
    private final SliderSetting healthSetting = new SliderSetting("\u0417\u0434\u043e\u0440\u043e\u0432\u044c\u0435", 16.0f, 1.0f, 20.0f, 0.05f);
    private final BooleanSetting eatAtTheStart = new BooleanSetting("\u0421\u044a\u0435\u0441\u0442\u044c \u0432 \u043d\u0430\u0447\u0430\u043b\u0435", true);
    private boolean isEating;
    private final StopWatch stopWatch = new StopWatch();

    public AutoGapple() {
        this.addSettings(this.healthSetting, this.eatAtTheStart);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (this.shouldToTakeGApple() && ((Boolean)this.eatAtTheStart.get()).booleanValue()) {
            this.takeGappleInOffHand();
        }
        this.eatGapple();
    }

    private void eatGapple() {
        if (this.conditionToEat()) {
            this.startEating();
        } else if (this.isEating) {
            this.stopEating();
        }
    }

    private boolean shouldToTakeGApple() {
        boolean bl = AutoGapple.mc.player.ticksExisted == 15;
        boolean bl2 = AutoGapple.mc.player.getAbsorptionAmount() == 0.0f || !AutoGapple.mc.player.isPotionActive(Effects.REGENERATION);
        boolean bl3 = AutoGapple.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE;
        boolean bl4 = this.stopWatch.isReached(200L);
        boolean bl5 = (Boolean)this.eatAtTheStart.get();
        return bl && bl2 && bl3 & bl4 && bl5;
    }

    private void takeGappleInOffHand() {
        int n = InventoryUtil.getInstance().getSlotInInventory(Items.GOLDEN_APPLE);
        if (n >= 0) {
            this.moveGappleToOffhand(n);
        }
    }

    private void moveGappleToOffhand(int n) {
        if (n < 9 && n != -1) {
            n += 36;
        }
        AutoGapple.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, AutoGapple.mc.player);
        AutoGapple.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, AutoGapple.mc.player);
        if (!(AutoGapple.mc.player.getHeldItemOffhand().getItem() instanceof AirItem)) {
            AutoGapple.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, AutoGapple.mc.player);
        }
        this.stopWatch.reset();
    }

    private void startEating() {
        if (AutoGapple.mc.currentScreen != null) {
            AutoGapple.mc.currentScreen.passEvents = true;
        }
        if (!AutoGapple.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            AutoGapple.mc.gameSettings.keyBindUseItem.setPressed(false);
            this.isEating = true;
        }
    }

    private void stopEating() {
        AutoGapple.mc.gameSettings.keyBindUseItem.setPressed(true);
        this.isEating = false;
    }

    private boolean conditionToEat() {
        float f = AutoGapple.mc.player.getHealth() + AutoGapple.mc.player.getAbsorptionAmount();
        boolean bl = AutoGapple.mc.player.getAbsorptionAmount() == 0.0f || !AutoGapple.mc.player.isPotionActive(Effects.REGENERATION);
        return (this.isHealthLow(f) || AutoGapple.mc.player.ticksExisted < 100 && bl) && this.hasGappleInHand() && !this.isGappleOnCooldown();
    }

    private boolean isGappleOnCooldown() {
        return AutoGapple.mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
    }

    private boolean isHealthLow(float f) {
        return f <= ((Float)this.healthSetting.get()).floatValue();
    }

    private boolean hasGappleInHand() {
        return AutoGapple.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE || AutoGapple.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE;
    }

    private void reset() {
        this.stopWatch.reset();
    }

    @Override
    public void onDisable() {
        this.reset();
        super.onDisable();
    }
}

