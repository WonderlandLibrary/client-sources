package net.futureclient.client.modules.miscellaneous.autofish;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.miscellaneous.AutoFish;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener2 extends n<we>
{
    public final AutoFish k;
    
    public Listener2(final AutoFish k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        final SPacketSoundEffect sPacketSoundEffect;
        if (eventPacket.M() instanceof SPacketSoundEffect && (sPacketSoundEffect = (SPacketSoundEffect)eventPacket.M()).getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH) && AutoFish.getMinecraft11().player.fishEntity != null && AutoFish.getMinecraft1().player.fishEntity.getAngler().equals((Object)AutoFish.getMinecraft13().player) && (AutoFish.M(this.k).B().doubleValue() == 0.0 || AutoFish.getMinecraft19().player.fishEntity.getPositionVector().distanceTo(new Vec3d(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ())) <= AutoFish.M(this.k).B().doubleValue())) {
            AutoFish.M(this.k, true);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
