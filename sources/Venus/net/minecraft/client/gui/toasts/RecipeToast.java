/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeToast
implements IToast {
    private static final ITextComponent field_243272_c = new TranslationTextComponent("recipe.toast.title");
    private static final ITextComponent field_243273_d = new TranslationTextComponent("recipe.toast.description");
    private final List<IRecipe<?>> recipes = Lists.newArrayList();
    private long firstDrawTime;
    private boolean hasNewOutputs;

    public RecipeToast(IRecipe<?> iRecipe) {
        this.recipes.add(iRecipe);
    }

    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long l) {
        if (this.hasNewOutputs) {
            this.firstDrawTime = l;
            this.hasNewOutputs = false;
        }
        if (this.recipes.isEmpty()) {
            return IToast.Visibility.HIDE;
        }
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        toastGui.blit(matrixStack, 0, 0, 0, 32, this.func_230445_a_(), this.func_238540_d_());
        toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, field_243272_c, 30.0f, 7.0f, -11534256);
        toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, field_243273_d, 30.0f, 18.0f, -16777216);
        IRecipe<?> iRecipe = this.recipes.get((int)(l / Math.max(1L, 5000L / (long)this.recipes.size()) % (long)this.recipes.size()));
        ItemStack itemStack = iRecipe.getIcon();
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.6f, 0.6f, 1.0f);
        toastGui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(itemStack, 3, 3);
        RenderSystem.popMatrix();
        toastGui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(iRecipe.getRecipeOutput(), 8, 8);
        return l - this.firstDrawTime >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

    private void addRecipe(IRecipe<?> iRecipe) {
        this.recipes.add(iRecipe);
        this.hasNewOutputs = true;
    }

    public static void addOrUpdate(ToastGui toastGui, IRecipe<?> iRecipe) {
        RecipeToast recipeToast = toastGui.getToast(RecipeToast.class, NO_TOKEN);
        if (recipeToast == null) {
            toastGui.add(new RecipeToast(iRecipe));
        } else {
            recipeToast.addRecipe(iRecipe);
        }
    }
}

