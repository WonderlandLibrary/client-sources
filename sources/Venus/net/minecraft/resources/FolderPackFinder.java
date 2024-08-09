/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.resources.FilePack;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;

public class FolderPackFinder
implements IPackFinder {
    private static final FileFilter FILE_FILTER = FolderPackFinder::lambda$static$0;
    private final File folder;
    private final IPackNameDecorator field_232610_c_;

    public FolderPackFinder(File file, IPackNameDecorator iPackNameDecorator) {
        this.folder = file;
        this.field_232610_c_ = iPackNameDecorator;
    }

    @Override
    public void findPacks(Consumer<ResourcePackInfo> consumer, ResourcePackInfo.IFactory iFactory) {
        File[] fileArray;
        if (!this.folder.isDirectory()) {
            this.folder.mkdirs();
        }
        if ((fileArray = this.folder.listFiles(FILE_FILTER)) != null) {
            for (File file : fileArray) {
                String string = "file/" + file.getName();
                ResourcePackInfo resourcePackInfo = ResourcePackInfo.createResourcePack(string, false, this.makePackSupplier(file), iFactory, ResourcePackInfo.Priority.TOP, this.field_232610_c_);
                if (resourcePackInfo == null) continue;
                consumer.accept(resourcePackInfo);
            }
        }
    }

    private Supplier<IResourcePack> makePackSupplier(File file) {
        return file.isDirectory() ? () -> FolderPackFinder.lambda$makePackSupplier$1(file) : () -> FolderPackFinder.lambda$makePackSupplier$2(file);
    }

    private static IResourcePack lambda$makePackSupplier$2(File file) {
        return new FilePack(file);
    }

    private static IResourcePack lambda$makePackSupplier$1(File file) {
        return new FolderPack(file);
    }

    private static boolean lambda$static$0(File file) {
        boolean bl = file.isFile() && file.getName().endsWith(".zip");
        boolean bl2 = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
        return bl || bl2;
    }
}

