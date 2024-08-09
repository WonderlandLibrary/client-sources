/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.Comparator;
import java.util.List;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import mpp.venusfr.utils.player.MoveUtils;
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
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="AutoPotion", type=Category.Combat)
public class AutoPotion
extends Function {
    float previousPitch;
    final StopWatch stopWatch = new StopWatch();

    @Subscribe
    public void onMotion(EventMotion eventMotion) {
        if (!this.canThrowPotion()) {
            return;
        }
        if (this.isActive()) {
            Vector3d vector3d = this.findNearestCollision();
            Vector2f vector2f = vector3d == null ? new Vector2f(AutoPotion.mc.player.rotationYaw, 90.0f) : MathUtil.rotationToVec(vector3d);
            this.previousPitch = vector2f.y;
            eventMotion.setYaw(vector2f.x);
            eventMotion.setPitch(this.previousPitch);
            AutoPotion.mc.player.rotationPitchHead = this.previousPitch;
        }
        eventMotion.setPostMotion(() -> this.lambda$onMotion$0(eventMotion));
    }

    public boolean isActive() {
        for (Potions potions : Potions.values()) {
            if (this.shouldUsePotion(potions) || !potions.isState()) continue;
            return false;
        }
        return true;
    }

    private boolean canThrowPotion() {
        boolean bl = !MoveUtils.isBlockUnder(0.5f) || AutoPotion.mc.player.isOnGround();
        boolean bl2 = this.stopWatch.isReached(400L);
        boolean bl3 = AutoPotion.mc.player.ticksExisted > 100;
        return bl && bl2 && bl3;
    }

    private boolean shouldUsePotion(Potions potions) {
        if (AutoPotion.mc.player.isPotionActive(potions.getPotion())) {
            potions.state = false;
            return false;
        }
        int n = potions.getId();
        if (this.findPotionSlot(n, false) == -1 && this.findPotionSlot(n, true) == -1) {
            potions.state = false;
            return false;
        }
        return true;
    }

    private void sendPotion(Potions potions) {
        int n = potions.getId();
        int n2 = this.findPotionSlot(n, false);
        int n3 = this.findPotionSlot(n, true);
        if (AutoPotion.mc.player.isPotionActive(potions.getPotion())) {
            potions.state = false;
        }
        if (n2 != -1) {
            this.sendUsePacket(n2, Hand.MAIN_HAND);
            return;
        }
        if (n3 != -1) {
            int n4 = InventoryUtil.getInstance().findBestSlotInHotBar();
            ItemStack itemStack = AutoPotion.mc.player.inventory.getStackInSlot(n3);
            ItemStack itemStack2 = AutoPotion.mc.player.inventory.getStackInSlot(n4);
            InventoryUtil.moveItem(n3, n4 + 36, itemStack2.getItem() != Items.AIR);
            this.sendUsePacket(n4, Hand.MAIN_HAND);
        }
    }

    private void sendUsePacket(int n, Hand hand) {
        AutoPotion.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
        AutoPotion.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        AutoPotion.mc.player.swingArm(Hand.MAIN_HAND);
        this.previousPitch = 0.0f;
        this.stopWatch.reset();
    }

    private int findPotionSlot(int n, boolean bl) {
        int n2 = bl ? 0 : 9;
        int n3 = bl ? 9 : 36;
        for (int i = n2; i < n3; ++i) {
            ItemStack itemStack = AutoPotion.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof SplashPotionItem)) continue;
            List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemStack);
            for (EffectInstance effectInstance : list) {
                if (effectInstance.getPotion() != Effect.get(n)) continue;
                return i;
            }
        }
        return 1;
    }

    private Vector3d findNearestCollision() {
        return AutoPotion.mc.world.getCollisionShapes(AutoPotion.mc.player, AutoPotion.mc.player.getBoundingBox().grow(0.0, 0.5, 0.0)).toList().stream().min(Comparator.comparingDouble(AutoPotion::lambda$findNearestCollision$1)).map(AutoPotion::lambda$findNearestCollision$2).orElse(null);
    }

    private static Vector3d lambda$findNearestCollision$2(VoxelShape voxelShape) {
        return voxelShape.getBoundingBox().getCenter();
    }

    private static double lambda$findNearestCollision$1(VoxelShape voxelShape) {
        return voxelShape.getBoundingBox().getCenter().squareDistanceTo(AutoPotion.mc.player.getPositionVec());
    }

    private void lambda$onMotion$0(EventMotion eventMotion) {
        boolean bl = this.previousPitch == eventMotion.getPitch();
        int n = AutoPotion.mc.player.inventory.currentItem;
        for (Potions potions : Potions.values()) {
            potions.state = true;
            if (this.shouldUsePotion(potions) || !potions.state || !bl) continue;
            this.sendPotion(potions);
            AutoPotion.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            AutoPotion.mc.playerController.syncCurrentPlayItem();
        }
    }

    public static enum Potions {
        STRENGHT(Effects.STRENGTH, 5),
        SPEED(Effects.SPEED, 1),
        FIRE_RESIST(Effects.FIRE_RESISTANCE, 12);

        private final Effect potion;
        private final int id;
        private boolean state;

        private Potions(Effect effect, int n2) {
            this.potion = effect;
            this.id = n2;
        }

        public Effect getPotion() {
            return this.potion;
        }

        public int getId() {
            return this.id;
        }

        public boolean isState() {
            return this.state;
        }
    }
}

