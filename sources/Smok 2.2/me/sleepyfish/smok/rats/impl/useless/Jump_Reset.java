package me.sleepyfish.smok.rats.impl.useless;

import maxstats.weave.event.EventSendPacket;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.stats.StatList;

// Class from SMok Client by SleepyFish
public class Jump_Reset extends Rat {

    BoolSetting weaponOnly;
    BoolSetting fakeJump;

    private Timer timer = new Timer();

    public Jump_Reset() {
        super("Jump Reset", Rat.Category.Useless, "Reduce KB by jumping everytime you get hit");
    }

    @Override
    public void setup() {
        this.addSetting(this.weaponOnly = new BoolSetting("Weapon only", true));
        this.addSetting(this.fakeJump = new BoolSetting("Fake Jump", false));
    }

    @SmokEvent
    public void onPacket(EventSendPacket e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            if (!Utils.canLegitWork()) {
                return;
            }

            if (mc.thePlayer.hurtTime < 9 || mc.thePlayer.isDead || mc.thePlayer.isSpectator()) {
                return;
            }

            if (this.fakeJump.isEnabled()) {
                double mX = mc.thePlayer.motionX;
                double mY = mc.thePlayer.motionY;
                double mZ = mc.thePlayer.motionZ;

                e.setCancelled(true);

                mc.thePlayer.onGround = false;
                mc.thePlayer.jumpMovementFactor = 0.02F;
                mc.thePlayer.addStat(StatList.jumpStat, 1);

                S12PacketEntityVelocity packet = new S12PacketEntityVelocity(mc.thePlayer.getEntityId(), mX / 4.0, mY / 2.0, mZ / 4.0);
                e.setPacket(packet);

                if (this.timer.delay(200L)) {
                    mc.thePlayer.onGround = false;
                    this.timer.reset();
                }
            } else if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    }

}