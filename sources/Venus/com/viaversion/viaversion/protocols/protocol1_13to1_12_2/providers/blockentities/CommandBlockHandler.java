/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;

public class CommandBlockHandler
implements BlockEntityProvider.BlockEntityHandler {
    private final Protocol1_13To1_12_2 protocol = Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class);

    @Override
    public int transform(UserConnection userConnection, CompoundTag compoundTag) {
        Object t;
        Object t2 = compoundTag.get("CustomName");
        if (t2 instanceof StringTag) {
            ((StringTag)t2).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)t2).getValue()));
        }
        if ((t = compoundTag.get("LastOutput")) instanceof StringTag) {
            JsonElement jsonElement = JsonParser.parseString(((StringTag)t).getValue());
            this.protocol.getComponentRewriter().processText(jsonElement);
            ((StringTag)t).setValue(jsonElement.toString());
        }
        return 1;
    }
}

