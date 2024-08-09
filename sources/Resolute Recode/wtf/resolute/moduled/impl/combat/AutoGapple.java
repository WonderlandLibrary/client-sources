package wtf.resolute.moduled.impl.combat;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.player.InventoryUtil;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "AutoGapple", type = Categories.Combat,server = "")
public class AutoGapple extends Module {
    private final SliderSetting healthSetting = new SliderSetting("Çהמנמגüו", 16.0f, 1.0f, 20.0f, 0.05f);
    private final BooleanSetting eatAtTheStart = new BooleanSetting("Ñתוסעü ג םאקאכו", true);
    private boolean isEating;
    private final StopWatch stopWatch = new StopWatch();

    public AutoGapple() {
        addSettings(healthSetting, eatAtTheStart);
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (shouldToTakeGApple() && eatAtTheStart.get()) {
            takeGappleInOffHand();
        }
        eatGapple();
    }


    private void eatGapple() {
        if (conditionToEat()) {
            startEating();
        } else if (isEating) {
            stopEating();
        }
    }

    private boolean shouldToTakeGApple() {
        boolean isTicksExisted = mc.player.ticksExisted == 15;
        boolean appleNotEaten = mc.player.getAbsorptionAmount() == 0.0f || !mc.player.isPotionActive(Effects.REGENERATION);
        boolean appleIsNotOffHand = mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE;
        boolean timeHasPassed = stopWatch.isReached(200);
        boolean settingIsEnalbed = eatAtTheStart.get();


        return (isTicksExisted && appleNotEaten && appleIsNotOffHand & timeHasPassed) && settingIsEnalbed;
    }

    private void takeGappleInOffHand() {
        int gappleSlot = InventoryUtil.getInstance().getSlotInInventory(Items.GOLDEN_APPLE);

        if (gappleSlot >= 0) {
            moveGappleToOffhand(gappleSlot);
        }
    }

    private void moveGappleToOffhand(int gappleSlot) {
        if (gappleSlot < 9 && gappleSlot != -1) {
            gappleSlot += 36;
        }
        mc.playerController.windowClick(0, gappleSlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
        if (!(mc.player.getHeldItemOffhand().getItem() instanceof AirItem)) {
            mc.playerController.windowClick(0, gappleSlot, 0, ClickType.PICKUP, mc.player);
        }
        stopWatch.reset();
    }

    private void startEating() {
        if (mc.currentScreen != null) {
            mc.currentScreen.passEvents = true;
        }
        if (!mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.gameSettings.keyBindUseItem.setPressed(true);
            isEating = true;
        }
    }

    private void stopEating() {
        mc.gameSettings.keyBindUseItem.setPressed(false);
        isEating = false;
    }

    private boolean conditionToEat() {
        float myHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        boolean appleNotEaten = mc.player.getAbsorptionAmount() == 0.0f
                || !mc.player.isPotionActive(Effects.REGENERATION);

        return (isHealthLow(myHealth) || mc.player.ticksExisted < 100 && appleNotEaten)
                && hasGappleInHand()
                && !isGappleOnCooldown();
    }

    private boolean isGappleOnCooldown() {
        return mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE);
    }

    private boolean isHealthLow(float health) {
        return health <= healthSetting.get();
    }

    private boolean hasGappleInHand() {
        return mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE ||
                mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE;
    }

    private void reset() {
        this.stopWatch.reset();
    }

    @Override
    public void onDisable() {
        reset();
        super.onDisable();
    }
}