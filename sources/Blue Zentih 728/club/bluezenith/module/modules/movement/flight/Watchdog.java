package club.bluezenith.module.modules.movement.flight;

import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.modules.movement.Flight;
import club.bluezenith.util.client.Chat;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class Watchdog implements IFlight {

    int ticks = 0;
    boolean hypixelStart = false;

    @Override
    public void onCollision(CollisionEvent event, Flight flight) {

    }

    @Override
    public void onPlayerUpdate(UpdatePlayerEvent event, Flight flight) {
        if (event.isPost()) return;

        ticks++;
        mc.thePlayer.motionY = 0;

        if (ticks % 5 == 0) {
            event.y += 8;
        }

        if (hypixelStart) {
            float multiplier = flight.wdDistance.get() - 0.01F;
            final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
            double x = mc.thePlayer.posX + -Math.sin(yaw) * 9;
            double y = mc.thePlayer.posY;
            double z = mc.thePlayer.posZ + Math.cos(yaw) * 9;
            BlockPos pos = new BlockPos(x, y, z);
            BlockPos pos2 = new BlockPos(x, y + 1, z);
            if(!flight.wdSmart.get() || mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(pos2).getBlock() instanceof BlockAir) {
                mc.thePlayer.setPosition(x, y, z);
            } else if(flight.wdSmart.get()) {
                boolean teleportedSuccessfully = false;
                do {
                    multiplier -= 0.5F;
                    x = mc.thePlayer.posX + -Math.sin(yaw) * multiplier;
                    y = mc.thePlayer.posY;
                    z = mc.thePlayer.posZ + Math.cos(yaw) * multiplier;
                    pos = new BlockPos(x, y, z);
                    pos2 = new BlockPos(x, y + 1, z);
                    if(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(pos2).getBlock() instanceof BlockAir) {
                        mc.thePlayer.setPosition(x, y, z);
                        teleportedSuccessfully = true;
                        break;
                    }
                } while(multiplier > 1);
                if(!teleportedSuccessfully)
                    Chat.xi("Found one blocking at target position");
            }
            hypixelStart = false;
        }
    }


    @Override
    public void onMoveEvent(MoveEvent event, Flight flight) {
        event.x = 0;
        event.y = 0;
        event.z = 0;
    }

    @Override
    public void onEnable(Flight flight) {
        ticks = 0;
        hypixelStart = false;
    }

    @Override
    public void onDisable(Flight flight) {

    }

    @Override
    public void onPacket(PacketEvent event, Flight flight) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            hypixelStart = true;
            ticks = 0;
        }
    }

}
