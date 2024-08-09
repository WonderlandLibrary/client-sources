package xyz.mathax.mathaxclient.systems.modules.misc;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.gui.widgets.WLabel;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.notebot.NotebotUtils;
import xyz.mathax.mathaxclient.utils.notebot.decoder.SongDecoders;
import xyz.mathax.mathaxclient.utils.notebot.song.Note;
import xyz.mathax.mathaxclient.utils.notebot.song.Song;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import xyz.mathax.mathaxclient.settings.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Notebot extends Module {
    private CompletableFuture<Song> loadingSongFuture = null;

    private final Map<Note, BlockPos> noteBlockPositions = new HashMap<>(); // Currently used noteblocks by the song
    private final Map<BlockPos, Integer> tuneHits = new HashMap<>(); // noteblock -> target hits number

    private final Multimap<Note, BlockPos> scannedNoteblocks = MultimapBuilder.linkedHashKeys().arrayListValues().build(); // Found noteblocks

    private final List<BlockPos> clickedBlocks = new ArrayList<>();

    private Song song; // Loaded song

    private Stage stage = Stage.None;

    private boolean anyNoteblockTuned = false;
    private boolean isPlaying = false;

    private int waitTicks = -1;
    private int currentTick = 0;
    private int ticks = 0;

    private WLabel status;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup noteMapSettings = settings.createGroup("Note Map");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    public final Setting<NotebotUtils.NotebotMode> modeSetting = generalSettings.add(new EnumSetting.Builder<NotebotUtils.NotebotMode>()
            .name("Mode")
            .description("How notebot functions.")
            .defaultValue(NotebotUtils.NotebotMode.Exact_Instruments)
            .build()
    );

    public final Setting<Integer> tickDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The delay when loading a song in ticks.")
            .defaultValue(1)
            .sliderRange(1, 20)
            .min(1)
            .build()
    );

    public final Setting<Integer> concurrentTuneBlocksSetting = generalSettings.add(new IntSetting.Builder()
            .name("Concurrent tune blocks")
            .description("How many noteblocks can be tuned at the same time. On Paper it is recommended to set it to 1 to avoid bugs.")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 20)
            .build()
    );

    public final Setting<Boolean> polyphonicSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Polyphonic")
            .description("Whether or not to allow multiple notes to be played at the same time.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> autoRotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Auto rotate")
            .description("Should client look at note block when it wants to hit it.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> autoPlaySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Auto play")
            .description("Auto plays random songs.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> roundOutOfRangeSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Round out of range")
            .description("Round out of range notes.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Integer> checkNoteblocksAgainDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Check noteblocks again delay")
            .description("How much delay should be between end of tuning and checking again.")
            .defaultValue(10)
            .min(1)
            .sliderRange(1, 20)
            .build()
    );

    // Render

    public final Setting<Boolean> swingSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing")
            .description("Swing hand client side.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> renderTextSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Render text")
            .description("Whether or not to render the text above noteblocks.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> renderBoxesSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Render boxes")
            .description("Whether or not to render the outline around the noteblocks.")
            .defaultValue(true)
            .build()
    );

    public final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    public final Setting<SettingColor> untunedSideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Untuned side color")
            .description("The color of the sides of the untuned blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 75))
            .build()
    );

    public final Setting<SettingColor> untunedLineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Untuned line color")
            .description("The color of the lines of the untuned blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0))
            .build()
    );

    public final Setting<SettingColor> tunedSideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Tuned side color")
            .description("The color of the sides of the tuned blocks being rendered.")
            .defaultValue(new SettingColor(0, 205, 0, 75))
            .build()
    );

    public final Setting<SettingColor> tunedLineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Tuned line color")
            .description("The color of the lines of the tuned blocks being rendered.")
            .defaultValue(new SettingColor(0, 205, 0))
            .build()
    );

    public final Setting<SettingColor> tuneHitSideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Hit side color")
            .description("The color of the sides being rendered on noteblock tune hit.")
            .defaultValue(new SettingColor(255, 155, 0, 75))
            .build()
    );

    private final Setting<SettingColor> tuneHitLineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Hit line color")
            .description("The color of the lines being rendered on noteblock tune hit.")
            .defaultValue(new SettingColor(255, 155, 0))
            .build()
    );

    public final Setting<SettingColor> scannedNoteblockSideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Scanned noteblock side color")
            .description("The color of the sides of the scanned noteblocks being rendered.")
            .defaultValue(new SettingColor(255, 255, 0, 75))
            .build()
    );

    private final Setting<SettingColor> scannedNoteblockLineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Scanned noteblock line color")
            .description("The color of the lines of the scanned noteblocks being rendered.")
            .defaultValue(new SettingColor(255, 255, 0))
            .build()
    );

    public final Setting<Double> noteTextScaleSetting = renderSettings.add(new DoubleSetting.Builder()
            .name("Note text scale")
            .description("The note text scale.")
            .defaultValue(1.5)
            .min(0)
            .build()
    );

    public final Setting<Boolean> showScannedNoteblocksSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Show scanned noteblocks")
            .description("Show scanned noteblocks.")
            .defaultValue(false)
            .build()
    );

    public Notebot(Category category) {
        super(category, "Notebot", "Plays songs using noteblocks.");

        for (Instrument inst : Instrument.values()) {
            NotebotUtils.OptionalInstrument optionalInstrument = NotebotUtils.OptionalInstrument.fromMinecraftInstrument(inst);
            if (optionalInstrument != null) {
                noteMapSettings.add(new EnumSetting.Builder<NotebotUtils.OptionalInstrument>()
                        .name(beautifyText(inst.name()))
                        .defaultValue(optionalInstrument)
                        .visible(() -> modeSetting.get() == NotebotUtils.NotebotMode.Exact_Instruments)
                        .build()
                );
            }
        }
    }

    @Override
    public String getInfoString() {
        return stage.toString();
    }

    @Override
    public void onEnable() {
        ticks = 0;
        resetVariables();
    }

    private void resetVariables() {
        if (loadingSongFuture != null) {
            loadingSongFuture.cancel(true);
            loadingSongFuture = null;
        }

        clickedBlocks.clear();
        tuneHits.clear();
        anyNoteblockTuned = false;
        currentTick = 0;
        isPlaying = false;
        stage = Stage.None;
        song = null;
        noteBlockPositions.clear();
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (!renderBoxesSetting.get()) {
            return;
        }

        if (stage != Stage.Set_Up && stage != Stage.Tune && stage != Stage.Waiting_To_Check_Noteblocks && !isPlaying) {
            return;
        }

        if (showScannedNoteblocksSetting.get()) {
            for (BlockPos blockPos : scannedNoteblocks.values()) {
                double x1 = blockPos.getX();
                double y1 = blockPos.getY();
                double z1 = blockPos.getZ();
                double x2 = blockPos.getX() + 1;
                double y2 = blockPos.getY() + 1;
                double z2 = blockPos.getZ() + 1;

                event.renderer.box(x1, y1, z1, x2, y2, z2, scannedNoteblockSideColorSetting.get(), scannedNoteblockLineColorSetting.get(), shapeModeSetting.get(), 0);
            }
        } else {
            for (var entry : noteBlockPositions.entrySet()) {
                Note note = entry.getKey();
                BlockPos blockPos = entry.getValue();

                BlockState state = mc.world.getBlockState(blockPos);
                if (state.getBlock() != Blocks.NOTE_BLOCK) {
                    continue;
                }

                int level = state.get(NoteBlock.NOTE);

                double x1 = blockPos.getX();
                double y1 = blockPos.getY();
                double z1 = blockPos.getZ();
                double x2 = blockPos.getX() + 1;
                double y2 = blockPos.getY() + 1;
                double z2 = blockPos.getZ() + 1;

                // Render boxes around noteblocks in use

                Color sideColor;
                Color lineColor;
                if (clickedBlocks.contains(blockPos)) {
                    sideColor = tuneHitSideColorSetting.get();
                    lineColor = tuneHitLineColorSetting.get();
                } else {
                    if (note.getNoteLevel() == level) {
                        sideColor = tunedSideColorSetting.get();
                        lineColor = tunedLineColorSetting.get();
                    } else {
                        sideColor = untunedSideColorSetting.get();
                        lineColor = untunedLineColorSetting.get();
                    }
                }

                event.renderer.box(x1, y1, z1, x2, y2, z2, sideColor, lineColor, shapeModeSetting.get(), 0);
            }
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (!renderTextSetting.get()) {
            return;
        }

        if (stage != Stage.Set_Up && stage != Stage.Tune && stage != Stage.Waiting_To_Check_Noteblocks && !isPlaying) {
            return;
        }

        Vector3d pos = new Vector3d();
        for (BlockPos blockPos : noteBlockPositions.values()) {
            BlockState state = mc.world.getBlockState(blockPos);
            if (state.getBlock() != Blocks.NOTE_BLOCK) {
                continue;
            }

            double x = blockPos.getX() + 0.5;
            double y = blockPos.getY() + 1;
            double z = blockPos.getZ() + 0.5;

            pos.set(x, y, z);

            // Render level text logic

            String levelText = String.valueOf(state.get(NoteBlock.NOTE));
            String tuneHitsText = null;
            if (tuneHits.containsKey(blockPos)) {
                tuneHitsText = " -" + tuneHits.get(blockPos);
            }

            if (!NametagUtils.to2D(pos, noteTextScaleSetting.get(), true)) {
                continue;
            }

            TextRenderer text = TextRenderer.get();

            NametagUtils.begin(pos);
            text.beginBig();

            double xScreen = text.getWidth(levelText) / 2.0;
            if (tuneHitsText != null) {
                xScreen += text.getWidth(tuneHitsText) / 2.0;
            }

            double hX = text.render(levelText, -xScreen, 0, Color.GREEN);
            if (tuneHitsText != null) {
                text.render(tuneHitsText, hX, 0, Color.RED);
            }

            text.end();

            NametagUtils.end();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        ticks++;

        clickedBlocks.clear();

        if (stage == Stage.Waiting_To_Check_Noteblocks) {
            waitTicks--;
            if (waitTicks == 0) {
                waitTicks = -1;
                info("Checking noteblocks again...");

                setupTuneHitsMap();
                stage = Stage.Tune;
            }
        } else if (stage == Stage.Set_Up) {
            scanForNoteblocks();
            if (scannedNoteblocks.isEmpty()) {
                error("Can't find any nearby noteblock!");
                stop();
                return;
            }

            setupNoteblocksMap();

            if (noteBlockPositions.isEmpty()) {
                error("Can't find any valid noteblock to play song.");
                stop();
                return;
            }

            setupTuneHitsMap();
            stage = Stage.Tune;
        } else if (stage == Stage.Tune) {
            tune();
        } else if (stage == Stage.Preview || stage == Stage.Playing) {
            if (!isPlaying) {
                return;
            }

            if (mc.player == null || currentTick > song.getLastTick()) {
                onSongEnd();
                return;
            }

            if (song.getNotesMap().containsKey(currentTick)) {
                if (stage == Stage.Preview) {
                    onTickPreview();
                } else if (mc.player.getAbilities().creativeMode) {
                    error("You need to be in survival mode.");
                    stop();
                    return;
                } else {
                    onTickPlay();
                }
            }

            currentTick++;

            updateStatus();
        }
    }

    private void setupNoteblocksMap() {
        noteBlockPositions.clear();

        List<Note> uniqueNotesToUse = new ArrayList<>(song.getRequirements());
        Map<Instrument, List<BlockPos>> incorrectNoteBlocks = new HashMap<>();
        for (var entry : scannedNoteblocks.asMap().entrySet()) {
            Note note = entry.getKey();
            List<BlockPos> noteblocks = new ArrayList<>(entry.getValue());
            if (uniqueNotesToUse.contains(note)) {
                noteBlockPositions.put(note, noteblocks.remove(0));
                uniqueNotesToUse.remove(note);
            }

            if (!noteblocks.isEmpty()) {
                if (!incorrectNoteBlocks.containsKey(note.getInstrument())) {
                    incorrectNoteBlocks.put(note.getInstrument(), new ArrayList<>());
                }

                incorrectNoteBlocks.get(note.getInstrument()).addAll(noteblocks);
            }
        }

        // Map note -> block pos
        for (var entry : incorrectNoteBlocks.entrySet()) {
            List<BlockPos> positions = entry.getValue();
            if (modeSetting.get() == NotebotUtils.NotebotMode.Exact_Instruments) {
                Instrument instrument = entry.getKey();
                List<Note> foundNotes = uniqueNotesToUse.stream().filter(note -> note.getInstrument() == instrument).collect(Collectors.toList());
                if (foundNotes.isEmpty()) {
                    continue;
                }

                for (BlockPos pos : positions) {
                    if (foundNotes.isEmpty()) {
                        break;
                    }

                    Note note = foundNotes.remove(0);
                    noteBlockPositions.put(note, pos);

                    uniqueNotesToUse.remove(note);
                }
            } else {
                for (BlockPos pos : positions) {
                    if (uniqueNotesToUse.isEmpty()) {
                        break;
                    }

                    Note note = uniqueNotesToUse.remove(0);
                    noteBlockPositions.put(note, pos);
                }
            }
        }

        if (!uniqueNotesToUse.isEmpty()) {
            for (Note note : uniqueNotesToUse) {
                warning("Missing note: " + note.getInstrument() + ", " + note.getNoteLevel());
            }

            warning(uniqueNotesToUse.size() + " missing notes!");
        }
    }

    private void setupTuneHitsMap() {
        tuneHits.clear();

        for (var entry : noteBlockPositions.entrySet()) {
            int targetLevel = entry.getKey().getNoteLevel();
            BlockPos blockPos = entry.getValue();

            BlockState blockState = mc.world.getBlockState(blockPos);
            int currentLevel = blockState.get(NoteBlock.NOTE);

            if (targetLevel != currentLevel) {
                tuneHits.put(blockPos, calcNumberOfHits(currentLevel, targetLevel));
            }
        }
    }

    @Override
    public WWidget getWidget(Theme theme) {
        WTable table = theme.table();

        // Open Song GUI
        WButton openSongGUI = table.add(theme.button("Open Song GUI")).expandX().minWidth(100).widget();
        openSongGUI.action = () -> mc.setScreen(theme.notebotSongs());

        table.row();

        // Align Center
        WButton alignCenter = table.add(theme.button("Align Center")).expandX().minWidth(100).widget();
        alignCenter.action = () -> {
            if (mc.player == null) {
                return;
            }

            mc.player.setPosition(Vec3d.ofBottomCenter(mc.player.getBlockPos()));
        };

        table.row();

        // Label
        status = table.add(theme.label(getStatus())).expandCellX().widget();

        // Pause
        WButton pause = table.add(theme.button(isPlaying ? "Pause" : "Resume")).right().widget();
        pause.action = () -> {
            pause();
            pause.set(isPlaying ? "Pause" : "Resume");
            updateStatus();
        };

        // Stop
        WButton stop = table.add(theme.button("Stop")).right().widget();
        stop.action = this::stop;

        return table;
    }


    public String getStatus() {
        if (!this.isEnabled()) {
            return "Module disabled.";
        }

        if (song == null) {
            return "No song loaded.";
        }

        if (isPlaying) {
            return String.format("Playing song. %d/%d", currentTick, song.getLastTick());
        }

        if (stage == Stage.Playing || stage == Stage.Preview) {
            return "Ready to play.";
        }

        if (stage == Stage.Set_Up || stage == Stage.Tune || stage == Stage.Waiting_To_Check_Noteblocks) {
            return "Setting up the noteblocks.";
        } else {
            return String.format("Stage: %s.", stage.toString());
        }
    }

    public void play() {
        if (mc.player == null) {
            return;
        }

        if (mc.player.getAbilities().creativeMode && stage != Stage.Preview) {
            error("You need to be in survival mode.");
        } else if (stage == Stage.Preview || stage == Stage.Playing) {
            isPlaying = true;
            info("Playing.");
        } else {
            error("No song loaded.");
        }
    }

    public void pause() {
        if (!isEnabled()) {
            forceToggle(true);
        }

        if (isPlaying) {
            info("Pausing.");
            isPlaying = false;
        } else {
            info("Resuming.");
            isPlaying = true;
        }
    }

    public void stop() {
        info("Stopping.");
        if (stage == Stage.Set_Up || stage == Stage.Tune || stage == Stage.Waiting_To_Check_Noteblocks || stage == Stage.Loading_Song) {
            resetVariables();
        } else {
            isPlaying = false;
            currentTick = 0;
        }

        updateStatus();

        disable();
    }

    public void onSongEnd() {
        if (autoPlaySetting.get() && stage != Stage.Preview) {
            playRandomSong();
        } else {
            stop();
        }
    }

    public void playRandomSong() {
        File[] files = MatHax.FOLDER.toPath().resolve("Notebot").toFile().listFiles();
        if (files == null) {
            return;
        }

        File randomSong = files[ThreadLocalRandom.current().nextInt(files.length)];
        if (SongDecoders.hasDecoder(randomSong)) {
            loadSong(randomSong);
        } else {
            playRandomSong();
        }
    }

    public void disable() {
        resetVariables();

        if (!isEnabled()) {
            toggle();
        }
    }

    public void loadSong(File file) {
        if (!isEnabled()) {
            forceToggle(true);
        }

        if (!loadFileToMap(file, () -> stage = Stage.Set_Up)) {
            if (autoPlaySetting.get()) {
                playRandomSong();
            }
        }

        updateStatus();
    }

    public void previewSong(File file) {
        if (!isEnabled()) {
            forceToggle(true);
        }

        loadFileToMap(file, () -> {
            stage = Stage.Preview;
            play();
        });

        updateStatus();
    }

    public boolean loadFileToMap(File file, Runnable callback) {
        if (!file.exists() || !file.isFile()) {
            error("File not found");
            return false;
        }

        if (!SongDecoders.hasDecoder(file)) {
            error("File is in wrong format. Decoder not found.");
            return false;
        }

        resetVariables();

        info("Loading song \"%s\".", FilenameUtils.getBaseName(file.getName()));

        loadingSongFuture = CompletableFuture.supplyAsync(() -> SongDecoders.parse(file));
        loadingSongFuture.completeOnTimeout(null, 10, TimeUnit.SECONDS);

        stage = Stage.Loading_Song;
        long time1 = System.currentTimeMillis();
        loadingSongFuture.thenAccept(song -> {
            if (song != null) {
                this.song = song;
                long time2 = System.currentTimeMillis();
                long difference = time2 - time1;
                info("Song '" + FilenameUtils.getBaseName(file.getName()) + "' has been loaded to the memory! Took " + difference + "ms");
                callback.run();
            } else {
                error("Could not load song '" + FilenameUtils.getBaseName(file.getName()) + "'");
                if (autoPlaySetting.get()) {
                    playRandomSong();
                }
            }
        });

        return true;
    }

    private void scanForNoteblocks() {
        if (mc.interactionManager == null || mc.world == null || mc.player == null) {
            return;
        }

        scannedNoteblocks.clear();

        int min = (int) (-mc.interactionManager.getReachDistance()) - 2;
        int max = (int) mc.interactionManager.getReachDistance() + 2;
        for (int y = min; y < max; y++) {
            for (int x = min; x < max; x++) {
                for (int z = min; z < max; z++) {
                    BlockPos pos = mc.player.getBlockPos().add(x, y + 1, z);

                    BlockState blockState = mc.world.getBlockState(pos);
                    if (blockState.getBlock() != Blocks.NOTE_BLOCK) {
                        continue;
                    }

                    // Copied from ServerPlayNetworkHandler#onPlayerInteractBlock
                    Vec3d vec3d2 = Vec3d.ofCenter(pos);
                    double sqDist = mc.player.getEyePos().squaredDistanceTo(vec3d2);
                    if (sqDist > ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) {
                        continue;
                    }

                    if (!isValidScanSpot(pos)) {
                        continue;
                    }

                    Note note = NotebotUtils.getNoteFromNoteBlock(blockState, modeSetting.get());
                    scannedNoteblocks.put(note, pos);
                }
            }

        }
    }

    private void onTickPreview() {
        for (Note note : song.getNotesMap().get(currentTick)) {
            if (modeSetting.get() == NotebotUtils.NotebotMode.Exact_Instruments) {
                mc.player.playSound(note.getInstrument().getSound().value(), 2f, (float) Math.pow(2.0D, (note.getNoteLevel() - 12) / 12.0D));
            } else {
                mc.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HARP.value(), 2f, (float) Math.pow(2.0D, (note.getNoteLevel() - 12) / 12.0D));
            }
        }
    }

    private void tune() {
        if (tuneHits.isEmpty()) {
            if (anyNoteblockTuned) {
                anyNoteblockTuned = false;
                waitTicks = checkNoteblocksAgainDelaySetting.get();
                stage = Stage.Waiting_To_Check_Noteblocks;

                info("Delaying check for noteblocks.");
            } else {
                stage = Stage.Playing;

                info("Loading done.");

                play();
            }
            return;
        }

        if (ticks < tickDelaySetting.get()) {
            return;
        }

        tuneBlocks();

        ticks = 0;
    }

    private void tuneBlocks() {
        if (mc.world == null || mc.player == null) {
            disable();
        }

        if (swingSetting.get()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        int iterations = 0;
        var iterator = tuneHits.entrySet().iterator();
        while (iterator.hasNext()){
            var entry = iterator.next();
            BlockPos pos = entry.getKey();
            int hitsNumber = entry.getValue();

            if (autoRotateSetting.get()) {
                Rotations.rotate(Rotations.getYaw(pos), Rotations.getPitch(pos), 100, () -> tuneNoteblockWithPackets(pos));
            } else {
                this.tuneNoteblockWithPackets(pos);
            }

            clickedBlocks.add(pos);

            hitsNumber--;
            entry.setValue(hitsNumber);
            if (hitsNumber == 0) {
                iterator.remove();
            }

            iterations++;
            if (iterations == concurrentTuneBlocksSetting.get()) {
                return;
            }
        }
    }

    private void tuneNoteblockWithPackets(BlockPos pos) {
        // We don't need to raycast here. Server handles this packet fine
        mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(pos), Direction.DOWN, pos, false), 0));

        anyNoteblockTuned = true;
    }

    public void updateStatus() {
        if (status != null) {
            status.set(getStatus());
        }
    }

    private static int calcNumberOfHits(int from, int to) {
        if (from > to) {
            return (25 - from) + to;
        } else {
            return to - from;
        }
    }

    private void onTickPlay() {
        Collection<Note> notes = song.getNotesMap().get(this.currentTick);
        if (!notes.isEmpty()) {
            if (autoRotateSetting.get()) {
                Optional<Note> firstNote = notes.stream().findFirst();
                if (firstNote.isPresent()) {
                    BlockPos firstPos = noteBlockPositions.get(firstNote.get());
                    if (firstPos != null) {
                        Rotations.rotate(Rotations.getYaw(firstPos), Rotations.getPitch(firstPos));
                    }
                }
            }

            if (swingSetting.get()) {
                mc.player.swingHand(Hand.MAIN_HAND);
            }

            for (Note note : notes) {
                BlockPos pos = noteBlockPositions.get(note);
                if (pos == null) {
                    return;
                }

                if (polyphonicSetting.get()) {
                    playRotate(pos);
                } else {
                    this.playRotate(pos);
                }
            }
        }
    }

    private void playRotate(BlockPos pos) {
        if (mc.interactionManager == null) {
            return;
        }

        try {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, Direction.DOWN, 0));
        } catch (NullPointerException ignored) {}
    }

    private boolean isValidScanSpot(BlockPos pos) {
        if (mc.world.getBlockState(pos).getBlock() != Blocks.NOTE_BLOCK) return false;
        return mc.world.getBlockState(pos.up()).isAir();
    }

    @Nullable
    public Instrument getMappedInstrument(Instrument inst) {
        if (modeSetting.get() == NotebotUtils.NotebotMode.Exact_Instruments) {
            return ((NotebotUtils.OptionalInstrument) noteMapSettings.getByIndex(inst.ordinal()).get()).toMinecraftInstrument();
        } else {
            return inst;
        }
    }

    private String beautifyText(String text) {
        text = text.toLowerCase(Locale.ROOT);

        String[] array = text.split("_");
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : array) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1));
        }

        return stringBuilder.toString().trim();
    }

    public enum Stage {
        None("None"),
        Loading_Song("Loading song"),
        Set_Up("Set up"),
        Tune("Tune"),
        Waiting_To_Check_Noteblocks("Waiting to check noteblocks"),
        Playing("Playing"),
        Preview("Preview");

        private final String name;

        Stage(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
