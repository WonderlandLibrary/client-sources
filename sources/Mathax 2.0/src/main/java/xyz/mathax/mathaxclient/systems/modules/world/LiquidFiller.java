package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;

import java.util.List;

public class LiquidFiller extends Module {
    private int timer;

    private final SettingGroup generalSettings  = settings.createGroup("General");

    // General

    private final Setting<List<Block>> whitelistSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Block whitelist")
            .description("The allowed blocks that it will use to fill up the liquid.")
            .defaultValue(
                    Blocks.DIRT,
                    Blocks.COBBLESTONE,
                    Blocks.STONE,
                    Blocks.NETHERRACK,
                    Blocks.DIORITE,
                    Blocks.GRANITE,
                    Blocks.ANDESITE
            )
            .build()
    );

    private final Setting<PlaceIn> placeInLiquidsSetting = generalSettings.add(new EnumSetting.Builder<PlaceIn>()
            .name("Place in")
            .description("What type of liquids to place in.")
            .defaultValue(PlaceIn.Lava)
            .build()
    );

    private final Setting<Integer> horizontalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Horizontal radius")
            .description("Horizontal radius in which to search for liquids.")
            .defaultValue(4)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Integer> verticalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("vertical-radius")
            .description("Vertical radius in which to search for liquids.")
            .defaultValue(4)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("delay")
            .description("Delay between actions in ticks.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically rotate towards the space targeted for filling.")
            .defaultValue(true)
            .build()
    );

    public LiquidFiller(Category category){
        super(category, "Liquid Filler", "Places blocks inside of liquid source blocks within range of you.");
    }

    @Override
    public void onEnable() {
        timer = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (timer < delaySetting.get()) {
            timer++;
            return;
        } else {
            timer = 0;
        }

        FindItemResult item = InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof BlockItem && whitelistSetting.get().contains(Block.getBlockFromItem(itemStack.getItem())));
        if (!item.found()) {
            return;
        }

        BlockIterator.register(horizontalRadiusSetting.get(), verticalRadiusSetting.get(), (blockPos, blockState) -> {
            if (isSource(blockState)) {
                Block liquid = blockState.getBlock();
                PlaceIn placeIn = placeInLiquidsSetting.get();
                if (placeIn == PlaceIn.Both || (placeIn == PlaceIn.Lava && liquid == Blocks.LAVA) || (placeIn == PlaceIn.Water && liquid == Blocks.WATER)) {
                    if (BlockUtils.place(blockPos, item, rotateSetting.get(), 0, true)) {
                        BlockIterator.disableCurrent();
                    }
                }
            }
        });
    }

    private boolean isSource(BlockState blockState) {
        return blockState.getFluidState().getLevel() == 8 && blockState.getFluidState().isStill();
    }

    public enum PlaceIn {
        Lava("Lava"),
        Water("Water"),
        Both("Both");

        private final String name;

        PlaceIn(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}