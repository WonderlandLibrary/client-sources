/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowledgeBookItem
extends Item {
    private static final Logger LOGGER = LogManager.getLogger();

    public KnowledgeBookItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        CompoundNBT compoundNBT = itemStack.getTag();
        if (!playerEntity.abilities.isCreativeMode) {
            playerEntity.setHeldItem(hand, ItemStack.EMPTY);
        }
        if (compoundNBT != null && compoundNBT.contains("Recipes", 0)) {
            if (!world.isRemote) {
                ListNBT listNBT = compoundNBT.getList("Recipes", 8);
                ArrayList<IRecipe<?>> arrayList = Lists.newArrayList();
                RecipeManager recipeManager = world.getServer().getRecipeManager();
                for (int i = 0; i < listNBT.size(); ++i) {
                    String string = listNBT.getString(i);
                    Optional<IRecipe<?>> optional = recipeManager.getRecipe(new ResourceLocation(string));
                    if (!optional.isPresent()) {
                        LOGGER.error("Invalid recipe: {}", (Object)string);
                        return ActionResult.resultFail(itemStack);
                    }
                    arrayList.add(optional.get());
                }
                playerEntity.unlockRecipes(arrayList);
                playerEntity.addStat(Stats.ITEM_USED.get(this));
            }
            return ActionResult.func_233538_a_(itemStack, world.isRemote());
        }
        LOGGER.error("Tag not valid: {}", (Object)compoundNBT);
        return ActionResult.resultFail(itemStack);
    }
}

