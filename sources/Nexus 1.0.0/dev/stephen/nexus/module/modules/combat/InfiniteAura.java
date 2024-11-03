package dev.stephen.nexus.module.modules.combat;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.CombatUtils;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.mc.PathFinder;
import dev.stephen.nexus.utils.timer.MillisTimer;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.block.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// rise
public class InfiniteAura extends Module {
    public static final NumberSetting range = new NumberSetting("Range", 0, 60, 32, 1);
    public static final BooleanSetting attackTeamMates = new BooleanSetting("Attack team mates", false);
    public static final BooleanSetting newVersion = new BooleanSetting("1.9 Attack Speed", false);
    public static final RangeSetting attackSpeed = new RangeSetting("CPS", 0, 25, 10, 12, 0.1);

    public PlayerEntity target;

    public InfiniteAura() {
        super("InfiniteAura", "", 0, ModuleCategory.COMBAT);
        this.addSettings(range, attackTeamMates, newVersion, attackSpeed);
        attackSpeed.addDependency(newVersion, false);
    }

    @Override
    public void onEnable() {
        target = null;
        attackTimer.reset();
        super.onEnable();
    }

    private final MillisTimer attackTimer = new MillisTimer();

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        getTarget();

        if (target == null || target.isDead()) {
            return;
        }

        if (newVersion.getValue()) {
            final float attackCooldown = mc.player.getAttackCooldownProgress(-1F);
            final float dmg = (float) mc.player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * (0.2f + attackCooldown * attackCooldown * 0.8f);
            if (dmg >= 0.95f) {
                teleportToPlayer();
                mc.player.resetLastAttackedTicks();
            }
        } else if (attackTimer.hasElapsed(getAttackDelay())) {
            teleportToPlayer();
            attackTimer.reset();
        }
    };

    private void teleportToPlayer() {
        List<Vec3d> path = computePath(new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()), new Vec3d(target.getX(), target.getY(), target.getZ()), true);

        if (path == null) {
            return;
        }

        for (final Vec3d vector : path) {
            PacketUtils.sendPacketSilently(new PlayerMoveC2SPacket.PositionAndOnGround(vector.getX(), vector.getY(), vector.getZ(), true));
        }

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        Collections.reverse(path);

        for (final Vec3d vector : path) {
            PacketUtils.sendPacketSilently(new PlayerMoveC2SPacket.PositionAndOnGround(vector.getX(), vector.getY(), vector.getZ(), true));
        }
    }

    private long getAttackDelay() {
        double x = attackSpeed.getValueMax();
        double y = attackSpeed.getValueMin();

        if (x < y) {
            double aux = x;
            x = y;
            y = aux;
        }

        double finalValue = RandomUtil.randomBetween((int) x, (int) y);
        return (long) (1000L / finalValue);
    }

    private void getTarget() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        target = null;

        PlayerEntity temp_target = null;
        float health = Float.MAX_VALUE;
        ArrayList<PlayerEntity> targetArray = new ArrayList<>();
        for (PlayerEntity e : mc.world.getPlayers()) {
            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot(e)) {
                continue;
            }

            double calculatedDistance = CombatUtils.getDistanceToEntity(e);

            if (calculatedDistance <= range.getValue()) {
                if (e != mc.player && e.getHealth() > 0) {
                    if (CombatUtils.isSameTeam(e)) {
                        if (attackTeamMates.getValue()) {
                            targetArray.add(e);
                            if (e.getHealth() < health) {
                                health = e.getHealth();
                                temp_target = e;
                            }
                        }
                    } else {
                        targetArray.add(e);
                        if (e.getHealth() < health) {
                            health = e.getHealth();
                            temp_target = e;
                        }
                    }
                }
            }
        }
        if (!targetArray.isEmpty()) {
            temp_target = targetArray.getFirst();
        }

        target = temp_target;
    }

    public List<Vec3d> computePath(final Vec3d from, final Vec3d to, final boolean exact) {
        return computePath(from, to, exact, 9.5);
    }

    public List<Vec3d> computePath(Vec3d from, final Vec3d to, final boolean exact, final double step) {
        final BlockPos blockPos = new BlockPos((int) from.x, (int) from.y, (int) from.z);
        final BlockState state = mc.world.getBlockState(blockPos);

        if (state == null) {
            return null;
        }

        final Block block = state.getBlock();

        if (block == null) {
            return null;
        }

        if (!canPassThroughMaterial(block)) {
            from = from.add(0, 1, 0);
        }

        final PathFinder pathFinder = new PathFinder(from, to);
        pathFinder.compute();

        int i = 0;
        Vec3d lastLoc = null;
        Vec3d lastDashLoc = null;
        final ArrayList<Vec3d> path = new ArrayList<>();
        final ArrayList<Vec3d> pathFinderPath = pathFinder.getPath();
        for (final Vec3d pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                path.add(pathElm.add(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squaredDistanceTo(lastDashLoc) > step * step) {
                    canContinue = false;
                } else {
                    final double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    final double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    final double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    final double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    final double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    final double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }

                if (!canContinue) {
                    path.add(lastLoc.add(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            i++;
        }

        if (exact) {
            path.add(to);
        }

        return path;
    }

    private boolean canPassThroughMaterial(final Block block) {
        return block.getDefaultState().isAir() || block instanceof PlantBlock || block instanceof VineBlock || block instanceof LadderBlock || block instanceof FluidBlock || block instanceof SignBlock;
    }
}
