package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.utils.time.TimerUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

import java.util.Arrays;
import java.util.List;

public final class InventoryMove extends Module {
    private final TimerUtil delayTimer = new TimerUtil();
    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Spoof", "Delay");
    private static final List<KeyBinding> keys = Arrays.asList(
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump
    );

    public static void updateStates() {
        if (mc.currentScreen != null) {
            keys.forEach(k -> k.pressed = GameSettings.isKeyDown(k));
        }
    }

    private final EventListener<MotionEvent> motionEventEventListener = e -> {
        switch (mode.getMode()) {
            case "Spoof":
            case "Vanilla":
                if (e.isPre() && mc.currentScreen instanceof GuiContainer) {
                    updateStates();
                }
                break;
            case "Delay":
                if (e.isPre() && mc.currentScreen instanceof GuiContainer) {
                    if (delayTimer.hasTimeElapsed(100)) {
                        updateStates();
                        delayTimer.reset();
                    }
                }
                break;
        }
    };

    private final EventListener<PacketReceiveEvent> packetReceiveEventEventListener = e -> {
        if (mode.is("Spoof")) {
            if (e.getPacket() instanceof S2DPacketOpenWindow) {
                e.cancel();
            }
            if (e.getPacket() instanceof S2EPacketCloseWindow) {
                e.cancel();
            }
        }
    };

    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT, "lets you move in your inventory");
        this.addSettings(mode);
    }
}
