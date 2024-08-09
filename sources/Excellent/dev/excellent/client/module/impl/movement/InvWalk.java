package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.other.InvCloseEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.time.TimerUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CClickWindowPacket;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Inv Walk", description = "Позволяет вам передвигаться с открытым инвентарём.", category = Category.MOVEMENT)
public class InvWalk extends Module {

    private final List<IPacket<?>> packet = new ArrayList<>();

    private final TimerUtil wait = TimerUtil.create();
    private final Listener<PacketEvent> onPacket = event -> {

        if (PlayerUtil.isFuntime()) {
            if (event.getPacket() instanceof CClickWindowPacket p && MoveUtil.isMoving()) {
                if (mc.currentScreen instanceof InventoryScreen) {
                    packet.add(p);
                    event.cancel();
                }
            }
        }
    };
    private final Listener<InvCloseEvent> onInvClose = event -> {
        if (PlayerUtil.isFuntime()) {
            if (mc.currentScreen instanceof InventoryScreen && !packet.isEmpty() && MoveUtil.isMoving()) {
                new Thread(() -> {
                    wait.reset();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    for (IPacket<?> p : packet) {
                        mc.player.connection.sendPacketWithoutEvent(p);
                    }
                    packet.clear();
                }).start();
                event.cancel();
            }
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player != null) {

            final KeyBinding[] pressedKeys = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
                    mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump};
            if (PlayerUtil.isFuntime()) {
                if (!wait.hasReached(400)) {
                    for (KeyBinding keyBinding : pressedKeys) {
                        keyBinding.setPressed(false);
                    }
                    return;
                }
            }

            if (mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof EditSignScreen) {
                return;
            }

            updateKeyBindingState(pressedKeys);

        }
    };

    private void updateKeyBindingState(KeyBinding[] keyBindings) {
        for (KeyBinding keyBinding : keyBindings) {
            boolean isKeyPressed = InputMappings.isKeyDown(mc.getMainWindow().getHandle(), keyBinding.getDefault().getKeyCode());
            keyBinding.setPressed(isKeyPressed);
        }
    }
}