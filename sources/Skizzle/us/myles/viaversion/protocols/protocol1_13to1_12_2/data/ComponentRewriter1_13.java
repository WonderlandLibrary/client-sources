/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data;

import java.io.IOException;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.nbt.BinaryTagIO;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.rewriters.ComponentRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonPrimitive;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;

public class ComponentRewriter1_13
extends ComponentRewriter {
    public ComponentRewriter1_13(Protocol protocol) {
        super(protocol);
    }

    public ComponentRewriter1_13() {
    }

    @Override
    protected void handleHoverEvent(JsonObject hoverEvent) {
        CompoundTag tag;
        super.handleHoverEvent(hoverEvent);
        String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
        if (!action.equals("show_item")) {
            return;
        }
        JsonElement value = hoverEvent.get("value");
        if (value == null) {
            return;
        }
        String text = this.findItemNBT(value);
        if (text == null) {
            return;
        }
        try {
            tag = BinaryTagIO.readString(text);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().warning("Error reading NBT in show_item:" + text);
            throw new RuntimeException(e);
        }
        CompoundTag itemTag = (CompoundTag)tag.get("tag");
        ShortTag damageTag = (ShortTag)tag.get("Damage");
        short damage = damageTag != null ? damageTag.getValue() : (short)0;
        Item item = new Item();
        item.setData(damage);
        item.setTag(itemTag);
        this.handleItem(item);
        if (damage != item.getData()) {
            tag.put(new ShortTag("Damage", item.getData()));
        }
        if (itemTag != null) {
            tag.put(itemTag);
        }
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        array.add(object);
        try {
            String serializedNBT = BinaryTagIO.writeString(tag);
            object.addProperty("text", serializedNBT);
            hoverEvent.add("value", array);
        }
        catch (IOException e) {
            Via.getPlatform().getLogger().warning("Error writing NBT in show_item:" + text);
            e.printStackTrace();
        }
    }

    protected void handleItem(Item item) {
        InventoryPackets.toClient(item);
    }

    protected String findItemNBT(JsonElement element) {
        if (element.isJsonArray()) {
            for (JsonElement jsonElement : element.getAsJsonArray()) {
                String value = this.findItemNBT(jsonElement);
                if (value == null) continue;
                return value;
            }
        } else if (element.isJsonObject()) {
            JsonPrimitive text = element.getAsJsonObject().getAsJsonPrimitive("text");
            if (text != null) {
                return text.getAsString();
            }
        } else if (element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive().getAsString();
        }
        return null;
    }

    @Override
    protected void handleTranslate(JsonObject object, String translate) {
        super.handleTranslate(object, translate);
        String newTranslate = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(translate);
        if (newTranslate == null) {
            newTranslate = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(translate);
        }
        if (newTranslate != null) {
            object.addProperty("translate", newTranslate);
        }
    }
}

