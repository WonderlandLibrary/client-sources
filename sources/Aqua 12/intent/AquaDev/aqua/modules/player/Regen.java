// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import events.listeners.EventTick;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Regen extends Module
{
    public Regen() {
        super("Regen", Type.Player, "Regen", 0, Category.Player);
        System.out.println("Sprint::init");
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
        if (event instanceof EventTick && Regen.mc.thePlayer.getHealth() < 20.0f && Regen.mc.thePlayer.getFoodStats().getFoodLevel() > 19) {
            for (int i = 0; i < 10; ++i) {
                Regen.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
