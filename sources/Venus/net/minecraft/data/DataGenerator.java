/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.registry.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Collection<Path> inputFolders;
    private final Path outputFolder;
    private final List<IDataProvider> providers = Lists.newArrayList();

    public DataGenerator(Path path, Collection<Path> collection) {
        this.outputFolder = path;
        this.inputFolders = collection;
    }

    public Collection<Path> getInputFolders() {
        return this.inputFolders;
    }

    public Path getOutputFolder() {
        return this.outputFolder;
    }

    public void run() throws IOException {
        DirectoryCache directoryCache = new DirectoryCache(this.outputFolder, "cache");
        directoryCache.addProtectedPath(this.getOutputFolder().resolve("version.json"));
        Stopwatch stopwatch = Stopwatch.createStarted();
        Stopwatch stopwatch2 = Stopwatch.createUnstarted();
        for (IDataProvider iDataProvider : this.providers) {
            LOGGER.info("Starting provider: {}", (Object)iDataProvider.getName());
            stopwatch2.start();
            iDataProvider.act(directoryCache);
            stopwatch2.stop();
            LOGGER.info("{} finished after {} ms", (Object)iDataProvider.getName(), (Object)stopwatch2.elapsed(TimeUnit.MILLISECONDS));
            stopwatch2.reset();
        }
        LOGGER.info("All providers took: {} ms", (Object)stopwatch.elapsed(TimeUnit.MILLISECONDS));
        directoryCache.writeCache();
    }

    public void addProvider(IDataProvider iDataProvider) {
        this.providers.add(iDataProvider);
    }

    static {
        Bootstrap.register();
    }
}

