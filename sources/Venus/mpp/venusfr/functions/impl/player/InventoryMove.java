/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.InventoryCloseEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CClickWindowPacket;

@FunctionRegister(name="InventoryMove", type=Category.Player)
public class InventoryMove
extends Function {
    private final List<IPacket<?>> packet = new ArrayList();
    public StopWatch wait = new StopWatch();

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (InventoryMove.mc.player != null) {
            KeyBinding[] keyBindingArray = new KeyBinding[]{InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindJump, InventoryMove.mc.gameSettings.keyBindSprint};
            if (ClientUtil.isConnectedToServer("funtime") && !this.wait.isReached(400L)) {
                for (KeyBinding keyBinding : keyBindingArray) {
                    keyBinding.setPressed(true);
                }
                return;
            }
            if (InventoryMove.mc.currentScreen instanceof ChatScreen || InventoryMove.mc.currentScreen instanceof EditSignScreen) {
                return;
            }
            this.updateKeyBindingState(keyBindingArray);
        }
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        IPacket<?> iPacket;
        if (ClientUtil.isConnectedToServer("funtime") && (iPacket = eventPacket.getPacket()) instanceof CClickWindowPacket) {
            CClickWindowPacket cClickWindowPacket = (CClickWindowPacket)iPacket;
            if (MoveUtils.isMoving() && InventoryMove.mc.currentScreen instanceof InventoryScreen) {
                this.packet.add(cClickWindowPacket);
                eventPacket.cancel();
            }
        }
    }

    @Subscribe
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        if (ClientUtil.isConnectedToServer("funtime") && InventoryMove.mc.currentScreen instanceof InventoryScreen && !this.packet.isEmpty() && MoveUtils.isMoving()) {
            new Thread(this::lambda$onClose$0).start();
            inventoryCloseEvent.cancel();
        }
    }

    private void updateKeyBindingState(KeyBinding[] keyBindingArray) {
        for (KeyBinding keyBinding : keyBindingArray) {
            boolean bl = InputMappings.isKeyDown(mc.getMainWindow().getHandle(), keyBinding.getDefault().getKeyCode());
            keyBinding.setPressed(bl);
        }
    }

    private void lambda$onClose$0() {
        this.wait.reset();
        try {
            Thread.sleep(300L);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        for (IPacket<?> iPacket : this.packet) {
            InventoryMove.mc.player.connection.sendPacketWithoutEvent(iPacket);
        }
        this.packet.clear();
    }
}

