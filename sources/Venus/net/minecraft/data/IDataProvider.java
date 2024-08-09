/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import net.minecraft.data.DirectoryCache;

public interface IDataProvider {
    public static final HashFunction HASH_FUNCTION = Hashing.sha1();

    public void act(DirectoryCache var1) throws IOException;

    public String getName();

    public static void save(Gson gson, DirectoryCache directoryCache, JsonElement jsonElement, Path path) throws IOException {
        String string = gson.toJson(jsonElement);
        String string2 = HASH_FUNCTION.hashUnencodedChars(string).toString();
        if (!Objects.equals(directoryCache.getPreviousHash(path), string2) || !Files.exists(path, new LinkOption[0])) {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                bufferedWriter.write(string);
            }
        }
        directoryCache.recordHash(path, string2);
    }
}

