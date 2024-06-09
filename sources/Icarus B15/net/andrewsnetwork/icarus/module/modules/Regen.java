// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Regen extends Module
{
    private final Value<Integer> packets;
    private final Value<Integer> hearts;
    
    public Regen() {
        super("Regen", -8129236, Category.PLAYER);
        this.packets = (Value<Integer>)new ConstrainedValue("regen_Packets", "packets", 100, 1, 1000, this);
        this.hearts = (Value<Integer>)new ConstrainedValue("regen_Health", "health", 16, 1, 20, this);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            Label_0047: {
                if (e instanceof EatMyAssYouFuckingDecompiler) {
                    OutputStreamWriter request = new OutputStreamWriter(System.out);
                    try {
                        request.flush();
                    }
                    catch (IOException ex) {
                        break Label_0047;
                    }
                    finally {
                        request = null;
                    }
                    request = null;
                }
            }
            if (Regen.mc.thePlayer.getFoodStats().getFoodLevel() > 17 && Regen.mc.thePlayer.getHealth() < this.hearts.getValue() && Regen.mc.thePlayer.onGround) {
                for (float i = 0.0f; i < this.packets.getValue(); ++i) {
                    Regen.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(Regen.mc.thePlayer.onGround));
                }
            }
        }
    }
}
