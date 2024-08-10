package cc.slack.features.modules.impl.movement.speeds.verus;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.RandomUtils;

public class VerusGroundSpeed implements ISpeed {


    private double moveSpeed;
    private int stage;
    private boolean firstHop;


    @Override
    public void onEnable() {
        moveSpeed = MovementUtil.getBaseMoveSpeed();
        firstHop = true;
        stage = 0;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindJump.pressed = false;
    }

    @Override
    public void onMove(MoveEvent event) {
    if(MovementUtil.isMoving() && MovementUtil.isOnGround(0.02) && !mc.thePlayer.isCollidedHorizontally) {
            if(firstHop) {
                PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                event.setY(mc.thePlayer.motionY = 0.01);
                firstHop = false;
                stage = 0;
                moveSpeed = 0;
            } else {
                if(stage >= 8) {
                    stage = 0;
                    PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                    event.setY(mc.thePlayer.motionY = 0.01);
                    moveSpeed = 0;
                }
                if(mc.thePlayer.onGround) {
                    stage++;
                    MovementUtil.setSpeed(event, moveSpeed += RandomUtils.nextDouble(0.10, 0.18));
                }
            }
        } else {
            firstHop = true;
            stage = 0;
            moveSpeed = 0;
        }
    }

    @Override
    public String toString() {
        return "Verus Ground";
    }

}
