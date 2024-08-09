/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TagRewriter<C extends ClientboundPacketType> {
    private static final int[] EMPTY_ARRAY = new int[0];
    private final Protocol<C, ?, ?, ?> protocol;
    private final Map<RegistryType, List<TagData>> newTags = new EnumMap<RegistryType, List<TagData>>(RegistryType.class);
    private final Map<RegistryType, Map<String, String>> toRename = new EnumMap<RegistryType, Map<String, String>>(RegistryType.class);
    private final Set<String> toRemove = new HashSet<String>();

    public TagRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void loadFromMappingData() {
        for (RegistryType registryType : RegistryType.getValues()) {
            List<TagData> list = this.protocol.getMappingData().getTags(registryType);
            if (list == null) continue;
            this.getOrComputeNewTags(registryType).addAll(list);
        }
    }

    public void removeTags(String string) {
        this.toRemove.add(string);
    }

    public void renameTag(RegistryType registryType, String string, String string2) {
        this.toRename.computeIfAbsent(registryType, TagRewriter::lambda$renameTag$0).put(string, string2);
    }

    public void addEmptyTag(RegistryType registryType, String string) {
        this.getOrComputeNewTags(registryType).add(new TagData(string, EMPTY_ARRAY));
    }

    public void addEmptyTags(RegistryType registryType, String ... stringArray) {
        List<TagData> list = this.getOrComputeNewTags(registryType);
        for (String string : stringArray) {
            list.add(new TagData(string, EMPTY_ARRAY));
        }
    }

    public void addEntityTag(String string, EntityType ... entityTypeArray) {
        int[] nArray = new int[entityTypeArray.length];
        for (int i = 0; i < entityTypeArray.length; ++i) {
            nArray[i] = entityTypeArray[i].getId();
        }
        this.addTagRaw(RegistryType.ENTITY, string, nArray);
    }

    public void addTag(RegistryType registryType, String string, int ... nArray) {
        List<TagData> list = this.getOrComputeNewTags(registryType);
        IdRewriteFunction idRewriteFunction = this.getRewriter(registryType);
        if (idRewriteFunction != null) {
            for (int i = 0; i < nArray.length; ++i) {
                int n = nArray[i];
                nArray[i] = idRewriteFunction.rewrite(n);
            }
        }
        list.add(new TagData(string, nArray));
    }

    public void addTagRaw(RegistryType registryType, String string, int ... nArray) {
        this.getOrComputeNewTags(registryType).add(new TagData(string, nArray));
    }

    public void register(C c, @Nullable RegistryType registryType) {
        this.protocol.registerClientbound(c, this.getHandler(registryType));
    }

    public void registerGeneric(C c) {
        this.protocol.registerClientbound(c, this.getGenericHandler());
    }

    public PacketHandler getHandler(@Nullable RegistryType registryType) {
        return arg_0 -> this.lambda$getHandler$1(registryType, arg_0);
    }

    public PacketHandler getGenericHandler() {
        return this::lambda$getGenericHandler$2;
    }

    public void handle(PacketWrapper packetWrapper, @Nullable IdRewriteFunction idRewriteFunction, @Nullable List<TagData> list) throws Exception {
        this.handle(packetWrapper, idRewriteFunction, list, null);
    }

    /*
     * WARNING - void declaration
     */
    public void handle(PacketWrapper packetWrapper, @Nullable IdRewriteFunction idRewriteFunction, @Nullable List<TagData> list, @Nullable Map<String, String> map) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT, list != null ? n + list.size() : n);
        for (int i = 0; i < n; ++i) {
            void object2;
            Object object;
            String string = packetWrapper.read(Type.STRING);
            if (map != null && (object = map.get(string)) != null) {
                String string2 = object;
            }
            packetWrapper.write(Type.STRING, object2);
            object = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if (idRewriteFunction != null) {
                IntArrayList intArrayList = new IntArrayList(((Object)object).length);
                for (Object object3 : object) {
                    int n2 = idRewriteFunction.rewrite((int)object3);
                    if (n2 == -1) continue;
                    intArrayList.add(n2);
                }
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, intArrayList.toArray(EMPTY_ARRAY));
                continue;
            }
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, object);
        }
        if (list != null) {
            for (TagData tagData : list) {
                packetWrapper.write(Type.STRING, tagData.identifier());
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tagData.entries());
            }
        }
    }

    public @Nullable List<TagData> getNewTags(RegistryType registryType) {
        return this.newTags.get((Object)registryType);
    }

    public List<TagData> getOrComputeNewTags(RegistryType registryType) {
        return this.newTags.computeIfAbsent(registryType, TagRewriter::lambda$getOrComputeNewTags$3);
    }

    public @Nullable IdRewriteFunction getRewriter(RegistryType registryType) {
        MappingData mappingData = this.protocol.getMappingData();
        switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[registryType.ordinal()]) {
            case 1: {
                return mappingData != null && mappingData.getBlockMappings() != null ? mappingData::getNewBlockId : null;
            }
            case 2: {
                return mappingData != null && mappingData.getItemMappings() != null ? mappingData::getNewItemId : null;
            }
            case 3: {
                return this.protocol.getEntityRewriter() != null ? this::lambda$getRewriter$4 : null;
            }
        }
        return null;
    }

    private int lambda$getRewriter$4(int n) {
        return this.protocol.getEntityRewriter().newEntityId(n);
    }

    private static List lambda$getOrComputeNewTags$3(RegistryType registryType) {
        return new ArrayList();
    }

    private void lambda$getGenericHandler$2(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = n = packetWrapper.passthrough(Type.VAR_INT).intValue();
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            if (this.toRemove.contains(string)) {
                packetWrapper.set(Type.VAR_INT, 0, --n2);
                int n3 = packetWrapper.read(Type.VAR_INT);
                for (int j = 0; j < n3; ++j) {
                    packetWrapper.read(Type.STRING);
                    packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                }
                continue;
            }
            packetWrapper.write(Type.STRING, string);
            string = Key.stripMinecraftNamespace(string);
            RegistryType registryType = RegistryType.getByKey(string);
            if (registryType != null) {
                this.handle(packetWrapper, this.getRewriter(registryType), this.getNewTags(registryType), this.toRename.get((Object)registryType));
                continue;
            }
            this.handle(packetWrapper, null, null, null);
        }
    }

    private void lambda$getHandler$1(RegistryType registryType, PacketWrapper packetWrapper) throws Exception {
        for (RegistryType registryType2 : RegistryType.getValues()) {
            this.handle(packetWrapper, this.getRewriter(registryType2), this.getNewTags(registryType2), this.toRename.get((Object)registryType2));
            if (registryType2 == registryType) break;
        }
    }

    private static Map lambda$renameTag$0(RegistryType registryType) {
        return new HashMap();
    }
}

