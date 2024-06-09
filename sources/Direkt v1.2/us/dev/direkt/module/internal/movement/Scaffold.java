package us.dev.direkt.module.internal.movement;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

/**
 * Created by Meckimp on 1/25/2016.
 */
@ModData(label = "Scaffold", category = ModCategory.MOVEMENT)
public class Scaffold extends ToggleableModule {

    @Exposed(description = "Should you tower up as you move")
    private final Property<Boolean> doTower = new Property<>("Tower", false);

    private boolean doSetPitch = false;

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        int x = MathHelper.floor_double(Wrapper.getPlayer().posX);
        int z = MathHelper.floor_double(Wrapper.getPlayer().posZ);
        int y = MathHelper.floor_double((Wrapper.getPlayer().getEntityBoundingBox().minY - (Wrapper.getPlayer().motionY > 0.0D ? 2 : 1)));

        if (Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND) != null && Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            placeBlock(true, x, y, z, true);
        }
    });

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if(event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketPlayer.Position){
            if(this.doSetPitch) {
                event.setPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, 90.0F, Wrapper.getPlayer().onGround));
                this.doSetPitch = false;
            }
        }
    });

    private void placeBlock(boolean setPitch, int x, int y, int z, boolean turn) {
        int i = getSideNeeded(x, y, z);
        if (i == -1) {
            return;
        }
        placeBlock(setPitch, x, y, z, i, turn);
    }

    private void placeBlock(boolean setPitch, int x, int y, int z, int side, boolean turn) {
        float xo = side == 5 ? 1.0F : side == 4 ? -1.0F : 0.5F;
        float yo = side == 1 ? 1.0F : side == 0 ? -1.0F : 0.5F;
        float zo = side == 3 ? 1.0F : side == 2 ? -1.0F : 0.5F;
        if (xo != 0.5F) {
            x = (int) (x - xo);
        } else if (yo != 0.5F) {
            y = (int) (y - yo);
        } else if (zo != 0.5F) {
            z = (int) (z - zo);
        }
        if ((yo != 0.5F) && (turn)) {
            this.doSetPitch = true;
        }
        if (Wrapper.getPlayerController().processRightClickBlock(Wrapper.getPlayer(), Wrapper.getWorld(), Wrapper.getPlayer().getHeldItem(EnumHand.MAIN_HAND), new BlockPos(x, y, z), EnumFacing.getFront(side), new Vec3d(xo, yo, zo), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
        }
    }

    private int getSideNeeded(int x, int y, int z) {
        Block loc = getBlock(x, y, z);
        if (getBlocks(loc)) {
            return -1;
        }
        for (int i = -1; i <= 1; i++) {
            if (i != 0) {
                int bx = x + i;
                Block b = getBlock(bx, y, z);
                if (getBlocks(b)) {
                    return i == 1 ? 4 : 5;
                }
            }
        }
        if(this.doTower.getValue()) {
            for (int i = -1; i <= 1; i++) {
                if (i != 0) {
                    int bi = y + i;
                    Block b = getBlock(x, bi, z);
                    if (getBlocks(b)) {
                        return i == 1 ? 0 : 1;
                    }
                }
            }
        }
        for (int i = -1; i <= 1; i++) {
            if (i != 0) {
                int bi = z + i;
                Block b = getBlock(x, y, bi);
                if (getBlocks(b)) {
                    return i == 1 ? 2 : 3;
                }
            }
        }
        return -1;
    }

    private Block getBlock(double x, double y, double z) {
        return Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    private boolean getBlocks(Block b){
        return (b != Blocks.AIR) && (b != Blocks.TALLGRASS) && (b != Blocks.DEADBUSH) && (b != Blocks.DOUBLE_PLANT) && (b != Blocks.LAVA) && (b != Blocks.WATER);
    }

}