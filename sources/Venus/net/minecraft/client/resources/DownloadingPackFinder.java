/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.VirtualAssetsPack;
import net.minecraft.resources.FilePack;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.VanillaPack;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadingPackFinder
implements IPackFinder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern PATTERN_SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
    private final VanillaPack vanillaPack;
    private final File serverPackDir;
    private final ReentrantLock lockDownload = new ReentrantLock();
    private final ResourceIndex resourceIndex;
    @Nullable
    private CompletableFuture<?> currentDownload;
    @Nullable
    private ResourcePackInfo serverPack;

    public DownloadingPackFinder(File file, ResourceIndex resourceIndex) {
        this.serverPackDir = file;
        this.resourceIndex = resourceIndex;
        this.vanillaPack = new VirtualAssetsPack(resourceIndex);
    }

    @Override
    public void findPacks(Consumer<ResourcePackInfo> consumer, ResourcePackInfo.IFactory iFactory) {
        ResourcePackInfo resourcePackInfo;
        ResourcePackInfo resourcePackInfo2 = ResourcePackInfo.createResourcePack("vanilla", true, this::lambda$findPacks$0, iFactory, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.BUILTIN);
        if (resourcePackInfo2 != null) {
            consumer.accept(resourcePackInfo2);
        }
        if (this.serverPack != null) {
            consumer.accept(this.serverPack);
        }
        if ((resourcePackInfo = this.func_239453_a_(iFactory)) != null) {
            consumer.accept(resourcePackInfo);
        }
    }

    public VanillaPack getVanillaPack() {
        return this.vanillaPack;
    }

    private static Map<String, String> getPackDownloadRequestProperties() {
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("X-Minecraft-Username", Minecraft.getInstance().getSession().getUsername());
        hashMap.put("X-Minecraft-UUID", Minecraft.getInstance().getSession().getPlayerID());
        hashMap.put("X-Minecraft-Version", SharedConstants.getVersion().getName());
        hashMap.put("X-Minecraft-Version-ID", SharedConstants.getVersion().getId());
        hashMap.put("X-Minecraft-Pack-Format", String.valueOf(SharedConstants.getVersion().getPackVersion()));
        hashMap.put("User-Agent", "Minecraft Java/" + SharedConstants.getVersion().getName());
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CompletableFuture<?> downloadResourcePack(String string, String string2) {
        CompletableFuture<?> completableFuture;
        String string3 = DigestUtils.sha1Hex(string);
        String string4 = PATTERN_SHA1.matcher(string2).matches() ? string2 : "";
        this.lockDownload.lock();
        try {
            CompletableFuture<String> completableFuture2;
            this.clearResourcePack();
            this.clearDownloads();
            File file = new File(this.serverPackDir, string3);
            if (file.exists()) {
                completableFuture2 = CompletableFuture.completedFuture("");
            } else {
                WorkingScreen workingScreen = new WorkingScreen();
                Map<String, String> map = DownloadingPackFinder.getPackDownloadRequestProperties();
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.runImmediately(() -> DownloadingPackFinder.lambda$downloadResourcePack$1(minecraft, workingScreen));
                completableFuture2 = HTTPUtil.downloadResourcePack(file, string, map, 0x6400000, workingScreen, minecraft.getProxy());
            }
            completableFuture = this.currentDownload = ((CompletableFuture)completableFuture2.thenCompose(arg_0 -> this.lambda$downloadResourcePack$2(string4, file, arg_0))).whenComplete((arg_0, arg_1) -> DownloadingPackFinder.lambda$downloadResourcePack$3(file, arg_0, arg_1));
        } finally {
            this.lockDownload.unlock();
        }
        return completableFuture;
    }

    private static void deleteQuiet(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException iOException) {
            LOGGER.warn("Failed to delete file {}: {}", (Object)file, (Object)iOException.getMessage());
        }
    }

    public void clearResourcePack() {
        this.lockDownload.lock();
        try {
            if (this.currentDownload != null) {
                this.currentDownload.cancel(false);
            }
            this.currentDownload = null;
            if (this.serverPack != null) {
                this.serverPack = null;
                Minecraft.getInstance().scheduleResourcesRefresh();
            }
        } finally {
            this.lockDownload.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean checkHash(String string, File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            String string2 = DigestUtils.sha1Hex(fileInputStream);
            if (string.isEmpty()) {
                LOGGER.info("Found file {} without verification hash", (Object)file);
                boolean bl = true;
                return bl;
            }
            if (string2.toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT))) {
                LOGGER.info("Found file {} matching requested hash {}", (Object)file, (Object)string);
                boolean bl = true;
                return bl;
            }
            LOGGER.warn("File {} had wrong hash (expected {}, found {}).", (Object)file, (Object)string, (Object)string2);
            return true;
        } catch (IOException iOException) {
            LOGGER.warn("File {} couldn't be hashed.", (Object)file, (Object)iOException);
        }
        return true;
    }

    private void clearDownloads() {
        try {
            ArrayList<File> arrayList = Lists.newArrayList(FileUtils.listFiles(this.serverPackDir, TrueFileFilter.TRUE, null));
            arrayList.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int n = 0;
            for (File file : arrayList) {
                if (n++ < 10) continue;
                LOGGER.info("Deleting old server resource pack {}", (Object)file.getName());
                FileUtils.deleteQuietly(file);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error("Error while deleting old server resource pack : {}", (Object)illegalArgumentException.getMessage());
        }
    }

    public CompletableFuture<Void> setServerPack(File file, IPackNameDecorator iPackNameDecorator) {
        PackMetadataSection packMetadataSection;
        try (FilePack filePack = new FilePack(file);){
            packMetadataSection = filePack.getMetadata(PackMetadataSection.SERIALIZER);
        } catch (IOException iOException) {
            return Util.completedExceptionallyFuture(new IOException(String.format("Invalid resourcepack at %s", file), iOException));
        }
        LOGGER.info("Applying server pack {}", (Object)file);
        this.serverPack = new ResourcePackInfo("server", true, () -> DownloadingPackFinder.lambda$setServerPack$4(file), new TranslationTextComponent("resourcePack.server.name"), packMetadataSection.getDescription(), PackCompatibility.getCompatibility(packMetadataSection.getPackFormat()), ResourcePackInfo.Priority.TOP, true, iPackNameDecorator);
        return Minecraft.getInstance().scheduleResourcesRefresh();
    }

    @Nullable
    private ResourcePackInfo func_239453_a_(ResourcePackInfo.IFactory iFactory) {
        File file;
        ResourcePackInfo resourcePackInfo = null;
        File file2 = this.resourceIndex.getFile(new ResourceLocation("resourcepacks/programmer_art.zip"));
        if (file2 != null && file2.isFile()) {
            resourcePackInfo = DownloadingPackFinder.func_239454_a_(iFactory, () -> DownloadingPackFinder.lambda$func_239453_a_$5(file2));
        }
        if (resourcePackInfo == null && SharedConstants.developmentMode && (file = this.resourceIndex.getFile("../resourcepacks/programmer_art")) != null && file.isDirectory()) {
            resourcePackInfo = DownloadingPackFinder.func_239454_a_(iFactory, () -> DownloadingPackFinder.lambda$func_239453_a_$6(file));
        }
        return resourcePackInfo;
    }

    @Nullable
    private static ResourcePackInfo func_239454_a_(ResourcePackInfo.IFactory iFactory, Supplier<IResourcePack> supplier) {
        return ResourcePackInfo.createResourcePack("programer_art", false, supplier, iFactory, ResourcePackInfo.Priority.TOP, IPackNameDecorator.BUILTIN);
    }

    private static FolderPack func_239459_b_(File file) {
        return new FolderPack(file){

            @Override
            public String getName() {
                return "Programmer Art";
            }
        };
    }

    private static IResourcePack func_239460_c_(File file) {
        return new FilePack(file){

            @Override
            public String getName() {
                return "Programmer Art";
            }
        };
    }

    private static IResourcePack lambda$func_239453_a_$6(File file) {
        return DownloadingPackFinder.func_239459_b_(file);
    }

    private static IResourcePack lambda$func_239453_a_$5(File file) {
        return DownloadingPackFinder.func_239460_c_(file);
    }

    private static IResourcePack lambda$setServerPack$4(File file) {
        return new FilePack(file);
    }

    private static void lambda$downloadResourcePack$3(File file, Void void_, Throwable throwable) {
        if (throwable != null) {
            LOGGER.warn("Pack application failed: {}, deleting file {}", (Object)throwable.getMessage(), (Object)file);
            DownloadingPackFinder.deleteQuiet(file);
        }
    }

    private CompletionStage lambda$downloadResourcePack$2(String string, File file, Object object) {
        return !this.checkHash(string, file) ? Util.completedExceptionallyFuture(new RuntimeException("Hash check failure for file " + file + ", see log")) : this.setServerPack(file, IPackNameDecorator.SERVER);
    }

    private static void lambda$downloadResourcePack$1(Minecraft minecraft, WorkingScreen workingScreen) {
        minecraft.displayGuiScreen(workingScreen);
    }

    private IResourcePack lambda$findPacks$0() {
        return this.vanillaPack;
    }
}

