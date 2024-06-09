// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import events.listeners.EventUpdate;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import intent.AquaDev.aqua.modules.misc.Disabler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import events.listeners.EventPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class InvMove extends Module
{
    public InvMove() {
        super("InvMove", Type.Player, "InvMove", 0, Category.Movement);
        Aqua.setmgr.register(new Setting("PacketSpoof", this, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPacket) {
            final Packet packet = EventPacket.getPacket();
            if (Aqua.setmgr.getSetting("InvMovePacketSpoof").isState()) {
                final C0BPacketEntityAction c0B = (C0BPacketEntityAction)packet;
                if (c0B.getAction().equals(C0BPacketEntityAction.Action.OPEN_INVENTORY)) {
                    Disabler.sendPacketUnlogged(new C0BPacketEntityAction(InvMove.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                }
                else {
                    Disabler.sendPacketUnlogged(new C0BPacketEntityAction(InvMove.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
            }
        }
        if (event instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("InvMovePacketSpoof").isState() && InvMove.mc.currentScreen instanceof GuiInventory) {
                return;
            }
            if (InvMove.mc.currentScreen instanceof GuiChat || Aqua.moduleManager.getModuleByName("Longjump").isToggled()) {
                return;
            }
            InvMove.mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindForward);
            InvMove.mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindBack);
            InvMove.mc.gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindRight);
            InvMove.mc.gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindLeft);
            InvMove.mc.gameSettings.keyBindSprint.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindSprint);
            InvMove.mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindJump);
        }
    }
}
