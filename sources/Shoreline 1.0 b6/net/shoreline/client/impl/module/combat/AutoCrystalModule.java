package net.shoreline.client.impl.module.combat;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.NumberDisplay;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.event.RunTickEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.event.network.PlayerTickEvent;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.impl.event.world.AddEntityEvent;
import net.shoreline.client.impl.event.world.RemoveEntityEvent;
import net.shoreline.client.impl.imixin.IPlayerInteractEntityC2SPacket;
import net.shoreline.client.impl.manager.world.tick.TickSync;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.util.EvictingQueue;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.shoreline.client.util.network.InteractType;
import net.shoreline.client.util.player.RotationUtil;
import net.shoreline.client.util.world.EndCrystalUtil;
import net.shoreline.client.util.world.EntityUtil;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Threaded AutoCrystal implementation.
 *
 * @author Shoreline
 * @since 1.0
 */
public class AutoCrystalModule extends RotationModule {
    // GENERAL SETTINGS
    Config<Boolean> multitaskConfig = new BooleanConfig("Multitask", "Allows attacking while using items", false);
    Config<Boolean> whileMiningConfig = new BooleanConfig("WhileMining", "Allows attacking while mining blocks", false);
    Config<Float> targetRangeConfig = new NumberConfig<>("EnemyRange", "Range to search for potential enemies", 1.0f, 10.0f, 13.0f);
    Config<Boolean> multithreadConfig = new BooleanConfig("Concurrent", "Attempts to run calculations in the background of the main minecraft thread on a concurrent thread pool", false);
    Config<Boolean> instantConfig = new BooleanConfig("Instant", "Instantly attacks crystals when they spawn", false);
    Config<Boolean> instantCalcConfig = new BooleanConfig("Instant-Calc", "Calculates a crystal when it spawns and attacks if it meets MINIMUM requirements, this will result in non-ideal crystal attacks", false, () -> instantConfig.getValue());
    Config<Float> instantDamageConfig = new NumberConfig<>("InstantDamage", "Minimum damage to attack crystals instantly", 1.0f, 6.0f, 10.0f, () -> instantConfig.getValue() && instantCalcConfig.getValue());
    Config<Boolean> instantMaxConfig = new BooleanConfig("InstantMax", "Attacks crystals instantly if they exceed the previous max attack damage (Note: This is still not a perfect check because the next tick could have better damages)", true, () -> instantConfig.getValue());
    Config<Boolean> latencyPositionConfig = new BooleanConfig("LatencyPosition", "Targets the latency positions of enemies", false);
    Config<Integer> maxLatencyConfig = new NumberConfig<>("MaxLatency", "Maximum latency factor when calculating positions", 50, 250, 1000, () -> latencyPositionConfig.getValue());
    Config<TickSync> latencySyncConfig = new EnumConfig<>("LatencyTimeout", "Latency calculations for time between crystal packets", TickSync.AVERAGE, TickSync.values());
    Config<Boolean> raytraceConfig = new BooleanConfig("Raytrace", "Raytrace to crystal position", true);
    Config<Sequential> sequentialConfig = new EnumConfig<>("Sequential", "Calculates placements sequentially, so placements occur once the expected crystal is broken", Sequential.NORMAL, Sequential.values());
    Config<Boolean> preSequentialConfig = new BooleanConfig("PreSequential", "Calculates placements before placing sequentially", true);
    Config<Boolean> swingConfig = new BooleanConfig("Swing", "Swing hand when placing and attacking crystals", true);
    // ROTATE SETTINGS
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotate before placing and breaking", false);
    Config<Rotate> strictRotateConfig = new EnumConfig<>("RotateStrict", "Rotates yaw over multiple ticks to prevent certain rotation flags in NCP", Rotate.OFF, Rotate.values(), () -> rotateConfig.getValue());
    Config<Integer> rotateLimitConfig = new NumberConfig<>("Rotate-Yaw", "Maximum yaw rotation in degrees for one tick", 1, 180, 180, NumberDisplay.DEGREES, () -> rotateConfig.getValue() && strictRotateConfig.getValue() != Rotate.OFF);
    Config<Boolean> rotateTickFactorConfig = new BooleanConfig("Rotate-TickReduction", "Factors in angles when calculating crystals to minimize attack ticks and speed up the break/place loop", false, () -> rotateConfig.getValue() && strictRotateConfig.getValue() != Rotate.OFF);

    // ENEMY SETTINGS
    Config<Boolean> playersConfig = new BooleanConfig("Players", "Target players", true);
    Config<Boolean> monstersConfig = new BooleanConfig("Monsters", "Target monsters", false);
    Config<Boolean> neutralsConfig = new BooleanConfig("Neutrals", "Target neutrals", false);
    Config<Boolean> animalsConfig = new BooleanConfig("Animals", "Target animals", false);
    // BREAK SETTINGS
    Config<Float> breakSpeedConfig = new NumberConfig<>("BreakSpeed", "Speed to break crystals", 0.1f, 18.0f, 20.0f);
    Config<Float> attackDelayConfig = new NumberConfig<>("AttackDelay", "Added delays", 0.0f, 0.0f, 5.0f);
    Config<Integer> attackFactorConfig = new NumberConfig<>("AttackFactor", "Factor of attack delay", 0, 0, 3, () -> attackDelayConfig.getValue() > 0.0);
    Config<Float> randomSpeedConfig = new NumberConfig<>("RandomSpeed", "Randomized delay for breaking crystals", 0.0f, 0.0f, 10.0f);
    Config<Boolean> breakDelayConfig = new BooleanConfig("BreakDelay", "Uses attack latency to calculate break delays", false);
    Config<Float> breakTimeoutConfig = new NumberConfig<>("BreakTimeout", "Time after waiting for the average break time before considering a crystal attack failed", 0.0f, 3.0f, 10.0f);
    Config<Float> minTimeoutConfig = new NumberConfig<>("MinTimeout", "Minimum time before considering a crystal break/place failed", 0.0f, 5.0f, 20.0f);
    Config<Integer> ticksExistedConfig = new NumberConfig<>("TicksExisted", "Minimum ticks alive to consider crystals for attack", 0, 0, 10);
    Config<Float> breakRangeConfig = new NumberConfig<>("BreakRange", "Range to break crystals", 0.1f, 4.0f, 5.0f);
    Config<Float> strictBreakRangeConfig = new NumberConfig<>("StrictBreakRange", "NCP range to break crystals", 0.1f, 4.0f, 5.0f);
    Config<Float> maxYOffsetConfig = new NumberConfig<>("MaxYOffset", "Maximum crystal y-offset difference", 1.0f, 5.0f, 10.0f);
    Config<Float> breakWallRangeConfig = new NumberConfig<>("BreakWallRange", "Range to break crystals through walls", 0.1f, 4.0f, 5.0f);
    Config<Swap> antiWeaknessConfig = new EnumConfig<>("AntiWeakness", "Swap to tools before attacking crystals", Swap.OFF, Swap.values());
    Config<Float> swapDelayConfig = new NumberConfig<>("SwapPenalty", "Delay for attacking after swapping items which prevents NCP flags", 0.0f, 0.0f, 10.0f);
    // fight.speed:
    //        limit: 13
    // shortterm:
    //        ticks: 8
    Config<Boolean> setDeadConfig = new BooleanConfig("SetDead", "Removes crystals faster client-side", false);
    Config<Boolean> inhibitConfig = new BooleanConfig("Inhibit", "Prevents excessive attacks", true);
    // default NCP config
    // limitforseconds:
    //        half: 8
    //        one: 15
    //        two: 30
    //        four: 60
    //        eight: 100
    Config<Boolean> manualConfig = new BooleanConfig("Manual", "Always breaks manually placed crystals", false);
    // PLACE SETTINGS
    Config<Boolean> placeConfig = new BooleanConfig("Place", "Places crystals to damage enemies. Place settings will only function if this setting is enabled.", true);
    Config<Float> placeSpeedConfig = new NumberConfig<>("PlaceSpeed", "Speed to place crystals", 0.1f, 18.0f, 20.0f, () -> placeConfig.getValue());
    Config<Float> placeRangeConfig = new NumberConfig<>("PlaceRange", "Range to place crystals", 0.1f, 4.0f, 5.0f, () -> placeConfig.getValue());
    Config<Float> strictPlaceRangeConfig = new NumberConfig<>("StrictPlaceRange", "NCP range to place crystals", 0.1f, 4.0f, 5.0f, () -> placeConfig.getValue());
    Config<Float> placeWallRangeConfig = new NumberConfig<>("PlaceWallRange", "Range to place crystals through walls", 0.1f, 4.0f, 5.0f, () -> placeConfig.getValue());
    Config<Boolean> boundsConfig = new BooleanConfig("Bounds", "Targets closest bounded rotations", false);
    Config<Boolean> placeRangeEyeConfig = new BooleanConfig("PlaceRangeEye", "Calculates place ranges starting from the eye position of the player", false, () -> placeConfig.getValue());
    Config<Boolean> placeRangeCenterConfig = new BooleanConfig("PlaceRangeCenter", "Calculates place ranges to the center of the block", true, () -> placeConfig.getValue());
    Config<Boolean> halfCrystalConfig = new BooleanConfig("HalfBB-Place", "Allow placements at a lower bounding", false, () -> placeConfig.getValue());
    Config<Boolean> antiTotemConfig = new BooleanConfig("AntiTotem", "Predicts totems and places crystals to instantly double pop and kill the target", false, () -> placeConfig.getValue());
    Config<Swap> autoSwapConfig = new EnumConfig<>("Swap", "Swaps to an end crystal before placing if the player is not holding one", Swap.OFF, Swap.values(), () -> placeConfig.getValue());
    Config<Float> alternateSpeedConfig = new NumberConfig<>("AlternateSpeed", "Speed for alternative swapping crystals", 1.0f, 18.0f, 20.0f, () -> placeConfig.getValue() && autoSwapConfig.getValue() == Swap.SILENT_ALT);
    Config<Boolean> antiSurroundConfig = new BooleanConfig("AntiSurround", "Places on mining blocks that when broken, can be placed on to damage enemies. Instantly destroys items spawned from breaking block and allows faster placing", false, () -> placeConfig.getValue());
    Config<Boolean> breakValidConfig = new BooleanConfig("BreakValid-Test", "Only places crystals that can be attacked", false, () -> placeConfig.getValue());
    Config<Boolean> strictDirectionConfig = new BooleanConfig("StrictDirection", "Interacts with only visible directions when placing crystals", false, () -> placeConfig.getValue());
    Config<Boolean> exposedDirectionConfig = new BooleanConfig("StrictDirection-Exposed", "Interacts with only exposed directions when placing crystals", false, () -> placeConfig.getValue());
    Config<Placements> placementsConfig = new EnumConfig<>("Placements", "Version standard for placing end crystals", Placements.NATIVE, Placements.values(), () -> placeConfig.getValue());
    // DAMAGE SETTINGS
    Config<Float> minDamageConfig = new NumberConfig<>("MinDamage", "Minimum damage required to consider attacking or placing an end crystal", 1.0f, 4.0f, 10.0f);
    Config<Boolean> armorBreakerConfig = new BooleanConfig("ArmorBreaker", "Attempts to break enemy armor with crystals", true);
    Config<Float> armorScaleConfig = new NumberConfig<>("ArmorScale", "Armor damage scale before attempting to break enemy armor with crystals", 1.0f, 5.0f, 20.0f, NumberDisplay.PERCENT, () -> armorBreakerConfig.getValue());
    Config<Float> lethalMultiplier = new NumberConfig<>("LethalMultiplier", "If we can kill an enemy with this many crystals, disregard damage values", 0.0f, 1.5f, 4.0f);
    Config<Boolean> safetyConfig = new BooleanConfig("Safety", "Accounts for total player safety when attacking and placing crystals", true);
    Config<Boolean> safetyBalanceConfig = new BooleanConfig("SafetyBalance", "Target damage must be greater than player damage for a position to be considered", false);
    Config<Boolean> safetyOverride = new BooleanConfig("SafetyOverride", "Overrides the safety checks if the crystal will kill an enemy", false);
    Config<Float> maxLocalDamageConfig = new NumberConfig<>("MaxLocalDamage", "The maximum player damage", 4.0f, 12.0f, 20.0f);
    Config<Boolean> blockDestructionConfig = new BooleanConfig("BlockDestruction", "Accounts for explosion block destruction when calculating damages", false);
    // EXTRAPOLATION
    Config<Boolean> extrapolateRangeConfig = new BooleanConfig("ExtrapolateRange", "Accounts for motion when calculating ranges", false);
    Config<Boolean> extrapolateGravityConfig = new BooleanConfig("Gravity-Extrapolation", "Accounts for gravity when extrapolating positions", false);
    Config<Integer> extrapolateTicksConfig = new NumberConfig<>("ExtrapolationTicks", "Accounts for motion when calculating enemy positions, not fully accurate.", 0, 0, 10);
    Config<Integer> selfExtrapolateTicksConfig = new NumberConfig<>("Self-ExtrapolationTicks", "Accounts for motion when calculating player positions, not fully accurate.", 0, 0, 10);
    // RENDER SETTINGS
    Config<Boolean> renderConfig = new BooleanConfig("Render", "Renders the current placement", true);
    Config<Boolean> renderAttackConfig = new BooleanConfig("Render-Attack", "Renders the current attack", false);
    Config<Boolean> renderSpawnConfig = new BooleanConfig("Render-Spawn", "Indicates if the current placement was spawned", false);
    Config<Boolean> damageNametagConfig = new BooleanConfig("Render-Damage", "Renders the current expected damage of a place/attack", false, () -> renderConfig.getValue());
    Config<Boolean> breakDebugConfig = new BooleanConfig("Break-Debug", "Debugs break ms in data", false);
    Config<Boolean> disableDeathConfig = new BooleanConfig("DisableOnDeath", "Disables during disconnect/death", false);
    //
    private DamageData<EndCrystalEntity> attackCrystal;
    private DamageData<BlockPos> placeCrystal;
    // This is retarded
    private String crystalStage;
    //
    private BlockPos renderPos;
    private BlockPos renderSpawnPos;
    //
    private Vec3d crystalRotation;
    private boolean attackRotate;
    private boolean rotated;
    //
    private static final Box FULL_CRYSTAL_BB = new Box(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
    private static final Box HALF_CRYSTAL_BB = new Box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    private final Timer lastAttackTimer = new CacheTimer();
    private final Timer lastPlaceTimer = new CacheTimer();
    private final Timer lastSwapTimer = new CacheTimer();
    private final Timer autoSwapTimer = new CacheTimer();
    //
    private final ArrayDeque<Long> attackLatency = new EvictingQueue<>(20);
    private final Map<Integer, Long> attackPackets =
            Collections.synchronizedMap(new ConcurrentHashMap<>());
    private final Map<BlockPos, Long> placePackets =
            Collections.synchronizedMap(new ConcurrentHashMap<>());
    //
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     *
     */
    public AutoCrystalModule() {
        super("AutoCrystal", "Attacks entities with end crystals",
                ModuleCategory.COMBAT, 800);
    }

    @Override
    public String getModuleData() {
        if (breakDebugConfig.getValue()) {
            return String.format("%dms", getBreakMs());
        }
        return "ARRAYLIST_INFO";
    }

    @Override
    public void onDisable() {
        renderPos = null;
        attackCrystal = null;
        placeCrystal = null;
        crystalRotation = null;
        attackPackets.clear();
        placePackets.clear();
        setStage("NONE");
    }

    @EventListener
    public void onDisconnect(DisconnectEvent event) {
        if (disableDeathConfig.getValue()) {
            disable();
        }
    }

    @EventListener
    public void onPlayerUpdate(PlayerTickEvent event) {
        renderPos = null;
        if (mc.player.isUsingItem() && mc.player.getActiveHand() == Hand.MAIN_HAND
                || mc.options.attackKey.isPressed()) {
            autoSwapTimer.reset();
        }
        ArrayList<Entity> entities = Lists.newArrayList(mc.world.getEntities());
        List<BlockPos> blocks = getSphere(mc.player.getPos());
        if (multithreadConfig.getValue()) {
            Future<DamageData<EndCrystalEntity>> attackCrystal1 = executor.submit(
                    new AttackCrystalTask(entities));
            Future<DamageData<BlockPos>> placeCrystal1 = executor.submit(
                    new PlaceCrystalTask(blocks, entities));
            try {
                attackCrystal = attackCrystal1.get();
                placeCrystal = placeCrystal1.get();
            } catch (InterruptedException | ExecutionException e) {
                Shoreline.error(Modules.AUTO_CRYSTAL, "Failed calc!");
                // e.printStackTrace();
            }
        } else {
            attackCrystal = calculateAttackCrystal(entities);
            placeCrystal = calculatePlaceCrystal(blocks, entities);
        }
        float breakDelay = 1000.0f - breakSpeedConfig.getValue() * 50.0f;
        if (breakDelayConfig.getValue()) {
            breakDelay = Math.max(minTimeoutConfig.getValue() * 50.0f, getBreakMs() + breakTimeoutConfig.getValue() * 50.0f);
        }
        attackRotate = attackCrystal != null && attackDelayConfig.getValue() <= 0.0 && lastAttackTimer.passed(breakDelay);
        if (attackCrystal != null) {
            crystalRotation = attackCrystal.damageData.getPos();
        } else if (placeCrystal != null) {
            crystalRotation = placeCrystal.damageData.toCenterPos().add(0.0, 0.5, 0.0);
        }
        if (rotateConfig.getValue() && crystalRotation != null && (placeCrystal == null || canHoldCrystal())) {
            float[] rotations = RotationUtil.getRotationsTo(mc.player.getEyePos(), crystalRotation);
            if (strictRotateConfig.getValue() == Rotate.FULL || strictRotateConfig.getValue() == Rotate.SEMI && attackRotate) {
                float serverYaw = Managers.ROTATION.getWrappedYaw();
                float diff = serverYaw - rotations[0];
                float diff1 = Math.abs(diff);
                if (diff1 > 180.0f) {
                    diff += diff > 0.0f ? -360.0f : 360.0f;
                }
                int dir = diff > 0.0f ? -1 : 1;
                float deltaYaw = dir * rotateLimitConfig.getValue();
                float yaw;
                if (diff1 > rotateLimitConfig.getValue()) {
                    yaw = serverYaw + deltaYaw;
                    rotated = false;
                } else {
                    yaw = rotations[0];
                    rotated = true;
                    crystalRotation = null;
                }
                setRotation(yaw, rotations[1]);
            } else {
                setRotation(rotations[0], rotations[1]);
                rotated = true;
                crystalRotation = null;
            }
        }
        if (isRotationBlocked() || !rotated && rotateConfig.getValue()) {
            return;
        }
        final Hand hand = getCrystalHand();
        if (attackCrystal != null) {
            // ChatUtil.clientSendMessage("yaw: " + rotations[0] + ", pitch: " + rotations[1]);
            if (attackRotate) {
                attackCrystal(attackCrystal.getDamageData(), hand);
                setStage("ATTACKING");
                lastAttackTimer.reset();
            }
        }
        if (placeCrystal != null) {
            renderPos = placeCrystal.getDamageData();
            if (lastPlaceTimer.passed(1000.0f - placeSpeedConfig.getValue() * 50.0f)) {
                placeCrystal(placeCrystal.getDamageData(), hand);
                setStage("PLACING");
                lastPlaceTimer.reset();
            }
        }
    }

    @EventListener
    public void onRunTick(RunTickEvent event) {
        if (mc.player == null || attackDelayConfig.getValue() <= 0.0) {
            return;
        }
        float attackFactor = 50.0f / Math.max(1.0f, attackFactorConfig.getValue());
        if (attackCrystal != null && lastAttackTimer.passed(attackDelayConfig.getValue() * attackFactor)) {
            attackCrystal(attackCrystal.getDamageData(), getCrystalHand());
            lastAttackTimer.reset();
        }
    }

    @EventListener
    public void onRenderWorld(RenderWorldEvent event) {
        if (renderPos != null && getCrystalHand() != null) {
            RenderManager.renderBox(event.getMatrices(), renderPos,
                    Modules.COLORS.getRGB(crystalStage.equals("ATTACKING") && renderAttackConfig.getValue() ? 105 : 80));
            RenderManager.renderBoundingBox(event.getMatrices(), renderPos, 1.5f,
                    Modules.COLORS.getRGB(145));
            if (damageNametagConfig.getValue() && placeCrystal != null) {
                DecimalFormat format = new DecimalFormat("0.0");
                RenderManager.renderSign(event.getMatrices(),
                        format.format(placeCrystal.getDamage()), renderPos.toCenterPos());
            }
        }
    }

    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof PlaySoundS2CPacket packet && packet.getCategory() == SoundCategory.BLOCKS
                && packet.getSound().value() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (Entity entity : Lists.newArrayList(mc.world.getEntities())) {
                if (entity instanceof EndCrystalEntity && entity.squaredDistanceTo(packet.getX(), packet.getY(), packet.getZ()) < 144.0) {
                    mc.executeSync(() -> {
                        mc.world.removeEntity(entity.getId(), Entity.RemovalReason.KILLED);
                        // entity.remove(Entity.RemovalReason.KILLED);
                        // entity.setRemoved(Entity.RemovalReason.KILLED);
                    });
                }
            }
        }
    }

    @EventListener
    public void onAddEntity(AddEntityEvent event) {
        if (!instantConfig.getValue() || !(event.getEntity() instanceof EndCrystalEntity crystalEntity)) {
            return;
        }
        Vec3d crystalPos = crystalEntity.getPos();
        BlockPos blockPos = BlockPos.ofFloored(crystalPos.add(0.0, -1.0, 0.0));
        renderSpawnPos = blockPos;
        Long time = placePackets.remove(blockPos);
        attackRotate = time != null;
        if (attackRotate) {
            attackInternal(crystalEntity, getCrystalHand());
            setStage("ATTACKING");
            lastAttackTimer.reset();
        } else if (instantCalcConfig.getValue()) {
            if (attackRangeCheck(crystalPos)) {
                return;
            }
            double selfDamage = EndCrystalUtil.getDamageTo(mc.player,
                    crystalPos, blockDestructionConfig.getValue());
            if (playerDamageCheck(selfDamage)) {
                return;
            }
            for (Entity entity : mc.world.getEntities()) {
                if (entity == null || !entity.isAlive() || entity == mc.player
                        || !isValidTarget(entity)
                        || Managers.SOCIAL.isFriend(entity.getUuid())) {
                    continue;
                }
                double crystalDist = crystalPos.squaredDistanceTo(entity.getPos());
                if (crystalDist > 144.0f) {
                    continue;
                }
                double dist = mc.player.squaredDistanceTo(entity);
                if (dist > targetRangeConfig.getValue() * targetRangeConfig.getValue()) {
                    continue;
                }
                double damage = EndCrystalUtil.getDamageTo(entity,
                        crystalPos, blockDestructionConfig.getValue());
                // WTFRICK
                attackRotate = damage > instantDamageConfig.getValue() || attackCrystal != null
                        && damage >= attackCrystal.getDamage() && instantMaxConfig.getValue()
                        || entity instanceof LivingEntity entity1 && isCrystalLethalTo(damage, entity1);
                if (attackRotate) {
                    attackInternal(crystalEntity, getCrystalHand());
                    setStage("ATTACKING");
                    lastAttackTimer.reset();
                    break;
                }
            }
        }
    }

    @EventListener
    public void onRemoveEntity(RemoveEntityEvent event) {
        if (disableDeathConfig.getValue() && event.getEntity() == mc.player) {
            disable();
        }
        if (!(event.getEntity() instanceof EndCrystalEntity crystalEntity)) {
            return;
        }
        Long time = attackPackets.remove(crystalEntity.getId());
        if (time != null) {
            attackLatency.add(System.currentTimeMillis() - time);
        }
        if (sequentialConfig.getValue() == Sequential.NORMAL) {
            placeSequential(crystalEntity, time);
        }
    }

    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (mc.player == null) {
            return;
        }
        if (event.getPacket() instanceof UpdateSelectedSlotC2SPacket) {
            if (!event.isClientPacket()) {
                autoSwapTimer.reset();
            }
            lastSwapTimer.reset();
        } else if (event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet && packet.getType() == InteractType.ATTACK
                && packet.getEntity() instanceof EndCrystalEntity crystalEntity && sequentialConfig.getValue() == Sequential.NORMAL) {
            placeSequential(crystalEntity, 1L);
        } else if (event.getPacket() instanceof PlayerActionC2SPacket packet && packet.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK
                && antiSurroundConfig.getValue() && canUseCrystalOnBlock(packet.getPos())) {
//            Vec3d crystalPos = crystalDamageVec(packet.getPos());
//            for (Entity entity : mc.world.getEntities()) {
//                if (entity == null || !entity.isAlive() || entity == mc.player
//                        || !isValidTarget(entity)
//                        || Managers.SOCIAL.isFriend(entity.getUuid())) {
//                    continue;
//                }
//                double crystalDist = crystalPos.squaredDistanceTo(entity.getPos());
//                if (crystalDist > 144.0f) {
//                    continue;
//                }
//                double dist = mc.player.squaredDistanceTo(entity);
//                if (dist > targetRangeConfig.getValue() * targetRangeConfig.getValue()) {
//                    continue;
//                }
//                double damage = EndCrystalUtil.getDamageTo(entity,
//                        crystalPos, blockDestructionConfig.getValue());
//                if (!targetDamageCheck(damage, entity)) {
//                    placeCrystal(packet.getPos(), getCrystalHand());
//                    break;
//                }
//            }
        }
    }

    public boolean isAttacking() {
        return attackCrystal != null;
    }

    public boolean isPlacing() {
        return placeCrystal != null && isHoldingCrystal();
    }

    public void attackCrystal(EndCrystalEntity entity, Hand hand) {
        if (attackCheckPre(hand)) {
            return;
        }
        StatusEffectInstance weakness = mc.player.getStatusEffect(StatusEffects.WEAKNESS);
        StatusEffectInstance strength = mc.player.getStatusEffect(StatusEffects.STRENGTH);
        if (weakness != null && (strength == null || weakness.getAmplifier() > strength.getAmplifier())) {
            int prev = mc.player.getInventory().selectedSlot;
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (!stack.isEmpty() && (stack.getItem() instanceof SwordItem
                        || stack.getItem() instanceof AxeItem
                        || stack.getItem() instanceof PickaxeItem)) {
                    slot = i;
                    break;
                }
            }
            if (slot != -1) {
                if (antiWeaknessConfig.getValue() != Swap.OFF) {
                    swapTo(slot);
                }
                attackInternal(entity, Hand.MAIN_HAND);
                if (antiWeaknessConfig.getValue() == Swap.SILENT
                        || antiWeaknessConfig.getValue() == Swap.SILENT_ALT) {
                    swapTo(autoSwapConfig.getValue() == Swap.SILENT_ALT ? slot : prev);
                }
            }
        } else {
            attackInternal(entity, hand);
        }
    }

    private void attackInternal(EndCrystalEntity crystalEntity, Hand hand) {
        hand = hand != null ? hand : Hand.MAIN_HAND;
        // ((AccessorPlayerInteractEntityC2SPacket) packet).hookSetEntityId(id);
        Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.attack(crystalEntity, mc.player.isSneaking()));
        if (setDeadConfig.getValue()) {
            mc.world.removeEntity(crystalEntity.getId(), Entity.RemovalReason.KILLED);
        }
        attackPackets.put(crystalEntity.getId(), System.currentTimeMillis());
        if (swingConfig.getValue()) {
            mc.player.swingHand(hand);
        } else {
            Managers.NETWORK.sendPacket(new HandSwingC2SPacket(hand));
        }
    }

    private void placeCrystal(BlockPos blockPos, Hand hand) {
        Direction sidePlace = getPlaceDirection(blockPos);
        // Vec3d vec3d = mc.player.getCameraPosVec(1.0f);
        // Vec3d vec3d1 = RotationUtil.getRotationVector();
        // Vec3d vec3d3 = vec3d.add(vec3d1.x * placeRangeConfig.getValue(),
        //        vec3d1.y * placeRangeConfig.getValue(), vec3d1.z * placeRangeConfig.getValue());
        // HitResult hitResult = mc.world.raycast(new RaycastContext(vec3d, vec3d3,
        //        RaycastContext.ShapeType.OUTLINE,
        //        RaycastContext.FluidHandling.NONE, mc.player));
        BlockHitResult result = new BlockHitResult(blockPos.toCenterPos(), sidePlace, blockPos, false);
        if (autoSwapConfig.getValue() != Swap.OFF
                && hand != Hand.OFF_HAND && !isHoldingCrystal()) {
            int prev = mc.player.getInventory().selectedSlot;
            int crystalSlot = getCrystalSlot();
            if (crystalSlot != -1) {
                swapTo(crystalSlot);
                placeInternal(result, hand);
                placePackets.put(blockPos, System.currentTimeMillis());
                if (autoSwapConfig.getValue() == Swap.SILENT
                        || autoSwapConfig.getValue() == Swap.SILENT_ALT) {
                    swapTo(autoSwapConfig.getValue() == Swap.SILENT_ALT ? crystalSlot : prev);
                }
            }
        } else {
            placeInternal(result, hand);
            placePackets.put(blockPos, System.currentTimeMillis());
        }
    }

    private void placeInternal(BlockHitResult result, Hand hand) {
        if (hand == null || !isHoldingCrystal()) {
            return;
        }
        Managers.NETWORK.sendSequencedPacket(id -> new PlayerInteractBlockC2SPacket(hand, result, id));
        if (swingConfig.getValue()) {
            mc.player.swingHand(hand);
        } else {
            Managers.NETWORK.sendPacket(new HandSwingC2SPacket(hand));
        }
    }

    private void swapTo(int slot) {
        if (autoSwapConfig.getValue() == Swap.NORMAL && !autoSwapTimer.passed(500)) {
            return;
        }
        if (autoSwapConfig.getValue() == Swap.SILENT_ALT) {
            mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId,
                    slot + 36, mc.player.getInventory().selectedSlot, SlotActionType.SWAP, mc.player);
        } else {
            Managers.INVENTORY.setClientSlot(slot);
        }
    }

    private int getCrystalSlot() {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof EndCrystalItem) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private Direction getPlaceDirection(BlockPos blockPos) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        if (strictDirectionConfig.getValue()) {
            if (mc.player.getY() >= blockPos.getY()) {
                return Direction.UP;
            }
            BlockHitResult result = mc.world.raycast(new RaycastContext(
                    mc.player.getEyePos(), new Vec3d(x + 0.5, y + 0.5, z + 0.5),
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE, mc.player));
            if (result != null && result.getType() == HitResult.Type.BLOCK) {
                Direction direction = result.getSide();
                if (!exposedDirectionConfig.getValue() || mc.world.isAir(blockPos.offset(direction))) {
                    return direction;
                }
            }
        } else {
            if (mc.world.isInBuildLimit(blockPos)) {
                return Direction.DOWN;
            }
            BlockHitResult result = mc.world.raycast(new RaycastContext(
                    mc.player.getEyePos(), new Vec3d(x + 0.5, y + 0.5, z + 0.5),
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE, mc.player));
            if (result != null && result.getType() == HitResult.Type.BLOCK) {
                return result.getSide();
            }
        }
        return Direction.UP;
    }

    private DamageData<EndCrystalEntity> calculateAttackCrystal(List<Entity> entities) {
        if (entities.isEmpty()) {
            return null;
        }
        double playerDamage = 0.0;
        double bestDamage = 0.0;
        EndCrystalEntity crystalEntity = null;
        Entity attackTarget = null;
        for (Entity crystal : entities) {
            if (!(crystal instanceof EndCrystalEntity crystal1) || !crystal.isAlive()) {
                continue;
            }
            Long time = attackPackets.get(crystal.getId());
            boolean attacked = time != null && time < getBreakMs();
            if ((crystal.age < ticksExistedConfig.getValue() || attacked) && inhibitConfig.getValue()) {
                continue;
            }
            if (attackRangeCheck(crystal1)) {
                continue;
            }
            double selfDamage = EndCrystalUtil.getDamageTo(mc.player,
                    crystal.getPos(), blockDestructionConfig.getValue());
            if (playerDamageCheck(selfDamage)) {
                continue;
            }
            for (Entity entity : entities) {
                if (entity == null || !entity.isAlive() || entity == mc.player
                        || !isValidTarget(entity)
                        || Managers.SOCIAL.isFriend(entity.getUuid())) {
                    continue;
                }
                double crystalDist = crystal.squaredDistanceTo(entity);
                if (crystalDist > 144.0f) {
                    continue;
                }
                double dist = mc.player.squaredDistanceTo(entity);
                if (dist > targetRangeConfig.getValue() * targetRangeConfig.getValue()) {
                    continue;
                }
                double damage = EndCrystalUtil.getDamageTo(entity,
                        crystal.getPos(), blockDestructionConfig.getValue());
                if (damage > bestDamage) {
                    bestDamage = damage;
                    playerDamage = selfDamage;
                    crystalEntity = crystal1;
                    attackTarget = entity;
                }
            }
        }
        if (crystalEntity == null || targetDamageCheck(bestDamage, attackTarget)) {
            return null;
        }
        return new DamageData<>(crystalEntity, attackTarget,
                bestDamage, playerDamage);
    }

    private boolean attackRangeCheck(EndCrystalEntity entity) {
        return attackRangeCheck(entity.getPos());
    }

    /**
     * @param entityPos
     * @return
     */
    private boolean attackRangeCheck(Vec3d entityPos) {
        Vec3d playerPos = mc.player.getEyePos();
        double dist = playerPos.squaredDistanceTo(entityPos);
        if (dist > breakRangeConfig.getValue() * breakRangeConfig.getValue()) {
            return true;
        }
        double yOff = Math.abs(entityPos.getY() - mc.player.getY());
        if (yOff > maxYOffsetConfig.getValue()) {
            return true;
        }
        BlockHitResult result = mc.world.raycast(new RaycastContext(
                playerPos, entityPos, RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE, mc.player));
        return result.getType() != HitResult.Type.MISS
                && dist > breakWallRangeConfig.getValue() * breakWallRangeConfig.getValue();
    }

    private DamageData<BlockPos> calculatePlaceCrystal(List<BlockPos> placeBlocks,
                                                       List<Entity> entities) {
        if (placeBlocks.isEmpty() || entities.isEmpty()) {
            return null;
        }
        double playerDamage = 0.0;
        double bestDamage = 0.0;
        BlockPos placeCrystal = null;
        Entity attackTarget = null;
        for (BlockPos pos : placeBlocks) {
            if (!canUseCrystalOnBlock(pos) || placeRangeCheck(pos)) {
                continue;
            }
            double selfDamage = EndCrystalUtil.getDamageTo(mc.player,
                    crystalDamageVec(pos), blockDestructionConfig.getValue());
            if (playerDamageCheck(selfDamage)) {
                continue;
            }
            for (Entity entity : entities) {
                if (entity == null || !entity.isAlive() || entity == mc.player
                        || !isValidTarget(entity)
                        || Managers.SOCIAL.isFriend(entity.getUuid())) {
                    continue;
                }
                double blockDist = pos.getSquaredDistance(entity.getPos());
                if (blockDist > 144.0f) {
                    continue;
                }
                double dist = mc.player.squaredDistanceTo(entity);
                if (dist > targetRangeConfig.getValue() * targetRangeConfig.getValue()) {
                    continue;
                }
                double damage = EndCrystalUtil.getDamageTo(entity,
                        crystalDamageVec(pos), blockDestructionConfig.getValue());
                if (damage > bestDamage) {
                    bestDamage = damage;
                    playerDamage = selfDamage;
                    placeCrystal = pos;
                    attackTarget = entity;
                }
            }
        }
        if (placeCrystal == null || targetDamageCheck(bestDamage, attackTarget)) {
            return null;
        }
        return new DamageData<>(placeCrystal, attackTarget,
                bestDamage, playerDamage);
    }

    /**
     * @param pos
     * @return
     */
    private boolean placeRangeCheck(BlockPos pos) {
        Vec3d player = placeRangeEyeConfig.getValue() ? mc.player.getEyePos() : mc.player.getPos();
        double dist = placeRangeCenterConfig.getValue() ?
                player.squaredDistanceTo(pos.toCenterPos()) : pos.getSquaredDistance(player.x, player.y, player.z);
        if (dist > placeRangeConfig.getValue() * placeRangeConfig.getValue()) {
            return true;
        }
        Vec3d raytrace = Vec3d.of(pos).add(0.0, raytraceConfig.getValue() ? 2.700000047683716 : 1.0, 0.0);
        BlockHitResult result = mc.world.raycast(new RaycastContext(
                mc.player.getEyePos(), raytrace,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE, mc.player));
        float maxDist = breakRangeConfig.getValue() * breakRangeConfig.getValue();
        if (result != null && result.getType() == HitResult.Type.BLOCK
                && result.getBlockPos() != pos) {
            maxDist = breakWallRangeConfig.getValue() * breakWallRangeConfig.getValue();
            if (dist > placeWallRangeConfig.getValue() * placeWallRangeConfig.getValue()) {
                return true;
            }
        }
        return breakValidConfig.getValue() && dist > maxDist;
    }

    private boolean targetDamageCheck(double bestDamage, Entity attackTarget) {
        double minDmg = minDamageConfig.getValue();
        if (attackTarget instanceof LivingEntity entity && isCrystalLethalTo(bestDamage, entity)) {
            minDmg = 2.0;
        }
        return bestDamage < minDmg;
    }

    private boolean playerDamageCheck(double playerDamage) {
        if (!mc.player.isCreative()) {
            float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            if (safetyConfig.getValue() && playerDamage >= health + 0.5f) {
                return true;
            }
            return playerDamage > maxLocalDamageConfig.getValue();
        }
        return false;
    }

    private boolean isCrystalLethalTo(double damage, LivingEntity entity) {
        float health = entity.getHealth() + entity.getAbsorptionAmount();
        if (damage * (1.0f + lethalMultiplier.getValue()) >= health + 0.5f) {
            return true;
        }
        if (armorBreakerConfig.getValue()) {
            for (ItemStack armorStack : entity.getArmorItems()) {
                int n = armorStack.getDamage();
                int n1 = armorStack.getMaxDamage();
                float durability = ((n1 - n) / (float) n1) * 100.0f;
                if (durability < armorScaleConfig.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void placeSequential(EndCrystalEntity crystalEntity, Long time) {
        if (time != null) {
            BlockPos sequentialPos = null;
            if (preSequentialConfig.getValue()) {
                List<BlockPos> blocks = getSphere(mc.player.getPos());
                DamageData<BlockPos> sequentialCalc = calculatePlaceCrystal(blocks,
                        Lists.newArrayList(mc.world.getEntities()));
                if (sequentialCalc != null) {
                    sequentialPos = sequentialCalc.getDamageData();
                }
            } else {
                sequentialPos = crystalEntity.getBlockPos().down();
            }
            Hand hand = getCrystalHand();
            if (sequentialPos == null || hand == null) {
                return;
            }
            renderPos = sequentialPos;
            placeCrystal(sequentialPos, hand);
            setStage("PLACING");
            lastPlaceTimer.reset();
        }
    }

    private boolean attackCheckPre(Hand hand) {
        if (!lastSwapTimer.passed(swapDelayConfig.getValue() * 25.0f)) {
            return true;
        }
        if (hand == Hand.MAIN_HAND) {
            return multitaskConfig.getValue() && mc.player.isUsingItem()
                    || !whileMiningConfig.getValue() && mc.interactionManager.isBreakingBlock();
        }
        return false;
    }

    private boolean isHoldingCrystal() {
        if (autoSwapConfig.getValue() == Swap.SILENT || autoSwapConfig.getValue() == Swap.SILENT_ALT) {
            return true;
        }
        return getCrystalHand() != null;
    }

    private Vec3d crystalDamageVec(BlockPos pos) {
        return Vec3d.of(pos).add(0.5, 1.0, 0.5);
    }

    /**
     * Returns <tt>true</tt> if the {@link Entity} is a valid enemy to attack.
     *
     * @param e The potential enemy entity
     * @return <tt>true</tt> if the entity is an enemy
     */
    private boolean isValidTarget(Entity e) {
        return e instanceof PlayerEntity && playersConfig.getValue()
                || EntityUtil.isMonster(e) && monstersConfig.getValue()
                || EntityUtil.isNeutral(e) && neutralsConfig.getValue()
                || EntityUtil.isPassive(e) && animalsConfig.getValue();
    }

    /**
     * Returns <tt>true</tt> if an {@link EndCrystalItem} can be used on the
     * param {@link BlockPos}.
     *
     * @param p The block pos
     * @return Returns <tt>true</tt> if the crystal item can be placed on the
     * block
     */
    public boolean canUseCrystalOnBlock(BlockPos p) {
        BlockState state = mc.world.getBlockState(p);
        if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.BEDROCK)) {
            return false;
        }
        BlockPos p2 = p.up();
        BlockState state2 = mc.world.getBlockState(p2);
        // ver 1.12.2 and below
        if (placementsConfig.getValue() == Placements.PROTOCOL && !mc.world.isAir(p2.up())) {
            return false;
        }
        if (!mc.world.isAir(p2) && !state2.isOf(Blocks.FIRE)) {
            return false;
        } else {
            final Box bb = halfCrystalConfig.getValue() ? HALF_CRYSTAL_BB :
                    FULL_CRYSTAL_BB;
            double d = p2.getX();
            double e = p2.getY();
            double f = p2.getZ();
            List<Entity> list = getEntitiesBlockingCrystal(new Box(d, e, f,
                    d + bb.maxX, e + bb.maxY, f + bb.maxZ));
            return list.isEmpty();
        }
    }

    private List<Entity> getEntitiesBlockingCrystal(Box box) {
        List<Entity> entities = new CopyOnWriteArrayList<>(
                mc.world.getOtherEntities(null, box));
        //
        for (Entity entity : entities) {
            if (entity == null || !entity.isAlive()
                    || entity instanceof ExperienceOrbEntity) {
                entities.remove(entity);
            } else if (entity instanceof EndCrystalEntity entity1
                    // && !intersectingCrystalCheck(entity1) // TODO: More advanced check for intersecting crystals
                    && entity.getPos().squaredDistanceTo(box.minX, box.minY, box.minZ) <= 1.0) {
                entities.remove(entity);
            }
        }
        return entities;
    }

    private boolean intersectingCrystalCheck(EndCrystalEntity entity) {
        // if (entity.age < ticksExistedConfig.getValue())
        // {
        //    return false;
        // }
        return attackRangeCheck(entity);
    }

    private List<BlockPos> getSphere(Vec3d origin) {
        List<BlockPos> sphere = new ArrayList<>();
        double rad = Math.ceil(placeRangeConfig.getValue());
        for (double x = -rad; x <= rad; ++x) {
            for (double y = -rad; y <= rad; ++y) {
                for (double z = -rad; z <= rad; ++z) {
                    Vec3i pos = new Vec3i((int) (origin.getX() + x),
                            (int) (origin.getY() + y), (int) (origin.getZ() + z));
                    final BlockPos p = new BlockPos(pos);
                    sphere.add(p);
                }
            }
        }
        return sphere;
    }

    private boolean canHoldCrystal() {
        return isHoldingCrystal() || autoSwapConfig.getValue() != Swap.OFF && getCrystalSlot() != -1;
    }

    private Hand getCrystalHand() {
        final ItemStack offhand = mc.player.getOffHandStack();
        final ItemStack mainhand = mc.player.getMainHandStack();
        if (offhand.getItem() instanceof EndCrystalItem) {
            return Hand.OFF_HAND;
        } else if (mainhand.getItem() instanceof EndCrystalItem) {
            return Hand.MAIN_HAND;
        }
        return null;
    }

    // Debug info
    public void setStage(String crystalStage) {
        this.crystalStage = crystalStage;
    }

    public int getBreakMs() {
        float avg = 0.0f;
        // fix ConcurrentModificationException
        ArrayList<Long> latencyCopy = Lists.newArrayList(attackLatency);
        if (!latencyCopy.isEmpty()) {
            for (float t : latencyCopy) {
                avg += t;
            }
            avg /= latencyCopy.size();
        }
        return (int) avg;
    }

    public enum Swap {
        NORMAL,
        SILENT,
        SILENT_ALT,
        OFF
    }

    public enum Sequential {
        NORMAL,
        STRICT,
        NONE
    }

    public enum Placements {
        NATIVE,
        PROTOCOL
    }

    public enum Rotate {
        FULL,
        SEMI,
        OFF
    }

    private static class DamageData<T> {
        //
        private final List<String> tags = new ArrayList<>();
        private T damageData;
        private Entity attackTarget;
        //
        private double damage, selfDamage;

        //
        public DamageData() {

        }

        public DamageData(T damageData, Entity attackTarget, double damage, double selfDamage) {
            this.damageData = damageData;
            this.attackTarget = attackTarget;
            this.damage = damage;
            this.selfDamage = selfDamage;
        }

        //
        public void addTag(String tag) {
            tags.add(tag);
        }

        public void setDamageData(T damageData, Entity attackTarget, double damage, double selfDamage) {
            this.damageData = damageData;
            this.attackTarget = attackTarget;
            this.damage = damage;
            this.selfDamage = selfDamage;
        }

        public T getDamageData() {
            return damageData;
        }

        public Entity getAttackTarget() {
            return attackTarget;
        }

        public double getDamage() {
            return damage;
        }

        public double getSelfDamage() {
            return selfDamage;
        }
    }

    private class AttackCrystalTask implements Callable<DamageData<EndCrystalEntity>> {
        private final List<Entity> threadSafeEntities;

        public AttackCrystalTask(List<Entity> threadSafeEntities) {
            this.threadSafeEntities = threadSafeEntities;
        }

        @Override
        public DamageData<EndCrystalEntity> call() throws Exception {
            return calculateAttackCrystal(threadSafeEntities);
        }
    }

    private class PlaceCrystalTask implements Callable<DamageData<BlockPos>> {
        private final List<BlockPos> threadSafeBlocks;
        private final List<Entity> threadSafeEntities;

        public PlaceCrystalTask(List<BlockPos> threadSafeBlocks,
                                List<Entity> threadSafeEntities) {
            this.threadSafeBlocks = threadSafeBlocks;
            this.threadSafeEntities = threadSafeEntities;
        }

        @Override
        public DamageData<BlockPos> call() throws Exception {
            return calculatePlaceCrystal(threadSafeBlocks, threadSafeEntities);
        }
    }
}
