/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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

public final class Wrapper {
    private static Font getFontFromLocation(String fileName, int size) {
        try {
            return Font.createFont(0, Wrapper.getMinecraft().getResourceManager().getResource(new ResourceLocation("radium/" + fileName)).getInputStream()).deriveFont(0, size);
        }
        catch (FontFormatException | IOException ignored) {
            return null;
        }
    }

    public static EntityRenderer getEntityRenderer() {
        return Wrapper.getMinecraft().entityRenderer;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        Wrapper.getMinecraft();
        return Minecraft.thePlayer;
    }

    public static WorldClient getWorld() {
        return Wrapper.getMinecraft().theWorld;
    }

    public static PlayerControllerMP getPlayerController() {
        return Wrapper.getMinecraft().playerController;
    }

    public static NetHandlerPlayClient getNetHandler() {
        return Wrapper.getMinecraft().getNetHandler();
    }

    public static GameSettings getGameSettings() {
        return Wrapper.getMinecraft().gameSettings;
    }

    public static boolean isInThirdPerson() {
        if (Wrapper.getGameSettings().thirdPersonView == 0) return false;
        return true;
    }

    public static ItemStack getStackInSlot(int index) {
        return Wrapper.getPlayer().inventoryContainer.getSlot(index).getStack();
    }

    public static Block getBlock(BlockPos pos) {
        return Wrapper.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static void addChatMessage(String message) {
        Wrapper.getPlayer().addChatMessage(new ChatComponentText("\u00a78[\u00a7CR\u00a78]\u00a77 " + message));
    }

    public static GuiScreen getCurrentScreen() {
        return Wrapper.getMinecraft().currentScreen;
    }

    public static List<Entity> getLoadedEntities() {
        return Wrapper.getWorld().getLoadedEntityList();
    }

    public static List<EntityLivingBase> getLivingEntities() {
        return Wrapper.getWorld().getLoadedEntityList().stream().filter(e -> e instanceof EntityLivingBase).map(e -> (EntityLivingBase)e).collect(Collectors.toList());
    }

    public static List<EntityPlayer> getLoadedPlayers() {
        return Wrapper.getWorld().playerEntities;
    }

    public static void forEachInventorySlot(int begin, int end, SlotConsumer consumer) {
        int i = begin;
        while (i < end) {
            ItemStack stack = Wrapper.getStackInSlot(i);
            if (stack != null) {
                consumer.accept(i, stack);
            }
            ++i;
        }
    }

    public static void sendPacket(Packet<?> packet) {
        Wrapper.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketDirect(Packet<?> packet) {
        Wrapper.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    @FunctionalInterface
    public static interface SlotConsumer {
        public void accept(int var1, ItemStack var2);
    }
}

