package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.SafeWalkEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.AimUtil;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.util.Arrays;
import java.util.List;

/**
 * Created by myche on 2/25/2017.
 */
public class Scaffold extends Module {

    private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);

    private Stopwatch timer = new Stopwatch();
    private BlockData blockData;
    boolean placing;
    private int slot;

    public Scaffold() {
        super("Scaffold", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPre(UpdateEvent event) {
        if (event.type == EventType.PRE) {
            int tempSlot = getBlockSlot();
            blockData = null;
            slot = -1;
            if (!mc.thePlayer.isSneaking() && tempSlot != -1) {
                double x2 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
                double z2 = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
                double xOffset = mc.thePlayer.movementInput.moveForward * 0.4 * x2 + mc.thePlayer.movementInput.moveStrafe * 0.4 * z2;
                double zOffset = mc.thePlayer.movementInput.moveForward * 0.4 * z2 - mc.thePlayer.movementInput.moveStrafe * 0.4 * x2;
                double x = mc.thePlayer.posX + xOffset, y = mc.thePlayer.posY - 1, z = mc.thePlayer.posZ + zOffset;
                BlockPos blockBelow1 = new BlockPos(x, y, z);
                if (mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air) {
                    blockData = getBlockData(blockBelow1, invalid);
                    slot = tempSlot;
                    if (blockData != null) {
                        event.yaw = AimUtil.getBlockRotations(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ(), blockData.face)[0];
                        event.pitch = AimUtil.getBlockRotations(blockData.position.getX(), blockData.position.getY(), blockData.position.getZ(), blockData.face)[1];
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void update(UpdateEvent e5) {
        if(mc.thePlayer.swingProgress <= 0 && !mc.thePlayer.isEating()) {
            mc.thePlayer.swingProgressInt = 5;
        }
    }

    @SubscribeEvent
    public void onPost(UpdateEvent post) {
        if ((post.type == EventType.POST) && (blockData != null) && (timer.hasCompleted(75L)) && slot != -1) {
            mc.rightClickDelayTimer = 3;
            boolean dohax = mc.thePlayer.inventory.currentItem != slot;
            if (dohax)
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
            if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + slot).getStack(),
                    blockData.position, blockData.face, new Vec3(blockData.position.getX(),
                            blockData.position.getY(), blockData.position.getZ()))) {
                mc.thePlayer.swingItem();
            }
            if (dohax)
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
    }

    @SubscribeEvent
    public void onSafewalk(SafeWalkEvent e) {
            e.setCancelled(true);
    }

    public static BlockData getBlockData(BlockPos pos, List list) {
        return !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? new BlockData(pos.add(0, -1, 0), EnumFacing.UP) : !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())
                ? new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? new BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())
                ? new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())
                ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null;
    }

    public static class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    public static Block getBlock(int x, int y, int z) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    private int getBlockSlot() {
        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock)
                return i - 36;
        }
        return -1;
    }
}
