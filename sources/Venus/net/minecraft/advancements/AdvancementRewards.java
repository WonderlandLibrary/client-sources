/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class AdvancementRewards {
    public static final AdvancementRewards EMPTY = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.EMPTY);
    private final int experience;
    private final ResourceLocation[] loot;
    private final ResourceLocation[] recipes;
    private final FunctionObject.CacheableFunction function;

    public AdvancementRewards(int n, ResourceLocation[] resourceLocationArray, ResourceLocation[] resourceLocationArray2, FunctionObject.CacheableFunction cacheableFunction) {
        this.experience = n;
        this.loot = resourceLocationArray;
        this.recipes = resourceLocationArray2;
        this.function = cacheableFunction;
    }

    public void apply(ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.giveExperiencePoints(this.experience);
        LootContext lootContext = new LootContext.Builder(serverPlayerEntity.getServerWorld()).withParameter(LootParameters.THIS_ENTITY, serverPlayerEntity).withParameter(LootParameters.field_237457_g_, serverPlayerEntity.getPositionVec()).withRandom(serverPlayerEntity.getRNG()).build(LootParameterSets.ADVANCEMENT);
        boolean bl = false;
        for (ResourceLocation resourceLocation : this.loot) {
            for (ItemStack itemStack : serverPlayerEntity.server.getLootTableManager().getLootTableFromLocation(resourceLocation).generate(lootContext)) {
                if (serverPlayerEntity.addItemStackToInventory(itemStack)) {
                    serverPlayerEntity.world.playSound(null, serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((serverPlayerEntity.getRNG().nextFloat() - serverPlayerEntity.getRNG().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    bl = true;
                    continue;
                }
                ItemEntity itemEntity = serverPlayerEntity.dropItem(itemStack, true);
                if (itemEntity == null) continue;
                itemEntity.setNoPickupDelay();
                itemEntity.setOwnerId(serverPlayerEntity.getUniqueID());
            }
        }
        if (bl) {
            serverPlayerEntity.container.detectAndSendChanges();
        }
        if (this.recipes.length > 0) {
            serverPlayerEntity.unlockRecipes(this.recipes);
        }
        MinecraftServer minecraftServer = serverPlayerEntity.server;
        this.function.func_218039_a(minecraftServer.getFunctionManager()).ifPresent(arg_0 -> AdvancementRewards.lambda$apply$0(minecraftServer, serverPlayerEntity, arg_0));
    }

    public String toString() {
        return "AdvancementRewards{experience=" + this.experience + ", loot=" + Arrays.toString(this.loot) + ", recipes=" + Arrays.toString(this.recipes) + ", function=" + this.function + "}";
    }

    public JsonElement serialize() {
        JsonArray jsonArray;
        if (this == EMPTY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.experience != 0) {
            jsonObject.addProperty("experience", this.experience);
        }
        if (this.loot.length > 0) {
            jsonArray = new JsonArray();
            for (ResourceLocation resourceLocation : this.loot) {
                jsonArray.add(resourceLocation.toString());
            }
            jsonObject.add("loot", jsonArray);
        }
        if (this.recipes.length > 0) {
            jsonArray = new JsonArray();
            for (ResourceLocation resourceLocation : this.recipes) {
                jsonArray.add(resourceLocation.toString());
            }
            jsonObject.add("recipes", jsonArray);
        }
        if (this.function.getId() != null) {
            jsonObject.addProperty("function", this.function.getId().toString());
        }
        return jsonObject;
    }

    public static AdvancementRewards deserializeRewards(JsonObject jsonObject) throws JsonParseException {
        int n = JSONUtils.getInt(jsonObject, "experience", 0);
        JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "loot", new JsonArray());
        ResourceLocation[] resourceLocationArray = new ResourceLocation[jsonArray.size()];
        for (int i = 0; i < resourceLocationArray.length; ++i) {
            resourceLocationArray[i] = new ResourceLocation(JSONUtils.getString(jsonArray.get(i), "loot[" + i + "]"));
        }
        JsonArray jsonArray2 = JSONUtils.getJsonArray(jsonObject, "recipes", new JsonArray());
        ResourceLocation[] resourceLocationArray2 = new ResourceLocation[jsonArray2.size()];
        for (int i = 0; i < resourceLocationArray2.length; ++i) {
            resourceLocationArray2[i] = new ResourceLocation(JSONUtils.getString(jsonArray2.get(i), "recipes[" + i + "]"));
        }
        FunctionObject.CacheableFunction cacheableFunction = jsonObject.has("function") ? new FunctionObject.CacheableFunction(new ResourceLocation(JSONUtils.getString(jsonObject, "function"))) : FunctionObject.CacheableFunction.EMPTY;
        return new AdvancementRewards(n, resourceLocationArray, resourceLocationArray2, cacheableFunction);
    }

    private static void lambda$apply$0(MinecraftServer minecraftServer, ServerPlayerEntity serverPlayerEntity, FunctionObject functionObject) {
        minecraftServer.getFunctionManager().execute(functionObject, serverPlayerEntity.getCommandSource().withFeedbackDisabled().withPermissionLevel(2));
    }

    public static class Builder {
        private int experience;
        private final List<ResourceLocation> loot = Lists.newArrayList();
        private final List<ResourceLocation> recipes = Lists.newArrayList();
        @Nullable
        private ResourceLocation function;

        public static Builder experience(int n) {
            return new Builder().addExperience(n);
        }

        public Builder addExperience(int n) {
            this.experience += n;
            return this;
        }

        public static Builder recipe(ResourceLocation resourceLocation) {
            return new Builder().addRecipe(resourceLocation);
        }

        public Builder addRecipe(ResourceLocation resourceLocation) {
            this.recipes.add(resourceLocation);
            return this;
        }

        public AdvancementRewards build() {
            return new AdvancementRewards(this.experience, this.loot.toArray(new ResourceLocation[0]), this.recipes.toArray(new ResourceLocation[0]), this.function == null ? FunctionObject.CacheableFunction.EMPTY : new FunctionObject.CacheableFunction(this.function));
        }
    }
}

