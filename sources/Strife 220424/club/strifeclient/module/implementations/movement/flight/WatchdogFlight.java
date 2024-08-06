package club.strifeclient.module.implementations.movement.flight;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.networking.PacketInboundEvent;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.module.implementations.movement.Flight;
import club.strifeclient.setting.Mode;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.networking.PacketUtil;
import club.strifeclient.util.player.ChatUtil;
import club.strifeclient.util.player.MovementUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public final class WatchdogFlight extends Mode<Flight.FlightMode> {

    private int stage;
    private double speedMultiplier;
    private Flight flight;

    @Override
    public Flight.FlightMode getRepresentation() {
        return Flight.FlightMode.WATCHDOG;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        speedMultiplier = 0;
        stage = 0;
        super.onDisable();
    }

    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundEventListener = e -> {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook packet = e.getPacket();
            final double x = packet.x, y = packet.y, z = packet.z;
            if (stage == 2 && mc.thePlayer.onGround) {
                for (int i = 0; i < 49; i++) {
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0617, z, false));
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                }
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
                mc.thePlayer.setPosition(x, y - (1 / 64f), z);
                stage = 3;
            }
        }
    };

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = e -> {
        if (stage <= 3 || !MovementUtil.isMoving()) e.setCancelled(true);
        double speed;
        final PotionEffect effect = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed);
        if (effect != null) speed = 0.295 + (effect.getAmplifier() + 1) * 0.054;
        else speed = 0.288;
        speed = MovementUtil.getBaseMovementSpeed() * 0.9698;
        if (mc.thePlayer.ticksExisted % MathUtil.randomInt(1, 3) == 0)
            speed += 1E-7;
        if(MovementUtil.isMoving())
            MovementUtil.setSpeed(e, speed);
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (flight == null)
            flight = Client.INSTANCE.getModuleManager().getModule(Flight.class);
        if (e.isPre()) {
            if (stage <= 3)
                e.setCancelled(true);
            mc.thePlayer.motionY = 0;
            e.ground = true;
            final BlockPos y = mc.thePlayer.getPosition().down();
            final double lastX = flight.startPos.getX(), lastY = flight.startPos.getY(), lastZ = flight.startPos.getZ();
            switch (stage) {
                case 0: {
//                    PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(y, EnumFacing.UP.getIndex(), mc.thePlayer.getHeldItem(), 0, 0.049392F, 0));
//                    final double[] jumpUp = {0.41999998688698, 0.7531999805212, 1.00133597911214, 1.16610926093821, 1.24918707874468, 1.24918707874468, 1.1707870772188};
//                    Arrays.stream(jumpUp).forEach(value -> PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastX, lastY + value, lastZ, false)));
                    stage = 1;
                    break;
                }
                case 1: {
//                    PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(y, EnumFacing.UP.getIndex(), mc.thePlayer.getHeldItem(), 0, 0.049392F, 0));
//                    final double[] jumpDown = {1.0155550727022, 0.78502770378923, 0.48071087633169, 0.10408037809304, 0.06};
//                    Arrays.stream(jumpDown).forEach(value -> PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastX, lastY + value, lastZ, false)));
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastX, lastY - 0.216, lastZ, false));
                    stage = 2;
                    break;
                }
            }
            ChatUtil.sendMessage(stage);
            if (stage > 2)
                stage++;
        }
        if (e.isPost()) {
//            mc.timer.timerSpeed = 0.7f;
//            if (stage >= 2 && MovementUtil.isMoving()) {
//                mc.timer.timerSpeed = 0.5f;
//                double x = (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * MathUtil.randomDouble(0.94, 0.98) + MovementUtil.getRandomHypixelValues();
//                double z = (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * MathUtil.randomDouble(0.94, 0.98) + MovementUtil.getRandomHypixelValues();
//                if (x == 0 && z == 0) return;
//                for(int i = 0; i < 2; i++) {
//                    if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(x, 0, z)).isEmpty()) {
//                        break;
//                    }
//                    PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, mc.thePlayer.onGround));
//                    mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
//                }
//            }
        }
    };
}
