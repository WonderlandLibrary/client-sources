/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.game.TickEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.CriticalsMod;
import digital.rbq.module.impl.movement.SpeedMod;
import digital.rbq.module.impl.player.FriendProtectMod;
import digital.rbq.module.impl.world.ScaffoldMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.PlayerUtils;
import digital.rbq.utils.RotationUtils;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.entity.EntityValidator;
import digital.rbq.utils.entity.impl.AliveCheck;
import digital.rbq.utils.entity.impl.ConstantDistanceCheck;
import digital.rbq.utils.entity.impl.DistanceCheck;
import digital.rbq.utils.entity.impl.EntityCheck;
import digital.rbq.utils.entity.impl.TeamsCheck;

@Label(value="Aura")
@Bind(value="R")
@Category(value=ModuleCategory.COMBAT)
@Aliases(value={"aura", "killaura", "ka"})
public final class AuraMod
extends Module {
    public static boolean blocking;
    private static final Random RANDOM;
    public final BoolOption autoBlock = new BoolOption("Auto Block", true);
    public final DoubleOption aps = new DoubleOption("APS", 13.0, 1.0, 20.0, 1.0);
    public final DoubleOption randomization = new DoubleOption("Randomization", 0.0, 0.0, 5.0, 1.0);
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.SWITCH);
    public final EnumOption<AutoBlockMode> autoBlockMode = new EnumOption<AutoBlockMode>("Auto Block Mode", AutoBlockMode.OFFSET);
    public final DoubleOption switchDelay = new DoubleOption("Switch Delay", 3.0, () -> this.mode.getValue() == Mode.SWITCH, 1.0, 10.0, 1.0);
    public final EnumOption<SortingMode> sortingMode = new EnumOption<SortingMode>("Sorting Mode", SortingMode.DISTANCE, () -> this.mode.getValue() == Mode.SINGLE);
    public final DoubleOption range = new DoubleOption("Range", 4.2, 3.0, 7.0, 0.1);
    public final BoolOption teams = new BoolOption("Teams", true);
    public final BoolOption players = new BoolOption("Players", true);
    public final BoolOption animals = new BoolOption("Animals", false);
    public final BoolOption monsters = new BoolOption("Monsters", false);
    public final BoolOption prioritizePlayers = new BoolOption("Prioritize Players", true, () -> (this.animals.getValue() != false || this.monsters.getValue() != false) && this.players.getValue() != false);
    public final BoolOption invisibles = new BoolOption("Invisibles", false);
    public final BoolOption forceUpdate = new BoolOption("Force Update", true);
    public final BoolOption disableOnDeath = new BoolOption("Disable on death", true);
    private final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
    private final Stopwatch attackStopwatch = new Stopwatch();
    private final Stopwatch updateStopwatch = new Stopwatch();
    private final Stopwatch critStopwatch = new Stopwatch();
    private final EntityValidator entityValidator = new EntityValidator();
    private final EntityValidator blockValidator = new EntityValidator();
    private final double[] hypixelOffsets = new double[]{0.05f, 0.0016f, 0.03f, 0.0016f};
    private final double[] offsets = new double[]{0.05, 0.0, 0.012511, 0.0};
    private int targetIndex;
    private boolean changeTarget;

    public AuraMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode, this.sortingMode, this.autoBlockMode, this.aps, this.randomization, this.range, this.switchDelay, this.teams, this.players, this.prioritizePlayers, this.animals, this.monsters, this.invisibles, this.autoBlock, this.forceUpdate, this.disableOnDeath);
        AliveCheck aliveCheck = new AliveCheck();
        EntityCheck entityCheck = new EntityCheck(this.players, this.animals, this.monsters, this.invisibles, () -> Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(FriendProtectMod.class).isEnabled());
        TeamsCheck teamsCheck = new TeamsCheck(this.teams);
        this.entityValidator.add(aliveCheck);
        this.entityValidator.add(new DistanceCheck(this.range));
        this.entityValidator.add(entityCheck);
        this.entityValidator.add(teamsCheck);
        this.blockValidator.add(aliveCheck);
        this.blockValidator.add(new ConstantDistanceCheck(8.0f));
        this.blockValidator.add(entityCheck);
        this.blockValidator.add(teamsCheck);
    }

    @Override
    public void onDisabled() {
        this.unblock();
    }

    @Override
    public void onEnabled() {
        this.updateStopwatch.reset();
        this.critStopwatch.reset();
        this.targetIndex = 0;
        this.targets.clear();
        this.changeTarget = false;
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityLivingBase target;
        this.updateTargets();
        this.sortTargets();
        if (!PlayerUtils.isHoldingSword()) {
            blocking = false;
        }
        if ((target = this.getTarget()) == null) {
            this.unblock();
        }
        if (event.isPre() && this.canAttack() && target != null) {
            if (this.updateStopwatch.elapsed(56L) && this.forceUpdate.getValue().booleanValue() && !AuraMod.mc.thePlayer.isMoving()) {
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(AuraMod.mc.thePlayer.posX, AuraMod.mc.thePlayer.posY, AuraMod.mc.thePlayer.posZ, AuraMod.mc.thePlayer.onGround));
                this.updateStopwatch.reset();
            }
            float[] angles = RotationUtils.getRotationsEntity(target);
            event.setYaw(angles[0]);
            event.setPitch(angles[1]);
        }
    }

    @Listener(value=TickEvent.class)
    public final void onTick() {
        EntityLivingBase target = this.getTarget();
        if (target != null && this.canAttack()) {
            if (this.attackStopwatch.elapsed(1000 / ((Double)this.aps.getValue()).intValue()) && this.canAttack()) {
                this.attack(target);
                this.attackStopwatch.reset();
            }
            if ((double)target.hurtTime >= (Double)this.switchDelay.getValue()) {
                this.changeTarget = true;
            }
        }
        this.block();
    }

    public final EntityLivingBase getTarget() {
        if (this.targets.isEmpty()) {
            return null;
        }
        if (this.mode.getValue() == Mode.SINGLE) {
            return this.targets.get(0);
        }
        int size = this.targets.size();
        if (size >= this.targetIndex && this.changeTarget) {
            ++this.targetIndex;
            this.changeTarget = false;
        }
        if (size <= this.targetIndex) {
            this.targetIndex = 0;
        }
        return this.targets.get(this.targetIndex);
    }

    private boolean isEntityNearby() {
        List loadedEntityList = AuraMod.mc.theWorld.loadedEntityList;
        int loadedEntityListSize = loadedEntityList.size();
        for (int i = 0; i < loadedEntityListSize; ++i) {
            Entity entity = (Entity)loadedEntityList.get(i);
            if (!this.blockValidator.validate(entity)) continue;
            return true;
        }
        return false;
    }

    private void attack(EntityLivingBase entity) {
        boolean shouldCritical;
        EntityPlayerSP player = AuraMod.mc.thePlayer;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        ModuleManager mm = Autumn.MANAGER_REGISTRY.moduleManager;
        boolean hypixel = PlayerUtils.isOnHypixel();
        boolean bl = shouldCritical = player.isCollidedVertically && player.onGround && mm.getModuleOrNull(CriticalsMod.class).isEnabled() && !mm.getModuleOrNull(SpeedMod.class).isEnabled() && this.critStopwatch.elapsed(200L) && entity.hurtTime <= 0;
        if (shouldCritical) {
            for (double offset : hypixel ? this.hypixelOffsets : this.offsets) {
                netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + offset, player.posZ, false));
            }
            this.critStopwatch.reset();
        }
        this.unblock();
        player.swingItem();
        netHandler.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
        if (this.autoBlockMode.getValue() == AutoBlockMode.OFFSET) {
            mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, AuraMod.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        }
    }

    private void unblock() {
        if ((this.autoBlock.getValue().booleanValue() || AuraMod.mc.thePlayer.isBlocking()) && PlayerUtils.isHoldingSword()) {
            switch ((AutoBlockMode)((Object)this.autoBlockMode.getValue())) {
                case OFFSET: 
                case SMART: {
                    if (!blocking) break;
                    AuraMod.mc.playerController.syncCurrentPlayItem();
                    mc.getNetHandler().addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    blocking = false;
                }
            }
        }
    }

    private void block() {
        if (this.autoBlock.getValue().booleanValue() && PlayerUtils.isHoldingSword() && this.isEntityNearby()) {
            switch ((AutoBlockMode)((Object)this.autoBlockMode.getValue())) {
                case SMART: {
                    if (blocking) break;
                    AuraMod.mc.playerController.syncCurrentPlayItem();
                    mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, AuraMod.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                    blocking = true;
                    break;
                }
                case OFFSET: {
                    AuraMod.mc.playerController.sendBlockSword(AuraMod.mc.thePlayer, AuraMod.mc.theWorld, AuraMod.mc.thePlayer.inventory.getCurrentItem());
                    blocking = true;
                }
            }
        }
    }

    private boolean canAttack() {
        return !ScaffoldMod.getInstance().isEnabled();
    }

    private void updateTargets() {
        this.targets.clear();
        List entities = AuraMod.mc.theWorld.loadedEntityList;
        int entitiesSize = entities.size();
        for (int i = 0; i < entitiesSize; ++i) {
            EntityLivingBase entityLivingBase;
            Entity entity = (Entity)entities.get(i);
            if (!(entity instanceof EntityLivingBase) || !this.entityValidator.validate(entityLivingBase = (EntityLivingBase)entity)) continue;
            this.targets.add(entityLivingBase);
        }
    }

    private void sortTargets() {
        switch ((SortingMode)((Object)this.sortingMode.getValue())) {
            case HEALTH: {
                this.targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                break;
            }
            case DISTANCE: {
                this.targets.sort(Comparator.comparingDouble(entity -> AuraMod.mc.thePlayer.getDistanceToEntity((Entity)entity)));
            }
        }
        if (this.prioritizePlayers.getValue().booleanValue()) {
            this.targets.sort(Comparator.comparing(entity -> entity instanceof EntityPlayer));
        }
    }

    static {
        RANDOM = new Random();
    }

    private static enum SortingMode {
        DISTANCE,
        HEALTH;

    }

    private static enum AutoBlockMode {
        SMART,
        OFFSET;

    }

    private static enum Mode {
        SINGLE,
        SWITCH;

    }
}

