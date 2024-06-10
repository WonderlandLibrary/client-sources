// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;
import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.Attacking;
import me.kaktuswasser.client.event.events.EveryTick;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.values.ModeValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;

public class Derp extends Module
{
    private final ModeValue derpMode;
    private float spin;
    private float fuck;
    private float anal;
    private float dick;
    
    public Derp() {
        super("Derp", -16711883, Category.PLAYER);
        this.derpMode = new ModeValue("derp_Derp Mode", "derpmode", "bop", new String[] { "bop", "crazy_bastard", "goat_fucker" }, this);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EveryTick) {
            this.setTag("Derp §7" + RenderHelper.getPrettyName(this.derpMode.getStringValue(), "_"));
        }
        if (event instanceof Attacking) {
            this.dick = 10.0f;
        }
        if (event instanceof PreMotion) {
            final PreMotion pre = (PreMotion)event;
            if (this.dick > 0.0f) {
                --this.dick;
            }
            final HealingBot hB = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
            if (hB.isHealing()) {
                this.dick = 25.0f;
            }
            if (this.dick == 0.0f) {
                if (this.derpMode.getStringValue().equals("crazy_bastard")) {
                    this.spin += 50.0f;
                    if (this.spin > 180.0f) {
                        this.spin = -180.0f;
                    }
                    else if (this.spin < -180.0f) {
                        this.spin = 180.0f;
                    }
                    pre.setYaw(this.spin);
                    pre.setPitch(180.0f);
                    Derp.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                }
                else if (this.derpMode.getStringValue().equals("goat_fucker")) {
                    this.fuck += 40.0f;
                    if (this.fuck > 360.0f) {
                        this.fuck = -360.0f;
                    }
                    else if (this.fuck < -360.0f) {
                        this.fuck = 360.0f;
                    }
                    pre.setPitch(this.fuck);
                    this.anal += 40.0f;
                    if (this.anal > 50.0f) {
                        this.anal = -50.0f;
                    }
                    else if (this.anal < -50.0f) {
                        this.anal = 50.0f;
                    }
                    pre.setYaw(this.anal);
                    Derp.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                }
                else if (this.derpMode.getStringValue().equals("bop")) {
                    this.spin += 3.0f;
                    if (this.spin > 360.0f) {
                        this.spin = -360.0f;
                    }
                    else if (this.spin < -360.0f) {
                        this.spin = 360.0f;
                    }
                    pre.setPitch(this.spin);
                }
            }
        }
        else if (event instanceof SentPacket) {
            final HealingBot hB2 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
            if (hB2.isHealing()) {
                return;
            }
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                if (this.dick == 0.0f) {
                    if (this.derpMode.getStringValue().equals("crazy_bastard")) {
                        if (!player.rotating) {
                            return;
                        }
                        player.yaw = this.spin;
                        player.pitch = 180.0f;
                    }
                    else if (this.derpMode.getStringValue().equals("goat_fucker")) {
                        if (!player.rotating) {
                            return;
                        }
                        player.rotating = true;
                        player.pitch = this.fuck;
                        player.yaw = this.anal;
                    }
                    else if (this.derpMode.getStringValue().equals("bop")) {
                        if (!player.rotating) {
                            return;
                        }
                        player.rotating = true;
                        player.pitch = this.spin;
                    }
                }
            }
        }
    }
}
