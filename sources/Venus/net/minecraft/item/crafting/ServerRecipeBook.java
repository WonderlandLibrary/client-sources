/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.item.crafting.RecipeBookStatus;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.play.server.SRecipeBookPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook
extends RecipeBook {
    private static final Logger LOGGER = LogManager.getLogger();

    public int add(Collection<IRecipe<?>> collection, ServerPlayerEntity serverPlayerEntity) {
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        int n = 0;
        for (IRecipe<?> iRecipe : collection) {
            ResourceLocation resourceLocation = iRecipe.getId();
            if (this.recipes.contains(resourceLocation) || iRecipe.isDynamic()) continue;
            this.unlock(resourceLocation);
            this.markNew(resourceLocation);
            arrayList.add(resourceLocation);
            CriteriaTriggers.RECIPE_UNLOCKED.trigger(serverPlayerEntity, iRecipe);
            ++n;
        }
        this.sendPacket(SRecipeBookPacket.State.ADD, serverPlayerEntity, arrayList);
        return n;
    }

    public int remove(Collection<IRecipe<?>> collection, ServerPlayerEntity serverPlayerEntity) {
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        int n = 0;
        for (IRecipe<?> iRecipe : collection) {
            ResourceLocation resourceLocation = iRecipe.getId();
            if (!this.recipes.contains(resourceLocation)) continue;
            this.lock(resourceLocation);
            arrayList.add(resourceLocation);
            ++n;
        }
        this.sendPacket(SRecipeBookPacket.State.REMOVE, serverPlayerEntity, arrayList);
        return n;
    }

    private void sendPacket(SRecipeBookPacket.State state, ServerPlayerEntity serverPlayerEntity, List<ResourceLocation> list) {
        serverPlayerEntity.connection.sendPacket(new SRecipeBookPacket(state, list, Collections.emptyList(), this.func_242139_a()));
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        this.func_242139_a().func_242160_b(compoundNBT);
        ListNBT listNBT = new ListNBT();
        for (Object object : this.recipes) {
            listNBT.add(StringNBT.valueOf(((ResourceLocation)object).toString()));
        }
        compoundNBT.put("recipes", listNBT);
        ListNBT listNBT2 = new ListNBT();
        for (ResourceLocation resourceLocation : this.newRecipes) {
            listNBT2.add(StringNBT.valueOf(resourceLocation.toString()));
        }
        compoundNBT.put("toBeDisplayed", listNBT2);
        return compoundNBT;
    }

    public void read(CompoundNBT compoundNBT, RecipeManager recipeManager) {
        this.func_242140_a(RecipeBookStatus.func_242154_a(compoundNBT));
        ListNBT listNBT = compoundNBT.getList("recipes", 8);
        this.deserializeRecipes(listNBT, this::unlock, recipeManager);
        ListNBT listNBT2 = compoundNBT.getList("toBeDisplayed", 8);
        this.deserializeRecipes(listNBT2, this::markNew, recipeManager);
    }

    private void deserializeRecipes(ListNBT listNBT, Consumer<IRecipe<?>> consumer, RecipeManager recipeManager) {
        for (int i = 0; i < listNBT.size(); ++i) {
            String string = listNBT.getString(i);
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string);
                Optional<IRecipe<?>> optional = recipeManager.getRecipe(resourceLocation);
                if (!optional.isPresent()) {
                    LOGGER.error("Tried to load unrecognized recipe: {} removed now.", (Object)resourceLocation);
                    continue;
                }
                consumer.accept(optional.get());
                continue;
            } catch (ResourceLocationException resourceLocationException) {
                LOGGER.error("Tried to load improperly formatted recipe: {} removed now.", (Object)string);
            }
        }
    }

    public void init(ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.connection.sendPacket(new SRecipeBookPacket(SRecipeBookPacket.State.INIT, this.recipes, this.newRecipes, this.func_242139_a()));
    }
}

