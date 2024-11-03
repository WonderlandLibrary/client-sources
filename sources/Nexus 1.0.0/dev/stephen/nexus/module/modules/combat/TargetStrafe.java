package dev.stephen.nexus.module.modules.combat;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventJump;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.event.impl.player.EventYawMoveFix;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.modules.movement.Speed;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.mc.MoveUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TargetStrafe extends Module {
    public static final NumberSetting range = new NumberSetting("Range", 0, 6, 3, 0.1);
    public static final BooleanSetting holdJump = new BooleanSetting("Hold jump", false);
    public static final BooleanSetting strafe = new BooleanSetting("Strafe", false);


    public TargetStrafe() {
        super("TargetStrafe", "Strafes around the target", 0, ModuleCategory.COMBAT);
        this.addSettings(range, holdJump, strafe);
    }

    public float yaw;
    public PlayerEntity target;
    private boolean left, colliding;
    public boolean active;

    @EventLink
    public final Listener<EventYawMoveFix> eventMoveListener = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink
    public final Listener<EventJump> eventJumpListener = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        this.setSuffix(String.valueOf(range.getValue()));
        Module scaffold = Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
        KillAura killaura = Client.INSTANCE.getModuleManager().getModule(KillAura.class);

        if (scaffold == null || scaffold.isEnabled() || killaura == null || !killaura.isEnabled()) {
            active = false;
            return;
        }

        active = true;

        Module speed = Client.INSTANCE.getModuleManager().getModule(Speed.class);
        Module flight = Client.INSTANCE.getModuleManager().getModule(Fly.class);

        if (holdJump.getValue() && !mc.options.jumpKey.isPressed() || !(mc.options.forwardKey.isPressed() && ((flight != null && flight.isEnabled()) || ((speed != null && speed.isEnabled()))))) {
            target = null;
            return;
        }

        if (killaura.target == null) {
            target = null;
            return;
        }

        if (mc.player.horizontalCollision || isOverVoid()) {
            if (!colliding) {
                if (strafe.getValue()) {
                    MoveUtils.strafe();
                }
                left = !left;
            }
            colliding = true;
        } else {
            colliding = false;
        }

        target = killaura.target;

        if (target == null) {
            return;
        }

        float yaw = getYaw(mc.player, new Vec3d(target.getX(), target.getY(), target.getZ())) + (90 + 45) * (left ? -1 : 1);

        final double range = this.range.getValue() + Math.random() / 100f;
        final double posX = -MathHelper.sin((float) Math.toRadians(yaw)) * range + target.getX();
        final double posZ = MathHelper.cos((float) Math.toRadians(yaw)) * range + target.getZ();

        yaw = getYaw(mc.player, new Vec3d(posX, target.getY(), posZ));

        this.yaw = yaw;
    };

    private boolean isOverVoid() {
        for (int posY = mc.player.getBlockY(); posY > 0.0; --posY) {
            Block block = mc.world.getBlockState(new BlockPos(mc.player.getBlockX(), posY, mc.player.getBlockZ())).getBlock();
            if (!(block instanceof AirBlock)) {
                return false;
            }
        }
        return true;
    }

    public static float getYaw(ClientPlayerEntity from, Vec3d pos) {
        return from.getYaw() + MathUtils.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(pos.getZ() - from.getZ(), pos.getX() - from.getX())) - 90f - from.getYaw());
    }
}
