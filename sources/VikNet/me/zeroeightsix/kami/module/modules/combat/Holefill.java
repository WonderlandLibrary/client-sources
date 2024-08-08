package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.exploits.NoBreakAnimation;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.zeroeightsix.kami.util.BlockInteractionHelper.canBeClicked;
import static me.zeroeightsix.kami.util.BlockInteractionHelper.getPlaceableSide;

//pasted from osiris by vik

@Module.Info(name = "Holefill", category = Module.Category.COMBAT)
public class Holefill extends Module {
    private int totalTicksRunning = 0;
    private ArrayList<BlockPos> holes = new ArrayList();

    private List<Block> whiteList = Arrays.asList(new Block[]{
            Blocks.OBSIDIAN
    });


    BlockPos pos;
    private int waitCounter;

    private Setting<Integer> range = register(Settings.integerBuilder("range").withMinimum(1).withValue(4).withMaximum(6).build());
    private Setting<Integer> yRange = register(Settings.integerBuilder("yRange").withMinimum(1).withValue(4).withMaximum(6).build());
    private Setting<Boolean> chat = register(Settings.b("chat", false));
    private Setting<Boolean> rotate = register(Settings.b("rotate", false));
    private Setting<Boolean> noGlitchBlocks = register(Settings.b("NoGlitchBlocks", true));
    private Setting<Boolean> triggerable = register(Settings.b("Triggerable", true));
    private Setting<Integer> timeoutTicks = register(Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(1).withMaximum(100).withVisibility(b -> triggerable.getValue()).build());

    public void onUpdate() {
        if (triggerable.getValue() && totalTicksRunning >= timeoutTicks.getValue()) {
            totalTicksRunning = 0;
            this.disable();
            return;
        }
        totalTicksRunning++;
        holes = new ArrayList<>();
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(mc.player.getPosition().add(-range.getValue(), -yRange.getValue(), -range.getValue()), mc.player.getPosition().add(range.getValue(), yRange.getValue(), range.getValue()));
        for (BlockPos pos : blocks) {
            if (!mc.world.getBlockState(pos).getMaterial().blocksMovement() && !mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                boolean solidNeighbours = (
                        mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN
                                && mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN
                                && mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN
                                && mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN
                                && mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR
                                && mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR
                                && mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR);
                if (solidNeighbours) {
                    this.holes.add(pos);
                }
            }
        }

        // search blocks in hotbar
        int newSlot = -1;
        for (int i = 0; i < 9; i++) {
            // filter out non-block items
            ItemStack stack =
                    mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            // only use whitelisted blocks
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (!whiteList.contains(block)) {
                continue;
            }

            newSlot = i;
            break;
        }

        // check if any blocks were found
        if (newSlot == -1)
            return;

        // set slot
        int oldSlot = mc.player.inventory.currentItem;
        //    Wrapper.getPlayer().inventory.currentItem = newSlot;

        {
            mc.player.inventory.currentItem = newSlot;
            holes.forEach(this::place);
            mc.player.inventory.currentItem = oldSlot;
            return;
        }
    }

    //  holes.forEach(blockPos -> BlockInteractionHelper.placeBlockScaffold(blockPos));

    public void onEnable() {
        if (mc.player != null && chat.getValue())
            Command.sendChatMessage("HoleFill " + ChatFormatting.GREEN + "Enabled!");
    }

    public void onDisable() {
        if (mc.player != null && chat.getValue())
            Command.sendChatMessage("HoleFill " + ChatFormatting.RED + "Disabled!");
    }

    private void place(BlockPos blockPos) {
        //if(mc.player.getDistanceSq(blockPos) <= minRange.getValue()) return;
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }// entity on block
        holefillExtras.placeBlockScaffold(blockPos, rotate.getValue());
        waitCounter++;
    }

    private void placeBlock(BlockPos pos) {

        // check if block is already placed
        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
        }

        // check if entity blocks placing
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
            }
        }

        EnumFacing side = getPlaceableSide(pos);

        // check if we have a block adjacent to blockpos to click at
        if (side == null) {
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        // check if neighbor can be right clicked
        if (!canBeClicked(neighbour)) {
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        if (noGlitchBlocks.getValue() && !mc.playerController.getCurrentGameType().equals(GameType.CREATIVE)) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
            if (ModuleManager.getModuleByName("NoBreakAnimation").isEnabled()) {
                ((NoBreakAnimation) ModuleManager.getModuleByName("NoBreakAnimation")).resetMining();
            }
        }
    }
}