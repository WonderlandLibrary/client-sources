/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public final class ItemTag {
    private final String nbt;

    private ItemTag(String nbt) {
        this.nbt = nbt;
    }

    public static ItemTag ofNbt(String nbt) {
        return new ItemTag(nbt);
    }

    private static Builder builder() {
        return new Builder();
    }

    public String toString() {
        return "ItemTag(nbt=" + this.getNbt() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemTag)) {
            return false;
        }
        ItemTag other = (ItemTag)o;
        String this$nbt = this.getNbt();
        String other$nbt = other.getNbt();
        return !(this$nbt == null ? other$nbt != null : !this$nbt.equals(other$nbt));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $nbt = this.getNbt();
        result = result * 59 + ($nbt == null ? 43 : $nbt.hashCode());
        return result;
    }

    public String getNbt() {
        return this.nbt;
    }

    private static class Builder {
        private String nbt;

        Builder() {
        }

        private Builder nbt(String nbt) {
            this.nbt = nbt;
            return this;
        }

        private ItemTag build() {
            return new ItemTag(this.nbt);
        }

        public String toString() {
            return "ItemTag.Builder(nbt=" + this.nbt + ")";
        }
    }

    public static class Serializer
    implements JsonSerializer<ItemTag>,
    JsonDeserializer<ItemTag> {
        @Override
        public ItemTag deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return ItemTag.ofNbt(element.getAsJsonPrimitive().getAsString());
        }

        @Override
        public JsonElement serialize(ItemTag itemTag, Type type, JsonSerializationContext context) {
            return context.serialize(itemTag.getNbt());
        }
    }
}

