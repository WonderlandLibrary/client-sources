package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventMotion;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import fun.ellant.utils.player.MoveUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Comparator;
import java.util.List;

@FunctionRegister(name = "AutoEXP", type = Category.COMBAT, desc = "Автоматически кидает опыт")
public class AutoEXP extends Function {
    float previousPitch;
    final StopWatch stopWatch = new StopWatch();

    public AutoEXP() {
    }

    @Subscribe
    public void onMotion(EventMotion e) {
        if (!canThrowPotion()) {
            return;
        }
        if (isActive()) {
            Vector3d posPoint = findNearestCollision();
            Vector2f rotationVector = posPoint == null
                    ? new Vector2f(mc.player.rotationYaw, 90.0F)
                    : MathUtil.rotationToVec(posPoint);

            previousPitch = rotationVector.y;
            e.setYaw(rotationVector.x);
            e.setPitch(previousPitch);
            mc.player.rotationPitchHead = previousPitch;
        }
        e.setPostMotion(() -> {
            boolean pitchIsValid = previousPitch == e.getPitch();
            int oldCurrentItem = mc.player.inventory.currentItem;

            for (AutoEXP.Potions potion : AutoEXP.Potions.values()) {
                potion.state = true;

                if (shouldUsePotion(potion)) {
                    continue;
                }
                if (potion.state && pitchIsValid) {
                    sendPotion(potion);
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(oldCurrentItem));
                    mc.playerController.syncCurrentPlayItem();
                }
            }
        });
    }


    public boolean isActive() {
        boolean timeIsReached = stopWatch.isReached(400);
        for (AutoEXP.Potions potionType : AutoEXP.Potions.values()) {
            if (shouldUsePotion(potionType)) {
                continue;
            }
            if (potionType.isState()) {
                return timeIsReached;
            }
        }
        return false;
    }


    private boolean canThrowPotion() {
        boolean isOnGround = !MoveUtils.isBlockUnder(0.5F) || mc.player.isOnGround();
        boolean timeIsReached = stopWatch.isReached(800);
        boolean ticksExisted = mc.player.ticksExisted > 100;

        return isOnGround && timeIsReached && ticksExisted;
    }
    private boolean shouldUsePotion(AutoEXP.Potions potions) {
        if (mc.player.isPotionActive(potions.getPotion())) {
            potions.state = false;
            return true;
        }
        int potionId = potions.getId();

        if (findPotionSlot(potionId, true) == -1 && findPotionSlot(potionId, false) == -1) {
            potions.state = false;
            return true;
        }

        // Проверяем, если броня имеет зачарование на починку и прочность меньше 50, то возвращаем true для кидания зельев опыта
        for (ItemStack armorStack : mc.player.getArmorInventoryList()) {
            if (armorStack.isEmpty() || !(armorStack.getItem() instanceof ArmorItem)) {
                continue;
            }
            ArmorItem armorItem = (ArmorItem) armorStack.getItem();
            int maxDamage = armorItem.getMaxDamage();
            int damageLeft = maxDamage - armorStack.getDamage();

            // Если броня имеет зачарование на починку и прочность меньше 50, возвращаем true для кидания зельев опыта
            if (armorStack.isEnchanted() && damageLeft < 100) {
                return true;
            }
        }

        return false;
    }


    private void sendPotion(AutoEXP.Potions potions) {
        int potionId = potions.getId();

        int hotBarSlot = findPotionSlot(potionId, true);
        int inventorySlot = findPotionSlot(potionId, false);

        if (mc.player.isPotionActive(potions.getPotion())) {
            potions.state = false;
        }

        if (hotBarSlot != -1) {
            sendUsePacket(hotBarSlot, Hand.MAIN_HAND);
            return;
        }

        if (inventorySlot != -1) {
            int bestSlotInHotBar = InventoryUtil.getInstance().findBestSlotInHotBar();
            ItemStack inventoryStack = mc.player.inventory.getStackInSlot(inventorySlot);
            ItemStack bestSlotStack = mc.player.inventory.getStackInSlot(bestSlotInHotBar);

            InventoryUtil.moveItem(inventorySlot, bestSlotInHotBar + 36, bestSlotStack.getItem() != Items.AIR);
            sendUsePacket(bestSlotInHotBar, Hand.MAIN_HAND);
        }
    }

    private void sendUsePacket(int slot, Hand hand) {
        mc.player.connection.sendPacket(new CHeldItemChangePacket(slot));
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        mc.player.swingArm(Hand.MAIN_HAND);
        previousPitch = 0;
        stopWatch.reset();
    }

    private int findPotionSlot(int id, boolean inHotBar) {
        int start = inHotBar ? 0 : 9;
        int end = inHotBar ? 9 : 36;

        for (int i = start; i < end; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (!(stack.getItem() instanceof SplashPotionItem)) {
                continue;
            }

            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(stack);

            for (EffectInstance effectInstance : potionEffects) {
                if (effectInstance.getPotion() == Effect.get(id)) {
                    return i;
                }
            }
        }

        return -1;
    }

    private Vector3d findNearestCollision() {
        return mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().grow(0.0, 0.5, 0.0))
                .toList()
                .stream()
                .min(Comparator.comparingDouble(box ->
                        box.getBoundingBox().getCenter().squareDistanceTo(mc.player.getPositionVec())))
                .map(box -> box.getBoundingBox().getCenter())
                .orElse(null);
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public enum Potions {
        INSTANT_HEALTH(Effects.INSTANT_HEALTH, 6);

        final Effect potion;
        final int id;
        boolean state;

        Potions(Effect potion, int potionId) {
            this.potion = potion;
            this.id = potionId;
        }
    }
}
