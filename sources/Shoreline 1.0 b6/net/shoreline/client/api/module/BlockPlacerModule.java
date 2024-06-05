package net.shoreline.client.api.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.impl.module.combat.SurroundModule;
import net.shoreline.client.init.Managers;

/**
 * @author linus
 * @see SurroundModule
 * @since 1.0
 */
public class BlockPlacerModule extends RotationModule {

    Config<Boolean> strictDirectionConfig = new BooleanConfig("StrictDirection", "Places on visible sides only", false);
    Config<Boolean> grimConfig = new BooleanConfig("Grim", "Places using grim instant rotations", false);

    // TODO: series of blocks
    public BlockPlacerModule(String name, String desc, ModuleCategory category) {
        super(name, desc, category);
        register(strictDirectionConfig, grimConfig);
    }

    public BlockPlacerModule(String name, String desc, ModuleCategory category, int rotationPriority) {
        super(name, desc, category, rotationPriority);
        register(strictDirectionConfig, grimConfig);
    }

    /**
     * @param pos
     */
    protected void placeObsidianBlock(BlockPos pos, boolean rotate) {
        int slot = getResistantBlockItem();
        if (slot == -1) {
            return;
        }
        placeBlock(slot, pos, rotate, strictDirectionConfig.getValue(), grimConfig.getValue());
    }

    protected void placeObsidianBlock(BlockPos pos) {
        placeObsidianBlock(pos, false);
    }

    protected void placeBlock(int slot, BlockPos pos) {
        placeBlock(slot, pos, false, strictDirectionConfig.getValue(), grimConfig.getValue());
    }

    /**
     * @param slot
     * @param pos
     * @param strictDirection
     */
    protected void placeBlock(int slot, BlockPos pos, boolean rotate, boolean strictDirection, boolean grim) {
        int prev = mc.player.getInventory().selectedSlot;
        Managers.INVENTORY.setSlot(slot);
        float[] rotations = Managers.INTERACT.placeBlock(pos, rotate, strictDirection, grim);
        Managers.INVENTORY.setSlot(prev);
    }

    /**
     * @return
     */
    public int getResistantBlockItem() {
        int slot = getBlockItemSlot(Blocks.OBSIDIAN);
        if (slot == -1) {
            slot = getBlockItemSlot(Blocks.CRYING_OBSIDIAN);
        }
        if (slot == -1) {
            return getBlockItemSlot(Blocks.ENDER_CHEST);
        }
        return slot;
    }

    public int getBlockItemSlot(Block block) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem block1
                    && block1.getBlock() == block) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
