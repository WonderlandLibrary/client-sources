package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.*;
import java.util.*;

public class AutoLogin extends Module
{
    private int ticks;
    private String password;
    
    public AutoLogin() {
        super("AutoLogin", "Auto login(include Login/Register/Verify) to Redesky", 0, Category.REKTSKY);
        this.ticks = 0;
        this.password = "12345678";
        this.toggle();
    }
    
    @Override
    public void onEvent(final Event e) {
        try {
            if (e instanceof PacketSentEvent) {
                final Packet p = ((PacketSentEvent)e).getPacket();
                if (p instanceof C0EPacketClickWindow) {
                    final C0EPacketClickWindow c0EPacketClickWindow = (C0EPacketClickWindow)((PacketSentEvent)e).getPacket();
                }
            }
            if (e instanceof PacketReceiveEvent) {
                final Packet p = ((PacketReceiveEvent)e).getPacket();
                if (p instanceof S45PacketTitle) {
                    final S45PacketTitle packet = (S45PacketTitle)p;
                    if (packet.getType() == S45PacketTitle.Type.TITLE && packet.getMessage().getUnformattedText().equals("§a§lAutenticado!")) {
                        ((PacketReceiveEvent)e).setCanceled(true);
                        return;
                    }
                    if (packet.getType() == S45PacketTitle.Type.SUBTITLE && packet.getMessage().getUnformattedText().equals("§fRedirecionando...")) {
                        ((PacketReceiveEvent)e).setCanceled(true);
                        return;
                    }
                }
            }
            if (e instanceof WorldTickEvent) {
                ++this.ticks;
                if (this.ticks % 1 == 0) {
                    if (this.mc.currentScreen != null && this.mc.currentScreen instanceof GuiChest && this.mc.thePlayer.openContainer != null) {
                        final GuiChest gui = (GuiChest)this.mc.currentScreen;
                        if (gui.lowerChestInventory.getDisplayName().getUnformattedText().contains("Clique no bloco verde")) {
                            int index = -1;
                            for (final ItemStack item : this.mc.thePlayer.openContainer.getInventory()) {
                                ++index;
                                if (item == null) {
                                    continue;
                                }
                                if (!item.getDisplayName().equals("§aClique aqui")) {
                                    continue;
                                }
                                this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, index, 0, 0, this.mc.thePlayer);
                                this.mc.thePlayer.inventory.setItemStack(null);
                                this.mc.thePlayer.openContainer = this.mc.thePlayer.inventoryContainer;
                                this.mc.displayGuiScreen(null);
                            }
                        }
                    }
                    if (this.mc.ingameGUI.subtitle.contains("/login")) {
                        this.mc.thePlayer.sendChatMessage("/login " + this.password);
                        this.mc.ingameGUI.subtitle = "§rfJoining...";
                        this.mc.ingameGUI.title = "§a§lRektSky AutoLogin";
                    }
                    if (this.mc.ingameGUI.subtitle.contains("/register")) {
                        this.mc.thePlayer.sendChatMessage("/register " + this.password + " " + this.password);
                        this.mc.ingameGUI.subtitle = "§rJoining...";
                        this.mc.ingameGUI.title = "§a§lRektSky AutoLogin";
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void onEnable() {
    }
}
