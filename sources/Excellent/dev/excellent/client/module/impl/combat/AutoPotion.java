package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.other.PotionUtil;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

@ModuleInfo(name = "Auto Potion", description = "Автоматическое применение взрывных зелий.", category = Category.COMBAT)
public class AutoPotion extends Module {
    public static Singleton<AutoPotion> singleton = Singleton.create(() -> Module.link(AutoPotion.class));
    private final MultiBooleanValue potions = new MultiBooleanValue("Бафы", this)
            .add(
                    new BooleanValue("Сила", true),
                    new BooleanValue("Скорость", true),
                    new BooleanValue("Огнестойкость", true)
            );
    private final BooleanValue onlyPvp = new BooleanValue("Только в пвп", this, true);
    private final BooleanValue autoDisable = new BooleanValue("Авто выкл", this, true);
    private final TimerUtil timerUtil = TimerUtil.create();
    public boolean isActive;
    private int selectedSlot;
    private float previousPitch;
    public boolean isActivePotion;
    private final PotionUtil potionUtil = new PotionUtil();
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (this.isActive() && this.shouldUsePotion()) {
            for (PotionType potionType : PotionType.values()) {
                isActivePotion = potionType.isEnabled();
            }
        } else {
            isActivePotion = false;
        }

        if (this.isActive() && this.shouldUsePotion() && previousPitch == mc.player.lastReportedPitch) {
            int oldItem = mc.player.inventory.currentItem;
            this.selectedSlot = -1;

            for (PotionType potionType : PotionType.values()) {
                if (potionType.isEnabled()) {
                    int slot = this.findPotionSlot(potionType);
                    if (this.selectedSlot == -1) {
                        this.selectedSlot = slot;
                    }

                    this.isActive = true;
                }
            }

            if (this.selectedSlot > 8) {
                mc.playerController.pickItem(this.selectedSlot);
            }

            mc.player.connection.sendPacket(new CHeldItemChangePacket(oldItem));
        }

        if (timerUtil.hasReached(500L)) {
            try {
                this.reset();
                this.selectedSlot = -2;
            } catch (Exception ignored) {
            }
        }

        this.potionUtil.changeItemSlot(this.selectedSlot == -2);
        if (this.autoDisable.getValue() && this.isActive && this.selectedSlot == -2) {
            this.toggle();
            this.isActive = false;
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (!this.isActive() || !this.shouldUsePotion()) {
            return;
        }

        this.previousPitch = 90.0F;
        event.setPitch(this.previousPitch);
    };

    private boolean shouldUsePotion() {
        return !(onlyPvp.getValue() && !PlayerUtil.isPvp());
    }

    private void reset() {
        for (PotionType potionType : PotionType.values()) {
            if (potionType.getPotionSetting().get()) {
                potionType.setEnabled(this.isPotionActive(potionType));
            }
        }
    }

    private int findPotionSlot(PotionType type) {
        int hbSlot = this.getPotionIndexHb(type.getPotionId());
        if (hbSlot != -1) {
            this.potionUtil.setPreviousSlot(mc.player.inventory.currentItem);
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            PotionUtil.useItem(Hand.MAIN_HAND);
            type.setEnabled(false);
            timerUtil.reset();
            return hbSlot;
        } else {
            int invSlot = this.getPotionIndexInv(type.getPotionId());
            if (invSlot != -1) {
                this.potionUtil.setPreviousSlot(mc.player.inventory.currentItem);
                mc.playerController.pickItem(invSlot);
                PotionUtil.useItem(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                type.setEnabled(false);
                timerUtil.reset();
                return invSlot;
            } else {
                return -1;
            }
        }
    }

    public boolean isActive() {
        final boolean isNotThrowing = !(mc.player.isOnGround() || mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getBoundingBox().minY - 0.3f, mc.player.getPosZ())).isSolid()) || mc.player.isOnLadder() || mc.player.getRidingEntity() != null || mc.player.abilities.isFlying || mc.player.isInLiquid();
        for (PotionType potionType : PotionType.values()) {
            if (potionType.getPotionSetting().get() && potionType.isEnabled() && !isNotThrowing) {
                return true;
            }
        }
        return false;
    }

    private boolean isPotionActive(PotionType type) {
        if (mc.player.isPotionActive(type.getPotion())) {
            this.isActive = false;
            return false;
        } else {
            return this.getPotionIndexInv(type.getPotionId()) != -1 || this.getPotionIndexHb(type.getPotionId()) != -1;
        }
    }

    private int getPotionIndexHb(int id) {
        for (int i = 0; i < 9; ++i) {
            for (EffectInstance potion : PotionUtils.getEffectsFromStack(mc.player.inventory.getStackInSlot(i))) {
                if (potion.getPotion() == Effect.get(id) && mc.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
                    return i;
                }
            }
        }

        return -1;
    }

    private int getPotionIndexInv(int id) {
        for (int i = 9; i < 36; ++i) {
            for (EffectInstance potion : PotionUtils.getEffectsFromStack(mc.player.inventory.getStackInSlot(i))) {
                if (potion.getPotion() == Effect.get(id) && mc.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    protected void onDisable() {
        isActive = false;
        super.onDisable();
    }

    @Getter
    @RequiredArgsConstructor
    private enum PotionType {
        STRENGHT(Effects.STRENGTH, 5, () -> singleton.get().potions.isEnabled("Сила")),
        SPEED(Effects.SPEED, 1, () -> singleton.get().potions.isEnabled("Скорость")),
        FIRE_RESIST(Effects.STRENGTH, 12, () -> singleton.get().potions.isEnabled("Огнестойкость"));
        private final Effect potion;
        private final int potionId;
        private final Supplier<Boolean> potionSetting;
        @Setter
        private boolean enabled;
    }
}