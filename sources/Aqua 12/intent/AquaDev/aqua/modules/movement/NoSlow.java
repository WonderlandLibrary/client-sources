// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.network.Packet;
import events.listeners.EventUpdate;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import events.listeners.EventPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class NoSlow extends Module
{
    public NoSlow() {
        super("NoSlow", Type.Movement, "NoSlow", 0, Category.Movement);
        Aqua.setmgr.register(new Setting("Mode", this, "Watchdog", new String[] { "Watchdog", "Vanilla" }));
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
        if (event instanceof EventPacket && Aqua.setmgr.getSetting("NoSlowMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof C09PacketHeldItemChange && NoSlow.mc.thePlayer.isUsingItem() && !Aqua.moduleManager.getModuleByName("Scaffold").isToggled() && !NoSlow.mc.isSingleplayer() && !NoSlow.mc.getCurrentServerData().serverIP.equalsIgnoreCase("cubecraft.net") && !NoSlow.mc.isSingleplayer()) {
                event.setCancelled(true);
            }
        }
        if (event instanceof EventUpdate && Aqua.setmgr.getSetting("NoSlowMode").getCurrentMode().equalsIgnoreCase("Watchdog") && !Aqua.moduleManager.getModuleByName("Scaffold").isToggled() && !NoSlow.mc.isSingleplayer() && !NoSlow.mc.getCurrentServerData().serverIP.equalsIgnoreCase("cubecraft.net") && !NoSlow.mc.isSingleplayer()) {
            final int curSlot = NoSlow.mc.thePlayer.inventory.currentItem;
            final int spoof = (curSlot == 0) ? 1 : -1;
            if (NoSlow.mc.thePlayer.isUsingItem()) {
                NoSlow.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(curSlot + spoof));
                NoSlow.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(curSlot));
                event.setCancelled(true);
            }
        }
    }
}
