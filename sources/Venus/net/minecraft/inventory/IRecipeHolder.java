/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public interface IRecipeHolder {
    public void setRecipeUsed(@Nullable IRecipe<?> var1);

    @Nullable
    public IRecipe<?> getRecipeUsed();

    default public void onCrafting(PlayerEntity playerEntity) {
        IRecipe<?> iRecipe = this.getRecipeUsed();
        if (iRecipe != null && !iRecipe.isDynamic()) {
            playerEntity.unlockRecipes(Collections.singleton(iRecipe));
            this.setRecipeUsed(null);
        }
    }

    default public boolean canUseRecipe(World world, ServerPlayerEntity serverPlayerEntity, IRecipe<?> iRecipe) {
        if (!iRecipe.isDynamic() && world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) && !serverPlayerEntity.getRecipeBook().isUnlocked(iRecipe)) {
            return true;
        }
        this.setRecipeUsed(iRecipe);
        return false;
    }
}

