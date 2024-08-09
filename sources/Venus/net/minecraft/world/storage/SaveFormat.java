/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.server.SessionLockManager;
import net.minecraft.util.FileUtil;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.AnvilSaveConverter;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.PlayerData;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraft.world.storage.VersionData;
import net.minecraft.world.storage.WorldSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormat {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter BACKUP_DATE_FORMAT = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();
    private static final ImmutableList<String> WORLD_GEN_SETTING_STRINGS = ImmutableList.of("RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest");
    private final Path savesDir;
    private final Path backupsDir;
    private final DataFixer dataFixer;

    public SaveFormat(Path path, Path path2, DataFixer dataFixer) {
        this.dataFixer = dataFixer;
        try {
            Files.createDirectories(Files.exists(path, new LinkOption[0]) ? path.toRealPath(new LinkOption[0]) : path, new FileAttribute[0]);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        this.savesDir = path;
        this.backupsDir = path2;
    }

    public static SaveFormat create(Path path) {
        return new SaveFormat(path, path.resolve("../backups"), DataFixesManager.getDataFixer());
    }

    private static <T> Pair<DimensionGeneratorSettings, Lifecycle> getSettingLifecyclePair(Dynamic<T> dynamic, DataFixer dataFixer, int n) {
        Dynamic<T> dynamic2 = dynamic.get("WorldGenSettings").orElseEmptyMap();
        for (String object2 : WORLD_GEN_SETTING_STRINGS) {
            Optional<Dynamic<T>> optional = dynamic.get(object2).result();
            if (!optional.isPresent()) continue;
            dynamic2 = dynamic2.set(object2, optional.get());
        }
        Dynamic<T> dynamic3 = dataFixer.update(TypeReferences.WORLD_GEN_SETTINGS, dynamic2, n, SharedConstants.getVersion().getWorldVersion());
        DataResult dataResult = DimensionGeneratorSettings.field_236201_a_.parse(dynamic3);
        return Pair.of(dataResult.resultOrPartial(Util.func_240982_a_("WorldGenSettings: ", LOGGER::error)).orElseGet(() -> SaveFormat.lambda$getSettingLifecyclePair$3(dynamic3)), dataResult.lifecycle());
    }

    private static DatapackCodec decodeDatapackCodec(Dynamic<?> dynamic) {
        return DatapackCodec.CODEC.parse(dynamic).resultOrPartial(LOGGER::error).orElse(DatapackCodec.VANILLA_CODEC);
    }

    public List<WorldSummary> getSaveList() throws AnvilConverterException {
        File[] fileArray;
        if (!Files.isDirectory(this.savesDir, new LinkOption[0])) {
            throw new AnvilConverterException(new TranslationTextComponent("selectWorld.load_folder_access").getString());
        }
        ArrayList<WorldSummary> arrayList = Lists.newArrayList();
        for (File file : fileArray = this.savesDir.toFile().listFiles()) {
            boolean bl;
            if (!file.isDirectory()) continue;
            try {
                bl = SessionLockManager.func_232999_b_(file.toPath());
            } catch (Exception exception) {
                LOGGER.warn("Failed to read {} lock", (Object)file, (Object)exception);
                continue;
            }
            WorldSummary worldSummary = this.readFromLevelData(file, this.readWorldSummary(file, bl));
            if (worldSummary == null) continue;
            arrayList.add(worldSummary);
        }
        return arrayList;
    }

    private int getStorageVersionId() {
        return 0;
    }

    @Nullable
    private <T> T readFromLevelData(File file, BiFunction<File, DataFixer, T> biFunction) {
        T t;
        if (!file.exists()) {
            return null;
        }
        File file2 = new File(file, "level.dat");
        if (file2.exists() && (t = biFunction.apply(file2, this.dataFixer)) != null) {
            return t;
        }
        file2 = new File(file, "level.dat_old");
        return file2.exists() ? (T)biFunction.apply(file2, this.dataFixer) : null;
    }

    @Nullable
    private static DatapackCodec readWorldDatapackCodec(File file, DataFixer dataFixer) {
        try {
            CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(file);
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("Data");
            compoundNBT2.remove("Player");
            int n = compoundNBT2.contains("DataVersion", 0) ? compoundNBT2.getInt("DataVersion") : -1;
            Dynamic<CompoundNBT> dynamic = dataFixer.update(DefaultTypeReferences.LEVEL.getTypeReference(), new Dynamic<CompoundNBT>(NBTDynamicOps.INSTANCE, compoundNBT2), n, SharedConstants.getVersion().getWorldVersion());
            return dynamic.get("DataPacks").result().map(SaveFormat::decodeDatapackCodec).orElse(DatapackCodec.VANILLA_CODEC);
        } catch (Exception exception) {
            LOGGER.error("Exception reading {}", (Object)file, (Object)exception);
            return null;
        }
    }

    private static BiFunction<File, DataFixer, ServerWorldInfo> readServerWorldInfo(DynamicOps<INBT> dynamicOps, DatapackCodec datapackCodec) {
        return (arg_0, arg_1) -> SaveFormat.lambda$readServerWorldInfo$4(dynamicOps, datapackCodec, arg_0, arg_1);
    }

    private BiFunction<File, DataFixer, WorldSummary> readWorldSummary(File file, boolean bl) {
        return (arg_0, arg_1) -> this.lambda$readWorldSummary$5(file, bl, arg_0, arg_1);
    }

    public boolean isNewLevelIdAcceptable(String string) {
        try {
            Path path = this.savesDir.resolve(string);
            Files.createDirectory(path, new FileAttribute[0]);
            Files.deleteIfExists(path);
            return true;
        } catch (IOException iOException) {
            return true;
        }
    }

    public boolean canLoadWorld(String string) {
        return Files.isDirectory(this.savesDir.resolve(string), new LinkOption[0]);
    }

    public Path getSavesDir() {
        return this.savesDir;
    }

    public Path getBackupsFolder() {
        return this.backupsDir;
    }

    public LevelSave getLevelSave(String string) throws IOException {
        return new LevelSave(this, string);
    }

    private WorldSummary lambda$readWorldSummary$5(File file, boolean bl, File file2, DataFixer dataFixer) {
        try {
            CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(file2);
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("Data");
            compoundNBT2.remove("Player");
            int n = compoundNBT2.contains("DataVersion", 0) ? compoundNBT2.getInt("DataVersion") : -1;
            Dynamic<CompoundNBT> dynamic = dataFixer.update(DefaultTypeReferences.LEVEL.getTypeReference(), new Dynamic<CompoundNBT>(NBTDynamicOps.INSTANCE, compoundNBT2), n, SharedConstants.getVersion().getWorldVersion());
            VersionData versionData = VersionData.getVersionData(dynamic);
            int n2 = versionData.getStorageVersionID();
            if (n2 != 19132 && n2 != 19133) {
                return null;
            }
            boolean bl2 = n2 != this.getStorageVersionId();
            File file3 = new File(file, "icon.png");
            DatapackCodec datapackCodec = dynamic.get("DataPacks").result().map(SaveFormat::decodeDatapackCodec).orElse(DatapackCodec.VANILLA_CODEC);
            WorldSettings worldSettings = WorldSettings.decodeWorldSettings(dynamic, datapackCodec);
            return new WorldSummary(worldSettings, versionData, file.getName(), bl2, bl, file3);
        } catch (Exception exception) {
            LOGGER.error("Exception reading {}", (Object)file2, (Object)exception);
            return null;
        }
    }

    private static ServerWorldInfo lambda$readServerWorldInfo$4(DynamicOps dynamicOps, DatapackCodec datapackCodec, File file, DataFixer dataFixer) {
        try {
            CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(file);
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("Data");
            CompoundNBT compoundNBT3 = compoundNBT2.contains("Player", 1) ? compoundNBT2.getCompound("Player") : null;
            compoundNBT2.remove("Player");
            int n = compoundNBT2.contains("DataVersion", 0) ? compoundNBT2.getInt("DataVersion") : -1;
            Dynamic<INBT> dynamic = dataFixer.update(DefaultTypeReferences.LEVEL.getTypeReference(), new Dynamic<CompoundNBT>(dynamicOps, compoundNBT2), n, SharedConstants.getVersion().getWorldVersion());
            Pair<DimensionGeneratorSettings, Lifecycle> pair = SaveFormat.getSettingLifecyclePair(dynamic, dataFixer, n);
            VersionData versionData = VersionData.getVersionData(dynamic);
            WorldSettings worldSettings = WorldSettings.decodeWorldSettings(dynamic, datapackCodec);
            return ServerWorldInfo.decodeWorldInfo(dynamic, dataFixer, n, compoundNBT3, worldSettings, versionData, pair.getFirst(), pair.getSecond());
        } catch (Exception exception) {
            LOGGER.error("Exception reading {}", (Object)file, (Object)exception);
            return null;
        }
    }

    private static DimensionGeneratorSettings lambda$getSettingLifecyclePair$3(Dynamic dynamic) {
        Registry registry = (Registry)RegistryLookupCodec.getLookUpCodec(Registry.DIMENSION_TYPE_KEY).codec().parse(dynamic).resultOrPartial(Util.func_240982_a_("Dimension type registry: ", LOGGER::error)).orElseThrow(SaveFormat::lambda$getSettingLifecyclePair$0);
        Registry registry2 = (Registry)RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).codec().parse(dynamic).resultOrPartial(Util.func_240982_a_("Biome registry: ", LOGGER::error)).orElseThrow(SaveFormat::lambda$getSettingLifecyclePair$1);
        Registry registry3 = (Registry)RegistryLookupCodec.getLookUpCodec(Registry.NOISE_SETTINGS_KEY).codec().parse(dynamic).resultOrPartial(Util.func_240982_a_("Noise settings registry: ", LOGGER::error)).orElseThrow(SaveFormat::lambda$getSettingLifecyclePair$2);
        return DimensionGeneratorSettings.func_242751_a(registry, registry2, registry3);
    }

    private static IllegalStateException lambda$getSettingLifecyclePair$2() {
        return new IllegalStateException("Failed to get noise settings registry");
    }

    private static IllegalStateException lambda$getSettingLifecyclePair$1() {
        return new IllegalStateException("Failed to get biome registry");
    }

    private static IllegalStateException lambda$getSettingLifecyclePair$0() {
        return new IllegalStateException("Failed to get dimension registry");
    }

    public class LevelSave
    implements AutoCloseable {
        private final SessionLockManager saveDirLockManager;
        private final Path saveDir;
        private final String saveName;
        private final Map<FolderName, Path> localPathCache;
        final SaveFormat this$0;

        public LevelSave(SaveFormat saveFormat, String string) throws IOException {
            this.this$0 = saveFormat;
            this.localPathCache = Maps.newHashMap();
            this.saveName = string;
            this.saveDir = saveFormat.savesDir.resolve(string);
            this.saveDirLockManager = SessionLockManager.func_232998_a_(this.saveDir);
        }

        public String getSaveName() {
            return this.saveName;
        }

        public Path resolveFilePath(FolderName folderName) {
            return this.localPathCache.computeIfAbsent(folderName, this::lambda$resolveFilePath$0);
        }

        public File getDimensionFolder(RegistryKey<World> registryKey) {
            return DimensionType.getDimensionFolder(registryKey, this.saveDir.toFile());
        }

        private void validateSaveDirLock() {
            if (!this.saveDirLockManager.func_232997_a_()) {
                throw new IllegalStateException("Lock is no longer valid");
            }
        }

        public PlayerData getPlayerDataManager() {
            this.validateSaveDirLock();
            return new PlayerData(this, this.this$0.dataFixer);
        }

        public boolean isSaveFormatOutdated() {
            WorldSummary worldSummary = this.readWorldSummary();
            return worldSummary != null && worldSummary.getVersionData().getStorageVersionID() != this.this$0.getStorageVersionId();
        }

        public boolean convertRegions(IProgressUpdate iProgressUpdate) {
            this.validateSaveDirLock();
            return AnvilSaveConverter.convertRegions(this, iProgressUpdate);
        }

        @Nullable
        public WorldSummary readWorldSummary() {
            this.validateSaveDirLock();
            return this.this$0.readFromLevelData(this.saveDir.toFile(), this.this$0.readWorldSummary(this.saveDir.toFile(), true));
        }

        @Nullable
        public IServerConfiguration readServerConfiguration(DynamicOps<INBT> dynamicOps, DatapackCodec datapackCodec) {
            this.validateSaveDirLock();
            return this.this$0.readFromLevelData(this.saveDir.toFile(), SaveFormat.readServerWorldInfo(dynamicOps, datapackCodec));
        }

        @Nullable
        public DatapackCodec readDatapackCodec() {
            this.validateSaveDirLock();
            return this.this$0.readFromLevelData(this.saveDir.toFile(), LevelSave::lambda$readDatapackCodec$1);
        }

        public void saveLevel(DynamicRegistries dynamicRegistries, IServerConfiguration iServerConfiguration) {
            this.saveLevel(dynamicRegistries, iServerConfiguration, null);
        }

        public void saveLevel(DynamicRegistries dynamicRegistries, IServerConfiguration iServerConfiguration, @Nullable CompoundNBT compoundNBT) {
            File file = this.saveDir.toFile();
            CompoundNBT compoundNBT2 = iServerConfiguration.serialize(dynamicRegistries, compoundNBT);
            CompoundNBT compoundNBT3 = new CompoundNBT();
            compoundNBT3.put("Data", compoundNBT2);
            try {
                File file2 = File.createTempFile("level", ".dat", file);
                CompressedStreamTools.writeCompressed(compoundNBT3, file2);
                File file3 = new File(file, "level.dat_old");
                File file4 = new File(file, "level.dat");
                Util.backupThenUpdate(file4, file2, file3);
            } catch (Exception exception) {
                LOGGER.error("Failed to save level {}", (Object)file, (Object)exception);
            }
        }

        public File getIconFile() {
            this.validateSaveDirLock();
            return this.saveDir.resolve("icon.png").toFile();
        }

        public void deleteSave() throws IOException {
            this.validateSaveDirLock();
            Path path = this.saveDir.resolve("session.lock");
            for (int i = 1; i <= 5; ++i) {
                LOGGER.info("Attempt {}...", (Object)i);
                try {
                    Files.walkFileTree(this.saveDir, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(this, path){
                        final Path val$path;
                        final LevelSave this$1;
                        {
                            this.this$1 = levelSave;
                            this.val$path = path;
                        }

                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                            if (!path.equals(this.val$path)) {
                                LOGGER.debug("Deleting {}", (Object)path);
                                Files.delete(path);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
                            if (iOException != null) {
                                throw iOException;
                            }
                            if (path.equals(this.this$1.saveDir)) {
                                this.this$1.saveDirLockManager.close();
                                Files.deleteIfExists(this.val$path);
                            }
                            Files.delete(path);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Object object, IOException iOException) throws IOException {
                            return this.postVisitDirectory((Path)object, iOException);
                        }

                        @Override
                        public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                            return this.visitFile((Path)object, basicFileAttributes);
                        }
                    });
                    break;
                } catch (IOException iOException) {
                    if (i >= 5) {
                        throw iOException;
                    }
                    LOGGER.warn("Failed to delete {}", (Object)this.saveDir, (Object)iOException);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    continue;
                }
            }
        }

        public void updateSaveName(String string) throws IOException {
            File file;
            this.validateSaveDirLock();
            File file2 = new File(this.this$0.savesDir.toFile(), this.saveName);
            if (file2.exists() && (file = new File(file2, "level.dat")).exists()) {
                CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(file);
                CompoundNBT compoundNBT2 = compoundNBT.getCompound("Data");
                compoundNBT2.putString("LevelName", string);
                CompressedStreamTools.writeCompressed(compoundNBT, file);
            }
        }

        public long createBackup() throws IOException {
            this.validateSaveDirLock();
            String string = LocalDateTime.now().format(BACKUP_DATE_FORMAT) + "_" + this.saveName;
            Path path = this.this$0.getBackupsFolder();
            try {
                Files.createDirectories(Files.exists(path, new LinkOption[0]) ? path.toRealPath(new LinkOption[0]) : path, new FileAttribute[0]);
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            Path path2 = path.resolve(FileUtil.findAvailableName(path, string, ".zip"));
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path2, new OpenOption[0])));){
                Path path3 = Paths.get(this.saveName, new String[0]);
                Files.walkFileTree(this.saveDir, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(this, path3, zipOutputStream){
                    final Path val$path2;
                    final ZipOutputStream val$zipoutputstream;
                    final LevelSave this$1;
                    {
                        this.this$1 = levelSave;
                        this.val$path2 = path;
                        this.val$zipoutputstream = zipOutputStream;
                    }

                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                        if (path.endsWith("session.lock")) {
                            return FileVisitResult.CONTINUE;
                        }
                        String string = this.val$path2.resolve(this.this$1.saveDir.relativize(path)).toString().replace('\\', '/');
                        ZipEntry zipEntry = new ZipEntry(string);
                        this.val$zipoutputstream.putNextEntry(zipEntry);
                        com.google.common.io.Files.asByteSource(path.toFile()).copyTo(this.val$zipoutputstream);
                        this.val$zipoutputstream.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                        return this.visitFile((Path)object, basicFileAttributes);
                    }
                });
            }
            return Files.size(path2);
        }

        @Override
        public void close() throws IOException {
            this.saveDirLockManager.close();
        }

        private static DatapackCodec lambda$readDatapackCodec$1(File file, DataFixer dataFixer) {
            return SaveFormat.readWorldDatapackCodec(file, dataFixer);
        }

        private Path lambda$resolveFilePath$0(FolderName folderName) {
            return this.saveDir.resolve(folderName.getFileName());
        }
    }
}

