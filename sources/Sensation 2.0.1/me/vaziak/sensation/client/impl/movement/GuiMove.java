package me.vaziak.sensation.client.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.utils.math.TimerUtil;

import java.util.Objects;

public class GuiMove extends Module {

    private BooleanProperty prop_desync = new BooleanProperty("Desyncronize", "Desyncronizes the inventory with the server (spoof not being inside, bypasses most inventory move checks)", null, false);

    public static boolean closed, megalul, cancancel;

    private TimerUtil stopwatch;

    public GuiMove() {
        super("Gui Move", Category.MOVEMENT);
        registerValue(prop_desync);
        stopwatch = new TimerUtil();
    }

    @Collect
    public void onSendPacket(SendPacketEvent sendPacketEvent) {
        if (prop_desync.getValue() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest)) {
            if (sendPacketEvent.getPacket() instanceof C0BPacketEntityAction) {
                C0BPacketEntityAction packet = (C0BPacketEntityAction) sendPacketEvent.getPacket();
                if (packet.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
                    closed = false;
                }
            }

            if (sendPacketEvent.getPacket() instanceof C0EPacketClickWindow) {
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                closed = false;
                megalul = true;
                sendPacketEvent.setCancelled(cancancel);
            }
        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (this.getState()) {
            if (prop_desync.getValue() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest)) {
                if (!closed) {
                    if (stopwatch.hasPassed(51)) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
                        closed = true;
                        stopwatch.reset();
                    }
                } else {
                    if (megalul) {
                        if (stopwatch.hasPassed(52)) {
                            cancancel = true;
                            stopwatch.reset();
                        } else {
                            cancancel = false;
                        }
                    }
                }
            }
            KeyBinding[] moveKeys = {mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft,
                    mc.gameSettings.keyBindBack, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump,
                    mc.gameSettings.keyBindSprint,};
            if ((mc.currentScreen != null)
                    && !(mc.currentScreen instanceof GuiChat)) {
                KeyBinding[] array;
                int length = (array = moveKeys).length;
                for (int i = 0; i < length; i++) {
                    KeyBinding key = array[i];
                    key.pressed = Keyboard.isKeyDown(key.getKeyCode());
                }
            } else if (Objects.isNull(mc.currentScreen)) {
                KeyBinding[] array2;
                int length2 = (array2 = moveKeys).length;
                for (int j = 0; j < length2; j++) {
                    KeyBinding bind = array2[j];
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
    }

}
