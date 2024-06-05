package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    private final Option<Boolean> Blink = new Option("Hypixel Bypass", "Hypixel Bypass", true);

    public InvMove() {
        super("InvMove", new String[]{}, ModuleType.Blatant);
        this.addValues(Blink);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        if (Minecraft.currentScreen != null && !(Minecraft.currentScreen instanceof GuiChat)) {
//			mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
//			mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            //Client.getModuleManager().getModuleByClass(Blink.class).setEnabled(true);
            KeyBinding[] key = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
                    mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight,
                    mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump};
            KeyBinding[] array;
            for (int length = (array = key).length, i = 0; i < length; ++i) {
                KeyBinding b = array[i];
                KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
            }
            //Client.getModuleManager().getModuleByClass(Blink.class).setEnabled(false);
//		}else if(this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat) && ((Boolean) this.Blink.getValue()).booleanValue() == true){
//			KeyBinding[] key = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack,
//					this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight,
//					this.mc.gameSettings.keyBindSprint, this.mc.gameSettings.keyBindJump };
//			KeyBinding[] array;
//			for (int length = (array = key).length, i = 0; i < length; ++i) {
//				KeyBinding b = array[i];
//				KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
//			}
//		}
            //Client.getModuleManager().getModuleByClass(Blink.class).setEnabled(false);
        }
    }

    @EventHandler
    public void onPacket(EventPacketSend e, EventPacketReceive e1) {
        Packet p = EventPacketSend.getPacket();
        C0FPacketConfirmTransaction c0F = (C0FPacketConfirmTransaction) p;
        if (Blink.getValue().booleanValue()) {
            if (Minecraft.currentScreen != null && !(Minecraft.currentScreen instanceof GuiChat) && Minecraft.currentScreen instanceof GuiInventory) {
                if (e1.getPacket() instanceof S08PacketPlayerPosLook) {
                    e1.setCancelled(true);
                    Helper.sendDebugMessage("Trying Disabled S08...");
                    if (EventPacketSend.getPacket() instanceof C0FPacketConfirmTransaction) {
                        e.setCancelled(true);
                        Helper.sendMessage("Trying Disabled C0F...");
                    }
                }
            }
        }
    }
}
