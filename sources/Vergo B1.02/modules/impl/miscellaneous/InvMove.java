package xyz.vergoclient.modules.impl.miscellaneous;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;

import java.util.Arrays;
import java.util.List;

public class InvMove extends Module implements OnEventInterface {

    public InvMove() {
        super("InvMove", Module.Category.MISCELLANEOUS);
    }

    private static final List<KeyBinding> keys = Arrays.asList(
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump
    );

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event e) {
        //if(e instanceof EventReceivePacket) {
        //    EventReceivePacket event = (EventReceivePacket) e;
        //    theSpoofMethod(event);
        //}

        if(e instanceof EventMove) {
            if(e.isPre() && mc.currentScreen instanceof GuiContainer) {
                updateStates();
            }
        }
    }

    public static void updateStates() {
        if (mc.currentScreen != null) {
            keys.forEach(k -> k.pressed = GameSettings.isKeyDown(k));
        }
    }

    public void theSpoofMethod(EventReceivePacket e) {
        if(e.isPre()) {

            if(e.packet instanceof S2DPacketOpenWindow) {
                e.setCanceled(true);
            }

            if(e.packet instanceof S2EPacketCloseWindow) {
                e.setCanceled(true);
            }

        }
    }

}