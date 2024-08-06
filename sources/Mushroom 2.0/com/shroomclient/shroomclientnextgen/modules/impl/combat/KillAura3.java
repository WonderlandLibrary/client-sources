package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.mixin.ClientPlayerEntityAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.*;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;

@RegisterModule(
    name = "Kill Aura",
    description = "Hit people",
    uniqueId = "ka3",
    category = ModuleCategory.Combat
)
public class KillAura3 extends Module {

    public static boolean isServerBlocking = false;
    public static boolean isClientBlocking = false;
    private final ThreadLocalRandom rng = ThreadLocalRandom.current();
    private final HashMap<Integer, Long> lastHitAt = new HashMap<>();

    @ConfigMode
    @ConfigOption(name = "Autoblock Mode", description = "", order = 25)
    @ConfigParentId("blockmode")
    public AutoBlockMode autoBlockMode = AutoBlockMode.Custom;

    @ConfigOption(
        name = "Safer AB",
        description = "Helps Bypass (Hypixel)",
        order = 25.1
    )
    @ConfigChild(value = "blockmode", parentEnumOrdinal = 5)
    public Boolean saferOldHypixelAb = true;

    @ConfigMinFor("cps")
    @ConfigOption(
        name = "CPS MIN",
        description = "",
        min = 0,
        max = 20,
        order = 100
    )
    public Integer minCps = 20;

    @ConfigMaxFor("cps")
    @ConfigOption(
        name = "CPS MAX",
        description = "Attacks Per Second",
        min = 0,
        max = 20,
        order = -1
    )
    public Integer maxCps = 20;

    @ConfigOption(
        name = "Rotation Range",
        description = "Range In Which To Rotate To Target",
        min = 1,
        max = 6,
        order = 0,
        precision = 1
    )
    public Double rotationReach = 5d;

    @ConfigOption(
        name = "Auto Equip Sword",
        description = "Turn Off Sword Only If This On",
        min = 1,
        max = 6,
        order = 3
    )
    public Boolean swordequip = true;

    @ConfigOption(
        name = "Attack Range",
        description = "Range In Which To Attack The Target",
        min = 1,
        max = 6,
        order = 1,
        precision = 1
    )
    public Double reach = 3d;

    @ConfigOption(
        name = "Hit Through Walls",
        description = "Whether To Hit Through Walls",
        order = 2
    )
    @ConfigParentId("thruwalls")
    public Boolean throughWalls = true;

    @ConfigOption(
        name = "Walls Range",
        description = "Range For Hit Through Walls",
        min = 1,
        max = 6,
        order = 3,
        precision = 1
    )
    @ConfigChild("thruwalls")
    public Double wallsReach = 3d;

    @ConfigOption(name = "Target Mode", description = "", order = 5.9)
    @ConfigParentId("targetmode")
    public TargetMode targetMode = TargetMode.Single;

    @ConfigChild(value = "targetmode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Switch delay",
        description = "In Ticks (50 milliseconds)",
        min = 0,
        max = 20,
        order = 5.99
    )
    public Integer switchAuraDelay = 2;

    @ConfigOption(name = "Sorting Mode", description = "", order = 6)
    public SortingMode sortingMode = SortingMode.Health;

    @ConfigOption(
        name = "Target Randomization",
        description = "Randomize Where To Look",
        order = 8
    )
    @ConfigParentId("randomtarget")
    public Boolean targetRandomization = true;

    @ConfigOption(
        name = "Aim tries",
        description = "Higher Value = Less Strict When Target Is Nearly Out Of Range",
        order = 9,
        min = 0,
        max = 1000
    )
    @ConfigChild("randomtarget")
    public Integer reAimTries = 1000;

    @ConfigOption(
        name = "Min Horizontal Randomization",
        description = "",
        order = 10,
        min = 0,
        max = 1,
        precision = 2
    )
    @ConfigChild("randomtarget")
    public Double targetRandomizationHorizontalMin = 0.05d;

    @ConfigOption(
        name = "Max Horizontal Randomization",
        description = "",
        order = 11,
        min = 0,
        max = 1,
        precision = 2
    )
    @ConfigChild("randomtarget")
    public Double targetRandomizationHorizontalMax = 0.15d;

    @ConfigOption(
        name = "Min Vertical Randomization",
        description = "",
        order = 12,
        min = 0,
        max = 1,
        precision = 2
    )
    @ConfigChild("randomtarget")
    public Double targetRandomizationVerticalMin = 0.05d;

    @ConfigOption(
        name = "Max Vertical Randomization",
        description = "",
        order = 13,
        min = 0,
        max = 1,
        precision = 2
    )
    @ConfigChild("randomtarget")
    public Double targetRandomizationVerticalMax = 0.15d;

    @ConfigOption(
        name = "Swords Only",
        description = "Only Activates If Holding Sword",
        order = 14
    )
    public Boolean onlyIfSword = true;

    @ConfigOption(
        name = "Disable In Inventory",
        description = "Doesn't Attack While In Any Inventory",
        order = 14
    )
    public Boolean disableInGUI = true;

    @ConfigOption(
        name = "Always Swing",
        description = "Swings Even If You're Not Looking At The Target",
        order = 15
    )
    public Boolean swingInRotationReach = false;

    @ConfigOption(
        name = "Swing Time",
        description = "Switch This Depending On Your Minecraft Version",
        order = 16
    )
    public SwingOrder swingOrder = SwingOrder._1DOT20;

    @ConfigOption(
        name = "Hitboxes",
        description = "Modify The Hitbox Size Used By Aura Raytraces",
        order = 17
    )
    @ConfigParentId("hitboxes")
    public Boolean useAuraHitBoxes = false;

    @ConfigOption(
        name = "Hitbox Horizontal Multiplier",
        description = "",
        min = 0.5,
        max = 1.5,
        order = 18,
        precision = 1
    )
    @ConfigChild("hitboxes")
    public Double hitBoxHorizontalMulti = 1d;

    @ConfigOption(
        name = "Hitbox Vertical Multiplier",
        description = "",
        min = 0.5,
        max = 1.5,
        order = 19,
        precision = 1
    )
    @ConfigChild("hitboxes")
    public Double hitBoxVerticalMulti = 1d;

    @ConfigOption(
        name = "Only Rotate If Not Looking At Target",
        description = "",
        order = 20
    )
    public Boolean onlyRotateIfNecessary = false;

    @ConfigOption(name = "Client Side Rotations", description = "", order = 21)
    public Boolean clientSideRots = false;

    @ConfigOption(name = "Rotation Mode", description = "", order = 21.1)
    @ConfigParentId("rotmode")
    public RotationMode rotationMode = RotationMode.Simple;

    @ConfigChild(value = "rotmode", parentEnumOrdinal = 2)
    @ConfigMinFor("rotspeed")
    @ConfigOption(
        name = "Rotation Speed MIN",
        description = "",
        min = 0.1,
        max = 180,
        order = 23.99
    )
    public Double rotSimpleMin = 40d;

    @ConfigChild(value = "rotmode", parentEnumOrdinal = 2)
    @ConfigMaxFor("rotspeed")
    @ConfigOption(
        name = "Rotation Speed MAX",
        description = "Rotation Speed In Degrees Per Tick",
        min = 0.1,
        max = 180,
        order = 23.9
    )
    public Double rotSimpleMax = 60d;

    @ConfigOption(
        name = "Rotation Speed",
        description = "Lower Is Faster",
        min = 1,
        max = 5,
        precision = 2,
        order = 24
    )
    @ConfigChild(value = "rotmode", parentEnumOrdinal = 0)
    public Double rotSmoothSpeed = 3d;

    @ConfigOption(
        name = "Block Range",
        description = "Maximum Distance The Target Can Be Away To Trigger AutoBlock",
        min = 1,
        max = 6,
        order = 25.1,
        precision = 1
    )
    @ConfigChild(value = "blockmode", parentEnumOrdinal = { 1, 2, 3, 4, 5, 6 })
    public Double blockReach = 5d;

    @ConfigOption(name = "Block Hand", description = "", order = 25.9)
    @ConfigChild(value = "blockmode", parentEnumOrdinal = { 2, 3 })
    public BlockHand blockHand = BlockHand.Main_Hand;

    @ConfigOption(name = "Block Time", description = "", order = 26)
    @ConfigChild(value = "blockmode", parentEnumOrdinal = 3)
    public Timing blockTime = Timing.Post;

    @ConfigOption(name = "Blocking Mode", description = "", order = 27)
    @ConfigChild(value = "blockmode", parentEnumOrdinal = 3)
    public BlockMode blockMode = BlockMode.Fake;

    @ConfigOption(name = "Unblock Time", description = "", order = 28)
    @ConfigChild(value = "blockmode", parentEnumOrdinal = 3)
    public Timing unBlockTime = Timing.Post;

    @ConfigOption(name = "Unblock Mode", description = "", order = 29)
    @ConfigChild(value = "blockmode", parentEnumOrdinal = 3)
    public UnBlockMode unBlockMode = UnBlockMode.Fake;

    private Entity lastTargetEnt = null;
    private int ticksSinceSwitch = Integer.MAX_VALUE;
    private @Nullable Integer currentCps = null;
    // Between 1-20 (incl/incl)
    private int cpsResetTickCounter = 1;
    private int clicksSoFar = 0;
    private boolean shouldUnblock = false;
    // Tick 1: blink, Unblock
    // Tick 2: Hit
    // Tick 3: Block, unblink
    private int blink_ab_ticks = 0;
    private boolean blink_ab_blinking = false;

    private double getMaxReach(boolean includeThroughWalls) {
        return Math.max(
            Math.max(rotationReach, reach),
            Math.max(
                (includeThroughWalls && throughWalls) ? wallsReach : 0,
                autoBlockMode != AutoBlockMode.None ? blockReach : 0
            )
        );
    }

    private double getMaxReach() {
        return getMaxReach(true);
    }

    private Vec3d getEyeVec() {
        return C.p()
            .getPos()
            .add(new Vec3d(0, C.p().getEyeHeight(C.p().getPose()), 0));
    }

    private Vec3d getBoxSize(Box box) {
        double hbX = box.maxX - box.minX;
        double hbY = box.maxY - box.minY;
        double hbZ = box.maxZ - box.minZ;
        return new Vec3d(hbX, hbY, hbZ);
    }

    private Vec3d getEntityHitBoxSize(Entity ent) {
        Box entBb = ent.getBoundingBox();
        return getBoxSize(entBb);
    }

    private Vec3d getBoxMinPoint(Box box) {
        return new Vec3d(box.minX, box.minY, box.minZ);
    }

    private Vec3d getEntityHitBoxMinPoint(Entity ent) {
        Box entBb = ent.getBoundingBox();
        return getBoxMinPoint(entBb);
    }

    private Box getScaledEntityHitBox(
        Entity ent,
        double horizontalMulti,
        double verticalMulti
    ) {
        Vec3d hb = getEntityHitBoxSize(ent);
        double xPart = ((1 - horizontalMulti) / 2) * hb.x;
        double yPart = ((1 - verticalMulti) / 2) * hb.y;
        double zPart = ((1 - horizontalMulti) / 2) * hb.z;

        Vec3d s = getEntityHitBoxMinPoint(ent);
        return new Box(
            s.x + xPart,
            s.y + yPart,
            s.z + zPart,
            s.x + hb.x - xPart,
            s.y + hb.y + yPart,
            s.z + hb.z + zPart
        );
    }

    RotationUtil.Rotation currentRotationsKillaura = new RotationUtil.Rotation(
        0,
        0
    );

    private Vec3d getClosestPoint(
        Entity target,
        double hitBoxMultiHorizontal,
        double hitBoxMultiVertical,
        double xOffset,
        double yOffset,
        double zOffset
    ) {
        Box hb = getScaledEntityHitBox(
            target,
            hitBoxMultiHorizontal,
            hitBoxMultiVertical
        );
        Vec3d start = getBoxMinPoint(hb);
        Vec3d s = getBoxSize(hb);

        Vec3d eP = getEyeVec();

        Vec3d closest = null;
        double closestDist = Double.MAX_VALUE;
        double stepSize = 0.1d;
        for (double dX = 0; dX <= s.x; dX += stepSize) {
            for (double dY = 0; dY <= s.y; dY += stepSize) {
                for (double dZ = 0; dZ <= s.z; dZ += stepSize) {
                    Vec3d point = start.add(dX, dY, dZ);
                    Vec3d offsetPoint = point.add(xOffset, yOffset, zOffset);
                    if (!hb.contains(offsetPoint)) continue;
                    double d = point.squaredDistanceTo(eP);
                    if (d < closestDist) {
                        closestDist = d;
                        closest = point;
                    }
                }
            }
        }

        return closest != null ? closest.add(xOffset, yOffset, zOffset) : null;
    }

    private Stream<Entity> getPossibleTargets() {
        double maxReachSq = Math.pow(getMaxReach(), 2);
        Vec3d eP = getEyeVec();
        return StreamSupport.stream(C.w().getEntities().spliterator(), true)
            .filter(TargetUtil::shouldTarget)
            .filter(
                ent ->
                    getClosestPoint(
                        ent,
                        useAuraHitBoxes ? hitBoxHorizontalMulti : 1,
                        useAuraHitBoxes ? hitBoxVerticalMulti : 1,
                        0,
                        0,
                        0
                    ).squaredDistanceTo(eP) <=
                    maxReachSq
            );
    }

    private @Nullable Entity getSingleTarget(Stream<Entity> s) {
        Vec3d eP = getEyeVec();
        Optional<Entity> targetOpt = Optional.empty();
        if (sortingMode == SortingMode.Distance) {
            targetOpt = s.min(
                Comparator.comparingDouble(
                    ent ->
                        getClosestPoint(
                            ent,
                            useAuraHitBoxes ? hitBoxHorizontalMulti : 1,
                            useAuraHitBoxes ? hitBoxVerticalMulti : 1,
                            0,
                            0,
                            0
                        ).squaredDistanceTo(eP)
                )
            );
        } else if (sortingMode == SortingMode.Health) {
            targetOpt = s.min(
                Comparator.comparingDouble(ent -> {
                    if (ent instanceof LivingEntity l) {
                        return l.getHealth();
                    } else {
                        return Double.MAX_VALUE;
                    }
                })
            );
        } else if (sortingMode == SortingMode.Hurt) {
            targetOpt = s.min(
                Comparator.comparingDouble(ent -> {
                    if (ent instanceof LivingEntity l) {
                        return l.hurtTime;
                    } else {
                        return Double.MAX_VALUE;
                    }
                })
            );
        }

        return targetOpt.orElse(null);
    }

    private @Nullable Entity getTarget() {
        if (targetMode == TargetMode.Single) {
            return getSingleTarget(getPossibleTargets());
        } else if (targetMode == TargetMode.Switch) {
            if (lastTargetEnt == null || ticksSinceSwitch >= switchAuraDelay) {
                Vec3d eP = getEyeVec();
                double reachSq = Math.pow(reach, 2);
                double wallReachSq = throughWalls ? Math.pow(wallsReach, 2) : 0;
                Entity prefSingleTarget = getSingleTarget(
                    getPossibleTargets()
                        .filter(ent -> !lastHitAt.containsKey(ent.getId()))
                );
                Optional<Entity> newTarget = getPossibleTargets()
                    .filter(ent -> {
                        Vec3d tV = getTargetVec(ent);
                        return (
                            tV != null &&
                            tV.squaredDistanceTo(eP) <=
                                (WorldUtil.canEntityBeSeen(ent)
                                        ? reachSq
                                        : wallReachSq)
                        );
                    })
                    .max(
                        Comparator.comparingLong(
                            ent ->
                                lastHitAt.containsKey(ent.getId())
                                    ? Instant.now().toEpochMilli() -
                                    lastHitAt.get(ent.getId())
                                    : (prefSingleTarget != null &&
                                            prefSingleTarget.getId() ==
                                                ent.getId())
                                        ? Long.MAX_VALUE
                                        : (Long.MAX_VALUE - 1)
                        )
                    );

                if (newTarget.isPresent()) {
                    if (
                        lastTargetEnt == null ||
                        lastTargetEnt.getId() != newTarget.get().getId()
                    ) {
                        lastTargetEnt = newTarget.get();
                        ticksSinceSwitch = 0;
                    }
                } else {
                    lastTargetEnt = null;
                }
            }

            return lastTargetEnt;
        } else throw new RuntimeException("Unsupported TargetMode");
    }

    private @Nullable Vec3d getTargetVec(Entity target) {
        if (target == null) return null;

        double maxReachSq = Math.pow(getMaxReach(), 2);
        Vec3d eP = getEyeVec();

        int actualReAimTries = targetRandomization ? reAimTries : 1;
        for (int i = 0; i < actualReAimTries; i++) {
            double xOffset = targetRandomization
                ? rng.nextDouble(
                    targetRandomizationHorizontalMin,
                    targetRandomizationHorizontalMax + Double.MIN_VALUE
                ) *
                (rng.nextBoolean() ? -1 : 1)
                : 0;
            double yOffset = targetRandomization
                ? rng.nextDouble(
                    targetRandomizationVerticalMin,
                    targetRandomizationVerticalMax + Double.MIN_VALUE
                ) *
                (rng.nextBoolean() ? -1 : 1)
                : 0;
            double zOffset = targetRandomization
                ? rng.nextDouble(
                    targetRandomizationHorizontalMin,
                    targetRandomizationHorizontalMax + Double.MIN_VALUE
                ) *
                (rng.nextBoolean() ? -1 : 1)
                : 0;

            Vec3d point = getClosestPoint(
                target,
                useAuraHitBoxes ? hitBoxHorizontalMulti : 1,
                useAuraHitBoxes ? hitBoxVerticalMulti : 1,
                xOffset,
                yOffset,
                zOffset
            );

            if (point.squaredDistanceTo(eP) <= maxReachSq) {
                return point;
            }
        }

        return null;
    }

    private void rotateToVec(Vec3d target, MotionEvent.Pre e) {
        if (MovementUtil.OverrideHeadRotations()) return;

        Vec2f currentRot = new Vec2f(e.getPitch(), e.getYaw());
        Vec2f targetRot = RotationUtil.getRotation(
            target.x,
            target.y,
            target.z
        );
        Vec2f doRots;
        if (rotationMode == RotationMode.Simple) {
            doRots = RotationUtil.getLimitedRotation(
                currentRot,
                targetRot,
                (float) rng.nextDouble(
                    rotSimpleMin - 0.1d,
                    rotSimpleMax + Double.MIN_VALUE
                )
            );
        } else if (rotationMode == RotationMode.Smooth) {
            doRots = RotationUtil.getSmoothRotation(
                currentRot,
                targetRot,
                (float) (double) rotSmoothSpeed
            );
        } else if (rotationMode == RotationMode.Legit) {
            doRots = RotationUtil.getLegitRotation(currentRot, targetRot);
        } else throw new RuntimeException("Unsupported rotation mode");

        e.setPitch(doRots.x);
        e.setYaw(doRots.y);
        if (swordequip) {
            int current = getSword();
            if (current == -1) {
                //Notifications.notify("No Sword Found", new Color(200, 0, 0), 2);
                return;
            }

            C.p().getInventory().selectedSlot = getSword();
        }
    }

    static int getSword() {
        if (C.p() != null) {
            Item var1 = C.p()
                .getInventory()
                .getStack(C.p().getInventory().selectedSlot)
                .getItem();
            if (var1 instanceof SwordItem) {
                SwordItem h = (SwordItem) var1;
                return C.p().getInventory().selectedSlot;
            }
        }

        int current = -1;
        int stackSize = 0;

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = C.p().getInventory().getStack(i);
            if (stack != null && stackSize < stack.getCount()) {
                Item var5 = stack.getItem();
                if (var5 instanceof SwordItem) {
                    SwordItem h = (SwordItem) var5;
                    stackSize = stack.getCount();
                    current = i;
                }
            }
        }

        return current;
    }

    private void hit(Entity target) {
        if (swingOrder == SwingOrder._1DOT8) {
            C.p().swingHand(Hand.MAIN_HAND);
            PacketUtil.sendPacket(
                new PlayerInteractEntityC2SPacket(
                    target.getId(),
                    C.p().isSneaking(),
                    PlayerInteractEntityC2SPacket.ATTACK
                ),
                false
            );
        } else if (swingOrder == SwingOrder._1DOT20) {
            PacketUtil.sendPacket(
                new PlayerInteractEntityC2SPacket(
                    target.getId(),
                    C.p().isSneaking(),
                    PlayerInteractEntityC2SPacket.ATTACK
                ),
                false
            );
            C.p().swingHand(Hand.MAIN_HAND);
        } else throw new RuntimeException("Unsupported swing order");

        /* // no this is NOT meta for any antihake
        if (autoBlockMode == AutoBlockMode.Old_Hypixel2) {
            PacketUtil.sendPacket(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0), false);
            PacketUtil.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0), false);
        }
         */

        lastHitAt.put(target.getId(), Instant.now().toEpochMilli());
    }

    private boolean isHoldingSwordOrShield() {
        ItemStack s = C.p().getStackInHand(Hand.MAIN_HAND);
        return (
            s != null &&
            (s.getItem() instanceof SwordItem ||
                s.getItem() instanceof ShieldItem)
        );
    }

    private boolean shouldActivateAura() {
        boolean activate = true;
        if (onlyIfSword) {
            ItemStack s = C.p().getStackInHand(Hand.MAIN_HAND);
            activate = s != null && s.getItem() instanceof SwordItem;
        }
        if (disableInGUI) {
            activate = C.mc.currentScreen == null && activate;
        }

        return activate;
    }

    // CPS Throttler
    private boolean shouldClick() {
        if (currentCps == null) {
            if (minCps == maxCps) {
                currentCps = minCps;
            } else {
                currentCps = rng.nextInt(minCps, maxCps + 1);
            }

            int expectedClicksByNow = (int) Math.floor(
                ((double) cpsResetTickCounter) * (((double) currentCps) / 20d)
            );
            return clicksSoFar < expectedClicksByNow;
        }

        int expectedClicksByNow = (int) Math.floor(
            ((double) cpsResetTickCounter) * (((double) currentCps) / 20d)
        );
        return clicksSoFar < expectedClicksByNow;
    }

    private void clicked() {
        clicksSoFar++;
    }

    private void incrementTickCounters() {
        cpsResetTickCounter++;
        ticksSinceSwitch++;
        if (cpsResetTickCounter > 20) {
            cpsResetTickCounter = 1;
            currentCps = null;
            clicksSoFar = 0;
        }
        blink_ab_ticks++;
        if (blink_ab_ticks > 3) blink_ab_ticks = 0;
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        incrementTickCounters();

        if (!isHoldingSwordOrShield()) {
            isClientBlocking = false;
            isServerBlocking = false;
        }

        if (!shouldActivateAura()) return;
        Entity targetEnt = getTarget();
        if (targetEnt == null) {
            if (isServerBlocking) {
                shouldUnblock = true;
                ab_pre();
                ab_post_attack();
            }
            if (isClientBlocking) isClientBlocking = false;
            return;
        }
        Vec3d target = getTargetVec(targetEnt);
        if (target == null) {
            if (isServerBlocking) {
                shouldUnblock = true;
                ab_pre();
                ab_post_attack();
            }
            if (isClientBlocking) isClientBlocking = false;
            return;
        }

        if (!MovementUtil.OverrideHeadRotations()) {
            e.setYaw(((ClientPlayerEntityAccessor) C.p()).getLastYaw());
            e.setPitch(((ClientPlayerEntityAccessor) C.p()).getLastPitch());
        }

        if (
            !onlyRotateIfNecessary ||
            !WorldUtil.isLookingAtBox(
                getScaledEntityHitBox(
                    targetEnt,
                    useAuraHitBoxes ? hitBoxHorizontalMulti : 1,
                    useAuraHitBoxes ? hitBoxVerticalMulti : 1
                ),
                Math.max(reach, throughWalls ? wallsReach : 0),
                e.getPitch(),
                e.getYaw(),
                0.1d
            )
        ) rotateToVec(target, e);

        currentRotationsKillaura = new RotationUtil.Rotation(
            e.getPitch(),
            e.getYaw()
        );

        if (clientSideRots) {
            C.p().setYaw(e.getYaw());
            C.p().setPitch(e.getPitch());
        }

        if (!shouldClick()) return;

        ab_pre();

        if (
            !WorldUtil.isLookingAtBox(
                getScaledEntityHitBox(
                    targetEnt,
                    useAuraHitBoxes ? hitBoxHorizontalMulti : 1,
                    useAuraHitBoxes ? hitBoxVerticalMulti : 1
                ),
                Math.max(reach, throughWalls ? wallsReach : 0),
                e.getPitch(),
                e.getYaw(),
                0.1d
            )
        ) {
            if (swingInRotationReach) {
                C.p().swingHand(Hand.MAIN_HAND);
                clicked();
                ab_post_attack();
            }
            return;
        }

        BlockHitResult wallsRtr = WorldUtil.rayTraceBlocks(getEyeVec(), target);
        boolean isThroughWalls =
            wallsRtr != null && wallsRtr.getType() == HitResult.Type.BLOCK;
        if (isThroughWalls && !throughWalls) return;
        double actualReach = isThroughWalls ? wallsReach : reach;
        Vec3d eP = getEyeVec();
        if (target.distanceTo(eP) > actualReach) {
            if (swingInRotationReach) {
                C.p().swingHand(Hand.MAIN_HAND);
                clicked();
                ab_post_attack();
            }
            return;
        }

        if (shouldHit()) {
            if (
                autoBlockMode == AutoBlockMode.Old_Hypixel && saferOldHypixelAb
            ) {
                if (targetEnt instanceof PlayerEntity playerEntity) {
                    if (
                        playerEntity.hurtTime <= 6 &&
                        (C.p().handSwingTicks != 1)
                    ) {
                        hit(targetEnt);
                        clicked();
                    }
                }
            } else {
                hit(targetEnt);
                clicked();

                ab_post_attack();
            }
        }
    }

    @SubscribeEvent
    public void onMotionPost(MotionEvent.Post e) {
        ab_post();
    }

    public static boolean currentShouldAb = false;

    private boolean shouldAb() {
        currentShouldAb = false;
        Entity targetEnt = getTarget();
        if (targetEnt == null) return false;
        Vec3d targetVec = getTargetVec(targetEnt);
        if (targetVec == null) return false;
        if (getEyeVec().distanceTo(targetVec) > blockReach) return false;

        currentShouldAb = true;
        return true;
    }

    private boolean shouldHit() {
        // dupe if statement
        if (autoBlockMode == AutoBlockMode.Blink) return blink_ab_ticks == 2;
        return true;
    }

    private void ab_pre() {
        if (shouldUnblock) {
            if (autoBlockMode == AutoBlockMode.Hypixel) {
                ab_unblock();
            }

            if (autoBlockMode == AutoBlockMode.Old_Hypixel) ab_unblock();
            if (autoBlockMode == AutoBlockMode.Blink && isServerBlocking) {
                PacketUtil.sendPacket(
                    new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                        BlockPos.ORIGIN,
                        Direction.DOWN
                    ),
                    false
                );
                isServerBlocking = false;
                isClientBlocking = false;
            }
            if (
                autoBlockMode == AutoBlockMode.Custom &&
                unBlockTime == Timing.Pre
            ) ab_unblock();
            shouldUnblock = false;
            return;
        }

        if (shouldAb() && autoBlockMode == AutoBlockMode.Blink) {
            isClientBlocking = true;
            if (blink_ab_ticks == 1) {
                BlinkUtil.setOutgoingBlink(true);
                BlinkUtil.setIncomingBlink(true);
                blink_ab_blinking = true;
                if (isServerBlocking) {
                    PacketUtil.sendPacket(
                        new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                            BlockPos.ORIGIN,
                            Direction.DOWN
                        ),
                        false
                    );
                    isServerBlocking = false;
                }
            } else if (blink_ab_ticks == 3) {
                if (!isServerBlocking) {
                    PacketUtil.sendSequencedPacket(
                        sequence ->
                            new PlayerInteractItemC2SPacket(
                                blockHand == BlockHand.Main_Hand
                                    ? Hand.MAIN_HAND
                                    : Hand.OFF_HAND,
                                sequence
                            )
                    );
                    isServerBlocking = true;
                    BlinkUtil.setOutgoingBlink(false);
                    BlinkUtil.setIncomingBlink(false);
                    blink_ab_blinking = false;
                }
            }
        }

        if (
            shouldAb() && autoBlockMode == AutoBlockMode.Old_Hypixel
        ) ab_block();
        if (
            autoBlockMode == AutoBlockMode.Custom && unBlockTime == Timing.Pre
        ) ab_unblock();
        if (
            shouldAb() &&
            autoBlockMode == AutoBlockMode.Custom &&
            blockTime == Timing.Pre
        ) ab_block();
    }

    private void ab_post_attack() {
        if (shouldUnblock) {
            if (
                autoBlockMode == AutoBlockMode.Custom &&
                unBlockTime == Timing.Post_Attack
            ) ab_unblock();
            shouldUnblock = false;
            return;
        }

        if (shouldAb() && autoBlockMode == AutoBlockMode.Hypixel) {
            ab_block();
        }

        if (
            autoBlockMode == AutoBlockMode.Custom &&
            unBlockTime == Timing.Post_Attack
        ) ab_unblock();
        if (
            shouldAb() &&
            autoBlockMode == AutoBlockMode.Custom &&
            blockTime == Timing.Post_Attack
        ) ab_block();
    }

    private void ab_post() {
        if (shouldUnblock) {
            if (autoBlockMode == AutoBlockMode.Fake) ab_unblock();
            if (autoBlockMode == AutoBlockMode.Packet) ab_unblock();
            if (
                autoBlockMode == AutoBlockMode.Custom &&
                unBlockTime == Timing.Post
            ) ab_unblock();
            shouldUnblock = false;
            return;
        }

        if (
            autoBlockMode == AutoBlockMode.Custom && unBlockTime == Timing.Post
        ) ab_unblock();

        if (shouldAb()) {
            if (
                autoBlockMode == AutoBlockMode.Custom &&
                blockTime == Timing.Post
            ) ab_block();

            if (autoBlockMode == AutoBlockMode.Fake) {
                ab_block();
            }
        }
        if (autoBlockMode == AutoBlockMode.Packet) {
            ab_block();
        }
    }

    private void ab_block() {
        if (
            C.p()
                    .getInventory()
                    .getStack(C.p().getInventory().selectedSlot)
                    .getItem() instanceof
                SwordItem ||
            C.p().getOffHandStack().getItem() instanceof ShieldItem ||
            C.p()
                    .getInventory()
                    .getStack(C.p().getInventory().selectedSlot)
                    .getItem() instanceof
                ShieldItem
        ) {
            if (autoBlockMode == AutoBlockMode.Custom) {
                if (blockMode == BlockMode.Fake) {
                    isClientBlocking = true;
                } else if (blockMode == BlockMode.Packet) {
                    if (!isServerBlocking) {
                        PacketUtil.sendSequencedPacket(
                            sequence ->
                                new PlayerInteractItemC2SPacket(
                                    blockHand == BlockHand.Main_Hand
                                        ? Hand.MAIN_HAND
                                        : Hand.OFF_HAND,
                                    sequence
                                )
                        );
                        isClientBlocking = true;
                        isServerBlocking = true;
                    }
                } else if (blockMode == BlockMode.Click) {
                    if (!C.mc.options.useKey.isPressed()) {
                        isClientBlocking = true;
                        isServerBlocking = true;

                        C.mc.options.useKey.setPressed(true);
                    }
                } else if (blockMode == BlockMode.Press) {
                    if (C.mc.currentScreen == null) {
                        KeyBindUtil.pressKey(C.mc.options.attackKey, true);
                        isClientBlocking = true;
                    }
                } else if (blockMode == BlockMode.Interact) {
                    isClientBlocking = true;
                    isServerBlocking = true;

                    EntityHitResult entityHitResult = WorldUtil.rayTraceEntity(
                        currentRotationsKillaura.pitch,
                        currentRotationsKillaura.yaw,
                        reach
                    );
                    BlockHitResult hitResult = WorldUtil.rayTrace(
                        currentRotationsKillaura.pitch,
                        currentRotationsKillaura.yaw,
                        5f
                    );

                    if (
                        entityHitResult != null &&
                        entityHitResult.getEntity() != null
                    ) {
                        C.mc.interactionManager.interactEntity(
                            C.p(),
                            entityHitResult.getEntity(),
                            blockHand == BlockHand.Main_Hand
                                ? Hand.MAIN_HAND
                                : Hand.OFF_HAND
                        );
                    } else if (
                        hitResult != null &&
                        hitResult.getType() == HitResult.Type.BLOCK
                    ) {
                        C.mc.interactionManager.interactBlock(
                            C.p(),
                            blockHand == BlockHand.Main_Hand
                                ? Hand.MAIN_HAND
                                : Hand.OFF_HAND,
                            hitResult
                        );
                    }

                    // Interact with the item in the block hand
                    ActionResult actionResult =
                        C.mc.interactionManager.interactItem(
                            C.p(),
                            blockHand == BlockHand.Main_Hand
                                ? Hand.MAIN_HAND
                                : Hand.OFF_HAND
                        );
                    if (actionResult.isAccepted()) {
                        if (actionResult.shouldSwingHand()) {
                            C.p()
                                .swingHand(
                                    blockHand == BlockHand.Main_Hand
                                        ? Hand.MAIN_HAND
                                        : Hand.OFF_HAND
                                );
                        }
                    }
                } else throw new RuntimeException("Unsupported BlockMode");
            } else if (autoBlockMode == AutoBlockMode.Old_Hypixel) {
                //if (MovementUtil.ticksSinceLastHurt <= 3 && saferOldHypixelAb) {
                //    if (isServerBlocking) {
                //        C.mc.options.useKey.setPressed(false);
                //        isServerBlocking = false;
                //    }
                //}
                //else
                if (!isServerBlocking) {
                    C.mc.options.useKey.setPressed(true);
                    isServerBlocking = true;
                    isClientBlocking = true;
                }
            } else if (autoBlockMode == AutoBlockMode.Hypixel) {
                if (!isServerBlocking) {
                    PacketUtil.sendPacket(
                        new PlayerInteractItemC2SPacket(
                            blockHand == BlockHand.Main_Hand
                                ? Hand.MAIN_HAND
                                : Hand.OFF_HAND,
                            1
                        ),
                        false
                    );

                    isClientBlocking = true;
                    isServerBlocking = true;
                }
            } else if (autoBlockMode == AutoBlockMode.Packet) {
                if (!isServerBlocking) {
                    isClientBlocking = true;
                    isServerBlocking = true;
                }
            } else if (autoBlockMode == AutoBlockMode.Fake) {
                isClientBlocking = true;
            }
        }
    }

    private void ab_unblock() {
        if (autoBlockMode == AutoBlockMode.Custom) {
            if (unBlockMode == UnBlockMode.Fake) {
                isClientBlocking = false;
            } else if (unBlockMode == UnBlockMode.Packet) {
                if (isServerBlocking) {
                    PacketUtil.sendPacket(
                        new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                            BlockPos.ORIGIN,
                            Direction.DOWN
                        ),
                        false
                    );
                    isServerBlocking = false;
                    isClientBlocking = false;
                }
            } else if (unBlockMode == UnBlockMode.Swap_Slot) {
                if (isServerBlocking) {
                    int currentSlot = C.p().getInventory().selectedSlot;

                    PacketUtil.sendPacket(
                        new UpdateSelectedSlotC2SPacket((currentSlot + 1) % 9),
                        false
                    );
                    PacketUtil.sendPacket(
                        new UpdateSelectedSlotC2SPacket(currentSlot),
                        false
                    );

                    isServerBlocking = false;
                    isClientBlocking = false;
                }
            } else if (unBlockMode == UnBlockMode.Release) {
                if (C.mc.options.useKey.isPressed()) {
                    isClientBlocking = false;
                    isServerBlocking = false;

                    C.mc.options.useKey.setPressed(false);
                }
            } else if (unBlockMode == UnBlockMode.Interact) {
                C.mc.interactionManager.stopUsingItem(C.p());
            } else throw new RuntimeException("Unsupported UnBlockMode");
        } else if (autoBlockMode == AutoBlockMode.Old_Hypixel) {
            if (isServerBlocking) {
                C.mc.options.useKey.setPressed(false);
                isServerBlocking = false;
                isClientBlocking = false;
            }
        } else if (autoBlockMode == AutoBlockMode.Hypixel) {
            if (isServerBlocking) {
                PacketUtil.sendPacket(
                    new UpdateSelectedSlotC2SPacket(
                        (C.p().getInventory().selectedSlot + 1) % 9
                    ),
                    false
                );
                PacketUtil.sendPacket(
                    new UpdateSelectedSlotC2SPacket(
                        C.p().getInventory().selectedSlot
                    ),
                    false
                );

                isServerBlocking = false;
                isClientBlocking = false;
            }
        } else if (autoBlockMode == AutoBlockMode.Packet) {
            if (isServerBlocking) {
                isServerBlocking = false;
                isClientBlocking = false;
            }
        } else if (autoBlockMode == AutoBlockMode.Fake) {
            isClientBlocking = false;
        }
    }

    @Override
    protected void onEnable() {
        lastTargetEnt = null;
        ticksSinceSwitch = Integer.MAX_VALUE;
        lastHitAt.clear();
        currentCps = null;
        cpsResetTickCounter = 1;
        clicksSoFar = 0;
        isServerBlocking = false;
        isClientBlocking = false;
        shouldUnblock = false;
        blink_ab_ticks = 0;
        blink_ab_blinking = false;
    }

    @Override
    protected void onDisable() {
        if (isServerBlocking) {
            if (autoBlockMode == AutoBlockMode.Old_Hypixel) {
                ab_unblock();
            } else if (autoBlockMode == AutoBlockMode.Blink) {
                PacketUtil.sendPacket(
                    new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                        BlockPos.ORIGIN,
                        Direction.DOWN
                    ),
                    false
                );
            } else {
                ab_unblock();
            }
        }
        isServerBlocking = false;
        isClientBlocking = false;

        if (blink_ab_blinking) {
            BlinkUtil.setOutgoingBlink(false);
            BlinkUtil.setIncomingBlink(false);
        }
    }

    private enum TargetMode {
        Single,
        Switch,
    }

    private enum SortingMode {
        Distance,
        Health,
        Hurt,
    }

    private enum SwingOrder {
        _1DOT8,
        _1DOT20,
    }

    private enum RotationMode {
        Smooth,
        Legit,
        Simple,
    }

    private enum AutoBlockMode {
        None,
        Fake,
        Packet,
        Custom,
        Blink,
        Old_Hypixel,
        Hypixel,
    }

    private enum Timing {
        None,
        Pre,
        Post_Attack,
        Post,
    }

    private enum BlockHand {
        Main_Hand,
        Off_hand,
    }

    private enum BlockMode {
        Packet,
        Fake,
        Click,
        Interact,
        Press,
    }

    private enum UnBlockMode {
        Packet,
        None,
        Swap_Slot,
        Fake,
        Release,
        Interact,
    }
}
