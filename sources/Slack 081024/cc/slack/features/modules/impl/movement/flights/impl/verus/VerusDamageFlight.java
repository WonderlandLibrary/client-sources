// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.verus;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class VerusDamageFlight implements IFlight {

    double moveSpeed = 0.0;

    int stage = 0;
    int hops = 0;
    int ticks = 0;
    boolean attackcheck = true;

    @Override
    public void onEnable() {
        stage = -1;
        hops = 0;
        ticks = 0;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        EntityPlayer player = mc.thePlayer;
        if (player == null) return;

        if (attackcheck && !player.isDead) {
            BlockPos pos = player.getPosition().add(0, player.posY > 0 ? -100 : 100, 0);
            if (pos == null) return;

            PacketUtil.send(new C08PacketPlayerBlockPlacement(
                    pos,
                    1,
                    new ItemStack(Items.water_bucket),
                    0.0F,
                    0.5F + (float)Math.random() * 0.44F,
                    0.0F
            ));
        } else {
            attackcheck = true;
        }
    }

    @Override
    public void onAttack(AttackEvent event) {
        attackcheck = false;
    }

    @Override
    public void onMove(MoveEvent event) {
        switch (stage) {
            case -1:
                stage++;
                break;
            case 0:
                event.setZeroXZ();

                if (hops >= 4 && mc.thePlayer.onGround) {
                    stage++;
                    return;
                }

                if (mc.thePlayer.onGround) {
                    event.setY(0.42f);
                    ticks = 0;
                    hops++;
                } else {
                    ticks++;
                }
                break;
            case 1:
                mc.timer.timerSpeed = 1.0F;
                event.setZeroXZ();

                if (mc.thePlayer.hurtTime > 0) {
                    ticks = 0;
                    moveSpeed = 0.525;
                    stage++;
                    event.setY(0.42f);
                    MovementUtil.setSpeed(event, moveSpeed);
                }
                break;
            case 2:
                if (event.getY() < 0)
                    event.setY(-0.033);

                if (ticks == 0) moveSpeed *= 7;

                moveSpeed -= moveSpeed / 159.0;
                ticks++;

                MovementUtil.setSpeed(event, moveSpeed);

                if (mc.thePlayer.hurtTime == 0 && (mc.thePlayer.onGround || mc.thePlayer.isCollidedHorizontally))
                    Slack.getInstance().getModuleManager().getInstance(Flight.class).toggle();
                break;
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() != State.PRE) return;
        event.setYaw(MovementUtil.getDirection());
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getDirection() != PacketDirection.OUTGOING) return;
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (stage == 0 && hops >= 1) {
                ((C03PacketPlayer) event.getPacket()).onGround = false;
            }
        }
    }

    @Override
    public String toString() {
        return "Verus Damage";
    }
}
