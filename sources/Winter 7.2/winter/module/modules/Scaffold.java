/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.ThanksOG;
import winter.utils.other.Timer;

public class Scaffold
extends Module {
    private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
    private Timer timer = new Timer();
    private BlockData blockData;
    boolean placing;
    private int slot;

    public Scaffold() {
        super("Scaffold", Module.Category.World, -131114);
        this.setBind(47);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            double z2;
            double x2;
            double y2;
            double z22;
            double x22;
            double xOffset;
            double zOffset;
            BlockPos blockBelow1;
            int tempSlot = this.getBlockSlot();
            this.blockData = null;
            this.slot = -1;
            if (!this.mc.thePlayer.isSneaking() && tempSlot != -1 && this.mc.theWorld.getBlockState(blockBelow1 = new BlockPos(x2 = this.mc.thePlayer.posX + (xOffset = (double)MovementInput.moveForward * 0.4 * (x22 = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f))) + (double)MovementInput.moveStrafe * 0.4 * (z22 = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)))), y2 = this.mc.thePlayer.posY - 1.0, z2 = this.mc.thePlayer.posZ + (zOffset = (double)MovementInput.moveForward * 0.4 * z22 - (double)MovementInput.moveStrafe * 0.4 * x22))).getBlock() == Blocks.air) {
                this.blockData = Scaffold.getBlockData(blockBelow1, this.invalid);
                this.slot = tempSlot;
                if (this.blockData != null) {
                    event.setYaw(ThanksOG.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0]);
                    event.setPitch(ThanksOG.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1]);
                }
            }
        } else if (!event.isPre() && this.blockData != null && this.timer.hasPassed(75.0f) && this.slot != -1) {
            boolean dohax;
            this.mc.rightClickDelayTimer = 3;
            if (this.mc.gameSettings.keyBindJump.pressed && MovementInput.moveForward == 0.0f && MovementInput.moveStrafe == 0.0f) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.motionY = 0.0;
            }
            boolean bl2 = dohax = this.mc.thePlayer.inventory.currentItem != this.slot;
            if (dohax) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
            }
            if (this.mc.playerController.func_178890_a(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }
            if (dohax) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            }
        }
    }

    public static BlockData getBlockData(BlockPos pos, List list) {
        return !list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? new BlockData(pos.add(0, -1, 0), EnumFacing.UP) : (!list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : (!list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? new BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : (!list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : (!list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null))));
    }

    public static Block getBlock(int x2, int y2, int z2) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
    }

    private int getBlockSlot() {
        int i2 = 36;
        while (i2 < 45) {
            ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i2).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                return i2 - 36;
            }
            ++i2;
        }
        return -1;
    }

    public static class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

}

