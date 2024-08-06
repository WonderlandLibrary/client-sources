package club.strifeclient.module.implementations.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.EventState;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.movement.NoSlowdown;
import club.strifeclient.module.implementations.player.Scaffold;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.util.networking.PacketUtil;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.player.PlayerUtil;
import club.strifeclient.util.player.RotationUtil;
import club.strifeclient.util.system.Stopwatch;
import club.strifeclient.util.world.WorldUtil;
import lombok.Getter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@ModuleInfo(name = "KillAura", description = "Automatically attack entities.", aliases = "Aura", category = Category.COMBAT)
public final class KillAura extends Module {

    private final ModeSetting<KillAuraMode> modeSetting = new ModeSetting<>("Mode", KillAuraMode.SINGLE);
    private final DoubleSetting switchDelaySetting = new DoubleSetting("Switch Delay", 50, 0, 1000, 5,
            () -> modeSetting.getValue() == KillAuraMode.SWITCH);
    private final DoubleSetting entitiesSetting = new DoubleSetting("Entities", 2, 2, 20,
            () -> modeSetting.getValue() == KillAuraMode.MULTI);
    private final ModeSetting<KillAuraSortMode> sortModeSetting = new ModeSetting<>("Sort By", KillAuraSortMode.HEALTH,
            () -> modeSetting.getValue() == KillAuraMode.SINGLE || modeSetting.getValue() == KillAuraMode.MULTI);
    private final ModeSetting<KillAuraAttackMode> attackModeSetting = new ModeSetting<>("Attack In", KillAuraAttackMode.POST);
    private final DoubleSetting tickSetting = new DoubleSetting("Tick", 5, 0, 20, 1,
            () -> attackModeSetting.getValue() == KillAuraAttackMode.TICK);
    private final ModeSetting<KillAuraSwingMode> swingModeSetting = new ModeSetting<>("Swing", KillAuraSwingMode.NORMAL);

    private final DoubleSetting minAPSSetting = new DoubleSetting("Min APS", 11, 1, 20, 1);
    private final DoubleSetting maxAPSSetting = new DoubleSetting("Max APS", 14, 1, 20, 1);
    private final DoubleSetting rangeSetting = new DoubleSetting("Range", 4.2, 1, 6, 0.1);
    private final DoubleSetting blockRangeSetting = new DoubleSetting("Block Range", 7, 1, 10, 0.1);

    private final BooleanSetting autoBlockSetting = new BooleanSetting("AutoBlock", true);
    private final ModeSetting<KillAuraBlockMode> autoBlockMode = new ModeSetting<>("Block Mode", KillAuraBlockMode.WATCHDOG, autoBlockSetting::getValue);
    private final BooleanSetting targetHUDSetting = new BooleanSetting("TargetHUD", true);
    private final BooleanSetting keepSprintSetting = new BooleanSetting("Keep Sprint", true);
    private final BooleanSetting rotationsSetting = new BooleanSetting("Rotations", true);
    private final BooleanSetting lockViewSetting = new BooleanSetting("Lock View", false, rotationsSetting::getValue);

    private final MultiSelectSetting<WorldUtil.Target> targetsSetting = new MultiSelectSetting<>("Targets", WorldUtil.Target.PLAYERS);

    @Getter
    private EntityLivingBase nextTarget;
    @Getter
    private List<EntityLivingBase> multiTargets;

    private final Stopwatch switchTimer = new Stopwatch();
    private final Stopwatch attackTimer = new Stopwatch();
    private NoSlowdown noSlowdown;
    private Scaffold scaffold;
    private boolean blocking;
    private int switchIndex, tick, blockState;

    private final List<Packet<?>> packets = new ArrayList<>();

    private static final Comparator<EntityLivingBase> HEALTH_SORT = Comparator.comparingDouble(PlayerUtil::getEffectiveHealth);
    private static final Comparator<EntityLivingBase> ARMOR_SORT = Comparator.comparingDouble(EntityLivingBase::getTotalArmorValue);
    private static final Comparator<EntityLivingBase> HURT_TIME_SORT = Comparator.comparingDouble((EntityLivingBase entityOne) -> 20 - entityOne.hurtResistantTime);
    private static final Comparator<EntityLivingBase> DISTANCE_SORT = Comparator.comparingDouble((EntityLivingBase entityOne) -> mc.thePlayer.getDistanceToEntity(entityOne));

    @Override
    protected void onDisable() {
        super.onDisable();
        switchIndex = 0;
        blocking = false;
        nextTarget = null;
        multiTargets.clear();
        packets.clear();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        attackTimer.reset();
        switchTimer.reset();
    }

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {

        if (noSlowdown == null)
            noSlowdown = Client.INSTANCE.getModuleManager().getModule(NoSlowdown.class);
        if (scaffold == null)
            scaffold = Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
        if (multiTargets == null)
            multiTargets = new ArrayList<>();
        if (scaffold.isEnabled()) return;

        final EventState currentState = getCurrentState();
        final EventState previousState = getPreviousState(currentState);
        final EntityLivingBase nextTarget = getTarget();

        if (nextTarget == null) {
            if (autoBlockMode.getValue() == KillAuraBlockMode.RIGHT_CLICK)
                unblock();
            return;
        }

        if (e.isPre()) {
            if (inAttackRange(nextTarget)) {
                if (rotationsSetting.getValue()) {
                    final float[] rotations = RotationUtil.getRotationFromEntity(nextTarget);
                    final float sens = RotationUtil.getSensitivityMultiplier();
                    final float yaw = rotations[0];
                    final float pitch = Math.min(90, rotations[1] - 43);
                    float yawGCD = (Math.round((yaw + MovementUtil.getRandomHypixelValuesFloat()) / sens) * sens);
                    float pitchGCD = (Math.round((pitch + MovementUtil.getRandomHypixelValuesFloat()) / sens) * sens);
//                    if (Math.abs(pitchGCD) == Math.round(Math.abs(pitchGCD))) {
//                        pitchGCD += (Math.round(MovementUtil.getRandomHypixelValues() * 15 / sens) * sens);
//                    }
                    e.yaw = yawGCD;
                    e.pitch = pitchGCD;
                    if (lockViewSetting.getValue()) {
                        mc.thePlayer.rotationYaw = yaw;
                        mc.thePlayer.rotationPitch = pitch;
                    }
                }
            }
//            if (inBlockRange()) {
//                if (!noSlowdown.isEnabled())
//                    this.nextTarget = nextTarget;
//                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
//            }
        }
        if (e.isPost()) {
            if (autoBlockMode.getValue() == KillAuraBlockMode.RIGHT_CLICK) {
                blockState++;
                if (blockState >= 2) {
                    for (Packet<?> packet : packets)
                        PacketUtil.sendPacket(packet);
                    packets.clear();
                    blockState = 0;
                }
            }
        }
        if (!multiTargets.isEmpty() && e.eventState == currentState)
            multiTargets.forEach(this::attackAndSwing);
        else {
            switch (attackModeSetting.getValue()) {
                case TICK: {
                    if (tick == tickSetting.getInt()) {
                        attack(nextTarget);
                        tick = 0;
                    } else tick++;
                    break;
                }
                case HURT: {
                    if (nextTarget.hurtTime <= 5)
                        attack(nextTarget);
                    break;
                }
                default: {
                    if (e.eventState == currentState)
                        attack(nextTarget);
                    break;
                }
            }
        }
        if(e.eventState == previousState && autoBlockMode.getValue() != KillAuraBlockMode.WATCHDOG) {
            unblock();
        }
    };

    private EntityLivingBase getTarget() {
        final List<EntityLivingBase> livingEntities = WorldUtil.getLivingEntities(entity -> WorldUtil.isValid((EntityLivingBase) entity, targetsSetting)
                && mc.thePlayer.getDistanceToEntity(entity) <= blockRangeSetting.getDouble());
        switch (modeSetting.getValue()) {
            case SINGLE: {
                livingEntities.sort(getEntitySorter());
                return livingEntities.get(0);
            }
            case SWITCH: {
                if (livingEntities.size() > 1) {
                    if (modeSetting.getValue() == KillAuraMode.SWITCH) {
                        if (switchTimer.hasElapsed(switchDelaySetting.getLong())) {
                            switchIndex++;
                            switchTimer.reset();
                        }
                        if (switchIndex >= livingEntities.size()) {
                            switchIndex = 0;
                        }
                    }
                } else switchIndex = 0;
                return livingEntities.get(switchIndex);
            }
            case MULTI: {
                livingEntities.sort(getEntitySorter());
                multiTargets.clear();
                if (livingEntities.size() > 1) {
                    for (int i = 0; i < entitiesSetting.getInt(); i++) {
                        final EntityLivingBase target = livingEntities.get(i);
                        if (target != null)
                            multiTargets.add(target);
                    }
                } else return livingEntities.get(0);
            }
        }
        return null;
    }

    public boolean hasTarget() {
        return nextTarget != null || !multiTargets.isEmpty();
    }

    private boolean inBlockRange(final EntityLivingBase entityLivingBase) {
        return mc.thePlayer.getDistanceToEntity(entityLivingBase) <= blockRangeSetting.getDouble() && mc.thePlayer.getDistanceToEntity(entityLivingBase) > rangeSetting.getDouble();
    }

    private boolean inAttackRange(final EntityLivingBase entityLivingBase) {
        return mc.thePlayer.getDistanceToEntity(entityLivingBase) <= rangeSetting.getDouble();
    }

    private boolean canBlock() {
        return autoBlockSetting.getValue() && InventoryUtil.isHoldingSword();
    }

    public void attack(final EntityLivingBase entity) {
        if (entity == null || !inAttackRange(entity)) return;
        final int aps = RandomUtils.nextInt(minAPSSetting.getInt(), maxAPSSetting.getInt() + 1);
        if (attackTimer.hasElapsed(1000 / aps)) {
            attackAndSwing(entity);
            attackTimer.reset();
        }
        if (canBlock()) {
            switch (autoBlockMode.getValue()) {
                case WATCHDOG: {
                    if (mc.thePlayer.swingProgressInt == -1)
                        block();
                    else if (mc.thePlayer.swingProgressInt == 0)
                        unblock();
                    break;
                }
                case FAKE: {
                    block();
                    break;
                }
            }
        }
    }

    private void attackAndSwing(final EntityLivingBase entity) {
        if (entity == null || !inAttackRange(entity)) return;
        switch (swingModeSetting.getValue()) {
            case NORMAL: {
                mc.thePlayer.swingItem();
                break;
            }
            case NO_SWING: {
                mc.thePlayer.swingProgressInt = -1;
                mc.thePlayer.isSwingInProgress = true;
                PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                break;
            }
        }
        if (keepSprintSetting.getValue()) {
            mc.playerController.syncCurrentPlayItem();
            PacketUtil.sendPacketNoEvent(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        } else mc.playerController.attackEntity(mc.thePlayer, entity);
    }

    private void block() {
        if (!canBlock()) return;
        switch (autoBlockMode.getValue()) {
            case WATCHDOG: {
                mc.playerController.syncCurrentPlayItem();
                PacketUtil.sendPacketNoEvent(new C02PacketUseEntity(-1, C02PacketUseEntity.Action.INTERACT));
                PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                blocking = true;
                break;
            }
            case RIGHT_CLICK: {
                mc.gameSettings.keyBindUseItem.pressed = blocking = true;
                break;
            }
        }
    }

    private void unblock() {
        switch (autoBlockMode.getValue()) {
            case WATCHDOG: {
                PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                blocking = false;
                break;
            }
            case RIGHT_CLICK: {
                mc.gameSettings.keyBindUseItem.pressed = blocking = false;
                for (Packet<?> packet : packets)
                    PacketUtil.sendPacket(packet);
                packets.clear();
            }
        }
    }

    private EventState getCurrentState() {
        return attackModeSetting.getValue() == KillAuraAttackMode.UPDATE ? EventState.UPDATE : attackModeSetting.getValue()
                == KillAuraAttackMode.PRE ? EventState.PRE : attackModeSetting.getValue() == KillAuraAttackMode.POST ?
                EventState.POST : EventState.values()[RandomUtils.nextInt(0, KillAuraAttackMode.values().length - 2)];
    }

    private EventState getPreviousState(EventState state) {
        switch (state) {
            case PRE:
                return EventState.UPDATE;
            case POST:
                return EventState.PRE;
            case UPDATE:
                return EventState.POST;
            default:
                return state;
        }
    }

    public Comparator<EntityLivingBase> getEntitySorter() {
        switch (sortModeSetting.getValue()) {
            case DISTANCE:
                return DISTANCE_SORT;
            case ARMOR:
                return ARMOR_SORT;
            case HURT_TIME:
                return HURT_TIME_SORT;
            default:
                return HEALTH_SORT;
        }
    }

    public enum KillAuraMode implements SerializableEnum {
        SINGLE("Single"), SWITCH("Switch"), MULTI("Multi");
        final String name;

        KillAuraMode(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum KillAuraSortMode implements SerializableEnum {
        HEALTH("Health"), ARMOR("Armor"), HURT_TIME("Hurt Time"), DISTANCE("Distance");
        final String name;

        KillAuraSortMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum KillAuraAttackMode implements SerializableEnum {
        PRE("Pre"), POST("Post"), UPDATE("Update"), RANDOM("Random"), TICK("Tick"), HURT("Hurt");
        final String name;

        KillAuraAttackMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum KillAuraSwingMode implements SerializableEnum {
        NORMAL("Normal"), NO_SWING("No Swing"), NONE("None");

        final String name;

        KillAuraSwingMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum KillAuraBlockMode implements SerializableEnum {
        WATCHDOG("Watchdog"), FAKE("Fake"), RIGHT_CLICK("Right Click");
        final String name;

        KillAuraBlockMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
