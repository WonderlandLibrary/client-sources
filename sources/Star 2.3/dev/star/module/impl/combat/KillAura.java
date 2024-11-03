package dev.star.module.impl.combat;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.star.Client;
import dev.star.commands.impl.FriendCommand;
import dev.star.event.impl.player.*;
import dev.star.event.impl.render.Render3DEvent;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.impl.movement.Scaffold;
import dev.star.module.impl.display.HUDMod;
import dev.star.module.settings.impl.*;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.player.InventoryUtils;
import dev.star.utils.player.RotationUtils;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.server.PacketUtils;
import dev.star.utils.time.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import dev.star.utils.addons.viamcp.vialoadingbase.ViaLoadingBase;
import dev.star.utils.addons.viamcp.viamcp.fixes.AttackOrder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class KillAura extends Module {
    private int cps;
    private int attackCounter;

    public static boolean attacking;
    public static boolean blocking;
    public static boolean wasBlocking;
    public static EntityLivingBase target;
    private final TimerUtil attackTimer = new TimerUtil();
    private final TimerUtil switchTimer = new TimerUtil();
    public static final List<EntityLivingBase> targets = new ArrayList<>();

    public static MultipleBoolSetting targetType = new MultipleBoolSetting("Target Type",
            new BooleanSetting("Players", true),
            new BooleanSetting("Mobs", false),
            new BooleanSetting("Animals", false),
            new BooleanSetting("Invisibles", true)
    );

    private final ModeSetting mode = new ModeSetting("Mode", "Single", "Single", "Multi");
    private final ModeSetting AttackMode = new ModeSetting("Attack Mode", "Cps", "Cps", "Ticks");

    private final NumberSetting attackDelay = new NumberSetting("Attack Delay", 2, 20, 1, 1);
    private final NumberSetting maxTargetAmount = new NumberSetting("Max Target Amount", 3, 50, 2, 1);
    private final NumberSetting minCPS = new NumberSetting("Min CPS", 10, 20, 1, 1);
    private final NumberSetting maxCPS = new NumberSetting("Max CPS", 10, 20, 1, 1);
    private final NumberSetting reach = new NumberSetting("Reach", 4, 6, 3, 0.1);
    private final NumberSetting AimValue = new NumberSetting("Start Value", 4, 6, 3, 0.1);
    private final BooleanSetting autoblock = new BooleanSetting("Autoblock", false);
    private final ModeSetting autoblockMode = new ModeSetting("Mode", "Fake", "Fake", "Watchdog", "Test");
    private final BooleanSetting rotations = new BooleanSetting("Rotations", true);
    private final ModeSetting rotationMode = new ModeSetting("Rotation Mode", "Vanilla", "Vanilla", "Smooth");
    private final NumberSetting rotsppedmax = new NumberSetting("Rot Speed", 1, 2, 0.1, 0.1);
    private final ModeSetting sortMode = new ModeSetting("Sort Mode", "Range", "Range", "Hurt Time", "Health", "Armor");

    public static final MultipleBoolSetting auraESP = new MultipleBoolSetting("Target ESP",
            new BooleanSetting("Nurikzapen", true),
            new BooleanSetting("Round", true),
            new BooleanSetting("Circle", true),
            new BooleanSetting("Tracer", false),
            new BooleanSetting("Box", false));

    private final MultipleBoolSetting addons = new MultipleBoolSetting("Addons",
            new BooleanSetting("Sprint", true),
            new BooleanSetting("Attack Through Walls", true),
            new BooleanSetting("Safe Rotations", false));

    private EntityLivingBase auraESPTarget;

    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks players");
        autoblockMode.addParent(autoblock, a -> autoblock.isEnabled());
        rotationMode.addParent(rotations, r -> rotations.isEnabled());
        attackDelay.addParent(AttackMode, m -> AttackMode.is("Ticks"));
        rotsppedmax.addParent(mode, m -> rotationMode.is("Smooth"));

        maxTargetAmount.addParent(mode, m -> mode.is("Multi"));
        this.addSettings(targetType, mode, AttackMode, maxTargetAmount, attackDelay, minCPS, maxCPS, AimValue, reach, autoblock, autoblockMode,
                rotations, rotationMode, rotsppedmax, sortMode, addons, auraESP);
    }

    @Override
    public void onDisable() {
        KillAura.target = null;
        targets.clear();
        blocking = false;
        attacking = false;
        if (wasBlocking) {
            PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.
                    Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        wasBlocking = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        attackCounter = 0;
        wasBlocking = false;
        blocking = false;
        attacking = false;
        if (autoblock.isEnabled() && autoblockMode.is("Watchdog") && ViaLoadingBase.getInstance().getTargetVersion().isOlderThan(ProtocolVersion.v1_16)) {
            NotificationManager.post(NotificationType.WARNING,"KillAura AutoBlock", "AutoBlock Requires ViaVersion 1.16 or Newer");
            super.onDisable();
            toggleSilent();
        }
        else {
            super.onEnable();
        }
    }

    @Override
    public void onMotionEvent(MotionEvent event) {
        this.setSuffix(mode.getMode());

        if (minCPS.getValue() > maxCPS.getValue()) {
            minCPS.setValue(minCPS.getValue() - 1);
        }

        if (KillAura.target == null) {
            attackCounter = attackDelay.getValue().intValue();
            if (wasBlocking) {
                PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                wasBlocking = false;
            }
        }

        // Gets all entities in specified range, sorts them using your specified sort mode, and adds them to target list
        this.sortTargets();

        if (event.isPre()) {
            attacking = !targets.isEmpty() && !Client.INSTANCE.isEnabled(Scaffold.class);
            blocking = autoblock.isEnabled() && attacking && InventoryUtils.isHoldingSword();
            if (attacking) {
                KillAura.target = targets.get(0);

                if (rotations.isEnabled()) {
                    float[] rotations = new float[]{0, 0};
                    switch (rotationMode.getMode()) {
                        case "Vanilla":
                            rotations = RotationUtils.getRotationsNeeded(KillAura.target);
                            break;
                        case "Smooth":
                            rotations = RotationUtils.getSmoothRotations(KillAura.target, rotsppedmax.getValue().floatValue());
                            break;
                    }
                    event.setRotations(rotations[0], rotations[1]);
                    RotationUtils.setVisualRotations(event.getYaw(), event.getPitch());
                }

                if (addons.getSetting("Safe Rotations").isEnabled() && !RotationUtils.isMouseOver(event.getYaw(), event.getPitch(), KillAura.target, AimValue.getValue().floatValue()))
                    return;

                if (mc.thePlayer.getDistanceToEntity(KillAura.target) <= reach.getValue()) {
                    if (AttackMode.is("Cps")) {
                        if (attackTimer.hasTimeElapsed(cps, true)) {
                            final int maxValue = (int) ((minCPS.getMaxValue() - maxCPS.getValue()) * 20);
                            final int minValue = (int) ((minCPS.getMaxValue() - minCPS.getValue()) * 20);
                            cps = MathUtils.getRandomInRange(minValue, maxValue);

                            if (mode.is("Multi")) {
                                for (EntityLivingBase entityLivingBase : targets) {
                                    AttackEvent attackEvent = new AttackEvent(entityLivingBase);
                                    Client.INSTANCE.getEventProtocol().handleEvent(attackEvent);

                                    if (!attackEvent.isCancelled()) {
                                        AttackOrder.sendFixedAttack(mc.thePlayer, entityLivingBase);
                                    }
                                }
                            } else {
                                AttackEvent attackEvent = new AttackEvent(KillAura.target);
                                Client.INSTANCE.getEventProtocol().handleEvent(attackEvent);

                                if (!attackEvent.isCancelled()) {
                                    AttackOrder.sendFixedAttack(mc.thePlayer, KillAura.target);
                                }
                            }
                        }
                    }
                    else if (AttackMode.is("Ticks")) {
                        if (++attackCounter >= attackDelay.getValue()) {

                            if (mode.is("Multi")) {
                                for (EntityLivingBase entityLivingBase : targets) {
                                    AttackEvent attackEvent = new AttackEvent(entityLivingBase);
                                    Client.INSTANCE.getEventProtocol().handleEvent(attackEvent);

                                    if (!attackEvent.isCancelled()) {
                                        AttackOrder.sendFixedAttack(mc.thePlayer, entityLivingBase);
                                    }
                                }
                            } else {
                                AttackEvent attackEvent = new AttackEvent(KillAura.target);
                                Client.INSTANCE.getEventProtocol().handleEvent(attackEvent);

                                if (!attackEvent.isCancelled()) {
                                    AttackOrder.sendFixedAttack(mc.thePlayer, KillAura.target);
                                }
                            }
                        }
                    }
                }
            } else {
                KillAura.target = null;
                switchTimer.reset();
            }
        }
        if (blocking) {
            switch (autoblockMode.getMode()) {
                case "Watchdog":
                    if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_16)) {
                        if (event.isPre() && wasBlocking && mc.thePlayer.ticksExisted % 4 == 0) {
                            PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            wasBlocking = false;
                        }

                        if (event.isPost() && !wasBlocking && KillAura.target != null) {
                            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, mc.thePlayer.getHeldItem(), 255, 255, 255));
                            wasBlocking = true;
                        }
                    } else {
                        NotificationManager.post(NotificationType.WARNING, "KillAura", "AutoBlock Requires ViaVersion 1.16 or Newer");
                    }
                    break;
                case "Test":
                    if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_16)) {
                        if (event.isPre() && KillAura.target != null) {
                            if (wasBlocking) {
                                PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.
                                        Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                            wasBlocking = true;

                        }
                    } else {
                        NotificationManager.post(NotificationType.WARNING, "KillAura", "AutoBlock Requires ViaVersion 1.16 or Newer");
                    }
                    break;
                case "Fake":
                    break;
            }
        } else if (wasBlocking && autoblockMode.is("Watchdog") && event.isPre() && (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_16))) {
            PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.
                    Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            wasBlocking = false;
        }
    }

    private void sortTargets() {
        targets.clear();
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(entity) <= AimValue.getValue() && isValid(entity) && mc.thePlayer != entityLivingBase && !FriendCommand.isFriend(entityLivingBase.getName())) {
                    targets.add(entityLivingBase);
                }
            }
        }
        switch (sortMode.getMode()) {
            case "Range":
                targets.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
                break;
            case "Hurt Time":
                targets.sort(Comparator.comparingInt(EntityLivingBase::getHurtTime));
                break;
            case "Health":
                targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                break;
            case "Armor":
                targets.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue));
                break;
        }
    }

    public boolean isValid(Entity entity) {
        if (entity instanceof EntityPlayer && !targetType.isEnabled("Players")) return false;

        if (entity instanceof EntityPlayer) {
            if (!Client.INSTANCE.getModuleCollection().getModule(Teams.class).canAttack((EntityPlayer) entity))
                return false;
        }
        if (addons.isEnabled("Attack Through Walls") && !mc.thePlayer.canEntityBeSeen(entity)) return false;

        if (entity.getClass().getPackage().getName().contains("monster") && !targetType.isEnabled("Mobs")) return false;

        if (entity.getClass().getPackage().getName().contains("passive") && !targetType.isEnabled("Animals"))
            return false;

        return !entity.isInvisible() || targetType.isEnabled("Invisibles");
    }

    @Override
    public void onKeepSprintEvent(KeepSprintEvent event) {
        if (addons.getSetting("Sprint").isEnabled()) {
            event.cancel();
        }
    }

    @Override
    public void onAttackEvent(AttackEvent event) {
        if (event.getTargetEntity() != null) {
            try {
                auraESPTarget = event.getTargetEntity();
            } catch (ClassCastException e) {
                auraESPTarget = null;
            }
        }
    }

    private final Animation auraESPAnim = new DecelerateAnimation(300, 1);

    @Override
    public void onRender3DEvent(Render3DEvent event) {
        auraESPAnim.setDirection(target != null ? Direction.FORWARDS : Direction.BACKWARDS);

        if (target != null) {
            auraESPTarget = target;
        }

        if (auraESPAnim.finished(Direction.BACKWARDS)) {
            auraESPTarget = null;
        }

        Color color = HUDMod.getClientColors().getFirst();
        Color color2 = HUDMod.getClientColors().getSecond();
        float dst = mc.thePlayer.getSmoothDistanceToEntity(auraESPTarget);

        if (auraESPTarget != null) {
            if (auraESP.getSetting("Box").isEnabled()) {
                RenderUtil.renderBoundingBox(auraESPTarget, color, auraESPAnim.getOutput().floatValue());
            }
            if (auraESP.getSetting("Circle").isEnabled()) {
                RenderUtil.drawCircle(auraESPTarget, event.getTicks(), .75f, color.getRGB(), auraESPAnim.getOutput().floatValue());
            }
            if (auraESP.getSetting("Tracer").isEnabled()) {
                RenderUtil.drawTracerLine(auraESPTarget, 4f, Color.BLACK, auraESPAnim.getOutput().floatValue());
                RenderUtil.drawTracerLine(auraESPTarget, 2.5f, color, auraESPAnim.getOutput().floatValue());
            } try {
                RenderUtil.drawTargetESP2D(Objects.requireNonNull(RenderUtil.targetESPSPos(auraESPTarget)).x, Objects.requireNonNull(RenderUtil.targetESPSPos(auraESPTarget)).y, color, color2,
                        (1.0f - MathHelper.clamp_float(Math.abs(dst - 6.0f) / 60.0f, 0f, 0.75f)) * 1, 1, auraESPAnim.getOutput().floatValue());
            } catch (Exception e) {
                auraESPTarget = null;
            }
        }
    }
}
