/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.rewriters;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.MappingData;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.IdRewriteFunction;
import us.myles.ViaVersion.api.rewriters.RegistryType;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.fastutil.ints.IntArrayList;

public class TagRewriter {
    private static final int[] EMPTY_ARRAY = new int[0];
    private final Protocol protocol;
    private final IdRewriteFunction entityRewriter;
    private final List<TagData> newBlockTags = new ArrayList<TagData>();
    private final List<TagData> newItemTags = new ArrayList<TagData>();
    private final List<TagData> newEntityTags = new ArrayList<TagData>();

    public TagRewriter(Protocol protocol, @Nullable IdRewriteFunction entityRewriter) {
        this.protocol = protocol;
        this.entityRewriter = entityRewriter;
    }

    public void addEmptyTag(RegistryType tagType, String id) {
        this.getNewTags(tagType).add(new TagData(id, EMPTY_ARRAY));
    }

    public void addEmptyTags(RegistryType tagType, String ... ids) {
        List<TagData> tagList = this.getNewTags(tagType);
        for (String id : ids) {
            tagList.add(new TagData(id, EMPTY_ARRAY));
        }
    }

    public void addTag(RegistryType tagType, String id, int ... oldIds) {
        List<TagData> newTags = this.getNewTags(tagType);
        IdRewriteFunction rewriteFunction = this.getRewriter(tagType);
        for (int i = 0; i < oldIds.length; ++i) {
            int oldId = oldIds[i];
            oldIds[i] = rewriteFunction.rewrite(oldId);
        }
        newTags.add(new TagData(id, oldIds));
    }

    public void register(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    MappingData mappingData = TagRewriter.this.protocol.getMappingData();
                    TagRewriter.this.handle(wrapper, id -> mappingData != null ? Integer.valueOf(mappingData.getNewBlockId(id)) : null, TagRewriter.this.newBlockTags);
                    TagRewriter.this.handle(wrapper, id -> mappingData != null ? Integer.valueOf(mappingData.getNewItemId(id)) : null, TagRewriter.this.newItemTags);
                    if (TagRewriter.this.entityRewriter == null && TagRewriter.this.newEntityTags.isEmpty()) {
                        return;
                    }
                    int fluidTagsSize = wrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < fluidTagsSize; ++i) {
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                    }
                    TagRewriter.this.handle(wrapper, TagRewriter.this.entityRewriter, TagRewriter.this.newEntityTags);
                });
            }
        });
    }

    private void handle(PacketWrapper wrapper, IdRewriteFunction rewriteFunction, List<TagData> newTags) throws Exception {
        int tagsSize = wrapper.read(Type.VAR_INT);
        wrapper.write(Type.VAR_INT, newTags != null ? tagsSize + newTags.size() : tagsSize);
        for (int i = 0; i < tagsSize; ++i) {
            wrapper.passthrough(Type.STRING);
            int[] ids = wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if (rewriteFunction != null) {
                IntArrayList idList = new IntArrayList(ids.length);
                for (int id : ids) {
                    int mappedId = rewriteFunction.rewrite(id);
                    if (mappedId == -1) continue;
                    idList.add(mappedId);
                }
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, idList.toArray(EMPTY_ARRAY));
                continue;
            }
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, ids);
        }
        if (newTags != null && !newTags.isEmpty()) {
            for (TagData tag : newTags) {
                wrapper.write(Type.STRING, tag.identifier);
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.entries);
            }
        }
    }

    private List<TagData> getNewTags(RegistryType tagType) {
        switch (tagType) {
            case BLOCK: {
                return this.newBlockTags;
            }
            case ITEM: {
                return this.newItemTags;
            }
            case ENTITY: {
                return this.newEntityTags;
            }
        }
        return null;
    }

    @Nullable
    private IdRewriteFunction getRewriter(RegistryType tagType) {
        switch (tagType) {
            case BLOCK: {
                return this.protocol.getMappingData().getBlockMappings() != null ? id -> this.protocol.getMappingData().getNewBlockId(id) : null;
            }
            case ITEM: {
                return this.protocol.getMappingData().getItemMappings() != null ? id -> this.protocol.getMappingData().getNewItemId(id) : null;
            }
            case ENTITY: {
                return this.entityRewriter;
            }
        }
        return null;
    }

    private static final class TagData {
        private final String identifier;
        private final int[] entries;

        private TagData(String identifier, int[] entries) {
            this.identifier = identifier;
            this.entries = entries;
        }
    }
}

