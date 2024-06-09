package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.events.SentPacketEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.EntityUtils;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by k1x on 4/23/17.
 */
public class Blink extends Module {

    private List<Packet> packets = new ArrayList<>();
    private EntityOtherPlayerMP position;

    public Blink() {
        super("Blink", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void packet(SentPacketEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer player = (C03PacketPlayer) e.getPacket();
            double yDifference = mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
            boolean groundCheck = yDifference == 0.0D;

            boolean moving = mc.thePlayer.movementInput.moveForward != 0;
            boolean strafing = mc.thePlayer.movementInput.moveStrafe != 0;
            moving = moving || strafing;

            if (moving || !groundCheck)
                packets.add(player);
            e.setCancelled(true);
        }

        if (e.getPacket() instanceof C07PacketPlayerDigging ||
                e.getPacket() instanceof C08PacketPlayerBlockPlacement ||
                e.getPacket() instanceof C0BPacketEntityAction ||
                e.getPacket() instanceof C02PacketUseEntity ||
                e.getPacket() instanceof C0APacketAnimation) {
            packets.add(e.getPacket());
            e.setCancelled(true);
        }
    }

    @SubscribeEvent
    public void render(RenderWorldEvent e){
        if (position == null) {
            position = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            position.copyLocationAndAnglesFrom(mc.thePlayer);

            EntityUtils.setReference(position);
        }

        double[] start = new double[]{position.posX, position.posY, position.posZ};

        RenderingMethods.enableGL3D();
        GlStateManager.color(0.3F, 0.7F, 1.0F, 1.0F);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        var3.startDrawing(2);
        var3.addVertex(start[0] - mc.getRenderManager().renderPosX, start[1] - mc.getRenderManager().renderPosY, start[2] - mc.getRenderManager().renderPosZ);
        var3.addVertex(start[0] - mc.getRenderManager().renderPosX, start[1] + mc.thePlayer.height - mc.getRenderManager().renderPosY, start[2] - mc.getRenderManager().renderPosZ);
        var2.draw();
        RenderingMethods.disableGL3D();

    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (Objects.nonNull(mc.thePlayer)) {
            position = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            position.copyLocationAndAnglesFrom(mc.thePlayer);

            EntityUtils.setReference(position);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (Objects.nonNull(mc.thePlayer)) {
            EntityUtils.setReference(mc.thePlayer);

            for (Packet packet : packets) {
                mc.getNetHandler().addToSendQueue(packet);
            }
        }

        packets.clear();
    }
}
