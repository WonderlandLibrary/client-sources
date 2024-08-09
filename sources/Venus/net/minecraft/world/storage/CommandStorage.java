/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class CommandStorage {
    private final Map<String, Container> loadedContainers = Maps.newHashMap();
    private final DimensionSavedDataManager manager;

    public CommandStorage(DimensionSavedDataManager dimensionSavedDataManager) {
        this.manager = dimensionSavedDataManager;
    }

    private Container createContainer(String string, String string2) {
        Container container = new Container(string2);
        this.loadedContainers.put(string, container);
        return container;
    }

    public CompoundNBT getData(ResourceLocation resourceLocation) {
        String string;
        String string2 = resourceLocation.getNamespace();
        Container container = this.manager.get(() -> this.lambda$getData$0(string2, string = CommandStorage.prefixStorageNamespace(string2)), string);
        return container != null ? container.getData(resourceLocation.getPath()) : new CompoundNBT();
    }

    public void setData(ResourceLocation resourceLocation, CompoundNBT compoundNBT) {
        String string = resourceLocation.getNamespace();
        String string2 = CommandStorage.prefixStorageNamespace(string);
        this.manager.getOrCreate(() -> this.lambda$setData$1(string, string2), string2).setData(resourceLocation.getPath(), compoundNBT);
    }

    public Stream<ResourceLocation> getSavedDataKeys() {
        return this.loadedContainers.entrySet().stream().flatMap(CommandStorage::lambda$getSavedDataKeys$2);
    }

    private static String prefixStorageNamespace(String string) {
        return "command_storage_" + string;
    }

    private static Stream lambda$getSavedDataKeys$2(Map.Entry entry) {
        return ((Container)entry.getValue()).getSavedKeys((String)entry.getKey());
    }

    private Container lambda$setData$1(String string, String string2) {
        return this.createContainer(string, string2);
    }

    private Container lambda$getData$0(String string, String string2) {
        return this.createContainer(string, string2);
    }

    static class Container
    extends WorldSavedData {
        private final Map<String, CompoundNBT> contents = Maps.newHashMap();

        public Container(String string) {
            super(string);
        }

        @Override
        public void read(CompoundNBT compoundNBT) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("contents");
            for (String string : compoundNBT2.keySet()) {
                this.contents.put(string, compoundNBT2.getCompound(string));
            }
        }

        @Override
        public CompoundNBT write(CompoundNBT compoundNBT) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            this.contents.forEach((arg_0, arg_1) -> Container.lambda$write$0(compoundNBT2, arg_0, arg_1));
            compoundNBT.put("contents", compoundNBT2);
            return compoundNBT;
        }

        public CompoundNBT getData(String string) {
            CompoundNBT compoundNBT = this.contents.get(string);
            return compoundNBT != null ? compoundNBT : new CompoundNBT();
        }

        public void setData(String string, CompoundNBT compoundNBT) {
            if (compoundNBT.isEmpty()) {
                this.contents.remove(string);
            } else {
                this.contents.put(string, compoundNBT);
            }
            this.markDirty();
        }

        public Stream<ResourceLocation> getSavedKeys(String string) {
            return this.contents.keySet().stream().map(arg_0 -> Container.lambda$getSavedKeys$1(string, arg_0));
        }

        private static ResourceLocation lambda$getSavedKeys$1(String string, String string2) {
            return new ResourceLocation(string, string2);
        }

        private static void lambda$write$0(CompoundNBT compoundNBT, String string, CompoundNBT compoundNBT2) {
            compoundNBT.put(string, compoundNBT2.copy());
        }
    }
}

