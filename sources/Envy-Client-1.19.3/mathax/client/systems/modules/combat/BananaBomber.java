package mathax.client.systems.modules.combat;

import com.google.common.util.concurrent.AtomicDouble;
import it.unimi.dsi.fastutil.ints.*;
import mathax.client.eventbus.EventHandler;
import mathax.client.eventbus.EventPriority;
import mathax.client.events.entity.EntityAddedEvent;
import mathax.client.events.entity.EntityRemovedEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.render.Render2DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixininterface.IBox;
import mathax.client.mixininterface.IRaycastContext;
import mathax.client.mixininterface.IVec3d;
import mathax.client.renderer.ShapeMode;
import mathax.client.renderer.text.TextRenderer;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.BananaUtils.BDamageUtils;
import mathax.client.utils.BananaUtils.BPlayerUtils;
import mathax.client.utils.BananaUtils.CrystalUtils;
import mathax.client.utils.BananaUtils.TimerUtils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.Target;
import mathax.client.utils.misc.KeyBind;
import mathax.client.utils.misc.Vec3;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.render.NametagUtils;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.utils.world.BlockIterator;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BananaBomber extends Module {
    public enum YawStepMode {
        Break,
        All,
    }

    public enum AutoSwitchMode {
        Normal,
        Silent,
        None
    }

    public enum SupportMode {
        Disabled,
        Accurate,
        Fast
    }

    public enum CancelCrystalMode {
        Hit,
        NoDesync
    }

    public enum DamageIgnore {
        Always,
        WhileSafe,
        Never
    }

    public enum SlowMode {
        Delay,
        Age,
        Both
    }

    public enum SelfPopIgnore {
        Place,
        Break,
        Both
    }

    public enum PopPause {
        Place,
        Break,
        Both
    }

    public enum RenderMode {
        Normal,
        Fade,
        None
    }

    public enum TrapType {
        BothTrapped,
        AnyTrapped,
        TopTrapped,
        FaceTrapped,
        Always
    }

    public enum FacePlaceMode {
        Normal,
        Slow,
        None
    }


    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgFacePlace = settings.createGroup("Face Place");
    private final SettingGroup sgSurround = settings.createGroup("Surround");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgFastBreak = settings.createGroup("Fast Break");
    private final SettingGroup sgChainPop = settings.createGroup("Chain Pop");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRender = settings.createGroup("Render");


    // General
    public final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("debug-mode")
        .description("Informs you what the CA is doing.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("target-range")
        .description("Range in which to target players.")
        .defaultValue(10)
        .min(0)
        .sliderRange(0,20)
        .build()
    );

    public final Setting<Double> explosionRadiusToTarget = sgGeneral.add(new DoubleSetting.Builder()
        .name("place-radius")
        .description("How far crystals can be placed from the target.")
        .defaultValue(12)
        .range(1,12)
        .sliderRange(1,12)
        .build()
    );

    private final Setting<Boolean> predictMovement = sgGeneral.add(new BoolSetting.Builder()
        .name("predict-movement")
        .description("Predict the target's movement.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ignoreTerrain = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-terrain")
        .description("Ignore blocks if they can be blown up by crystals.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> fullBlocks = sgGeneral.add(new BoolSetting.Builder()
        .name("full-blocks")
        .description("Treat anvils and ender chests as full blocks.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> hideSwings = sgGeneral.add(new BoolSetting.Builder()
        .name("hide-swings")
        .description("Whether to send hand swing packets to the server.")
        .defaultValue(false)
        .build()
    );

    private final Setting<AutoSwitchMode> autoSwitch = sgGeneral.add(new EnumSetting.Builder<AutoSwitchMode>()
        .name("auto-switch")
        .description("Switches to crystals in your hotbar once a target is found.")
        .defaultValue(AutoSwitchMode.Normal)
        .build()
    );

    private final Setting<Boolean> noGapSwitch = sgGeneral.add(new BoolSetting.Builder()
        .name("No Gap Switch")
        .description("Disables normal auto switch when you are holding a gap.")
        .defaultValue(true)
        .visible(() -> autoSwitch.get() == AutoSwitchMode.Normal)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotates server-side towards the crystals being hit/placed.")
        .defaultValue(false)
        .build()
    );

    private final Setting<YawStepMode> yawStepMode = sgGeneral.add(new EnumSetting.Builder<YawStepMode>()
        .name("yaw-steps-mode")
        .description("When to run the yaw steps check.")
        .defaultValue(YawStepMode.Break)
        .visible(rotate::get)
        .build()
    );

    private final Setting<Double> yawSteps = sgGeneral.add(new DoubleSetting.Builder()
        .name("yaw-steps")
        .description("Maximum degrees to rotate in one tick.")
        .defaultValue(180)
        .range(1,180)
        .sliderRange(1,180)
        .visible(rotate::get)
        .build()
    );


    // Place
    public final Setting<Boolean> doPlace = sgPlace.add(new BoolSetting.Builder()
        .name("place")
        .description("If the CA should place crystals.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Double> PminDamage = sgPlace.add(new DoubleSetting.Builder()
        .name("min-place-damage")
        .description("Minimum place damage the crystal needs to deal to your target.")
        .defaultValue(6)
        .min(0)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<DamageIgnore> PDamageIgnore = sgPlace.add(new EnumSetting.Builder<DamageIgnore>()
        .name("ignore-self-damage")
        .description("Whether to ignore damage to yourself.")
        .defaultValue(DamageIgnore.Never)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<Double> PmaxDamage = sgPlace.add(new DoubleSetting.Builder()
        .name("max-place-damage")
        .description("Maximum place damage crystals can deal to yourself.")
        .defaultValue(6)
        .range(0,36)
        .sliderRange(0,36)
        .visible(() -> PDamageIgnore.get() != DamageIgnore.Always && doPlace.get())
        .build()
    );

    public final Setting<Boolean> PantiSuicide = sgPlace.add(new BoolSetting.Builder()
        .name("anti-suicide-place")
        .description("Will not place crystals if they will pop / kill you.")
        .defaultValue(true)
        .visible(() -> PDamageIgnore.get() != DamageIgnore.Always && doPlace.get())
        .build()
    );

    public final Setting<Integer> placeDelay = sgPlace.add(new IntSetting.Builder()
        .name("place-delay")
        .description("The delay in ticks to wait to place a crystal after it's exploded.")
        .defaultValue(0)
        .range(0,20)
        .sliderRange(0,20)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<Double> placeRange = sgPlace.add(new DoubleSetting.Builder()
        .name("place-range")
        .description("How far away you can place crystals.")
        .defaultValue(4)
        .range(0,6)
        .sliderRange(0,6)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<Double> placeWallsRange = sgPlace.add(new DoubleSetting.Builder()
        .name("place-walls-range")
        .description("How far away you can place crystals through blocks.")
        .defaultValue(4)
        .range(0,6)
        .sliderRange(0,6)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<Boolean> placement112 = sgPlace.add(new BoolSetting.Builder()
        .name("1.12-placement")
        .description("Uses 1.12 crystal placement.")
        .defaultValue(false)
        .visible(doPlace::get)
        .build()
    );

    public final Setting<Boolean> smallBox = sgPlace.add(new BoolSetting.Builder()
        .name("small-box")
        .description("Allows you to place in 1x1x1 box instead of 1x2x1 boxes.")
        .defaultValue(false)
        .visible(doPlace::get)
        .build()
    );

    private final Setting<SupportMode> support = sgPlace.add(new EnumSetting.Builder<SupportMode>()
        .name("support")
        .description("Places a support block in air if no other position have been found.")
        .defaultValue(SupportMode.Disabled)
        .visible(doPlace::get)
        .build()
    );

    private final Setting<Integer> supportDelay = sgPlace.add(new IntSetting.Builder()
        .name("support-delay")
        .description("Delay in ticks after placing support block.")
        .defaultValue(1)
        .min(0)
        .visible(() -> support.get() != SupportMode.Disabled && doPlace.get())
        .build()
    );


    // Face place
    public final Setting<Boolean> facePlace = sgFacePlace.add(new BoolSetting.Builder()
        .name("face-place")
        .description("Will place crystals against the enemy's face.")
        .defaultValue(true)
        .build()
    );

    public final Setting<KeyBind> forceFacePlace = sgFacePlace.add(new KeyBindSetting.Builder()
        .name("force-face-place")
        .description("Starts face place when this button is pressed.")
        .defaultValue(KeyBind.none())
        .build()
    );

    public final Setting<Boolean> slowFacePlace = sgFacePlace.add(new BoolSetting.Builder()
        .name("slow-place")
        .description("Place slower while face placing to reserve crystals.")
        .defaultValue(false)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<SlowMode> slowFPMode = sgFacePlace.add(new EnumSetting.Builder<SlowMode>()
        .name("slow-FP-mode")
        .description("How to measure the delay for slow face place.")
        .defaultValue(SlowMode.Delay)
        .visible(() -> facePlace.get() && slowFacePlace.get())
        .build()
    );

    public final Setting<Integer> slowFPDelay = sgFacePlace.add(new IntSetting.Builder()
        .name("slow-FP-delay")
        .description("How long in ticks to wait to break a crystal.")
        .defaultValue(10)
        .range(0,20)
        .sliderRange(0,20)
        .visible(() -> facePlace.get() && slowFacePlace.get() && slowFPMode.get() != SlowMode.Age)
        .build()
    );

    public final Setting<Integer> slowFPAge = sgFacePlace.add(new IntSetting.Builder()
        .name("slow-FP-age")
        .description("How old a crystal must be server-side in ticks to be broken.")
        .defaultValue(3)
        .range(0,20)
        .sliderRange(0,20)
        .visible(() -> facePlace.get() && slowFacePlace.get() && slowFPMode.get() != SlowMode.Delay)
        .build()
    );

    public final Setting<Boolean> surrHoldPause = sgFacePlace.add(new BoolSetting.Builder()
        .name("pause-on-hold")
        .description("Will pause face placing while surround hold is active.")
        .defaultValue(true)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<Boolean> KAPause = sgFacePlace.add(new BoolSetting.Builder()
        .name("pause-on-KA")
        .description("Will pause face placing when KA is active.")
        .defaultValue(true)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<Boolean> CevPause = sgFacePlace.add(new BoolSetting.Builder()
        .name("pause-on-cev")
        .description("Will pause face placing when Cev Breaker is active.")
        .defaultValue(true)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<Double> facePlaceHealth = sgFacePlace.add(new DoubleSetting.Builder()
        .name("face-place-health")
        .description("The health the target has to be at to start face placing.")
        .defaultValue(6)
        .range(0,36)
        .sliderRange(0,36)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<Double> facePlaceDurability = sgFacePlace.add(new DoubleSetting.Builder()
        .name("face-place-durability")
        .description("The durability threshold percentage to be able to face-place.")
        .defaultValue(10)
        .range(1,100)
        .sliderRange(1,100)
        .visible(facePlace::get)
        .build()
    );

    public final Setting<Boolean> facePlaceArmor = sgFacePlace.add(new BoolSetting.Builder()
        .name("missing-armor")
        .description("Automatically starts face placing when a target misses a piece of armor.")
        .defaultValue(false)
        .visible(facePlace::get)
        .build()
    );


    // Surround
    public final Setting<Boolean> burrowBreak = sgSurround.add(new BoolSetting.Builder()
        .name("burrow-break")
        .description("Will try to break target's burrow.")
        .defaultValue(false)
        .build()
    );

    public final Setting<KeyBind> forceBurrowBreak = sgSurround.add(new KeyBindSetting.Builder()
        .name("force-break")
        .description("Starts burrow breaking when this button is pressed.")
        .defaultValue(KeyBind.none())
        .build()
    );

    public final Setting<Integer> burrowBreakDelay = sgSurround.add(new IntSetting.Builder()
        .name("burrow-break-delay")
        .description("Place delay in ticks for burrow break.")
        .defaultValue(10)
        .range(0,20)
        .sliderRange(0,20)
        .visible(burrowBreak::get)
        .build()
    );

    public final Setting<TrapType> burrowBWhen = sgSurround.add(new EnumSetting.Builder<TrapType>()
        .name("burrow-break-when")
        .description("When to start burrow breaking.")
        .defaultValue(TrapType.Always)
        .visible(burrowBreak::get)
        .build()
    );

    public final Setting<Boolean> surroundBreak = sgSurround.add(new BoolSetting.Builder()
        .name("surround-break")
        .description("Will automatically places a crystal next to target's surround.")
        .defaultValue(false)
        .build()
    );

    public final Setting<KeyBind> forceSurroundBreak = sgSurround.add(new KeyBindSetting.Builder()
        .name("force-break")
        .description("Starts surround breaking when this button is pressed.")
        .defaultValue(KeyBind.none())
        .build()
    );

    public final Setting<TrapType> surroundBWhen = sgSurround.add(new EnumSetting.Builder<TrapType>()
        .name("surround-break-when")
        .description("When to start surround breaking.")
        .defaultValue(TrapType.FaceTrapped)
        .visible(surroundBreak::get)
        .build()
    );

    public final Setting<Integer> surroundBreakDelay = sgSurround.add(new IntSetting.Builder()
        .name("surround-break-delay")
        .description("Place delay in ticks for surround break.")
        .defaultValue(10)
        .range(0,20)
        .sliderRange(0,20)
        .visible(surroundBreak::get)
        .build()
    );

    public final Setting<Boolean> surroundBHorse = sgSurround.add(new BoolSetting.Builder()
        .name("horse")
        .description("Allow horse sides of the target's surround to be surround broken.")
        .defaultValue(false)
        .visible(surroundBreak::get)
        .build()
    );

    public final Setting<Boolean> surroundBDiagonal = sgSurround.add(new BoolSetting.Builder()
        .name("diagonal")
        .description("Allow diagonal sides of the target's surround to be surround broken.")
        .defaultValue(false)
        .visible(surroundBreak::get)
        .build()
    );

    public final Setting<Boolean> surroundHold = sgSurround.add(new BoolSetting.Builder()
        .name("surround-hold")
        .description("Break crystals slower to make them harder to surround over.")
        .defaultValue(false)
        .build()
    );

    public final Setting<TrapType> surroundHWhen = sgSurround.add(new EnumSetting.Builder<TrapType>()
        .name("surround-hold-when")
        .description("When to start surround holding.")
        .defaultValue(TrapType.AnyTrapped)
        .visible(surroundHold::get)
        .build()
    );

    public final Setting<SlowMode> surroundHoldMode = sgSurround.add(new EnumSetting.Builder<SlowMode>()
        .name("surround-hold-mode")
        .description("Timing to use for surround hold.")
        .defaultValue(SlowMode.Both)
        .visible(surroundHold::get)
        .build()
    );

    public final Setting<Integer> surroundHoldDelay = sgSurround.add(new IntSetting.Builder()
        .name("surround-hold-delay")
        .description("The delay in ticks to wait to break a crystal for surround hold.")
        .defaultValue(10)
        .min(0)
        .sliderRange(0,15)
        .visible(() -> surroundHold.get() && surroundHoldMode.get() != SlowMode.Age)
        .build()
    );

    public final Setting<Integer> surroundHoldAge = sgSurround.add(new IntSetting.Builder()
        .name("surround-hold-age")
        .description("Crystal age for surround hold (to prevent unnecessary attacks when people are around.")
        .defaultValue(3)
        .min(0)
        .sliderRange(0,15)
        .visible(() -> surroundHold.get() && surroundHoldMode.get() != SlowMode.Delay)
        .build()
    );


    // Break
    private final Setting<Boolean> doBreak = sgBreak.add(new BoolSetting.Builder()
        .name("break")
        .description("If the CA should break crystals.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> onlyBreakOwn = sgBreak.add(new BoolSetting.Builder()
        .name("only-own")
        .description("Only break crystals that you placed.")
        .defaultValue(false)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Boolean> antiWeakness = sgBreak.add(new BoolSetting.Builder()
        .name("anti-weakness")
        .description("Switches to tools with high enough damage to explode the crystal with weakness effect.")
        .defaultValue(true)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Double> BminDamage = sgBreak.add(new DoubleSetting.Builder()
        .name("min-break-damage")
        .description("Minimum break damage the crystal needs to deal to your target.")
        .defaultValue(6)
        .range(0,36)
        .sliderRange(0,36)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<DamageIgnore> BDamageIgnore = sgBreak.add(new EnumSetting.Builder<DamageIgnore>()
        .name("ignore-break-damage")
        .description("Whether to ignore self damage when breaking crystals.")
        .defaultValue(DamageIgnore.Never)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Double> BmaxDamage = sgBreak.add(new DoubleSetting.Builder()
        .name("max-break-damage")
        .description("Maximum break damage crystals can deal to yourself.")
        .defaultValue(6)
        .range(0,36)
        .sliderRange(0,36)
        .visible(() -> doBreak.get() && BDamageIgnore.get() != DamageIgnore.Always)
        .build()
    );

    public final Setting<Integer> attackFrequency = sgBreak.add(new IntSetting.Builder()
        .name("attack-frequency")
        .description("Maximum hits to do per second.")
        .defaultValue(25)
        .range(1,30)
        .sliderRange(1,30)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Boolean> BantiSuicide = sgBreak.add(new BoolSetting.Builder()
        .name("anti-suicide-break")
        .description("Will not break crystals if they will pop or kill you.")
        .defaultValue(true)
        .visible(() -> doBreak.get() && BDamageIgnore.get() != DamageIgnore.Always)
        .build()
    );

    public final Setting<CancelCrystalMode> cancelCrystalMode = sgBreak.add(new EnumSetting.Builder<CancelCrystalMode>()
        .name("cancel-mode")
        .description("Mode to use for the crystals to be removed from the world.")
        .defaultValue(CancelCrystalMode.NoDesync)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Integer> cancelTicks = sgBreak.add(new IntSetting.Builder()
        .name("cancel-ticks")
        .description("How long a tick should exist before being canceled.")
        .defaultValue(3)
        .range(1,5)
        .sliderRange(1,5)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Integer> breakDelay = sgBreak.add(new IntSetting.Builder()
        .name("break-delay")
        .description("The delay in ticks to wait to break a crystal after it's placed.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0,20)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Boolean> smartDelay = sgBreak.add(new BoolSetting.Builder()
        .name("smart-delay")
        .description("Only breaks crystals when the target can receive damage.")
        .defaultValue(false)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Integer> switchDelay = sgBreak.add(new IntSetting.Builder()
        .name("switch-delay")
        .description("The delay in ticks to wait to break a crystal after switching hotbar slot.")
        .defaultValue(0)
        .min(0)
        .sliderMax(10)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Double> breakRange = sgBreak.add(new DoubleSetting.Builder()
        .name("break-range")
        .description("Range in which to break crystals.")
        .defaultValue(4)
        .range(0,6)
        .sliderRange(0,6)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Double> breakWallsRange = sgBreak.add(new DoubleSetting.Builder()
        .name("break-walls-range")
        .description("Range in which to break crystals when behind blocks.")
        .defaultValue(4)
        .range(0,6)
        .sliderRange(0,6)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Boolean> attemptCheck = sgBreak.add(new BoolSetting.Builder()
        .name("break-attempt-check")
        .description("Whether to account for how many times you try to hit a crystal.")
        .defaultValue(false)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Integer> breakAttempts = sgBreak.add(new IntSetting.Builder()
        .name("break-attempts")
        .description("How many times to hit a crystal before finding a new placement.")
        .defaultValue(2)
        .sliderRange(0,5)
        .visible(() -> doBreak.get() && attemptCheck.get())
        .build()
    );

    public final Setting<Boolean> ageCheck = sgBreak.add(new BoolSetting.Builder()
        .name("crystal-age-check")
        .description("To check how old a crystal is server-side.")
        .defaultValue(true)
        .visible(doBreak::get)
        .build()
    );

    public final Setting<Integer> ticksExisted = sgBreak.add(new IntSetting.Builder()
        .name("ticks-existed")
        .description("Amount of ticks a crystal needs to have existed for it to be attacked.")
        .defaultValue(1)
        .min(1)
        .visible(() -> doBreak.get() && ageCheck.get())
        .build()
    );


    // Fast break
    public final Setting<Boolean> fastBreak = sgFastBreak.add(new BoolSetting.Builder()
        .name("fast-break")
        .description("Ignores break delay and tries to break the crystal as soon as it's spawned in the world.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> freqCheck = sgFastBreak.add(new BoolSetting.Builder()
        .name("frequency-check")
        .description("Will not try to fast break if your attack exceeds the attack frequency.")
        .defaultValue(true)
        .visible(fastBreak::get)
        .build()
    );

    public final Setting<Boolean> damageCheck = sgFastBreak.add(new BoolSetting.Builder()
        .name("damage-check")
        .description("Check if the crystal meets min damage first.")
        .defaultValue(true)
        .visible(fastBreak::get)
        .build()
    );

    public final Setting<Boolean> smartCheck = sgFastBreak.add(new BoolSetting.Builder()
        .name("smart-check")
        .description("Will not try to fast break for slow face place / surround hold.")
        .defaultValue(true)
        .visible(fastBreak::get)
        .build()
    );


    // Chain Pop
    public final Setting<Boolean> selfPopInvincibility = sgChainPop.add(new BoolSetting.Builder()
        .name("self-pop-invincibility")
        .description("Ignores self damage if you just popped.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Integer> selfPopInvincibilityTime = sgChainPop.add(new IntSetting.Builder()
        .name("self-pop-time")
        .description("How many millisecond to consider for self-pop invincibility")
        .defaultValue(300)
        .sliderRange(1,2000)
        .visible(selfPopInvincibility::get)
        .build()
    );

    public final Setting<SelfPopIgnore> selfPopIgnore = sgChainPop.add(new EnumSetting.Builder<SelfPopIgnore>()
        .name("self-pop-ignore")
        .description("What to ignore when you just popped.")
        .defaultValue(SelfPopIgnore.Break)
        .visible(selfPopInvincibility::get)
        .build()
    );

    public final Setting<Boolean> targetPopInvincibility = sgChainPop.add(new BoolSetting.Builder()
        .name("target-pop-invincibility")
        .description("Tries to pause certain actions when your enemy just popped.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Integer> targetPopInvincibilityTime = sgChainPop.add(new IntSetting.Builder()
        .name("target-pop-time")
        .description("How many milliseconds to consider for target-pop invincibility")
        .defaultValue(500)
        .sliderRange(1,2000)
        .visible(targetPopInvincibility::get)
        .build()
    );

    public final Setting<PopPause> popPause = sgChainPop.add(new EnumSetting.Builder<PopPause>()
        .name("pop-pause-mode")
        .description("What to pause when your enemy just popped.")
        .defaultValue(PopPause.Break)
        .visible(targetPopInvincibility::get)
        .build()
    );


    // Pause
    public final Setting<Double> pauseAtHealth = sgPause.add(new DoubleSetting.Builder()
        .name("pause-health")
        .description("Pauses when you go below a certain health.")
        .defaultValue(5)
        .min(0)
        .build()
    );

    private final Setting<Boolean> eatPause = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-eat")
        .description("Pauses Crystal Aura when eating.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> drinkPause = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-drink")
        .description("Pauses Crystal Aura when drinking.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> minePause = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-mine")
        .description("Pauses Crystal Aura when mining.")
        .defaultValue(false)
        .build()
    );


    // Render
    public final Setting<Boolean> renderSwing = sgRender.add(new BoolSetting.Builder()
        .name("render-swing")
        .description("Whether to swing your hand client side")
        .defaultValue(true)
        .build()
    );

    private final Setting<RenderMode> renderMode = sgRender.add(new EnumSetting.Builder<RenderMode>()
        .name("render-mode")
        .description("The mode to render in.")
        .defaultValue(RenderMode.Normal)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .visible(() -> renderMode.get() != RenderMode.None)
        .build()
    );

    private final Setting<Integer> fadeTime = sgRender.add(new IntSetting.Builder()
        .name("fade-time")
        .description("Tick duration for rendering placing.")
        .defaultValue(8)
        .range(0,20)
        .sliderRange(0,20)
        .visible(()-> (renderMode.get() == RenderMode.Fade))
        .build()
    );

    private final Setting<Integer> fadeAmount = sgRender.add(new IntSetting.Builder()
        .name("fade-amount")
        .description("How strong the fade should be.")
        .defaultValue(8)
        .range(0,100)
        .sliderRange(0,100)
        .visible(()-> (renderMode.get() == RenderMode.Fade))
        .build()
    );

    private final Setting<Integer> renderTime = sgRender.add(new IntSetting.Builder()
        .name("place-time")
        .description("How long to render placements for.")
        .defaultValue(10)
        .range(0,20)
        .sliderRange(0,20)
        .visible(() -> renderMode.get() == RenderMode.Normal)
        .build()
    );

    private final Setting<SettingColor> placeSideColor = sgRender.add(new ColorSetting.Builder()
        .name("place-side-color")
        .description("The side color of the block overlay.")
        .defaultValue(new SettingColor(255, 255, 255, 45))
        .visible(() -> renderMode.get() != RenderMode.None && shapeMode.get() != ShapeMode.Lines)
        .build()
    );

    private final Setting<SettingColor> placeLineColor = sgRender.add(new ColorSetting.Builder()
        .name("place-line-color")
        .description("The line color of the block overlay.")
        .defaultValue(new SettingColor(255, 255, 255, 255))
        .visible(() -> renderMode.get() != RenderMode.None && shapeMode.get() != ShapeMode.Sides)
        .build()
    );

    private final Setting<Boolean> renderBreak = sgRender.add(new BoolSetting.Builder()
        .name("break")
        .description("Renders a block overlay over the block the crystals are broken on.")
        .defaultValue(false)
        .visible(() -> renderMode.get() != RenderMode.None)
        .build()
    );

    private final Setting<Integer> renderBreakTime = sgRender.add(new IntSetting.Builder()
        .name("break-time")
        .description("How long to render breaking for.")
        .defaultValue(7)
        .range(0,20)
        .sliderRange(0,20)
        .visible(()-> renderMode.get() != RenderMode.None && renderBreak.get() && renderMode.get() == RenderMode.Normal)
        .build()
    );

    private final Setting<SettingColor> breakSideColor = sgRender.add(new ColorSetting.Builder()
        .name("break-side-color")
        .description("The side color of the block overlay.")
        .defaultValue(new SettingColor(255, 255, 255, 45))
        .visible(() -> renderMode.get() != RenderMode.None && renderBreak.get() && shapeMode.get() != ShapeMode.Lines)
        .build()
    );

    private final Setting<SettingColor> breakLineColor = sgRender.add(new ColorSetting.Builder()
        .name("break-line-color")
        .description("The line color of the block overlay.")
        .defaultValue(new SettingColor(255, 255, 255, 255))
        .visible(() -> renderMode.get() != RenderMode.None && renderBreak.get() && shapeMode.get() != ShapeMode.Sides)
        .build()
    );

    private final Setting<Boolean> renderDamage = sgRender.add(new BoolSetting.Builder()
        .name("damage")
        .description("Renders crystal damage text in the block overlay.")
        .defaultValue(true)
        .visible(() -> renderMode.get() != RenderMode.None)
        .build()
    );

    private final Setting<Double> damageScale = sgRender.add(new DoubleSetting.Builder()
        .name("damage-scale")
        .description("How big the damage text should be.")
        .defaultValue(1.25)
        .min(1)
        .sliderMax(4)
        .visible(() -> renderMode.get() != RenderMode.None && renderDamage.get())
        .build()
    );

    private final Setting<SettingColor> damageColor = sgRender.add(new ColorSetting.Builder()
        .name("damage-color")
        .description("What the color of the damage text should be.")
        .defaultValue(new SettingColor(255, 255, 255, 255))
        .visible(() -> renderMode.get() != RenderMode.None && renderDamage.get())
        .build()
    );


    public BananaBomber() {
        super(Categories.Combat, Items.AIR, "banana-bomber", "Automatically places and attacks crystals.");
    }


    public int breakTimer;
    private int placeTimer;
    private int switchTimer;
    private int ticksPassed;
    public final List<PlayerEntity> targets = new ArrayList<>();

    private final Vec3d vec3d = new Vec3d(0, 0, 0);
    private final Vec3d playerEyePos = new Vec3d(0, 0, 0);
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
    private final Box box = new Box(0, 0, 0, 0, 0, 0);

    private final Vec3d vec3dRayTraceEnd = new Vec3d(0, 0, 0);
    private RaycastContext raycastContext;

    public final IntSet placedCrystals = new IntOpenHashSet();
    private boolean placing;
    private int placingTimer;
    public final BlockPos.Mutable placingCrystalBlockPos = new BlockPos.Mutable();

    public final IntSet removed = new IntOpenHashSet();
    public final Int2IntMap attemptedBreaks = new Int2IntOpenHashMap();
    private final Int2IntMap waitingToExplode = new Int2IntOpenHashMap();
    public int attacks;

    private double serverYaw;

    public PlayerEntity bestTarget;
    private float bestTargetDamage;
    private int bestTargetTimer;

    private boolean didRotateThisTick;
    private boolean isLastRotationPos;
    private final Vec3d lastRotationPos = new Vec3d(0, 0 ,0);
    private double lastYaw, lastPitch;
    private int lastRotationTimer;

    public TimerUtils selfPoppedTimer = new TimerUtils();
    public TimerUtils targetPoppedTimer = new TimerUtils();

    private int renderTimer, breakRenderTimer;
    private final BlockPos.Mutable renderPos = new BlockPos.Mutable();
    private final BlockPos.Mutable breakRenderPos = new BlockPos.Mutable();

    private double damage;


    @Override
    public boolean onActivate() {
        breakTimer = 0;
        placeTimer = 0;
        ticksPassed = 0;

        assert mc.player != null;
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
        return false;
    }

    @Override
    public void onDeactivate() {
        targets.clear();

        placedCrystals.clear();

        attemptedBreaks.clear();
        waitingToExplode.clear();

        removed.clear();

        bestTarget = null;
    }

    private int getLastRotationStopDelay() {
        return Math.max(10, placeDelay.get() / 2 + breakDelay.get() / 2 + 10);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPreTick(TickEvent.Pre event) {
        // Decrement render timers
        if (renderTimer > 0) renderTimer--;
        if (breakRenderTimer > 0) breakRenderTimer--;

        // Update last rotation
        didRotateThisTick = false;
        lastRotationTimer++;

        // Decrement placing timer
        if (placing) {
            if (placingTimer > 0) placingTimer--;
            else placing = false;
        }

        if (ticksPassed < 20) ticksPassed++;
        else {
            ticksPassed = 0;
            attacks = 0;
        }

        // Decrement best target timer
        if (bestTargetTimer > 0) bestTargetTimer--;
        bestTargetDamage = 0;

        // Decrement break, place and switch timers
        if (breakTimer > 0) breakTimer--;
        if (placeTimer > 0) placeTimer--;
        if (switchTimer > 0) switchTimer--;

        // Update waiting to explode crystals and mark them as existing if reached threshold
        for (IntIterator it = waitingToExplode.keySet().iterator(); it.hasNext();) {
            int id = it.nextInt();
            int ticks = waitingToExplode.get(id);

            if (ticks >= cancelTicks.get()) {
                it.remove();
                removed.remove(id);
            }
            else {
                waitingToExplode.put(id, ticks + 1);
            }
        }

        // Check pause settings
        if (PlayerUtils.shouldPause(minePause.get(), eatPause.get(), drinkPause.get()) || PlayerUtils.getTotalHealth() <= pauseAtHealth.get()) {
            if (debug.get()) warning("Pausing");
            return;
        }

        // Set player eye pos
        ((IVec3d) playerEyePos).set(mc.player.getPos().x, mc.player.getPos().y + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getPos().z);

        // Find targets, break and place
        findTargets();

        if (targets.size() > 0) {
            doBreak();
            doPlace();
        }

        if ((cancelCrystalMode.get() == CancelCrystalMode.Hit)) {
            removed.forEach((java.util.function.IntConsumer) id -> {
                assert mc.world != null;
                Objects.requireNonNull(mc.world.getEntityById(id)).kill();
            });
            removed.clear();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST - 666)
    private void onPreTickLast(TickEvent.Pre event) {
        // Rotate to last rotation
        if (rotate.get() && lastRotationTimer < getLastRotationStopDelay() && !didRotateThisTick) {
            Rotations.rotate(isLastRotationPos ? Rotations.getYaw(lastRotationPos) : lastYaw, isLastRotationPos ? Rotations.getPitch(lastRotationPos) : lastPitch, -100, null);
        }
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent event) {
        if (!(event.entity instanceof EndCrystalEntity)) return;

        if (placing && event.entity.getBlockPos().equals(placingCrystalBlockPos)) {
            placing = false;
            placingTimer = 0;
            placedCrystals.add(event.entity.getId());
        }

        if (fastBreak.get()) {
            if (freqCheck.get()) {
                if (attacks > attackFrequency.get()) return;
            }

            if (smartCheck.get()) {
                if (CrystalUtils.isSurroundHolding() || (slowFacePlace.get() && CrystalUtils.isFacePlacing() || (targetPopInvincibility.get() && CrystalUtils.targetJustPopped()))) return;
            }

            float damage = getBreakDamage(event.entity, false);
            if (damageCheck.get()) {
                if (damage < BminDamage.get()) return;
            }

            doBreak(event.entity);
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

        if (isPos) ((IVec3d) lastRotationPos).set(pos.x, pos.y, pos.z);
        else {
            lastYaw = yaw;
            lastPitch = pitch;
        }

        lastRotationTimer = 0;
    }

    // Break

    private void doBreak() {
        if (!doBreak.get() || breakTimer > 0 || switchTimer > 0 || attacks >= attackFrequency.get() || (popPause.get() != PopPause.Place && CrystalUtils.targetJustPopped())) return;

        float bestDamage = 0;
        Entity crystal = null;

        // Find the best crystal to break
        assert mc.world != null;
        for (Entity entity : mc.world.getEntities()) {
            float damage = getBreakDamage(entity, true);

            if (damage > bestDamage) {
                bestDamage = damage;
                crystal = entity;
            }
        }

        // Break the crystal
        if (crystal != null) doBreak(crystal);
    }

    private float getBreakDamage(Entity entity, boolean checkCrystalAge) {
        if (!(entity instanceof EndCrystalEntity)) return 0;

        // Check only break own
        if (onlyBreakOwn.get() && !placedCrystals.contains(entity.getId())) return 0;

        // Check if it should already be removed
        if (removed.contains(entity.getId())) return 0;

        // Check attempted breaks
        if (attemptCheck.get()) {
            if (attemptedBreaks.get(entity.getId()) > breakAttempts.get()) return 0;
        }

        // Check crystal age
        if (ageCheck.get()) {
            if (checkCrystalAge && entity.age < ticksExisted.get()) return 0;
        }

        if (CrystalUtils.isSurroundHolding() && surroundHoldMode.get() != SlowMode.Delay) {
            if (checkCrystalAge && entity.age < surroundHoldAge.get()) return 0;
        }

        if (slowFacePlace.get() && slowFPMode.get() != SlowMode.Delay && CrystalUtils.isFacePlacing() && bestTarget != null && bestTarget.getY() < placingCrystalBlockPos.getY()) {
            if (checkCrystalAge && entity.age < slowFPAge.get()) return 0;
        }

        // Check range
        if (isOutOfBreakRange(entity)) return 0;

        // Check damage to self and anti suicide
        blockPos.set(entity.getBlockPos()).move(0, -1, 0);

        if (!CrystalUtils.shouldIgnoreSelfBreakDamage()) {
            float selfDamage = BDamageUtils.crystalDamage(mc.player, entity.getPos(), predictMovement.get(), breakRange.get().floatValue(), ignoreTerrain.get(), fullBlocks.get());
            if (selfDamage > BmaxDamage.get() || (BantiSuicide.get() && selfDamage >= EntityUtils.getTotalHealth(Objects.requireNonNull(mc.player))))
                return 0;
        } else if (debug.get()) warning("Ignoring self break dmg");

        // Check damage to target and face place
        float damage = getDamageToTargets(entity.getPos(), true, false);
        boolean facePlaced = (facePlace.get() && CrystalUtils.shouldFacePlace(blockPos) || forceFacePlace.get().isPressed());

        if (!facePlaced && damage < BminDamage.get()) return 0;

        return damage;
    }

    private void doBreak(Entity crystal) {
        // Anti weakness
        if (antiWeakness.get()) {
            assert mc.player != null;
            StatusEffectInstance weakness = mc.player.getStatusEffect(StatusEffects.WEAKNESS);
            StatusEffectInstance strength = mc.player.getStatusEffect(StatusEffects.STRENGTH);

            // Check for strength
            if (weakness != null && (strength == null || strength.getAmplifier() <= weakness.getAmplifier())) {
                // Check if the item in your hand is already valid
                if (!isValidWeaknessItem(mc.player.getMainHandStack())) {
                    // Find valid item to break with
                    if (!InvUtils.swap(InvUtils.findInHotbar(this::isValidWeaknessItem).slot(), false)) return;

                    switchTimer = 1;
                    return;
                }
            }
        }

        // Rotate and attack
        boolean attacked = true;

        if (!rotate.get()) {
            CrystalUtils.attackCrystal(crystal);
        }
        else {
            double yaw = Rotations.getYaw(crystal);
            double pitch = Rotations.getPitch(crystal, Target.Feet);

            if (doYawSteps(yaw, pitch)) {
                setRotation(true, crystal.getPos(), 0, 0);
                Rotations.rotate(yaw, pitch, 50, () -> CrystalUtils.attackCrystal(crystal));
            }
            else {
                attacked = false;
            }
        }

        if (attacked) {
            // Update state
            removed.add(crystal.getId());
            attemptedBreaks.put(crystal.getId(), attemptedBreaks.get(crystal.getId()) + 1);
            waitingToExplode.put(crystal.getId(), 0);

            // Break render
            breakRenderPos.set(crystal.getBlockPos().down());
            breakRenderTimer = renderBreakTime.get();
        }
    }

    private boolean isValidWeaknessItem(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ToolItem) || itemStack.getItem() instanceof HoeItem) return false;

        ToolMaterial material = ((ToolItem) itemStack.getItem()).getMaterial();
        return material == ToolMaterials.DIAMOND || material == ToolMaterials.NETHERITE;
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (event.packet instanceof UpdateSelectedSlotC2SPacket) {
            switchTimer = switchDelay.get();
        }
    }

    // Place

    private void doPlace() {
        if (!doPlace.get() || placeTimer > 0 || (popPause.get() != PopPause.Break && CrystalUtils.targetJustPopped())) return;

        // Return if there are no crystals in hotbar or offhand
        if (!InvUtils.findInHotbar(Items.END_CRYSTAL).found()) return;

        // Return if there are no crystals in either hand and auto switch mode is none
        if (autoSwitch.get() == AutoSwitchMode.None) {
            assert mc.player != null;
            if (mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL && mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL)
                return;
        }

        // Check for multiplace
        assert mc.world != null;
        for (Entity entity : mc.world.getEntities()) {
            if (getBreakDamage(entity, false) > 0) return;
        }

        // Setup variables
        AtomicDouble bestDamage = new AtomicDouble(0);
        AtomicReference<BlockPos.Mutable> bestBlockPos = new AtomicReference<>(new BlockPos.Mutable());
        AtomicBoolean isSupport = new AtomicBoolean(support.get() != SupportMode.Disabled);

        // Find best position to place the crystal on
        BlockIterator.register((int) Math.ceil(placeRange.get()), (int) Math.ceil(placeRange.get()), (bp, blockState) -> {
            // Todo : implement a method to check more efficiently instead of using the blockstate check,
            //  maybe another check can come in before them to see if they're a solid block or not since
            //  it takes too many resources (maybe a fullcube check?)

            // Check if its bedrock or obsidian and return if isSupport is false
            boolean hasBlock = blockState.isOf(Blocks.BEDROCK) || blockState.isOf(Blocks.OBSIDIAN);

            // Check for support
            if (!hasBlock) {
                if (isSupport.get()) {
                    if (!blockState.getMaterial().isReplaceable()) return;
                } else return;
            }

            // Check if there is air on top
            blockPos.set(bp.getX(), bp.getY() + 1, bp.getZ());
            if (!mc.world.getBlockState(blockPos).isAir()) return;

            if (placement112.get()) {
                blockPos.move(0, 1, 0);
                if (!mc.world.getBlockState(blockPos).isAir()) return;
            }

            // Check range
            ((IVec3d) vec3d).set(bp.getX() + 0.5, bp.getY() + 1, bp.getZ() + 0.5);
            blockPos.set(bp).move(0, 1, 0);
            if (isOutOfPlaceRange(vec3d, blockPos)) return;

            // Check if it can be placed
            int x = bp.getX();
            int y = bp.getY() + 1;
            int z = bp.getZ();
            // Weird bug this is prolly a temporary fix
            ((IBox) box).set(x + 0.001, y, z + 0.001, x + 0.999, y + (smallBox.get() ? 1 : 2), z + 0.999);

            if (intersectsWithEntities(box)) return;

            // Check damage to self and anti suicide
            if (!CrystalUtils.shouldIgnoreSelfPlaceDamage()) {
                float selfDamage = BDamageUtils.crystalDamage(mc.player, vec3d, predictMovement.get(), placeRange.get().floatValue(), ignoreTerrain.get(), fullBlocks.get());
                if (selfDamage > PmaxDamage.get() || (PantiSuicide.get() && selfDamage >= EntityUtils.getTotalHealth(Objects.requireNonNull(mc.player))))
                    return;
            } else if (debug.get()) warning("Ignoring self place dmg");

            // Check damage to target and face place
            float damage = getDamageToTargets(vec3d, false, !hasBlock && support.get() == SupportMode.Fast);

            boolean facePlaced = (facePlace.get() && CrystalUtils.shouldFacePlace(blockPos) || forceFacePlace.get().isPressed());

            boolean burrowBreaking = (CrystalUtils.isBurrowBreaking() && CrystalUtils.shouldBurrowBreak(blockPos));

            boolean surroundBreaking = (CrystalUtils.isSurroundBreaking() && CrystalUtils.shouldSurroundBreak(blockPos));

            if ((!facePlaced && !surroundBreaking && !burrowBreaking) && damage < PminDamage.get()) return;

            // Compare damage
            if (damage > bestDamage.get() || (isSupport.get() && hasBlock)) {
                bestDamage.set(damage);
                bestBlockPos.get().set(bp);
            }

            if (hasBlock) isSupport.set(false);
        });

        // Place the crystal
        BlockIterator.after(() -> {
            if (bestDamage.get() == 0) return;

            BlockHitResult result = getPlaceInfo(bestBlockPos.get());

            ((IVec3d) vec3d).set(
                result.getBlockPos().getX() + 0.5 + result.getSide().getVector().getX() * 0.5,
                result.getBlockPos().getY() + 0.5 + result.getSide().getVector().getY() * 0.5,
                result.getBlockPos().getZ() + 0.5 + result.getSide().getVector().getZ() * 0.5
            );

            if (rotate.get()) {
                double yaw = Rotations.getYaw(vec3d);
                double pitch = Rotations.getPitch(vec3d);

                if (yawStepMode.get() == YawStepMode.Break || doYawSteps(yaw, pitch)) {
                    setRotation(true, vec3d, 0, 0);
                    Rotations.rotate(yaw, pitch, 50, () -> placeCrystal(result, bestDamage.get(), isSupport.get() ? bestBlockPos.get() : null));

                    placeTimer += CrystalUtils.getPlaceDelay();
                }
            }
            else {
                placeCrystal(result, bestDamage.get(), isSupport.get() ? bestBlockPos.get() : null);
                placeTimer += CrystalUtils.getPlaceDelay();
            }
        });
    }

    private BlockHitResult getPlaceInfo(BlockPos blockPos) {
        assert mc.player != null;
        ((IVec3d) vec3d).set(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());

        for (Direction side : Direction.values()) {
            ((IVec3d) vec3dRayTraceEnd).set(
                blockPos.getX() + 0.5 + side.getVector().getX() * 0.5,
                blockPos.getY() + 0.5 + side.getVector().getY() * 0.5,
                blockPos.getZ() + 0.5 + side.getVector().getZ() * 0.5
            );

            ((IRaycastContext) raycastContext).set(vec3d, vec3dRayTraceEnd, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
            assert mc.world != null;
            BlockHitResult result = mc.world.raycast(raycastContext);

            if (result != null && result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(blockPos)) {
                return result;
            }
        }

        Direction side = blockPos.getY() > vec3d.y ? Direction.DOWN : Direction.UP;
        return new BlockHitResult(vec3d, side, blockPos, false);
    }

    private void placeCrystal(BlockHitResult result, double damage, BlockPos supportBlock) {
        // Switch
        Item targetItem = supportBlock == null ? Items.END_CRYSTAL : Items.OBSIDIAN;

        FindItemResult item = InvUtils.findInHotbar(targetItem);
        if (!item.found()) return;

        assert mc.player != null;
        int prevSlot = mc.player.getInventory().selectedSlot;

        if (!(mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) && (autoSwitch.get() == AutoSwitchMode.Normal && noGapSwitch.get()) && (mc.player.getMainHandStack().getItem() instanceof EnchantedGoldenAppleItem)) return;
        if (autoSwitch.get() != AutoSwitchMode.None && !item.isOffhand()) InvUtils.swap(item.slot(), false);

        Hand hand = item.getHand();
        if (hand == null) return;

        // Place
        if (supportBlock == null) {
            // Place crystal
            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, result, 0));

            if (renderSwing.get()) mc.player.swingHand(hand);
            if (!hideSwings.get()) Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new HandSwingC2SPacket(hand));

            if (debug.get()) warning("Placing");

            placing = true;
            placingTimer = 4;
            placingCrystalBlockPos.set(result.getBlockPos()).move(0, 1, 0);


            //TODO: fix so this doesn't add if its already in the list

            renderTimer = renderTime.get();
            renderPos.set(result.getBlockPos());
            this.damage = damage;
        }
        else {
            // Place support block
            BlockUtils.place(supportBlock, item, false, 0, renderSwing.get(), true, false);
            placeTimer += supportDelay.get();

            if (supportDelay.get() == 0) placeCrystal(result, damage, null);
        }

        // Switch back
        if (autoSwitch.get() == AutoSwitchMode.Silent) InvUtils.swap(prevSlot, false);
    }

    // Yaw steps

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (event.packet instanceof PlayerMoveC2SPacket) {
            serverYaw = ((PlayerMoveC2SPacket) event.packet).getYaw((float) serverYaw);
        }
    }

    public boolean doYawSteps(double targetYaw, double targetPitch) {
        targetYaw = MathHelper.wrapDegrees(targetYaw) + 180;
        double serverYaw = MathHelper.wrapDegrees(this.serverYaw) + 180;

        if (distanceBetweenAngles(serverYaw, targetYaw) <= yawSteps.get()) return true;

        double delta = Math.abs(targetYaw - serverYaw);
        double yaw = this.serverYaw;

        if (serverYaw < targetYaw) {
            if (delta < 180) yaw += yawSteps.get();
            else yaw -= yawSteps.get();
        }
        else {
            if (delta < 180) yaw -= yawSteps.get();
            else yaw += yawSteps.get();
        }

        setRotation(false, null, yaw, targetPitch);
        Rotations.rotate(yaw, targetPitch, -100, null); // Priority -100 so it sends the packet as the last one, im pretty sure it doesn't matte but idc
        return false;
    }

    private static double distanceBetweenAngles(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }

    // Others

    private boolean isOutOfPlaceRange(Vec3d vec3d, BlockPos blockPos) {
        ((IRaycastContext) raycastContext).set(playerEyePos, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);

        assert mc.world != null;
        BlockHitResult result = mc.world.raycast(raycastContext);
        boolean behindWall = result == null || !result.getBlockPos().equals(blockPos);
        assert mc.player != null;
        double distance = mc.player.getEyePos().distanceTo(vec3d);

        return distance > (behindWall ? placeWallsRange.get() : placeRange.get());
    }

    private boolean isOutOfBreakRange(Entity entity) {
        assert mc.player != null;
        boolean behindWall = !mc.player.canSee(entity);
        double distance = BPlayerUtils.distanceFromEye(entity);

        return distance > (behindWall ? breakWallsRange.get() : breakRange.get());
    }

    private PlayerEntity getNearestTarget() {
        PlayerEntity nearestTarget = null;
        double nearestDistance = targetRange.get();

        for (PlayerEntity target : targets) {
            double distance = target.squaredDistanceTo(mc.player);

            if (distance < nearestDistance) {
                nearestTarget = target;
                nearestDistance = distance;
            }
        }

        return nearestTarget;
    }

    private float getDamageToTargets(Vec3d vec3d, boolean breaking, boolean fast) {
        float damage = 0;

        if (fast) {
            PlayerEntity target = getNearestTarget();
            if (!(smartDelay.get() && breaking && target.hurtTime > 0)) damage = BDamageUtils.crystalDamage(target, vec3d, predictMovement.get(), explosionRadiusToTarget.get().floatValue(), ignoreTerrain.get(), fullBlocks.get());
        }
        else {
            for (PlayerEntity target : targets) {
                if (smartDelay.get() && breaking && target.hurtTime > 0) continue;

                float dmg = BDamageUtils.crystalDamage(target, vec3d, predictMovement.get(), explosionRadiusToTarget.get().floatValue(), ignoreTerrain.get(), fullBlocks.get());

                // Update best target
                if (dmg > bestTargetDamage) {
                    bestTarget = target;
                    bestTargetDamage = dmg;
                    bestTargetTimer = 10;
                }

                // Todo : this part is broken as fuck, it should be = not += but = will fuck a lot of scenarios up when there's multiple targets so  just using this for now until I find a fix
                damage += dmg;
            }
        }

        return damage;
    }

    @Override
    public String getInfoString() {
        return bestTarget != null && bestTargetTimer > 0 ? bestTarget.getGameProfile().getName() : null;
    }

    private void findTargets() {
        targets.clear();

        // Players
        assert mc.world != null;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.getAbilities().creativeMode || player == mc.player) continue;

            if (!player.isDead() && player.isAlive() && Friends.get().shouldAttack(player) && player.distanceTo(mc.player) <= targetRange.get()) {
                targets.add(player);
            }
        }
    }

    private boolean intersectsWithEntities(Box box) {
        return EntityUtils.intersectsWithEntity(box, entity -> !entity.isSpectator() && !removed.contains(entity.getId()));
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (!(event.packet instanceof EntityStatusS2CPacket p)) return;

        if (p.getStatus() != 35) return;

        Entity entity = p.getEntity(mc.world);

        if (!(entity instanceof PlayerEntity)) return;

        if (entity.equals(mc.player) && selfPopInvincibility.get()) selfPoppedTimer.reset();

        if (entity.equals(bestTarget) && targetPopInvincibility.get()) targetPoppedTimer.reset();

    }

    @EventHandler(priority = EventPriority.LOWEST - 1000)
    private void onTick(TickEvent.Post event) {
        if (debug.get()) {
            if (CrystalUtils.isFacePlacing() && bestTarget != null && bestTarget.getY() < placingCrystalBlockPos.getY()) {
                if (slowFacePlace.get()) warning("Slow faceplacing");
                else warning("Faceplacing");
            }

            if (CrystalUtils.isBurrowBreaking()) warning("Burrow breaking");

            if (CrystalUtils.isSurroundHolding()) warning("Surround holding");

            if (CrystalUtils.isSurroundBreaking()) warning("Surround breaking");
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (renderMode.get() == RenderMode.None|| renderTimer <= 0 || !renderDamage.get()) return;

        Vector3d vec = new Vector3d(renderPos.getX() + 0.5, renderPos.getY() + 0.5, renderPos.getZ() + 0.5);

        if (NametagUtils.to2D(new Vec3(vec.x, vec.y, vec.z), damageScale.get())) {
            NametagUtils.begin(new Vec3(vec.x, vec.y, vec.z));
            TextRenderer.get().begin(1, false, true);

            String text = String.format("%.1f", damage);
            double w = TextRenderer.get().getWidth(text) * 0.5;
            TextRenderer.get().render(text, -w, 0, damageColor.get(), true);

            TextRenderer.get().end();
            NametagUtils.end();
        }
    }

    public PlayerEntity getPlayerTarget() {
        if(bestTarget != null) {
            return bestTarget;
        } else {
            return null;
        }
    }
}

