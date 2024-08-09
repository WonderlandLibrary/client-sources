package dev.darkmoon.client.module.impl.player;

import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleAnnotation(name = "NoInteract", category = Category.PLAYER)
public class NoInteract extends Module {
    public static BooleanSetting allBlocks = new BooleanSetting("All-Blocks", false);

    public static MultiBooleanSetting selected = new MultiBooleanSetting("Blocks",
            Arrays.asList("Door", "Button", "Chest", "Hopper", "Dispenser", "Note Blocks",
                    "Crafting Table", "Trap Door", "Furnace", "Gate", "Anvil", "Lever"), () -> !allBlocks.get());

    public static List<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        if (selected.get(0)) {
            blocks.add(Blocks.ACACIA_DOOR);
            blocks.add(Blocks.DARK_OAK_DOOR);
            blocks.add(Blocks.BIRCH_DOOR);
            blocks.add(Blocks.IRON_DOOR);
            blocks.add(Blocks.JUNGLE_DOOR);
            blocks.add(Blocks.OAK_DOOR);
            blocks.add(Blocks.SPRUCE_DOOR);
        }
        if (selected.get(1)) {
            blocks.add(Blocks.WOODEN_BUTTON);
            blocks.add(Blocks.STONE_BUTTON);
        }
        if (selected.get(2)) {
            blocks.add(Blocks.CHEST);
            blocks.add(Blocks.TRAPPED_CHEST);
            blocks.add(Blocks.ENDER_CHEST);
        }
        if (selected.get(3)) {
            blocks.add(Blocks.HOPPER);
        }
        if (selected.get(4)) {
            blocks.add(Blocks.DISPENSER);
        }
        if (selected.get(5)) {
            blocks.add(Blocks.NOTEBLOCK);
        }
        if (selected.get(6)) {
            blocks.add(Blocks.CRAFTING_TABLE);
        }
        if (selected.get(7)) {
            blocks.add(Blocks.TRAPDOOR);
            blocks.add(Blocks.IRON_TRAPDOOR);
        }
        if (selected.get(8)) {
            blocks.add(Blocks.FURNACE);
        }
        if (selected.get(9)) {
            blocks.add(Blocks.ACACIA_FENCE_GATE);
            blocks.add(Blocks.DARK_OAK_FENCE_GATE);
            blocks.add(Blocks.BIRCH_FENCE_GATE);
            blocks.add(Blocks.JUNGLE_FENCE_GATE);
            blocks.add(Blocks.OAK_FENCE_GATE);
            blocks.add(Blocks.SPRUCE_FENCE_GATE);
        }
        if (selected.get(10)) {
            blocks.add(Blocks.ANVIL);
        }
        if (selected.get(10)) {
            blocks.add(Blocks.LEVER);
        }
        return blocks;
    }
}