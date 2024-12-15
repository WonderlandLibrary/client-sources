package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class VulcanAndVerusNoFall extends Mode<NoFall> {

    public VulcanAndVerusNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        boolean overVoid = false;


        for (var i = 0; i <= 5*40; i++) {
            final WorldClient world = PlayerUtil.mc.theWorld;
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ);

            // Get the block at the current position
            Block block = world.getBlockState(pos).getBlock();

            // Check if the block is not air
            if (block != Blocks.air || mc.thePlayer.onGround) {
                overVoid = false; // Found a solid block
                break; // Stop checking further
            } else {
                overVoid = true; // Still ewover tehe void (air)
            }
        }


        if (FallDistanceComponent.distance > 2.2 && !overVoid && !getModule(Scaffold.class).isEnabled() && !getModule(LongJump.class).isEnabled()) {
            event.setPosY(event.getPosY() + 1E-13);
            PacketUtil.send(new C03PacketPlayer(true));
            mc.timer.timerSpeed = 0.5f;
            FallDistanceComponent.distance = 0;

        } else if(FallDistanceComponent.distance > 2 && !overVoid && !getModule(Scaffold.class).isEnabled()){
            event.setPosY(event.getPosY() + 1E-13);
            PacketUtil.send(new C03PacketPlayer(true));
            mc.timer.timerSpeed = 0.5f;
            FallDistanceComponent.distance = 0;
        }

    };
}