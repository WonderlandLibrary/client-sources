package Ascii4UwUWareClient.Util;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Modules.Combat.AntiBot;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Wrapper {


    private static Font getFontFromLocation(String fileName, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("radium/" + fileName))
                    .getInputStream())
                    .deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ignored) {
            return null;
        }
    }

    public static EntityRenderer getEntityRenderer() {
        return getMinecraft().entityRenderer;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }

    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }


    public static PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }

    public static NetHandlerPlayClient getNetHandler() {
        return getMinecraft().getNetHandler();
    }

    public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }

    public static boolean isInThirdPerson() {
        return getGameSettings().thirdPersonView != 0;
    }

    public static ItemStack getStackInSlot(int index) {
        return getPlayer().inventoryContainer.getSlot(index).getStack();
    }


    public static Block getBlock(BlockPos pos) {
        return getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static void addChatMessage(String message) {
        getPlayer().addChatMessage(new ChatComponentText("\2478[\247CR\2478]\2477 " + message));
    }

    public static GuiScreen getCurrentScreen() {
        return getMinecraft().currentScreen;
    }

    public static List<Entity> getLoadedEntities() {
        return getWorld().getLoadedEntityList();
    }

    public static List<EntityLivingBase> getLivingEntities() {
        return getWorld().getLoadedEntityList()
                .stream()
                .filter(e -> e instanceof EntityLivingBase)
                .map(e -> (EntityLivingBase) e)
                .collect(Collectors.toList());
    }

    public static List<EntityPlayer> getLoadedPlayers() {
        return getWorld().playerEntities;
    }

    public static void forEachInventorySlot(int begin, int end, SlotConsumer consumer) {
        for (int i = begin; i < end; i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null)
                consumer.accept(i, stack);
        }
    }

    public static List<EntityPlayer> getLoadedPlayersNoNPCs() {
        if (Client.instance.getModuleManager().getModuleByClass(AntiBot.class).isEnabled()) {
            List<EntityPlayer> loadedPlayers = new ArrayList<>();

            for (EntityPlayer player : getLoadedPlayers())
                if (!AntiBot.isBot(player))
                    loadedPlayers.add(player);

            return loadedPlayers;
        } else {
            return getLoadedPlayers();
        }
    }

    public static void sendPacket(Packet<?> packet) {
        getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketDirect(Packet<?> packet) {
        getNetHandler().getNetworkManager().sendPacket(packet);
    }

    @FunctionalInterface
    public interface SlotConsumer {
        void accept(int slot, ItemStack stack);
    }
}
