package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventCollideWithBlock;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Jesus extends Module {

    int stage, water;
    private final TimerUtil timer = new TimerUtil();
    private boolean wasWater = false;
    private int ticks = 0;
    private final Mode<Enum> mode = new Mode("Mode", "Mode", JMode.values(), JMode.Dolphin);

    public Jesus() {
        super("LiquidBounce", new String[]{"waterwalk", "float"}, ModuleType.Movement);
        this.setColor(new Color(188, 233, 248).getRGB());
        this.addValues(this.mode);
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
                mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        else
            return 0;
    }

    public static void setMotion(double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F))
                    + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F))
                    - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    @Override
    public void onEnable() {
        this.wasWater = false;
        super.onEnable();
    }

    private boolean canJeboos() {
        return !(mc.thePlayer.fallDistance >= 3.0f || mc.gameSettings.keyBindJump.isPressed()
                || BlockHelper.isInLiquid() || mc.thePlayer.isSneaking());
    }

    boolean shouldJesus() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        ArrayList<BlockPos> pos = new ArrayList<BlockPos>(
                Arrays.asList(new BlockPos(x + 0.3, y, z + 0.3), new BlockPos(x - 0.3, y, z + 0.3),
                        new BlockPos(x + 0.3, y, z - 0.3), new BlockPos(x - 0.3, y, z - 0.3)));
        for (BlockPos po : pos) {
            if (!(mc.theWorld.getBlockState(po).getBlock() instanceof BlockLiquid))
                continue;
            if (mc.theWorld.getBlockState(po).getProperties().get(BlockLiquid.LEVEL) instanceof Integer) {
                if ((int) mc.theWorld.getBlockState(po).getProperties().get(BlockLiquid.LEVEL) <= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onPre(EventPreUpdate e) {
        this.setSuffix(this.mode.getValue());
        if (this.mode.getValue() == JMode.Dolphin) {
            if (mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && this.shouldJesus()) {
                mc.thePlayer.motionY = 0.09;
            }
            if (e.getType() == 1) {
                return;
            }
            if (mc.thePlayer.onGround || mc.thePlayer.isOnLadder()) {
                this.wasWater = false;
            }
            if (mc.thePlayer.motionY > 0.0 && this.wasWater) {
                if (mc.thePlayer.motionY <= 0.11) {
                    EntityPlayerSP player = mc.thePlayer;
                    player.motionY *= 1.2671;
                }
                EntityPlayerSP player2 = mc.thePlayer;
                player2.motionY += 0.05172;
            }
            if (isInLiquid() && !mc.thePlayer.isSneaking()) {
                if (this.ticks < 3) {
                    mc.thePlayer.motionY = 0.13;
                    ++this.ticks;
                    this.wasWater = false;
                } else {
                    mc.thePlayer.motionY = 0.5;
                    this.ticks = 0;
                    this.wasWater = true;
                }
            }
        } else if (this.mode.getValue() == JMode.NCP) {
            if (BlockHelper.isInLiquid() && !mc.thePlayer.isSneaking()
                    && !mc.gameSettings.keyBindJump.isPressed()) {
                mc.thePlayer.motionY = 0.05;
                mc.thePlayer.onGround = true;
            }
        }
    }

    private boolean isInLiquid() {
        if (mc.thePlayer == null) {
            return false;
        }
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
                    .floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.boundingBox.minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    public double getMotionY(double stage) {
        stage--;
        double[] motion = new double[]{0.500, 0.484, 0.468, 0.436, 0.404, 0.372, 0.340, 0.308, 0.276, 0.244, 0.212,
                0.180, 0.166, 0.166, 0.156, 0.123, 0.135, 0.111, 0.086, 0.098, 0.073, 0.048, 0.06, 0.036, 0.0106, 0.015,
                0.004, 0.004, 0.004, 0.004, -0.013, -0.045, -0.077, -0.109};
        if (stage < motion.length && stage >= 0)
            return motion[(int) stage];
        else
            return -999;

    }

    @EventHandler
    public void onPacket(EventPacketSend e) {
        if (this.mode.getValue() == JMode.NCP) {
            if (EventPacketSend.getPacket() instanceof C03PacketPlayer && this.canJeboos() && BlockHelper.isOnLiquid()) {
                C03PacketPlayer packet = (C03PacketPlayer) EventPacketSend.getPacket();
                packet.y = mc.thePlayer.ticksExisted % 2 == 0 ? packet.y + 0.01 : packet.y - 0.01;
            }
        }
    }

    @EventHandler
    public void onBB(EventCollideWithBlock e) {
        if (this.mode.getValue() == JMode.NCP) {
            if (e.getBlock() instanceof BlockLiquid && this.canJeboos()) {
                e.setBoundingBox(new AxisAlignedBB(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(),
                        (double) e.getPos().getX() + 1.0, (double) e.getPos().getY() + 1.0,
                        (double) e.getPos().getZ() + 1.0));
            }
        }

    }

    enum JMode {
        NCP, Dolphin
    }
}
