/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SNBTToNBTConverter
implements IDataProvider {
    @Nullable
    private static final Path EMPTY = null;
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final List<ITransformer> transformers = Lists.newArrayList();

    public SNBTToNBTConverter(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    public SNBTToNBTConverter addTransformer(ITransformer iTransformer) {
        this.transformers.add(iTransformer);
        return this;
    }

    private CompoundNBT snbtToNBT(String string, CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2 = compoundNBT;
        for (ITransformer iTransformer : this.transformers) {
            compoundNBT2 = iTransformer.func_225371_a(string, compoundNBT2);
        }
        return compoundNBT2;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        Path path = this.generator.getOutputFolder();
        ArrayList arrayList = Lists.newArrayList();
        for (Path path2 : this.generator.getInputFolders()) {
            Files.walk(path2, new FileVisitOption[0]).filter(SNBTToNBTConverter::lambda$act$0).forEach(arg_0 -> this.lambda$act$2(arrayList, path2, arg_0));
        }
        Util.gather(arrayList).join().stream().filter(Objects::nonNull).forEach(arg_0 -> this.lambda$act$3(directoryCache, path, arg_0));
    }

    @Override
    public String getName() {
        return "SNBT -> NBT";
    }

    private String getFileName(Path path, Path path2) {
        String string = path.relativize(path2).toString().replaceAll("\\\\", "/");
        return string.substring(0, string.length() - 5);
    }

    @Nullable
    private TaskResult convertSNBTToNBT(Path path, String string) {
        block10: {
            TaskResult taskResult;
            block9: {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                try {
                    String string2 = IOUtils.toString(bufferedReader);
                    CompoundNBT compoundNBT = this.snbtToNBT(string, JsonToNBT.getTagFromJson(string2));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    CompressedStreamTools.writeCompressed(compoundNBT, byteArrayOutputStream);
                    byte[] byArray = byteArrayOutputStream.toByteArray();
                    String string3 = HASH_FUNCTION.hashBytes(byArray).toString();
                    String string4 = EMPTY != null ? compoundNBT.toFormattedComponent("    ", 0).getString() + "\n" : null;
                    taskResult = new TaskResult(string, byArray, string4, string3);
                    if (bufferedReader == null) break block9;
                } catch (Throwable throwable) {
                    try {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    } catch (CommandSyntaxException commandSyntaxException) {
                        LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", (Object)string, (Object)path, (Object)commandSyntaxException);
                        break block10;
                    } catch (IOException iOException) {
                        LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", (Object)string, (Object)path, (Object)iOException);
                    }
                }
                bufferedReader.close();
            }
            return taskResult;
        }
        return null;
    }

    private void writeStructureSNBT(DirectoryCache directoryCache, TaskResult taskResult, Path path) {
        Path path2;
        if (taskResult.field_240515_c_ != null) {
            path2 = EMPTY.resolve(taskResult.fileName + ".snbt");
            try {
                FileUtils.write(path2.toFile(), (CharSequence)taskResult.field_240515_c_, StandardCharsets.UTF_8);
            } catch (IOException iOException) {
                LOGGER.error("Couldn't write structure SNBT {} at {}", (Object)taskResult.fileName, (Object)path2, (Object)iOException);
            }
        }
        path2 = path.resolve(taskResult.fileName + ".nbt");
        try {
            if (!Objects.equals(directoryCache.getPreviousHash(path2), taskResult.bytesHash) || !Files.exists(path2, new LinkOption[0])) {
                Files.createDirectories(path2.getParent(), new FileAttribute[0]);
                try (OutputStream outputStream = Files.newOutputStream(path2, new OpenOption[0]);){
                    outputStream.write(taskResult.nbtBytes);
                }
            }
            directoryCache.recordHash(path2, taskResult.bytesHash);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't write structure {} at {}", (Object)taskResult.fileName, (Object)path2, (Object)iOException);
        }
    }

    private void lambda$act$3(DirectoryCache directoryCache, Path path, TaskResult taskResult) {
        this.writeStructureSNBT(directoryCache, taskResult, path);
    }

    private void lambda$act$2(List list, Path path, Path path2) {
        list.add(CompletableFuture.supplyAsync(() -> this.lambda$act$1(path2, path), Util.getServerExecutor()));
    }

    private TaskResult lambda$act$1(Path path, Path path2) {
        return this.convertSNBTToNBT(path, this.getFileName(path2, path));
    }

    private static boolean lambda$act$0(Path path) {
        return path.toString().endsWith(".snbt");
    }

    @FunctionalInterface
    public static interface ITransformer {
        public CompoundNBT func_225371_a(String var1, CompoundNBT var2);
    }

    static class TaskResult {
        private final String fileName;
        private final byte[] nbtBytes;
        @Nullable
        private final String field_240515_c_;
        private final String bytesHash;

        public TaskResult(String string, byte[] byArray, @Nullable String string2, String string3) {
            this.fileName = string;
            this.nbtBytes = byArray;
            this.field_240515_c_ = string2;
            this.bytesHash = string3;
        }
    }
}

