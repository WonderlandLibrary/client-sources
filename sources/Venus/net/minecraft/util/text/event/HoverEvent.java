/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text.event;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoverEvent {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Action<?> action;
    private final Object value;

    public <T> HoverEvent(Action<T> action, T t) {
        this.action = action;
        this.value = t;
    }

    public Action<?> getAction() {
        return this.action;
    }

    @Nullable
    public <T> T getParameter(Action<T> action) {
        return this.action == action ? (T)action.castParameter(this.value) : null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            HoverEvent hoverEvent = (HoverEvent)object;
            return this.action == hoverEvent.action && Objects.equals(this.value, hoverEvent.value);
        }
        return true;
    }

    public String toString() {
        return "HoverEvent{action=" + this.action + ", value='" + this.value + "'}";
    }

    public int hashCode() {
        int n = this.action.hashCode();
        return 31 * n + (this.value != null ? this.value.hashCode() : 0);
    }

    @Nullable
    public static HoverEvent deserialize(JsonObject jsonObject) {
        String string = JSONUtils.getString(jsonObject, "action", null);
        if (string == null) {
            return null;
        }
        Action action = Action.getValueByCanonicalName(string);
        if (action == null) {
            return null;
        }
        JsonElement jsonElement = jsonObject.get("contents");
        if (jsonElement != null) {
            return action.deserialize(jsonElement);
        }
        IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("value"));
        return iFormattableTextComponent != null ? action.deserialize(iFormattableTextComponent) : null;
    }

    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", this.action.getCanonicalName());
        jsonObject.add("contents", this.action.serialize(this.value));
        return jsonObject;
    }

    public static class Action<T> {
        public static final Action<ITextComponent> SHOW_TEXT = new Action<ITextComponent>("show_text", true, ITextComponent.Serializer::getComponentFromJson, ITextComponent.Serializer::toJsonTree, Function.identity());
        public static final Action<ItemHover> SHOW_ITEM = new Action<ItemHover>("show_item", true, Action::lambda$static$0, Action::lambda$static$1, Action::lambda$static$2);
        public static final Action<EntityHover> SHOW_ENTITY = new Action<EntityHover>("show_entity", true, EntityHover::deserialize, EntityHover::serialize, EntityHover::deserialize);
        private static final Map<String, Action> NAME_MAPPING = Stream.of(SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY).collect(ImmutableMap.toImmutableMap(Action::getCanonicalName, Action::lambda$static$3));
        private final String canonicalName;
        private final boolean allowedInChat;
        private final Function<JsonElement, T> deserializeFromJSON;
        private final Function<T, JsonElement> serializeToJSON;
        private final Function<ITextComponent, T> deserializeFromTextComponent;

        public Action(String string, boolean bl, Function<JsonElement, T> function, Function<T, JsonElement> function2, Function<ITextComponent, T> function3) {
            this.canonicalName = string;
            this.allowedInChat = bl;
            this.deserializeFromJSON = function;
            this.serializeToJSON = function2;
            this.deserializeFromTextComponent = function3;
        }

        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }

        public String getCanonicalName() {
            return this.canonicalName;
        }

        @Nullable
        public static Action getValueByCanonicalName(String string) {
            return NAME_MAPPING.get(string);
        }

        private T castParameter(Object object) {
            return (T)object;
        }

        @Nullable
        public HoverEvent deserialize(JsonElement jsonElement) {
            T t = this.deserializeFromJSON.apply(jsonElement);
            return t == null ? null : new HoverEvent(this, t);
        }

        @Nullable
        public HoverEvent deserialize(ITextComponent iTextComponent) {
            T t = this.deserializeFromTextComponent.apply(iTextComponent);
            return t == null ? null : new HoverEvent(this, t);
        }

        public JsonElement serialize(Object object) {
            return this.serializeToJSON.apply(this.castParameter(object));
        }

        public String toString() {
            return "<action " + this.canonicalName + ">";
        }

        private static Action lambda$static$3(Action action) {
            return action;
        }

        private static ItemHover lambda$static$2(ITextComponent iTextComponent) {
            return ItemHover.deserialize(iTextComponent);
        }

        private static JsonElement lambda$static$1(ItemHover itemHover) {
            return itemHover.serialize();
        }

        private static ItemHover lambda$static$0(JsonElement jsonElement) {
            return ItemHover.deserialize(jsonElement);
        }
    }

    public static class ItemHover {
        private final Item item;
        private final int count;
        @Nullable
        private final CompoundNBT tag;
        @Nullable
        private ItemStack stack;

        ItemHover(Item item, int n, @Nullable CompoundNBT compoundNBT) {
            this.item = item;
            this.count = n;
            this.tag = compoundNBT;
        }

        public ItemHover(ItemStack itemStack) {
            this(itemStack.getItem(), itemStack.getCount(), itemStack.getTag() != null ? itemStack.getTag().copy() : null);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                ItemHover itemHover = (ItemHover)object;
                return this.count == itemHover.count && this.item.equals(itemHover.item) && Objects.equals(this.tag, itemHover.tag);
            }
            return true;
        }

        public int hashCode() {
            int n = this.item.hashCode();
            n = 31 * n + this.count;
            return 31 * n + (this.tag != null ? this.tag.hashCode() : 0);
        }

        public ItemStack createStack() {
            if (this.stack == null) {
                this.stack = new ItemStack(this.item, this.count);
                if (this.tag != null) {
                    this.stack.setTag(this.tag);
                }
            }
            return this.stack;
        }

        private static ItemHover deserialize(JsonElement jsonElement) {
            if (jsonElement.isJsonPrimitive()) {
                return new ItemHover(Registry.ITEM.getOrDefault(new ResourceLocation(jsonElement.getAsString())), 1, null);
            }
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "item");
            Item item = Registry.ITEM.getOrDefault(new ResourceLocation(JSONUtils.getString(jsonObject, "id")));
            int n = JSONUtils.getInt(jsonObject, "count", 1);
            if (jsonObject.has("tag")) {
                String string = JSONUtils.getString(jsonObject, "tag");
                try {
                    CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(string);
                    return new ItemHover(item, n, compoundNBT);
                } catch (CommandSyntaxException commandSyntaxException) {
                    LOGGER.warn("Failed to parse tag: {}", (Object)string, (Object)commandSyntaxException);
                }
            }
            return new ItemHover(item, n, null);
        }

        @Nullable
        private static ItemHover deserialize(ITextComponent iTextComponent) {
            try {
                CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(iTextComponent.getString());
                return new ItemHover(ItemStack.read(compoundNBT));
            } catch (CommandSyntaxException commandSyntaxException) {
                LOGGER.warn("Failed to parse item tag: {}", (Object)iTextComponent, (Object)commandSyntaxException);
                return null;
            }
        }

        private JsonElement serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", Registry.ITEM.getKey(this.item).toString());
            if (this.count != 1) {
                jsonObject.addProperty("count", this.count);
            }
            if (this.tag != null) {
                jsonObject.addProperty("tag", this.tag.toString());
            }
            return jsonObject;
        }
    }

    public static class EntityHover {
        public final EntityType<?> type;
        public final UUID id;
        @Nullable
        public final ITextComponent name;
        @Nullable
        private List<ITextComponent> tooltip;

        public EntityHover(EntityType<?> entityType, UUID uUID, @Nullable ITextComponent iTextComponent) {
            this.type = entityType;
            this.id = uUID;
            this.name = iTextComponent;
        }

        @Nullable
        public static EntityHover deserialize(JsonElement jsonElement) {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            EntityType<?> entityType = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(JSONUtils.getString(jsonObject, "type")));
            UUID uUID = UUID.fromString(JSONUtils.getString(jsonObject, "id"));
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("name"));
            return new EntityHover(entityType, uUID, iFormattableTextComponent);
        }

        @Nullable
        public static EntityHover deserialize(ITextComponent iTextComponent) {
            try {
                CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(iTextComponent.getString());
                IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("name"));
                EntityType<?> entityType = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(compoundNBT.getString("type")));
                UUID uUID = UUID.fromString(compoundNBT.getString("id"));
                return new EntityHover(entityType, uUID, iFormattableTextComponent);
            } catch (JsonSyntaxException | CommandSyntaxException exception) {
                return null;
            }
        }

        public JsonElement serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", Registry.ENTITY_TYPE.getKey(this.type).toString());
            jsonObject.addProperty("id", this.id.toString());
            if (this.name != null) {
                jsonObject.add("name", ITextComponent.Serializer.toJsonTree(this.name));
            }
            return jsonObject;
        }

        public List<ITextComponent> getTooltip() {
            if (this.tooltip == null) {
                this.tooltip = Lists.newArrayList();
                if (this.name != null) {
                    this.tooltip.add(this.name);
                }
                this.tooltip.add(new TranslationTextComponent("gui.entity_tooltip.type", this.type.getName()));
                this.tooltip.add(new StringTextComponent(this.id.toString()));
            }
            return this.tooltip;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                EntityHover entityHover = (EntityHover)object;
                return this.type.equals(entityHover.type) && this.id.equals(entityHover.id) && Objects.equals(this.name, entityHover.name);
            }
            return true;
        }

        public int hashCode() {
            int n = this.type.hashCode();
            n = 31 * n + this.id.hashCode();
            return 31 * n + (this.name != null ? this.name.hashCode() : 0);
        }
    }
}

