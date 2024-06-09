/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.fun;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.network.play.client.C0APacketAnimation;

@Module.Mod
public class Derp
extends Module {
    @Option.Op
    private boolean rotation;
    @Option.Op
    private boolean head;
    @Option.Op(name="RotationSpeed", min=1.0, max=100.0, increment=1.0)
    private double increment = 25.0;
    private double serverYaw;

    @EventTarget(value=0)
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.rotation) {
                this.serverYaw += this.increment;
                event.setYaw((float)this.serverYaw);
            }
            if (this.head) {
                event.setPitch(180.0f);
            } else if (!this.head && !this.rotation) {
                event.setYaw((float)(Math.random() * 360.0));
                event.setPitch((float)(Math.random() * 360.0));
                ClientUtils.packet(new C0APacketAnimation());
            }
        }
    }
}

