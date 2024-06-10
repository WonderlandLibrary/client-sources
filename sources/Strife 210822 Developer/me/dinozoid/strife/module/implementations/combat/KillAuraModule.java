package me.dinozoid.strife.module.implementations.combat;

import com.google.common.base.Predicates;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.player.ScaffoldModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import me.dinozoid.strife.util.world.WorldUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;

import java.util.Comparator;
import java.util.List;

@ModuleInfo(name = "KillAura", renderName = "KillAura", description = "Automatically attack entities.", aliases = "Aura", category = Category.COMBAT)
public final class KillAuraModule extends Module {

    private final EnumProperty<KillAuraMode> modeProperty = new EnumProperty<>("Mode", KillAuraMode.SINGLE);

    private final DoubleProperty switchDelayProperty = new DoubleProperty("Switch Delay", 50, 1, 1000, 25, Property.Representation.INT, () -> modeProperty.value() == KillAuraMode.SWITCH);
    private final EnumProperty<SortMode> sortProperty = new EnumProperty("Sort by", SortMode.HEALTH, () -> modeProperty.value() == KillAuraMode.SINGLE);

    private final DoubleProperty minAPSProperty = new DoubleProperty("Min APS", 11, 1, 20, 1, Property.Representation.INT);
    private final DoubleProperty maxAPSProperty = new DoubleProperty("Max APS", 14, 1, 20, 1, Property.Representation.INT);

    private final EnumProperty<AttackMode> attackModeProperty = new EnumProperty("Attack in", AttackMode.PRE);

    private final DoubleProperty rangeProperty = new DoubleProperty("Range", 4.2, 1, 7, 0.1);
    private final DoubleProperty wallRangeProperty = new DoubleProperty("Wall Range", 4.2, 1, 7, 0.1);
    private final DoubleProperty blockRangeProperty = new DoubleProperty("Block Range", 4.2, 1, 10, 0.1);
    private final DoubleProperty fovRangeProperty = new DoubleProperty("Fov Range", 180, 1, 180, 10, Property.Representation.INT);

    private final Property<Boolean> rayTraceProperty = new Property("Ray Trace", false);
    private final Property<Boolean> rotationsProperty = new Property("Rotations", true);
    private final Property<Boolean> lockViewProperty = new Property("Lock View", false);
    private final Property<Boolean> autoBlockProperty = new Property("Autoblock", true);
    private final Property<Boolean> targetHUDProperty = new Property("Target HUD", true);
    private final Property<Boolean> noSwingProperty = new Property("No Swing", false);
    private final Property<Boolean> keepSprintProperty = new Property("Keep Sprint", true);

    private final EnumProperty<TargetHUDMode> targetHUDModeProperty = new EnumProperty("Target HUD Mode", TargetHUDMode.STRIFE, targetHUDProperty::value);

    private final TimerUtil switchTimer = new TimerUtil();
    private final TimerUtil attackTimer = new TimerUtil();

    private int targetIndex;
    private EntityLivingBase target;

    @Override
    public void init() {
        super.init();
        addValueChangeListener(modeProperty);
    }

    @Override
    public void onEnable() {
        switchTimer.reset();
        attackTimer.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        targetIndex = 0;
        target = null;
    }

    public void directUpdate(UpdatePlayerEvent event) {
        if(!toggled()) return;
        if(ScaffoldModule.instance().toggled()) return;
        if(event.state() == EventState.PRE) {
            EntityLivingBase optimalTarget = null;
            target = null;
            List<EntityLivingBase> livingEntities = WorldUtil.getLivingEntities(Predicates.and(KillAuraModule::isValid, this::distanceCheck));
            livingEntities.sort(sortProperty.value().sorter());
            if(livingEntities.size() > 0) {
                if(modeProperty.value() == KillAuraMode.SWITCH) {
                    if(switchTimer.hasElapsed(switchDelayProperty.value().longValue())) {
                        targetIndex++;
                        switchTimer.reset();
                    }
                    if(targetIndex >= livingEntities.size())
                        targetIndex = 0;
                }
                optimalTarget = livingEntities.get(targetIndex);
                target = optimalTarget;
            }
            if(optimalTarget != null && distanceCheck(optimalTarget)) {
                final float[] rotations = PlayerUtil.getPredictedRotations(optimalTarget);
                if(!wallRangeProperty.value().equals(rangeProperty.value()) || rayTraceProperty.value()) {
                    EntityLivingBase raycastedEntity = WorldUtil.raycast(rangeProperty.value(), rotations);
                    if(raycastedEntity != null && !(raycastedEntity == optimalTarget) && rayTraceProperty.value()) return;
                    if(optimalTarget.getPositionVector().subtract(mc.thePlayer.getPositionEyes(1.0F)).angle(mc.thePlayer.getLookVec()) > fovRangeProperty.value()) return;
                }
                if(rotationsProperty.value()) {
                    event.yaw(rotations[0]);
                    event.pitch(rotations[1]);
                    if(lockViewProperty.value()) {
                        mc.thePlayer.rotationYaw = rotations[0];
                        mc.thePlayer.rotationPitch = rotations[1];
                    }
                }
                if(wallRangeProperty.value().equals(rangeProperty.value()) || WorldUtil.lastRaycastRange() < wallRangeProperty.value()) {
                    if((autoBlockProperty.value() && distanceCheck(target) || mc.thePlayer.getDistanceToEntity(target) < blockRangeProperty.value()) && PlayerUtil.isHoldingSword()) {
                        unblock();
                    }
                    if(attackModeProperty.value() == AttackMode.PRE)
                        attack(optimalTarget);
                }
            }
        } else {
            if(isValid(target)) {
                if(WorldUtil.lastRaycastRange() < wallRangeProperty.value() && distanceCheck(target)) {
                    if(attackModeProperty.value() == AttackMode.POST)
                        attack(target);
                }
                if((autoBlockProperty.value() && distanceCheck(target) || mc.thePlayer.getDistanceToEntity(target) < blockRangeProperty.value()) && PlayerUtil.isHoldingSword()) {
                    block();
                }
            }
        }
    }

//    @EventHandler
//    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
//        if(event.state() == EventState.PRE) {
//            EntityLivingBase optimalTarget = null;
//            target = null;
//            List<EntityLivingBase> livingEntities = WorldUtil.getLivingEntities(Predicates.and(this::isValid, this::distanceCheck));
//            livingEntities.sort(sortProperty.value().sorter());
//            if(livingEntities.size() > 0) {
//                if(modeProperty.value() == KillAuraMode.SWITCH) {
//                    if(switchTimer.hasElapsed(switchDelayProperty.value().longValue())) {
//                        targetIndex++;
//                        switchTimer.reset();
//                    }
//                    if(targetIndex >= livingEntities.size())
//                        targetIndex = 0;
//                }
//                optimalTarget = livingEntities.get(targetIndex);
//                target = optimalTarget;
//            }
//            if(optimalTarget != null && distanceCheck(optimalTarget)) {
//                final float[] rotations = PlayerUtil.getPredictedRotations(optimalTarget);
//                if(!wallRangeProperty.value().equals(rangeProperty.value()) || rayTraceProperty.value()) {
//                    EntityLivingBase raycastedEntity = WorldUtil.raycast(rangeProperty.value(), rotations);
//                    if(raycastedEntity != null && !(raycastedEntity == optimalTarget) && rayTraceProperty.value()) return;
//                    if(optimalTarget.getPositionVector().subtract(mc.thePlayer.getPositionEyes(1.0F)).angle(mc.thePlayer.getLookVec()) > fovRangeProperty.value()) return;
//                }
//                if(rotationsProperty.value()) {
//                    event.yaw(rotations[0]);
//                    event.pitch(rotations[1]);
//                    if(lockViewProperty.value()) {
//                        mc.thePlayer.rotationYaw = rotations[0];
//                        mc.thePlayer.rotationPitch = rotations[1];
//                    }
//                }
//                if(wallRangeProperty.value().equals(rangeProperty.value()) || WorldUtil.lastRaycastRange() < wallRangeProperty.value()) {
//                    if((autoBlockProperty.value() && distanceCheck(target) || mc.thePlayer.getDistanceToEntity(target) < blockRangeProperty.value()) && PlayerUtil.isHoldingSword()) {
//                        unblock();
//                    }
//                    if(attackModeProperty.value() == AttackMode.PRE)
//                        attack(optimalTarget);
//                }
//            }
//        } else {
//            if(isValid(target)) {
//                if(WorldUtil.lastRaycastRange() < wallRangeProperty.value() && distanceCheck(target)) {
//                    if(attackModeProperty.value() == AttackMode.POST)
//                        attack(target);
//                }
//                if((autoBlockProperty.value() && distanceCheck(target) || mc.thePlayer.getDistanceToEntity(target) < blockRangeProperty.value()) && PlayerUtil.isHoldingSword()) {
//                    block();
//                }
//            }
//        }
//    });

    private void attack(EntityLivingBase entity) {
        int min = minAPSProperty.value().intValue();
        int max = maxAPSProperty.value().intValue();
        int cps;

        if(min == max) cps = min;
        else cps = RandomUtils.nextInt(min, max + 1);

        if(attackTimer.hasElapsed(1000 / cps)) {
            if(keepSprintProperty.value()) {
                if(noSwingProperty.value())
                    mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                else mc.thePlayer.swingItem();
                mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
            }
            else mc.playerController.attackEntity(mc.thePlayer, entity);
            attackTimer.reset();
        }
    }

    private void unblock() {
        mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }

    private void block() {
        mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 8100);
        mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));
    }

    public static boolean isValid(EntityLivingBase entityLivingBase) {
        if(entityLivingBase == null) return false;
        if (entityLivingBase.isInvisible()) return false;
        if(!entityLivingBase.isEntityAlive()) return false;
        if (entityLivingBase instanceof EntityOtherPlayerMP) {
            final EntityPlayer player = (EntityPlayer) entityLivingBase;
            if(StrifeClient.INSTANCE.targetRepository().isFriend(player.getName())) return false;
            if(PlayerUtil.isTeammate(player)) return false;
            if (WorldUtil.checkPing(player)) return true;
            // TODO: Teammates and Friends / Enemies
        } else if (entityLivingBase instanceof EntityMob) {
            return false;
        } else if (entityLivingBase instanceof EntityAnimal) {
            return false;
        } else {
            return false;
        }
        return false;
    }

    private boolean distanceCheck(EntityLivingBase entityLivingBase) {
        return mc.thePlayer.getDistanceToEntity(entityLivingBase) < rangeProperty.value();
    }

    private final static class HealthSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(PlayerUtil.getEffectiveHealth(o1), PlayerUtil.getEffectiveHealth(o2));
        }
    }
    private final static class ArmorSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(o1.getTotalArmorValue(), o2.getTotalArmorValue());
        }
    }
    private final static class FovSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(o1.getPositionVector().subtract(mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks)).angle(mc.thePlayer.getLookVec()), o2.getPositionVector().subtract(mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks)).angle(mc.thePlayer.getLookVec()));
        }
    }
    private final static class HurtTimeSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(PlayerUtil.MAX_HURT_RESISTANT_TIME - o1.hurtResistantTime, PlayerUtil.MAX_HURT_RESISTANT_TIME - o2.hurtResistantTime);
        }
    }
    private final static class DistanceSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return -Double.compare(mc.thePlayer.getDistanceToEntity(o1), mc.thePlayer.getDistanceToEntity(o2));
        }
    }

    private enum SortMode {
        HEALTH(new HealthSorter()), ARMOR(new ArmorSorter()), FOV(new FovSorter()), HURTTIME(new HurtTimeSorter()), DISTANCE(new DistanceSorter());

        private final Comparator<EntityLivingBase> sorter;

        SortMode(Comparator<EntityLivingBase> sorter) {
            this.sorter = sorter;
        }

        public Comparator<EntityLivingBase> sorter() {
            return sorter;
        }
    }

    public static KillAuraModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(KillAuraModule.class);
    }

    public EntityLivingBase target() {
        return target;
    }

    private enum KillAuraMode {
        SINGLE, SWITCH
    }

    private enum AttackMode {
        PRE, POST
    }

    private enum TargetHUDMode {
        STRIFE, EXHIBITION, REMIX, NOVOLINE
    }

}
