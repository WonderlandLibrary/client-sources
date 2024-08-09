/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.io.IOException;

public class ComponentRewriter1_13<C extends ClientboundPacketType>
extends ComponentRewriter<C> {
    public ComponentRewriter1_13(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
    }

    @Override
    protected void handleHoverEvent(JsonObject jsonObject) {
        CompoundTag compoundTag;
        super.handleHoverEvent(jsonObject);
        String string = jsonObject.getAsJsonPrimitive("action").getAsString();
        if (!string.equals("show_item")) {
            return;
        }
        JsonElement jsonElement = jsonObject.get("value");
        if (jsonElement == null) {
            return;
        }
        String string2 = this.findItemNBT(jsonElement);
        if (string2 == null) {
            return;
        }
        try {
            compoundTag = BinaryTagIO.readString(string2);
        } catch (Exception exception) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Error reading NBT in show_item:" + string2);
                exception.printStackTrace();
            }
            return;
        }
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("tag");
        NumberTag numberTag = (NumberTag)compoundTag.get("Damage");
        short s = numberTag != null ? numberTag.asShort() : (short)0;
        DataItem dataItem = new DataItem();
        dataItem.setData(s);
        dataItem.setTag(compoundTag2);
        this.protocol.getItemRewriter().handleItemToClient(dataItem);
        if (s != dataItem.data()) {
            compoundTag.put("Damage", new ShortTag(dataItem.data()));
        }
        if (compoundTag2 != null) {
            compoundTag.put("tag", compoundTag2);
        }
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject2 = new JsonObject();
        jsonArray.add(jsonObject2);
        try {
            String string3 = BinaryTagIO.writeString(compoundTag);
            jsonObject2.addProperty("text", string3);
            jsonObject.add("value", jsonArray);
        } catch (IOException iOException) {
            Via.getPlatform().getLogger().warning("Error writing NBT in show_item:" + string2);
            iOException.printStackTrace();
        }
    }

    protected String findItemNBT(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                String string = this.findItemNBT(jsonElement2);
                if (string == null) continue;
                return string;
            }
        } else if (jsonElement.isJsonObject()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonObject().getAsJsonPrimitive("text");
            if (jsonPrimitive != null) {
                return jsonPrimitive.getAsString();
            }
        } else if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsJsonPrimitive().getAsString();
        }
        return null;
    }

    @Override
    protected void handleTranslate(JsonObject jsonObject, String string) {
        super.handleTranslate(jsonObject, string);
        String string2 = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(string);
        if (string2 == null) {
            string2 = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(string);
        }
        if (string2 != null) {
            jsonObject.addProperty("translate", string2);
        }
    }
}

