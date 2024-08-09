/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public final class Ingredient
implements Predicate<ItemStack> {
    public static final Ingredient EMPTY = new Ingredient(Stream.empty());
    private final IItemList[] acceptedItems;
    private ItemStack[] matchingStacks;
    private IntList matchingStacksPacked;

    private Ingredient(Stream<? extends IItemList> stream) {
        this.acceptedItems = (IItemList[])stream.toArray(Ingredient::lambda$new$0);
    }

    public ItemStack[] getMatchingStacks() {
        this.determineMatchingStacks();
        return this.matchingStacks;
    }

    private void determineMatchingStacks() {
        if (this.matchingStacks == null) {
            this.matchingStacks = (ItemStack[])Arrays.stream(this.acceptedItems).flatMap(Ingredient::lambda$determineMatchingStacks$1).distinct().toArray(Ingredient::lambda$determineMatchingStacks$2);
        }
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return true;
        }
        this.determineMatchingStacks();
        if (this.matchingStacks.length == 0) {
            return itemStack.isEmpty();
        }
        for (ItemStack itemStack2 : this.matchingStacks) {
            if (itemStack2.getItem() != itemStack.getItem()) continue;
            return false;
        }
        return true;
    }

    public IntList getValidItemStacksPacked() {
        if (this.matchingStacksPacked == null) {
            this.determineMatchingStacks();
            this.matchingStacksPacked = new IntArrayList(this.matchingStacks.length);
            for (ItemStack itemStack : this.matchingStacks) {
                this.matchingStacksPacked.add(RecipeItemHelper.pack(itemStack));
            }
            this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
        }
        return this.matchingStacksPacked;
    }

    public void write(PacketBuffer packetBuffer) {
        this.determineMatchingStacks();
        packetBuffer.writeVarInt(this.matchingStacks.length);
        for (int i = 0; i < this.matchingStacks.length; ++i) {
            packetBuffer.writeItemStack(this.matchingStacks[i]);
        }
    }

    public JsonElement serialize() {
        if (this.acceptedItems.length == 1) {
            return this.acceptedItems[0].serialize();
        }
        JsonArray jsonArray = new JsonArray();
        for (IItemList iItemList : this.acceptedItems) {
            jsonArray.add(iItemList.serialize());
        }
        return jsonArray;
    }

    public boolean hasNoMatchingItems() {
        return !(this.acceptedItems.length != 0 || this.matchingStacks != null && this.matchingStacks.length != 0 || this.matchingStacksPacked != null && !this.matchingStacksPacked.isEmpty());
    }

    private static Ingredient fromItemListStream(Stream<? extends IItemList> stream) {
        Ingredient ingredient = new Ingredient(stream);
        return ingredient.acceptedItems.length == 0 ? EMPTY : ingredient;
    }

    public static Ingredient fromItems(IItemProvider ... iItemProviderArray) {
        return Ingredient.fromStacks(Arrays.stream(iItemProviderArray).map(ItemStack::new));
    }

    public static Ingredient fromStacks(ItemStack ... itemStackArray) {
        return Ingredient.fromStacks(Arrays.stream(itemStackArray));
    }

    public static Ingredient fromStacks(Stream<ItemStack> stream) {
        return Ingredient.fromItemListStream(stream.filter(Ingredient::lambda$fromStacks$3).map(Ingredient::lambda$fromStacks$4));
    }

    public static Ingredient fromTag(ITag<Item> iTag) {
        return Ingredient.fromItemListStream(Stream.of(new TagList(iTag)));
    }

    public static Ingredient read(PacketBuffer packetBuffer) {
        int n = packetBuffer.readVarInt();
        return Ingredient.fromItemListStream(Stream.generate(() -> Ingredient.lambda$read$5(packetBuffer)).limit(n));
    }

    public static Ingredient deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            if (jsonElement.isJsonObject()) {
                return Ingredient.fromItemListStream(Stream.of(Ingredient.deserializeItemList(jsonElement.getAsJsonObject())));
            }
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (jsonArray.size() == 0) {
                    throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                }
                return Ingredient.fromItemListStream(StreamSupport.stream(jsonArray.spliterator(), false).map(Ingredient::lambda$deserialize$6));
            }
            throw new JsonSyntaxException("Expected item to be object or array of objects");
        }
        throw new JsonSyntaxException("Item cannot be null");
    }

    private static IItemList deserializeItemList(JsonObject jsonObject) {
        if (jsonObject.has("item") && jsonObject.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        }
        if (jsonObject.has("item")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "item"));
            Item item = Registry.ITEM.getOptional(resourceLocation).orElseThrow(() -> Ingredient.lambda$deserializeItemList$7(resourceLocation));
            return new SingleItemList(new ItemStack(item));
        }
        if (jsonObject.has("tag")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
            ITag<Item> iTag = TagCollectionManager.getManager().getItemTags().get(resourceLocation);
            if (iTag == null) {
                throw new JsonSyntaxException("Unknown item tag '" + resourceLocation + "'");
            }
            return new TagList(iTag);
        }
        throw new JsonParseException("An ingredient entry needs either a tag or an item");
    }

    @Override
    public boolean test(@Nullable Object object) {
        return this.test((ItemStack)object);
    }

    private static JsonSyntaxException lambda$deserializeItemList$7(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown item '" + resourceLocation + "'");
    }

    private static IItemList lambda$deserialize$6(JsonElement jsonElement) {
        return Ingredient.deserializeItemList(JSONUtils.getJsonObject(jsonElement, "item"));
    }

    private static SingleItemList lambda$read$5(PacketBuffer packetBuffer) {
        return new SingleItemList(packetBuffer.readItemStack());
    }

    private static SingleItemList lambda$fromStacks$4(ItemStack itemStack) {
        return new SingleItemList(itemStack);
    }

    private static boolean lambda$fromStacks$3(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }

    private static ItemStack[] lambda$determineMatchingStacks$2(int n) {
        return new ItemStack[n];
    }

    private static Stream lambda$determineMatchingStacks$1(IItemList iItemList) {
        return iItemList.getStacks().stream();
    }

    private static IItemList[] lambda$new$0(int n) {
        return new IItemList[n];
    }

    static interface IItemList {
        public Collection<ItemStack> getStacks();

        public JsonObject serialize();
    }

    static class TagList
    implements IItemList {
        private final ITag<Item> tag;

        private TagList(ITag<Item> iTag) {
            this.tag = iTag;
        }

        @Override
        public Collection<ItemStack> getStacks() {
            ArrayList<ItemStack> arrayList = Lists.newArrayList();
            for (Item item : this.tag.getAllElements()) {
                arrayList.add(new ItemStack(item));
            }
            return arrayList;
        }

        @Override
        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tag", TagCollectionManager.getManager().getItemTags().getValidatedIdFromTag(this.tag).toString());
            return jsonObject;
        }
    }

    static class SingleItemList
    implements IItemList {
        private final ItemStack stack;

        private SingleItemList(ItemStack itemStack) {
            this.stack = itemStack;
        }

        @Override
        public Collection<ItemStack> getStacks() {
            return Collections.singleton(this.stack);
        }

        @Override
        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registry.ITEM.getKey(this.stack.getItem()).toString());
            return jsonObject;
        }
    }
}

