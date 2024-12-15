package com.alan.clients.module.impl.player.antivoid;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.BlinkComponent;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.AntiVoid;
import com.alan.clients.module.impl.player.FastBreak;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.BlockPos;

import static com.alan.clients.module.impl.player.scaffold.sprint.WatchdogJumpSprint.start3;

public class WatchdogAntiVoid extends Mode<AntiVoid> {

    private Vector3d position, motion;

    private boolean wasVoid, setBack;
    private int overVoidTicks;
    private boolean sprinting;

    public WatchdogAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    private final NumberValue distance = new NumberValue("Distance", this, 3, 0, 10, .1);

    @EventLink(value = Priorities.LOW)
    public final Listener<MoveEvent> moveEventListener = event -> {
        if (mc.thePlayer.ticksExisted <= 75) return;

        boolean overVoid = false;
        if (MoveUtil.enoughMovementForSprinting()) {
            sprinting = true;
        } else {
            sprinting = false;
        }

        for (var i = 0; i <= distance.getValue().doubleValue() * 40; i++) {
            final WorldClient world = PlayerUtil.mc.theWorld;

            // Predict the player's position using prediction method
            Vector3d predictedPos = MoveUtil.predictMovement(mc.thePlayer, new Vector2f(mc.thePlayer.moveStrafing, mc.thePlayer.moveForward), i, true);

            // Get the block position under the predicted position
            BlockPos pos = new BlockPos(predictedPos.x, predictedPos.y - i, predictedPos.z);

            // Get the block at the predicted position
            Block block = world.getBlockState(pos).getBlock();

            // Check if the block is not air, or if the player is on the ground
            if (block != Blocks.air || mc.thePlayer.onGround) {
                overVoid = false; // Found a solid block or the player is on the ground
                break; // Stop checking further
            } else {
                overVoid = true; // Still over the void (air)
            }
        }


        if (overVoid) {
            overVoidTicks++;
        } else if (mc.thePlayer.onGround) {
            overVoidTicks = 0;
        }

        if (overVoid && position != null && motion != null && overVoidTicks < 30 + distance.getValue().doubleValue() * 30 && !(getModule(LongJump.class).isEnabled() && Client.INSTANCE.getModuleManager().get(LongJump.class).mode.getValue().getName().equals("Watchdog Fire Ball")) && !getModule(Flight.class).isEnabled() && (mc.thePlayer.inventory.getStackInSlot(0) == null || mc.thePlayer.inventory.getStackInSlot(0).getItem() != Items.compass) && !(getModule(Scaffold.class).isEnabled())) {

            if (!setBack) {
                wasVoid = true;

                PingSpoofComponent.blink();
                BlinkComponent.setExempt(C0FPacketConfirmTransaction.class, C00PacketKeepAlive.class, C01PacketChatMessage.class);

                if ((FallDistanceComponent.distance > distance.getValue().doubleValue() || setBack)   && !(getModule(Scaffold.class).isEnabled())) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(position.x, position.y - MoveUtil.UNLOADED_CHUNK_MOTION, position.z, false));

                    BlinkComponent.packets.clear();

                    FallDistanceComponent.distance = 0;

                    setBack = true;
                }
            } else {
                PingSpoofComponent.dispatch();
            }
        } else {

            setBack = false;

            if (wasVoid) {
                PingSpoofComponent.dispatch();
                wasVoid = false;
            }

            motion = new Vector3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            position = new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        }
    };
}


