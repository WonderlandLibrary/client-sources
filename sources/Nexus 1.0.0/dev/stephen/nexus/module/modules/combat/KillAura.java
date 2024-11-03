package dev.stephen.nexus.module.modules.combat;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.Priorities;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.input.EventHandleInput;
import dev.stephen.nexus.event.impl.player.EventSilentRotation;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.player.BedAura;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.*;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.*;
import dev.stephen.nexus.utils.rotation.RotationUtils;
import dev.stephen.nexus.utils.timer.MillisTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KillAura extends Module {
    public static final BooleanSetting switchAura = new BooleanSetting("Switch Aura", false);
    public static final NumberSetting switchDelay = new NumberSetting("Switch Delay", 100, 1000, 150, 5);
    public static final ModeSetting sortMode = new ModeSetting("Sort Mode", "Off", "Off", "Health", "Distance", "Hurttime");

    public static final NumberSetting findRange = new NumberSetting("Find Range", 0, 6, 3, 0.1);
    public static final NumberSetting attackRange = new NumberSetting("Attack Range", 0, 6, 3, 0.1);
    public static final NumberSetting blockRange = new NumberSetting("Block Range", 0, 6, 3, 0.1);

    public static final ModeSetting vecMode = new ModeSetting("Vec Mode", "Normal", "Normal", "Custom");

    public static final ModeSetting rotMode = new ModeSetting("Rot Mode", "Normal", "Normal", "Grim 1.17", "None");

    public static final MultiModeSetting randomMode = new MultiModeSetting("Random Mode", "Basic", "Noise", "Time", "Jitter");
    public static final RangeSetting randomizationYawValue = new RangeSetting("Yaw Randomization Value", -20, 20, 3, 5, 1);
    public static final RangeSetting randomizationPitchValue = new RangeSetting("Pitch Randomization Value", -20, 20, 3, 5, 1);

    public static final MultiModeSetting angleSmoothMode = new MultiModeSetting("Smooth Mode", "FovBased", "Bezier", "Exp", "Noise", "MousePad");
    public static final RangeSetting noiseSmoothValue = new RangeSetting("Noise Smooth", 0.1, 1, 0.4, 0.6, 0.1);
    public static final RangeSetting rotSpeed = new RangeSetting("Rot Speed", 0, 180, 120, 160, 1);

    public static final BooleanSetting newVersion = new BooleanSetting("1.9 Attack Speed", false);
    public static final RangeSetting attackSpeed = new RangeSetting("CPS", 0, 25, 10, 12, 0.1);
    public static final BooleanSetting dropCps = new BooleanSetting("Drop CPS", false);
    public static final BooleanSetting dontAttackWhileBlocking = new BooleanSetting("Dont attack while blocking", false);
    public static final ModeSetting autoBlockMode = new ModeSetting("AutoBlock", "Off", "Off", "Vanilla", "Interact", "Reblock", "Watchdog", "NCP", "Legit");
    public static final BooleanSetting renderFakeAnim = new BooleanSetting("Fake autoblock", false);
    public static final BooleanSetting attackTeamMates = new BooleanSetting("Attack team mates", false);
    public static final BooleanSetting perfectHit = new BooleanSetting("Perfect Hit", false);
    public static final BooleanSetting noGui = new BooleanSetting("Stop in guis", true);
    public static final BooleanSetting onedoteight = new BooleanSetting("1.8 Swing Order", false);
    public static final BooleanSetting thoughWalls = new BooleanSetting("Though Walls", false);
    public static final BooleanSetting raytrace = new BooleanSetting("Raytrace", false);
    public static final BooleanSetting strictRaytrace = new BooleanSetting("Strict Raytrace", false);

    // Attacks per second
    public final MillisTimer attackTimer = new MillisTimer();
    private final MillisTimer guiTimer = new MillisTimer();

    private boolean wasInScreen;
    public boolean blocking;
    private boolean visualBlocking;
    private boolean rotated;

    // Switch
    private final ArrayList<PlayerEntity> targetArray = new ArrayList<>();
    public final MillisTimer switchTimer = new MillisTimer();
    private boolean switchTarget;
    public PlayerEntity target;
    private int targetCount;

    // rotations
    private float[] rotations;

    // Legit AB
    private boolean justAttacked;
    private int attacks;

    public long clicksPerSecond;
    private long prevClicksPerSecond;
    private long lastAttackMS;

    // reblock ab
    private int blockTicks = 0;

    public KillAura() {
        super("KillAura", "Attacks players", 0, ModuleCategory.COMBAT);
        addSettings(switchAura, switchDelay, sortMode, findRange, attackRange, blockRange, vecMode, rotMode, randomMode, randomizationYawValue, randomizationPitchValue, angleSmoothMode,
                noiseSmoothValue, rotSpeed, newVersion, attackSpeed, dropCps, dontAttackWhileBlocking, autoBlockMode, renderFakeAnim,
                attackTeamMates, perfectHit, noGui, onedoteight, thoughWalls, raytrace, strictRaytrace);

        attackSpeed.addDependency(newVersion, false);
        dropCps.addDependency(newVersion, false);

        switchDelay.addDependency(switchAura, true);

        angleSmoothMode.addDependency(vecMode, "Custom");

        noiseSmoothValue.addDependency(vecMode, "Custom");
        noiseSmoothValue.addDependency(angleSmoothMode, "Noise");

        randomMode.addDependency(vecMode, "Custom");
        randomizationPitchValue.addDependency(vecMode, "Custom");
        randomizationYawValue.addDependency(vecMode, "Custom");

        strictRaytrace.addDependency(raytrace, true);
    }

    @EventLink(Priorities.LOW)
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (Client.INSTANCE.getModuleManager().getModule(BedAura.class).isEnabled()) {
            if (BedAura.bedPos != null && BedAura.allowRotate.getValue()) {
                if (BedAura.rotate) {
                    float[] rot = RotationUtils.getRotationToBlock(BedAura.bedPos, BedAura.getDirection(BedAura.bedPos));
                    event.setSpeed(180F);
                    event.setYaw(rot[0]);
                    event.setPitch(rot[1]);

                    BedAura.rotate = false;
                    rotated = false;
                    return;
                }
            }
        }

        if (!shouldWork()) {
            return;
        }

        if (rotMode.isMode("None") || rotMode.isMode("Grim 1.17")) {
            if (rotMode.isMode("Grim 1.17")) {
                rotations = RotationUtils.getRotationToEntity(target);
            }
            rotated = true;
            return;
        }

        switch (vecMode.getMode()) {
            case "Normal":
                rotations = RotationUtils.getRotationToEntity(target);
                break;
            case "Custom":
                rotations = RotationUtils.getRotationToEntity(target);
                break;
        }

        if (rotations == null) {
            return;
        }

        float speed = (float) RandomUtil.randomBetween(rotSpeed.getValueMin(), rotSpeed.getValueMax());

        if (vecMode.isMode("Custom")) {
            if (angleSmoothMode.isModeSelected("Exp")) {
                rotations = AngleSmooth.smoothRotationsUsingExp(new float[]{Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch}, rotations, speed, target);
            }
            if (angleSmoothMode.isModeSelected("MousePad")) {
                rotations = AngleSmooth.smoothRotationsUsingMousePad(new float[]{Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch}, rotations, speed, target);
            }
            if (angleSmoothMode.isModeSelected("Bezier")) {
                rotations = AngleSmooth.smoothRotationsUsingBezier(new float[]{Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch}, rotations, speed, target);
            }
            if (angleSmoothMode.isModeSelected("Noise")) {
                rotations = AngleSmooth.smoothRotationsUsingNoise(new float[]{Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch}, rotations, noiseSmoothValue.getValueMinFloat(), noiseSmoothValue.getValueMaxFloat(), target);
            }
            if (angleSmoothMode.isModeSelected("FovBased")) {
                speed = AngleSmooth.smoothRotationsUsingFov(new float[]{Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch}, rotations, speed, target);
            }
        }

        if (vecMode.isMode("Custom")) {
            if (mc.player.age % 5 == 0 && !(randomizationYawValue.getValueMin() == 0 && randomizationYawValue.getValueMax() == 0 && randomizationPitchValue.getValueMin() == 0 && randomizationPitchValue.getValueMax() == 0)) {
                if (randomMode.isModeSelected("Basic")) {
                    double randomYaw = RandomUtil.randomBetween(randomizationYawValue.getValueMin(), randomizationYawValue.getValueMax()), randomPitch = RandomUtil.randomBetween(randomizationPitchValue.getValueMin(), randomizationPitchValue.getValueMax());

                    rotations[0] += (float) randomYaw;
                    rotations[1] += (float) randomPitch;
                }

                if (randomMode.isModeSelected("Noise")) {
                    double randomYaw = RandomUtil.randomNoise(randomizationYawValue.getValueMin(), randomizationYawValue.getValueMax()), randomPitch = RandomUtil.randomNoise(randomizationPitchValue.getValueMin(), randomizationPitchValue.getValueMax());

                    rotations[0] += (float) randomYaw;
                    rotations[1] += (float) randomPitch;
                }

                if (randomMode.isModeSelected("Time")) {
                    double randomYaw = RandomUtil.randomLast(randomizationYawValue.getValueMin(), randomizationYawValue.getValueMax(), lastAttackMS), randomPitch = RandomUtil.randomLast(randomizationPitchValue.getValueMin(), randomizationPitchValue.getValueMax(), lastAttackMS);

                    rotations[0] += (float) randomYaw;
                    rotations[1] += (float) randomPitch;
                }
            }
            if (randomMode.isModeSelected("Jitter")) {
                rotations[0] += (float) ((Math.random() * 2) - (Math.random() * 2));
                rotations[1] += (float) ((Math.random() * 2) - (Math.random() * 2));
            }
        }

        event.setSpeed(speed);
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);
        rotated = true;
    };

    @EventLink
    public final Listener<EventHandleInput> eventTickPreListener = event -> {
        if (!shouldWork()) {
            return;
        }

        if (!rotated) {
            return;
        }

        if (CombatUtils.getDistanceToEntity(target) <= blockRange.getValue() && mc.player.getInventory().getMainHandStack().getItem() instanceof SwordItem) {
            this.preAttackBlock();
        } else {
            this.unblock();
        }

        if (CombatUtils.getDistanceToEntity(target) <= attackRange.getValue()) {
            this.attack();
        }

        if (CombatUtils.getDistanceToEntity(target) <= blockRange.getValue() && mc.player.getInventory().getMainHandStack().getItem() instanceof SwordItem) {
            this.postAttackBlock();
        }
    };

    private void attack() {
        boolean stop = false;

        if (raytrace.getValue()) {
            if (strictRaytrace.getValue()) {
                stop = true;
                HitResult hitResult = mc.crosshairTarget;
                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHitResult = (EntityHitResult) mc.crosshairTarget;
                    if (entityHitResult.getEntity() != null) {
                        stop = false;
                    }
                }
            } else {
                boolean raytracing = RayTraceUtils.isLookingAtPlayer(target, attackRange.getValue(), rotMode.isMode("Grim 1.17") ? rotations[0] : Client.INSTANCE.getRotationManager().yaw, rotMode.isMode("Grim 1.17") ? rotations[1] : Client.INSTANCE.getRotationManager().pitch);
                if (!raytracing) {
                    stop = true;
                }
            }
        }

        if (stop) {
            return;
        }

        if (dontAttackWhileBlocking.getValue() && mc.player.isBlocking()) {
            return;
        }

        if (newVersion.getValue()) {
            final float attackCooldown = mc.player.getAttackCooldownProgress(-1F);
            final float dmg = (float) mc.player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * (0.2f + attackCooldown * attackCooldown * 0.8f);
            if (dmg >= 0.95f) {
                switchTarget = true;

                swingAndAttack();

                mc.player.resetLastAttackedTicks();
                justAttacked = true;
                attacks++;
            }
        } else if (attackTimer.hasElapsed(1000L / clicksPerSecond)) {
            if (perfectHit()) {
                switchTarget = true;

                swingAndAttack();

                attackTimer.reset();
                justAttacked = true;
                attacks++;

                prevClicksPerSecond = clicksPerSecond;
                getClicksPerSecond();
            }
        }
    }

    private void swingAndAttack() {
        if (rotMode.isMode("Grim 1.17")) {
            PacketUtils.sendPacketSilently(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), rotations[0], rotations[1], mc.player.isOnGround()));
        }

        if (onedoteight.getValue()) {
            mc.player.swingHand(Hand.MAIN_HAND);
            if (autoBlockMode.isMode("Interact")) {
                mc.interactionManager.interactEntity(mc.player, target, Hand.MAIN_HAND);
            }
        }

        mc.interactionManager.attackEntity(mc.player, target);

        lastAttackMS = System.currentTimeMillis();

        if (!onedoteight.getValue()) {
            mc.player.swingHand(Hand.MAIN_HAND);
            if (autoBlockMode.isMode("Interact")) {
                mc.interactionManager.interactEntity(mc.player, target, Hand.MAIN_HAND);
            }
        }

        if (rotMode.isMode("Grim 1.17")) {
            PacketUtils.sendPacketSilently(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
        }
    }

    private boolean perfectHit() {
        if (perfectHit.getValue()) {
            if (!mc.player.isOnGround() && mc.player.getVelocity().y > 0.3 && mc.player.fallDistance > 1) return false;
            if (Math.abs(MathUtils.wrapAngleTo180_float(Client.INSTANCE.getRotationManager().yaw - rotations[0])) >= 160)
                return false;
            if (mc.player.hurtTime == 0) {
                return target.hurtTime < RandomUtil.randomBetween(2, 4);
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void preAttackBlock() {
        switch (autoBlockMode.getMode()) {
            case "Vanilla", "Interact":
                mc.options.useKey.setPressed(true);
                blocking = true;
                visualBlocking = true;
                break;

            case "Reblock":
                blockTicks++;

                if (blockTicks == 4) {
                    mc.options.useKey.setPressed(true);
                    blocking = true;
                    visualBlocking = true;
                } else if (blocking && mc.player.getOffHandStack().getItem() instanceof ShieldItem) {
                    unblock();
                }
                break;

            case "Legit":
                if (justAttacked && mc.player.distanceTo(target) > 2.5 && attacks >= 6) {
                    mc.options.useKey.setPressed(true);

                    blocking = true;
                    visualBlocking = true;

                    justAttacked = false;
                    attacks = 0;
                } else {
                    this.unblock();
                }
                break;

            case "Watchdog":
                mc.options.useKey.setPressed(true);

                PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket((mc.player.getInventory().selectedSlot + 1) % 8));
                PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));

                blocking = false;
                visualBlocking = true;
                break;
            case "NCP":
                PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                blocking = false;

                visualBlocking = true;
                break;
        }
    }

    private void postAttackBlock() {
        switch (autoBlockMode.getMode()) {
            case "Watchdog", "NCP":
                if (autoBlockMode.isMode("NCP")) {
                    PacketUtils.sendPacket(PlayerInteractEntityC2SPacket.interactAt(target, mc.player.isSneaking(), Hand.MAIN_HAND, new Vec3d((double) randomNumber(-50, 50) / 100, (double) randomNumber(0, 200) / 100, (double) randomNumber(-50, 50) / 100)));
                    PacketUtils.sendPacket(PlayerInteractEntityC2SPacket.interact(target, mc.player.isSneaking(), Hand.MAIN_HAND));
                }
                mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                blocking = true;
                break;
        }
    }

    public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }

    private void unblock() {
        switch (autoBlockMode.getMode()) {
            case "Vanilla", "Legit", "Interact", "Watchdog":
                mc.options.useKey.setPressed(false);
                break;
            case "Reblock":
                mc.options.useKey.setPressed(false);
                blockTicks = 0;
                break;
            case "NCP":
                PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                mc.options.useKey.setPressed(false);
                break;
        }

        blocking = false;
        visualBlocking = false;
    }

    @EventLink
    public final Listener<EventTickPre> eventListener = event -> {
        if (isNull()) {
            blocking = false;
            visualBlocking = false;
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() && (mc.player.getInventory().getMainHandStack().getItem() instanceof BlockItem)) {
            return;
        }

        getTarget();

        if (target == null) {
            rotations = null;
            this.unblock();
            return;
        }

        if (noGui.getValue() && mc.currentScreen != null) {
            this.unblock();
            guiTimer.reset();
            wasInScreen = true;
            return;
        }

        if (wasInScreen && guiTimer.hasElapsed(200L)) {
            wasInScreen = false;
        }

        if (dropCps.getValue()) {
            if (mc.player.age % 12 == 0) {
                clicksPerSecond -= (long) RandomUtil.smartRandom(1, 3);
            }
        }

        if (clicksPerSecond == prevClicksPerSecond) clicksPerSecond -= (long) RandomUtil.smartRandom(1, 3);
    };

    private void getTarget() {
        if (isNull()) {
            return;
        }
        PlayerEntity temp_target = null;
        float health = Float.MAX_VALUE;
        targetArray.clear();
        List<PlayerEntity> inAttackRangeTargets = new ArrayList<>();

        for (PlayerEntity e : mc.world.getPlayers()) {
            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot(e)) {
                continue;
            }
            if (thoughWalls.getValue() && !mc.player.canSee(e)) {
                continue;
            }
            double calculatedDistance = CombatUtils.getDistanceToEntity(e);

            if (calculatedDistance <= findRange.getValue()) {
                if (e != mc.player && e.getHealth() > 0) {
                    if (CombatUtils.isSameTeam(e)) {
                        if (attackTeamMates.getValue()) {
                            targetArray.add(e);
                            if (calculatedDistance <= attackRange.getValue()) {
                                inAttackRangeTargets.add(e);
                            }
                            if (e.getHealth() < health) {
                                health = e.getHealth();
                                temp_target = e;
                            }
                        }
                    } else {
                        targetArray.add(e);
                        if (calculatedDistance <= attackRange.getValue()) {
                            inAttackRangeTargets.add(e);
                        }
                        if (e.getHealth() < health) {
                            health = e.getHealth();
                            temp_target = e;
                        }
                    }
                }
            }
        }

        switch (sortMode.getMode()) {
            case "Health":
                targetArray.sort(Comparator.comparingDouble(LivingEntity::getHealth));
                break;
            case "Distance":
                targetArray.sort(Comparator.comparingDouble(CombatUtils::getDistanceToEntity));
                break;
            case "Hurttime":
                targetArray.sort(Comparator.comparingDouble(it -> it.hurtTime));
                break;
        }

        if (switchAura.getValue()) {
            if (switchTarget) {
                if (switchTimer.hasElapsed((long) switchDelay.getValue())) {
                    targetCount++;

                    rotated = false;
                    switchTarget = false;

                    switchTimer.reset();
                }
            }

            if (targetCount >= inAttackRangeTargets.size()) {
                targetCount = 0;
            }

            if (!inAttackRangeTargets.isEmpty()) {
                temp_target = inAttackRangeTargets.get(targetCount);
            } else if (!targetArray.isEmpty()) {
                targetArray.sort(Comparator.comparingDouble(CombatUtils::getDistanceToEntity));
                temp_target = targetArray.getFirst();
            }
        } else {
            targetCount = 0;
            targetArray.sort(Comparator.comparingDouble(CombatUtils::getDistanceToEntity));
            if (!targetArray.isEmpty()) {
                temp_target = targetArray.get(targetCount);
            }
        }

        target = temp_target;
    }

    private void getClicksPerSecond() {
        clicksPerSecond = (long) RandomUtil.smartRandom(attackSpeed.getValueMaxInt(), attackSpeed.getValueMinInt()) + 6;
    }

    private boolean shouldWork() {
        if (isNull()) {
            return false;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() && mc.player.getInventory().getMainHandStack().getItem() instanceof BlockItem) {
            return false;
        }

        if (target == null) {
            return false;
        }

        if (noGui.getValue() && mc.currentScreen != null) {
            return false;
        }

        return guiTimer.hasElapsed(200L) || !wasInScreen;
    }

    @Override
    public void onDisable() {
        target = null;
        this.unblock();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        attackTimer.reset();
        switchTimer.reset();

        rotations = new float[]{mc.player.getYaw(), mc.player.getPitch()};
        lastAttackMS = System.currentTimeMillis();

        visualBlocking = false;
        switchTarget = false;
        justAttacked = false;
        wasInScreen = false;
        blocking = false;
        rotated = false;
        targetCount = 0;
        target = null;

        getClicksPerSecond();
        super.onEnable();
    }

    public boolean shouldRenderFakeAnim() {
        if (renderFakeAnim.getValue()) {
            return true;
        } else {
            return visualBlocking;
        }
    }

    private void interactWithFront() {
        EntityHitResult entityHitResult = RayTraceUtils.rayTrace(findRange.getValue(), Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch);

        if (entityHitResult != null) {
            Entity entity = entityHitResult.getEntity();
            if (entity != null) {
                mc.interactionManager.interactEntityAtLocation(mc.player, entity, entityHitResult, Hand.MAIN_HAND);
                mc.interactionManager.interactEntity(mc.player, entity, Hand.MAIN_HAND);
            }
        }

        BlockHitResult blockHitResult = RayTraceUtils.rayTrace(findRange.getValueFloat(), Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch);
        if (blockHitResult != null) {
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
        }
    }
}
