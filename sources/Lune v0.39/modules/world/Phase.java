package me.superskidder.lune.modules.world;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.*;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Phase extends Mod {
    private Mode mode;
    private int moveUnder;
    private Num<Double> distance;
    private Num<Double> vanillaspeed;
    private Num<Double> verticalspeed;
    public static boolean phasing;
    private int delay;
    private int state;
    private boolean NCPSetup;
    private TimerUtil timer = new TimerUtil();

    public Phase() {
        super("Phase", ModCategory.World,"Make you able to cross the walls");
        this.values.add(mode = new Mode("Mode", Phase.Mod.values(), Phase.Mod.NCP));
        this.values.add(distance = new Num<>("Distance", 2.0, 0.0, 10.0));
        this.values.add(vanillaspeed = new Num("VanillaSpeed", 0.5, 0.0, 3.5));
        this.values.add(verticalspeed = new Num("VerticalSpeed", 0.5, 0.0, 3.5));
    }

    public enum Mod {
        Vanilla, NCP
    }

    @EventTarget
    public void onTick(EventTick event/*, EventBlock bolck*/) {
        if (mc.thePlayer == null)
            return;
        if (mode.getValue() == Phase.Mod.Vanilla) {
            if (mc.thePlayer != null && moveUnder == 1) {
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, true));
                moveUnder = 0;
            }
            double multiplier = 1;
            double mx = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
            double mz = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
            double x = mc.thePlayer.movementInput.moveForward * multiplier * mx
                    + mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            double z = mc.thePlayer.movementInput.moveForward * multiplier * mz
                    - mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            if (mc.thePlayer.isCollidedHorizontally && !isInsideBlock() && mc.thePlayer.isMoving()) {
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, true));
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, true));
            }
        }
        if (mode.getValue() == Phase.Mod.NCP) {
            if (mc.thePlayer.isCollidedHorizontally && mc.gameSettings.keyBindSprint.isKeyDown()) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, true));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, true));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, true));
            }

            if (mode.getValue() == Phase.Mod.NCP) {
                if (isInsideBlock()) {
//                    bolck.setBoundingBox(null);
                }
            }
        }
    }

//    @EventTarget
//    public void run(EventBlock event) {
//        setDisplayName(StringUtils.capitalize(mode.getValue().toString().toLowerCase()));
//        if (mode.getValue() == Phase.Mod.NCP) {
//            if (isInsideBlock()) {
//                event.setBoundingBox(null);
//            }
//        }
//        if (mode.getValue() == Phase.Mod.Vanilla) {
//            if (isInsideBlock()) {
//                event.setBoundingBox(null);
//            }
//        }
//        if (mode.getValue() == Phase.Mod.NCP) {
//            if (this.isInBlock(mc.thePlayer, 0.0f) && !mc.gameSettings.keyBindSprint.isKeyDown()
//                    && event.getPos().getY() > mc.thePlayer.getEntityBoundingBox().minY - 0.4
//                    && event.getPos().getY() < mc.thePlayer.getEntityBoundingBox().maxY + 1.0) {
//                mc.thePlayer.jumpMovementFactor = 0;
//                event.setBoundingBox(null);
//            }
//            if (this.isInBlock(mc.thePlayer, 0.0f) && mc.gameSettings.keyBindSprint.isKeyDown()) {
//                event.setBoundingBox(null);
//            }
//        }
//    }

    @EventTarget
    public void onPacketSend(EventPacketSend event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            if (packet.getChatComponent().getFormattedText().contains("You cannot go past the border.")) {
                event.setCancelled(true);
            }
        }
        if (mode.getValue() == Phase.Mod.Vanilla) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 2) {
                moveUnder = 1;
            }
        }
        if (mode.getValue() == Phase.Mod.NCP) {
            if (event.getPacket() instanceof C03PacketPlayer && !mc.thePlayer.isMoving()
                    && mc.thePlayer.posY == mc.thePlayer.lastTickPosY) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onMove(EventMove event) {
        if (mode.getValue() == Phase.Mod.NCP) {
            if (isInsideBlock()) {
                if (mc.gameSettings.keyBindJump.isKeyDown())
                    event.setY(mc.thePlayer.motionY += 0.09f);
                else if (mc.gameSettings.keyBindSneak.isKeyDown())
                    event.setY(mc.thePlayer.motionY -= 0.00);
                else
                    event.setY(mc.thePlayer.motionY = 0.0f);
                setMoveSpeed(event, 0.3);
            }
        }

        if (mode.getValue() == Phase.Mod.Vanilla) {
            if (isInsideBlock()) {
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    event.setY(mc.thePlayer.motionY = verticalspeed.getValue());
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    event.setY(mc.thePlayer.motionY = -verticalspeed.getValue());
                } else {
                    event.setY(mc.thePlayer.motionY = 0.0);
                }
                setMoveSpeed(event, vanillaspeed.getValue());
            }
        }
    }

    private void setMoveSpeed(EventMove event, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(
                    forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
            event.setZ(
                    forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
    }

    @EventTarget
    public void onUpdate(EventPostUpdate event) {
        if (mode.getValue() == Phase.Mod.NCP) {
            double multiplier = 0.3;
            double mx = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
            double mz = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
            double x = mc.thePlayer.movementInput.moveForward * multiplier * mx
                    + mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            double z = mc.thePlayer.movementInput.moveForward * multiplier * mz
                    - mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder()) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));
                for (int i = 1; i < 10; ++i) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                            mc.thePlayer.posX, 8.988465674311579E307, mc.thePlayer.posZ, false));
                }
                mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
            }
        }
        if (mode.getValue() == Phase.Mod.Vanilla) {
            if (mc.gameSettings.keyBindSneak.isPressed() && !isInsideBlock()) {
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ, true));
                moveUnder = 2;
            }
        }
        if (mode.getValue() == Phase.Mod.NCP) {
            final float dist = 2.0f;
            if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward != 0.0f) {
                ++this.delay;
                final String lowerCase;
                switch (lowerCase = mc.getRenderViewEntity().getHorizontalFacing().name().toLowerCase()) {
                    case "east": {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX + 9.999999747378752E-6, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    }
                    case "west": {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX - 9.999999747378752E-6, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    }
                    case "north": {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                                mc.thePlayer.posY, mc.thePlayer.posZ - 9.999999747378752E-6, false));
                        break;
                    }
                    case "south": {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                                mc.thePlayer.posY, mc.thePlayer.posZ + 9.999999747378752E-6, false));
                        break;
                    }
                    default:
                        break;
                }
                if (this.delay >= 1) {
                    final String lowerCase2;
                    switch (lowerCase2 = mc.getRenderViewEntity().getHorizontalFacing().name().toLowerCase()) {
                        case "east": {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.thePlayer.posX + 2.0, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                            break;
                        }
                        case "west": {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.thePlayer.posX - 2.0, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                            break;
                        }
                        case "north": {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                                    mc.thePlayer.posY, mc.thePlayer.posZ - 2.0, false));
                            break;
                        }
                        case "south": {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                                    mc.thePlayer.posY, mc.thePlayer.posZ + 2.0, false));
                            break;
                        }
                        default:
                            break;
                    }
                    this.delay = 0;
                }
            }
        }
    }

    private boolean isInBlock(Entity e, float offset) {
        for (int x = MathHelper.floor_double(e.getEntityBoundingBox().minX); x < MathHelper
                .floor_double(e.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(e.getEntityBoundingBox().minY); y < MathHelper
                    .floor_double(e.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(e.getEntityBoundingBox().minZ); z < MathHelper
                        .floor_double(e.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = mc.theWorld.getBlockState(new BlockPos(x, y + offset, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
                        final AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld,
                                new BlockPos(x, y + offset, z),
                                mc.theWorld.getBlockState(new BlockPos(x, y + offset, z)));
                        if (boundingBox != null && e.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper
                .floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper
                    .floor_double(mc.thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper
                        .floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
                                mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null)
                                && (mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void teleport(double dist) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
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
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        double xspeed = forward * dist * Math.cos(Math.toRadians(yaw + 90.0F))
                + strafe * dist * Math.sin(Math.toRadians(yaw + 90.0F));
        double zspeed = forward * dist * Math.sin(Math.toRadians(yaw + 90.0F))
                - strafe * dist * Math.cos(Math.toRadians(yaw + 90.0F));
        mc.thePlayer.setPosition(x + xspeed, y, z + zspeed);

    }

    @Override
    public void onEnabled() {
        phasing = false;
    }

    @Override
    public void onDisable() {
        this.delay = 0;
        this.NCPSetup = false;
        timer.reset();
    }
}
