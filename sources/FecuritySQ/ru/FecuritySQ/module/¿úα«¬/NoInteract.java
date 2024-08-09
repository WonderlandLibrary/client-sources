package ru.FecuritySQ.module.игрок;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventInteractEntity;
import ru.FecuritySQ.event.imp.EventRightClickBlock;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;

import java.util.ArrayList;

public class NoInteract extends Module {

    public static OptionBoolList blocks = new OptionBoolList("Игнорировать",
            new OptionBoolean("Сундук", true),
            new OptionBoolean("Дверь", true),
            new OptionBoolean("Кнопка", false),
            new OptionBoolean("Воронка", true),
            new OptionBoolean("Нотный блок", true),
            new OptionBoolean("Верстак", true),
            new OptionBoolean("Печка", true),
            new OptionBoolean("Накавальня", false));
    public OptionBoolean entites = new OptionBoolean("Сущности", true);

    public NoInteract() {
        super(Category.Игрок, GLFW.GLFW_KEY_0);
        addOption(entites);
        addOption(blocks);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof EventRightClickBlock eventRightClickBlock){
            BlockState state = mc.world.getBlockState(eventRightClickBlock.pos);
            if(getBlocks().contains(state.getBlock())) e.setCancel(true);
        }
        if(e instanceof EventInteractEntity eventInteractEntity && entites.get()){
            if(eventInteractEntity.entity instanceof PlayerEntity || eventInteractEntity.entity instanceof ArmorStandEntity){
                eventInteractEntity.cancel = true;
            }
        }
    }

    private ArrayList<Block> getBlocks() {
        ArrayList<Block> blockList = new ArrayList<>();

        if (blocks.setting("Сундук").get()) {
            blockList.add(Blocks.CHEST);
            blockList.add(Blocks.ENDER_CHEST);
            blockList.add(Blocks.TRAPPED_CHEST);
        }
        if (blocks.setting("Дверь").get()) {
            blockList.add(Blocks.IRON_DOOR);
            blockList.add(Blocks.OAK_DOOR);
            blockList.add(Blocks.DARK_OAK_DOOR);
            blockList.add(Blocks.JUNGLE_DOOR);
            blockList.add(Blocks.ACACIA_DOOR);
            blockList.add(Blocks.BIRCH_DOOR);
        }
        if (blocks.setting("Кнопка").get()) {
            blockList.add(Blocks.STONE_BUTTON);
            blockList.add(Blocks.BIRCH_BUTTON);
            blockList.add(Blocks.ACACIA_BUTTON);
            blockList.add(Blocks.DARK_OAK_BUTTON);
            blockList.add(Blocks.JUNGLE_BUTTON);
        }
        if (blocks.setting("Воронка").get()) {
            blockList.add(Blocks.HOPPER);
        }
        if (blocks.setting("Нотный блок").get()) {
            blockList.add(Blocks.NOTE_BLOCK);
        }
        if (blocks.setting("Верстак").get()) {
            blockList.add(Blocks.CRAFTING_TABLE);
        }
        if (blocks.setting("Печка").get()) {
            blockList.add(Blocks.FURNACE);
            blockList.add(Blocks.BLAST_FURNACE);
        }
        if (blocks.setting("Накавальня").get()) {
            blockList.add(Blocks.ANVIL);
            blockList.add(Blocks.CHIPPED_ANVIL);
            blockList.add(Blocks.DAMAGED_ANVIL);
        }
        return blockList;
    }
}
