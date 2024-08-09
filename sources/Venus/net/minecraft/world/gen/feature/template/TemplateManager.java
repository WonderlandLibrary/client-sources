/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.FileUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TemplateManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, Template> templates = Maps.newHashMap();
    private final DataFixer fixer;
    private IResourceManager field_237130_d_;
    private final Path pathGenerated;

    public TemplateManager(IResourceManager iResourceManager, SaveFormat.LevelSave levelSave, DataFixer dataFixer) {
        this.field_237130_d_ = iResourceManager;
        this.fixer = dataFixer;
        this.pathGenerated = levelSave.resolveFilePath(FolderName.GENERATED).normalize();
    }

    public Template getTemplateDefaulted(ResourceLocation resourceLocation) {
        Template template = this.getTemplate(resourceLocation);
        if (template == null) {
            template = new Template();
            this.templates.put(resourceLocation, template);
        }
        return template;
    }

    @Nullable
    public Template getTemplate(ResourceLocation resourceLocation) {
        return this.templates.computeIfAbsent(resourceLocation, this::lambda$getTemplate$0);
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.field_237130_d_ = iResourceManager;
        this.templates.clear();
    }

    @Nullable
    private Template loadTemplateResource(ResourceLocation resourceLocation) {
        Template template;
        block9: {
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), "structures/" + resourceLocation.getPath() + ".nbt");
            IResource iResource = this.field_237130_d_.getResource(resourceLocation2);
            try {
                template = this.loadTemplate(iResource.getInputStream());
                if (iResource == null) break block9;
            } catch (Throwable throwable) {
                try {
                    if (iResource != null) {
                        try {
                            iResource.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (FileNotFoundException fileNotFoundException) {
                    return null;
                } catch (Throwable throwable3) {
                    LOGGER.error("Couldn't load structure {}: {}", (Object)resourceLocation, (Object)throwable3.toString());
                    return null;
                }
            }
            iResource.close();
        }
        return template;
    }

    @Nullable
    private Template loadTemplateFile(ResourceLocation resourceLocation) {
        Template template;
        if (!this.pathGenerated.toFile().isDirectory()) {
            return null;
        }
        Path path = this.resolvePath(resourceLocation, ".nbt");
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        try {
            template = this.loadTemplate(fileInputStream);
        } catch (Throwable throwable) {
            try {
                try {
                    ((InputStream)fileInputStream).close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            } catch (FileNotFoundException fileNotFoundException) {
                return null;
            } catch (IOException iOException) {
                LOGGER.error("Couldn't load structure from {}", (Object)path, (Object)iOException);
                return null;
            }
        }
        ((InputStream)fileInputStream).close();
        return template;
    }

    private Template loadTemplate(InputStream inputStream) throws IOException {
        CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(inputStream);
        return this.func_227458_a_(compoundNBT);
    }

    public Template func_227458_a_(CompoundNBT compoundNBT) {
        if (!compoundNBT.contains("DataVersion", 0)) {
            compoundNBT.putInt("DataVersion", 500);
        }
        Template template = new Template();
        template.read(NBTUtil.update(this.fixer, DefaultTypeReferences.STRUCTURE, compoundNBT, compoundNBT.getInt("DataVersion")));
        return template;
    }

    public boolean writeToFile(ResourceLocation resourceLocation) {
        boolean bl;
        Template template = this.templates.get(resourceLocation);
        if (template == null) {
            return true;
        }
        Path path = this.resolvePath(resourceLocation, ".nbt");
        Path path2 = path.getParent();
        if (path2 == null) {
            return true;
        }
        try {
            Files.createDirectories(Files.exists(path2, new LinkOption[0]) ? path2.toRealPath(new LinkOption[0]) : path2, new FileAttribute[0]);
        } catch (IOException iOException) {
            LOGGER.error("Failed to create parent directory: {}", (Object)path2);
            return true;
        }
        CompoundNBT compoundNBT = template.writeToNBT(new CompoundNBT());
        FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
        try {
            CompressedStreamTools.writeCompressed(compoundNBT, fileOutputStream);
            bl = true;
        } catch (Throwable throwable) {
            try {
                try {
                    ((OutputStream)fileOutputStream).close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            } catch (Throwable throwable3) {
                return true;
            }
        }
        ((OutputStream)fileOutputStream).close();
        return bl;
    }

    public Path resolvePathStructures(ResourceLocation resourceLocation, String string) {
        try {
            Path path = this.pathGenerated.resolve(resourceLocation.getNamespace());
            Path path2 = path.resolve("structures");
            return FileUtil.resolveResourcePath(path2, resourceLocation.getPath(), string);
        } catch (InvalidPathException invalidPathException) {
            throw new ResourceLocationException("Invalid resource path: " + resourceLocation, invalidPathException);
        }
    }

    private Path resolvePath(ResourceLocation resourceLocation, String string) {
        if (resourceLocation.getPath().contains("//")) {
            throw new ResourceLocationException("Invalid resource path: " + resourceLocation);
        }
        Path path = this.resolvePathStructures(resourceLocation, string);
        if (path.startsWith(this.pathGenerated) && FileUtil.isNormalized(path) && FileUtil.containsReservedName(path)) {
            return path;
        }
        throw new ResourceLocationException("Invalid resource path: " + path);
    }

    public void remove(ResourceLocation resourceLocation) {
        this.templates.remove(resourceLocation);
    }

    private Template lambda$getTemplate$0(ResourceLocation resourceLocation) {
        Template template = this.loadTemplateFile(resourceLocation);
        return template != null ? template : this.loadTemplateResource(resourceLocation);
    }
}

