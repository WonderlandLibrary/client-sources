package me.finz0.osiris.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//wrote this like a long time ago code is bad but it works so whatever
public class HoleFill extends Module {
    public HoleFill() {
        super("HoleFill", Category.COMBAT, "Fills nearby obby and bedrock holes");
    }

    private ArrayList<BlockPos> holes = new ArrayList();

    private List<Block> whiteList = Arrays.asList(new Block[] {
            Blocks.OBSIDIAN
    });

    Setting range;
    Setting yRange;
    Setting waitTick;
    Setting chat;
    Setting rotate;
    Setting ec;

    BlockPos pos;
    private int waitCounter;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(range = new Setting("Range", this, 5, 0, 10, false, "HoleFillRange"));
        AuroraMod.getInstance().settingsManager.rSetting(yRange = new Setting("RangeY", this, 2, 0, 10, false, "HoleFillRangeY"));
        AuroraMod.getInstance().settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "HoleFillRotate"));
        AuroraMod.getInstance().settingsManager.rSetting(waitTick = new Setting("TickDelay", this, 1, 0, 20, true, "HoleFillTickDelay"));
        AuroraMod.getInstance().settingsManager.rSetting(ec = new Setting("UseEC", this, false, "HoleFillUseEC"));
        AuroraMod.getInstance().settingsManager.rSetting(chat = new Setting("Chat", this, false, "HoleFillChat"));
    }

    public void onUpdate() {
        holes = new ArrayList<>();
        if(ec.getValBoolean()){
            if(!whiteList.contains(Blocks.ENDER_CHEST))
                whiteList.add(Blocks.ENDER_CHEST);
        } else {
            if(whiteList.contains(Blocks.ENDER_CHEST))
                whiteList.remove(Blocks.ENDER_CHEST);
        }

        Iterable<BlockPos> blocks = BlockPos.getAllInBox(mc.player.getPosition().add(-range.getValDouble(), -yRange.getValDouble(), -range.getValDouble()), mc.player.getPosition().add(range.getValDouble(), yRange.getValDouble(), range.getValDouble()));
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
        for(int i = 0; i < 9; i++)
        {
            // filter out non-block items
            ItemStack stack =
                    mc.player.inventory.getStackInSlot(i);

            if(stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
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
        if(newSlot == -1)
            return;

        // set slot
        int oldSlot = mc.player.inventory.currentItem;
        //    Wrapper.getPlayer().inventory.currentItem = newSlot;

        if (waitTick.getValDouble() > 0) {
            if (waitCounter < waitTick.getValDouble()) {
                //  waitCounter++;
                mc.player.inventory.currentItem = newSlot;
                holes.forEach(this::place);
                mc.player.inventory.currentItem = oldSlot;
                return;
            } else {
                waitCounter = 0;
            }
        }

        //  holes.forEach(blockPos -> BlockInteractionHelper.placeBlockScaffold(blockPos));
    }

    public void onEnable() {
        if(mc.player != null && chat.getValBoolean()) Command.sendClientMessage("HoleFill " + ChatFormatting.GREEN + "Enabled!");
    }

    public void onDisable() {
        if(mc.player != null && chat.getValBoolean()) Command.sendClientMessage("HoleFill " + ChatFormatting.RED + "Disabled!");
    }

    private void place(BlockPos blockPos) {
        //if(mc.player.getDistanceSq(blockPos) <= minRange.getValue()) return;
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) { return; }
        }// entity on block
        AutoFeetPlace.placeBlockScaffold(blockPos, rotate.getValBoolean());
        waitCounter++;
    }
}
