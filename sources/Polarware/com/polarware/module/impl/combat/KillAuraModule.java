package com.polarware.module.impl.combat;

import com.polarware.Client;
import com.polarware.component.impl.hypixel.InventoryDeSyncComponent;
import com.polarware.component.impl.player.BadPacketsComponent;
import com.polarware.component.impl.player.GUIDetectionComponent;
import com.polarware.component.impl.player.RotationComponent;
import com.polarware.component.impl.player.SlotComponent;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.event.impl.other.LegitClickEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.ClickEvent;
import com.polarware.event.impl.motion.PostMotionEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.event.impl.render.MouseOverEvent;
import com.polarware.event.impl.render.RenderItemEvent;
import com.polarware.util.RayCastUtil;
import com.polarware.util.math.MathUtil;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.rotation.RotationUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import util.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @since 11/17/2021
 */
@ModuleInfo(name = "module.combat.killaura.name", description = "module.combat.killaura.description", category = Category.COMBAT)
public final class KillAuraModule extends Module {
    private final ModeValue mode = new ModeValue("Attack Mode", this)
            .add(new SubMode("Single"))
            .add(new SubMode("Switch"))
            .add(new SubMode("Multiple"))
            .setDefault("Single");

    private final ModeValue autoBlock = new ModeValue("Auto Block", this)
            .add(new SubMode("None"))
            .add(new SubMode("Fake"))
            .add(new SubMode("Vanilla"))
            .add(new SubMode("NCP"))
            .add(new SubMode("Watchdog"))
            .add(new SubMode("Watchdog HvH"))
            .add(new SubMode("Legit"))
            .add(new SubMode("Intave"))
            .add(new SubMode("Old Intave"))
            .add(new SubMode("Imperfect Vanilla"))
            .add(new SubMode("Vanilla ReBlock"))
            .add(new SubMode("New NCP"))
            .setDefault("None");

    private final ModeValue clickMode = new ModeValue("Click Delay Mode", this)
            .add(new SubMode("Normal"))
            .add(new SubMode("Hit Select"))
            .add(new SubMode("1.9+"))
            .add(new SubMode("1.9+ (1.8 Visuals)"))
            .setDefault("Normal");

    private final NumberValue range = new NumberValue("Range", this, 3, 3, 6, 0.1);
    private final BoundsNumberValue cps = new BoundsNumberValue("CPS", this, 10, 15, 1, 20, 1);
    private final BoundsNumberValue rotationSpeed = new BoundsNumberValue("Rotation speed", this, 5, 10, 0, 10, 1);
    private final ListValue<MovementFix> movementCorrection = new ListValue<>("Movement correction", this);
    private final BooleanValue keepSprint = new BooleanValue("Keep sprint", this, false);
    private final BooleanValue rayCast = new BooleanValue("Ray cast", this, false);
    private final BooleanValue preswing = new BooleanValue("Pre Swing", this, true);
    private final BooleanValue advanced = new BooleanValue("Advanced", this, false);
    private final BooleanValue lookAtTheClosestPoint = new BooleanValue("Look at the closest point on the player", this, true, () -> !advanced.getValue());
    private final ModeValue rotationMode = new ModeValue("Rotation Mode", this, () -> !advanced.getValue())
            .add(new SubMode("Legit/Normal"))
            .add(new SubMode("Autistic AntiCheat"))
            .setDefault("Legit/Normal");
    private final BooleanValue attackWhilstScaffolding = new BooleanValue("Attack whilst Scaffolding", this, false, () -> !advanced.getValue());
    private final BooleanValue noSwing = new BooleanValue("No swing", this, false, () -> !advanced.getValue());
    private final BooleanValue autoDisable = new BooleanValue("Auto disable", this, false, () -> !advanced.getValue());
    private final BooleanValue grimFalse = new BooleanValue("Prevent Grim false positives", this, false, () -> !advanced.getValue());

    private final BooleanValue showTargets = new BooleanValue("Targets", this, false);
    public final BooleanValue player = new BooleanValue("Player", this, false, () -> !showTargets.getValue());
    public final BooleanValue invisibles = new BooleanValue("Invisibles", this, false, () -> !showTargets.getValue());
    public final BooleanValue animals = new BooleanValue("Animals", this, false, () -> !showTargets.getValue());
    public final BooleanValue mobs = new BooleanValue("Mobs", this, false, () -> !showTargets.getValue());
    public final BooleanValue teams = new BooleanValue("Teams", this, false, () -> !showTargets.getValue());


    private final StopWatch attackStopWatch = new StopWatch();
    private final StopWatch clickStopWatch = new StopWatch();

    private float randomYaw, randomPitch, angle;
    private boolean blocking, swing, allowAttack;
    private long nextSwing;
    private Vec3 targetPoint;

    public static List<EntityLivingBase> targets;
    public static List<Entity> pastTargets = new ArrayList<>();
    public static Entity target;
    private boolean justSwitchedBetweenPoints;

    public StopWatch subTicksStopWatch = new StopWatch();
    private int attack, hitTicks, expandRange;


    public KillAuraModule() {
        for (MovementFix movementFix : MovementFix.values()) {
            movementCorrection.add(movementFix);
        }

        movementCorrection.setDefault(MovementFix.OFF);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.hitTicks++;

        if (GUIDetectionComponent.inGUI()) {
            return;
        }

        if (target == null || mc.thePlayer.isDead || this.getModule(ScaffoldModule.class).isEnabled()) {
            this.unblock(false);
            target = null;
        }
    };

    @Override
    protected void onEnable() {
        this.attack = 0;
    }

    @Override
    public void onDisable() {
        target = null;

        this.unblock(false);
    }

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        if (this.autoDisable.getValue()) {
            this.toggle();
        }
    };

    public void getTargets(double range) {
        targets = Client.INSTANCE.getTargetComponent().getTargets(range);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer.getHealth() <= 0.0 && this.autoDisable.getValue()) {
            this.toggle();
        }

        if (getModule(ScaffoldModule.class).isEnabled() && !attackWhilstScaffolding.getValue()) {
            return;
        }

        this.attack = Math.max(Math.min(this.attack, this.attack - 2), 0);

        /*
         * Historic fix
         */
        if (mc.thePlayer.ticksExisted % 20 == 0) {
            expandRange = (int) (2 + Math.random() * 3);
        }

        if (GUIDetectionComponent.inGUI()) {
            return;
        }

        /*
         * Getting targets and selecting the nearest one
         */
        this.getTargets(range.getValue().doubleValue() + expandRange);

        if (targets.isEmpty()) {
            this.randomiseTargetRotations();
            target = null;
            return;
        }

        target = targets.get(0);

        if (target == null || mc.thePlayer.isDead) {
            this.randomiseTargetRotations();
            return;
        }


        if (this.canBlock()) {
            this.preBlock();
        }

        /*
         * Calculating rotations to target
         */
        this.rotations();

        /*
         * Doing the attack
         */
       // this.doAttack(targets);

        /*
         * Blocking
         */
        if (this.canBlock()) {
            this.postAttackBlock();
        }
    };

    @EventLink()
    public final Listener<com.polarware.event.impl.input.LegitClickEvent> onPossibleCLick = event -> {
        /*
         * Doing the attack
         */
        if(target != null) {
            this.doAttack(targets);
        }
    };

    public void rotations() {
        final double minRotationSpeed = this.rotationSpeed.getValue().doubleValue();
        final double maxRotationSpeed = this.rotationSpeed.getSecondValue().doubleValue();
        final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);

        switch (rotationMode.getValue().getName()) {
            case "Legit/Normal":
                if(preswing.getValue() && !noSwing.getValue() ) {
                    mc.thePlayer.swingItem();
                }
                this.bypassHeuristics();
                Vector2f targetRotations = RotationUtil.calculate(target, lookAtTheClosestPoint.getValue(), range.getValue().doubleValue());
                Vector2f targetPointRotations = RotationUtil.calculate(targetPoint);
                if (justSwitchedBetweenPoints) {
                     targetRotations = new Vector2f((targetPointRotations.x - targetRotations.x) / 2, (targetPointRotations.y - targetRotations.y) / 2);
                     justSwitchedBetweenPoints = false;
                }

                this.randomiseTargetRotations();

                targetRotations.x += randomYaw;
                targetRotations.y += randomPitch;
                //targetRotations.y *= 0.66667f;

                if (RayCastUtil.rayCast(targetRotations, range.getValue().doubleValue()).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
                    randomYaw = randomPitch = 0;
                }


                if (rotationSpeed != 0) {
                    RotationComponent.setRotations(targetRotations, rotationSpeed,
                            movementCorrection.getValue() == MovementFix.OFF ? MovementFix.OFF : movementCorrection.getValue());
                }
                break;

            case "Autistic AntiCheat":
                double speed = rotationSpeed * 10;
                RotationComponent.setRotations(new Vector2f((float) (RotationComponent.rotations.x + speed), 0), speed / 18,
                        movementCorrection.getValue() == MovementFix.OFF ? MovementFix.OFF : movementCorrection.getValue());
                break;
        }

    }

    /*
     * Randomising rotation target to simulate legit players
     */
    private void randomiseTargetRotations() {
        randomYaw += (float) (Math.random() - 0.5f);
        randomPitch += (float) (Math.random() - 0.5f) * 2;
    }

    private void bypassHeuristics() {
        if (justSwitchedBetweenPoints) {
            justSwitchedBetweenPoints = false;
        }
        if (attackStopWatch.finished((long) MathUtil.getRandom(500, 2000))) {
            targetPoint = new Vec3(MathUtil.getRandom(target.getEntityBoundingBox().minX, target.getEntityBoundingBox().maxX), target.posY + MathUtil.getRandom(-0.5, 0.5), MathUtil.getRandom(target.getEntityBoundingBox().minZ, target.getEntityBoundingBox().maxZ));

            justSwitchedBetweenPoints = true;
        };
    }

    @EventLink
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        event.setRange(event.getRange() + range.getValue().doubleValue() - 3);
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (target != null && this.canBlock()) {
            this.postBlock();
        }
    };

    private void doAttack(final List<EntityLivingBase> targets) {
        String autoBlock = this.autoBlock.getValue().getName();
        if (BadPacketsComponent.bad(false, true, true, true, true) &&
                (autoBlock.equals("Fake") || autoBlock.equals("None") ||
                        autoBlock.equals("Imperfect Vanilla") || autoBlock.equals("Vanilla ReBlock"))) {
            return;
        }

        double delay = -1;
        boolean flag = false;

        switch (clickMode.getValue().getName()) {
            case "Hit Select": {
                try {
                    delay = 9;
                    flag = target.hurtResistantTime <= 10;
                } catch (Throwable ignored) {};
                break;
            }

            case "1.9+": {
                double speed = 4;

                if (mc.thePlayer.getHeldItem() != null) {
                    final Item item = mc.thePlayer.getHeldItem().getItem();

                    if (item instanceof ItemSword) {
                        speed = 1.6;
                    } else if (item instanceof ItemSpade) {
                        speed = 1;
                    } else if (item instanceof ItemPickaxe) {
                        speed = 1.2;
                    } else if (item instanceof ItemAxe) {
                        switch (((ItemAxe) item).getToolMaterial()) {
                            case WOOD:
                            case STONE:
                                speed = 0.8;
                                break;

                            case IRON:
                                speed = 0.9;
                                break;

                            default:
                                speed = 1;
                                break;
                        }
                    } else if (item instanceof ItemHoe) {
                        switch (((ItemHoe) item).getToolMaterial()) {
                            case WOOD:
                            case GOLD:
                                speed = 1;
                                break;

                            case STONE:
                                speed = 2;
                                break;

                            case IRON:
                                speed = 3;
                                break;
                        }
                    }
                }

                delay = 1 / speed * 20 - 1;
                break;
            }
        }

        if (attackStopWatch.finished(this.nextSwing) && (!grimFalse.getValue() || !(mc.thePlayer.ticksSprint <= 1 && mc.thePlayer.isSprinting())) && !BadPacketsComponent.bad(false, true, true, false, true) && target != null && (clickStopWatch.finished((long) (delay * 50)) || flag)) {
            final long clicks = (long) (Math.round(MathUtil.getRandom(this.cps.getValue().intValue(), this.cps.getSecondValue().intValue())));
            this.nextSwing = 1000 / clicks;


            if (attackStopWatch.finished(nextSwing)) {
                this.allowAttack = true;

                if (this.canBlock()) {
                    this.attackBlock();
                }

                if (this.allowAttack) {
                    /*
                     * Attacking target
                     */
                    final double range = this.range.getValue().doubleValue();
                    final MovingObjectPosition movingObjectPosition = mc.objectMouseOver;

                    switch (this.mode.getValue().getName()) {
                        case "Single": {
                            if ((mc.thePlayer.getDistanceToEntity(target) <= range && !rayCast.getValue()) ||
                                    (rayCast.getValue() && movingObjectPosition != null && movingObjectPosition.entityHit == target)) {
                                mc.thePlayer.swingItem();
                                this.attack(target);
                            } else if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                                mc.thePlayer.swingItem();
                                this.attack(movingObjectPosition.entityHit);
                            } else {
                                switch (clickMode.getValue().getName()) {
                                    case "Normal":
                                    case "Hit Select":
                                        this.clickStopWatch.reset();
                                        this.hitTicks = 0;
                                        break;
                                }
                            }
                            break;
                        }

                        case "Multiple": {
                            targets.removeIf(target -> mc.thePlayer.getDistanceToEntity(target) > range);

                            if (!targets.isEmpty()) {
                                targets.forEach(this::attack);
                            }
                            break;
                        }
                    }
                }

                this.attackStopWatch.reset();
            }
        }
    }

    private void attack(final Entity target) {
        this.attack = Math.min(Math.max(this.attack, this.attack + 2), 5);

        Client.INSTANCE.getEventBus().handle(new ClickEvent());
        if (!this.noSwing.getValue()) mc.thePlayer.swingItem();

        final AttackEvent event = new AttackEvent(target);
        Client.INSTANCE.getEventBus().handle(event);

        if (!event.isCancelled()) {
            if (this.keepSprint.getValue()) {
                mc.playerController.syncCurrentPlayItem();

                PacketUtil.send(new C02PacketUseEntity(event.getTarget(), C02PacketUseEntity.Action.ATTACK));

                if (mc.thePlayer.fallDistance > 0 && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && mc.thePlayer.ridingEntity == null) {
                    mc.thePlayer.onCriticalHit(target);
                }
            } else {
                mc.playerController.attackEntity(mc.thePlayer, target);
            }
        }

        this.clickStopWatch.reset();
        this.hitTicks = 0;

//        if (!pastTargets.contains(target)) pastTargets.add(target);
    }

    private void block(final boolean check, final boolean interact) {
        if (!blocking || !check) {
            if (interact && target != null && mc.objectMouseOver.entityHit == target) {
                mc.playerController.interactWithEntitySendPacket(mc.thePlayer, target);
            }

            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
            blocking = true;
        }
    }

    private void unblock(final boolean swingCheck) {
        if (blocking && (!swingCheck || !swing)) {
            if (!mc.gameSettings.keyBindUseItem.isKeyDown()) {
                PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else {
                mc.gameSettings.keyBindUseItem.setPressed(false);
            }
            blocking = false;
        }
    }

    @EventLink(value = Priority.HIGH)
    public final Listener<RenderItemEvent> onRenderItem = event -> {
        if (target != null && !autoBlock.getValue().getName().equals("None") && this.canBlock()) {
            event.setEnumAction(EnumAction.BLOCK);
            event.setUseItem(true);
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof C0APacketAnimation) {
            swing = true;
        } else if (packet instanceof C03PacketPlayer) {
            swing = false;
        }

        if ((packet instanceof C0APacketAnimation || packet instanceof C02PacketUseEntity) &&
                this.mode.getValue().getName().equals("1.9+ (1.8 Visuals)")) {

        }

        this.packetBlock(event);
    };

    public void packetBlock(final PacketSendEvent event) {
        final Packet<?> packet = event.getPacket();

        switch (autoBlock.getValue().getName()) {
            case "Intave":
                if (packet instanceof C03PacketPlayer) {
                    event.setCancelled(true);
                    this.unblock(false);
                    PacketUtil.sendNoEvent(packet);
                    this.block(false, true);
                }
                break;

            case "Fake":
            case "None":
                if (SlotComponent.getItemStack() == null || !(SlotComponent.getItemStack().getItem() instanceof ItemSword)) {
                    return;
                }

                if (packet instanceof C08PacketPlayerBlockPlacement) {
                    final C08PacketPlayerBlockPlacement wrapper = (C08PacketPlayerBlockPlacement) packet;

                    if (wrapper.getPlacedBlockDirection() == 255) {
                        event.setCancelled(true);
                    }
                } else if (packet instanceof C07PacketPlayerDigging) {
                    C07PacketPlayerDigging wrapper = ((C07PacketPlayerDigging) packet);

                    if (wrapper.getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                        event.setCancelled(true);
                    }
                }
                break;
        }
    }

    private void attackBlock() {
        if ("Legit".equals(autoBlock.getValue().getName())) {
            if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                mc.gameSettings.keyBindUseItem.setPressed(false);
            }

            this.allowAttack = !BadPacketsComponent.bad(false, false, false, true, false);
        }
    }

    private void postAttackBlock() {
        switch (autoBlock.getValue().getName()) {
            case "Legit":
                if (this.hitTicks == 1) {
                    mc.gameSettings.keyBindUseItem.setPressed(true);
                    this.blocking = true;
                }
                break;

            case "Intave":
                this.block(false, false);
                break;

            case "Vanilla":
                if (this.hitTicks != 0) {
                    this.block(false, true);
                }
                break;

            case "Imperfect Vanilla":
                if (this.hitTicks == 1 && mc.thePlayer.isSwingInProgress && Math.random() > 0.1) {
                    this.block(false, true);
                }
                break;

            case "Vanilla ReBlock":
                if (this.hitTicks == 1 || !this.blocking) {
                    this.block(false, true);
                }
                break;

            case "Watchdog":
                if (mc.thePlayer.ticksSincePlayerVelocity >= 5 + (Math.random() * 4) && mc.thePlayer.ticksSincePlayerVelocity <= 20 && !this.blocking) {
                    this.block(true, true);
                }

                if (mc.thePlayer.ticksSincePlayerVelocity >= 16 + (Math.random() * 4) && this.blocking) {
                    this.unblock(true);
                }
                break;

            case "Watchdog HvH":
                mc.gameSettings.keyBindUseItem.setPressed(true);
                if ((this.hitTicks == 1 || !this.blocking) && !BadPacketsComponent.bad(false, true, true, true, false)) {
                    this.block(false, true);
                }
                break;
        }
    }

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if (autoBlock.getValue().getName().equals("Watchdog HvH")) {
            event.setCancelled(false);
            event.setStrafeMultiplier(0.2F);
            event.setForwardMultiplier(0.2F);
        }
    };

    private void preBlock() {
        switch (autoBlock.getValue().getName()) {
            case "NCP":
            case "Intave":
                this.unblock(false);
                break;

            case "New NCP":
                if (this.blocking) {
                    PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
                    PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
                    this.blocking = false;
                }
                break;

            case "Old Intave":
//                InventoryDeSyncComponent.setActive("/booster");

                if (mc.thePlayer.isUsingItem()) {
                    PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
                    PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
                }
                break;
        }
    }

    private void postBlock() {
        switch (autoBlock.getValue().getName()) {
            case "NCP":
            case "New NCP":
                this.block(true, false);
                break;

            case "Old Intave":
                if (mc.thePlayer.isUsingItem() && InventoryDeSyncComponent.isDeSynced()) {
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                }
                break;
        }
    }

    private boolean canBlock() {
        return SlotComponent.getItemStack() != null && SlotComponent.getItemStack().getItem() instanceof ItemSword;
    }

    @EventLink()
    public final Listener<LegitClickEvent> onLegitClick = event -> {
        targets = Client.INSTANCE.getTargetComponent().getTargets(range.getValue().doubleValue() + expandRange);

        if (targets.isEmpty()) {
            this.randomiseTargetRotations();
            target = null;
            return;
        }

        this.doAttack(targets);
    };
}