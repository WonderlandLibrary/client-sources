package wtf.shiyeno.modules.impl.movement;

import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.Heightmap;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.ServerUtil;

import java.util.concurrent.ThreadLocalRandom;

@FunctionAnnotation(name = "KTLeave", type = Type.Movement)
public class KTLeave extends Function {

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (ServerUtil.isHW()) {
                if (e.getPacket() instanceof SEntityVelocityPacket p) {
                    if (p.getEntityID() == mc.player.getEntityId()) {
                        leaveHoly();
                    }
                }
            } else {
                ClientUtil.sendMesage(TextFormatting.RED + "Данная функция работает только на Holyworld!");
                toggle();
            }
        }

    }


    private void leaveHoly() {
        float x = (float) mc.player.getPosX() + ThreadLocalRandom.current().nextFloat(-50, 50);
        float z = (float) mc.player.getPosZ() + ThreadLocalRandom.current().nextFloat(-50, 50);

        float y = mc.world.getHeight(Heightmap.Type.WORLD_SURFACE, (int) x, (int) z) - 1;
        ClientUtil.sendMesage("Телепортирую...");

        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), -180, 0, false));
        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), -180, 0, false));

        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        toggle();
    }
}