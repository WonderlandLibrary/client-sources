package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ListValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.module.value.builders.AbstractBuilder.createList;

public class XRay extends Module {

    public static List<Block> blocks = new ArrayList<>();

    private final ListValue blockList = createList("Blocks")
            .range("Diamond", "Emerald", "Iron", "Gold", "Coal", "Redstone")
            .index(1)
            .build();

    public XRay() {
        super("XRay", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        blocks = new ArrayList<Block>(){{
            /*add(Blocks.diamond_ore);
            add(Blocks.emerald_ore);
            add(Blocks.iron_ore);
            add(Blocks.gold_ore);
            add(Blocks.coal_ore);
            add(Blocks.water);
            add(Blocks.flowing_water);
            add(Blocks.lava);
            add(Blocks.flowing_lava);
            add(Blocks.redstone_ore);
            add(Blocks.lit_redstone_ore);*/
        }};
        mc.renderGlobal.loadRenderers();
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        checkBlock(get("Diamond"), Blocks.diamond_ore)
                .checkBlock(get("Emerald"), Blocks.emerald_ore)
                .checkBlock(get("Iron"), Blocks.iron_ore)
                .checkBlock(get("Gold"), Blocks.gold_ore)
                .checkBlock(get("Coal"), Blocks.coal_ore)
                .checkBlock(get("Redstone"), Blocks.redstone_ore)
                .checkBlock(true, Blocks.water)
                .checkBlock(true, Blocks.flowing_water)
                .checkBlock(true, Blocks.lava)
                .checkBlock(true, Blocks.flowing_lava)
                .checkBlock(get("Redstone"), Blocks.redstone_ore)
                .checkBlock(true, Blocks.reeds);
    }

    @Override
    public void onDisable() {
        blocks.clear();
        mc.renderGlobal.loadRenderers();
    }

    private XRay checkBlock(boolean condition, Block block) {

        if(condition && !blocks.contains(block)) {
            blocks.add(block);
            mc.renderGlobal.loadRenderers();
        }
        if(!condition && blocks.contains(block)) {
            blocks.remove(block);
            mc.renderGlobal.loadRenderers();
        }
        return this;
    }

    private boolean get(String option) {
        return blockList.getOptionState(option);
    }
}
