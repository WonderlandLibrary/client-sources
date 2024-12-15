package com.alan.clients.module.impl.other;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.BlinkComponent;
import com.alan.clients.component.impl.render.ESPComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.interfaces.ThreadAccess;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.DragValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.*;
import net.minecraft.util.EnumChatFormatting;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static com.alan.clients.layer.Layers.*;

@ModuleInfo(aliases = {"module.other.debugger.name"}, category = Category.PLAYER, description = "module.other.debugger.description")
public final class Debugger extends Module implements ThreadAccess {

    private final BooleanValue transaction = new BooleanValue("Transactions", this, true);
    private final BooleanValue keepAlive = new BooleanValue("Keep Alive", this, true);
    private final BooleanValue teleport = new BooleanValue("Teleport", this, true);
    private final BooleanValue velocity = new BooleanValue("Velocity", this, true);
    private final BooleanValue abilities = new BooleanValue("Abilities", this, true);
    private final BooleanValue displayAll = new BooleanValue("Display All", this, true);
    private final BooleanValue blacklist = new BooleanValue("Blacklist", this, true, () -> !displayAll.getValue());
    private final BooleanValue time = new BooleanValue("Time Since Move", this, true, () -> !displayAll.getValue());
    private final BooleanValue devPanel = new BooleanValue("Dev Panel", this, false);
    private final BooleanValue eventCalls = new BooleanValue("Event Calls", this, false);

    private final DragValue position = new DragValue("", this, new Vector2d(200, 200), true);

    private final DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final ArrayList<String> blacklisted = new ArrayList<>();

    public static HashMap<String, Integer> calls = new HashMap<>();
    private long threadLag;
    private boolean measuring;
    private StopWatch stopWatch = new StopWatch();

    @Override
    public void onEnable() {
        blacklisted.clear();
    }

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (!Client.DEVELOPMENT_SWITCH) return;

        final Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && teleport.getValue()) {

            stopWatch.reset();
        }

        if (displayAll.getValue()) {
            String name = packet.getClass().getSimpleName();

            if (blacklist.getValue()) {
                if (!blacklisted.contains(name)) {
                    ChatUtil.display(EnumChatFormatting.BLACK + " BlackListed Packet " + EnumChatFormatting.RESET + " (ID: %s)", name);
                    blacklisted.add(name);
                }
            } else if (!blacklisted.contains(name)) {
                ChatUtil.display(EnumChatFormatting.GOLD + " Packet " + EnumChatFormatting.RESET + " (ID: %s)", name);

                for (Field field : packet.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    try {
                        ChatUtil.display(EnumChatFormatting.YELLOW + " %s:" + EnumChatFormatting.GRAY + " %s", field.getName(), field.get(packet));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                if (time.getValue()) ChatUtil.display(EnumChatFormatting.YELLOW + " %s:" + EnumChatFormatting.GRAY + " %s", "time since movement", stopWatch.getElapsedTime());

                ChatUtil.display("");
            }
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (transaction.getValue() && packet instanceof S32PacketConfirmTransaction) {
            final S32PacketConfirmTransaction transaction = ((S32PacketConfirmTransaction) packet);

            ChatUtil.display(EnumChatFormatting.RED + " Transaction " + EnumChatFormatting.RESET + " (ID: %s)   (WindowID: %s)", transaction.actionNumber, transaction.windowId);
        } else if (keepAlive.getValue() && packet instanceof S00PacketKeepAlive) {
            final S00PacketKeepAlive wrapper = ((S00PacketKeepAlive) packet);

            ChatUtil.display(EnumChatFormatting.GREEN + " Keep Alive " + EnumChatFormatting.RESET + " (ID: %s)", wrapper.func_149134_c());
        } else if (teleport.getValue() && packet instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook wrapper = ((S08PacketPlayerPosLook) packet);

            ChatUtil.display(EnumChatFormatting.BLUE + " Server Teleport " + EnumChatFormatting.RESET + " (Position: %s)",
                    MathUtil.round(wrapper.x, 3) + " " +
                            MathUtil.round(wrapper.y, 3) + " " +
                            MathUtil.round(wrapper.z, 3));
        } else if (velocity.getValue() && packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = ((S12PacketEntityVelocity) packet);

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                ChatUtil.display(EnumChatFormatting.LIGHT_PURPLE + " Velocity " + EnumChatFormatting.RESET + " (DeltaX: %s) (DeltaY: %s)  (DeltaZ: %s) ",
                        wrapper.motionX / 8000D, wrapper.motionY / 8000D, wrapper.motionZ / 8000D);
            }
        } else if (velocity.getValue() && packet instanceof S27PacketExplosion) {
            S27PacketExplosion explosion = ((S27PacketExplosion) packet);
            ChatUtil.display(EnumChatFormatting.LIGHT_PURPLE + " Explosion (Velocity) " + EnumChatFormatting.RESET + " (DeltaX: %s) (DeltaY: %s)  (DeltaZ: %s) ",
                    explosion.func_149149_c(), explosion.func_149144_d(), explosion.func_149147_e());
        } else if (abilities.getValue() && packet instanceof S39PacketPlayerAbilities) {
            S39PacketPlayerAbilities abilitiesp = ((S39PacketPlayerAbilities) packet);
            ChatUtil.display(EnumChatFormatting.YELLOW + " Abilities " + abilitiesp.getFlySpeed() + " " + abilitiesp.isAllowFlying() + " " + abilitiesp.isFlying());
        }
    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (devPanel.getValue()) {
            double padding = 10;
            position.scale = new Vector2d(180, 207);

            getLayer(REGULAR, 1).add(() -> RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, getTheme().getRound(), getTheme().getBackgroundShade()));
            getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, getTheme().getRound(), Color.BLACK));
            getLayer(BLOOM).add(() -> RenderUtil.roundedRectangle(position.position.x, position.position.y, position.scale.x, position.scale.y, getTheme().getRound(), getTheme().getDropShadow()));

            getLayer(REGULAR, 1).add(() -> {
                mc.fontRendererObj.drawWithShadow(Client.NAME + " " + Client.VERSION + " INDEV " + date.format(LocalDateTime.now()), position.position.x + padding, position.position.y + padding, new Color(255, 255, 0).getRGB());
                mc.fontRendererObj.drawWithShadow("FPS: " + Minecraft.getDebugFPS() + " [target " + mc.getLimitFramerate() + "]", position.position.x + padding, position.position.y + padding * 2, new Color(255, 255, 0).getRGB());

                mc.fontRendererObj.draw("Debugger", position.position.x + padding, position.position.y + padding * 4, getTheme().getFirstColor().hashCode());

                if (Client.DEVELOPMENT_SWITCH) {
                    mc.fontRendererObj.draw("Blink: " + BlinkComponent.blinking, position.position.x + padding, position.position.y + padding * 6, Color.WHITE.hashCode());
                } else {
                    mc.fontRendererObj.draw("Hidden due to not in dev mode", position.position.x + padding, position.position.y + padding * 5, Color.WHITE.hashCode());
                    mc.fontRendererObj.draw("Hidden due to not in dev mode", position.position.x + padding, position.position.y + padding * 6, Color.WHITE.hashCode());
                }

//                mc.fontRendererObj.drawString("Bot Amount: " + Client.INSTANCE.getBotManager().size(), position.position.x + padding, position.position.y + padding * 7, Color.WHITE.hashCode());
                mc.fontRendererObj.draw("ESPs Amount: " + ESPComponent.esps.size(), position.position.x + padding, position.position.y + padding * 8, Color.WHITE.hashCode());

                mc.fontRendererObj.draw("Performance", position.position.x + padding, position.position.y + padding * 9, getTheme().getFirstColor().hashCode());
                mc.fontRendererObj.draw("ThreadLag: " + threadLag, position.position.x + padding, position.position.y + padding * 16, Color.WHITE.hashCode());

                mc.fontRendererObj.draw("Other", position.position.x + padding, position.position.y + padding * 18, getTheme().getFirstColor().hashCode());
                mc.fontRendererObj.draw("Timer: " + mc.timer.timerSpeed, position.position.x + padding, position.position.y + padding * 19, Color.WHITE.hashCode());
            });
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (measuring) return;

        long systemTime = System.currentTimeMillis();
        measuring = true;

        boolean run = mc.thePlayer.ticksExisted % 100 == 0 && eventCalls.getValue();
        threadPool.execute(() -> {
            threadLag = System.currentTimeMillis() - systemTime;
            measuring = false;

            if (run) {
                ChatUtil.display("Displaying Calls: ");

                for (String name : calls.keySet()) {
                    ChatUtil.display(name + ": " + calls.get(name));
                }

                calls.clear();
            }
        });
    };
}
