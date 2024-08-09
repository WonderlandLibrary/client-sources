package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventMotion;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MoveUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Comparator;
import java.util.List;

@FunctionRegister(name = "AutoPotion", type = Category.COMBAT)
public class AutoPotion extends Function {

    float previousPitch;
    final StopWatch stopWatch = new StopWatch();


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

            for (Potions potion : Potions.values()) {
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
        for (Potions potionType : Potions.values()) {
            if (shouldUsePotion(potionType)) {
                continue;
            }
            if (potionType.isState()) {
                return true;
            }
        }
        return false;
    }

    private boolean canThrowPotion() {
        boolean isOnGround = !MoveUtils.isBlockUnder(0.5F) || mc.player.isOnGround();
        boolean timeIsReached = stopWatch.isReached(400);
        boolean ticksExisted = mc.player.ticksExisted > 100;

        return isOnGround && timeIsReached && ticksExisted;
    }

    private boolean shouldUsePotion(Potions potions) {
        if (mc.player.isPotionActive(potions.getPotion())) {
            potions.state = false;
            return true;
        }
        int potionId = potions.getId();

        if (findPotionSlot(potionId, true) == -1 && findPotionSlot(potionId, false) == -1) {
            potions.state = false;
            return true;
        }
        return false;
    }

    private void sendPotion(Potions potions) {
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
        STRENGHT(Effects.STRENGTH, 5),
        SPEED(Effects.SPEED, 1),
        FIRE_RESIST(Effects.FIRE_RESISTANCE, 12);

        final Effect potion;
        final int id;
        boolean state;

        Potions(Effect potion, int potionId) {
            this.potion = potion;
            this.id = potionId;
        }
    }
}
