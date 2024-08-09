package xyz.mathax.mathaxclient.systems.modules.combat;

import com.google.common.util.concurrent.AtomicDouble;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.EntityAddedEvent;
import xyz.mathax.mathaxclient.events.entity.EntityRemovedEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixininterface.IBox;
import xyz.mathax.mathaxclient.mixininterface.IRaycastContext;
import xyz.mathax.mathaxclient.mixininterface.IVec3d;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.entity.Target;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerManager;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.joml.Vector3d;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.utils.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CrystalAura extends Module {
    private int breakTimer, placeTimer, switchTimer, ticksPassed;
    private final List<LivingEntity> targets = new ArrayList<>();

    private final Vec3d vec3d = new Vec3d(0, 0, 0);
    private final Vec3d playerEyePos = new Vec3d(0, 0, 0);
    private final Vector3d vec3 = new Vector3d();
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
    private final Box box = new Box(0, 0, 0, 0, 0, 0);

    private final Vec3d vec3dRayTraceEnd = new Vec3d(0, 0, 0);
    private RaycastContext raycastContext;

    private final IntSet placedCrystals = new IntOpenHashSet();
    private boolean placing;
    private int placingTimer;
    private final BlockPos.Mutable placingCrystalBlockPos = new BlockPos.Mutable();

    private final IntSet removed = new IntOpenHashSet();
    private final Int2IntMap attemptedBreaks = new Int2IntOpenHashMap();
    private final Int2IntMap waitingToExplode = new Int2IntOpenHashMap();
    private int attacks;

    private double serverYaw;

    private LivingEntity bestTarget;
    private double bestTargetDamage;
    private int bestTargetTimer;

    private boolean didRotateThisTick;
    private boolean isLastRotationPos;
    private final Vec3d lastRotationPos = new Vec3d(0, 0 ,0);
    private double lastYaw, lastPitch;
    private int lastRotationTimer;

    private int renderTimer, breakRenderTimer;
    private final BlockPos.Mutable renderPos = new BlockPos.Mutable();
    private final BlockPos.Mutable breakRenderPos = new BlockPos.Mutable();
    private double renderDamage;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup targetingSettings = settings.createGroup("Targeting");
    private final SettingGroup placeSettings = settings.createGroup("Place");
    private final SettingGroup facePlaceSettings = settings.createGroup("Face Place");
    private final SettingGroup breakSettings = settings.createGroup("Break");
    private final SettingGroup pauseSettings = settings.createGroup("Pause");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Double> minDamageSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Min damage")
            .description("Minimum damage the crystal needs to deal to your target.")
            .defaultValue(6)
            .min(0)
            .build()
    );

    private final Setting<Double> maxDamageSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Max damage")
            .description("Maximum damage crystals can deal to yourself.")
            .defaultValue(6)
            .range(0, 36)
            .sliderRange(0, 36)
            .build()
    );

    private final Setting<AutoSwitchMode> autoSwitchSetting = generalSettings.add(new EnumSetting.Builder<AutoSwitchMode>()
            .name("Auto switch")
            .description("Switches to crystals in your hotbar once a target is found.")
            .defaultValue(AutoSwitchMode.Normal)
            .build()
    );

    private final Setting<Boolean> antiSuicideSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Anti suicide")
            .description("Will not place and break crystals if they will kill you.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Rotates server-side towards the crystals being hit/placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<YawStepMode> yawStepModeSetting = generalSettings.add(new EnumSetting.Builder<YawStepMode>()
            .name("Yaw steps mode")
            .description("When to run the yaw steps check.")
            .defaultValue(YawStepMode.Break)
            .visible(rotateSetting::get)
            .build()
    );

    private final Setting<Double> yawStepsSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Yaw steps")
            .description("Maximum number of degrees its allowed to rotate in one tick.")
            .defaultValue(180)
            .range(1, 180)
            .visible(rotateSetting::get)
            .build()
    );

    private final Setting<Boolean> ignoreTerrainSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore terrain")
            .description("Completely ignores terrain if it can be blown up by end crystals.")
            .defaultValue(true)
            .build()
    );

    // Targeting

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = targetingSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Entities to attack.")
            .defaultValue(EntityType.PLAYER)
            .onlyAttackable()
            .build()
    );

    private final Setting<Double> targetRangeSetting = targetingSettings.add(new DoubleSetting.Builder()
            .name("Target range")
            .description("Range in which to search for targets.")
            .defaultValue(10)
            .min(0)
            .sliderRange(0, 16)
            .build()
    );

    private final Setting<Boolean> predictMovementSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Predict movement")
            .description("Predicts target movement.")
            .defaultValue(false)
            .build()
    );

    // Place

    private final Setting<Boolean> doPlaceSetting = placeSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("If the Crystal Aura should place crystals.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> placeDelaySetting = placeSettings.add(new IntSetting.Builder()
            .name("Place delay")
            .description("The delay in ticks to wait to place a crystal after it's exploded.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Double> placeRangeSetting = placeSettings.add(new DoubleSetting.Builder()
            .name("Place range")
            .description("Range in which to place crystals.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Double> placeWallsRangeSetting = placeSettings.add(new DoubleSetting.Builder()
            .name("Place walls range")
            .description("Range in which to place crystals when behind blocks.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Boolean> placement112Setting = placeSettings.add(new BoolSetting.Builder()
            .name("1.12 placement")
            .description("Uses 1.12 crystal placement.")
            .defaultValue(false)
            .build()
    );

    private final Setting<SupportMode> supportSetting = placeSettings.add(new EnumSetting.Builder<SupportMode>()
            .name("Support")
            .description("Places a support block in air if no other position have been found.")
            .defaultValue(SupportMode.Disabled)
            .build()
    );

    private final Setting<Integer> supportDelaySetting = placeSettings.add(new IntSetting.Builder()
            .name("Support delay")
            .description("Delay in ticks after placing support block.")
            .defaultValue(1)
            .min(0)
            .visible(() -> supportSetting.get() != SupportMode.Disabled)
            .build()
    );

    // Face place

    private final Setting<Boolean> facePlaceSetting = facePlaceSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Will face-place when target is below a certain health or armor durability threshold.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> facePlaceHealthSetting = facePlaceSettings.add(new DoubleSetting.Builder()
            .name("Health")
            .description("The health the target has to be at to start face placing.")
            .defaultValue(8)
            .min(1)
            .sliderRange(0, 36)
            .visible(facePlaceSetting::get)
            .build()
    );

    private final Setting<Double> facePlaceDurabilitySetting = facePlaceSettings.add(new DoubleSetting.Builder()
            .name("Durability")
            .description("The durability threshold percentage to be able to face-place.")
            .defaultValue(2)
            .min(1)
            .sliderRange(0, 100)
            .visible(facePlaceSetting::get)
            .build()
    );

    private final Setting<Boolean> facePlaceArmorSetting = facePlaceSettings.add(new BoolSetting.Builder()
            .name("Missing armor")
            .description("Automatically starts face placing when a target misses a piece of armor.")
            .defaultValue(false)
            .visible(facePlaceSetting::get)
            .build()
    );

    private final Setting<KeyBind> forceFacePlaceSetting = facePlaceSettings.add(new KeyBindSetting.Builder()
            .name("Force activate")
            .description("Starts face place when this button is pressed.")
            .defaultValue(KeyBind.none())
            .build()
    );

    // Break

    private final Setting<Boolean> doBreakSetting = breakSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Automatically breaks crystals.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> breakDelaySetting = breakSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The delay in ticks to wait to break a crystal after it's placed.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Boolean> smartDelaySetting = breakSettings.add(new BoolSetting.Builder()
            .name("Smart delay")
            .description("Only breaks crystals when the target can receive damage.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> switchDelaySetting = breakSettings.add(new IntSetting.Builder()
            .name("Switch delay")
            .description("The delay in ticks to wait to break a crystal after switching hotbar slot.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Double> breakRangeSetting = breakSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("Range in which to break crystals.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Double> breakWallsRangeSetting = breakSettings.add(new DoubleSetting.Builder()
            .name("Walls range")
            .description("Range in which to break crystals when behind blocks.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Boolean> onlyBreakOwnSetting = breakSettings.add(new BoolSetting.Builder()
            .name("Only own")
            .description("Only breaks own crystals.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> breakAttemptsSetting = breakSettings.add(new IntSetting.Builder()
            .name("Break attempts")
            .description("How many times to hit a crystal before stopping to target it.")
            .defaultValue(2)
            .min(1)
            .sliderRange(1, 5)
            .build()
    );

    private final Setting<Integer> ticksExistedSetting = breakSettings.add(new IntSetting.Builder()
            .name("Ticks existed")
            .description("Amount of ticks a crystal needs to have lived for it to be attacked by CrystalAura.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Integer> attackFrequencySetting = breakSettings.add(new IntSetting.Builder()
            .name("Attack frequency")
            .description("Maximum hits to do per second.")
            .defaultValue(25)
            .min(1)
            .sliderRange(1, 30)
            .build()
    );

    private final Setting<Boolean> fastBreakSetting = breakSettings.add(new BoolSetting.Builder()
            .name("Fast break")
            .description("Ignores break delay and tries to break the crystal as soon as it's spawned in the world.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> antiWeaknessSetting = breakSettings.add(new BoolSetting.Builder()
            .name("Anti weakness")
            .description("Switches to tools with high enough damage to explode the crystal with weakness effect.")
            .defaultValue(true)
            .build()
    );

    // Pause

    private final Setting<Boolean> eatPauseSetting = pauseSettings.add(new BoolSetting.Builder()
            .name("Pause on eat")
            .description("Pauses Crystal Aura when eating.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> drinkPauseSetting = pauseSettings.add(new BoolSetting.Builder()
            .name("Pause on drink")
            .description("Pauses Crystal Aura when drinking.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> minePauseSetting = pauseSettings.add(new BoolSetting.Builder()
            .name("Pause on mine")
            .description("Pauses Crystal Aura when mining.")
            .defaultValue(false)
            .build()
    );

    // Render

    private final Setting<Boolean> renderSwingSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing")
            .description("Swing your hand client side when placing or interacting.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render a block overlay over the block the crystals are being placed on.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderBreakSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Break")
            .description("Render a block overlay over the block the crystals are broken on.")
            .defaultValue(false)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("Determine how the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the block overlay.")
            .defaultValue(new SettingColor(255, 255, 255, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the block overlay.")
            .defaultValue(new SettingColor(255, 255, 255))
            .build()
    );

    private final Setting<Boolean> renderDamageTextSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Damage")
            .description("Render crystal damage text in the block overlay.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> damageTextScaleSetting = renderSettings.add(new DoubleSetting.Builder()
            .name("Damage scale")
            .description("How big the damage text should be.")
            .defaultValue(1.25)
            .min(1)
            .sliderRange(1, 4)
            .visible(renderDamageTextSetting::get)
            .build()
    );

    private final Setting<ColorMode> textColorModeSetting = renderSettings.add(new EnumSetting.Builder<ColorMode>()
            .name("Text color mode")
            .description("Determine the color mode of the damage text.")
            .defaultValue(ColorMode.Damage)
            .build()
    );

    private final Setting<SettingColor> textColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Text color")
            .description("The damage text color of the block overlay.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .visible(() -> textColorModeSetting.get() == ColorMode.Static)
            .build()
    );

    private final Setting<Integer> renderTimeSetting = renderSettings.add(new IntSetting.Builder()
            .name("Render time")
            .description("How long to render for.")
            .defaultValue(10)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Integer> renderBreakTimeSetting = renderSettings.add(new IntSetting.Builder()
            .name("Break time")
            .description("How long to render breaking for.")
            .defaultValue(13)
            .min(0)
            .sliderRange(0, 20)
            .visible(renderBreakSetting::get)
            .build()
    );

    public CrystalAura(Category category) {
        super(category, "Crystal Aura", "Automatically places and attacks crystals.");
    }

    @Override
    public void onEnable() {
        breakTimer = 0;
        placeTimer = 0;
        ticksPassed = 0;

        raycastContext = new RaycastContext(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);

        placing = false;
        placingTimer = 0;

        attacks = 0;

        serverYaw = mc.player.getYaw();

        bestTargetDamage = 0;
        bestTargetTimer = 0;

        lastRotationTimer = getLastRotationStopDelay();

        renderTimer = 0;
        breakRenderTimer = 0;
    }

    @Override
    public void onDisable() {
        targets.clear();

        placedCrystals.clear();

        attemptedBreaks.clear();
        waitingToExplode.clear();

        removed.clear();

        bestTarget = null;
    }

    private int getLastRotationStopDelay() {
        return Math.max(10, placeDelaySetting.get() / 2 + breakDelaySetting.get() / 2 + 10);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPreTick(TickEvent.Pre event) {
        didRotateThisTick = false;

        lastRotationTimer++;

        if (placing) {
            if (placingTimer > 0) {
                placingTimer--;
            } else {
                placing = false;
            }
        }

        if (ticksPassed < 20) {
            ticksPassed++;
        } else {
            ticksPassed = 0;
            attacks = 0;
        }

        if (bestTargetTimer > 0) {
            bestTargetTimer--;
        }

        bestTargetDamage = 0;

        if (breakTimer > 0) {
            breakTimer--;
        }

        if (placeTimer > 0) {
            placeTimer--;
        }

        if (switchTimer > 0) {
            switchTimer--;
        }

        if (renderTimer > 0) {
            renderTimer--;
        }

        if (breakRenderTimer > 0) {
            breakRenderTimer--;
        }

        for (IntIterator iterator = waitingToExplode.keySet().iterator(); iterator.hasNext();) {
            int id = iterator.nextInt();
            int ticks = waitingToExplode.get(id);
            if (ticks > 3) {
                iterator.remove();
                removed.remove(id);
            } else {
                waitingToExplode.put(id, ticks + 1);
            }
        }

        if (PlayerUtils.shouldPause(minePauseSetting.get(), eatPauseSetting.get(), drinkPauseSetting.get())) {
            return;
        }

        ((IVec3d) playerEyePos).set(mc.player.getPos().x, mc.player.getPos().y + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getPos().z);

        findTargets();

        if (targets.size() > 0) {
            if (!didRotateThisTick) {
                doBreak();
            }

            if (!didRotateThisTick) {
                doPlace();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST - 666)
    private void onPreTickLast(TickEvent.Pre event) {
        if (rotateSetting.get() && lastRotationTimer < getLastRotationStopDelay() && !didRotateThisTick) {
            Rotations.rotate(isLastRotationPos ? Rotations.getYaw(lastRotationPos) : lastYaw, isLastRotationPos ? Rotations.getPitch(lastRotationPos) : lastPitch, -100, null);
        }
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent event) {
        if (!(event.entity instanceof EndCrystalEntity)) {
            return;
        }

        if (placing && event.entity.getBlockPos().equals(placingCrystalBlockPos)) {
            placing = false;
            placingTimer = 0;
            placedCrystals.add(event.entity.getId());
        }

        if (fastBreakSetting.get() && !didRotateThisTick && attacks < attackFrequencySetting.get()) {
            if (getBreakDamage(event.entity, true) > minDamageSetting.get()) {
                doBreak(event.entity);
            }
        }
    }

    @EventHandler
    private void onEntityRemoved(EntityRemovedEvent event) {
        if (event.entity instanceof EndCrystalEntity) {
            placedCrystals.remove(event.entity.getId());
            removed.remove(event.entity.getId());
            waitingToExplode.remove(event.entity.getId());
        }
    }

    private void setRotation(boolean isPos, Vec3d pos, double yaw, double pitch) {
        didRotateThisTick = true;

        isLastRotationPos = isPos;

        if (isPos) {
            ((IVec3d) lastRotationPos).set(pos.x, pos.y, pos.z);
        } else {
            lastYaw = yaw;
            lastPitch = pitch;
        }

        lastRotationTimer = 0;
    }

    // Break

    private void doBreak() {
        if (!doBreakSetting.get() || breakTimer > 0 || switchTimer > 0 || attacks >= attackFrequencySetting.get()) {
            return;
        }

        double bestDamage = 0;
        Entity crystal = null;
        for (Entity entity : mc.world.getEntities()) {
            double damage = getBreakDamage(entity, true);
            if (damage > bestDamage) {
                bestDamage = damage;
                crystal = entity;
            }
        }

        if (crystal != null) {
            doBreak(crystal);
        }
    }

    private double getBreakDamage(Entity entity, boolean checkCrystalAge) {
        if (!(entity instanceof EndCrystalEntity)) {
            return 0;
        }

        if (onlyBreakOwnSetting.get() && !placedCrystals.contains(entity.getId())) {
            return 0;
        }

        if (removed.contains(entity.getId())) {
            return 0;
        }

        if (attemptedBreaks.get(entity.getId()) > breakAttemptsSetting.get()) {
            return 0;
        }

        if (checkCrystalAge && entity.age < ticksExistedSetting.get()) {
            return 0;
        }

        if (isOutOfRange(entity.getPos(), entity.getBlockPos(), false)) {
            return 0;
        }

        blockPos.set(entity.getBlockPos()).move(0, -1, 0);
        double selfDamage = DamageUtils.crystalDamage(mc.player, entity.getPos(), predictMovementSetting.get(), blockPos, ignoreTerrainSetting.get());
        if (selfDamage > maxDamageSetting.get() || (antiSuicideSetting.get() && selfDamage >= EntityUtils.getTotalHealth(mc.player))) {
            return 0;
        }

        double damage = getDamageToTargets(entity.getPos(), blockPos, true, false);
        boolean shouldFacePlace = shouldFacePlace();
        double minimumDamage = Math.min(minDamageSetting.get(), shouldFacePlace ? 1.5 : minDamageSetting.get());
        if (damage < minimumDamage) {
            return 0;
        }

        return damage;
    }

    private void doBreak(Entity crystal) {
        if (antiWeaknessSetting.get()) {
            StatusEffectInstance weakness = mc.player.getStatusEffect(StatusEffects.WEAKNESS);
            StatusEffectInstance strength = mc.player.getStatusEffect(StatusEffects.STRENGTH);
            if (weakness != null && (strength == null || strength.getAmplifier() <= weakness.getAmplifier())) {
                if (!isValidWeaknessItem(mc.player.getMainHandStack())) {
                    if (!InvUtils.swap(InvUtils.findInHotbar(this::isValidWeaknessItem).slot(), false)) {
                        return;
                    }

                    switchTimer = 1;
                    return;
                }
            }
        }

        boolean attacked = true;
        if (rotateSetting.get()) {
            double yaw = Rotations.getYaw(crystal);
            double pitch = Rotations.getPitch(crystal, Target.Feet);
            if (doYawSteps(yaw, pitch)) {
                setRotation(true, crystal.getPos(), 0, 0);
                Rotations.rotate(yaw, pitch, 50, () -> attackCrystal(crystal));

                breakTimer = breakDelaySetting.get();
            } else {
                attacked = false;
            }
        } else {
            attackCrystal(crystal);
            breakTimer = breakDelaySetting.get();
        }

        if (attacked) {
            removed.add(crystal.getId());
            attemptedBreaks.put(crystal.getId(), attemptedBreaks.get(crystal.getId()) + 1);
            waitingToExplode.put(crystal.getId(), 0);

            breakRenderPos.set(crystal.getBlockPos().down());
            breakRenderTimer = renderBreakTimeSetting.get();
        }
    }

    private boolean isValidWeaknessItem(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ToolItem) || itemStack.getItem() instanceof HoeItem) {
            return false;
        }

        ToolMaterial material = ((ToolItem) itemStack.getItem()).getMaterial();
        return material == ToolMaterials.DIAMOND || material == ToolMaterials.NETHERITE;
    }

    private void attackCrystal(Entity entity) {
        mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(entity, mc.player.isSneaking()));

        Hand hand = InvUtils.findInHotbar(Items.END_CRYSTAL).getHand();
        if (hand == null) {
            hand = Hand.MAIN_HAND;
        }

        if (renderSwingSetting.get()) {
            mc.player.swingHand(hand);
        } else {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
        }

        attacks++;
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (event.packet instanceof UpdateSelectedSlotC2SPacket) {
            switchTimer = switchDelaySetting.get();
        }
    }

    private void doPlace() {
        if (!doPlaceSetting.get() || placeTimer > 0) {
            return;
        }

        if (!InvUtils.testInHotbar(Items.END_CRYSTAL)) {
            return;
        }

        if (autoSwitchSetting.get() == AutoSwitchMode.None && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL && mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }

        for (Entity entity : mc.world.getEntities()) {
            if (getBreakDamage(entity, false) > 0) {
                return;
            }
        }

        AtomicDouble bestDamage = new AtomicDouble(0);
        AtomicReference<BlockPos.Mutable> bestBlockPos = new AtomicReference<>(new BlockPos.Mutable());
        AtomicBoolean isSupport = new AtomicBoolean(supportSetting.get() != SupportMode.Disabled);

        BlockIterator.register((int) Math.ceil(placeRangeSetting.get()), (int) Math.ceil(placeRangeSetting.get()), (bp, blockState) -> {
            boolean hasBlock = blockState.isOf(Blocks.BEDROCK) || blockState.isOf(Blocks.OBSIDIAN);
            if (!hasBlock && (!isSupport.get() || !blockState.getMaterial().isReplaceable())) {
                return;
            }

            blockPos.set(bp.getX(), bp.getY() + 1, bp.getZ());
            if (!mc.world.getBlockState(blockPos).isAir()) {
                return;
            }

            if (placement112Setting.get()) {
                blockPos.move(0, 1, 0);
                if (!mc.world.getBlockState(blockPos).isAir()) {
                    return;
                }
            }

            ((IVec3d) vec3d).set(bp.getX() + 0.5, bp.getY() + 1, bp.getZ() + 0.5);
            blockPos.set(bp).move(0, 1, 0);
            if (isOutOfRange(vec3d, blockPos, true)) {
                return;
            }

            double selfDamage = DamageUtils.crystalDamage(mc.player, vec3d, predictMovementSetting.get(), bp, ignoreTerrainSetting.get());
            if (selfDamage > maxDamageSetting.get() || (antiSuicideSetting.get() && selfDamage >= EntityUtils.getTotalHealth(mc.player))) {
                return;
            }

            double damage = getDamageToTargets(vec3d, bp, false, !hasBlock && supportSetting.get() == SupportMode.Fast);
            boolean shouldFacePlace = shouldFacePlace();
            double minimumDamage = Math.min(minDamageSetting.get(), shouldFacePlace ? 1.5 : minDamageSetting.get());
            if (damage < minimumDamage) {
                return;
            }

            double x = bp.getX();
            double y = bp.getY() + 1;
            double z = bp.getZ();
            ((IBox) box).set(x, y, z, x + 1, y + (placement112Setting.get() ? 1 : 2), z + 1);
            if (intersectsWithEntities(box)) {
                return;
            }

            if (damage > bestDamage.get() || (isSupport.get() && hasBlock)) {
                bestDamage.set(damage);
                bestBlockPos.get().set(bp);
            }

            if (hasBlock) {
                isSupport.set(false);
            }
        });

        BlockIterator.after(() -> {
            if (bestDamage.get() == 0) {
                return;
            }

            BlockHitResult result = getPlaceInfo(bestBlockPos.get());
            ((IVec3d) vec3d).set(result.getBlockPos().getX() + 0.5 + result.getSide().getVector().getX() * 1.0 / 2.0, result.getBlockPos().getY() + 0.5 + result.getSide().getVector().getY() * 1.0 / 2.0, result.getBlockPos().getZ() + 0.5 + result.getSide().getVector().getZ() * 1.0 / 2.0);
            if (rotateSetting.get()) {
                double yaw = Rotations.getYaw(vec3d);
                double pitch = Rotations.getPitch(vec3d);
                if (yawStepModeSetting.get() == YawStepMode.Break || doYawSteps(yaw, pitch)) {
                    setRotation(true, vec3d, 0, 0);
                    Rotations.rotate(yaw, pitch, 50, () -> placeCrystal(result, bestDamage.get(), isSupport.get() ? bestBlockPos.get() : null));

                    placeTimer += placeDelaySetting.get();
                }
            } else {
                placeCrystal(result, bestDamage.get(), isSupport.get() ? bestBlockPos.get() : null);
                placeTimer += placeDelaySetting.get();
            }
        });
    }

    private BlockHitResult getPlaceInfo(BlockPos blockPos) {
        ((IVec3d) vec3d).set(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        for (Direction side : Direction.values()) {
            ((IVec3d) vec3dRayTraceEnd).set(blockPos.getX() + 0.5 + side.getVector().getX() * 0.5, blockPos.getY() + 0.5 + side.getVector().getY() * 0.5, blockPos.getZ() + 0.5 + side.getVector().getZ() * 0.5);
            ((IRaycastContext) raycastContext).set(vec3d, vec3dRayTraceEnd, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
            BlockHitResult result = mc.world.raycast(raycastContext);
            if (result != null && result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(blockPos)) {
                return result;
            }
        }

        Direction side = blockPos.getY() > vec3d.y ? Direction.DOWN : Direction.UP;
        return new BlockHitResult(vec3d, side, blockPos, false);
    }

    private void placeCrystal(BlockHitResult result, double damage, BlockPos supportBlock) {
        Item targetItem = supportBlock == null ? Items.END_CRYSTAL : Items.OBSIDIAN;
        FindItemResult item = InvUtils.findInHotbar(targetItem);
        if (!item.found()) {
            return;
        }

        int prevSlot = mc.player.getInventory().selectedSlot;
        if (autoSwitchSetting.get() != AutoSwitchMode.None && !item.isOffhand()) {
            InvUtils.swap(item.slot(), false);
        }

        Hand hand = item.getHand();
        if (hand == null) {
            return;
        }

        if (supportBlock == null) {
            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, result, 0));

            if (renderSwingSetting.get()) {
                mc.player.swingHand(hand);
            } else {
                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
            }

            placing = true;
            placingTimer = 4;
            placingCrystalBlockPos.set(result.getBlockPos()).move(0, 1, 0);

            renderTimer = renderTimeSetting.get();
            renderPos.set(result.getBlockPos());
            renderDamage = damage;
        } else {
            BlockUtils.place(supportBlock, item, false, 0, renderSwingSetting.get(), true, false);
            placeTimer += supportDelaySetting.get();
            if (supportDelaySetting.get() == 0) {
                placeCrystal(result, damage, null);
            }
        }

        if (autoSwitchSetting.get() == AutoSwitchMode.Silent) {
            InvUtils.swap(prevSlot, false);
        }
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (event.packet instanceof PlayerMoveC2SPacket) {
            serverYaw = ((PlayerMoveC2SPacket) event.packet).getYaw((float) serverYaw);
        }
    }

    public boolean doYawSteps(double targetYaw, double targetPitch) {
        targetYaw = MathHelper.wrapDegrees(targetYaw) + 180;
        double serverYaw = MathHelper.wrapDegrees(this.serverYaw) + 180;
        if (distanceBetweenAngles(serverYaw, targetYaw) <= yawStepsSetting.get()) {
            return true;
        }

        double delta = Math.abs(targetYaw - serverYaw);
        double yaw = this.serverYaw;
        if (serverYaw < targetYaw) {
            if (delta < 180) {
                yaw += yawStepsSetting.get();
            } else {
                yaw -= yawStepsSetting.get();
            }
        } else {
            if (delta < 180) {
                yaw -= yawStepsSetting.get();
            } else {
                yaw += yawStepsSetting.get();
            }
        }

        setRotation(false, null, yaw, targetPitch);
        Rotations.rotate(yaw, targetPitch, -100, null); // Priority -100 so it sends the packet as the last one, im pretty sure it doesn't matter but idc
        return false;
    }

    private static double distanceBetweenAngles(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }

    private boolean shouldFacePlace() {
        if (!facePlaceSetting.get()) {
            return false;
        }

        if (forceFacePlaceSetting.get().isPressed()) {
            return true;
        }

        for (LivingEntity target : targets) {
            if (EntityUtils.getTotalHealth(target) <= facePlaceHealthSetting.get()) {
                return true;
            }

            for (ItemStack itemStack : target.getArmorItems()) {
                if (itemStack == null || itemStack.isEmpty()) {
                    if (facePlaceArmorSetting.get()) {
                        return true;
                    }
                } else if ((double) (itemStack.getMaxDamage() - itemStack.getDamage()) / itemStack.getMaxDamage() * 100 <= facePlaceDurabilitySetting.get()) {
                    return true;
                }
            }
        }

        return false;
    }

    // Others

    private boolean isOutOfRange(Vec3d vec3d, BlockPos blockPos, boolean place) {
        ((IRaycastContext) raycastContext).set(playerEyePos, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
        BlockHitResult result = mc.world.raycast(raycastContext);
        if (result == null || !result.getBlockPos().equals(blockPos)) {
            return !PlayerUtils.isWithin(vec3d, (place ? placeWallsRangeSetting : breakWallsRangeSetting).get());
        }

        return !PlayerUtils.isWithin(vec3d, (place ? placeRangeSetting : breakRangeSetting).get());
    }

    private LivingEntity getNearestTarget() {
        LivingEntity nearestTarget = null;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity target : targets) {
            double distance = target.squaredDistanceTo(mc.player);
            if (distance < nearestDistance) {
                nearestTarget = target;
                nearestDistance = distance;
            }
        }

        return nearestTarget;
    }

    private double getDamageToTargets(Vec3d vec3d, BlockPos obsidianPos, boolean breaking, boolean fast) {
        double damage = 0;
        if (fast) {
            LivingEntity target = getNearestTarget();
            if (!(smartDelaySetting.get() && breaking && target.hurtTime > 0)) {
                damage = DamageUtils.crystalDamage(target, vec3d, predictMovementSetting.get(), obsidianPos, ignoreTerrainSetting.get());
            }
        } else {
            for (LivingEntity target : targets) {
                if (smartDelaySetting.get() && breaking && target.hurtTime > 0) {
                    continue;
                }

                double damage1 = DamageUtils.crystalDamage(target, vec3d, predictMovementSetting.get(), obsidianPos, ignoreTerrainSetting.get());
                if (damage1 > bestTargetDamage) {
                    bestTarget = target;
                    bestTargetDamage = damage1;
                    bestTargetTimer = 10;
                }

                damage += damage1;
            }
        }

        return damage;
    }

    @Override
    public String getInfoString() {
        String name = null;
        if (bestTarget != null) {
            if (bestTarget instanceof PlayerEntity bestPlayerTarget) {
                name = bestTargetTimer > 0 ? bestPlayerTarget.getGameProfile().getName() : null;
            } else {
                name = bestTargetTimer > 0 ? bestTarget.getType().getName().getString() : null;
            }
        }

        return name;
    }

    private void findTargets() {
        targets.clear();

        // Players
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (!entitiesSetting.get().containsKey(EntityType.PLAYER) || player.getAbilities().creativeMode || player == mc.player) {
                continue;
            }

            if (!player.isDead() && player.isAlive() && Friends.get().shouldAttack(player) && Enemies.get().shouldAttack(player) && player.distanceTo(mc.player) <= targetRangeSetting.get()) {
                targets.add(player);
            }
        }

        // Entities
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity || !entitiesSetting.get().containsKey(entity.getType())) {
                continue;
            }

            if (entity instanceof LivingEntity livingEntity && entity.isAlive() && entity.distanceTo(mc.player) <= targetRangeSetting.get()) {
                targets.add(livingEntity);
            }
        }
    }

    private boolean intersectsWithEntities(Box box) {
        return EntityUtils.intersectsWithEntity(box, entity -> !entity.isSpectator() && !removed.contains(entity.getId()));
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (renderTimer > 0 && renderSetting.get()) {
            event.renderer.box(renderPos, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        }

        if (breakRenderTimer > 0 && renderBreakSetting.get() && !mc.world.getBlockState(breakRenderPos).isAir()) {
            int preSideA = sideColorSetting.get().a;
            sideColorSetting.get().a -= 20;
            sideColorSetting.get().validate();

            int preLineA = lineColorSetting.get().a;
            lineColorSetting.get().a -= 20;
            lineColorSetting.get().validate();

            event.renderer.box(breakRenderPos, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);

            sideColorSetting.get().a = preSideA;
            lineColorSetting.get().a = preLineA;
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (!renderSetting.get() || renderTimer <= 0 || !renderDamageTextSetting.get()) {
            return;
        }

        Theme theme = Systems.get(Themes.class).getTheme();
        vec3.set(renderPos.getX() + 0.5, renderPos.getY() + 0.5, renderPos.getZ() + 0.5);
        if (NametagUtils.to2D(vec3, damageTextScaleSetting.get())) {
            NametagUtils.begin(vec3);
            theme.textRenderer().begin(1, false, true);

            String text = String.format("%.1f", renderDamage);
            double textWidth = theme.textRenderer().getWidth(text) / 2;
            theme.textRenderer().render(text, -textWidth, 0, getDamageTextColor(renderDamage), theme.fontShadow());

            theme.textRenderer().end();
            NametagUtils.end();
        }
    }

    private Color getDamageTextColor(double damage) {
        if (textColorModeSetting.get() == ColorMode.Static) {
            return textColorSetting.get();
        } else if (damage < 7) {
            return Color.RED;
        } else if (damage < 13) {
            return Color.ORANGE;
        } else if (damage < 17) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }

    public enum YawStepMode {
        Break("Break"),
        All("All");

        private final String name;

        YawStepMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum AutoSwitchMode {
        Normal("Normal"),
        Silent("Silent"),
        None("None");

        private final String name;

        AutoSwitchMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum SupportMode {
        Disabled("Disabled"),
        Accurate("Accurate"),
        Fast("Fast");

        private final String name;

        SupportMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum ColorMode {
        Damage("Damage"),
        Static("Static");

        private final String name;

        ColorMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}