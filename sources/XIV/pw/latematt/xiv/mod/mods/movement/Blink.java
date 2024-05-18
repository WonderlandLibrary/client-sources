package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.EntityUtils;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jack
 */

public class Blink extends Mod {
    private final Listener packetListener, renderListener;
    private final List<Packet> packets = new ArrayList<>();
    private EntityOtherPlayerMP position;

    public Blink() {
        super("Blink", ModType.MOVEMENT, Keyboard.KEY_NONE, 11184895);
        setTag("" + packets.size());

        this.packetListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
                    final boolean moving = mc.thePlayer.movementInput.moveForward != 0;
                    final boolean strafing = mc.thePlayer.movementInput.moveStrafe != 0;

                    double yDifference = mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
                    boolean groundCheck = mc.thePlayer.onGround && yDifference == 0.0D;

                    boolean movingCheck = moving || strafing || !groundCheck;

                    event.setCancelled(true);
                    if (movingCheck) {
                        packets.add(event.getPacket());
                    }
                }

                if (event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C08PacketPlayerBlockPlacement || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation) {
                    event.setCancelled(true);

                    packets.add(event.getPacket());
                }

                setTag("" + packets.size());
            }
        };

        this.renderListener = new Listener<Render3DEvent>() {
            @Override
            public void onEventCalled(Render3DEvent event) {
                if (position == null) {
                    position = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
                    position.copyLocationAndAnglesFrom(mc.thePlayer);

                    EntityUtils.setReference(position);
                }

                double[] start = new double[]{position.posX, position.posY, position.posZ};

                RenderUtils.beginGl();
                Tessellator var2 = Tessellator.getInstance();
                WorldRenderer var3 = var2.getWorldRenderer();
                var3.startDrawing(2);
                GlStateManager.color(0.3F, 0.7F, 1.0F, 1.0F);
                var3.addVertex(start[0] - mc.getRenderManager().renderPosX, start[1] - mc.getRenderManager().renderPosY, start[2] - mc.getRenderManager().renderPosZ);
                var3.addVertex(start[0] - mc.getRenderManager().renderPosX, start[1] + mc.thePlayer.height - mc.getRenderManager().renderPosY, start[2] - mc.getRenderManager().renderPosZ);
                var2.draw();
                RenderUtils.endGl();
            }
        };
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(packetListener, renderListener);

        if (Objects.nonNull(mc.thePlayer)) {
            position = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            position.copyLocationAndAnglesFrom(mc.thePlayer);

            EntityUtils.setReference(position);
        }
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(packetListener, renderListener);

        if (Objects.nonNull(mc.thePlayer)) {
            EntityUtils.setReference(mc.thePlayer);

            for (Packet packet : packets) {
                mc.getNetHandler().addToSendQueue(packet);
            }
        }

        packets.clear();
    }
}
