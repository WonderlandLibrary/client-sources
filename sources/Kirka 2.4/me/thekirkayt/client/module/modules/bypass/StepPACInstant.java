/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.StepEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

@Module.Mod(displayName="StepPACInstant")
public class StepPACInstant
extends Module {
    @Option.Op(min=1.0, max=10.0, increment=1.0, name="Height")
    private double height = 1.0;
    @Option.Op(name="Old Version")
    public boolean packet;
    @Option.Op(name="Reverse")
    private boolean reverse;
    private me.thekirkayt.utils.Timer timer = new me.thekirkayt.utils.Timer();

    @EventTarget
    private void onStep(StepEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.height > 1.0) {
                Timer.timerSpeed = 1.0f;
                event.setStepHeight(this.height);
            } else if (!ClientUtils.movementInput().jump && ClientUtils.player().isCollidedVertically) {
                event.setStepHeight(1.0);
                event.setActive(true);
            }
        } else if (event.getState() == Event.State.POST && this.packet) {
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.42, ClientUtils.z(), ClientUtils.player().onGround));
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.75, ClientUtils.z(), ClientUtils.player().onGround));
            Timer.timerSpeed = 0.3f;
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    Timer.timerSpeed = 1.0f;
                }
            }).start();
        }
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        if (this.reverse && !ClientUtils.player().onGround && !ClientUtils.player().isCollidedHorizontally) {
            ClientUtils.player().motionY = -5.0;
            event.setY(-5.0);
        }
    }

}

