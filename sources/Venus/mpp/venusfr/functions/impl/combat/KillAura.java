/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Comparator;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventInput;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.AntiBot;
import mpp.venusfr.functions.impl.combat.AutoPotion;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.SensUtils;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import mpp.venusfr.utils.player.MouseUtil;
import mpp.venusfr.utils.player.MoveUtils;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="KillAura", type=Category.Combat)
public class KillAura
extends Function {
    private final ModeSetting type = new ModeSetting("\u0422\u0438\u043f", "\u041f\u043b\u0430\u0432\u043d\u0430\u044f", "\u041f\u043b\u0430\u0432\u043d\u0430\u044f", "\u0420\u0435\u0437\u043a\u0430\u044f");
    private final SliderSetting attackRange = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f \u0430\u0442\u0442\u0430\u043a\u0438", 3.0f, 3.0f, 6.0f, 0.1f);
    final ModeListSetting targets = new ModeListSetting("\u0422\u0430\u0440\u0433\u0435\u0442\u044b", new BooleanSetting("\u0418\u0433\u0440\u043e\u043a\u0438", true), new BooleanSetting("\u0413\u043e\u043b\u044b\u0435", true), new BooleanSetting("\u041c\u043e\u0431\u044b", false), new BooleanSetting("\u0416\u0438\u0432\u043e\u0442\u043d\u044b\u0435", false), new BooleanSetting("\u0414\u0440\u0443\u0437\u044c\u044f", false), new BooleanSetting("\u0413\u043e\u043b\u044b\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438", true), new BooleanSetting("\u041d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438", true));
    final ModeListSetting options = new ModeListSetting("\u041e\u043f\u0446\u0438\u0438", new BooleanSetting("\u041d\u0435 \u0431\u0438\u0442\u044c \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b", true), new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u0440\u0438\u0442\u044b", true), new BooleanSetting("\u041b\u043e\u043c\u0430\u0442\u044c \u0449\u0438\u0442", true), new BooleanSetting("\u041e\u0442\u0436\u0438\u043c\u0430\u0442\u044c \u0449\u0438\u0442", true), new BooleanSetting("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435", false), new BooleanSetting("\u0421\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0430\u0442\u0430\u043a\u0443 \u0441 \u0422\u041f\u0421", false), new BooleanSetting("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c", true), new BooleanSetting("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f", true));
    final ModeSetting correctionType = new ModeSetting("\u0422\u0438\u043f \u043a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u0438", "\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u044b\u0439", "\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u044b\u0439", "\u0421\u0444\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u044b\u0439").setVisible(this::lambda$new$0);
    private final StopWatch stopWatch = new StopWatch();
    final BooleanSetting followTarget = new BooleanSetting("ElytraTarget", false);
    private ModeSetting elytraTargetType = new ModeSetting("ElytraTargetType", "ElytraTarget | Legit", "ElytraTarget | Legit", "ElytraTarget | Rage").setVisible(this::lambda$new$1);
    private final SliderSetting ggg = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u0440\u0438\u0432\u044f\u0437\u043a\u0438", 1.0f, 1.0f, 4.0f, 0.1f).setVisible(this::lambda$new$2);
    private Vector2f rotateVector = new Vector2f(0.0f, 0.0f);
    private LivingEntity target;
    private Entity selected;
    int ticks = 0;
    boolean isRotated;
    final AutoPotion autoPotion;
    private final SliderSetting newSliderSetting = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f \u043d\u0430\u0432\u043e\u0434\u043a\u0438", 15.0f, 3.0f, 40.0f, 0.5f).setVisible(this::lambda$new$3);
    float lastYaw;
    float lastPitch;

    public KillAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        this.addSettings(this.type, this.attackRange, this.targets, this.options, this.correctionType, this.followTarget, this.elytraTargetType, this.newSliderSetting, this.ggg);
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue() && this.correctionType.is("\u041d\u0435\u0437\u0430\u043c\u0435\u0442\u043d\u0430\u044f") && this.target != null && KillAura.mc.player != null) {
            MoveUtils.fixMovement(eventInput, this.rotateVector.x);
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (((Boolean)this.options.getValueByName("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c").get()).booleanValue() && (this.target == null || !this.isValid(this.target)) || !((Boolean)this.options.getValueByName("\u0424\u043e\u043a\u0443\u0441\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0434\u043d\u0443 \u0446\u0435\u043b\u044c").get()).booleanValue()) {
            this.updateTarget();
        }
        if (!(this.target == null || this.autoPotion.isState() && this.autoPotion.isActive())) {
            this.isRotated = false;
            if (this.shouldPlayerFalling() && this.stopWatch.hasTimeElapsed()) {
                this.updateAttack();
                this.ticks = 2;
            }
            if (this.type.is("\u0420\u0435\u0437\u043a\u0430\u044f")) {
                if (this.ticks > 0) {
                    this.updateRotation(true, 180.0f, 90.0f);
                    --this.ticks;
                } else {
                    this.reset();
                }
            } else if (!this.isRotated) {
                this.updateRotation(false, 80.0f, 35.0f);
            }
            if (((Boolean)this.followTarget.get()).booleanValue() && KillAura.mc.player.isElytraFlying()) {
                if (this.elytraTargetType.is("ElytraTarget | Legit")) {
                    this.lookAtTarget(this.target);
                } else if (this.elytraTargetType.is("ElytraTarget | Rage")) {
                    this.flyTowardsTarget2(this.target);
                }
            }
        } else {
            this.stopWatch.setLastMS(0L);
            this.reset();
        }
    }

    private void flyTowardsTarget(LivingEntity livingEntity) {
        if (livingEntity == null || KillAura.mc.player == null) {
            return;
        }
        if (this.elytraTargetType.is("ElytraTarget | Legit")) {
            this.lookAtTarget(livingEntity);
        } else if (this.elytraTargetType.is("ElytraTarget | Rage")) {
            this.flyTowardsTarget2(livingEntity);
        } else {
            this.lookAtTarget(livingEntity);
        }
    }

    private void flyTowardsTarget2(LivingEntity livingEntity) {
        double d;
        double d2;
        double d3;
        double d4;
        if (livingEntity == null || KillAura.mc.player == null) {
            return;
        }
        Vector3d vector3d = new Vector3d(livingEntity.getPosX(), livingEntity.getPosY() + (double)livingEntity.getEyeHeight(), livingEntity.getPosZ());
        Vector3d vector3d2 = KillAura.mc.player.getPositionVec();
        double d5 = vector3d.x - vector3d2.x;
        double d6 = vector3d.y - vector3d2.y;
        double d7 = vector3d.z - vector3d2.z;
        double d8 = vector3d2.distanceTo(vector3d);
        if (d8 < 0.1) {
            return;
        }
        float f = ((Float)this.ggg.get()).floatValue();
        double d9 = Math.min((double)f, d8 / 2.0);
        double d10 = d5 / d8 * d9;
        double d11 = vector3d2.x + d10;
        if (!this.isPathClear(vector3d2, new Vector3d(d11, d4 = vector3d2.y + (d3 = d6 / d8 * d9), d2 = vector3d2.z + (d = d7 / d8 * d9)))) {
            Vector3d vector3d3 = this.adjustForObstacles(vector3d2, new Vector3d(d10, d3, d));
            d10 = vector3d3.x;
            d3 = vector3d3.y;
            d = vector3d3.z;
        }
        KillAura.mc.player.setMotion(new Vector3d(d10, d3, d));
    }

    private boolean isPathClear(Vector3d vector3d, Vector3d vector3d2) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientWorld clientWorld = minecraft.world;
        for (double d = 0.0; d < 1.0; d += 0.1) {
            double d2 = vector3d.x + (vector3d2.x - vector3d.x) * d;
            double d3 = vector3d.y + (vector3d2.y - vector3d.y) * d;
            double d4 = vector3d.z + (vector3d2.z - vector3d.z) * d;
            BlockPos blockPos = new BlockPos(d2, d3, d4);
            if (clientWorld.isAirBlock(blockPos)) continue;
            return true;
        }
        return false;
    }

    private Vector3d adjustForObstacles(Vector3d vector3d, Vector3d vector3d2) {
        Vector3d vector3d3;
        int n;
        Minecraft minecraft = Minecraft.getInstance();
        ClientWorld clientWorld = minecraft.world;
        for (n = 1; n <= 3; ++n) {
            vector3d3 = vector3d.add(vector3d2.x, vector3d2.y + (double)n, vector3d2.z);
            if (!this.isPathClear(vector3d, vector3d3)) continue;
            return new Vector3d(vector3d2.x, vector3d2.y + (double)n, vector3d2.z);
        }
        for (n = 1; n <= 3; ++n) {
            vector3d3 = vector3d.add(vector3d2.x + (double)n, vector3d2.y, vector3d2.z);
            if (!this.isPathClear(vector3d, vector3d3)) continue;
            return new Vector3d(vector3d2.x + (double)n, vector3d2.y, vector3d2.z);
        }
        return vector3d2;
    }

    private void lookAtTarget(LivingEntity livingEntity) {
        Vector3d vector3d = new Vector3d(livingEntity.getPosX(), livingEntity.getPosY() + (double)livingEntity.getEyeHeight(), livingEntity.getPosZ());
        Vector3d vector3d2 = KillAura.mc.player.getPositionVec().add(0.0, KillAura.mc.player.getEyeHeight(), 0.0);
        double d = vector3d.x - vector3d2.x;
        double d2 = vector3d.y - vector3d2.y;
        double d3 = vector3d.z - vector3d2.z;
        double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
        double d5 = d / d4;
        double d6 = d2 / d4;
        double d7 = d3 / d4;
        float f = (float)(Math.atan2(d3, d) * 57.29577951308232) - 90.0f;
        float f2 = (float)(-(Math.atan2(d2, Math.sqrt(d * d + d3 * d3)) * 57.29577951308232));
        KillAura.mc.player.rotationYaw = f;
        KillAura.mc.player.rotationPitch = f2;
        Vector3d vector3d3 = KillAura.mc.player.getMotion();
        double d8 = vector3d3.length();
        KillAura.mc.player.setMotion(d5 * d8, d6 * d8, d7 * d8);
    }

    @Subscribe
    private void onWalking(EventMotion eventMotion) {
        if (this.target == null || this.autoPotion.isState() && this.autoPotion.isActive()) {
            return;
        }
        float f = this.rotateVector.x;
        float f2 = this.rotateVector.y;
        eventMotion.setYaw(f);
        eventMotion.setPitch(f2);
        KillAura.mc.player.rotationYawHead = f;
        KillAura.mc.player.renderYawOffset = f;
        KillAura.mc.player.rotationPitchHead = f2;
    }

    private void updateTarget() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (Entity entity2 : KillAura.mc.world.getAllEntities()) {
            LivingEntity livingEntity;
            if (!(entity2 instanceof LivingEntity) || !this.isValid(livingEntity = (LivingEntity)entity2)) continue;
            arrayList.add(livingEntity);
        }
        if (arrayList.isEmpty()) {
            this.target = null;
            return;
        }
        if (arrayList.size() == 1) {
            this.target = (LivingEntity)arrayList.get(0);
            return;
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$updateTarget$4).thenComparing(this::lambda$updateTarget$5).thenComparing(KillAura::lambda$updateTarget$6));
        this.target = (LivingEntity)arrayList.get(0);
    }

    private void updateRotation(boolean bl, float f, float f2) {
        Vector3d vector3d = this.target.getPositionVec().add(0.0, MathHelper.clamp(KillAura.mc.player.getPosYEye() - this.target.getPosY(), 0.0, (double)this.target.getHeight() * (KillAura.mc.player.getDistanceEyePos(this.target) / (double)((Float)this.attackRange.get()).floatValue())), 0.0).subtract(KillAura.mc.player.getEyePosition(1.0f));
        this.isRotated = true;
        float f3 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vector3d.z, vector3d.x)) - 90.0);
        float f4 = (float)(-Math.toDegrees(Math.atan2(vector3d.y, Math.hypot(vector3d.x, vector3d.z))));
        float f5 = MathHelper.wrapDegrees(f3 - this.rotateVector.x);
        float f6 = MathHelper.wrapDegrees(f4 - this.rotateVector.y);
        int n = (int)f5;
        switch ((String)this.type.get()) {
            case "\u041f\u043b\u0430\u0432\u043d\u0430\u044f": {
                float f7 = Math.min(Math.max(Math.abs(f5), 1.0f), f);
                float f8 = Math.min(Math.max(Math.abs(f6), 1.0f), f2);
                float f9 = f8 = bl && this.selected != this.target && (Boolean)this.options.getValueByName("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435").get() != false ? Math.max(Math.abs(f6), 1.0f) : (f8 = f8 / 3.0f);
                if (Math.abs(f7 - this.lastYaw) <= 3.0f) {
                    f7 = this.lastYaw + 3.1f;
                }
                float f10 = this.rotateVector.x + (f5 > 0.0f ? f7 : -f7);
                float f11 = MathHelper.clamp(this.rotateVector.y + (f6 > 0.0f ? f8 : -f8), -89.0f, 89.0f);
                float f12 = SensUtils.getGCDValue();
                f10 -= (f10 - this.rotateVector.x) % f12;
                f11 -= (f11 - this.rotateVector.y) % f12;
                this.rotateVector = new Vector2f(f10, f11);
                this.lastYaw = f7;
                this.lastPitch = f8;
                if (!((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) break;
                KillAura.mc.player.rotationYawOffset = f10;
                break;
            }
            case "\u0420\u0435\u0437\u043a\u0430\u044f": {
                float f13 = this.rotateVector.x + (float)n;
                float f14 = MathHelper.clamp(this.rotateVector.y + f6, -90.0f, 90.0f);
                float f15 = SensUtils.getGCDValue();
                f13 -= (f13 - this.rotateVector.x) % f15;
                f14 -= (f14 - this.rotateVector.y) % f15;
                this.rotateVector = new Vector2f(f13, f14);
                if (!((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) break;
                KillAura.mc.player.rotationYawOffset = f13;
            }
        }
    }

    private void updateAttack() {
        this.selected = MouseUtil.getMouseOver(this.target, this.rotateVector.x, this.rotateVector.y, ((Float)this.attackRange.get()).floatValue());
        if (((Boolean)this.options.getValueByName("\u0423\u0441\u043a\u043e\u0440\u044f\u0442\u044c \u0440\u043e\u0442\u0430\u0446\u0438\u044e \u043f\u0440\u0438 \u0430\u0442\u0430\u043a\u0435").get()).booleanValue()) {
            this.updateRotation(true, 60.0f, 35.0f);
        }
        if (((Boolean)this.followTarget.get()).booleanValue() && KillAura.mc.player.isElytraFlying()) {
            this.selected = MouseUtil.getMouseOver(this.target, this.rotateVector.x, this.rotateVector.y, ((Float)this.attackRange.get()).floatValue());
        }
        if (this.selected == null || this.selected != this.target) {
            return;
        }
        if (KillAura.mc.player.isBlocking() && ((Boolean)this.options.getValueByName("\u041e\u0442\u0436\u0438\u043c\u0430\u0442\u044c \u0449\u0438\u0442").get()).booleanValue()) {
            KillAura.mc.playerController.onStoppedUsingItem(KillAura.mc.player);
        }
        this.stopWatch.setLastMS(500L);
        KillAura.mc.playerController.attackEntity(KillAura.mc.player, this.target);
        KillAura.mc.player.swingArm(Hand.MAIN_HAND);
        LivingEntity livingEntity = this.target;
    }

    private boolean shouldPlayerFalling() {
        boolean bl = KillAura.mc.player.isInWater() && KillAura.mc.player.areEyesInFluid(FluidTags.WATER) || KillAura.mc.player.isInLava() || KillAura.mc.player.isOnLadder() || KillAura.mc.player.isPassenger() || KillAura.mc.player.abilities.isFlying;
        float f = KillAura.mc.player.getCooledAttackStrength((Boolean)this.options.getValueByName("\u0421\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0430\u0442\u0430\u043a\u0443 \u0441 \u0422\u041f\u0421").get() != false ? venusfr.getInstance().getTpsCalc().getAdjustTicks() : 1.5f);
        if (f < 0.92f) {
            return true;
        }
        if (!bl && ((Boolean)this.options.getValueByName("\u0422\u043e\u043b\u044c\u043a\u043e \u043a\u0440\u0438\u0442\u044b").get()).booleanValue()) {
            return !KillAura.mc.player.isOnGround() && KillAura.mc.player.fallDistance > 0.0f;
        }
        return false;
    }

    private boolean isValid(LivingEntity livingEntity) {
        if (livingEntity instanceof ClientPlayerEntity) {
            return true;
        }
        if (livingEntity.ticksExisted < 3) {
            return true;
        }
        if ((Boolean)this.followTarget.get() != false && KillAura.mc.player.isElytraFlying() ? KillAura.mc.player.getDistanceEyePos(livingEntity) > (double)((Float)this.newSliderSetting.get()).floatValue() : KillAura.mc.player.getDistanceEyePos(livingEntity) > (double)((Float)this.attackRange.get()).floatValue()) {
            return true;
        }
        if (((Boolean)this.options.getValueByName("\u041d\u0435 \u0431\u0438\u0442\u044c \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b").get()).booleanValue() && !KillAura.mc.player.canEntityBeSeen(livingEntity)) {
            return true;
        }
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)livingEntity;
            if (AntiBot.isBot(livingEntity)) {
                return true;
            }
            if (!((Boolean)this.targets.getValueByName("\u0414\u0440\u0443\u0437\u044c\u044f").get()).booleanValue() && FriendStorage.isFriend(playerEntity.getName().getString())) {
                return true;
            }
            if (playerEntity.getName().getString().equalsIgnoreCase(KillAura.mc.player.getName().getString())) {
                return true;
            }
        }
        if (livingEntity instanceof PlayerEntity) {
            if (!((Boolean)this.targets.getValueByName("\u0418\u0433\u0440\u043e\u043a\u0438").get()).booleanValue()) {
                return true;
            }
            if (livingEntity.getTotalArmorValue() == 0 && !((Boolean)this.targets.getValueByName("\u0413\u043e\u043b\u044b\u0435").get()).booleanValue()) {
                return true;
            }
            if (livingEntity.isInvisible() && livingEntity.getTotalArmorValue() == 0 && !((Boolean)this.targets.getValueByName("\u0413\u043e\u043b\u044b\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438").get()).booleanValue()) {
                return true;
            }
            if (livingEntity.isInvisible() && !((Boolean)this.targets.getValueByName("\u041d\u0435\u0432\u0438\u0434\u0438\u043c\u043a\u0438").get()).booleanValue()) {
                return true;
            }
        } else if (livingEntity instanceof MonsterEntity ? (Boolean)this.targets.getValueByName("\u041c\u043e\u0431\u044b").get() == false : livingEntity instanceof AnimalEntity && (Boolean)this.targets.getValueByName("\u0416\u0438\u0432\u043e\u0442\u043d\u044b\u0435").get() == false) {
            return true;
        }
        return !livingEntity.isInvulnerable() && livingEntity.isAlive() && !(livingEntity instanceof ArmorStandEntity);
    }

    private void breakShieldPlayer(PlayerEntity playerEntity) {
        if (playerEntity.isBlocking()) {
            int n = InventoryUtil.getInstance().getAxeInInventory(true);
            int n2 = InventoryUtil.getInstance().getAxeInInventory(false);
            if (n2 == -1 && n != -1) {
                int n3 = InventoryUtil.getInstance().findBestSlotInHotBar();
                KillAura.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.playerController.windowClick(0, n3 + 36, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(n3));
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, playerEntity);
                KillAura.mc.player.swingArm(Hand.MAIN_HAND);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(KillAura.mc.player.inventory.currentItem));
                KillAura.mc.playerController.windowClick(0, n3 + 36, 0, ClickType.PICKUP, KillAura.mc.player);
                KillAura.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, KillAura.mc.player);
            }
            if (n2 != -1) {
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(n2));
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, playerEntity);
                KillAura.mc.player.swingArm(Hand.MAIN_HAND);
                KillAura.mc.player.connection.sendPacket(new CHeldItemChangePacket(KillAura.mc.player.inventory.currentItem));
            }
        }
    }

    private void reset() {
        if (((Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get()).booleanValue()) {
            KillAura.mc.player.rotationYawOffset = -2.14748365E9f;
        }
        this.rotateVector = new Vector2f(KillAura.mc.player.rotationYaw, KillAura.mc.player.rotationPitch);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.reset();
        this.target = null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.reset();
        this.stopWatch.setLastMS(0L);
        this.target = null;
    }

    private double getEntityArmor(PlayerEntity playerEntity) {
        double d = 0.0;
        for (int i = 0; i < 4; ++i) {
            ItemStack itemStack = playerEntity.inventory.armorInventory.get(i);
            if (!(itemStack.getItem() instanceof ArmorItem)) continue;
            d += this.getProtectionLvl(itemStack);
        }
        return d;
    }

    private double getProtectionLvl(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)item;
            double d = armorItem.getDamageReduceAmount();
            if (itemStack.isEnchanted()) {
                d += (double)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack) * 0.25;
            }
            return d;
        }
        return 0.0;
    }

    private double getEntityHealth(LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)livingEntity;
            return (double)(playerEntity.getHealth() + playerEntity.getAbsorptionAmount()) * (this.getEntityArmor(playerEntity) / 20.0);
        }
        return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
    }

    public ModeSetting getType() {
        return this.type;
    }

    public ModeListSetting getOptions() {
        return this.options;
    }

    public StopWatch getStopWatch() {
        return this.stopWatch;
    }

    public LivingEntity getTarget() {
        return this.target;
    }

    private static int lambda$updateTarget$6(Object object, Object object2) {
        double d = KillAura.mc.player.getDistance((LivingEntity)object);
        double d2 = KillAura.mc.player.getDistance((LivingEntity)object2);
        return Double.compare(d, d2);
    }

    private int lambda$updateTarget$5(Object object, Object object2) {
        double d = this.getEntityHealth((LivingEntity)object);
        double d2 = this.getEntityHealth((LivingEntity)object2);
        return Double.compare(d, d2);
    }

    private double lambda$updateTarget$4(Object object) {
        if (object instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)object;
            return -this.getEntityArmor(playerEntity);
        }
        if (object instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)object;
            return -livingEntity.getTotalArmorValue();
        }
        return 0.0;
    }

    private Boolean lambda$new$3() {
        return (Boolean)this.followTarget.get();
    }

    private Boolean lambda$new$2() {
        return this.elytraTargetType.is("ElytraTarget | Rage");
    }

    private Boolean lambda$new$1() {
        return (Boolean)this.followTarget.get();
    }

    private Boolean lambda$new$0() {
        return (Boolean)this.options.getValueByName("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f").get();
    }
}

