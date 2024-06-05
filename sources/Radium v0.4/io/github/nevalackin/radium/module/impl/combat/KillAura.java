package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.event.impl.player.MoveFlyingEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.render.Render2DEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.StringUtils;
import io.github.nevalackin.radium.utils.*;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;

@ModuleInfo(label = "Kill Aura", key = Keyboard.KEY_R, category = ModuleCategory.COMBAT)
public final class KillAura extends Module {

    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(
            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(
            new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);
    private final EnumProperty<AuraMode> auraModeProperty = new EnumProperty<>("Mode", AuraMode.PRIORITY);
    private final EnumProperty<SortingMethod> sortingMethodProperty = new EnumProperty<>("Sorting Method", SortingMethod.HEALTH);
    private final EnumProperty<AttackMethod> attackMethodProperty = new EnumProperty<>("Attack Method", AttackMethod.POST);
    private final DoubleProperty minApsProperty = new DoubleProperty("Min APS", 8.0, 1.0, 20.0, 0.5);
    private final DoubleProperty maxApsProperty = new DoubleProperty("Max APS", 10.0, 1.0, 20.0, 0.5);
    private final DoubleProperty rangeProperty = new DoubleProperty("Range", 4.3, 3.0,
            6.0, 0.1, Representation.DISTANCE);
    private final Property<Boolean> autoblockProperty = new Property<>("Autoblock", true);
    private final DoubleProperty blockRangeProperty = new DoubleProperty("Block Range", 8.0,
            autoblockProperty::getValue,
            3.0, 8.0, 0.1, Representation.DISTANCE);
    private final Property<Boolean> smoothAimProperty = new Property<>("Smooth Aim", false);
    private final DoubleProperty maxAngleChangeProperty = new DoubleProperty("Max Angle Change", 45.0,
            smoothAimProperty::getValue, 1.0, 360.0, 1.0);
    private final Property<Boolean> randomAimPatternProperty = new Property<>("Random Aim Pattern", true);
    private final DoubleProperty rapFactorProperty = new DoubleProperty("RAP Factor", 2.0,
            randomAimPatternProperty::getValue, 0.1, 30.0, 0.1);
    private final Property<Boolean> lockViewProperty = new Property<>("Lock View", false);
    public final Property<Boolean> aacRotationStrafing = new Property<>("Server Side Yaw Movement", false,
            () -> !lockViewProperty.getValue());
    private final Property<Boolean> keepSprintProperty = new Property<>("Keep Sprint", true);
    private final Property<Boolean> rayTraceProperty = new Property<>("Ray Trace", false);
    private final Property<Boolean> ignoreTeamMatesProperty = new Property<>("Ignore Team Mates", false);
    private final Property<Boolean> targetHudProperty = new Property<>("Target Hud", true);
    private final Property<Boolean> forceUpdateProperty = new Property<>("Force Update", false);
    private final TimerUtil attackTimer = new TimerUtil();
    public int waitTicks;
    private EntityLivingBase target;
    private boolean blocking;
    private boolean entityNearby;

    public KillAura() {
        setSuffixListener(auraModeProperty);
    }

    public static boolean isBlocking() {
        return getInstance().isEnabled() &&
                getInstance().autoblockProperty.getValue() &&
                getInstance().entityNearby;
    }

    public static KillAura getInstance() {
        return ModuleManager.getInstance(KillAura.class);
    }

    public static double getEffectiveHealth(EntityLivingBase entity) {
        // TODO: This is a shit
        return entity.getHealth() / (25.0F / entity.getTotalArmorValue());
    }

    public EntityLivingBase getTarget() {
        return target;
    }

    @Override
    public void onDisable() {
        if (blocking) {
            blocking = false;
            Wrapper.sendPacketDirect(PLAYER_DIGGING);
        }
        target = null;
        entityNearby = false;
    }

    @Listener
    public void onMoveFlyingEvent(MoveFlyingEvent event) {
        if (getTarget() != null && aacRotationStrafing.getValue() && !lockViewProperty.getValue())
            event.setYaw(Wrapper.getPlayer().currentEvent.getYaw());
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent event) {
        if (event.getPacket() instanceof C0APacketAnimation)
            attackTimer.reset();
    }

    @Listener
    public void onRender2DEvent(Render2DEvent e) {
        if (target != null && targetHudProperty.getValue()) {
            LockedResolution lr = e.getResolution();
            int skinResolution = 32;
            int width = 100;


        }
    }

    @Listener(Priority.LOW)
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (waitTicks > 0) {
                --waitTicks;
                return;
            }
            entityNearby = false;
            final float range = rangeProperty.getValue().floatValue();
            final float blockRange = blockRangeProperty.getValue().floatValue();
            EntityLivingBase optimalTarget = null;

//            List<EntityPlayer> entities = Wrapper.getLoadedPlayersNoNPCs();
            List<EntityLivingBase> entities = Wrapper.getLivingEntities();

            entities.sort(sortingMethodProperty.getValue().sorter);

            for (EntityLivingBase entity : entities) {
                if (isValid(entity)) {
                    float dist = Wrapper.getPlayer().getDistanceToEntity(entity);

                    if (!entityNearby && dist < blockRange)
                        entityNearby = true;

                    if (dist <= range) {
                        optimalTarget = entity;
                        break;
                    }
                }
            }

            target = optimalTarget;

            if (optimalTarget != null) {
                if (Wrapper.getTimer().timerSpeed > 1.0F)
                    Wrapper.getTimer().timerSpeed = 1.0F;

                float[] rotations;
                if (smoothAimProperty.getValue())
                    rotations = getSmoothedRotationsToEntity(optimalTarget,
                            lockViewProperty.getValue() ? Wrapper.getPlayer().rotationYaw : event.getPrevYaw(),
                            lockViewProperty.getValue() ? Wrapper.getPlayer().rotationPitch : event.getPrevPitch());
                else
                    rotations = RotationUtils.getRotationsToEntity(optimalTarget);

                float yaw = rotations[0];
                float pitch = rotations[1];

                if (randomAimPatternProperty.getValue()) {
                    if (pitch > 0.0F)
                        pitch += Math.random() * rapFactorProperty.getValue();
                    else
                        pitch -= Math.random() * rapFactorProperty.getValue();
                    final double yawRandom = Math.random() * rapFactorProperty.getValue();
                    if (yawRandom > yawRandom / 2)
                        yaw += yawRandom;
                    else
                        yaw -= yawRandom;
                }

                event.setYaw(yaw);
                event.setPitch(pitch);

                if (lockViewProperty.getValue()) {
                    Wrapper.getPlayer().rotationYaw = yaw;
                    Wrapper.getPlayer().rotationPitch = pitch;
                }

                if (forceUpdateProperty.getValue())
                    Wrapper.sendPacketDirect(
                            new C03PacketPlayer.C06PacketPlayerPosLook(
                                    event.getPosX(), event.getPosY(), event.getPosZ(),
                                    event.getYaw(), event.getPitch(), event.isOnGround()));

                if (attackMethodProperty.getValue() == AttackMethod.PRE)
                    tryAttack(event);
            }

            if (blocking) {
                blocking = false;
                if (isHoldingSword())
                    Wrapper.sendPacketDirect(PLAYER_DIGGING);
            }

        } else if (waitTicks <= 0) {
            if (target != null && attackMethodProperty.getValue() == AttackMethod.POST)
                tryAttack(event);

            if (entityNearby && autoblockProperty.getValue() && isHoldingSword()) {
                Wrapper.getPlayer().setItemInUse(Wrapper.getPlayer().getCurrentEquippedItem(),
                        Wrapper.getPlayer().getCurrentEquippedItem().getMaxItemUseDuration());
                if (!blocking) {
                    Wrapper.sendPacketDirect(BLOCK_PLACEMENT);
                    blocking = true;
                }
            }
        }
    }

    private void tryAttack(UpdatePositionEvent event) {
        final double min = minApsProperty.getValue();
        final double max = maxApsProperty.getValue();
        final double cps;
        if (min == max)
            cps = min;
        else
            cps = RandomUtils.getRandomInRange(minApsProperty.getValue(), maxApsProperty.getValue());
        if (attackTimer.hasElapsed(1000L / (long) cps) &&
                isLookingAtEntity(
                        event.getYaw(),
                        event.getPitch(),
                        target,
                        rayTraceProperty.getValue())) {
            attack(target);

            if (!keepSprintProperty.getValue() && Wrapper.getPlayer().isSprinting()) {
                Wrapper.getPlayer().motionX *= 0.6D;
                Wrapper.getPlayer().motionZ *= 0.6D;
                Wrapper.getPlayer().setSprinting(false);
            }
        }
    }

    // TODO: Thru blocks
    private boolean isLookingAtEntity(float yaw,
                                      float pitch,
                                      Entity entity,
                                      boolean rayTrace) {
        double range = rangeProperty.getValue();
        Vec3 src = Wrapper.getPlayer().getPositionEyes(1.0F);
        Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        Vec3 dest = src.addVector(rotationVec.xCoord * range, rotationVec.yCoord * range, rotationVec.zCoord * range);
        // Note: This is the same bounding box logic minecraft uses
        AxisAlignedBB entBB = entity.getEntityBoundingBox().expand(0.1F, 0.1F, 0.1F);

        if (rayTrace) {
            MovingObjectPosition obj = Wrapper.getWorld().rayTraceBlocks(src, dest,
                    false, false, false);

            if (obj != null) {
                if (obj.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
                    return false;
                if (obj.hitVec.distanceTo(src) > range)
                    return false;
            }
        }

        return entBB.calculateIntercept(src, dest) != null;
    }

    private boolean isHoldingSword() {
        return Wrapper.getPlayer().getCurrentEquippedItem() != null &&
                Wrapper.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

    private boolean isValid(EntityLivingBase entity) {
        return entity instanceof EntityMob &&
                entity.isEntityAlive() &&
                !entity.isInvisible() &&
                (!ignoreTeamMatesProperty.getValue() || !StringUtils.isTeamMate(entity));
    }

    private void attack(EntityLivingBase e) {
        Wrapper.getPlayer().swingItem();
        Wrapper.sendPacketDirect(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
    }

    private float[] getSmoothedRotationsToEntity(Entity entity,
                                                 float prevYaw,
                                                 float prevPitch) {
        final EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = entity.posX - player.posX;
        double zDist = entity.posZ - player.posZ;

        double entEyeHeight = entity.getEyeHeight();
        double yDist = ((entity.posY + entEyeHeight) - Math.min(Math.max(entity.posY - player.posY, 0), entEyeHeight)) -
                (player.posY + player.getEyeHeight());

        double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);

        float yaw = interpolateRotation(prevYaw, (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F);
        float pitch = interpolateRotation(prevPitch, (float) (-(Math.atan2(yDist, fDist) * 180.0D / Math.PI)));
        return new float[]{yaw, pitch};
    }

    private float interpolateRotation(float p_70663_1_,
                                      float p_70663_2_) {
        float maxTurn = maxAngleChangeProperty.getValue().floatValue() / 5.0F;
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (var4 > maxTurn) {
            var4 = maxTurn;
        }

        if (var4 < -maxTurn) {
            var4 = -maxTurn;
        }

        return p_70663_1_ + var4;
    }

    private enum AuraMode {
        PRIORITY
    }

    private enum AttackMethod {
        PRE, POST
    }

    private enum SortingMethod {
        DISTANCE(new DistanceSorting()),
        HEALTH(new HealthSorting()),
        HURTTIME(new HurtTimeSorting());

        private final Comparator<EntityLivingBase> sorter;

        SortingMethod(Comparator<EntityLivingBase> sorter) {
            this.sorter = sorter;
        }
    }

    private static class DistanceSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return (int) (o1.getDistanceToEntity(Wrapper.getPlayer()) - o2.getDistanceToEntity(Wrapper.getPlayer()));
        }
    }

    private static class HealthSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return (int) (getEffectiveHealth(o1) - getEffectiveHealth(o2));
        }
    }

    private static class HurtTimeSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            int h1 = EntityLivingBase.MAX_HURT_RESISTANT_TIME - o1.hurtResistantTime;
            int h2 = EntityLivingBase.MAX_HURT_RESISTANT_TIME - o2.hurtResistantTime;
            if (h1 == h2)
                return 0;
            if (h1 == 0)
                return -1;
            if (h2 == 0)
                return 1;
            return h2 - h1;
        }
    }
}
