// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.ReceivePacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.TimeHelper;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;

public class NoVelocity extends Module
{
    private final TimeHelper time;
    public final Value<Boolean> water;
    private final Value<Float> velocity;
    
    public NoVelocity() {
        super("NoVelocity", -10916572, Category.COMBAT);
        this.time = new TimeHelper();
        this.water = new Value<Boolean>("novelocity_Water", "water", true, this);
        this.velocity = (Value<Float>)new ConstrainedValue("novelocity_Velocity", "velocity", 0.0f, -1.0f, 1.0f, this);
        this.setTag("No Velocity");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof ReceivePacket) {
            final ReceivePacket event = (ReceivePacket)e;
            if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)event.getPacket()).func_149412_c() == NoVelocity.mc.thePlayer.getEntityId()) {
                final S12PacketEntityVelocity tmp58_57;
                final S12PacketEntityVelocity packet = tmp58_57 = (S12PacketEntityVelocity)event.getPacket();
                tmp58_57.field_149415_b *= (int)(Object)this.velocity.getValue();
                final S12PacketEntityVelocity tmp76_75 = packet;
                tmp76_75.field_149416_c *= (int)(Object)this.velocity.getValue();
                final S12PacketEntityVelocity tmp94_93 = packet;
                tmp94_93.field_149414_d *= (int)(Object)this.velocity.getValue();
                if (packet.field_149415_b == 0 && packet.field_149416_c == 0 && packet.field_149414_d == 0) {
                    event.setCancelled(true);
                }
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
                final S27PacketExplosion tmp156_155;
                final S27PacketExplosion packet2 = tmp156_155 = (S27PacketExplosion)event.getPacket();
                tmp156_155.field_149152_f *= this.velocity.getValue();
                final S27PacketExplosion tmp174_173 = packet2;
                tmp174_173.field_149153_g *= this.velocity.getValue();
                final S27PacketExplosion tmp192_191 = packet2;
                tmp192_191.field_149159_h *= this.velocity.getValue();
            }
        }
    }
}
