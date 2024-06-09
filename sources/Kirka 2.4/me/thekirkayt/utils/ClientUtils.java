/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ChatMessage;
import me.thekirkayt.utils.minecraft.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;

public final class ClientUtils {
    public static FontRenderer clientFont;

    public static void loadClientFont() {
        clientFont = new FontRenderer(ClientUtils.mc().gameSettings, new ResourceLocation("client/font/ascii.png"), ClientUtils.mc().renderEngine, false);
        if (ClientUtils.mc().gameSettings.language != null) {
            ClientUtils.mc().fontRendererObj.setUnicodeFlag(ClientUtils.mc().isUnicode());
            ClientUtils.mc().fontRendererObj.setBidiFlag(ClientUtils.mc().mcLanguageManager.isCurrentLanguageBidirectional());
        }
        ClientUtils.mc().mcResourceManager.registerReloadListener(clientFont);
    }

    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP player() {
        ClientUtils.mc();
        return Minecraft.thePlayer;
    }

    public static PlayerControllerMP playerController() {
        ClientUtils.mc();
        return Minecraft.playerController;
    }

    public static WorldClient world() {
        ClientUtils.mc();
        return Minecraft.theWorld;
    }

    public static List<Entity> loadedEntityList() {
        ArrayList<Entity> loadedList = new ArrayList<Entity>(ClientUtils.world().loadedEntityList);
        loadedList.remove(ClientUtils.player());
        return loadedList;
    }

    public static GameSettings gamesettings() {
        return ClientUtils.mc().gameSettings;
    }

    public static MovementInput movementInput() {
        return ClientUtils.player().movementInput;
    }

    public static double x() {
        return ClientUtils.player().posX;
    }

    public static void x(double x) {
        ClientUtils.player().posX = x;
    }

    public static double y() {
        return ClientUtils.player().posY;
    }

    public static void y(double y) {
        ClientUtils.player().posY = y;
    }

    public static double z() {
        return ClientUtils.player().posZ;
    }

    public static void z(double z) {
        ClientUtils.player().posZ = z;
    }

    public static float yaw() {
        return ClientUtils.player().rotationYaw;
    }

    public static void yaw(float yaw) {
        ClientUtils.player().rotationYaw = yaw;
    }

    public static float pitch() {
        return ClientUtils.player().rotationPitch;
    }

    public static void pitch(float pitch) {
        ClientUtils.player().rotationPitch = pitch;
    }

    public static FontRenderer clientFont() {
        return clientFont;
    }

    public static void packet(Packet packet) {
        ClientUtils.mc();
        Minecraft.getNetHandler().addToSendQueue(packet);
    }

    public static void sendMessage(String message) {
        new ChatMessage.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void sendMessage(String message, boolean prefix) {
        new ChatMessage.ChatMessageBuilder(prefix, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void setMoveSpeed(MoveEvent event, double speed) {
        ClientUtils.movementInput();
        double forward = MovementInput.moveForward;
        ClientUtils.movementInput();
        double strafe = MovementInput.moveStrafe;
        float yaw = ClientUtils.yaw();
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    public static void offsetPosition(double speed) {
        ClientUtils.movementInput();
        double forward = MovementInput.moveForward;
        ClientUtils.movementInput();
        double strafe = MovementInput.moveStrafe;
        float yaw = ClientUtils.yaw();
        if (forward == 0.0 && strafe == 0.0) {
            return;
        }
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        ClientUtils.player().setPositionAndUpdate(ClientUtils.x() + (forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f))), ClientUtils.y(), ClientUtils.z() + (forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f))));
    }
}

