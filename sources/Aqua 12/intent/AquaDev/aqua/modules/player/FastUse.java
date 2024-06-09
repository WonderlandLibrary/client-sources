// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.item.ItemFood;
import events.listeners.EventTick;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class FastUse extends Module
{
    public static double Packets;
    
    public FastUse() {
        super("FastUse", Type.Player, "FastUse", 0, Category.Player);
        Aqua.setmgr.register(new Setting("Packets", this, 6.0, 0.1, 50.0, false));
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
    public void onEvent(final Event e) {
        if (e instanceof EventTick && FastUse.mc.thePlayer.getCurrentEquippedItem() != null && FastUse.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood && FastUse.mc.thePlayer.isUsingItem()) {
            FastUse.Packets = (float)Aqua.setmgr.getSetting("FastUsePackets").getCurrentNumber();
            for (int i = 0; i < FastUse.Packets; ++i) {
                FastUse.mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
            }
        }
    }
}
