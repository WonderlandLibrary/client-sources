package cc.slack.features.modules.impl.movement.longjumps.impl.vulcan;

import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.LongJump;
import cc.slack.features.modules.impl.movement.longjumps.ILongJump;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class VulcanLJ implements ILongJump {

    private boolean checking;
    private int ticks;


    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() != State.PRE) return;

        ticks++;

        if (mc.thePlayer.fallDistance > 0 && ticks % 2 == 0 && mc.thePlayer.fallDistance < 2.2) {
            mc.thePlayer.motionY += 0.14F;
        }

        switch (ticks) {
            case 1:
                mc.timer.timerSpeed = 0.5F;

                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, mc.thePlayer.onGround));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));

                checking = true;
                MovementUtil.strafe(7.9F);
                mc.thePlayer.motionY = 0.42F;
                break;

            case 2:

                mc.thePlayer.motionY += 0.1F;
                MovementUtil.strafe(2.79F);
                break;

            case 3:
                MovementUtil.strafe(2.56F);
                break;

            case 4:
                event.setGround(true);
                mc.thePlayer.onGround = true;
                MovementUtil.strafe(0.49F);
                break;

            case 5:

                MovementUtil.strafe(0.59F);
                break;

            case 6:
                MovementUtil.resetMotion();
                MovementUtil.strafe(0.3F);
                break;
        }

        if (ticks > 6 && mc.thePlayer.onGround) Slack.getInstance().getModuleManager().getInstance(LongJump.class).toggle();
    }

    @Override
    public void onDisable() {
        MovementUtil.resetMotion();
        mc.timer.timerSpeed = 1F;
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 78400000F, mc.thePlayer.posZ, mc.thePlayer.onGround));
    }

    @Override
    public void onEnable() {
        if (!mc.thePlayer.onGround) {
            Slack.getInstance().getModuleManager().getInstance(LongJump.class).toggle();
        }

        checking = false;
        ticks = 0;
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook && checking) {
            event.cancel();
            checking = false;
        }
    }

    @Override
    public String toString() {
        return "Vulcan";
    }
}
