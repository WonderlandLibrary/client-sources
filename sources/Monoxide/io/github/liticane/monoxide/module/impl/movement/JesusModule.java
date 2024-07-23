package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.util.player.movement.MoveUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.event.minecraft.world.CollisionBoxesEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "Jesus", description = "Walk on water like jesus", category = ModuleCategory.MOVEMENT)
public class JesusModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{"Solid", "Matrix", "Verus"});

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Listen
    public void onMotion(UpdateMotionEvent updateMotionEvent) {
        if (updateMotionEvent.getType() == UpdateMotionEvent.Type.MID) {
            switch (mode.getValue()) {
                case "Matrix":
                    if (Methods.mc.thePlayer.isInWater()) {
                        if (Methods.mc.thePlayer.isCollidedHorizontally) {
                            Methods.mc.thePlayer.motionY = 0.22D;
                        } else {
                            Methods.mc.thePlayer.motionY = 0.13D;
                        }
                        Methods.mc.gameSettings.keyBindJump.pressed = false;
                    }
                    break;
                case "Verus":
                    if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockLiquid) {
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
                        mc.thePlayer.motionY = 0.0;
                        MoveUtil.strafe(0.35);
                    }
                    break;
            }
        }
    }

    @Listen
    public void onCollisionBoxes(CollisionBoxesEvent collisionBoxesEvent) {
        if (Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;

        switch (mode.getValue()) {
            case "Solid":
                BlockPos blockPos = collisionBoxesEvent.getBlockPos();

                if (Methods.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.water) {
                    collisionBoxesEvent.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
                break;
        }
    }

}
