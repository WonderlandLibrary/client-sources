package io.github.liticane.monoxide.module.impl.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjglx.input.Keyboard;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "InventoryMove", description = "Move inside your inventory.", category = ModuleCategory.MOVEMENT)
public class InventoryMoveModule extends Module {
    private final BooleanValue openPacket = new BooleanValue("No Open Packet", this, false),
            carry = new BooleanValue("Carry", this, false);

    @Listen
    public final void onUpdateMotion(UpdateMotionEvent updateMotionEvent) {
        if (updateMotionEvent.getType() == UpdateMotionEvent.Type.MID) {
            block3 : {
                KeyBinding[] moveKeys;
                block2 : {
                    moveKeys = new KeyBinding[]{Methods.mc.gameSettings.keyBindForward, Methods.mc.gameSettings.keyBindLeft, Methods.mc.gameSettings.keyBindBack, Methods.mc.gameSettings.keyBindRight, Methods.mc.gameSettings.keyBindJump, Methods.mc.gameSettings.keyBindSneak, Methods.mc.gameSettings.keyBindSprint};
                    if (Methods.mc.currentScreen == null || (Methods.mc.currentScreen instanceof GuiChat))
                        break block2;
                    for (KeyBinding key : moveKeys) {
                        key.pressed = Keyboard.isKeyDown(key.getKeyCode());
                    }
                    break block3;
                }

                for (KeyBinding bind : moveKeys) {
                    if (Keyboard.isKeyDown(bind.getKeyCode()))
                        continue;
                    KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                }
            }
        }
    }

    @Listen
    public final void onPacketEvent(PacketEvent packetEvent) {
        if(mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (packetEvent.getType() == PacketEvent.Type.OUTGOING) {
            if(openPacket.getValue() && (packetEvent.getPacket() instanceof S2DPacketOpenWindow || packetEvent.getPacket() instanceof S2EPacketCloseWindow)) {
                packetEvent.setCancelled(true);
            }

            if(carry.getValue() && packetEvent.getPacket() instanceof C0DPacketCloseWindow) {
                packetEvent.setCancelled(true);
            }
        }
    }

}
