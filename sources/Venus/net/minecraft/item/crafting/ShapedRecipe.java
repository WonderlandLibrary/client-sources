/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ShapedRecipe
implements ICraftingRecipe {
    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public ShapedRecipe(ResourceLocation resourceLocation, String string, int n, int n2, NonNullList<Ingredient> nonNullList, ItemStack itemStack) {
        this.id = resourceLocation;
        this.group = string;
        this.recipeWidth = n;
        this.recipeHeight = n2;
        this.recipeItems = nonNullList;
        this.recipeOutput = itemStack;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SHAPED;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n >= this.recipeWidth && n2 >= this.recipeHeight;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        for (int i = 0; i <= craftingInventory.getWidth() - this.recipeWidth; ++i) {
            for (int j = 0; j <= craftingInventory.getHeight() - this.recipeHeight; ++j) {
                if (this.checkMatch(craftingInventory, i, j, false)) {
                    return false;
                }
                if (!this.checkMatch(craftingInventory, i, j, true)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkMatch(CraftingInventory craftingInventory, int n, int n2, boolean bl) {
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                int n3 = i - n;
                int n4 = j - n2;
                Ingredient ingredient = Ingredient.EMPTY;
                if (n3 >= 0 && n4 >= 0 && n3 < this.recipeWidth && n4 < this.recipeHeight) {
                    ingredient = bl ? this.recipeItems.get(this.recipeWidth - n3 - 1 + n4 * this.recipeWidth) : this.recipeItems.get(n3 + n4 * this.recipeWidth);
                }
                if (ingredient.test(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()))) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        return this.getRecipeOutput().copy();
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    public int getHeight() {
        return this.recipeHeight;
    }

    private static NonNullList<Ingredient> deserializeIngredients(String[] stringArray, Map<String, Ingredient> map, int n, int n2) {
        NonNullList<Ingredient> nonNullList = NonNullList.withSize(n * n2, Ingredient.EMPTY);
        HashSet<String> hashSet = Sets.newHashSet(map.keySet());
        hashSet.remove(" ");
        for (int i = 0; i < stringArray.length; ++i) {
            for (int j = 0; j < stringArray[i].length(); ++j) {
                String string = stringArray[i].substring(j, j + 1);
                Ingredient ingredient = map.get(string);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }
                hashSet.remove(string);
                nonNullList.set(j + n * i, ingredient);
            }
        }
        if (!hashSet.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + hashSet);
        }
        return nonNullList;
    }

    @VisibleForTesting
    static String[] shrink(String ... stringArray) {
        int n = Integer.MAX_VALUE;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            n = Math.min(n, ShapedRecipe.firstNonSpace(string));
            int n5 = ShapedRecipe.lastNonSpace(string);
            n2 = Math.max(n2, n5);
            if (n5 < 0) {
                if (n3 == i) {
                    ++n3;
                }
                ++n4;
                continue;
            }
            n4 = 0;
        }
        if (stringArray.length == n4) {
            return new String[0];
        }
        String[] stringArray2 = new String[stringArray.length - n4 - n3];
        for (int i = 0; i < stringArray2.length; ++i) {
            stringArray2[i] = stringArray[i + n3].substring(n, n2 + 1);
        }
        return stringArray2;
    }

    private static int firstNonSpace(String string) {
        int n;
        for (n = 0; n < string.length() && string.charAt(n) == ' '; ++n) {
        }
        return n;
    }

    private static int lastNonSpace(String string) {
        int n;
        for (n = string.length() - 1; n >= 0 && string.charAt(n) == ' '; --n) {
        }
        return n;
    }

    private static String[] patternFromJson(JsonArray jsonArray) {
        String[] stringArray = new String[jsonArray.size()];
        if (stringArray.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        }
        if (stringArray.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string = JSONUtils.getString(jsonArray.get(i), "pattern[" + i + "]");
            if (string.length() > 3) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }
            if (i > 0 && stringArray[0].length() != string.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            stringArray[i] = string;
        }
        return stringArray;
    }

    private static Map<String, Ingredient> deserializeKey(JsonObject jsonObject) {
        HashMap<String, Ingredient> hashMap = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            hashMap.put(entry.getKey(), Ingredient.deserialize(entry.getValue()));
        }
        hashMap.put(" ", Ingredient.EMPTY);
        return hashMap;
    }

    public static ItemStack deserializeItem(JsonObject jsonObject) {
        String string = JSONUtils.getString(jsonObject, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(string)).orElseThrow(() -> ShapedRecipe.lambda$deserializeItem$0(string));
        if (jsonObject.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        int n = JSONUtils.getInt(jsonObject, "count", 1);
        return new ItemStack(item, n);
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }

    private static JsonSyntaxException lambda$deserializeItem$0(String string) {
        return new JsonSyntaxException("Unknown item '" + string + "'");
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IRecipeSerializer<ShapedRecipe> {
        @Override
        public ShapedRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.deserializeKey(JSONUtils.getJsonObject(jsonObject, "key"));
            String[] stringArray = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(JSONUtils.getJsonArray(jsonObject, "pattern")));
            int n = stringArray[0].length();
            int n2 = stringArray.length;
            NonNullList<Ingredient> nonNullList = ShapedRecipe.deserializeIngredients(stringArray, map, n, n2);
            ItemStack itemStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(jsonObject, "result"));
            return new ShapedRecipe(resourceLocation, string, n, n2, nonNullList, itemStack);
        }

        @Override
        public ShapedRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            int n = packetBuffer.readVarInt();
            int n2 = packetBuffer.readVarInt();
            String string = packetBuffer.readString(Short.MAX_VALUE);
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(n * n2, Ingredient.EMPTY);
            for (int i = 0; i < nonNullList.size(); ++i) {
                nonNullList.set(i, Ingredient.read(packetBuffer));
            }
            ItemStack itemStack = packetBuffer.readItemStack();
            return new ShapedRecipe(resourceLocation, string, n, n2, nonNullList, itemStack);
        }

        @Override
        public void write(PacketBuffer packetBuffer, ShapedRecipe shapedRecipe) {
            packetBuffer.writeVarInt(shapedRecipe.recipeWidth);
            packetBuffer.writeVarInt(shapedRecipe.recipeHeight);
            packetBuffer.writeString(shapedRecipe.group);
            for (Ingredient ingredient : shapedRecipe.recipeItems) {
                ingredient.write(packetBuffer);
            }
            packetBuffer.writeItemStack(shapedRecipe.recipeOutput);
        }

        @Override
        public void write(PacketBuffer packetBuffer, IRecipe iRecipe) {
            this.write(packetBuffer, (ShapedRecipe)iRecipe);
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return this.read(resourceLocation, packetBuffer);
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.read(resourceLocation, jsonObject);
        }
    }
}

