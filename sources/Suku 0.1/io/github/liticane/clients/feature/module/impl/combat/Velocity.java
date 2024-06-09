package io.github.liticane.clients.feature.module.impl.combat;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@Module.Info(name = "Velocity", category = Module.Category.COMBAT)
public class Velocity extends Module {
    public StringProperty mode = new StringProperty("Mode", this, "Packet", "Packet", "Watchdog");
    public NumberProperty horizontal = new NumberProperty("Horizontal", this, 100, 0, 100, 1);
    public NumberProperty vertical = new NumberProperty("Vertical", this, 100, 0, 100, 1);
    public BooleanProperty difAura = new BooleanProperty("dif Aura",this, false);
    public NumberProperty horizontala = new NumberProperty("Aura Horizontal", this, 100, 0, 100, 1,() -> difAura.isToggled());
    public NumberProperty verticala = new NumberProperty("Aura Vertical", this, 100, 0, 100, 1,() -> difAura.isToggled());

    @SubscribeEvent
    private final EventListener<PacketEvent> preMotionEventEventListener = e -> {
        setSuffix(mode.getMode());
        if(e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) e.getPacket();
            switch (mode.getMode()) {
                case "Packet":
                    //might cause an issue idk
                    s12.motionX *= horizontal.getValue() / 100;
                    s12.motionY *= vertical.getValue() / 100;
                    s12.motionZ *= horizontal.getValue() / 100;
                    if (difAura.isToggled() &&Client.INSTANCE.getModuleManager().get(Aura.class).isToggled()) {
                        s12.motionX *= horizontala.getValue() / 100;
                        s12.motionY *= verticala.getValue() / 100;
                        s12.motionZ *= horizontala.getValue() / 100;
                    }
                break;
                case "Watchdog":
                    s12.motionX = 100;
                    if(mc.player.ticksExisted % 3 == 0) {
                        s12.motionY = 100;
                    } else{
                        if(!mc.player.onGround) {
                            s12.motionY = 100;
                        }
                    }
                    s12.motionZ = 100;
                    break;
            }
        }
    };
}
