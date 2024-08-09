package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import xyz.mathax.mathaxclient.utils.world.MobSpawn;
import xyz.mathax.mathaxclient.utils.world.MobSpawnUtils;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SpawnProofer extends Module {
    private final Pool<BlockPos.Mutable> spawnPool = new Pool<>(BlockPos.Mutable::new);
    private final List<BlockPos.Mutable> spawns = new ArrayList<>();

    private int ticksWaited;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Which spawn type should be spawn proofed.")
            .defaultValue(Mode.Both)
            .build()
    );

    private final Setting<Integer> rangeSetting = generalSettings.add(new IntSetting.Builder()
            .name("Range")
            .description("Range for block placement and rendering.")
            .defaultValue(3)
            .min(0)
            .sliderRange(0, 4)
            .build()
    );

    private final Setting<List<Block>> blocksSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Blocks")
            .description("Block to use for spawn proofing.")
            .defaultValue(
                    Blocks.TORCH,
                    Blocks.STONE_BUTTON,
                    Blocks.STONE_SLAB
            )
            .filter(this::filterBlocks)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("Delay in ticks between placing blocks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Rotates towards the blocks being placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> newMobSpawnLightLevelSetting = generalSettings.add(new BoolSetting.Builder()
            .name("New mob spawn light level")
            .description("Use the new (1.18+) mob spawn behavior.")
            .defaultValue(true)
            .build()
    );

    public SpawnProofer(Category category) {
        super(category, "Spawn Proofer", "Automatically spawnproofs unlit areas.");
    }

    @EventHandler
    private void onTickPre(TickEvent.Pre event) {
        if (delaySetting.get() != 0 && ticksWaited < delaySetting.get() - 1) {
            return;
        }

        FindItemResult block = InvUtils.findInHotbar(itemStack -> blocksSetting.get().contains(Block.getBlockFromItem(itemStack.getItem())));
        if (!block.found()) {
            error("Found none of the chosen blocks in hotbar, disabling...");
            forceToggle(false);
            return;
        }

        for (BlockPos.Mutable blockPos : spawns) {
            spawnPool.free(blockPos);
        }

        spawns.clear();

        BlockIterator.register(rangeSetting.get(), rangeSetting.get(), (blockPos, blockState) -> {
            MobSpawn spawn = MobSpawnUtils.isValidMobSpawn(blockPos, newMobSpawnLightLevelSetting.get());
            if ((spawn == MobSpawn.Always && (modeSetting.get() == Mode.Always || modeSetting.get() == Mode.Both)) || spawn == MobSpawn.Potential && (modeSetting.get() == Mode.Potential || modeSetting.get() == Mode.Both)) {
                spawns.add(spawnPool.get().set(blockPos));
            }
        });
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (delaySetting.get() != 0 && ticksWaited < delaySetting.get() - 1) {
            ticksWaited++;
            return;
        }

        if (spawns.isEmpty()) {
            return;
        }

        FindItemResult block = InvUtils.findInHotbar(itemStack -> blocksSetting.get().contains(Block.getBlockFromItem(itemStack.getItem())));
        if (!block.found()) {
            error("Found none of the chosen blocks in hotbar, disabling...");
            forceToggle(false);
            return;
        }

        if (delaySetting.get() == 0) {
            for (BlockPos blockPos : spawns) {
                BlockUtils.place(blockPos, block, rotateSetting.get(), -50, false);
            }
        } else {
            if (isLightSource(Block.getBlockFromItem(mc.player.getInventory().getStack(block.slot()).getItem()))) {
                int lowestLightLevel = 16;
                BlockPos.Mutable selectedBlockPos = spawns.get(0);
                for (BlockPos blockPos : spawns) {
                    int lightLevel = mc.world.getLightLevel(blockPos);
                    if (lightLevel < lowestLightLevel) {
                        lowestLightLevel = lightLevel;
                        selectedBlockPos.set(blockPos);
                    }
                }

                BlockUtils.place(selectedBlockPos, block, rotateSetting.get(), -50, false);
            } else {
                BlockUtils.place(spawns.get(0), block, rotateSetting.get(), -50, false);
            }
        }

        ticksWaited = 0;
    }

    private boolean filterBlocks(Block block) {
        return isNonOpaqueBlock(block) || isLightSource(block);
    }

    private boolean isNonOpaqueBlock(Block block) {
        return block instanceof ButtonBlock || block instanceof SlabBlock || block instanceof AbstractPressurePlateBlock || block instanceof TransparentBlock || block instanceof TripwireBlock || block instanceof CarpetBlock;
    }

    private boolean isLightSource(Block block) {
        return block.getDefaultState().getLuminance() > 0;
    }

    public enum Mode {
        Always("Always"),
        Potential("Potential"),
        Both("Both"),
        None("None");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}