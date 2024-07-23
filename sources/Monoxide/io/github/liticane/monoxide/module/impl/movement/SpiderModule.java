package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.event.minecraft.world.CollisionBoxesEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.value.impl.ModeValue;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleData(name = "Spider", description = "Climb up walls", category = ModuleCategory.MOVEMENT)
public class SpiderModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{"Jump", "Collision", "Verus"});

    // Verus
    private boolean chill = false;

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void onCollisionBoxes(CollisionBoxesEvent collisionBoxesEvent) {
        if (Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;

        if (noWallNearby())
            return;

        if (mode.getValue().equals("Collision")) {
            if (mc.thePlayer.motionY > 0) {
                return;
            }

            BlockPos blockPos = collisionBoxesEvent.getBlockPos();
            collisionBoxesEvent.setBoundingBox(new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, 1, blockPos.getZ() + 1));
        }
    }

    @Listen
    public void onUpdateEvent(UpdateMotionEvent updateMotionEvent) {
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        switch (mode.getValue()) {
            case "Jump":
                if (!noWallNearby())
                    mc.thePlayer.jump();
                break;
            case "Verus":
                if (updateMotionEvent.getType() == UpdateMotionEvent.Type.PRE) {
                    if (!noWallNearby()) {
                        BlockPos pos = mc.thePlayer.getPosition().add(0.0, -1.5, 0.0);
                        sendPacketUnlogged(
                                new C08PacketPlayerBlockPlacement(
                                        pos,
                                        1,
                                        new ItemStack(Blocks.stone.getItem(mc.theWorld, pos)),
                                        0.0F,
                                        0.5F + (float) Math.random() * 0.44F,
                                        0.0F
                                )
                        );
                        if (mc.thePlayer.ticksExisted % 2 == 0) {
                            chill = true;
                            mc.thePlayer.motionY = 0.72134;
                        }
                    } else {
                        if (chill) {
                            chill = false;
                            mc.thePlayer.motionY = 0.1;
                        }
                    }
                }
                break;
        }

    }

    private boolean noWallNearby() {
        return mc.thePlayer == null || !mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || !(mc.thePlayer.fallDistance < 1.0F);
    }

}
