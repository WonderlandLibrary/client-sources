/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.text.ITextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTToSNBTConverter
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;

    public NBTToSNBTConverter(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        Path path = this.generator.getOutputFolder();
        for (Path path2 : this.generator.getInputFolders()) {
            Files.walk(path2, new FileVisitOption[0]).filter(NBTToSNBTConverter::lambda$act$0).forEach(arg_0 -> this.lambda$act$1(path2, path, arg_0));
        }
    }

    @Override
    public String getName() {
        return "NBT to SNBT";
    }

    private String getFileName(Path path, Path path2) {
        String string = path.relativize(path2).toString().replaceAll("\\\\", "/");
        return string.substring(0, string.length() - 4);
    }

    @Nullable
    public static Path convertNBTToSNBT(Path path, String string, Path path2) {
        try {
            CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(Files.newInputStream(path, new OpenOption[0]));
            ITextComponent iTextComponent = compoundNBT.toFormattedComponent("    ", 0);
            String string2 = iTextComponent.getString() + "\n";
            Path path3 = path2.resolve(string + ".snbt");
            Files.createDirectories(path3.getParent(), new FileAttribute[0]);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path3, new OpenOption[0]);){
                bufferedWriter.write(string2);
            }
            LOGGER.info("Converted {} from NBT to SNBT", (Object)string);
            return path3;
        } catch (IOException iOException) {
            LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", (Object)string, (Object)path, (Object)iOException);
            return null;
        }
    }

    private void lambda$act$1(Path path, Path path2, Path path3) {
        NBTToSNBTConverter.convertNBTToSNBT(path3, this.getFileName(path, path3), path2);
    }

    private static boolean lambda$act$0(Path path) {
        return path.toString().endsWith(".nbt");
    }
}

