package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.render.Render3DEvent;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.widgets.containers.WHorizontalList;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*/--------------------------------------------------/*/
/*/ Ported from BleachHack and modified by Matejko06 /*/
/*/ https://bleachhack.org                           /*/
/*/--------------------------------------------------/*/

public class NewChunks extends Module {
    private static final Direction[] searchDirections = new Direction[] {
        Direction.EAST,
        Direction.NORTH,
        Direction.WEST,
        Direction.SOUTH,
        Direction.UP
    };

    private final Set<ChunkPos> newChunks = Collections.synchronizedSet(new HashSet<>());
    private final Set<ChunkPos> oldChunks = Collections.synchronizedSet(new HashSet<>());

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgNewChunks = settings.createGroup("New Chunks");
    private final SettingGroup sgOldChunks = settings.createGroup("Old Chunks");

    // General

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Determines how New Chunks operates.")
        .defaultValue(Mode.Both)
        .build()
    );

    private final Setting<Boolean> remove = sgGeneral.add(new BoolSetting.Builder()
        .name("remove")
        .description("Removes the cached chunks when disabling the module and leaving.")
        .defaultValue(true)
        .build()
    );

    // New Chunks

    private final Setting<Boolean> newChunksToggle = sgNewChunks.add(new BoolSetting.Builder()
        .name("new-chunks")
        .description("Determines if new chunks are visible.")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> newChunksFillColor = sgNewChunks.add(new ColorSetting.Builder()
        .name("fill-color")
        .description("Fill color of the chunks that are (most likely) completely new.")
        .defaultValue(new SettingColor(255, 0, 0, 0))
        .build()
    );

    private final Setting<SettingColor> newChunksLineColor = sgNewChunks.add(new ColorSetting.Builder()
        .name("line-color")
        .description("Line color of the chunks that are (most likely) completely new.")
        .defaultValue(new SettingColor(255, 0, 0))
        .build()
    );

    // Old Chunks

    private final Setting<Boolean> oldChunksToggle = sgOldChunks.add(new BoolSetting.Builder()
        .name("old-chunks")
        .description("Determines if old chunks are visible.")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> oldChunksFillColor = sgOldChunks.add(new ColorSetting.Builder()
        .name("fill-color")
        .description("Fill color of the chunks that are old.")
        .defaultValue(new SettingColor(0, 255, 0, 0))
        .build()
    );

    private final Setting<SettingColor> oldChunksLineColor = sgOldChunks.add(new ColorSetting.Builder()
        .name("line-color")
        .description("Line color of the chunks that are old.")
        .defaultValue(new SettingColor(0, 255, 0))
        .build()
    );

    // Buttons

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WHorizontalList w = theme.horizontalList();

        WButton remove = w.add(theme.button("Remove")).widget();
        remove.action = () -> {
            newChunks.clear();
            oldChunks.clear();
        };
        w.add(theme.label("Removes all cached chunks."));

        return w;
    }

    public NewChunks() {
        super(Categories.Render, Items.GRASS_BLOCK, "new-chunks", "Detects completely new and old chunks using certain traits of them.");
    }

    @Override
    public void onDeactivate() {
        if (remove.get()) {
            newChunks.clear();
            oldChunks.clear();
        }
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive event) {
        if (mode.get() == Mode.Liquids || mode.get() == Mode.Both) {
            Direction[] searchDirs = new Direction[] {
                Direction.EAST,
                Direction.NORTH,
                Direction.WEST,
                Direction.SOUTH,
                Direction.UP
            };

            if (event.packet instanceof ChunkDeltaUpdateS2CPacket packet) {
                packet.visitUpdates((position, state) -> {
                    if (!state.getFluidState().isEmpty() && !state.getFluidState().isStill()) {
                        ChunkPos chunkPos = new ChunkPos(position);
                        Direction[] directions = searchDirs;
                        int length = searchDirs.length;

                        for (int value = 0; value < length; ++value) {
                            Direction dir = directions[value];
                            if (mc.world.getBlockState(position.offset(dir)).getFluidState().isStill() && !oldChunks.contains(chunkPos)) {
                                newChunks.add(chunkPos);
                                return;
                            }
                        }
                    }

                });
            } else {
                ChunkPos pos;
                int x;
                int y;
                if (event.packet instanceof BlockUpdateS2CPacket packet) {
                    if (!packet.getState().getFluidState().isEmpty() && !packet.getState().getFluidState().isStill()) {
                        pos = new ChunkPos(packet.getPos());
                        Direction[] directions = searchDirs;
                        x = searchDirs.length;

                        for (y = 0; y < x; y++) {
                            Direction dir = directions[y];
                            if (mc.world.getBlockState(packet.getPos().offset(dir)).getFluidState().isStill() && !oldChunks.contains(pos)) {
                                newChunks.add(pos);
                                return; //this barely works
                            }
                        }
                    }
                } else if (event.packet instanceof ChunkDataS2CPacket packet && mc.world != null) {
                    pos = new ChunkPos(packet.getX(), packet.getZ());
                    if (!newChunks.contains(pos) && mc.world.getChunkManager().getChunk(packet.getX(), packet.getZ()) == null) {
                        WorldChunk chunk = new WorldChunk(mc.world, pos);
                        chunk.loadFromPacket(packet.getChunkData().getSectionsDataBuf(), new NbtCompound(), packet.getChunkData().getBlockEntities(packet.getX(), packet.getZ()));

                        for (x = 0; x < 16; x++) {
                            for (y = 0; y < mc.world.getHeight(); y++) {
                                for (int z = 0; z < 16; z++) {
                                    FluidState fluid = chunk.getFluidState(x, y, z);
                                    if (!fluid.isEmpty() && !fluid.isStill()) {
                                        oldChunks.add(pos);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (newChunksToggle.get()) {
            synchronized (newChunks) {
                for (ChunkPos c : newChunks) if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.getStartPos(), 1024)) drawBoxOutline(new Box(c.getStartPos(), c.getStartPos().add(16, 0, 16)), newChunksFillColor.get(), newChunksLineColor.get(), event);
            }
        }

        if (oldChunksToggle.get()){
            synchronized (oldChunks) {
                for (ChunkPos c : oldChunks) if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.getStartPos(), 1024)) drawBoxOutline(new Box(c.getStartPos(), c.getStartPos().add(16, 0, 16)), oldChunksFillColor.get(), oldChunksLineColor.get(), event);
            }
        }
    }

    private void drawBoxOutline(Box box, Color fillColor, Color lineColor, Render3DEvent event) {
        event.renderer.box(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, fillColor, lineColor, ShapeMode.Both, 0);
    }

    public enum Mode {
        Mobs,
        Liquids,
        Both
    }
}
