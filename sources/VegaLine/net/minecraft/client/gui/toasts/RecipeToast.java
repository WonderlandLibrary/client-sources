/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class RecipeToast
implements IToast {
    private final List<ItemStack> field_193666_c = Lists.newArrayList();
    private long field_193667_d;
    private boolean field_193668_e;

    public RecipeToast(ItemStack p_i47489_1_) {
        this.field_193666_c.add(p_i47489_1_);
    }

    @Override
    public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
        if (this.field_193668_e) {
            this.field_193667_d = p_193653_2_;
            this.field_193668_e = false;
        }
        if (this.field_193666_c.isEmpty()) {
            return IToast.Visibility.HIDE;
        }
        p_193653_1_.func_192989_b().getTextureManager().bindTexture(field_193654_a);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        p_193653_1_.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
        p_193653_1_.func_192989_b().fontRendererObj.drawString(I18n.format("recipe.toast.title", new Object[0]), 30.0f, 7.0, -11534256);
        p_193653_1_.func_192989_b().fontRendererObj.drawString(I18n.format("recipe.toast.description", new Object[0]), 30.0f, 18.0, -16777216);
        RenderHelper.enableGUIStandardItemLighting();
        p_193653_1_.func_192989_b().getRenderItem().renderItemAndEffectIntoGUI(null, this.field_193666_c.get((int)(p_193653_2_ / (5000L / (long)this.field_193666_c.size()) % (long)this.field_193666_c.size())), 8, 8);
        return p_193653_2_ - this.field_193667_d >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

    public void func_193664_a(ItemStack p_193664_1_) {
        if (this.field_193666_c.add(p_193664_1_)) {
            this.field_193668_e = true;
        }
    }

    public static void func_193665_a(GuiToast p_193665_0_, IRecipe p_193665_1_) {
        RecipeToast recipetoast = p_193665_0_.func_192990_a(RecipeToast.class, field_193655_b);
        if (recipetoast == null) {
            p_193665_0_.func_192988_a(new RecipeToast(p_193665_1_.getRecipeOutput()));
        } else {
            recipetoast.func_193664_a(p_193665_1_.getRecipeOutput());
        }
    }
}

