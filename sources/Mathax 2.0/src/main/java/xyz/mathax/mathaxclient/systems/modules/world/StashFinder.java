package xyz.mathax.mathaxclient.systems.modules.world;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.ChunkDataEvent;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.containers.WVerticalList;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.MatHaxToast;
import net.minecraft.block.entity.*;
import net.minecraft.item.Items;
import net.minecraft.util.math.ChunkPos;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class StashFinder extends Module {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public List<Chunk> chunks = new ArrayList<>();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup notificationSettings = settings.createGroup("Notifications");

    // General

    private final Setting<List<BlockEntityType<?>>> storageBlocksSetting = generalSettings.add(new StorageBlockListSetting.Builder()
            .name("Storage blocks")
            .description("Storage blocks to search for.")
            .defaultValue(StorageBlockListSetting.STORAGE_BLOCKS)
            .build()
    );

    private final Setting<Integer> minimumStorageCountSetting = generalSettings.add(new IntSetting.Builder()
            .name("Minimum storage cont")
            .description("The minimum amount of storage blocks in a chunk to record the chunk.")
            .defaultValue(4)
            .min(1)
            .sliderRange(1, 10)
            .build()
    );

    private final Setting<Integer> minimumDistanceSetting = generalSettings.add(new IntSetting.Builder()
            .name("Minimum distance")
            .description("The minimum distance you must be from spawn to record a certain chunk.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 10000)
            .build()
    );

    // Notifications

    private final Setting<Boolean> sendNotificationsSetting = notificationSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Send in-game notifications when new stashes are found.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Mode> notificationModeSetting = notificationSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("The mode to use for notifications.")
            .defaultValue(Mode.Both)
            .visible(sendNotificationsSetting::get)
            .build()
    );

    public StashFinder(Category category) {
        super(category, "Stash Finder", "Searches loaded chunks for storage blocks. Saves to <your minecraft folder>/meteor-client");
    }

    @Override
    public void onEnable() {
        load();
    }

    @EventHandler
    private void onChunkData(ChunkDataEvent event) {
        double chunkXAbs = Math.abs(event.chunk.getPos().x * 16);
        double chunkZAbs = Math.abs(event.chunk.getPos().z * 16);
        if (Math.sqrt(chunkXAbs * chunkXAbs + chunkZAbs * chunkZAbs) < minimumDistanceSetting.get()) {
            return;
        }

        Chunk chunk = new Chunk(event.chunk.getPos());
        for (BlockEntity blockEntity : event.chunk.getBlockEntities().values()) {
            if (!storageBlocksSetting.get().contains(blockEntity.getType())) {
                continue;
            }

            if (blockEntity instanceof ChestBlockEntity) {
                chunk.chests++;
            } else if (blockEntity instanceof BarrelBlockEntity) {
                chunk.barrels++;
            } else if (blockEntity instanceof ShulkerBoxBlockEntity) {
                chunk.shulkers++;
            } else if (blockEntity instanceof EnderChestBlockEntity) {
                chunk.enderChests++;
            } else if (blockEntity instanceof AbstractFurnaceBlockEntity) {
                chunk.furnaces++;
            } else if (blockEntity instanceof DispenserBlockEntity) {
                chunk.dispensersDroppers++;
            } else if (blockEntity instanceof HopperBlockEntity) {
                chunk.hoppers++;
            }
        }

        if (chunk.getTotal() >= minimumStorageCountSetting.get()) {
            Chunk prevChunk = null;
            int i = chunks.indexOf(chunk);
            if (i < 0) {
                chunks.add(chunk);
            } else {
                prevChunk = chunks.set(i, chunk);
            }

            saveJson();
            saveCsv();

            if (sendNotificationsSetting.get() && (!chunk.equals(prevChunk) || !chunk.countsEqual(prevChunk))) {
                switch (notificationModeSetting.get()) {
                    case Chat -> info("Found stash at (highlight)%s(default), (highlight)%s(default).", chunk.x, chunk.z);
                    case Toast -> mc.getToastManager().add(new MatHaxToast(Items.CHEST, name, "Found Stash!"));
                    case Both -> {
                        info("Found stash at (highlight)%s(default), (highlight)%s(default).", chunk.x, chunk.z);
                        mc.getToastManager().add(new MatHaxToast(Items.CHEST, name, "Found Stash!"));
                    }
                }
            }
        }

        ChunkDataEvent.returnChunkDataEvent(event);
    }

    @Override
    public WWidget getWidget(Theme theme) {
        // Sort
        chunks.sort(Comparator.comparingInt(value -> -value.getTotal()));

        WVerticalList list = theme.verticalList();

        // Clear
        WButton clear = list.add(theme.button("Clear")).widget();

        WTable table = new WTable();
        if (chunks.size() > 0) {
            list.add(table);
        }

        clear.action = () -> {
            chunks.clear();
            table.clear();
        };

        // Chunks
        fillTable(theme, table);

        return list;
    }

    private void fillTable(Theme theme, WTable table) {
        for (Chunk chunk : chunks) {
            table.add(theme.label("Pos: " + chunk.x + ", " + chunk.z));
            table.add(theme.label("Total: " + chunk.getTotal()));

            WButton open = table.add(theme.button("Open")).widget();
            open.action = () -> mc.setScreen(new ChunkScreen(theme, chunk));

            WButton gotoBtn = table.add(theme.button("Goto")).widget();
            gotoBtn.action = () -> BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalXZ(chunk.x, chunk.z));

            WMinus delete = table.add(theme.minus()).widget();
            delete.action = () -> {
                if (chunks.remove(chunk)) {
                    table.clear();
                    fillTable(theme, table);

                    saveJson();
                    saveCsv();
                }
            };

            table.row();
        }
    }

    private void load() {
        boolean loaded = false;

        File file = getJsonFile();
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                chunks = GSON.fromJson(reader, new TypeToken<List<Chunk>>() {}.getType());
                reader.close();

                for (Chunk chunk : chunks) chunk.calculatePos();

                loaded = true;
            } catch (Exception ignored) {
                if (chunks == null) {
                    chunks = new ArrayList<>();
                }
            }
        }

        file = getCsvFile();
        if (!loaded && file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    Chunk chunk = new Chunk(new ChunkPos(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
                    chunk.chests = Integer.parseInt(values[2]);
                    chunk.shulkers = Integer.parseInt(values[3]);
                    chunk.enderChests = Integer.parseInt(values[4]);
                    chunk.furnaces = Integer.parseInt(values[5]);
                    chunk.dispensersDroppers = Integer.parseInt(values[6]);
                    chunk.hoppers = Integer.parseInt(values[7]);

                    chunks.add(chunk);
                }

                reader.close();
            } catch (Exception ignored) {
                if (chunks == null) {
                    chunks = new ArrayList<>();
                }
            }
        }
    }

    private void saveCsv() {
        try {
            File file = getCsvFile();
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);

            writer.write("X,Z,Chests,Barrels,Shulkers,EnderChests,Furnaces,DispensersDroppers,Hoppers\n");
            for (Chunk chunk : chunks) {
                chunk.write(writer);
            }

            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void saveJson() {
        try {
            File file = getJsonFile();
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            GSON.toJson(chunks, writer);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private File getJsonFile() {
        return new File(new File(new File(MatHax.FOLDER, "Stashes"), Utils.getFileWorldName()), "Stashes.json");
    }

    private File getCsvFile() {
        return new File(new File(new File(MatHax.FOLDER, "Stashes"), Utils.getFileWorldName()), "Stashes.csv");
    }

    public static class Chunk {
        private static final StringBuilder sb = new StringBuilder();

        public ChunkPos chunkPos;

        public transient int x, z;
        public int chests, barrels, shulkers, enderChests, furnaces, dispensersDroppers, hoppers;

        public Chunk(ChunkPos chunkPos) {
            this.chunkPos = chunkPos;

            calculatePos();
        }

        public void calculatePos() {
            x = chunkPos.x * 16 + 8;
            z = chunkPos.z * 16 + 8;
        }

        public int getTotal() {
            return chests + barrels + shulkers + enderChests + furnaces + dispensersDroppers + hoppers;
        }

        public void write(Writer writer) throws IOException {
            sb.setLength(0);
            sb.append(x).append(',').append(z).append(',');
            sb.append(chests).append(',').append(barrels).append(',').append(shulkers).append(',').append(enderChests).append(',').append(furnaces).append(',').append(dispensersDroppers).append(',').append(hoppers).append('\n');
            writer.write(sb.toString());
        }

        public boolean countsEqual(Chunk chunk) {
            if (chunk == null) {
                return false;
            }

            return chests != chunk.chests || barrels != chunk.barrels || shulkers != chunk.shulkers || enderChests != chunk.enderChests || furnaces != chunk.furnaces || dispensersDroppers != chunk.dispensersDroppers || hoppers != chunk.hoppers;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }

            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            Chunk chunk = (Chunk) object;
            return Objects.equals(chunkPos, chunk.chunkPos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(chunkPos);
        }
    }

    private static class ChunkScreen extends WindowScreen {
        private final Chunk chunk;

        public ChunkScreen(Theme theme, Chunk chunk) {
            super(theme, "Chunk at " + chunk.x + ", " + chunk.z);

            this.chunk = chunk;
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().widget();

            // Total
            table.add(theme.label("Total:"));
            table.add(theme.label(chunk.getTotal() + ""));
            table.row();

            table.add(theme.horizontalSeparator()).expandX();
            table.row();

            // Separate
            table.add(theme.label("Chests:"));
            table.add(theme.label(chunk.chests + ""));
            table.row();

            table.add(theme.label("Barrels:"));
            table.add(theme.label(chunk.barrels + ""));
            table.row();

            table.add(theme.label("Shulkers:"));
            table.add(theme.label(chunk.shulkers + ""));
            table.row();

            table.add(theme.label("Ender Chests:"));
            table.add(theme.label(chunk.enderChests + ""));
            table.row();

            table.add(theme.label("Furnaces:"));
            table.add(theme.label(chunk.furnaces + ""));
            table.row();

            table.add(theme.label("Dispensers and droppers:"));
            table.add(theme.label(chunk.dispensersDroppers + ""));
            table.row();

            table.add(theme.label("Hoppers:"));
            table.add(theme.label(chunk.hoppers + ""));
        }
    }

    public enum Mode {
        Chat("Chat"),
        Toast("Toast"),
        Both("Both");

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