package koks.modules.impl.utilities;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.KeyBindEvent;
import koks.event.impl.KeyPressEvent;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao | kroko
 * @created on 11.09.2020 : 21:47
 */
public class ShopSaver extends Module {

    public ShopSaver() {
        super("ShopSaver", "You can shop anywhere", Category.UTILITIES);
    }

    public GuiScreen guiScreen;

    @Override
    public void onEvent(Event event) {

        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getPacket() instanceof S2EPacketCloseWindow || ((PacketEvent) event).getPacket() instanceof C0DPacketCloseWindow) {
                event.setCanceled(true);
            }
        }

        if (event instanceof EventUpdate) {
            if (mc.currentScreen instanceof GuiChest) {
                if (guiScreen != mc.currentScreen) {
                    mc.thePlayer.addChatMessage(new ChatComponentText(Koks.getKoks().PREFIX + "§aShop Saved."));
                }
                guiScreen = mc.currentScreen;
            }
        }

        if (event instanceof KeyPressEvent) {
            if (((KeyPressEvent) event).getKey() == mc.gameSettings.keyBindInventory.getKeyCode() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {

                event.setCanceled(true);
                mc.displayGuiScreen(guiScreen);

            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
